package com.suarez.campusgps.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

private val Context.campusGpsDataStore: DataStore<Preferences> by preferencesDataStore(name = "campusgps_preferences")

class PreferenciasRepositoryDataStore(context: Context) : PreferenciasRepository {
    private val dataStore = context.applicationContext.campusGpsDataStore
    private val ultimoLugarKey = intPreferencesKey("ultimo_lugar_visitado_id")

    override val ultimoLugarVisitadoId: Flow<Int?> = dataStore.data
        .catch { error ->
            if (error is IOException) emit(emptyPreferences()) else throw error
        }
        .map { preferencias -> preferencias[ultimoLugarKey] }

    override suspend fun guardarUltimoLugarVisitado(id: Int) {
        dataStore.edit { preferencias -> preferencias[ultimoLugarKey] = id }
    }
}
