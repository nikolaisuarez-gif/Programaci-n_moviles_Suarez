package com.tecsup.footballaianalyst.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tecsup.footballaianalyst.data.prefs.ApiKeyStore
import com.tecsup.footballaianalyst.data.repository.FootballRepository
import com.tecsup.footballaianalyst.model.Team
import com.tecsup.footballaianalyst.model.TeamComparison
import com.tecsup.footballaianalyst.model.TeamStats
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class TeamCompareViewModel(
    private val repository: FootballRepository,
    private val apiKeyStore: ApiKeyStore
) : ViewModel() {

    private val _teamA = MutableStateFlow<Team?>(null)
    val teamA: StateFlow<Team?> = _teamA.asStateFlow()

    private val _teamB = MutableStateFlow<Team?>(null)
    val teamB: StateFlow<Team?> = _teamB.asStateFlow()

    private val _comparison = MutableStateFlow<TeamComparison?>(null)
    val comparison: StateFlow<TeamComparison?> = _comparison.asStateFlow()

    private val _sampleSize = MutableStateFlow(10)
    val sampleSize: StateFlow<Int> = _sampleSize.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    val apiFootballKey: StateFlow<String> = apiKeyStore.apiFootballKey.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = ApiKeyStore.DEFAULT_API_FOOTBALL_KEY
    )

    fun setTeamA(team: Team) {
        _teamA.value = team
        checkAndCompare()
    }

    fun setTeamB(team: Team) {
        _teamB.value = team
        checkAndCompare()
    }

    fun setSampleSize(size: Int) {
        _sampleSize.value = size
        checkAndCompare()
    }

    private fun checkAndCompare() {
        val tA = _teamA.value ?: return
        val tB = _teamB.value ?: return
        _isLoading.value = true

        viewModelScope.launch {
            var statsA: TeamStats? = null
            var statsB: TeamStats? = null

            repository.getTeamStats(apiFootballKey.value, tA, _sampleSize.value, "ALL").collect {
                statsA = it
            }
            repository.getTeamStats(apiFootballKey.value, tB, _sampleSize.value, "ALL").collect {
                statsB = it
            }

            if (statsA != null && statsB != null) {
                val comp = repository.compareTeams(statsA!!, statsB!!)
                _comparison.value = comp
            }
            _isLoading.value = false
        }
    }
}
