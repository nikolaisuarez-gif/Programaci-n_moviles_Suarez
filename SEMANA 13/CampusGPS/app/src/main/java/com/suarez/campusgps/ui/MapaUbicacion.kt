package com.suarez.campusgps.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.key
import androidx.compose.ui.Modifier
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Circle
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberCameraPositionState
import com.suarez.campusgps.model.LugarConDistancia
import com.suarez.campusgps.model.UbicacionUi

private val CentroLima = LatLng(-12.0464, -77.0428)

@Composable
fun MapaUbicacion(
    estado: UbicacionUi,
    lugares: List<LugarConDistancia>,
    seleccionado: Int?,
    radioMetros: Int?,
    onSeleccionar: (Int?) -> Unit,
    modifier: Modifier = Modifier
) {
    val camara = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(CentroLima, 12f)
    }
    val posicionUsuario = (estado as? UbicacionUi.Lista)?.posicion

    LaunchedEffect(estado) {
        if (estado is UbicacionUi.Lista) {
            val posicion = estado.posicion
            camara.animate(CameraUpdateFactory.newLatLngZoom(LatLng(posicion.latitude, posicion.longitude), 16f))
        }
    }

    LaunchedEffect(seleccionado, lugares) {
        val lugar = lugares.firstOrNull { it.lugar.id == seleccionado }?.lugar
        if (lugar != null) {
            camara.animate(CameraUpdateFactory.newLatLngZoom(LatLng(lugar.latitude, lugar.longitude), 17f))
        }
    }

    GoogleMap(modifier = modifier.fillMaxSize(), cameraPositionState = camara) {
        if (posicionUsuario != null) {
            Marker(
                state = com.google.maps.android.compose.rememberMarkerState(
                    position = LatLng(posicionUsuario.latitude, posicionUsuario.longitude)
                ),
                title = "Estás aquí"
            )
        }

        if (posicionUsuario != null && radioMetros != null) {
            Circle(
                center = LatLng(posicionUsuario.latitude, posicionUsuario.longitude),
                radius = radioMetros.toDouble(),
                fillColor = android.graphics.Color.argb(35, 25, 118, 210),
                strokeColor = android.graphics.Color.rgb(25, 118, 210),
                strokeWidth = 2f
            )
        }

        val masCercanoId = lugares.firstOrNull { it.metros != null }?.lugar?.id
        lugares.forEach { item ->
            val lugar = item.lugar
            key(lugar.id) {
                Marker(
                    state = com.google.maps.android.compose.rememberMarkerState(
                        position = LatLng(lugar.latitude, lugar.longitude)
                    ),
                    title = lugar.nombre,
                    snippet = lugar.categoria,
                    icon = BitmapDescriptorFactory.defaultMarker(
                        if (lugar.id == masCercanoId) BitmapDescriptorFactory.HUE_GREEN
                        else BitmapDescriptorFactory.HUE_AZURE
                    ),
                    onClick = {
                        onSeleccionar(lugar.id)
                        false
                    }
                )
            }
        }
    }
}
