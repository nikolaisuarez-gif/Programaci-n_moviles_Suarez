package com.example.semana05_navegacion.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.semana05_navegacion.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AgendarCita(nav: NavController, nombre:String){
    var fecha by remember { mutableStateOf("") }
    var hora by remember { mutableStateOf("") }
    val fechas = listOf("Jue 26","Vie 27","Sáb 28")
    val horas = listOf("9:00","10:30","3:00")
    Scaffold(topBar={ TopAppBar(title={Text("Agendar cita")})}){ p ->
        Column(Modifier.padding(p).padding(16.dp)){
            Text("Selecciona fecha", style=MaterialTheme.typography.titleSmall)
            LazyRow(horizontalArrangement=Arrangement.spacedBy(8.dp)){
                items(fechas){ f ->
                    FilterChip(selected= fecha==f, onClick={ fecha=f }, label={Text(f)})
                }
            }
            Spacer(Modifier.height(16.dp))
            Text("Selecciona hora", style=MaterialTheme.typography.titleSmall)
            LazyRow(horizontalArrangement=Arrangement.spacedBy(8.dp)){
                items(horas){ h ->
                    FilterChip(selected= hora==h, onClick={ hora=h }, label={Text(h)})
                }
            }
            Spacer(Modifier.weight(1f))
            Button(
                onClick={ if(fecha!="" && hora!="") nav.navigate(Screen.Confirmacion.createRoute(nombre, fecha, hora))},
                enabled= fecha!="" && hora!="",
                modifier=Modifier.fillMaxWidth()
            ){ Text("Confirmar cita")}
        }
    }
}
