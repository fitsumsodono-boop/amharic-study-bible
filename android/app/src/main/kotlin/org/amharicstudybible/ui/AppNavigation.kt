package org.amharicstudybible.ui

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

sealed class Screen(val route: String) {
    data object Home : Screen("home")
    data object Bible : Screen("bible")
    data object Search : Screen("search")
    data object VerseStudy : Screen("verse/{verseId}")
    data object WordStudy : Screen("word/{lexicalEntryId}")
}

@Composable
fun AppNavigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screen.Home.route) {
        composable(Screen.Home.route) { HomeScreen(navController) }
        composable(Screen.Bible.route) { BibleScreen(navController) }
        composable(Screen.Search.route) { SearchScreen(navController) }
        composable(Screen.VerseStudy.route) { Text("Verse Study") }
        composable(Screen.WordStudy.route) { Text("Word Study") }
    }
}

@Composable
fun HomeScreen(navController: NavHostController) = Text("Amharic Study Bible")
@Composable
fun BibleScreen(navController: NavHostController) = Text("Bible Reader")
@Composable
fun SearchScreen(navController: NavHostController) = Text("Offline Search")
