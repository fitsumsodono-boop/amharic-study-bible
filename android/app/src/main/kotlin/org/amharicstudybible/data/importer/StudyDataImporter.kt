package org.amharicstudybible.data.importer

import android.content.Context
import org.amharicstudybible.data.local.CrossReferenceEntity
import org.amharicstudybible.data.local.HistoricalRecordEntity
import org.amharicstudybible.data.local.LexicalEntryEntity
import org.amharicstudybible.data.local.MorphologyEntryEntity
import org.amharicstudybible.data.local.PatristicRecordEntity
import org.amharicstudybible.data.local.StudyDao
import org.amharicstudybible.data.local.StudySourceEntity
import org.json.JSONArray
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader

/** Imports normalized JSON records from app assets into Room.
 *  Bundling is deliberately blocked for unverified sources.
 */
class StudyDataImporter(
    private val context: Context,
    private val dao: StudyDao
) {
    suspend fun importJsonAsset(assetPath: String, expectedSourceId: String, verified: Boolean) {
        require(verified) { "Refusing to import an unverified dataset: $expectedSourceId" }
        val json = context.assets.open(assetPath).use { input ->
            BufferedReader(InputStreamReader(input, Charsets.UTF_8)).use { it.readText() }
        }
        val root = JSONObject(json)
        val source = root.getJSONObject("source")
        require(source.getString("sourceId") == expectedSourceId)
        require(source.optBoolean("verified", false))

        dao.upsertSource(
            StudySourceEntity(
                sourceId = expectedSourceId,
                name = source.getString("name"),
                role = source.getString("role"),
                license = source.getString("license"),
                attribution = source.getString("attribution"),
                sourceUrl = source.optString("sourceUrl").ifBlank { null },
                verified = true
            )
        )

        val records = root.optJSONArray("records") ?: JSONArray()
        val lexical = mutableListOf<LexicalEntryEntity>()
        val morphology = mutableListOf<MorphologyEntryEntity>()
        val crossRefs = mutableListOf<CrossReferenceEntity>()
        val history = mutableListOf<HistoricalRecordEntity>()
        val fathers = mutableListOf<PatristicRecordEntity>()

        for (i in 0 until records.length()) {
            val r = records.getJSONObject(i)
            when (r.getString("type")) {
                "lexical" -> lexical += LexicalEntryEntity(
                    r.getString("id"), expectedSourceId, r.optString("language"),
                    r.optString("lemma"), r.optString("strongs").ifBlank { null },
                    r.optString("gloss").ifBlank { null }, r.optString("definition").ifBlank { null },
                    r.optString("sourceUrl").ifBlank { null }
                )
                "morphology" -> morphology += MorphologyEntryEntity(
                    r.getString("id"), expectedSourceId, r.optString("reference"),
                    r.optString("language"), r.optString("surface"), r.optString("lemma").ifBlank { null },
                    r.optString("strongs").ifBlank { null }, r.optString("morphology").ifBlank { null },
                    r.optString("syntax").ifBlank { null }
                )
                "cross_reference" -> crossRefs += CrossReferenceEntity(
                    r.getString("id"), expectedSourceId, r.getString("fromReference"),
                    r.getString("toReference"), r.optString("relationship").ifBlank { null }
                )
                "historical" -> history += HistoricalRecordEntity(
                    r.getString("id"), expectedSourceId, r.optString("reference").ifBlank { null },
                    r.getString("topic"), r.getString("title"), r.getString("text"),
                    r.optString("sourceUrl").ifBlank { null }
                )
                "patristic" -> fathers += PatristicRecordEntity(
                    r.getString("id"), expectedSourceId, r.getString("author"), r.getString("work"),
                    r.optString("reference").ifBlank { null }, r.getString("text"),
                    r.optString("sourceUrl").ifBlank { null }
                )
            }
        }

        if (lexical.isNotEmpty()) dao.upsertLexical(lexical)
        if (morphology.isNotEmpty()) dao.upsertMorphology(morphology)
        if (crossRefs.isNotEmpty()) dao.upsertCrossReferences(crossRefs)
        if (history.isNotEmpty()) dao.upsertHistorical(history)
        if (fathers.isNotEmpty()) dao.upsertPatristic(fathers)
    }
}
