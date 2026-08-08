package org.amharicstudybible.data

/** Remote source plus the existing offline database, keeping the app usable offline. */
class BibleDataSources(
    private val api: AbiduApiClient,
    private val offline: OfflineDatabase
) {
    fun cachedVerse(versionId: String, book: String, chapter: Int, verse: Int): VerseRecord? =
        offline.getVerse(versionId, book, chapter, verse)

    fun cachedSearch(query: String): List<SearchRecord> = offline.search(query.trim())

    fun wordStudy(verseId: String): List<WordLinkRecord> = offline.getWordLinks(verseId)

    fun remoteChapter(version: String, book: String, chapter: Int): List<RemoteVerse> =
        api.chapter(version, book, chapter)

    fun remoteVerse(version: String, book: String, chapter: Int, verse: Int): RemoteVerse? =
        api.verse(version, book, chapter, verse)

    fun remoteSearch(version: String, query: String): List<RemoteVerse> =
        api.search(version, query)
}
