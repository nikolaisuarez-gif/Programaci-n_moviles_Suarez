package com.tecsup.footballaianalyst.data.remote

import com.tecsup.footballaianalyst.data.remote.dto.FootballDataMatchesResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface FootballDataApi {

    @GET("v4/matches")
    suspend fun getMatches(
        @Header("X-Auth-Token") token: String,
        @Query("dateFrom") dateFrom: String? = null,
        @Query("dateTo") dateTo: String? = null
    ): FootballDataMatchesResponse
}
