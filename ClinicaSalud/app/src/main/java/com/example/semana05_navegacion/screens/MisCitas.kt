package com.example.semana05_navegacion.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

data class Cita(val medico:String, val fecha:String, val estado:String)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MisCitas(nav: NavController){
    val citas = listOf(
        Cita("Dra. Ana Torres","Viernes 27, 10:30 am","Confirmada"),
        Cita("Dr. Luis Vega","Miércoles 15, 3:00 pm","Completada")
    )
    Scaffold(topBar={ TopAppBar(title={Text("Mis citas")}) }){ p ->
        LazyColumn(Modifier.padding(p).padding(16.dp), verticalArrangement=Arrangement.spacedBy(10.dp)){
            items(citas){ c ->
                Card(Modifier.fillMaxWidth(), colors=CardDefaults.cardColors(containerColor= if(c.estado=="Confirmada") Color(0xFFE8F5E9) else Color(0xFFF5F5F5))){
                    Column(Modifier.padding(16.dp)){
                        Text(c.medico, style=MaterialTheme.typography.titleSmall)
                        Text(c.fecha, style=MaterialTheme.typography.bodySmall)
                        Spacer(Modifier.height(6.dp))
                        Badge(containerColor= if(c.estado=="Confirmada") Color(0xFF4CAF50) else Color.Gray){ Text(c.estado, color=Color.White)}
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistorialMedico(nav: NavController){
    Scaffold(topBar={ TopAppBar(title={Text("Historial médico")}) }){ p ->
        Column(Modifier.padding(p).padding(16.dp)){ Text("No hay historial aún", style=MaterialTheme.typography.bodyMedium)}
    }
}
