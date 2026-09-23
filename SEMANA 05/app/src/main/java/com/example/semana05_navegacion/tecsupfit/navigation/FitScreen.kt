package com.example.semana05_navegacion.tecsupfit.navigation

// Contract central de navegación para TECSUP Fit
sealed class FitScreen(val route: String) {
    object Inicio : FitScreen("fit_inicio")
    object Reservas : FitScreen("fit_reservas")
    object Rutinas : FitScreen("fit_rutinas")
    object Perfil : FitScreen("fit_perfil")

    object ClaseDetail : FitScreen("fit_clase_detail/{claseId}") {
        fun createRoute(claseId: Int): String = "fit_clase_detail/$claseId"
    }

    object ConfirmacionReserva : FitScreen("fit_confirmacion_reserva/{claseId}/{horario}") {
        fun createRoute(claseId: Int, horario: String): String =
            "fit_confirmacion_reserva/$claseId/$horario"
    }
}
