package com.tecsup.cafecampus.ui.cliente

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tecsup.cafecampus.model.EstadoPedido
import com.tecsup.cafecampus.ui.components.AppTopBar
import com.tecsup.cafecampus.ui.components.PedidoCard
import com.tecsup.cafecampus.viewmodel.PedidosClienteViewModel

@Composable
fun MisPedidosScreen(
    clienteId: Int,
    vm: PedidosClienteViewModel,
    onPedidoClick: (Int) -> Unit
) {
    val pedidos by vm.observarPedidosCliente(clienteId).collectAsState(initial = emptyList())
    var tabSeleccionado by remember { mutableIntStateOf(0) }

    val pedidosFiltrados = pedidos.filter {
        val estado = try { EstadoPedido.valueOf(it.pedido.estado) } catch (e: Exception) { EstadoPedido.RECIBIDO }
        if (tabSeleccionado == 0) {
            estado == EstadoPedido.RECIBIDO || estado == EstadoPedido.PREPARANDO || estado == EstadoPedido.LISTO
        } else {
            estado == EstadoPedido.ENTREGADO || estado == EstadoPedido.CANCELADO
        }
    }

    Scaffold(
        topBar = { AppTopBar(title = "Mis pedidos") }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            TabRow(selectedTabIndex = tabSeleccionado) {
                Tab(
                    selected = tabSeleccionado == 0,
                    onClick = { tabSeleccionado = 0 },
                    text = { Text("Activos", fontWeight = FontWeight.Bold) }
                )
                Tab(
                    selected = tabSeleccionado == 1,
                    onClick = { tabSeleccionado = 1 },
                    text = { Text("Historial", fontWeight = FontWeight.Bold) }
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            if (pedidosFiltrados.isEmpty()) {
                Text(
                    text = if (tabSeleccionado == 0) "No tienes pedidos activos" else "No tienes pedidos en el historial",
                    color = MaterialTheme.colorScheme.outline,
                    modifier = Modifier.padding(20.dp)
                )
            } else {
                LazyColumn(
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(12.dp)
                ) {
                    items(pedidosFiltrados, key = { it.pedido.id }) { item ->
                        PedidoCard(
                            pedidoConDetalle = item,
                            onClick = { onPedidoClick(item.pedido.id) }
                        )
                    }
                }
            }
        }
    }
}
