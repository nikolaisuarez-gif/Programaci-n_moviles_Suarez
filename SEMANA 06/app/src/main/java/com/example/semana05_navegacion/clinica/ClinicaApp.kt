package com.example.semana05_navegacion.clinica

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
import com.example.semana05_navegacion.clinica.modelo.CitaMedica
import com.example.semana05_navegacion.clinica.modelo.listaCitasIniciales
import com.example.semana05_navegacion.clinica.modelo.listaMedicosFake
import com.example.semana05_navegacion.clinica.screens.AgendarCitaScreen
import com.example.semana05_navegacion.clinica.screens.ConfirmacionCitaScreen
import com.example.semana05_navegacion.clinica.screens.HistorialMedicoScreen
import com.example.semana05_navegacion.clinica.screens.InicioClinicaScreen
import com.example.semana05_navegacion.clinica.screens.MisCitasScreen
import com.example.semana05_navegacion.clinica.screens.PerfilMedicoScreen
import com.example.semana05_navegacion.clinica.screens.PerfilPacienteScreen

private object RutasClinica {
    const val INICIO = "inicio"
    const val PERFIL_MEDICO = "perfil_medico/{medicoId}"
    const val AGENDAR = "agendar/{medicoId}"
    const val CONFIRMACION = "confirmacion/{medicoNombre}/{fecha}/{hora}"
    const val CITAS = "citas"
    const val HISTORIAL = "historial"
    const val PERFIL = "perfil"

    fun perfilMedico(id: Int) = "perfil_medico/$id"
    fun agendar(id: Int) = "agendar/$id"
    fun confirmacion(nombre: String, fecha: String, hora: String) = "confirmacion/$nombre/$fecha/$hora"
}

@Composable
fun ClinicaApp() {
    val navController = rememberNavController()
    var citas by remember { mutableStateOf(listaCitasIniciales) }

    NavHost(
        navController = navController,
        startDestination = RutasClinica.INICIO
    ) {
        composable(RutasClinica.INICIO) {
            InicioClinicaScreen(
                medicos = listaMedicosFake,
                destinoDrawerActual = "inicio",
                onNavegarDrawer = { ruta ->
                    when (ruta) {
                        "inicio" -> { /* Ya estamos en inicio */ }
                        "citas" -> navController.navigate(RutasClinica.CITAS)
                        "historial" -> navController.navigate(RutasClinica.HISTORIAL)
                        "perfil" -> navController.navigate(RutasClinica.PERFIL)
                    }
                },
                onMedicoSelected = { medicoId ->
                    navController.navigate(RutasClinica.perfilMedico(medicoId))
                }
            )
        }

        composable(
            route = RutasClinica.PERFIL_MEDICO,
            arguments = listOf(navArgument("medicoId") { type = NavType.IntType })
        ) { backStackEntry ->
            val medicoId = backStackEntry.arguments?.getInt("medicoId") ?: 1
            val medico = listaMedicosFake.firstOrNull { it.id == medicoId } ?: listaMedicosFake.first()

            PerfilMedicoScreen(
                medico = medico,
                onVolver = { navController.popBackStack() },
                onAgendarCita = {
                    navController.navigate(RutasClinica.agendar(medico.id))
                }
            )
        }

        composable(
            route = RutasClinica.AGENDAR,
            arguments = listOf(navArgument("medicoId") { type = NavType.IntType })
        ) { backStackEntry ->
            val medicoId = backStackEntry.arguments?.getInt("medicoId") ?: 1
            val medico = listaMedicosFake.firstOrNull { it.id == medicoId } ?: listaMedicosFake.first()

            AgendarCitaScreen(
                medicoNombre = medico.nombre,
                onVolver = { navController.popBackStack() },
                onConfirmarCita = { fecha, hora ->
                    val nuevaCita = CitaMedica(
                        id = citas.size + 1,
                        medicoNombre = medico.nombre,
                        especialidad = medico.especialidad,
                        fecha = fecha,
                        hora = hora,
                        estado = "Confirmada"
                    )
                    citas = listOf(nuevaCita) + citas
                    navController.navigate(RutasClinica.confirmacion(medico.nombre, fecha, hora))
                }
            )
        }

        composable(
            route = RutasClinica.CONFIRMACION,
            arguments = listOf(
                navArgument("medicoNombre") { type = NavType.StringType },
                navArgument("fecha") { type = NavType.StringType },
                navArgument("hora") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val medicoNombre = backStackEntry.arguments?.getString("medicoNombre") ?: ""
            val fecha = backStackEntry.arguments?.getString("fecha") ?: ""
            val hora = backStackEntry.arguments?.getString("hora") ?: ""

            ConfirmacionCitaScreen(
                medicoNombre = medicoNombre,
                fecha = fecha,
                hora = hora,
                onVerMisCitas = {
                    navController.navigate(RutasClinica.CITAS) {
                        popUpTo(RutasClinica.INICIO)
                    }
                }
            )
        }

        composable(RutasClinica.CITAS) {
            MisCitasScreen(
                citas = citas,
                onVolver = { navController.popBackStack() },
                onCancelarCita = { citaId ->
                    citas = citas.filterNot { it.id == citaId }
                }
            )
        }

        composable(RutasClinica.HISTORIAL) {
            HistorialMedicoScreen(
                onVolver = { navController.popBackStack() }
            )
        }

        composable(RutasClinica.PERFIL) {
            PerfilPacienteScreen(
                onVolver = { navController.popBackStack() }
            )
        }
    }
}
