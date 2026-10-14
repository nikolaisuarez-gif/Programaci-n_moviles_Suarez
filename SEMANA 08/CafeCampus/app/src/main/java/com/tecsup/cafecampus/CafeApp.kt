package com.tecsup.cafecampus

import android.app.Application
import com.tecsup.cafecampus.data.local.CafeDatabase
import com.tecsup.cafecampus.data.local.DatosIniciales
import com.tecsup.cafecampus.data.prefs.PreferenciasStore
import com.tecsup.cafecampus.data.repository.CafeRepository
import com.tecsup.cafecampus.data.repository.CafeRepositoryRoom
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CafeApp : Application() {
    lateinit var database: CafeDatabase
        private set
    lateinit var repository: CafeRepository
        private set
    lateinit var preferenciasStore: PreferenciasStore
        private set

    override fun onCreate() {
        super.onCreate()
        database = CafeDatabase.getDatabase(this)
        repository = CafeRepositoryRoom(
            database.productoDao(),
            database.usuarioDao(),
            database.pedidoDao()
        )
        preferenciasStore = PreferenciasStore(this)

        CoroutineScope(Dispatchers.IO).launch {
            DatosIniciales.sembrar(database)
        }
    }
}
