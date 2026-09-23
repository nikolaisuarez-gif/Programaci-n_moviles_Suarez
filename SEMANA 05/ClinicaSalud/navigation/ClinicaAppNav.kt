package com.example.semana05_navegacion.clinicasalud.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.semana05_navegacion.clinicasalud.screens.*
import kotlinx.coroutines.launch

@Composable
fun ClinicaAppNav() {
    val navController = rememberNavController()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(60.dp)
                            .background(Color(0xFFE8DEF8), CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "JP",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF1D192B)
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "Juan Pérez",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Paciente",
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                HorizontalDivider()

                Spacer(modifier = Modifier.height(12.dp))

                NavigationDrawerItem(
                    label = { Text("Inicio") },
                    selected = currentRoute == ClinicaScreen.Inicio.route,
                    icon = { Icon(Icons.Default.Home, contentDescription = "Inicio") },
                    onClick = {
                        scope.launch { drawerState.close() }
                        navController.navigate(ClinicaScreen.Inicio.route) {
                            popUpTo(ClinicaScreen.Inicio.route) { inclusive = true }
                        }
                    },
                    modifier = Modifier.padding(horizontal = 12.dp)
                )

                NavigationDrawerItem(
                    label = { Text("Mis citas") },
                    selected = currentRoute == ClinicaScreen.MisCitas.route,
                    icon = { Icon(Icons.Default.DateRange, contentDescription = "Mis citas") },
                    onClick = {
                        scope.launch { drawerState.close() }
                        navController.navigate(ClinicaScreen.MisCitas.route) {
                            popUpTo(ClinicaScreen.Inicio.route)
                        }
                    },
                    modifier = Modifier.padding(horizontal = 12.dp)
                )

                NavigationDrawerItem(
                    label = { Text("Historial médico") },
                    selected = currentRoute == ClinicaScreen.HistorialMedico.route,
                    icon = { Icon(Icons.Default.History, contentDescription = "Historial médico") },
                    onClick = {
                        scope.launch { drawerState.close() }
                        navController.navigate(ClinicaScreen.HistorialMedico.route) {
                            popUpTo(ClinicaScreen.Inicio.route)
                        }
                    },
                    modifier = Modifier.padding(horizontal = 12.dp)
                )

                NavigationDrawerItem(
                    label = { Text("Perfil") },
                    selected = currentRoute == ClinicaScreen.Perfil.route,
                    icon = { Icon(Icons.Default.Person, contentDescription = "Perfil") },
                    onClick = {
                        scope.launch { drawerState.close() }
                        navController.navigate(ClinicaScreen.Perfil.route) {
                            popUpTo(ClinicaScreen.Inicio.route)
                        }
                    },
                    modifier = Modifier.padding(horizontal = 12.dp)
                )
            }
        }
    ) {
        NavHost(
            navController = navController,
            startDestination = ClinicaScreen.Inicio.route
        ) {
            composable(ClinicaScreen.Inicio.route) {
                InicioScreen(
                    navController = navController,
                    onOpenDrawer = { scope.launch { drawerState.open() } }
                )
            }

            composable(ClinicaScreen.MisCitas.route) {
                MisCitasScreen(
                    onOpenDrawer = { scope.launch { drawerState.open() } }
                )
            }

            composable(ClinicaScreen.HistorialMedico.route) {
                HistorialScreen(
                    onOpenDrawer = { scope.launch { drawerState.open() } }
                )
            }

            composable(ClinicaScreen.Perfil.route) {
                PerfilScreen(
                    onOpenDrawer = { scope.launch { drawerState.open() } }
                )
            }

            composable(
                route = ClinicaScreen.DoctorDetail.route,
                arguments = listOf(
                    navArgument("doctorId") { type = NavType.IntType }
                )
            ) { backStackEntry ->
                val doctorId = backStackEntry.arguments?.getInt("doctorId") ?: 1
                DoctorDetailScreen(navController = navController, doctorId = doctorId)
            }

            composable(
                route = ClinicaScreen.AgendarCita.route,
                arguments = listOf(
                    navArgument("doctorId") { type = NavType.IntType }
                )
            ) { backStackEntry ->
                val doctorId = backStackEntry.arguments?.getInt("doctorId") ?: 1
                AgendarCitaScreen(navController = navController, doctorId = doctorId)
            }

            composable(
                route = ClinicaScreen.ConfirmacionCita.route,
                arguments = listOf(
                    navArgument("doctorId") { type = NavType.IntType },
                    navArgument("fecha") { type = NavType.StringType },
                    navArgument("hora") { type = NavType.StringType }
                )
            ) { backStackEntry ->
                val doctorId = backStackEntry.arguments?.getInt("doctorId") ?: 1
                val fecha = backStackEntry.arguments?.getString("fecha") ?: "Viernes 27"
                val hora = backStackEntry.arguments?.getString("hora") ?: "10:30 am"
                ConfirmacionCitaScreen(
                    navController = navController,
                    doctorId = doctorId,
                    fecha = fecha,
                    hora = hora
                )
            }
        }
    }
}
