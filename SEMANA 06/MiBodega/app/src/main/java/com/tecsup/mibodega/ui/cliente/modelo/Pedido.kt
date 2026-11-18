package com.tecsup.mibodega.ui.cliente.modelo

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * Representa un pedido verídico realizado por el usuario en la app.
 */
data class Pedido(
    val numero: String,
    val items: List<ItemCarrito>,
    val subtotal: Double,
    val delivery: Double,
    val total: Double,
    val direccion: String,
    val metodoPago: String,
    val fecha: String = SimpleDateFormat("dd 'de' MMMM, yyyy", Locale("es", "PE")).format(Date()),
    val estado: String = "En preparación"
)
