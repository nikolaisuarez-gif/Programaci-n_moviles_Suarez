package com.tecsup.cafecampus.ui.admin

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tecsup.cafecampus.model.EstadoPedido
import com.tecsup.cafecampus.ui.components.AppTopBar
import com.tecsup.cafecampus.ui.components.CafeButton
import com.tecsup.cafecampus.ui.components.ProductoIcono
import com.tecsup.cafecampus.ui.theme.AdminPrimary
import com.tecsup.cafecampus.viewmodel.PedidosAdminViewModel

@Composable
fun DetallePedidoAdminScreen(
    pedidoId: Int,
    vm: PedidosAdminViewModel,
    onBackClick: () -> Unit
) {
    val pedidoConDetalle by vm.observarPedido(pedidoId).collectAsState(initial = null)

    if (pedidoConDetalle == null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Cargando detalles...")
        }
        return
    }

    val pedido = pedidoConDetalle!!.pedido
    val detalles = pedidoConDetalle!!.detalles
    val usuario = pedidoConDetalle!!.usuario
    val estadoActual = try { EstadoPedido.valueOf(pedido.estado) } catch (e: Exception) { EstadoPedido.RECIBIDO }

    val estados = listOf(
        EstadoPedido.RECIBIDO to "Recibido",
        EstadoPedido.PREPARANDO to "Preparando",
        EstadoPedido.LISTO to "Listo",
        EstadoPedido.ENTREGADO to "Entregado"
    )

    Scaffold(
        topBar = {
            AppTopBar(
                title = "Detalle del pedido",
                onBackClick = onBackClick
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(20.dp)
        ) {
            Text(
                text = "Pedido #%04d".format(pedido.id),
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Recojo: ${pedido.horaRecojo} · Pago: ${pedido.pago}",
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.outline
            )

            Spacer(modifier = Modifier.height(16.dp))

            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                shape = androidx.compose.foundation.shape.RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Cliente",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.outline
                    )
                    Text(
                        text = "${usuario?.nombre ?: "Juan Pérez"} · ${usuario?.rol ?: "Estudiante"}",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )

                    HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp))

                    detalles.forEach { item ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(vertical = 4.dp)
                        ) {
                            ProductoIcono(icono = "plato", size = 36.dp)
                            Spacer(modifier = Modifier.padding(horizontal = 6.dp))
                            Text(text = item.nombreProducto, modifier = Modifier.weight(1f))
                            Text(text = "x${item.cantidad}", fontSize = 14.sp, color = MaterialTheme.colorScheme.outline)
                            Spacer(modifier = Modifier.padding(horizontal = 8.dp))
                            Text(text = "S/ %.2f".format(item.precioUnitario * item.cantidad), fontWeight = FontWeight.Bold)
                        }
                    }

                    HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp))

                    Row {
                        Text("Total", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                        Spacer(modifier = Modifier.weight(1f))
                        Text(
                            text = "S/ %.2f".format(pedido.total),
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }

            if (pedido.nota.isNotEmpty()) {
                Spacer(modifier = Modifier.height(16.dp))
                Card(
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.4f)),
                    shape = androidx.compose.foundation.shape.RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text("Nota del cliente", fontSize = 12.sp, color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)
                        Text(pedido.nota, fontSize = 14.sp)
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text("Cambiar estado", fontWeight = FontWeight.Bold, fontSize = 16.sp)

            Row(modifier = Modifier.padding(vertical = 8.dp)) {
                estados.forEach { (est, label) ->
                    FilterChip(
                        selected = estadoActual == est,
                        onClick = { vm.cambiarEstado(pedido.id, est) },
                        label = { Text(label) },
                        modifier = Modifier.padding(end = 6.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            val siguienteEstado = when (estadoActual) {
                EstadoPedido.RECIBIDO -> EstadoPedido.PREPARANDO
                EstadoPedido.PREPARANDO -> EstadoPedido.LISTO
                EstadoPedido.LISTO -> EstadoPedido.ENTREGADO
                else -> null
            }

            if (siguienteEstado != null) {
                CafeButton(
                    text = "Pasar a ${siguienteEstado.name.lowercase().replaceFirstChar { it.uppercase() }}",
                    containerColor = AdminPrimary,
                    onClick = { vm.cambiarEstado(pedido.id, siguienteEstado) }
                )
                Spacer(modifier = Modifier.height(8.dp))
            }

            CafeButton(
                text = "Cancelar pedido",
                isOutlined = true,
                onClick = {
                    vm.cambiarEstado(pedido.id, EstadoPedido.CANCELADO)
                    onBackClick()
                }
            )
        }
    }
}
