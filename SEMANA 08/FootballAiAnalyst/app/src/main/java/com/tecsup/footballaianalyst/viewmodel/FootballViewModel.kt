package com.tecsup.footballaianalyst.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tecsup.footballaianalyst.data.prefs.ApiKeyStore
import com.tecsup.footballaianalyst.data.repository.FootballRepository
import com.tecsup.footballaianalyst.model.Match
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn

class FootballViewModel(
    private val repository: FootballRepository,
    private val apiKeyStore: ApiKeyStore
) : ViewModel() {

    private val _selectedTab = MutableStateFlow(0) // 0: Live, 1: Hoy, 2: Mañana
    val selectedTab: StateFlow<Int> = _selectedTab.asStateFlow()

    val apiFootballKey: StateFlow<String> = apiKeyStore.apiFootballKey.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = ApiKeyStore.DEFAULT_API_FOOTBALL_KEY
    )

    val liveMatches: StateFlow<List<Match>> = apiFootballKey.flatMapLatest { key ->
        repository.getLiveMatches(key)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    fun setSelectedTab(tabIndex: Int) {
        _selectedTab.value = tabIndex
    }
}
