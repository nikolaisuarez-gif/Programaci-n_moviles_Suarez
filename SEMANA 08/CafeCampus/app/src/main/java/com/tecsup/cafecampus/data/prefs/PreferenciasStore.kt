package com.tecsup.cafecampus.data.prefs

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "cafe_prefs")

class PreferenciasStore(private val context: Context) {
    companion object {
        val MODO_OSCURO = booleanPreferencesKey("modo_oscuro")
        val NOTIFICACIONES = booleanPreferencesKey("notificaciones")
        val RECIBIR_PEDIDOS = booleanPreferencesKey("recibir_pedidos")
    }

    val modoOscuro: Flow<Boolean> = context.dataStore.data.map { prefs ->
        prefs[MODO_OSCURO] ?: false
    }

    val notificaciones: Flow<Boolean> = context.dataStore.data.map { prefs ->
        prefs[NOTIFICACIONES] ?: true
    }

    val recibirPedidos: Flow<Boolean> = context.dataStore.data.map { prefs ->
        prefs[RECIBIR_PEDIDOS] ?: true
    }

    suspend fun guardarModoOscuro(valor: Boolean) {
        context.dataStore.edit { prefs ->
            prefs[MODO_OSCURO] = valor
        }
    }

    suspend fun guardarNotificaciones(valor: Boolean) {
        context.dataStore.edit { prefs ->
            prefs[NOTIFICACIONES] = valor
        }
    }

    suspend fun guardarRecibirPedidos(valor: Boolean) {
        context.dataStore.edit { prefs ->
            prefs[RECIBIR_PEDIDOS] = valor
        }
    }
}
