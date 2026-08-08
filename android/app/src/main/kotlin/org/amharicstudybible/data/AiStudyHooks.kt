package org.amharicstudybible.data

/** AI is optional; Bible reading and offline study never depend on this interface. */
interface AiStudyHooks {
    suspend fun explainVerse(reference: VerseReference, verseText: String, context: StudyContext = StudyContext()): AiStudyResult
    suspend fun summarizeChapter(book: String, chapter: Int, verses: List<VerseRecord>): AiStudyResult
    suspend fun answerQuestion(question: String, context: List<VerseRecord>): AiStudyResult
}

data class StudyContext(
    val wordLinks: List<WordLinkRecord> = emptyList(),
    val crossReferences: List<VerseReference> = emptyList(),
    val historicalSources: List<String> = emptyList(),
    val patristicSources: List<String> = emptyList()
)

data class AiStudyResult(val answer: String, val sources: List<StudySource> = emptyList())
data class StudySource(val id: String, val title: String, val attribution: String?, val license: String?, val url: String?)
