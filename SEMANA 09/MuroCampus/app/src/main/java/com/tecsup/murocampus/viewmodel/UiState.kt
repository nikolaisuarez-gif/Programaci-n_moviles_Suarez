package com.tecsup.murocampus.viewmodel

import java.io.IOException
import com.tecsup.murocampus.data.HttpStatusException
import kotlinx.serialization.SerializationException

sealed interface UiState<out T> {
    data object Cargando : UiState<Nothing>
    data class Exito<T>(val datos: T) : UiState<T>
    data class Error(val mensaje: String) : UiState<Nothing>
}

fun mensajeDe(e: Throwable): String = when (e) {
    is IOException -> "Sin conexión a internet.\nRevisa tu red e inténtalo otra vez."
    is HttpStatusException -> "Error del servidor (${e.statusCode})"
    is SerializationException -> "Respuesta inesperada"
    else -> "Ocurrió un error inesperado"
}
