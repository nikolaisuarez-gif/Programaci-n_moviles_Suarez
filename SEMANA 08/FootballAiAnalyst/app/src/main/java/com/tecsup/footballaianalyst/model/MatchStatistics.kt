package com.tecsup.footballaianalyst.model

data class MatchStatistics(
    val matchId: Int,
    val homeCorners: Int,
    val awayCorners: Int,
    val homeFouls: Int,
    val awayFouls: Int,
    val homeYellowCards: Int,
    val awayYellowCards: Int,
    val homeRedCards: Int,
    val awayRedCards: Int,
    val homeShotsOnTarget: Int,
    val awayShotsOnTarget: Int,
    val homeShotsOffTarget: Int,
    val awayShotsOffTarget: Int,
    val homePossessionPercent: Int,
    val awayPossessionPercent: Int,
    val homePassesTotal: Int,
    val awayPassesTotal: Int,
    val homePassAccuracy: Int,
    val awayPassAccuracy: Int,
    val homeOffsides: Int,
    val awayOffsides: Int
)
