package com.example.semana05_navegacion.fit.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.semana05_navegacion.fit.modelo.ClaseGym

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetalleClaseScreen(
    clase: ClaseGym,
    onVolver: () -> Unit,
    onReservarCupo: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detalle de clase", fontWeight = androidx.compose.ui.text.font.FontWeight.Bold) },
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
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .background(VerdeFondoChip, RoundedCornerShape(16.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.FitnessCenter,
                    contentDescription = clase.nombre,
                    tint = VerdeFit,
                    modifier = Modifier.size(64.dp)
                )
            }

            Spacer(Modifier.height(20.dp))

            Text(
                text = clase.nombre,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
            )

            Text(
                text = "${clase.horario} • ${clase.sala} • ${clase.duracion}",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(Modifier.height(16.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = clase.descripcion,
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Spacer(Modifier.height(8.dp))
                    Text(
                        text = "${clase.cuposDisponibles} de ${clase.cuposTotales} cupos disponibles",
                        style = MaterialTheme.typography.bodySmall,
                        fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                        color = VerdeFit
                    )
                }
            }

            Spacer(Modifier.weight(1f))

            Button(
                onClick = onReservarCupo,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = VerdeFit)
            ) {
                Text("Reservar cupo", fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
            }
        }
    }
}
