package com.tecsup.centrotareas.data

import kotlinx.coroutines.flow.Flow

data class AvancePrimos(val porcentaje: Float, val cantidadFinal: Int? = null)

interface TareasRepository {
    suspend fun contarPrimos(hasta: Int): Int
    fun contarPrimosConProgreso(hasta: Int): Flow<AvancePrimos>
    fun progresoTareaLarga(): Flow<Float>
    suspend fun cargarPerfil(): String
    suspend fun cargarPedidos(simularError: Boolean): List<String>
}
