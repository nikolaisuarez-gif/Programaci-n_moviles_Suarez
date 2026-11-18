package com.suarez.campusgps.data

import kotlinx.coroutines.flow.Flow

interface PreferenciasRepository {
    val ultimoLugarVisitadoId: Flow<Int?>
    suspend fun guardarUltimoLugarVisitado(id: Int)
}
