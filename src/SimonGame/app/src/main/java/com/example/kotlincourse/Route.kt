package com.example.kotlincourse

sealed class Route(val destination: String) {
    data object MainMenu : Route("main_menu")
    data object NewGame : Route("new_game")
    data object FreeGame : Route("free_game")
    data object Settings : Route("setting_screen")
    data object About : Route("info_screen")

}