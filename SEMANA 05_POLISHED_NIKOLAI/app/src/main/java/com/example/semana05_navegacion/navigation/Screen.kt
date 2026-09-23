package com.example.semana05_navegacion.navigation

// Clase sellada que actúa como contrato central de navegación.
// Recibe "route" como parámetro — es el identificador único de cada pantalla.
sealed class Screen(val route: String) {
    // Pantalla de inicio de sesión / portada
    object Login : Screen(route = "login")

    // Pantalla principal de bienvenida
    object Home : Screen(route = "home")

    // Pantalla que muestra el directorio de alumnos
    object List : Screen(route = "list")

    // Pantalla de configuración del perfil
    object Profile : Screen(route = "profile")

    // RUTA CON ARGUMENTO
    // {itemId} es el placeholder que Navigation reemplaza con el valor real
    object Detail : Screen(route = "detail/{itemId}") {
        fun createRoute(itemId: Int): String = "detail/$itemId"
    }
}
