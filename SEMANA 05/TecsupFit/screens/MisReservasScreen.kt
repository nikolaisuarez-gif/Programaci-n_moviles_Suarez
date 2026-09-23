package com.example.semana05_navegacion.tecsupfit.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class ReservaItem(
    val className: String,
    val schedule: String,
    val estado: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MisReservasScreen() {
    val reservas = listOf(
        ReservaItem("Cross Training", "Hoy, 6:00 pm", "Confirmada"),
        ReservaItem("Yoga Funcional", "Ayer, 7:00 am", "Completada"),
        ReservaItem("Spinning", "12 Feb, 7:30 pm", "Completada")
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Mis reservas", fontSize = 20.sp, fontWeight = FontWeight.Bold) }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            contentPadding = padding,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Spacer(modifier = Modifier.height(8.dp))
            }

            items(reservas) { reserva ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFF1F8E9))
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp)
                    ) {
                        Text(
                            text = reserva.className,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = reserva.schedule,
                            fontSize = 14.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        val isConfirmada = reserva.estado == "Confirmada"
                        Surface(
                            shape = RoundedCornerShape(20.dp),
                            color = if (isConfirmada) Color(0xFFC8E6C9) else Color(0xFFE0E0E0)
                        ) {
                            Text(
                                text = reserva.estado,
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                                fontSize = 12.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = if (isConfirmada) Color(0xFF1B5E20) else Color(0xFF616161)
                            )
                        }
                    }
                }
            }
        }
    }
}
