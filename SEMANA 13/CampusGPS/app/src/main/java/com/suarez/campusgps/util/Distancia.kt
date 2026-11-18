package com.suarez.campusgps.util

import android.location.Location
import com.suarez.campusgps.model.Lugar
import com.suarez.campusgps.model.Posicion

fun distanciaMetros(desde: Posicion, hasta: Lugar): Float {
    val resultado = FloatArray(1)
    Location.distanceBetween(desde.latitude, desde.longitude, hasta.latitude, hasta.longitude, resultado)
    return resultado[0]
}
