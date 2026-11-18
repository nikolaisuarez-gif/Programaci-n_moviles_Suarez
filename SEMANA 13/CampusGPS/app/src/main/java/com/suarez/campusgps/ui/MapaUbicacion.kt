package com.tecsup.campusgps.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState
import com.tecsup.campusgps.model.UbicacionUi

private val CentroLima = LatLng(-12.0464, -77.0428)

@Composable
fun MapaUbicacion(estado: UbicacionUi, modifier: Modifier = Modifier) {
    val camara = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(CentroLima, 12f)
    }
    LaunchedEffect(estado) {
        if (estado is UbicacionUi.Lista) {
            camara.animate(CameraUpdateFactory.newLatLngZoom(LatLng(estado.posicion.latitude, estado.posicion.longitude), 16f))
        }
    }
    GoogleMap(modifier = modifier.fillMaxSize(), cameraPositionState = camara) {
        (estado as? UbicacionUi.Lista)?.posicion?.let { posicion ->
            Marker(
                state = rememberMarkerState(position = LatLng(posicion.latitude, posicion.longitude)),
                title = "Estás aquí"
            )
        }
    }
}
