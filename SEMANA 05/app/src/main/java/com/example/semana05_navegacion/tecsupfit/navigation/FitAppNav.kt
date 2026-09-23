package com.example.semana05_navegacion.tecsupfit.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.semana05_navegacion.tecsupfit.screens.*

@Composable
fun FitAppNav() {
    val navController = rememberNavController()

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        bottomBar = {
            // bottomBar visible en Inicio, Reservas, Rutinas, Perfil (y resalta la activa)
            NavigationBar(
                containerColor = MaterialTheme.colorScheme.surface,
                contentColor = Color(0xFF1B5E20)
            ) {
                NavigationBarItem(
                    selected = currentRoute == FitScreen.Inicio.route,
                    onClick = {
                        navController.navigate(FitScreen.Inicio.route) {
                            popUpTo(FitScreen.Inicio.route) { inclusive = true }
                        }
                    },
                    icon = { Icon(Icons.Default.Home, contentDescription = "Inicio") },
                    label = { Text("Inicio", fontWeight = if (currentRoute == FitScreen.Inicio.route) FontWeight.Bold else FontWeight.Normal) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color(0xFF1B5E20),
                        indicatorColor = Color(0xFFC8E6C9)
                    )
                )

                NavigationBarItem(
                    selected = currentRoute == FitScreen.Reservas.route,
                    onClick = {
                        navController.navigate(FitScreen.Reservas.route) {
                            popUpTo(FitScreen.Inicio.route)
                        }
                    },
                    icon = { Icon(Icons.Default.DateRange, contentDescription = "Reservas") },
                    label = { Text("Reservas", fontWeight = if (currentRoute == FitScreen.Reservas.route) FontWeight.Bold else FontWeight.Normal) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color(0xFF1B5E20),
                        indicatorColor = Color(0xFFC8E6C9)
                    )
                )

                NavigationBarItem(
                    selected = currentRoute == FitScreen.Rutinas.route,
                    onClick = {
                        navController.navigate(FitScreen.Rutinas.route) {
                            popUpTo(FitScreen.Inicio.route)
                        }
                    },
                    icon = { Icon(Icons.Default.FitnessCenter, contentDescription = "Rutinas") },
                    label = { Text("Rutinas", fontWeight = if (currentRoute == FitScreen.Rutinas.route) FontWeight.Bold else FontWeight.Normal) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color(0xFF1B5E20),
                        indicatorColor = Color(0xFFC8E6C9)
                    )
                )

                NavigationBarItem(
                    selected = currentRoute == FitScreen.Perfil.route,
                    onClick = {
                        navController.navigate(FitScreen.Perfil.route) {
                            popUpTo(FitScreen.Inicio.route)
                        }
                    },
                    icon = { Icon(Icons.Default.Person, contentDescription = "Perfil") },
                    label = { Text("Perfil", fontWeight = if (currentRoute == FitScreen.Perfil.route) FontWeight.Bold else FontWeight.Normal) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color(0xFF1B5E20),
                        indicatorColor = Color(0xFFC8E6C9)
                    )
                )
            }
        }
    ) { padding ->
        NavHost(
            navController = navController,
            startDestination = FitScreen.Inicio.route,
            modifier = Modifier.padding(padding)
        ) {
            composable(FitScreen.Inicio.route) {
                InicioScreen(navController = navController)
            }

            composable(FitScreen.Reservas.route) {
                MisReservasScreen()
            }

            composable(FitScreen.Rutinas.route) {
                RutinasScreen()
            }

            composable(FitScreen.Perfil.route) {
                PerfilScreen()
            }

            composable(
                route = FitScreen.ClaseDetail.route,
                arguments = listOf(
                    navArgument("claseId") { type = NavType.IntType }
                )
            ) { backStackEntry ->
                val claseId = backStackEntry.arguments?.getInt("claseId") ?: 1
                ClaseDetailScreen(navController = navController, claseId = claseId)
            }

            composable(
                route = FitScreen.ConfirmacionReserva.route,
                arguments = listOf(
                    navArgument("claseId") { type = NavType.IntType },
                    navArgument("horario") { type = NavType.StringType }
                )
            ) { backStackEntry ->
                val claseId = backStackEntry.arguments?.getInt("claseId") ?: 1
                val horario = backStackEntry.arguments?.getString("horario") ?: "6:00 pm - Sala 1"
                ConfirmacionReservaScreen(
                    navController = navController,
                    claseId = claseId,
                    horario = horario
                )
            }
        }
    }
}
