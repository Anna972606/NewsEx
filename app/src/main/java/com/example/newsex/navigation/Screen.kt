package com.example.newsex.navigation

sealed class Screen(val route: String) {
    object MainScreen : Screen(Route.MAIN_SCREEN)
    object TopHeadLineScreen : Screen(Route.TOP_HEADLINE_SCREEN)
    object EveryThingScreen : Screen(Route.GET_EVERY_THING_SCREEN)
}