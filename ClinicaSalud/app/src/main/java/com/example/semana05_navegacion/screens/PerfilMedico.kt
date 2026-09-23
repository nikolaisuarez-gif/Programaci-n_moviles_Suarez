package com.example.semana05_navegacion.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.semana05_navegacion.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PerfilMedico(nav: NavController, nombre:String, esp:String){
    Scaffold(topBar={
        TopAppBar(title={Text("Perfil del médico")},
            navigationIcon={ IconButton(onClick={nav.popBackStack()}){ Icon(Icons.AutoMirrored.Filled.ArrowBack, null)}}
        )
    }){ p ->
        Column(Modifier.padding(p).padding(16.dp), horizontalAlignment=Alignment.CenterHorizontally){
            Icon(Icons.Filled.Person, null, Modifier.size(80.dp))
            Text(nombre, style=MaterialTheme.typography.titleLarge)
            Text(esp, style=MaterialTheme.typography.bodyMedium)
            Text("4.9 (128 reseñas)", style=MaterialTheme.typography.labelSmall)
            Spacer(Modifier.height(12.dp))
            Text("Especialista en arritmias e hipertensión, formación en la Clínica Mayo.", style=MaterialTheme.typography.bodySmall)
            Spacer(Modifier.weight(1f))
            Button(onClick={ nav.navigate(Screen.Agendar.createRoute(nombre))}, Modifier.fillMaxWidth()){ Text("Agendar cita")}
        }
    }
}
