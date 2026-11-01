package com.tecsup.footballaianalyst.data.local

import com.tecsup.footballaianalyst.model.Team
import com.tecsup.footballaianalyst.model.TeamStats
import java.util.concurrent.ConcurrentHashMap

data class CacheEntry<T>(
    val data: T,
    val timestamp: Long = System.currentTimeMillis()
)

class TeamCacheManager {
    private val teamSearchCache = ConcurrentHashMap<String, CacheEntry<List<Team>>>()
    private val teamStatsCache = ConcurrentHashMap<String, CacheEntry<TeamStats>>()
    private val ttlMillis = 60 * 60 * 1000L // 1 hora de caché

    fun getCachedSearch(query: String): List<Team>? {
        val entry = teamSearchCache[query.lowercase().trim()] ?: return null
        if (System.currentTimeMillis() - entry.timestamp > ttlMillis) {
            teamSearchCache.remove(query.lowercase().trim())
            return null
        }
        return entry.data
    }

    fun putCachedSearch(query: String, teams: List<Team>) {
        teamSearchCache[query.lowercase().trim()] = CacheEntry(teams)
    }

    fun getCachedTeamStats(key: String): TeamStats? {
        val entry = teamStatsCache[key] ?: return null
        if (System.currentTimeMillis() - entry.timestamp > ttlMillis) {
            teamStatsCache.remove(key)
            return null
        }
        return entry.data
    }

    fun putCachedTeamStats(key: String, stats: TeamStats) {
        teamStatsCache[key] = stats
    }

    fun clearAll() {
        teamSearchCache.clear()
        teamStatsCache.clear()
    }
}
