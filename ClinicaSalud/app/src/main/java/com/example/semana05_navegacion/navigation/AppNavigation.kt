package com.example.semana05_navegacion.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.semana05_navegacion.screens.*

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.Inicio.route) {
        composable(Screen.Inicio.route) { InicioClinica(navController) }
        composable(
            "perfil/{nombre}/{especialidad}",
            arguments = listOf(
                navArgument("nombre") { type = NavType.StringType },
                navArgument("especialidad") { type = NavType.StringType }
            )
        ) {
            val nombre = it.arguments?.getString("nombre") ?: ""
            val esp = it.arguments?.getString("especialidad") ?: ""
            PerfilMedico(navController, nombre, esp)
        }
        composable("agendar/{nombre}") {
            val nombre = it.arguments?.getString("nombre") ?: ""
            AgendarCita(navController, nombre)
        }
        composable(
            "confirm/{nombre}/{fecha}/{hora}",
            arguments = listOf(
                navArgument("nombre") { type = NavType.StringType },
                navArgument("fecha") { type = NavType.StringType },
                navArgument("hora") { type = NavType.StringType }
            )
        ) {
            val nombre = it.arguments?.getString("nombre") ?: ""
            val fecha = it.arguments?.getString("fecha") ?: ""
            val hora = it.arguments?.getString("hora") ?: ""
            Confirmacion(navController, nombre, fecha, hora)
        }
        composable(Screen.MisCitas.route) { MisCitas(navController) }
        composable(Screen.Historial.route) { HistorialMedico(navController) }
    }
}
