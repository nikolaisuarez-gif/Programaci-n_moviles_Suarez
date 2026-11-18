package com.suarez.campusgps.data

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Looper
import androidx.core.content.ContextCompat
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.google.android.gms.location.CurrentLocationRequest
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import com.suarez.campusgps.model.Posicion
import com.suarez.campusgps.model.TipoError
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.ExperimentalCoroutinesApi

class ErrorUbicacion(val tipo: TipoError, message: String) : Exception(message)

class UbicacionRepositoryGoogle(private val context: Context) : UbicacionRepository {
    private val fusedClient = LocationServices.getFusedLocationProviderClient(context)

    override fun playServicesDisponible(): Boolean =
        GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(context) == ConnectionResult.SUCCESS

    override suspend fun obtenerPosicion(): Result<Posicion> = runCatching {
        if (!playServicesDisponible()) {
            throw ErrorUbicacion(TipoError.SIN_PLAY_SERVICES, "Google Play Services no está disponible.")
        }
        val fine = ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
        val coarse = ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
        if (!fine && !coarse) throw ErrorUbicacion(TipoError.SIN_PERMISO, "Sin permiso de ubicación no puedo mostrar dónde estás")

        val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val gpsEnabled = runCatching { locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) }.getOrDefault(false)
        val networkEnabled = runCatching { locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER) }.getOrDefault(false)
        if (!gpsEnabled && !networkEnabled) throw ErrorUbicacion(TipoError.GPS_APAGADO, "No se pudo obtener la ubicación. Activa el GPS e intenta de nuevo")

        val request = CurrentLocationRequest.Builder()
            .setPriority(if (fine) Priority.PRIORITY_HIGH_ACCURACY else Priority.PRIORITY_BALANCED_POWER_ACCURACY)
            .setMaxUpdateAgeMillis(10_000)
            .build()
        val location = try {
            fusedClient.getCurrentLocation(request, CancellationTokenSource().token).await()
        } catch (e: SecurityException) {
            throw ErrorUbicacion(TipoError.SIN_PERMISO, "Sin permiso de ubicación no puedo mostrar dónde estás")
        }
            ?: throw ErrorUbicacion(TipoError.OTRO, "No se pudo obtener la ubicación. Intenta de nuevo.")
        Posicion(location.latitude, location.longitude)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @SuppressLint("MissingPermission")
    override fun seguirUbicacion(): Flow<Posicion> = callbackFlow {
        if (!playServicesDisponible()) {
            close(ErrorUbicacion(TipoError.SIN_PLAY_SERVICES, "Google Play Services no está disponible."))
            return@callbackFlow
        }

        val tienePermiso = ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED ||
            ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
        if (!tienePermiso) {
            close(ErrorUbicacion(TipoError.SIN_PERMISO, "Sin permiso de ubicación no puedo seguir tu recorrido."))
            return@callbackFlow
        }

        val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val gpsEnabled = runCatching { locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) }.getOrDefault(false)
        val networkEnabled = runCatching { locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER) }.getOrDefault(false)
        if (!gpsEnabled && !networkEnabled) {
            close(ErrorUbicacion(TipoError.GPS_APAGADO, "No se pudo obtener la ubicación. Activa el GPS e intenta de nuevo."))
            return@callbackFlow
        }

        val request = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 5_000L)
            .setMinUpdateIntervalMillis(2_000L)
            .build()
        val callback = object : LocationCallback() {
            override fun onLocationResult(result: LocationResult) {
                result.lastLocation?.let { location ->
                    trySend(Posicion(location.latitude, location.longitude))
                }
            }
        }

        try {
            fusedClient.requestLocationUpdates(request, callback, Looper.getMainLooper())
                .addOnFailureListener { error -> close(error) }
        } catch (error: SecurityException) {
            close(ErrorUbicacion(TipoError.SIN_PERMISO, "Sin permiso de ubicación no puedo seguir tu recorrido."))
        }

        awaitClose { fusedClient.removeLocationUpdates(callback) }
    }
}
