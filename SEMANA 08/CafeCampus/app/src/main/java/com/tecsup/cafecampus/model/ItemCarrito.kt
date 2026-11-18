package com.suarez.cafecampus.model

import com.suarez.cafecampus.data.local.entity.Producto

data class ItemCarrito(
    val producto: Producto,
    var cantidad: Int = 1
)
