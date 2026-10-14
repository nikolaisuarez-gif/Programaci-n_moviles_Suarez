package com.tecsup.cafecampus.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.tecsup.cafecampus.data.local.entity.DetallePedido
import com.tecsup.cafecampus.data.local.entity.Pedido
import com.tecsup.cafecampus.data.local.relation.PedidoConDetalle
import kotlinx.coroutines.flow.Flow

@Dao
interface PedidoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun crearPedido(pedido: Pedido): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarDetalles(detalles: List<DetallePedido>)

    @Transaction
    @Query("SELECT * FROM pedidos WHERE clientId = :clientId ORDER BY fecha DESC")
    fun observarPedidosCliente(clientId: Int): Flow<List<PedidoConDetalle>>

    @Transaction
    @Query("SELECT * FROM pedidos WHERE estado = :estado ORDER BY fecha DESC")
    fun observarPedidosPorEstado(estado: String): Flow<List<PedidoConDetalle>>

    @Transaction
    @Query("SELECT * FROM pedidos WHERE id = :id LIMIT 1")
    fun observarPedidoPorId(id: Int): Flow<PedidoConDetalle?>

    @Query("UPDATE pedidos SET estado = :estado WHERE id = :id")
    suspend fun cambiarEstado(id: Int, estado: String)

    @Query("SELECT SUM(total) FROM pedidos WHERE fecha >= :inicioDia AND estado != 'CANCELADO'")
    fun observarVentasHoy(inicioDia: Long): Flow<Double?>

    @Query("SELECT COUNT(*) FROM pedidos WHERE estado = :estado")
    fun observarCantidadPorEstado(estado: String): Flow<Int>
}
