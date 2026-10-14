package com.tecsup.cafecampus.model

import com.tecsup.cafecampus.data.local.entity.Producto

data class ItemCarrito(
    val producto: Producto,
    var cantidad: Int = 1
)
