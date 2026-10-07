package com.example.semana05_navegacion.clinica.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MedicalServices
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.semana05_navegacion.clinica.modelo.Medico
import com.example.semana05_navegacion.clinica.modelo.especialidadesClinica
import com.example.semana05_navegacion.clinica.modelo.listaMedicosFake
import kotlinx.coroutines.launch

val MoradoClinica = Color(0xFF673AB7)
val MoradoFondoChip = Color(0xFFEDE7F6)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InicioClinicaScreen(
    usuarioNombre: String = "Nikolai Suarez",
    medicos: List<Medico> = listaMedicosFake,
    destinoDrawerActual: String = "inicio",
    onNavegarDrawer: (String) -> Unit,
    onMedicoSelected: (Int) -> Unit
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    var especialidadSeleccionada by remember { mutableStateOf("Cardiología") }

    val medicosFiltrados = medicos.filter {
        it.especialidad.contains(especialidadSeleccionada, ignoreCase = true) || especialidadSeleccionada.isEmpty()
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                // Header del Drawer: Avatar "NS", Nikolai Suarez, Paciente
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MoradoClinica.copy(alpha = 0.08f))
                        .padding(24.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(56.dp)
                            .background(MoradoFondoChip, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "NS",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = MoradoClinica
                        )
                    }
                    Spacer(Modifier.height(12.dp))
                    Text(text = usuarioNombre, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                    Text(text = "Paciente", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }

                HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

                // Destinos del Drawer
                val items = listOf(
                    Triple("Inicio", "inicio", Icons.Default.Home),
                    Triple("Mis citas", "citas", Icons.Default.DateRange),
                    Triple("Historial médico", "historial", Icons.Default.MedicalServices),
                    Triple("Perfil", "perfil", Icons.Default.Person)
                )

                items.forEach { (etiqueta, ruta, icono) ->
                    NavigationDrawerItem(
                        icon = { Icon(icono, contentDescription = etiqueta) },
                        label = { Text(etiqueta, fontWeight = FontWeight.Medium) },
                        selected = (destinoDrawerActual == ruta),
                        onClick = {
                            scope.launch { drawerState.close() }
                            onNavegarDrawer(ruta)
                        },
                        colors = NavigationDrawerItemDefaults.colors(
                            selectedContainerColor = MoradoFondoChip,
                            selectedIconColor = MoradoClinica,
                            selectedTextColor = MoradoClinica
                        ),
                        modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                    )
                }
            }
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Column {
                            Text("Clínica Salud+", fontWeight = FontWeight.Bold, color = Color.White)
                            Text("Hola, Nikolai", style = MaterialTheme.typography.bodySmall, color = Color.White.copy(alpha = 0.8f))
                        }
                    },
                    navigationIcon = {
                        IconButton(onClick = { scope.launch { drawerState.open() } }) {
                            Icon(Icons.Default.Menu, contentDescription = "Menú lateral", tint = Color.White)
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = MoradoClinica)
                )
            }
        ) { paddingInterno ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingInterno)
                    .padding(horizontal = 16.dp)
            ) {
                Spacer(Modifier.height(12.dp))

                // LazyRow de especialidades
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    contentPadding = PaddingValues(vertical = 4.dp)
                ) {
                    items(especialidadesClinica) { esp ->
                        val seleccionado = (esp == especialidadSeleccionada)
                        FilterChip(
                            selected = seleccionado,
                            onClick = { especialidadSeleccionada = esp },
                            label = { Text(esp) },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = MoradoClinica,
                                selectedLabelColor = Color.White,
                                containerColor = MoradoFondoChip
                            )
                        )
                    }
                }

                Spacer(Modifier.height(12.dp))

                Text(
                    text = "Médicos disponibles",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                // LazyColumn de médicos
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    contentPadding = PaddingValues(bottom = 16.dp)
                ) {
                    items(medicosFiltrados) { medico ->
                        Card(
                            onClick = { onMedicoSelected(medico.id) },
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
                                        .background(MoradoFondoChip, CircleShape),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Add,
                                        contentDescription = medico.nombre,
                                        tint = MoradoClinica,
                                        modifier = Modifier.size(28.dp)
                                    )
                                }

                                Spacer(Modifier.width(14.dp))

                                Column(modifier = Modifier.weight(1f)) {
                                    Text(text = medico.nombre, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium)
                                    Text(text = medico.especialidad, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                }

                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(Icons.Default.Star, contentDescription = "Estrella", tint = Color(0xFFFFC107), modifier = Modifier.size(18.dp))
                                    Spacer(Modifier.width(4.dp))
                                    Text(text = "${medico.calificacion}", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.bodyMedium)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
