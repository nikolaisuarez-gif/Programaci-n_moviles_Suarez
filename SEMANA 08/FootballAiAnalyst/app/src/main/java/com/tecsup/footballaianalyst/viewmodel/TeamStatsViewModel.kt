package com.tecsup.footballaianalyst.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tecsup.footballaianalyst.data.prefs.ApiKeyStore
import com.tecsup.footballaianalyst.data.repository.FootballRepository
import com.tecsup.footballaianalyst.model.Team
import com.tecsup.footballaianalyst.model.TeamStats
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@OptIn(FlowPreview::class)
class TeamStatsViewModel(
    private val repository: FootballRepository,
    private val apiKeyStore: ApiKeyStore
) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _selectedTeam = MutableStateFlow<Team?>(null)
    val selectedTeam: StateFlow<Team?> = _selectedTeam.asStateFlow()

    private val _sampleSize = MutableStateFlow(10) // 5, 10, 20
    val sampleSize: StateFlow<Int> = _sampleSize.asStateFlow()

    private val _locationFilter = MutableStateFlow("ALL") // "ALL", "HOME", "AWAY"
    val locationFilter: StateFlow<String> = _locationFilter.asStateFlow()

    private val _teamStats = MutableStateFlow<TeamStats?>(null)
    val teamStats: StateFlow<TeamStats?> = _teamStats.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    val apiFootballKey: StateFlow<String> = apiKeyStore.apiFootballKey.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = ApiKeyStore.DEFAULT_API_FOOTBALL_KEY
    )

    val searchResults: StateFlow<List<Team>> = searchQuery
        .debounce(300)
        .flatMapLatest { query ->
            repository.searchTeams(apiFootballKey.value, query)
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun onSearchQueryChange(newQuery: String) {
        _searchQuery.value = newQuery
    }

    fun selectTeam(team: Team) {
        _selectedTeam.value = team
        loadTeamStats()
    }

    fun setSampleSize(size: Int) {
        _sampleSize.value = size
        loadTeamStats()
    }

    fun setLocationFilter(filter: String) {
        _locationFilter.value = filter
        loadTeamStats()
    }

    private fun loadTeamStats() {
        val team = _selectedTeam.value ?: return
        _isLoading.value = true

        viewModelScope.launch {
            repository.getTeamStats(
                apiKey = apiFootballKey.value,
                team = team,
                sampleCount = _sampleSize.value,
                locationFilter = _locationFilter.value
            ).collect { stats ->
                _teamStats.value = stats
                _isLoading.value = false
            }
        }
    }
}
