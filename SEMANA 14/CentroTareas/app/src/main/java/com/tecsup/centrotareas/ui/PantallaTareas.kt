package com.tecsup.centrotareas.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.tecsup.centrotareas.model.CargaUi
import com.tecsup.centrotareas.model.PrimosUi
import com.tecsup.centrotareas.viewmodel.TareasViewModel

private val VerdeResultado = Color(0xFFE3F3E4)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaTareas(viewModel: TareasViewModel) {
    val mensaje by viewModel.mensaje.collectAsState()
    val progreso by viewModel.progreso.collectAsState()
    val corriendo by viewModel.corriendo.collectAsState()
    val primos by viewModel.primos.collectAsState()
    val progresoPrimos by viewModel.progresoPrimos.collectAsState()
    val carga by viewModel.carga.collectAsState()
    val tiemposCarga by viewModel.tiemposCarga.collectAsState()
    val simularError by viewModel.simularError.collectAsState()
    var toques by remember { mutableIntStateOf(0) }
    var limitePrimos by remember { mutableStateOf("500000") }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            TopAppBar(
                title = { Text("CentroTareas", style = MaterialTheme.typography.titleMedium) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { insets ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(insets)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Tarjeta(titulo = "Tarea con progreso") {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                    Button(onClick = { toques++ }) { Text("Tócame: $toques") }
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Button(
                        onClick = viewModel::tareaLarga,
                        enabled = !corriendo
                    ) { Text("Iniciar") }
                    OutlinedButton(
                        onClick = viewModel::cancelar,
                        enabled = corriendo
                    ) { Text("Cancelar") }
                }
                LinearProgressIndicator(progress = { progreso }, modifier = Modifier.fillMaxWidth())
                Text("${(progreso * 100).toInt()} %", modifier = Modifier.align(Alignment.CenterHorizontally))
                Text(mensaje, modifier = Modifier.align(Alignment.CenterHorizontally))
            }

            Tarjeta(titulo = "Contar números primos") {
                OutlinedTextField(
                    value = limitePrimos,
                    onValueChange = { nuevo ->
                        if (nuevo.all(Char::isDigit)) limitePrimos = nuevo
                    },
                    label = { Text("Hasta el número") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Button(
                        onClick = { limitePrimos.toIntOrNull()?.let(viewModel::contar) },
                        enabled = primos != PrimosUi.Calculando && (limitePrimos.toIntOrNull() ?: 0) >= 2
                    ) { Text("Contar") }
                    OutlinedButton(
                        onClick = viewModel::cancelarPrimos,
                        enabled = primos == PrimosUi.Calculando
                    ) { Text("Cancelar") }
                }
                when (val estado = primos) {
                    PrimosUi.Inicial -> Unit
                    PrimosUi.Calculando -> Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        LinearProgressIndicator(progress = { progresoPrimos }, modifier = Modifier.fillMaxWidth())
                        Text("Contando: ${(progresoPrimos * 100).toInt()} %")
                    }
                    PrimosUi.Cancelado -> Text("Cálculo cancelado.", color = MaterialTheme.colorScheme.error)
                    is PrimosUi.Listo -> ResultadoVerde {
                        Text("Primos hasta ${limitePrimos}: ${estado.cantidad}")
                        Text("Tiempo: ${estado.milisegundos} ms")
                    }
                }
            }

            Tarjeta(titulo = "Dos cargas") {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Switch(checked = simularError, onCheckedChange = viewModel::cambiarSimularError)
                    Text("Simular error")
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    OutlinedButton(
                        onClick = viewModel::cargarUnaPorUna,
                        enabled = carga != CargaUi.Cargando
                    ) { Text("Una por una") }
                    Button(
                        onClick = viewModel::cargarALaVez,
                        enabled = carga != CargaUi.Cargando
                    ) { Text("A la vez") }
                }
                when (val estado = carga) {
                    CargaUi.Inicial -> Unit
                    CargaUi.Cargando -> CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
                    is CargaUi.Exito -> ResultadoVerde {
                        Text("Modo: ${estado.modo}")
                        Text("Perfil: ${estado.perfil}")
                        Text("Pedidos: ${estado.pedidos}")
                        Text("Tiempo: ${estado.milisegundos} ms")
                    }
                    is CargaUi.Error -> Card(
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.errorContainer),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier.padding(12.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text(estado.mensaje, color = MaterialTheme.colorScheme.error)
                            OutlinedButton(onClick = viewModel::cargarALaVez) { Text("Reintentar") }
                        }
                    }
                }
            }

            if (tiemposCarga.isNotEmpty()) {
                Tarjeta(titulo = "Comparación de tiempos") {
                    Row(modifier = Modifier.fillMaxWidth()) {
                        Text("Modo", modifier = Modifier.weight(1f))
                        Text("Tiempo (ms)")
                    }
                    tiemposCarga.forEach { medicion ->
                        Row(modifier = Modifier.fillMaxWidth()) {
                            Text(medicion.modo, modifier = Modifier.weight(1f))
                            Text(medicion.milisegundos.toString())
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun Tarjeta(titulo: String, contenido: @Composable ColumnScope.() -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(10.dp),
        border = BorderStroke(1.dp, Color(0xFFD6E3F0)),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = titulo,
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.titleSmall
            )
            contenido()
        }
    }
}

@Composable
private fun ResultadoVerde(contenido: @Composable ColumnScope.() -> Unit) {
    Card(
        colors = CardDefaults.cardColors(containerColor = VerdeResultado),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) { contenido() }
    }
}
