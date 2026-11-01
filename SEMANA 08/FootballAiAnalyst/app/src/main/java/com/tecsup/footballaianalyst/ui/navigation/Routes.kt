package com.tecsup.footballaianalyst.ui.navigation

sealed class Routes(val route: String) {
    object Home : Routes("home")
    object Standings : Routes("standings")
    object Settings : Routes("settings")
    object MatchDetail : Routes("match_detail")
}
