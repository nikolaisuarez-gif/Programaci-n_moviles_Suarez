package com.tecsup.footballaianalyst.data.remote

import com.tecsup.footballaianalyst.data.remote.dto.FootballDataMatchesResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface FootballDataApi {

    @GET("v4/matches")
    suspend fun getMatches(
        @Header("X-Auth-Token") token: String,
        @Query("dateFrom") dateFrom: String? = null,
        @Query("dateTo") dateTo: String? = null
    ): FootballDataMatchesResponse

    @GET("v4/teams/{teamId}/matches")
    suspend fun getTeamMatches(
        @Header("X-Auth-Token") token: String,
        @Path("teamId") teamId: Int,
        @Query("status") status: String = "FINISHED",
        @Query("limit") limit: Int = 20
    ): FootballDataMatchesResponse
}
