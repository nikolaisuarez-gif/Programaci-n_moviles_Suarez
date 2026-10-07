package com.example.semana05_navegacion

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.LocalHospital
import androidx.compose.material3.Icon
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.example.semana05_navegacion.clinica.ClinicaApp
import com.example.semana05_navegacion.clinica.screens.MoradoClinica
import com.example.semana05_navegacion.fit.FitApp
import com.example.semana05_navegacion.fit.screens.VerdeFit

@Composable
fun MainSelectorApp() {
    var opcionSeleccionada by remember { mutableStateOf(0) }

    Column(modifier = Modifier.fillMaxSize()) {
        TabRow(
            selectedTabIndex = opcionSeleccionada,
            modifier = Modifier.fillMaxWidth()
        ) {
            Tab(
                selected = (opcionSeleccionada == 0),
                onClick = { opcionSeleccionada = 0 },
                text = { Text("1. Clínica Salud+", fontWeight = FontWeight.Bold) },
                icon = { Icon(Icons.Default.LocalHospital, contentDescription = "Clínica") },
                selectedContentColor = MoradoClinica
            )
            Tab(
                selected = (opcionSeleccionada == 1),
                onClick = { opcionSeleccionada = 1 },
                text = { Text("2. TECSUP Fit", fontWeight = FontWeight.Bold) },
                icon = { Icon(Icons.Default.FitnessCenter, contentDescription = "Gimnasio") },
                selectedContentColor = VerdeFit
            )
        }

        when (opcionSeleccionada) {
            0 -> ClinicaApp()
            1 -> FitApp()
        }
    }
}
