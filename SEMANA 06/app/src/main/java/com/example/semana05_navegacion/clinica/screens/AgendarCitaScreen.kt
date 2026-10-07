package com.example.semana05_navegacion.clinica.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AgendarCitaScreen(
    medicoNombre: String,
    onVolver: () -> Unit,
    onConfirmarCita: (fecha: String, hora: String) -> Unit
) {
    val fechas = listOf("Jue 26", "Vie 27", "Sáb 28")
    val horas = listOf("9:00 am", "10:30 am", "3:00 pm")

    var fechaSeleccionada by remember { mutableStateOf<String?>(null) }
    var horaSeleccionada by remember { mutableStateOf<String?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Agendar cita", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onVolver) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(24.dp)
        ) {
            Text(text = "Médico: $medicoNombre", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)

            Spacer(Modifier.height(24.dp))

            Text(text = "Selecciona fecha", style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.SemiBold)
            Spacer(Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                fechas.forEach { f ->
                    val seleccionado = (f == fechaSeleccionada)
                    FilterChip(
                        selected = seleccionado,
                        onClick = { fechaSeleccionada = f },
                        label = {
                            Text(
                                text = f,
                                fontWeight = if (seleccionado) FontWeight.Bold else FontWeight.Normal
                            )
                        },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = MoradoClinica,
                            selectedLabelColor = Color.White,
                            containerColor = MoradoFondoChip
                        ),
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            Spacer(Modifier.height(24.dp))

            Text(text = "Selecciona hora", style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.SemiBold)
            Spacer(Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                horas.forEach { h ->
                    val seleccionado = (h == horaSeleccionada)
                    FilterChip(
                        selected = seleccionado,
                        onClick = { horaSeleccionada = h },
                        label = {
                            Text(
                                text = h,
                                fontWeight = if (seleccionado) FontWeight.Bold else FontWeight.Normal
                            )
                        },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = MoradoClinica,
                            selectedLabelColor = Color.White,
                            containerColor = MoradoFondoChip
                        ),
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            Spacer(Modifier.weight(1f))

            val puedeContinuar = fechaSeleccionada != null && horaSeleccionada != null

            Button(
                onClick = {
                    if (puedeContinuar) {
                        onConfirmarCita(fechaSeleccionada!!, horaSeleccionada!!)
                    }
                },
                enabled = puedeContinuar,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MoradoClinica)
            ) {
                Text("Confirmar cita", fontWeight = FontWeight.Bold)
            }
        }
    }
}
