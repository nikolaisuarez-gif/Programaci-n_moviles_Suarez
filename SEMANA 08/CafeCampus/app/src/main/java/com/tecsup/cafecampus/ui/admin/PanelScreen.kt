package com.tecsup.cafecampus.ui.admin

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tecsup.cafecampus.ui.theme.AdminPrimary
import com.tecsup.cafecampus.ui.theme.StatusListo
import com.tecsup.cafecampus.ui.theme.StatusPreparando
import com.tecsup.cafecampus.ui.theme.StatusRecibido
import com.tecsup.cafecampus.viewmodel.PanelAdminViewModel

@Composable
fun PanelScreen(
    vm: PanelAdminViewModel,
    onVerTodosPedidos: () -> Unit,
    onPrepararPedido: (Int) -> Unit
) {
    val ventasHoy by vm.ventasHoy.collectAsState()
    val recibidosCount by vm.recibidosCount.collectAsState()
    val preparandoCount by vm.preparandoCount.collectAsState()
    val listoCount by vm.listoCount.collectAsState()
    val pedidosRecientes by vm.pedidosRecientes.collectAsState()

    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(20.dp)
        ) {
            Text(
                text = "Panel de control",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Cafetería · Hoy",
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.outline
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Stat cards
            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                StatCard(
                    titulo = "Recibidos",
                    cantidad = recibidosCount,
                    color = StatusRecibido,
                    modifier = Modifier.weight(1f)
                )
                StatCard(
                    titulo = "Preparando",
                    cantidad = preparandoCount,
                    color = StatusPreparando,
                    modifier = Modifier.weight(1f)
                )
                StatCard(
                    titulo = "Listos",
                    cantidad = listoCount,
                    color = StatusListo,
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Banner Ventas del día
            Card(
                colors = CardDefaults.cardColors(containerColor = AdminPrimary),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Text(
                        text = "Ventas del día",
                        color = Color.White.copy(alpha = 0.8f),
                        fontSize = 14.sp
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "S/ %.2f".format(ventasHoy),
                        color = Color.White,
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Pedidos por hora",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Chart simulado de barra
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.Bottom,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .background(
                        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                        shape = RoundedCornerShape(12.dp)
                    )
                    .padding(16.dp)
            ) {
                val horas = listOf("9h" to 40.dp, "10h" to 70.dp, "11h" to 100.dp, "12h" to 60.dp, "13h" to 80.dp)
                horas.forEach { (h, alto) ->
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Box(
                            modifier = Modifier
                                .width(20.dp)
                                .height(alto)
                                .clip(RoundedCornerShape(4.dp))
                                .background(AdminPrimary)
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(text = h, fontSize = 10.sp)
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Nuevos pedidos",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )

                Spacer(modifier = Modifier.weight(1f))

                TextButton(onClick = onVerTodosPedidos) {
                    Text("Ver todos")
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            if (pedidosRecientes.isEmpty()) {
                Text(
                    text = "No hay pedidos pendientes por atender",
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.outline
                )
            } else {
                pedidosRecientes.take(3).forEach { item ->
                    Card(
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(12.dp)
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = "Pedido #%04d · ${item.usuario?.nombre ?: "Cliente"}".format(item.pedido.id),
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp
                                )
                                val resumen = item.detalles.joinToString(", ") { "${it.cantidad}x ${it.nombreProducto}" }
                                Text(
                                    text = resumen,
                                    fontSize = 12.sp,
                                    color = MaterialTheme.colorScheme.outline
                                )
                            }

                            Button(
                                onClick = { onPrepararPedido(item.pedido.id) },
                                shape = RoundedCornerShape(8.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = AdminPrimary)
                            ) {
                                Text("Preparar", fontSize = 12.sp)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun StatCard(
    titulo: String,
    cantidad: Int,
    color: Color,
    modifier: Modifier = Modifier
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = color.copy(alpha = 0.12f)),
        shape = RoundedCornerShape(12.dp),
        modifier = modifier
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "$cantidad",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = color
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = titulo,
                fontSize = 11.sp,
                fontWeight = FontWeight.Medium,
                color = color
            )
        }
    }
}
