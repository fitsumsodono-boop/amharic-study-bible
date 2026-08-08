package org.amharicstudybible.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.amharicstudybible.data.local.CrossReferenceEntity
import org.amharicstudybible.data.local.HistoricalRecordEntity
import org.amharicstudybible.data.local.LexicalEntryEntity
import org.amharicstudybible.data.local.MorphologyEntryEntity
import org.amharicstudybible.data.local.PatristicRecordEntity
import org.amharicstudybible.data.local.StudyDao

/** Single access point for scholarly study data. UI/AI code does not depend on individual datasets. */
class StudyDataRepository(private val dao: StudyDao) {
    suspend fun wordStudy(lemma: String, strongs: String): List<LexicalEntryEntity> =
        withContext(Dispatchers.IO) { dao.lexical(lemma, strongs) }

    suspend fun morphology(reference: String): List<MorphologyEntryEntity> =
        withContext(Dispatchers.IO) { dao.morphology(reference) }

    suspend fun crossReferences(reference: String): List<CrossReferenceEntity> =
        withContext(Dispatchers.IO) { dao.crossReferences(reference) }

    suspend fun historical(reference: String, query: String = reference): List<HistoricalRecordEntity> =
        withContext(Dispatchers.IO) { dao.historical(reference, query) }

    suspend fun churchFathers(reference: String, query: String = reference): List<PatristicRecordEntity> =
        withContext(Dispatchers.IO) { dao.patristic(reference, query) }
}
