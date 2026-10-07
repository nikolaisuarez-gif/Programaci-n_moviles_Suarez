package com.example.semana05_navegacion.clinica.screens

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
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Star
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.semana05_navegacion.clinica.modelo.Medico

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PerfilMedicoScreen(
    medico: Medico,
    onVolver: () -> Unit,
    onAgendarCita: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Perfil del médico", fontWeight = FontWeight.Bold) },
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
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(96.dp)
                    .background(MoradoFondoChip, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = medico.nombre,
                    tint = MoradoClinica,
                    modifier = Modifier.size(52.dp)
                )
            }

            Spacer(Modifier.height(16.dp))

            Text(text = medico.nombre, style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
            Text(text = "${medico.especialidad} • ${medico.experiencia}", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)

            Spacer(Modifier.height(8.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.Star, contentDescription = "Estrella", tint = Color(0xFFFFC107), modifier = Modifier.size(20.dp))
                Spacer(Modifier.width(4.dp))
                Text(text = "${medico.calificacion} (${medico.resenas} reseñas)", fontWeight = FontWeight.SemiBold)
            }

            Spacer(Modifier.height(24.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
            ) {
                Text(
                    text = medico.biografia,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(16.dp),
                    textAlign = TextAlign.Start
                )
            }

            Spacer(Modifier.weight(1f))

            Button(
                onClick = onAgendarCita,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MoradoClinica)
            ) {
                Text("Agendar cita", fontWeight = FontWeight.Bold)
            }
        }
    }
}
