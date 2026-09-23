package com.example.semana05_navegacion.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.semana05_navegacion.navigation.Screen

@Composable
fun Confirmacion(nav: NavController, nombre:String, fecha:String, hora:String){
    Scaffold{ p ->
        Column(Modifier.padding(p).padding(24.dp), horizontalAlignment=Alignment.CenterHorizontally, verticalArrangement=Arrangement.Center){
            Icon(Icons.Filled.CheckCircle, null, Modifier.size(80.dp), tint=MaterialTheme.colorScheme.primary)
            Spacer(Modifier.height(16.dp))
            Text("¡Cita agendada!", style=MaterialTheme.typography.titleLarge)
            Text(nombre, style=MaterialTheme.typography.bodyMedium)
            Text(fecha + ", " + hora, style=MaterialTheme.typography.bodySmall)
            Spacer(Modifier.height(24.dp))
            Button(onClick={ nav.navigate(Screen.MisCitas.route)} , Modifier.fillMaxWidth()){ Text("Ver mis citas")}
            Spacer(Modifier.height(8.dp))
            OutlinedButton(onClick={ nav.navigate(Screen.Inicio.route){ popUpTo(Screen.Inicio.route){ inclusive=true } }}, Modifier.fillMaxWidth()){ Text("Volver al inicio")}
        }
    }
}
