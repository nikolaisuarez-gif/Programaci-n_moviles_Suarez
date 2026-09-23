package com.example.semana05_navegacion.tecsupfit.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.semana05_navegacion.tecsupfit.navigation.FitScreen

data class GymClassInfo(
    val id: Int,
    val name: String,
    val defaultSchedule: String,
    val location: String,
    val duration: String,
    val description: String,
    val spotsLeft: String,
    val availableHorarios: List<String>
)

val classesList = listOf(
    GymClassInfo(
        id = 1,
        name = "Yoga Funcional",
        defaultSchedule = "7:00 am - Sala 2",
        location = "Sala 2",
        duration = "45 min",
        description = "Clase orientada a mejorar la flexibilidad, postura y fuerza del core mediante técnicas de estiramiento y respiración.",
        spotsLeft = "12 de 15 cupos disponibles",
        availableHorarios = listOf("7:00 am", "8:30 am", "6:00 pm")
    ),
    GymClassInfo(
        id = 2,
        name = "Cross Training",
        defaultSchedule = "6:00 pm - Sala 1",
        location = "Sala 1",
        duration = "50 min",
        description = "Entrenamiento funcional de alta intensidad. Trabajo de fuerza, resistencia metabólica e intervalos de alta potencia. Cupos limitados.",
        spotsLeft = "8 de 12 cupos disponibles",
        availableHorarios = listOf("6:00 pm", "7:00 pm", "8:00 pm")
    ),
    GymClassInfo(
        id = 3,
        name = "Spinning",
        defaultSchedule = "7:30 pm - Sala 3",
        location = "Sala 3",
        duration = "45 min",
        description = "Sesión cardiovascular sobre bicicleta estática con ritmo de música motivadora e intervalos de velocidad y resistencia.",
        spotsLeft = "5 de 20 cupos disponibles",
        availableHorarios = listOf("6:30 am", "6:00 pm", "7:30 pm")
    )
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClaseDetailScreen(navController: NavController, claseId: Int) {
    val gymClass = classesList.find { it.id == claseId } ?: classesList[0]

    // Selección única de horario usando remember/mutableStateOf
    var selectedHorario by remember { mutableStateOf(gymClass.availableHorarios[0]) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detalle de clase", fontSize = 18.sp, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver"
                        )
                    }
                }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp),
            contentPadding = padding
        ) {
            item {
                Spacer(modifier = Modifier.height(12.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(140.dp)
                        .background(Color(0xFFE8F5E9), RoundedCornerShape(16.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.FitnessCenter,
                        contentDescription = "Gimnasio",
                        modifier = Modifier.size(70.dp),
                        tint = Color(0xFF1B5E20)
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = gymClass.name,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "${gymClass.duration} • ${gymClass.location}",
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = gymClass.description,
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    lineHeight = 20.sp
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = gymClass.spotsLeft,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF2E7D32)
                )

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "Selecciona un horario:",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(12.dp))

                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(gymClass.availableHorarios) { horario ->
                        FilterChip(
                            selected = (selectedHorario == horario),
                            onClick = { selectedHorario = horario },
                            label = {
                                Text(
                                    text = horario,
                                    fontSize = 14.sp,
                                    fontWeight = if (selectedHorario == horario) FontWeight.Bold else FontWeight.Normal
                                )
                            },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = Color(0xFF1B5E20),
                                selectedLabelColor = Color.White
                            )
                        )
                    }
                }

                Spacer(modifier = Modifier.height(36.dp))

                Button(
                    onClick = {
                        val finalHorario = "$selectedHorario - ${gymClass.location}"
                        navController.navigate(
                            FitScreen.ConfirmacionReserva.createRoute(
                                claseId = gymClass.id,
                                horario = finalHorario
                            )
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1B5E20))
                ) {
                    Text(
                        text = "Reservar cupo",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }
        }
    }
}
