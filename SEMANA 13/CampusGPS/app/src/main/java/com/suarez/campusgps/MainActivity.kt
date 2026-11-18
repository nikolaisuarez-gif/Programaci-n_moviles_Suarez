package com.suarez.campusgps

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.lifecycle.viewmodel.compose.viewModel
import com.suarez.campusgps.data.UbicacionRepositoryGoogle
import com.suarez.campusgps.ui.PantallaMapa
import com.suarez.campusgps.ui.theme.CampusGpsTheme
import com.suarez.campusgps.viewmodel.MapaViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val factory = MapaViewModel.Factory(UbicacionRepositoryGoogle(applicationContext))
        setContent {
            CampusGpsTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    val vm: MapaViewModel = viewModel(factory = factory)
                    PantallaMapa(vm)
                }
            }
        }
    }
}
