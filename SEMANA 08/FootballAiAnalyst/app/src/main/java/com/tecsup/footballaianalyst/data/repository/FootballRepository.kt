package com.tecsup.footballaianalyst.data.repository

import com.tecsup.footballaianalyst.model.AiAnalysis
import com.tecsup.footballaianalyst.model.LeagueStandings
import com.tecsup.footballaianalyst.model.Match
import com.tecsup.footballaianalyst.model.MatchLineups
import com.tecsup.footballaianalyst.model.MatchStatistics
import com.tecsup.footballaianalyst.model.Team
import com.tecsup.footballaianalyst.model.TeamComparison
import com.tecsup.footballaianalyst.model.TeamStats
import kotlinx.coroutines.flow.Flow

interface FootballRepository {
    fun searchTeams(apiKey: String, query: String): Flow<List<Team>>
    fun getTeamStats(apiKey: String, team: Team, sampleCount: Int, locationFilter: String): Flow<TeamStats>
    fun compareTeams(teamAStats: TeamStats, teamBStats: TeamStats): TeamComparison
    
    fun getLiveMatches(apiKey: String): Flow<List<Match>>
    fun getMatchesByDate(apiKey: String, dateStr: String): Flow<List<Match>>
    suspend fun getMatchStatistics(apiKey: String, matchId: Int): MatchStatistics
    suspend fun getMatchLineups(apiKey: String, matchId: Int): MatchLineups?
    suspend fun getStandings(apiKey: String, leagueId: Int): LeagueStandings
    fun generateAiAnalysis(match: Match, stats: MatchStatistics?): AiAnalysis
}
