package com.tecsup.footballaianalyst.data.prefs

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "football_api_prefs")

class ApiKeyStore(private val context: Context) {
    companion object {
        val API_FOOTBALL_KEY = stringPreferencesKey("api_football_key")
        val FOOTBALL_DATA_TOKEN = stringPreferencesKey("football_data_token")
        
        // Clave pública/demo para pruebas gratuitas
        const val DEFAULT_API_FOOTBALL_KEY = "demo_free_key_football_ai"
        const val DEFAULT_FOOTBALL_DATA_TOKEN = "demo_free_token_football_ai"
    }

    val apiFootballKey: Flow<String> = context.dataStore.data.map { prefs ->
        prefs[API_FOOTBALL_KEY] ?: DEFAULT_API_FOOTBALL_KEY
    }

    val footballDataToken: Flow<String> = context.dataStore.data.map { prefs ->
        prefs[FOOTBALL_DATA_TOKEN] ?: DEFAULT_FOOTBALL_DATA_TOKEN
    }

    suspend fun guardarApiFootballKey(key: String) {
        context.dataStore.edit { prefs ->
            prefs[API_FOOTBALL_KEY] = key.trim()
        }
    }

    suspend fun guardarFootballDataToken(token: String) {
        context.dataStore.edit { prefs ->
            prefs[FOOTBALL_DATA_TOKEN] = token.trim()
        }
    }
}
