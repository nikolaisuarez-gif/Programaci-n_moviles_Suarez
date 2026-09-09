package com.example.lab04registronotas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

import androidx.compose.ui.tooling.preview.Preview

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    RegistroNotasApp()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistroNotasApp() {
    var nota1 by remember { mutableStateOf(0f) }
    var nota2 by remember { mutableStateOf(0f) }
    var nota3 by remember { mutableStateOf(0f) }
    var nota4 by remember { mutableStateOf(0f) }
    
    var redondeo by remember { mutableStateOf(false) }
    var confirmado by remember { mutableStateOf(false) }
    var mostrarResultado by remember { mutableStateOf(false) }

    val gradientBackground = Brush.verticalGradient(
        colors = listOf(Color(0xFFE3F2FD), Color(0xFFBBDEFB))
    )

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Registro de Notas", fontWeight = FontWeight.Bold) }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(gradientBackground)
                .padding(16.dp)
        ) {
            Text("Complete los datos del ciclo", style = MaterialTheme.typography.titleMedium)
            Text("Desliza para asignar cada nota (0 a 20)", style = MaterialTheme.typography.bodySmall, color = Color.Gray)
            Spacer(modifier = Modifier.height(16.dp))

            CursoSlider(label = "Fundamentos de Programación (20%)", value = nota1, onValueChange = { nota1 = it })
            CursoSlider(label = "Programación Orientada a Objetos (25%)", value = nota2, onValueChange = { nota2 = it })
            CursoSlider(label = "Programación en Móviles (30%)", value = nota3, onValueChange = { nota3 = it })
            CursoSlider(label = "Base de Datos (25%)", value = nota4, onValueChange = { nota4 = it })

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Redondear promedio final", modifier = Modifier.weight(1f))
                Switch(checked = redondeo, onCheckedChange = { redondeo = it })
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(checked = confirmado, onCheckedChange = { confirmado = it })
                Text("Confirmo que las notas son correctas", style = MaterialTheme.typography.bodyMedium)
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { mostrarResultado = true },
                modifier = Modifier.fillMaxWidth(),
                enabled = confirmado
            ) {
                Text("CALCULAR PROMEDIO")
            }

            if (!mostrarResultado) {
                Text(
                    text = "Asigna las notas y confirma para calcular",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray,
                    modifier = Modifier.padding(top = 8.dp)
                )
            } else {
                val ponderado = (nota1 * 0.20f) + (nota2 * 0.25f) + (nota3 * 0.30f) + (nota4 * 0.25f)
                val promedioFinal = if (redondeo) kotlin.math.round(ponderado).toInt().toFloat() else ponderado

                val (observacion, color) = when {
                    promedioFinal >= 17 -> "EXCELENTE" to Color(0xFF1B5E20) // Verde oscuro
                    promedioFinal >= 13 -> "APROBADO" to Color(0xFF4CAF50) // Verde
                    promedioFinal >= 10 -> "EN RECUPERACIÓN" to Color(0xFFFFC107) // Ámbar
                    else -> "DESAPROBADO" to Color(0xFFD32F2F) // Rojo
                }

                Spacer(modifier = Modifier.height(16.dp))

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.7f))
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("Detalle por curso:", style = MaterialTheme.typography.labelSmall, color = Color.Gray)
                        Text("• Fundamentos: ${nota1.toInt()} x 20% = ${String.format("%.2f", nota1 * 0.20f)}", style = MaterialTheme.typography.bodySmall)
                        Text("• POO: ${nota2.toInt()} x 25% = ${String.format("%.2f", nota2 * 0.25f)}", style = MaterialTheme.typography.bodySmall)
                        Text("• Móviles: ${nota3.toInt()} x 30% = ${String.format("%.2f", nota3 * 0.30f)}", style = MaterialTheme.typography.bodySmall)
                        Text("• Base de Datos: ${nota4.toInt()} x 25% = ${String.format("%.2f", nota4 * 0.25f)}", style = MaterialTheme.typography.bodySmall)
                        
                        Divider(modifier = Modifier.padding(vertical = 8.dp))

                        Text("Promedio ponderado: ${String.format("%.2f", ponderado)}")
                        Text(
                            "Promedio final: ${if (redondeo) promedioFinal.toInt() else String.format("%.2f", promedioFinal)}",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold
                        )
                        if (redondeo) {
                            Text("(redondeado)", style = MaterialTheme.typography.bodySmall, color = Color.Gray)
                        }
                        
                        Spacer(modifier = Modifier.height(8.dp))
                        
                        Surface(
                            color = color,
                            shape = RoundedCornerShape(16.dp)
                        ) {
                            Text(
                                text = observacion,
                                color = Color.White,
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
                                style = MaterialTheme.typography.labelMedium,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
                
                OutlinedButton(
                    onClick = {
                        nota1 = 0f; nota2 = 0f; nota3 = 0f; nota4 = 0f
                        redondeo = false; confirmado = false; mostrarResultado = false
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("LIMPIAR DATOS")
                }
            }

            Spacer(modifier = Modifier.weight(1f))
            Text(
                "Desarrollado por: Nikolai Suarez",
                modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
                textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                style = MaterialTheme.typography.bodySmall,
                color = Color.DarkGray
            )
        }
    }
}

@Composable
fun CursoSlider(label: String, value: Float, onValueChange: (Float) -> Unit) {
    Column(modifier = Modifier.padding(vertical = 4.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(label, style = MaterialTheme.typography.bodyMedium)
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(50))
                    .background(MaterialTheme.colorScheme.primaryContainer)
                    .padding(horizontal = 12.dp, vertical = 4.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = value.toInt().toString(),
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
        }
        Slider(
            value = value,
            onValueChange = onValueChange,
            valueRange = 0f..20f,
            steps = 19
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun RegistroNotasAppPreview() {
    MaterialTheme {
        RegistroNotasApp()
    }
}
