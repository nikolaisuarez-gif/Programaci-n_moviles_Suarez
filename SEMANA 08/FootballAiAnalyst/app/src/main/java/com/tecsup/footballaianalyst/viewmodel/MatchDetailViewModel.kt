package com.suarez.footballaianalyst.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.suarez.footballaianalyst.data.prefs.ApiKeyStore
import com.suarez.footballaianalyst.data.repository.FootballRepository
import com.suarez.footballaianalyst.model.AiAnalysis
import com.suarez.footballaianalyst.model.Match
import com.suarez.footballaianalyst.model.MatchLineups
import com.suarez.footballaianalyst.model.MatchStatistics
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MatchDetailViewModel(
    private val repository: FootballRepository,
    private val apiKeyStore: ApiKeyStore
) : ViewModel() {

    private val _selectedMatch = MutableStateFlow<Match?>(null)
    val selectedMatch: StateFlow<Match?> = _selectedMatch.asStateFlow()

    private val _statistics = MutableStateFlow<MatchStatistics?>(null)
    val statistics: StateFlow<MatchStatistics?> = _statistics.asStateFlow()

    private val _lineups = MutableStateFlow<MatchLineups?>(null)
    val lineups: StateFlow<MatchLineups?> = _lineups.asStateFlow()

    private val _aiAnalysis = MutableStateFlow<AiAnalysis?>(null)
    val aiAnalysis: StateFlow<AiAnalysis?> = _aiAnalysis.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    fun loadMatchDetails(match: Match, apiKey: String) {
        _selectedMatch.value = match
        _isLoading.value = true

        viewModelScope.launch {
            val stats = repository.getMatchStatistics(apiKey, match.id)
            val lines = repository.getMatchLineups(apiKey, match.id)
            val ai = repository.generateAiAnalysis(match, stats)

            _statistics.value = stats
            _lineups.value = lines
            _aiAnalysis.value = ai
            _isLoading.value = false
        }
    }
}
