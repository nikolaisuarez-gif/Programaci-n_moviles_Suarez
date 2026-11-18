package com.tecsup.centrotareas.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.tecsup.centrotareas.viewmodel.TareasViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaTareas(viewModel: TareasViewModel) {
    val mensaje by viewModel.mensaje.collectAsState()
    var toques by remember { mutableIntStateOf(0) }
    Scaffold(topBar = { TopAppBar(title = { Text("CentroTareas") }) }) { insets ->
        Column(
            modifier = Modifier.fillMaxSize().padding(insets).verticalScroll(rememberScrollState()).padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text("Tarea con progreso", color = MaterialTheme.colorScheme.primary, style = MaterialTheme.typography.titleMedium)
                    Button(onClick = { toques++ }, modifier = Modifier.fillMaxWidth()) {
                        Text("Tócame: $toques")
                    }
                    Button(onClick = viewModel::tareaLarga, modifier = Modifier.fillMaxWidth()) {
                        Text("Tarea larga")
                    }
                    Text(mensaje)
                }
            }
            TarjetaBase("Contar números primos", "Cálculo pesado")
            TarjetaBase("Dos cargas", "Perfil y pedidos simulados")
        }
    }
}

@Composable
private fun TarjetaBase(titulo: String, detalle: String) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
            Text(titulo, color = MaterialTheme.colorScheme.primary, style = MaterialTheme.typography.titleMedium)
            Text(detalle)
        }
    }
}
