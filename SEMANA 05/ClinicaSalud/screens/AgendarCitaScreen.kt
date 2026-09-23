package com.example.semana05_navegacion.clinicasalud.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.semana05_navegacion.clinicasalud.navigation.ClinicaScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AgendarCitaScreen(navController: NavController, doctorId: Int) {
    val doctor = doctorsList.find { it.id == doctorId } ?: doctorsList[0]

    val fechas = listOf("Jue 26", "Vie 27", "Sáb 28")
    var selectedFecha by remember { mutableStateOf("Vie 27") }

    val horas = listOf("9:00", "10:30", "3:00")
    var selectedHora by remember { mutableStateOf("10:30") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Agendar cita", fontSize = 18.sp, fontWeight = FontWeight.Bold) },
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
                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Doctor(a): ${doctor.name}",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.primary
                )

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "Selecciona fecha",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(12.dp))

                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(fechas) { fecha ->
                        FilterChip(
                            selected = (selectedFecha == fecha),
                            onClick = { selectedFecha = fecha },
                            label = {
                                Text(
                                    text = fecha,
                                    fontSize = 14.sp,
                                    fontWeight = if (selectedFecha == fecha) FontWeight.Bold else FontWeight.Normal
                                )
                            },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = Color(0xFF4A148C),
                                selectedLabelColor = Color.White
                            )
                        )
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))

                Text(
                    text = "Selecciona hora",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(12.dp))

                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(horas) { hora ->
                        FilterChip(
                            selected = (selectedHora == hora),
                            onClick = { selectedHora = hora },
                            label = {
                                Text(
                                    text = hora,
                                    fontSize = 14.sp,
                                    fontWeight = if (selectedHora == hora) FontWeight.Bold else FontWeight.Normal
                                )
                            },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = Color(0xFF4A148C),
                                selectedLabelColor = Color.White
                            )
                        )
                    }
                }

                Spacer(modifier = Modifier.height(48.dp))

                Button(
                    onClick = {
                        val formattedFecha = if (selectedFecha == "Vie 27") "Viernes 27" else selectedFecha
                        val formattedHora = "$selectedHora am"
                        navController.navigate(
                            ClinicaScreen.ConfirmacionCita.createRoute(
                                doctorId = doctor.id,
                                fecha = formattedFecha,
                                hora = formattedHora
                            )
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4A148C))
                ) {
                    Text(
                        text = "Confirmar cita",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }
        }
    }
}
