package com.tecsup.footballaianalyst.data.repository

import com.tecsup.footballaianalyst.model.AiAnalysis
import com.tecsup.footballaianalyst.model.LeagueStandings
import com.tecsup.footballaianalyst.model.Match
import com.tecsup.footballaianalyst.model.MatchLineups
import com.tecsup.footballaianalyst.model.MatchStatistics
import kotlinx.coroutines.flow.Flow

interface FootballRepository {
    fun getLiveMatches(apiKey: String): Flow<List<Match>>
    fun getMatchesByDate(apiKey: String, dateStr: String): Flow<List<Match>>
    suspend fun getMatchStatistics(apiKey: String, matchId: Int): MatchStatistics
    suspend fun getMatchLineups(apiKey: String, matchId: Int): MatchLineups?
    suspend fun getStandings(apiKey: String, leagueId: Int): LeagueStandings
    fun generateAiAnalysis(match: Match, stats: MatchStatistics?): AiAnalysis
}
