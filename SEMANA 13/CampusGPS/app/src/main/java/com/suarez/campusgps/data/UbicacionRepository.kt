package com.suarez.campusgps.data

import com.suarez.campusgps.model.Posicion

interface UbicacionRepository {
    suspend fun obtenerPosicion(): Result<Posicion>
}
