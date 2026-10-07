package com.example.semana05_navegacion.clinica.screens

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
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
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
import com.example.semana05_navegacion.clinica.modelo.CitaMedica

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MisCitasScreen(
    citas: List<CitaMedica>,
    onVolver: () -> Unit,
    onCancelarCita: (Int) -> Unit
) {
    var citaACancelar by remember { mutableStateOf<CitaMedica?>(null) }

    if (citaACancelar != null) {
        AlertDialog(
            onDismissRequest = { citaACancelar = null },
            title = { Text("Cancelar Cita", fontWeight = FontWeight.Bold) },
            text = { Text("¿Estás seguro de que deseas cancelar la cita con ${citaACancelar?.medicoNombre}?") },
            confirmButton = {
                TextButton(
                    onClick = {
                        citaACancelar?.let { onCancelarCita(it.id) }
                        citaACancelar = null
                    }
                ) {
                    Text("Sí, cancelar cita", color = Color(0xFFD32F2F), fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(onClick = { citaACancelar = null }) {
                    Text("No, mantener cita")
                }
            }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Mis citas", fontWeight = FontWeight.Bold, color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = onVolver) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MoradoClinica)
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp)
        ) {
            Spacer(Modifier.height(12.dp))

            if (citas.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("No tienes citas agendadas.", color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    contentPadding = PaddingValues(bottom = 16.dp)
                ) {
                    items(citas, key = { it.id }) { cita ->
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
                                        Text(text = cita.medicoNombre, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium)
                                        Text(text = "${cita.fecha}, ${cita.hora}", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                    }

                                    if (cita.estado == "Confirmada") {
                                        IconButton(onClick = { citaACancelar = cita }) {
                                            Icon(Icons.Default.Delete, contentDescription = "Cancelar cita", tint = Color(0xFFD32F2F))
                                        }
                                    }
                                }

                                Spacer(Modifier.height(8.dp))

                                val esConfirmada = cita.estado == "Confirmada"
                                val fondoChip = if (esConfirmada) Color(0xFFE8F5E9) else Color(0xFFE0E0E0)
                                val textoChip = if (esConfirmada) Color(0xFF2E7D32) else Color(0xFF616161)

                                Box(
                                    modifier = Modifier
                                        .background(fondoChip, RoundedCornerShape(12.dp))
                                        .padding(horizontal = 10.dp, vertical = 4.dp)
                                ) {
                                    Text(
                                        text = cita.estado,
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
}
