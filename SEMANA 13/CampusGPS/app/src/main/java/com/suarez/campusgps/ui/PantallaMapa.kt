package com.tecsup.campusgps.ui

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tecsup.campusgps.model.UbicacionUi
import com.tecsup.campusgps.viewmodel.MapaViewModel

@Composable
fun PantallaMapa(vm: MapaViewModel) {
    val context = LocalContext.current
    val estado by vm.estado.collectAsStateWithLifecycle()
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { result ->
        if (result.values.any { it }) vm.buscar() else vm.permisoDenegado()
    }
    fun alPulsar() {
        val fine = ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
        val coarse = ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
        if (fine || coarse) vm.buscar()
        else launcher.launch(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION))
    }
    Column(Modifier.fillMaxSize().padding(20.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
        Text("CampusGPS")
        Button(onClick = ::alPulsar, enabled = estado !is UbicacionUi.Buscando) { Text("Obtener mi ubicación") }
        (estado as? UbicacionUi.Lista)?.let { Text("Latitud: ${it.posicion.latitude}\nLongitud: ${it.posicion.longitude}") }
        (estado as? UbicacionUi.Error)?.let { Text(it.mensaje) }
    }
}
