package org.amharicstudybible.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun VerseStudyScreen(verseReference: String, verseText: String) {
    Column(Modifier.fillMaxSize().padding(16.dp)) {
        Text(verseReference)
        Text(verseText, Modifier.padding(vertical = 12.dp))
        Text("Original Language")
        Text("Word Study")
        Text("Historical Background")
        Text("Theological Context")
        Text("Church Fathers")
        Text("Cross References")
        Text("AI Study — separate from offline sources")
    }
}

@Composable
fun WordStudyScreen(lemma: String, strongs: String?, amharicMeaning: String?) {
    Column(Modifier.fillMaxSize().padding(16.dp)) {
        Text(lemma)
        if (strongs != null) Text("Strong's: $strongs")
        Text("English meaning")
        Text(amharicMeaning ?: "🇪🇹 Amharic explanation not yet imported")
        Text("Morphology")
        Text("Semantic range")
        Text("Verse-specific sense")
        Text("Other occurrences")
    }
}

@Composable
fun OfflineSearchBox(onSearch: (String) -> Unit) {
    var query by remember { mutableStateOf("") }
    Column(Modifier.padding(16.dp)) {
        TextField(value = query, onValueChange = { query = it }, label = { Text("Search Bible, Greek, Hebrew, Amharic…") })
        Button(onClick = { onSearch(query) }, Modifier.padding(top = 8.dp)) { Text("Search offline") }
    }
}
