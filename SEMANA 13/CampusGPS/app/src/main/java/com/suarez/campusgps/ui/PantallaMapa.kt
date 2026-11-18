package com.suarez.campusgps.ui

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.suarez.campusgps.model.TipoError
import com.suarez.campusgps.model.UbicacionUi
import com.suarez.campusgps.util.formatearDistancia
import com.suarez.campusgps.viewmodel.MapaViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaMapa(vm: MapaViewModel) {
    val context = LocalContext.current
    val estado by vm.estado.collectAsStateWithLifecycle()
    val lugares by vm.lugares.collectAsStateWithLifecycle()
    val seleccionado by vm.seleccionado.collectAsStateWithLifecycle()
    val masCercano by vm.masCercano.collectAsStateWithLifecycle()
    val categoria by vm.categoria.collectAsStateWithLifecycle()

    val permisoLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { resultado ->
        if (resultado.values.any { it }) vm.buscar() else vm.permisoDenegado()
    }

    fun alPulsar() {
        val fine = ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
        val coarse = ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
        if (fine || coarse) vm.buscar()
        else permisoLauncher.launch(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION))
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("CampusGPS", fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(padding).padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            MapaUbicacion(
                estado = estado,
                lugares = lugares,
                seleccionado = seleccionado,
                onSeleccionar = vm::seleccionar,
                modifier = Modifier.weight(1f).fillMaxWidth()
            )

            Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                vm.categorias.forEach { opcion ->
                    val valor = opcion.takeUnless { it == "Todas" }
                    FilterChip(
                        selected = categoria == valor,
                        onClick = { vm.filtrarCategoria(valor) },
                        label = { Text(opcion) }
                    )
                }
            }

            Spacer(Modifier.height(8.dp))
            Button(onClick = ::alPulsar, enabled = estado !is UbicacionUi.Buscando) {
                if (estado is UbicacionUi.Buscando) {
                    CircularProgressIndicator(Modifier.size(18.dp), strokeWidth = 2.dp, color = MaterialTheme.colorScheme.onPrimary)
                    Spacer(Modifier.size(8.dp))
                }
                Text("Obtener mi ubicación")
            }

            (estado as? UbicacionUi.Lista)?.let { posicion ->
                Text(
                    "Latitud: ${"%.4f".format(posicion.posicion.latitude)}    Longitud: ${"%.4f".format(posicion.posicion.longitude)}",
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(top = 4.dp, bottom = 6.dp)
                )
            }

            (estado as? UbicacionUi.Error)?.let { error ->
                MensajeError(error = error, onBuscar = vm::buscar)
            }

            OutlinedButton(
                onClick = { masCercano?.let { vm.seleccionar(it.id) } },
                enabled = masCercano != null,
                modifier = Modifier.padding(top = 2.dp, bottom = 2.dp)
            ) { Text("Ir al más cercano") }

            masCercano?.let { lugar ->
                val metros = lugares.firstOrNull { it.lugar.id == lugar.id }?.metros
                Text(
                    "Más cercano: ${lugar.nombre} (${metros?.let(::formatearDistancia) ?: "Sin calcular"})",
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
            }

            ListaLugares(
                lugares = lugares,
                seleccionado = seleccionado,
                onClick = vm::seleccionar
            )
        }
    }
}
