package org.amharicstudybible.data

class BibleRepository(private val database: OfflineDatabase) {
    fun verse(versionId: String, book: String, chapter: Int, verse: Int) =
        database.getVerse(versionId, book, chapter, verse)

    fun search(query: String) = database.search(query.trim())

    fun words(verseId: String) = database.getWordLinks(verseId)
}
