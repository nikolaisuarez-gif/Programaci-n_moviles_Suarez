package com.tecsup.cafecampus.data.repository

import com.tecsup.cafecampus.data.local.dao.PedidoDao
import com.tecsup.cafecampus.data.local.dao.ProductoDao
import com.tecsup.cafecampus.data.local.dao.UsuarioDao
import com.tecsup.cafecampus.data.local.entity.DetallePedido
import com.tecsup.cafecampus.data.local.entity.Pedido
import com.tecsup.cafecampus.data.local.entity.Producto
import com.tecsup.cafecampus.data.local.entity.Usuario
import com.tecsup.cafecampus.data.local.relation.PedidoConDetalle
import com.tecsup.cafecampus.model.EstadoPedido
import com.tecsup.cafecampus.model.ItemCarrito
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CafeRepositoryRoom(
    private val productoDao: ProductoDao,
    private val usuarioDao: UsuarioDao,
    private val pedidoDao: PedidoDao
) : CafeRepository {

    override suspend fun login(correo: String, clave: String): Usuario? {
        return usuarioDao.login(correo, clave)
    }

    override suspend fun obtenerUsuarioPorId(id: Int): Usuario? {
        return usuarioDao.obtenerPorId(id)
    }

    override fun observarProductos(): Flow<List<Producto>> {
        return productoDao.observarTodos()
    }

    override suspend fun guardarProducto(p: Producto) {
        if (p.id == 0) {
            productoDao.insertar(p)
        } else {
            productoDao.actualizar(p)
        }
    }

    override suspend fun eliminarProducto(p: Producto) {
        productoDao.eliminar(p)
    }

    override suspend fun cambiarDisponibilidad(id: Int, disponible: Boolean) {
        productoDao.cambiarDisponibilidad(id, disponible)
    }

    override suspend fun crearPedido(
        clientId: Int,
        items: List<ItemCarrito>,
        horaRecojo: String,
        pago: String,
        nota: String
    ): Long {
        val total = items.sumOf { it.producto.precio * it.cantidad }
        val pedido = Pedido(
            clientId = clientId,
            estado = EstadoPedido.RECIBIDO.name,
            horaRecojo = horaRecojo,
            pago = pago,
            nota = nota,
            total = total
        )
        val pedidoId = pedidoDao.crearPedido(pedido)
        val detalles = items.map {
            DetallePedido(
                pedidoId = pedidoId.toInt(),
                productoId = it.producto.id,
                nombreProducto = it.producto.nombre,
                cantidad = it.cantidad,
                precioUnitario = it.producto.precio
            )
        }
        pedidoDao.insertarDetalles(detalles)
        return pedidoId
    }

    override fun observarPedidosCliente(clientId: Int): Flow<List<PedidoConDetalle>> {
        return pedidoDao.observarPedidosCliente(clientId)
    }

    override fun observarPedidosPorEstado(estado: EstadoPedido): Flow<List<PedidoConDetalle>> {
        return pedidoDao.observarPedidosPorEstado(estado.name)
    }

    override fun observarPedido(id: Int): Flow<PedidoConDetalle?> {
        return pedidoDao.observarPedidoPorId(id)
    }

    override suspend fun cambiarEstado(pedidoId: Int, estado: EstadoPedido) {
        pedidoDao.cambiarEstado(pedidoId, estado.name)
    }

    override fun observarVentasHoy(inicioDia: Long): Flow<Double> {
        return pedidoDao.observarVentasHoy(inicioDia).map { it ?: 0.0 }
    }

    override fun observarCantidadPorEstado(estado: EstadoPedido): Flow<Int> {
        return pedidoDao.observarCantidadPorEstado(estado.name)
    }

    override fun observarPedidosHoy(inicioDia: Long): Flow<List<Pedido>> {
        return pedidoDao.observarPedidosHoy(inicioDia)
    }
}
