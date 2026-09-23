package com.example.semana05_navegacion.navigation
sealed class Screen(val route: String) {
    object Inicio : Screen("inicio")
    object Detalle : Screen("detalle/{nombre}/{horario}") {
        fun createRoute(nombre:String, horario:String) = "detalle/$nombre/$horario"
    }
    object Confirmacion : Screen("confirm/{nombre}/{horario}") {
        fun createRoute(nombre:String, horario:String) = "confirm/$nombre/$horario"
    }
    object Reservas : Screen("reservas")
    object Rutinas : Screen("rutinas")
    object Perfil : Screen("perfil")
}
