package com.tecsup.footballaianalyst.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tecsup.footballaianalyst.data.prefs.ApiKeyStore
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SettingsViewModel(private val apiKeyStore: ApiKeyStore) : ViewModel() {

    val apiFootballKey: StateFlow<String> = apiKeyStore.apiFootballKey.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = ApiKeyStore.DEFAULT_API_FOOTBALL_KEY
    )

    val footballDataToken: StateFlow<String> = apiKeyStore.footballDataToken.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = ApiKeyStore.DEFAULT_FOOTBALL_DATA_TOKEN
    )

    fun guardarApiFootballKey(key: String) {
        viewModelScope.launch {
            apiKeyStore.guardarApiFootballKey(key)
        }
    }

    fun guardarFootballDataToken(token: String) {
        viewModelScope.launch {
            apiKeyStore.guardarFootballDataToken(token)
        }
    }
}
