package org.amharicstudybible.data

import android.database.Cursor
import android.database.sqlite.SQLiteDatabase

/** Read-only adapter over the bundled offline SQLite database. */
class SqliteOfflineDatabase(private val db: SQLiteDatabase) : OfflineDatabase {
    override fun getVerse(versionId: String, book: String, chapter: Int, verse: Int): VerseRecord? {
        db.query("verses", arrayOf("id", "version_id", "book", "chapter", "verse", "text"),
            "version_id=? AND book=? AND chapter=? AND verse=?",
            arrayOf(versionId, book, chapter.toString(), verse.toString()), null, null, null
        ).use { c -> return if (c.moveToFirst()) c.toVerse() else null }
    }

    override fun search(query: String): List<SearchRecord> {
        db.rawQuery("SELECT record_id, kind, search_text, amharic_text FROM search_fts WHERE search_fts MATCH ? LIMIT 100", arrayOf(query + "*"))
            .use { c ->
                val out = mutableListOf<SearchRecord>()
                while (c.moveToNext()) out += SearchRecord(c.getString(0), c.getString(1), c.getString(2), c.getString(3))
                return out
            }
    }

    override fun getWordLinks(verseId: String): List<WordLinkRecord> {
        db.query("verse_word_links", arrayOf("id", "verse_id", "token", "language", "lemma", "strongs", "lexical_entry_id"),
            "verse_id=?", arrayOf(verseId), null, null, "position ASC").use { c ->
            val out = mutableListOf<WordLinkRecord>()
            while (c.moveToNext()) out += WordLinkRecord(c.getString(0), c.getString(1), c.getString(2), c.getString(3), c.getStringOrNull(4), c.getStringOrNull(5), c.getStringOrNull(6))
            return out
        }
    }

    private fun Cursor.getStringOrNull(index: Int): String? = if (isNull(index)) null else getString(index)
    private fun Cursor.toVerse() = VerseRecord(getString(0), getString(1), getString(2), getInt(3), getInt(4), getString(5))
}
