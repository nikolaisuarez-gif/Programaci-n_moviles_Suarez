package com.tecsup.cafecampus.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.tecsup.cafecampus.ui.cliente.MenuScreen
import com.tecsup.cafecampus.ui.cliente.SplashScreen
import com.tecsup.cafecampus.viewmodel.MenuViewModel

@Composable
fun NavCafe(
    menuViewModel: MenuViewModel,
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = Rutas.Splash.ruta
    ) {
        composable(Rutas.Splash.ruta) {
            SplashScreen(
                onComenzarClick = { navController.navigate(Rutas.ClienteMenu.ruta) },
                onTengoCuentaClick = { navController.navigate(Rutas.ClienteMenu.ruta) }
            )
        }

        composable(Rutas.ClienteMenu.ruta) {
            MenuScreen(
                vm = menuViewModel,
                onProductoClick = { id -> }
            )
        }
    }
}
