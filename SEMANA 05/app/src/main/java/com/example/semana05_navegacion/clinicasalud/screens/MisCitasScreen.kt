package com.example.semana05_navegacion.clinicasalud.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class CitaItem(
    val doctorName: String,
    val fechaHora: String,
    val estado: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MisCitasScreen(onOpenDrawer: () -> Unit) {
    val citas = listOf(
        CitaItem("Dra. Ana Torres", "Viernes 27, 10:30 am", "Confirmada"),
        CitaItem("Dr. Luis Vega", "Miércoles 15, 3:00 pm", "Completada"),
        CitaItem("Dra. Rosa Díaz", "Lunes 10, 9:00 am", "Completada")
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Mis citas", fontSize = 20.sp, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onOpenDrawer) {
                        Icon(
                            imageVector = Icons.Default.Menu,
                            contentDescription = "Menú"
                        )
                    }
                }
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

            items(citas) { cita ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFF3EDF7))
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp)
                    ) {
                        Text(
                            text = cita.doctorName,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = cita.fechaHora,
                            fontSize = 14.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        val isConfirmada = cita.estado == "Confirmada"
                        Surface(
                            shape = RoundedCornerShape(20.dp),
                            color = if (isConfirmada) Color(0xFFE8F5E9) else Color(0xFFEEEEEE)
                        ) {
                            Text(
                                text = cita.estado,
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                                fontSize = 12.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = if (isConfirmada) Color(0xFF2E7D32) else Color(0xFF616161)
                            )
                        }
                    }
                }
            }
        }
    }
}
