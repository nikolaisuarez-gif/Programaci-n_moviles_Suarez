package com.example.semana05_navegacion.fit

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.semana05_navegacion.fit.modelo.ReservaGym
import com.example.semana05_navegacion.fit.modelo.listaClasesFitFake
import com.example.semana05_navegacion.fit.modelo.listaReservasInicialesFit
import com.example.semana05_navegacion.fit.screens.ConfirmacionReservaScreen
import com.example.semana05_navegacion.fit.screens.DetalleClaseScreen
import com.example.semana05_navegacion.fit.screens.InicioFitScreen

private object RutasFit {
    const val INICIO = "inicio_fit"
    const val DETALLE = "detalle_clase/{claseId}"
    const val CONFIRMACION = "confirmacion_fit/{claseNombre}/{horario}/{sala}"

    fun detalle(id: Int) = "detalle_clase/$id"
    fun confirmacion(nombre: String, horario: String, sala: String) = "confirmacion_fit/$nombre/$horario/$sala"
}

@Composable
fun FitApp() {
    val navController = rememberNavController()
    var reservas by remember { mutableStateOf(listaReservasInicialesFit) }

    NavHost(
        navController = navController,
        startDestination = RutasFit.INICIO
    ) {
        composable(RutasFit.INICIO) {
            InicioFitScreen(
                clases = listaClasesFitFake,
                reservas = reservas,
                onClaseClick = { claseId ->
                    navController.navigate(RutasFit.detalle(claseId))
                },
                onCancelarReserva = { reservaId ->
                    reservas = reservas.filterNot { it.id == reservaId }
                }
            )
        }

        composable(
            route = RutasFit.DETALLE,
            arguments = listOf(navArgument("claseId") { type = NavType.IntType })
        ) { backStackEntry ->
            val claseId = backStackEntry.arguments?.getInt("claseId") ?: 1
            val clase = listaClasesFitFake.firstOrNull { it.id == claseId } ?: listaClasesFitFake.first()

            DetalleClaseScreen(
                clase = clase,
                onVolver = { navController.popBackStack() },
                onReservarCupo = {
                    val nuevaReserva = ReservaGym(
                        id = reservas.size + 1,
                        claseNombre = clase.nombre,
                        horario = clase.horario,
                        sala = clase.sala,
                        estado = "Confirmada"
                    )
                    reservas = listOf(nuevaReserva) + reservas
                    navController.navigate(RutasFit.confirmacion(clase.nombre, clase.horario, clase.sala))
                }
            )
        }

        composable(
            route = RutasFit.CONFIRMACION,
            arguments = listOf(
                navArgument("claseNombre") { type = NavType.StringType },
                navArgument("horario") { type = NavType.StringType },
                navArgument("sala") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val claseNombre = backStackEntry.arguments?.getString("claseNombre") ?: ""
            val horario = backStackEntry.arguments?.getString("horario") ?: ""
            val sala = backStackEntry.arguments?.getString("sala") ?: ""

            ConfirmacionReservaScreen(
                claseNombre = claseNombre,
                horario = horario,
                sala = sala,
                onVerReservas = {
                    navController.navigate(RutasFit.INICIO) {
                        popUpTo(RutasFit.INICIO)
                    }
                }
            )
        }
    }
}
