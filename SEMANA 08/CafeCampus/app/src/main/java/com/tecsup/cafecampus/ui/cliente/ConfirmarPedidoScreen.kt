package com.tecsup.cafecampus.ui.cliente

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import com.tecsup.cafecampus.ui.components.AppTopBar
import com.tecsup.cafecampus.ui.components.CafeButton
import com.tecsup.cafecampus.ui.components.ProductoIcono
import com.tecsup.cafecampus.viewmodel.MenuViewModel
import com.tecsup.cafecampus.viewmodel.PedidosClienteViewModel

@Composable
fun ConfirmarPedidoScreen(
    clienteId: Int,
    menuVm: MenuViewModel,
    pedidosVm: PedidosClienteViewModel,
    onBackClick: () -> Unit,
    onPedidoConfirmado: (Long) -> Unit
) {
    val carrito by menuVm.carrito.collectAsState()
    val horaRecojo by pedidosVm.horaRecojo.collectAsState()
    val pago by pedidosVm.pago.collectAsState()

    val horasDisponibles = listOf("Ahora", "11:30", "12:00", "12:30")
    val metodosPago = listOf("CAJA" to "Pagar en caja", "YAPE" to "Yape / Plin")

    val subtotal = menuVm.totalCarrito
    val servicio = 0.0
    val total = subtotal + servicio

    Scaffold(
        topBar = {
            AppTopBar(
                title = "Confirmar pedido",
                onBackClick = onBackClick
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(20.dp)
        ) {
            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    carrito.forEach { item ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 6.dp)
                        ) {
                            ProductoIcono(icono = item.producto.icono, size = 40.dp)

                            Spacer(modifier = Modifier.padding(horizontal = 6.dp))

                            Column(modifier = Modifier.weight(1f)) {
                                Text(text = item.producto.nombre, fontWeight = FontWeight.SemiBold)
                                Text(text = "x${item.cantidad}", fontSize = 12.sp, color = MaterialTheme.colorScheme.outline)
                            }

                            Text(
                                text = "S/ %.2f".format(item.producto.precio * item.cantidad),
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text(text = "Hora de recojo", fontWeight = FontWeight.Bold, fontSize = 16.sp)
            Row(modifier = Modifier.padding(vertical = 8.dp)) {
                horasDisponibles.forEach { h ->
                    FilterChip(
                        selected = horaRecojo == h,
                        onClick = { pedidosVm.setHoraRecojo(h) },
                        label = { Text(h) },
                        modifier = Modifier.padding(end = 8.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(text = "Forma de pago", fontWeight = FontWeight.Bold, fontSize = 16.sp)
            Row(modifier = Modifier.padding(vertical = 8.dp)) {
                metodosPago.forEach { (clave, label) ->
                    FilterChip(
                        selected = pago == clave,
                        onClick = { pedidosVm.setPago(clave) },
                        label = { Text(label) },
                        modifier = Modifier.padding(end = 8.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) {
                Row(modifier = Modifier.fillMaxWidth()) {
                    Text("Subtotal", color = MaterialTheme.colorScheme.outline)
                    Spacer(modifier = Modifier.weight(1f))
                    Text("S/ %.2f".format(subtotal))
                }
                Spacer(modifier = Modifier.height(4.dp))
                Row(modifier = Modifier.fillMaxWidth()) {
                    Text("Servicio", color = MaterialTheme.colorScheme.outline)
                    Spacer(modifier = Modifier.weight(1f))
                    Text("S/ %.2f".format(servicio))
                }
                HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
                Row(modifier = Modifier.fillMaxWidth()) {
                    Text("Total", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                    Spacer(modifier = Modifier.weight(1f))
                    Text(
                        text = "S/ %.2f".format(total),
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            CafeButton(
                text = "Confirmar pedido",
                onClick = {
                    pedidosVm.crearPedido(clienteId, carrito) { pedidoId ->
                        menuVm.limpiarCarrito()
                        onPedidoConfirmado(pedidoId)
                    }
                }
            )

            Spacer(modifier = Modifier.height(12.dp))

            CafeButton(
                text = "Seguir pidiendo",
                isOutlined = true,
                onClick = onBackClick
            )
        }
    }
}
