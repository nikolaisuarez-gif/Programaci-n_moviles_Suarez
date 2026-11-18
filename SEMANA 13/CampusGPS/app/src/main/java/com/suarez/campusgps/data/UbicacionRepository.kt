package com.suarez.campusgps.data

import com.suarez.campusgps.model.Posicion
import kotlinx.coroutines.flow.Flow

interface UbicacionRepository {
    suspend fun obtenerPosicion(): Result<Posicion>
    fun playServicesDisponible(): Boolean
    fun seguirUbicacion(): Flow<Posicion>
}
