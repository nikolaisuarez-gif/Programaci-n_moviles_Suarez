package com.example.semana05_navegacion.fit.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.SportsGymnastics
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.semana05_navegacion.fit.modelo.ClaseGym
import com.example.semana05_navegacion.fit.modelo.ReservaGym
import com.example.semana05_navegacion.fit.modelo.listaClasesFitFake
import com.example.semana05_navegacion.fit.modelo.listaFiltrosFit

val VerdeFit = Color(0xFF00695C)
val VerdeFondoChip = Color(0xFFE0F2F1)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InicioFitScreen(
    usuarioNombre: String = "Nikolai Suarez",
    clases: List<ClaseGym> = listaClasesFitFake,
    reservas: List<ReservaGym> = emptyList(),
    onClaseClick: (Int) -> Unit,
    onCancelarReserva: (Int) -> Unit
) {
    var pestañaSeleccionada by remember { mutableStateOf(0) }
    var filtroSeleccionado by remember { mutableStateOf("Hoy") }

    val clasesFiltradas = clases.filter {
        it.filtro == filtroSeleccionado || filtroSeleccionado == "Esta semana"
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text("TECSUP Fit", fontWeight = FontWeight.Bold, color = Color.White)
                        Text("Hola, Nikolai", style = MaterialTheme.typography.bodySmall, color = Color.White.copy(alpha = 0.8f))
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = VerdeFit)
            )
        },
        bottomBar = {
            BarraInferiorFit(
                seleccionado = pestañaSeleccionada,
                onSeleccionar = { pestañaSeleccionada = it }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp)
        ) {
            when (pestañaSeleccionada) {
                0 -> {
                    // Pestaña INICIO
                    Spacer(Modifier.height(12.dp))

                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        contentPadding = PaddingValues(vertical = 4.dp)
                    ) {
                        items(listaFiltrosFit) { f ->
                            val seleccionado = (f == filtroSeleccionado)
                            FilterChip(
                                selected = seleccionado,
                                onClick = { filtroSeleccionado = f },
                                label = { Text(f) },
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = VerdeFit,
                                    selectedLabelColor = Color.White,
                                    containerColor = VerdeFondoChip
                                )
                            )
                        }
                    }

                    Spacer(Modifier.height(12.dp))

                    Text(
                        text = "Clases disponibles",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        contentPadding = PaddingValues(bottom = 16.dp)
                    ) {
                        items(clasesFiltradas) { clase ->
                            Card(
                                onClick = { onClaseClick(clase.id) },
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(14.dp),
                                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(14.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .size(48.dp)
                                            .background(VerdeFondoChip, CircleShape),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.FitnessCenter,
                                            contentDescription = clase.nombre,
                                            tint = VerdeFit,
                                            modifier = Modifier.size(26.dp)
                                        )
                                    }

                                    Spacer(Modifier.width(14.dp))

                                    Column(modifier = Modifier.weight(1f)) {
                                        Text(text = clase.nombre, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium)
                                        Text(text = "${clase.horario} - ${clase.sala}", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                    }
                                }
                            }
                        }
                    }
                }
                1 -> {
                    // Pestaña RESERVAS
                    MisReservasFitScreen(reservas = reservas, onCancelarReserva = onCancelarReserva)
                }
                2 -> {
                    // Pestaña RUTINAS
                    RutinasFitScreen()
                }
                3 -> {
                    // Pestaña PERFIL
                    PerfilFitScreen(usuarioNombre = usuarioNombre)
                }
            }
        }
    }
}

@Composable
private fun BarraInferiorFit(
    seleccionado: Int,
    onSeleccionar: (Int) -> Unit
) {
    val items = listOf(
        Triple("Inicio", Icons.Default.Home, 0),
        Triple("Reservas", Icons.Default.DateRange, 1),
        Triple("Rutinas", Icons.Default.SportsGymnastics, 2),
        Triple("Perfil", Icons.Default.Person, 3)
    )
    NavigationBar {
        items.forEach { (etiqueta, icono, indice) ->
            NavigationBarItem(
                selected = seleccionado == indice,
                onClick = { onSeleccionar(indice) },
                icon = { Icon(icono, contentDescription = etiqueta) },
                label = { Text(etiqueta) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = VerdeFit,
                    selectedTextColor = VerdeFit
                )
            )
        }
    }
}
