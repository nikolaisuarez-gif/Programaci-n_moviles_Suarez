package com.tecsup.cafecampus.ui.admin

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
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
import com.tecsup.cafecampus.ui.components.EstadoChip
import com.tecsup.cafecampus.ui.theme.AdminPrimary
import com.tecsup.cafecampus.ui.theme.StatusCancelado
import com.tecsup.cafecampus.viewmodel.PedidosAdminViewModel

@Composable
fun PedidosAdminScreen(
    vm: PedidosAdminViewModel,
    onPedidoClick: (Int) -> Unit
) {
    val estadoFiltro by vm.estadoFiltro.collectAsState()
    val pedidos by vm.pedidosPorEstado.collectAsState()

    val tabs = listOf(
        EstadoPedido.RECIBIDO to "Recibido",
        EstadoPedido.PREPARANDO to "Preparando",
        EstadoPedido.LISTO to "Listo"
    )

    val indiceTab = tabs.indexOfFirst { it.first == estadoFiltro }.coerceAtLeast(0)

    Scaffold(
        topBar = { AppTopBar(title = "Pedidos") }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            TabRow(selectedTabIndex = indiceTab) {
                tabs.forEach { (estado, label) ->
                    Tab(
                        selected = estadoFiltro == estado,
                        onClick = { vm.seleccionarEstado(estado) },
                        text = { Text(label, fontWeight = FontWeight.Bold) }
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            if (pedidos.isEmpty()) {
                Text(
                    text = "No hay pedidos en estado ${estadoFiltro.name}",
                    color = MaterialTheme.colorScheme.outline,
                    modifier = Modifier.padding(20.dp)
                )
            } else {
                LazyColumn(
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(pedidos, key = { it.pedido.id }) { item ->
                        Card(
                            shape = RoundedCornerShape(12.dp),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text(
                                        text = "#%04d · ${item.usuario?.nombre ?: "Cliente"}".format(item.pedido.id),
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 16.sp
                                    )
                                    Spacer(modifier = Modifier.weight(1f))
                                    EstadoChip(estadoStr = item.pedido.estado)
                                }

                                Spacer(modifier = Modifier.height(8.dp))

                                val resumen = item.detalles.joinToString(", ") { "${it.cantidad}x ${it.nombreProducto}" }
                                Text(
                                    text = resumen,
                                    fontSize = 14.sp,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )

                                Spacer(modifier = Modifier.height(8.dp))

                                Text(
                                    text = "Recojo: ${item.pedido.horaRecojo} · Pago: ${item.pedido.pago}",
                                    fontSize = 12.sp,
                                    color = MaterialTheme.colorScheme.outline
                                )

                                Spacer(modifier = Modifier.height(12.dp))

                                Row(
                                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    val siguienteEstado = when (estadoFiltro) {
                                        EstadoPedido.RECIBIDO -> EstadoPedido.PREPARANDO
                                        EstadoPedido.PREPARANDO -> EstadoPedido.LISTO
                                        EstadoPedido.LISTO -> EstadoPedido.ENTREGADO
                                        else -> null
                                    }

                                    if (siguienteEstado != null) {
                                        Button(
                                            onClick = { vm.cambiarEstado(item.pedido.id, siguienteEstado) },
                                            shape = RoundedCornerShape(8.dp),
                                            colors = ButtonDefaults.buttonColors(containerColor = AdminPrimary),
                                            modifier = Modifier.weight(1f)
                                        ) {
                                            Text("Pasar a ${siguienteEstado.name.lowercase().replaceFirstChar { it.uppercase() }}", fontSize = 12.sp)
                                        }
                                    }

                                    OutlinedButton(
                                        onClick = { vm.cambiarEstado(item.pedido.id, EstadoPedido.CANCELADO) },
                                        shape = RoundedCornerShape(8.dp),
                                        modifier = Modifier.weight(0.6f)
                                    ) {
                                        Text("Cancelar", color = StatusCancelado, fontSize = 12.sp)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
