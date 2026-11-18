package com.suarez.cafecampus.data.local.relation

import androidx.room.Embedded
import androidx.room.Relation
import com.suarez.cafecampus.data.local.entity.DetallePedido
import com.suarez.cafecampus.data.local.entity.Pedido
import com.suarez.cafecampus.data.local.entity.Usuario

data class PedidoConDetalle(
    @Embedded val pedido: Pedido,
    @Relation(
        parentColumn = "id",
        entityColumn = "pedidoId"
    )
    val detalles: List<DetallePedido>,
    @Relation(
        parentColumn = "clientId",
        entityColumn = "id"
    )
    val usuario: Usuario? = null
)
