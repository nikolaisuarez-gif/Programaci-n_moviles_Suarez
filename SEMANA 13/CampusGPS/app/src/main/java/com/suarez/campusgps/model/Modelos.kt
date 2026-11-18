package com.suarez.campusgps.model

data class Posicion(val latitude: Double, val longitude: Double)

data class Lugar(
    val id: Int,
    val nombre: String,
    val categoria: String,
    val latitude: Double,
    val longitude: Double
)

data class LugarConDistancia(val lugar: Lugar, val metros: Float?)

sealed interface UbicacionUi {
    data object Inicial : UbicacionUi
    data object Buscando : UbicacionUi
    data class Lista(val posicion: Posicion) : UbicacionUi
    data class Error(val mensaje: String) : UbicacionUi
}
