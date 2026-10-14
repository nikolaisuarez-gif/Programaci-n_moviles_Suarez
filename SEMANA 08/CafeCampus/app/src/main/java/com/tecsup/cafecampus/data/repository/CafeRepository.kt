package com.tecsup.cafecampus.data.repository

import com.tecsup.cafecampus.data.local.entity.Producto
import kotlinx.coroutines.flow.Flow

interface CafeRepository {
    fun observarProductos(): Flow<List<Producto>>
}
