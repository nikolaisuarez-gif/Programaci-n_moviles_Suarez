package com.example.lab05estados

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    LaboratorioScreen()
                }
            }
        }
    }
}

@Composable
fun ContadorRoto() {
    var contador = 0 // Esto se resetea a 0 en cada recomposición
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text("Contador (Roto): $contador")
        Button(onClick = { contador++ }) { // No causa recomposición
            Text("Incrementar")
        }
    }
}

@Composable
fun ContadorConRemember() {
    var contador by remember { mutableStateOf(0) }
    Column(
        modifier = Modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Contador (Remember): $contador",
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = { contador++ }) {
            Text("Incrementar")
        }
    }
}

@Composable
fun TemperatureDisplay() {
    var temperatura by remember { mutableStateOf(20) }
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text("Temperatura: $temperatura°C")
    }
}

@Composable
fun LaboratorioScreen() {
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Laboratorio Semana 05", style = MaterialTheme.typography.headlineLarge)
        Spacer(modifier = Modifier.height(16.dp))
        // Comentamos contadores para ver mejor la tarea 1
        // ContadorRoto()
        // Divider(modifier = Modifier.padding(vertical = 16.dp))
        // ContadorConRemember()
        TemperatureDisplay()
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewContadores() {
    MaterialTheme {
        LaboratorioScreen()
    }
}
