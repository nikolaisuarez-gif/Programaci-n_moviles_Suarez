package com.example.semana05_navegacion.fit.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.semana05_navegacion.fit.modelo.ReservaGym

@Composable
fun MisReservasFitScreen(
    reservas: List<ReservaGym>,
    onCancelarReserva: (Int) -> Unit
) {
    var reservaACancelar by remember { mutableStateOf<ReservaGym?>(null) }

    if (reservaACancelar != null) {
        AlertDialog(
            onDismissRequest = { reservaACancelar = null },
            title = { Text("Cancelar Reserva", fontWeight = FontWeight.Bold) },
            text = { Text("¿Estás seguro de que deseas cancelar la reserva de ${reservaACancelar?.claseNombre}?") },
            confirmButton = {
                TextButton(
                    onClick = {
                        reservaACancelar?.let { onCancelarReserva(it.id) }
                        reservaACancelar = null
                    }
                ) {
                    Text("Sí, cancelar reserva", color = Color(0xFFD32F2F), fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(onClick = { reservaACancelar = null }) {
                    Text("No, mantener reserva")
                }
            }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 12.dp)
    ) {
        Text(
            text = "Mis reservas",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        if (reservas.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Aún no tienes reservas activas.", color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(bottom = 16.dp)
            ) {
                items(reservas, key = { it.id }) { reserva ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(text = reserva.claseNombre, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium)
                                    Text(text = "${reserva.horario} • ${reserva.sala}", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                }

                                if (reserva.estado == "Confirmada") {
                                    IconButton(onClick = { reservaACancelar = reserva }) {
                                        Icon(Icons.Default.Delete, contentDescription = "Cancelar reserva", tint = Color(0xFFD32F2F))
                                    }
                                }
                            }

                            Spacer(Modifier.height(8.dp))

                            val esConfirmada = reserva.estado == "Confirmada"
                            val fondoChip = if (esConfirmada) Color(0xFFE0F2F1) else Color(0xFFE0E0E0)
                            val textoChip = if (esConfirmada) VerdeFit else Color(0xFF616161)

                            Box(
                                modifier = Modifier
                                    .background(fondoChip, RoundedCornerShape(12.dp))
                                    .padding(horizontal = 10.dp, vertical = 4.dp)
                            ) {
                                Text(
                                    text = reserva.estado,
                                    style = MaterialTheme.typography.bodySmall,
                                    fontWeight = FontWeight.Bold,
                                    color = textoChip
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
