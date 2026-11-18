package com.suarez.campusgps.data

import android.content.Context
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import com.suarez.campusgps.model.Posicion
import kotlinx.coroutines.tasks.await

class UbicacionRepositoryGoogle(context: Context) : UbicacionRepository {
    private val client = LocationServices.getFusedLocationProviderClient(context)

    override suspend fun obtenerPosicion(): Result<Posicion> = runCatching {
        val location = client.getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, CancellationTokenSource().token).await()
            ?: error("No se pudo obtener la ubicación")
        Posicion(location.latitude, location.longitude)
    }
}
