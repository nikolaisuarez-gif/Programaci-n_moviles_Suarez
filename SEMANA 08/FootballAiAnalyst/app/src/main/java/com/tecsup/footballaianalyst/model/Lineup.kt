package com.tecsup.footballaianalyst.model

data class Player(
    val id: Int,
    val name: String,
    val number: Int,
    val position: String,
    val isSubstitute: Boolean = false
)

data class TeamLineup(
    val team: Team,
    val formation: String,
    val coach: String,
    val startingEleven: List<Player>,
    val substitutes: List<Player>
)

data class MatchLineups(
    val homeLineup: TeamLineup,
    val awayLineup: TeamLineup
)
