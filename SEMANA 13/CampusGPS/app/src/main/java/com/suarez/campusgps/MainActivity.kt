package com.suarez.campusgps

import android.os.Bundle
import android.graphics.Color as AndroidColor
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.suarez.campusgps.data.UbicacionRepositoryGoogle
import com.suarez.campusgps.data.LugaresRepositoryLocal
import com.suarez.campusgps.data.PreferenciasRepositoryDataStore
import com.suarez.campusgps.ui.PantallaMapa
import com.suarez.campusgps.ui.BienvenidaCampusGps
import com.suarez.campusgps.ui.theme.CampusGpsTheme
import com.suarez.campusgps.viewmodel.MapaViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.statusBarColor = AndroidColor.rgb(25, 118, 185)
        window.decorView.systemUiVisibility = 0
        val ubicacionRepository = UbicacionRepositoryGoogle(applicationContext)
        val lugaresRepository = LugaresRepositoryLocal()
        val preferenciasRepository = PreferenciasRepositoryDataStore(applicationContext)
        val factory = MapaViewModel.Factory(ubicacionRepository, lugaresRepository, preferenciasRepository)
        setContent {
            val temaSistemaOscuro = isSystemInDarkTheme()
            var modoOscuro by rememberSaveable { mutableStateOf(temaSistemaOscuro) }
            var mostrarMapa by rememberSaveable { mutableStateOf(false) }
            CampusGpsTheme(darkTheme = modoOscuro) {
                if (mostrarMapa) {
                    val vm: MapaViewModel = viewModel(factory = factory)
                    PantallaMapa(
                        vm = vm,
                        modoOscuro = modoOscuro,
                        onCambiarTema = { modoOscuro = !modoOscuro },
                        onVolver = { mostrarMapa = false }
                    )
                } else {
                    BienvenidaCampusGps(
                        modoOscuro = modoOscuro,
                        onCambiarTema = { modoOscuro = !modoOscuro },
                        onContinuar = { mostrarMapa = true }
                    )
                }
            }
        }
    }
}
