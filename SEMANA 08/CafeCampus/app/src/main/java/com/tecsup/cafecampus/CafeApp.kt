package com.suarez.cafecampus

import android.app.Application
import com.suarez.cafecampus.data.local.CafeDatabase
import com.suarez.cafecampus.data.local.DatosIniciales
import com.suarez.cafecampus.data.prefs.PreferenciasStore
import com.suarez.cafecampus.data.repository.CafeRepository
import com.suarez.cafecampus.data.repository.CafeRepositoryRoom
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
