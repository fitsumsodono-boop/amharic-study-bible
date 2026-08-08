package org.amharicstudybible.data

/**
 * Contract for the offline Bible database.
 *
 * The implementation will be backed by the bundled SQLite/Room database.
 * AI/network services must not be required by this interface.
 */
interface OfflineDatabase {
    fun getVerse(versionId: String, book: String, chapter: Int, verse: Int): VerseRecord?
    fun search(query: String): List<SearchRecord>
    fun getWordLinks(verseId: String): List<WordLinkRecord>
}

data class VerseRecord(val id: String, val versionId: String, val book: String, val chapter: Int, val verse: Int, val text: String)
data class WordLinkRecord(val id: String, val verseId: String, val token: String, val language: String, val lemma: String?, val strongs: String?, val lexicalEntryId: String?)
data class SearchRecord(val id: String, val kind: String, val title: String, val snippet: String)
