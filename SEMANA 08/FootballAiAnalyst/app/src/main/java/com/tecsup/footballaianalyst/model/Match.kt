package com.tecsup.footballaianalyst.model

data class Match(
    val id: Int,
    val competitionName: String,
    val competitionLogo: String,
    val homeTeam: Team,
    val awayTeam: Team,
    val homeScore: Int?,
    val awayScore: Int?,
    val status: String, // "LIVE", "FT", "NS" (Not Started)
    val elapsedMinutes: Int?,
    val date: String,
    val time: String,
    val referee: String?,
    val stadium: String?
)

data class Team(
    val id: Int,
    val name: String,
    val logo: String,
    val form: String? = null
)
