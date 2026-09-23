package com.example.semana05_navegacion.navigation

// Contrato central de navegación Clínica Salud+ - sealed class
// Estudiante: NIKOLAI SUAREZ | Docente: JUAN LEÓN
sealed class Screen(val route: String) {
    object Inicio : Screen("inicio")
    object PerfilMedico : Screen("perfil/{nombre}/{especialidad}") {
        fun createRoute(nombre: String, especialidad: String) = "perfil/$nombre/$especialidad"
    }
    object Agendar : Screen("agendar/{nombre}") {
        fun createRoute(nombre: String) = "agendar/$nombre"
    }
    object Confirmacion : Screen("confirm/{nombre}/{fecha}/{hora}") {
        fun createRoute(nombre: String, fecha: String, hora: String) = "confirm/$nombre/$fecha/$hora"
    }
    object MisCitas : Screen("miscitas")
    object Historial : Screen("historial")
}
