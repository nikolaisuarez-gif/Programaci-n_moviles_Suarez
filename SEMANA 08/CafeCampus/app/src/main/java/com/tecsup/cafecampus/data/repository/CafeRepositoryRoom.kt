package com.tecsup.cafecampus.data.repository

import com.tecsup.cafecampus.data.local.dao.ProductoDao
import com.tecsup.cafecampus.data.local.entity.Producto
import kotlinx.coroutines.flow.Flow

class CafeRepositoryRoom(
    private val productoDao: ProductoDao
) : CafeRepository {
    override fun observarProductos(): Flow<List<Producto>> {
        return productoDao.observarTodos()
    }
}
