package org.amharicstudybible.data.local

import androidx.room.Entity
import androidx.room.Index

@Entity(tableName = "study_sources", primaryKeys = ["sourceId"])
data class StudySourceEntity(
    val sourceId: String,
    val name: String,
    val role: String,
    val license: String,
    val attribution: String,
    val sourceUrl: String? = null,
    val verified: Boolean = false
)

@Entity(
    tableName = "lexical_entries",
    primaryKeys = ["id"],
    indices = [Index("lemma"), Index("strongs")]
)
data class LexicalEntryEntity(
    val id: String,
    val sourceId: String,
    val language: String,
    val lemma: String,
    val strongs: String?,
    val gloss: String?,
    val definition: String?,
    val sourceUrl: String?
)

@Entity(
    tableName = "morphology_entries",
    primaryKeys = ["id"],
    indices = [Index("reference"), Index("lemma"), Index("strongs")]
)
data class MorphologyEntryEntity(
    val id: String,
    val sourceId: String,
    val reference: String,
    val language: String,
    val surface: String,
    val lemma: String?,
    val strongs: String?,
    val morphology: String?,
    val syntax: String?
)

@Entity(
    tableName = "cross_references",
    primaryKeys = ["id"],
    indices = [Index("fromReference"), Index("toReference")]
)
data class CrossReferenceEntity(
    val id: String,
    val sourceId: String,
    val fromReference: String,
    val toReference: String,
    val relationship: String?
)

@Entity(
    tableName = "historical_records",
    primaryKeys = ["id"],
    indices = [Index("reference"), Index("topic")]
)
data class HistoricalRecordEntity(
    val id: String,
    val sourceId: String,
    val reference: String?,
    val topic: String,
    val title: String,
    val text: String,
    val sourceUrl: String?
)

@Entity(
    tableName = "patristic_records",
    primaryKeys = ["id"],
    indices = [Index("reference"), Index("author")]
)
data class PatristicRecordEntity(
    val id: String,
    val sourceId: String,
    val author: String,
    val work: String,
    val reference: String?,
    val text: String,
    val sourceUrl: String?
)
