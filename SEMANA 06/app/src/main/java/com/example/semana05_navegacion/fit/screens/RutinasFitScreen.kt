package com.example.semana05_navegacion.fit.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun RutinasFitScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 12.dp)
    ) {
        Text(
            text = "Rutinas de entrenamiento",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(bottom = 16.dp)
        ) {
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(text = "Día 1: Pecho y Tríceps", fontWeight = FontWeight.Bold)
                        Spacer(Modifier.height(4.dp))
                        Text(text = "• Press de banca: 4 series x 10 reps", style = MaterialTheme.typography.bodySmall)
                        Text(text = "• Flexiones: 3 series x 12 reps", style = MaterialTheme.typography.bodySmall)
                        Text(text = "• Extensión de tríceps: 3 series x 12 reps", style = MaterialTheme.typography.bodySmall)
                    }
                }
            }
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(text = "Día 2: Espalda y Bíceps", fontWeight = FontWeight.Bold)
                        Spacer(Modifier.height(4.dp))
                        Text(text = "• Dominadas: 4 series x 8 reps", style = MaterialTheme.typography.bodySmall)
                        Text(text = "• Remo con barra: 4 series x 10 reps", style = MaterialTheme.typography.bodySmall)
                        Text(text = "• Curl de bíceps: 3 series x 12 reps", style = MaterialTheme.typography.bodySmall)
                    }
                }
            }
        }
    }
}
