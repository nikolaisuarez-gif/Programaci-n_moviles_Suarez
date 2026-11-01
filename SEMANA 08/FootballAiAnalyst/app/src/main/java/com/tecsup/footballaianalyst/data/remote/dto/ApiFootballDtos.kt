package com.tecsup.footballaianalyst.data.remote.dto

import com.google.gson.annotations.SerializedName

data class ApiFootballResponse<T>(
    @SerializedName("get") val getParam: String?,
    @SerializedName("results") val results: Int?,
    @SerializedName("response") val response: List<T>
)

data class TeamSearchWrapperDto(
    @SerializedName("team") val team: TeamDto
)

data class FixtureResponseDto(
    @SerializedName("fixture") val fixture: FixtureDto,
    @SerializedName("league") val league: LeagueDto,
    @SerializedName("teams") val teams: TeamsPairDto,
    @SerializedName("goals") val goals: GoalsDto,
    @SerializedName("score") val score: ScoreDto?
)

data class FixtureDto(
    @SerializedName("id") val id: Int,
    @SerializedName("referee") val referee: String?,
    @SerializedName("date") val date: String,
    @SerializedName("status") val status: FixtureStatusDto,
    @SerializedName("venue") val venue: VenueDto?
)

data class FixtureStatusDto(
    @SerializedName("long") val longStatus: String,
    @SerializedName("short") val shortStatus: String,
    @SerializedName("elapsed") val elapsed: Int?
)

data class VenueDto(
    @SerializedName("name") val name: String?,
    @SerializedName("city") val city: String?
)

data class LeagueDto(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("logo") val logo: String,
    @SerializedName("flag") val flag: String?
)

data class TeamsPairDto(
    @SerializedName("home") val home: TeamDto,
    @SerializedName("away") val away: TeamDto
)

data class TeamDto(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("logo") val logo: String
)

data class GoalsDto(
    @SerializedName("home") val home: Int?,
    @SerializedName("away") val away: Int?
)

data class ScoreDto(
    @SerializedName("halftime") val halftime: GoalsDto?,
    @SerializedName("fulltime") val fulltime: GoalsDto?
)

data class TeamStatsResponseDto(
    @SerializedName("team") val team: TeamDto,
    @SerializedName("statistics") val statistics: List<StatisticItemDto>
)

data class StatisticItemDto(
    @SerializedName("type") val type: String,
    @SerializedName("value") val value: Any?
)
