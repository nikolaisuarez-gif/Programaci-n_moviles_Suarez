package com.tecsup.footballaianalyst.data.remote

import com.tecsup.footballaianalyst.data.remote.dto.ApiFootballResponse
import com.tecsup.footballaianalyst.data.remote.dto.FixtureResponseDto
import com.tecsup.footballaianalyst.data.remote.dto.TeamStatsResponseDto
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface ApiFootballApi {

    @GET("v3/fixtures")
    suspend fun getLiveFixtures(
        @Header("x-apisports-key") apiKey: String,
        @Query("live") live: String = "all"
    ): ApiFootballResponse<FixtureResponseDto>

    @GET("v3/fixtures")
    suspend fun getFixturesByDate(
        @Header("x-apisports-key") apiKey: String,
        @Query("date") date: String
    ): ApiFootballResponse<FixtureResponseDto>

    @GET("v3/fixtures/statistics")
    suspend fun getFixtureStatistics(
        @Header("x-apisports-key") apiKey: String,
        @Query("fixture") fixtureId: Int
    ): ApiFootballResponse<TeamStatsResponseDto>
}
