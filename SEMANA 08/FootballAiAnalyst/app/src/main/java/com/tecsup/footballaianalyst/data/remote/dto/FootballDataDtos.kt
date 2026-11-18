package com.tecsup.footballaianalyst.data.remote.dto

import com.google.gson.annotations.SerializedName

data class FootballDataMatchesResponse(
    @SerializedName("matches") val matches: List<FootballDataMatchDto>
)

data class FootballDataMatchDto(
    @SerializedName("id") val id: Int,
    @SerializedName("utcDate") val utcDate: String,
    @SerializedName("status") val status: String,
    @SerializedName("competition") val competition: CompetitionDto?,
    @SerializedName("homeTeam") val homeTeam: TeamFdDto,
    @SerializedName("awayTeam") val awayTeam: TeamFdDto,
    @SerializedName("score") val score: ScoreFdDto?
)

data class CompetitionDto(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("emblem") val emblem: String?
)

data class TeamFdDto(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("crest") val crest: String?
)

data class ScoreFdDto(
    @SerializedName("winner") val winner: String?,
    @SerializedName("fullTime") val fullTime: GoalsFdDto?
)

data class GoalsFdDto(
    @SerializedName("home") val home: Int?,
    @SerializedName("away") val away: Int?
)
