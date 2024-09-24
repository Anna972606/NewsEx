package com.example.newsex.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.newsex.presentation.ui.EveryThingScreen
import com.example.newsex.presentation.ui.MainScreen
import com.example.newsex.presentation.ui.TopHeadlinesScreen

@Composable
fun RootNavHost() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.MainScreen.route) {
        composable(Screen.MainScreen.route) { MainScreen(navController) }
        composable(Screen.TopHeadLineScreen.route) { TopHeadlinesScreen() }
        composable(Screen.EveryThingScreen.route) { EveryThingScreen() }
    }
}