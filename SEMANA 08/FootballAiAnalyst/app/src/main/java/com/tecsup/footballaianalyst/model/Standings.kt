package com.tecsup.footballaianalyst.model

data class TeamStanding(
    val rank: Int,
    val team: Team,
    val points: Int,
    val playedGames: Int,
    val won: Int,
    val draw: Int,
    val lost: Int,
    val goalsFor: Int,
    val goalsAgainst: Int,
    val goalDifference: Int,
    val form: String?
)

data class LeagueStandings(
    val leagueName: String,
    val logo: String,
    val standings: List<TeamStanding>
)
