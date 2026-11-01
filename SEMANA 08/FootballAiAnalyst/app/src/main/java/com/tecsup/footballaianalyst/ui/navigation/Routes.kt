package com.tecsup.footballaianalyst.ui.navigation

sealed class Routes(val route: String) {
    object Home : Routes("home")
    object TeamSearch : Routes("team_search")
    object TeamDetail : Routes("team_detail")
    object TeamCompare : Routes("team_compare")
    object Standings : Routes("standings")
    object Settings : Routes("settings")
    object MatchDetail : Routes("match_detail")
}
