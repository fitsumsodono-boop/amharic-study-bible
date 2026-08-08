package org.amharicstudybible.data

import android.content.Context
import org.json.JSONArray
import org.json.JSONObject

/** Small local store for bookmarks, notes and study requests. Bible text remains in OfflineDatabase. */
class StudyStore(context: Context) {
    private val prefs = context.getSharedPreferences("study_store", Context.MODE_PRIVATE)

    fun bookmarks(): List<VerseReference> = readRefs("bookmarks")

    fun toggleBookmark(ref: VerseReference) {
        val current = bookmarks().toMutableList()
        if (!current.remove(ref)) current.add(ref)
        writeRefs("bookmarks", current)
    }

    fun notes(): List<VerseNote> = try {
        val a = JSONArray(prefs.getString("notes", "[]"))
        (0 until a.length()).map { val o = a.getJSONObject(it); VerseNote(VerseReference(o.getString("book"), o.getInt("chapter"), o.getInt("verse")), o.getString("text"), o.getLong("updatedAt")) }
    } catch (_: Exception) { emptyList() }

    fun saveNote(ref: VerseReference, text: String) {
        val list = notes().filterNot { it.ref == ref }.toMutableList()
        if (text.isNotBlank()) list.add(VerseNote(ref, text, System.currentTimeMillis()))
        val a = JSONArray(); list.forEach { a.put(JSONObject().put("book", it.ref.book).put("chapter", it.ref.chapter).put("verse", it.ref.verse).put("text", it.text).put("updatedAt", it.updatedAt)) }
        prefs.edit().putString("notes", a.toString()).apply()
    }

    private fun readRefs(key: String): List<VerseReference> = try {
        val a = JSONArray(prefs.getString(key, "[]")); (0 until a.length()).map { val o = a.getJSONObject(it); VerseReference(o.getString("book"), o.getInt("chapter"), o.getInt("verse")) }
    } catch (_: Exception) { emptyList() }

    private fun writeRefs(key: String, refs: List<VerseReference>) {
        val a = JSONArray(); refs.forEach { a.put(JSONObject().put("book", it.book).put("chapter", it.chapter).put("verse", it.verse)) }
        prefs.edit().putString(key, a.toString()).apply()
    }
}

data class VerseReference(val book: String, val chapter: Int, val verse: Int)
data class VerseNote(val ref: VerseReference, val text: String, val updatedAt: Long)
