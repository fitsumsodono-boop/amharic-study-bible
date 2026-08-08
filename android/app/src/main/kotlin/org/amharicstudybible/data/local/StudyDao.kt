package org.amharicstudybible.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface StudyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertSource(source: StudySourceEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertLexical(entries: List<LexicalEntryEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertMorphology(entries: List<MorphologyEntryEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertCrossReferences(entries: List<CrossReferenceEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertHistorical(entries: List<HistoricalRecordEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertPatristic(entries: List<PatristicRecordEntity>)

    @Query("SELECT * FROM lexical_entries WHERE lemma = :lemma OR strongs = :strongs")
    suspend fun lexical(lemma: String, strongs: String): List<LexicalEntryEntity>

    @Query("SELECT * FROM morphology_entries WHERE reference = :reference ORDER BY id")
    suspend fun morphology(reference: String): List<MorphologyEntryEntity>

    @Query("SELECT * FROM cross_references WHERE fromReference = :reference")
    suspend fun crossReferences(reference: String): List<CrossReferenceEntity>

    @Query("SELECT * FROM historical_records WHERE reference = :reference OR topic LIKE '%' || :query || '%'")
    suspend fun historical(reference: String, query: String): List<HistoricalRecordEntity>

    @Query("SELECT * FROM patristic_records WHERE reference = :reference OR text LIKE '%' || :query || '%'")
    suspend fun patristic(reference: String, query: String): List<PatristicRecordEntity>
}
