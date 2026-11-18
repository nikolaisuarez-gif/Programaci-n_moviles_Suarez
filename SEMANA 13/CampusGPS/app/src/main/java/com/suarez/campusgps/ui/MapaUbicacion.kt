package com.suarez.campusgps.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.key
import androidx.compose.ui.Modifier
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState
import com.suarez.campusgps.model.UbicacionUi
import com.suarez.campusgps.model.LugarConDistancia
import com.google.android.gms.maps.model.BitmapDescriptorFactory

private val CentroLima = LatLng(-12.0464, -77.0428)

@Composable
fun MapaUbicacion(
    estado: UbicacionUi,
    lugares: List<LugarConDistancia>,
    seleccionado: Int?,
    onSeleccionar: (Int?) -> Unit,
    modifier: Modifier = Modifier
) {
    val camara = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(CentroLima, 12f)
    }
    LaunchedEffect(estado) {
        if (estado is UbicacionUi.Lista) {
            camara.animate(CameraUpdateFactory.newLatLngZoom(LatLng(estado.posicion.latitude, estado.posicion.longitude), 16f))
        }
    }
    LaunchedEffect(seleccionado) {
        val lugar = lugares.firstOrNull { it.lugar.id == seleccionado }?.lugar
        if (lugar != null) camara.animate(CameraUpdateFactory.newLatLngZoom(LatLng(lugar.latitude, lugar.longitude), 17f))
    }
    GoogleMap(modifier = modifier.fillMaxSize(), cameraPositionState = camara) {
        (estado as? UbicacionUi.Lista)?.posicion?.let { posicion ->
            Marker(
                state = rememberMarkerState(position = LatLng(posicion.latitude, posicion.longitude)),
                title = "Estás aquí"
            )
        }
        val masCercanoId = lugares.firstOrNull { it.metros != null }?.lugar?.id
        lugares.forEach { item ->
            val lugar = item.lugar
            key(lugar.id) {
                Marker(
                    state = rememberMarkerState(position = LatLng(lugar.latitude, lugar.longitude)),
                    title = lugar.nombre,
                    snippet = lugar.categoria,
                    icon = BitmapDescriptorFactory.defaultMarker(
                        if (lugar.id == masCercanoId) BitmapDescriptorFactory.HUE_GREEN else BitmapDescriptorFactory.HUE_AZURE
                    ),
                    onClick = { onSeleccionar(lugar.id); false }
                )
            }
        }
    }
}
