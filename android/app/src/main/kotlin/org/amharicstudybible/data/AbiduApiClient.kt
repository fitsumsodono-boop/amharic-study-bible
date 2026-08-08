package org.amharicstudybible.data

import org.json.JSONArray
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URLEncoder
import java.net.URL

/** Thin client for the public Abidu Bible API. */
class AbiduApiClient(
    private val baseUrl: String = "https://bible-api-kappa.vercel.app"
) {
    fun listBookIds(): List<BookRef> {
        val data = getJson("/api/v1/listbookids").optJSONArray("data") ?: JSONArray()
        val books = data.optJSONObject(0)?.optJSONObject("books") ?: JSONObject()
        return books.keys().asSequence()
            .mapNotNull { key -> books.optString(key, null)?.let { BookRef(key.toIntOrNull() ?: 0, it) } }
            .filter { it.code.isNotBlank() }
            .sortedBy { it.number }
            .toList()
    }

    fun bookInfo(book: String): BookInfo {
        val data = getJson("/api/v1/book/info/${encode(book)}").optJSONObject("data") ?: JSONObject()
        val chapters = data.optJSONObject("chapters") ?: JSONObject()
        val counts = chapters.keys().asSequence().associateWith { chapters.optInt(it, 0) }
        return BookInfo(data.optString("book", book), data.optInt("totchapter", counts.size), counts)
    }

    fun chapter(version: String, book: String, chapter: Int): List<RemoteVerse> {
        return parseVerseArray(getJson("/api/v1/verses/${encode(version)}/${encode(book)}/$chapter").optJSONArray("data"))
    }

    fun verse(version: String, book: String, chapter: Int, verse: Int): RemoteVerse? {
        return parseVerse(getJson("/api/v1/verses/${encode(version)}/${encode(book)}/$chapter/$verse").optJSONObject("data"))
    }

    fun search(version: String, query: String): List<RemoteVerse> {
        require(query.isNotBlank()) { "Search query must not be blank" }
        val q = URLEncoder.encode(query.trim(), "UTF-8")
        return parseVerseArray(getJson("/api/v1/search/${encode(version)}/verse?q=$q").optJSONArray("data"))
    }

    private fun parseVerseArray(array: JSONArray?): List<RemoteVerse> {
        if (array == null) return emptyList()
        return (0 until array.length()).mapNotNull { parseVerse(array.optJSONObject(it)) }
    }

    private fun parseVerse(obj: JSONObject?): RemoteVerse? {
        if (obj == null) return null
        val book = obj.optString("book").takeIf { it.isNotBlank() } ?: return null
        val chapter = obj.optInt("chapter", -1).takeIf { it > 0 } ?: return null
        val number = obj.optString("verseNum").toIntOrNull() ?: return null
        return RemoteVerse(book, chapter, number, obj.optString("verse"))
    }

    private fun getJson(path: String): JSONObject {
        val connection = (URL(baseUrl.trimEnd('/') + path).openConnection() as HttpURLConnection).apply {
            requestMethod = "GET"
            connectTimeout = 15_000
            readTimeout = 20_000
            setRequestProperty("Accept", "application/json")
            useCaches = true
        }
        return try {
            val code = connection.responseCode
            val stream = if (code in 200..299) connection.inputStream else connection.errorStream
            val body = BufferedReader(InputStreamReader(stream, Charsets.UTF_8)).use { it.readText() }
            if (code !in 200..299) error("Abidu API HTTP $code: $body")
            JSONObject(body)
        } finally {
            connection.disconnect()
        }
    }

    private fun encode(value: String) = URLEncoder.encode(value, "UTF-8")
}

data class BookRef(val number: Int, val code: String)
data class BookInfo(val book: String, val totalChapters: Int, val verseCounts: Map<String, Int>)
data class RemoteVerse(val book: String, val chapter: Int, val verse: Int, val text: String)
