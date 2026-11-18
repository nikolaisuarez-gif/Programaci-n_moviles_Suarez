package com.tecsup.centrotareas.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.tecsup.centrotareas.model.PrimosUi
import com.tecsup.centrotareas.viewmodel.TareasViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaTareas(viewModel: TareasViewModel) {
    val mensaje by viewModel.mensaje.collectAsState()
    val progreso by viewModel.progreso.collectAsState()
    val corriendo by viewModel.corriendo.collectAsState()
    val primos by viewModel.primos.collectAsState()
    var toques by remember { mutableIntStateOf(0) }
    var limitePrimos by remember { androidx.compose.runtime.mutableStateOf("100000") }
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
                    androidx.compose.foundation.layout.Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Button(
                            onClick = viewModel::tareaLarga,
                            enabled = !corriendo,
                            modifier = Modifier.weight(1f)
                        ) { Text("Iniciar") }
                        OutlinedButton(
                            onClick = viewModel::cancelar,
                            enabled = corriendo,
                            modifier = Modifier.weight(1f)
                        ) { Text("Cancelar") }
                    }
                    LinearProgressIndicator(progress = progreso, modifier = Modifier.fillMaxWidth())
                    Text("${(progreso * 100).toInt()} %")
                    Text(mensaje)
                }
            }
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text("Contar números primos", color = MaterialTheme.colorScheme.primary, style = MaterialTheme.typography.titleMedium)
                    OutlinedTextField(
                        value = limitePrimos,
                        onValueChange = { nuevo -> if (nuevo.all(Char::isDigit)) limitePrimos = nuevo },
                        label = { Text("Calcular hasta N") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                    androidx.compose.foundation.layout.Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Button(
                            onClick = { limitePrimos.toIntOrNull()?.let(viewModel::contar) },
                            enabled = primos != PrimosUi.Calculando && (limitePrimos.toIntOrNull() ?: 0) >= 2,
                            modifier = Modifier.weight(1f)
                        ) { Text("Contar") }
                        OutlinedButton(
                            onClick = viewModel::cancelarPrimos,
                            enabled = primos == PrimosUi.Calculando,
                            modifier = Modifier.weight(1f)
                        ) { Text("Cancelar") }
                    }
                    when (val estado = primos) {
                        PrimosUi.Inicial -> Text("Ingresa un número desde 2.")
                        PrimosUi.Calculando -> Text("Calculando primos…")
                        PrimosUi.Cancelado -> Text("Cálculo cancelado.", color = MaterialTheme.colorScheme.error)
                        is PrimosUi.Listo -> Text("${estado.cantidad} primos en ${estado.milisegundos} ms")
                    }
                }
            }
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
