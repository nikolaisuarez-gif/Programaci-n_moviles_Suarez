package com.suarez.campusgps.model

data class Posicion(val latitude: Double, val longitude: Double)

sealed interface UbicacionUi {
    data object Inicial : UbicacionUi
    data object Buscando : UbicacionUi
    data class Lista(val posicion: Posicion) : UbicacionUi
    data class Error(val mensaje: String) : UbicacionUi
}
