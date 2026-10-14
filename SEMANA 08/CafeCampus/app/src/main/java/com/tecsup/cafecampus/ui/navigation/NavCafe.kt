package com.tecsup.cafecampus.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.ListAlt
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.RestaurantMenu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.tecsup.cafecampus.data.prefs.PreferenciasStore
import com.tecsup.cafecampus.model.Rol
import com.tecsup.cafecampus.ui.admin.AjustesScreen
import com.tecsup.cafecampus.ui.admin.DetallePedidoAdminScreen
import com.tecsup.cafecampus.ui.admin.FormularioProductoScreen
import com.tecsup.cafecampus.ui.admin.MenuAdminScreen
import com.tecsup.cafecampus.ui.admin.PanelScreen
import com.tecsup.cafecampus.ui.admin.PedidosAdminScreen
import com.tecsup.cafecampus.ui.cliente.ConfirmarPedidoScreen
import com.tecsup.cafecampus.ui.cliente.DetalleProductoScreen
import com.tecsup.cafecampus.ui.cliente.LoginScreen
import com.tecsup.cafecampus.ui.cliente.MenuScreen
import com.tecsup.cafecampus.ui.cliente.MisPedidosScreen
import com.tecsup.cafecampus.ui.cliente.SeguimientoScreen
import com.tecsup.cafecampus.ui.cliente.SplashScreen
import com.tecsup.cafecampus.viewmodel.MenuAdminViewModel
import com.tecsup.cafecampus.viewmodel.MenuViewModel
import com.tecsup.cafecampus.viewmodel.PanelAdminViewModel
import com.tecsup.cafecampus.viewmodel.PedidosAdminViewModel
import com.tecsup.cafecampus.viewmodel.PedidosClienteViewModel
import com.tecsup.cafecampus.viewmodel.SesionViewModel

@Composable
fun NavCafe(
    sesionViewModel: SesionViewModel,
    menuViewModel: MenuViewModel,
    pedidosClienteViewModel: PedidosClienteViewModel,
    panelAdminViewModel: PanelAdminViewModel,
    pedidosAdminViewModel: PedidosAdminViewModel,
    menuAdminViewModel: MenuAdminViewModel,
    preferenciasStore: PreferenciasStore,
    modoOscuro: Boolean,
    notificaciones: Boolean,
    recibirPedidos: Boolean,
    navController: NavHostController = rememberNavController()
) {
    val rolSeleccionado by sesionViewModel.rolSeleccionado.collectAsState()
    val usuarioActivo by sesionViewModel.usuarioActivo.collectAsState()

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination?.route

    val clienteRoutes = listOf(
        Rutas.ClienteMenu.ruta,
        Rutas.ClienteMisPedidos.ruta,
        Rutas.AdminAjustes.ruta
    )

    val adminRoutes = listOf(
        Rutas.AdminPanel.ruta,
        Rutas.AdminPedidos.ruta,
        Rutas.AdminMenu.ruta,
        Rutas.AdminAjustes.ruta
    )

    val mostrarBottomBar = when (rolSeleccionado) {
        Rol.CLIENTE -> currentDestination in clienteRoutes
        Rol.ADMIN -> currentDestination in adminRoutes
    }

    Scaffold(
        bottomBar = {
            if (mostrarBottomBar) {
                NavigationBar {
                    if (rolSeleccionado == Rol.CLIENTE) {
                        NavigationBarItem(
                            icon = { Icon(Icons.Default.RestaurantMenu, contentDescription = null) },
                            label = { Text("Menú") },
                            selected = currentDestination == Rutas.ClienteMenu.ruta,
                            onClick = {
                                navController.navigate(Rutas.ClienteMenu.ruta) {
                                    popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                                    launchSingleTop = true; restoreState = true
                                }
                            }
                        )
                        NavigationBarItem(
                            icon = { Icon(Icons.Default.ListAlt, contentDescription = null) },
                            label = { Text("Pedidos") },
                            selected = currentDestination == Rutas.ClienteMisPedidos.ruta,
                            onClick = {
                                navController.navigate(Rutas.ClienteMisPedidos.ruta) {
                                    popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                                    launchSingleTop = true; restoreState = true
                                }
                            }
                        )
                        NavigationBarItem(
                            icon = { Icon(Icons.Default.Person, contentDescription = null) },
                            label = { Text("Perfil") },
                            selected = currentDestination == Rutas.AdminAjustes.ruta,
                            onClick = {
                                navController.navigate(Rutas.AdminAjustes.ruta) {
                                    popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                                    launchSingleTop = true; restoreState = true
                                }
                            }
                        )
                    } else {
                        NavigationBarItem(
                            icon = { Icon(Icons.Default.Dashboard, contentDescription = null) },
                            label = { Text("Panel") },
                            selected = currentDestination == Rutas.AdminPanel.ruta,
                            onClick = {
                                navController.navigate(Rutas.AdminPanel.ruta) {
                                    popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                                    launchSingleTop = true; restoreState = true
                                }
                            }
                        )
                        NavigationBarItem(
                            icon = { Icon(Icons.Default.ListAlt, contentDescription = null) },
                            label = { Text("Pedidos") },
                            selected = currentDestination == Rutas.AdminPedidos.ruta,
                            onClick = {
                                navController.navigate(Rutas.AdminPedidos.ruta) {
                                    popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                                    launchSingleTop = true; restoreState = true
                                }
                            }
                        )
                        NavigationBarItem(
                            icon = { Icon(Icons.Default.RestaurantMenu, contentDescription = null) },
                            label = { Text("Menú") },
                            selected = currentDestination == Rutas.AdminMenu.ruta,
                            onClick = {
                                navController.navigate(Rutas.AdminMenu.ruta) {
                                    popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                                    launchSingleTop = true; restoreState = true
                                }
                            }
                        )
                        NavigationBarItem(
                            icon = { Icon(Icons.Default.Settings, contentDescription = null) },
                            label = { Text("Ajustes") },
                            selected = currentDestination == Rutas.AdminAjustes.ruta,
                            onClick = {
                                navController.navigate(Rutas.AdminAjustes.ruta) {
                                    popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                                    launchSingleTop = true; restoreState = true
                                }
                            }
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Rutas.Splash.ruta,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Rutas.Splash.ruta) {
                SplashScreen(
                    onComenzarClick = { navController.navigate(Rutas.Login.ruta) },
                    onTengoCuentaClick = { navController.navigate(Rutas.Login.ruta) }
                )
            }

            composable(Rutas.Login.ruta) {
                LoginScreen(
                    vm = sesionViewModel,
                    onLoginExitoso = { rol ->
                        if (rol == Rol.ADMIN) {
                            navController.navigate(Rutas.AdminPanel.ruta) {
                                popUpTo(Rutas.Splash.ruta) { inclusive = true }
                            }
                        } else {
                            navController.navigate(Rutas.ClienteMenu.ruta) {
                                popUpTo(Rutas.Splash.ruta) { inclusive = true }
                            }
                        }
                    }
                )
            }

            // Cliente
            composable(Rutas.ClienteMenu.ruta) {
                MenuScreen(
                    vm = menuViewModel,
                    nombreUsuario = usuarioActivo?.nombre ?: "Juan",
                    onProductoClick = { id -> navController.navigate(Rutas.ClienteDetalleProducto.crearRuta(id)) },
                    onCarritoClick = { navController.navigate(Rutas.ClienteConfirmarPedido.ruta) }
                )
            }

            composable(
                route = Rutas.ClienteDetalleProducto.ruta,
                arguments = listOf(navArgument("productoId") { type = NavType.IntType })
            ) { backStackEntry ->
                val id = backStackEntry.arguments?.getInt("productoId") ?: 0
                DetalleProductoScreen(
                    productoId = id,
                    vm = menuViewModel,
                    onBackClick = { navController.popBackStack() },
                    onAgregarExitoso = { navController.popBackStack() }
                )
            }

            composable(Rutas.ClienteConfirmarPedido.ruta) {
                ConfirmarPedidoScreen(
                    clienteId = usuarioActivo?.id ?: 1,
                    menuVm = menuViewModel,
                    pedidosVm = pedidosClienteViewModel,
                    onBackClick = { navController.popBackStack() },
                    onPedidoConfirmado = { id ->
                        navController.navigate(Rutas.ClienteSeguimiento.crearRuta(id.toInt())) {
                            popUpTo(Rutas.ClienteMenu.ruta)
                        }
                    }
                )
            }

            composable(
                route = Rutas.ClienteSeguimiento.ruta,
                arguments = listOf(navArgument("pedidoId") { type = NavType.IntType })
            ) { backStackEntry ->
                val id = backStackEntry.arguments?.getInt("pedidoId") ?: 0
                SeguimientoScreen(
                    pedidoId = id,
                    vm = pedidosClienteViewModel,
                    onBackClick = { navController.popBackStack() }
                )
            }

            composable(Rutas.ClienteMisPedidos.ruta) {
                MisPedidosScreen(
                    clienteId = usuarioActivo?.id ?: 1,
                    vm = pedidosClienteViewModel,
                    onPedidoClick = { id -> navController.navigate(Rutas.ClienteSeguimiento.crearRuta(id)) }
                )
            }

            // Admin
            composable(Rutas.AdminPanel.ruta) {
                PanelScreen(
                    vm = panelAdminViewModel,
                    onVerTodosPedidos = { navController.navigate(Rutas.AdminPedidos.ruta) },
                    onPrepararPedido = { id -> navController.navigate(Rutas.AdminDetallePedido.crearRuta(id)) }
                )
            }

            composable(Rutas.AdminPedidos.ruta) {
                PedidosAdminScreen(
                    vm = pedidosAdminViewModel,
                    onPedidoClick = { id -> navController.navigate(Rutas.AdminDetallePedido.crearRuta(id)) }
                )
            }

            composable(
                route = Rutas.AdminDetallePedido.ruta,
                arguments = listOf(navArgument("pedidoId") { type = NavType.IntType })
            ) { backStackEntry ->
                val id = backStackEntry.arguments?.getInt("pedidoId") ?: 0
                DetallePedidoAdminScreen(
                    pedidoId = id,
                    vm = pedidosAdminViewModel,
                    onBackClick = { navController.popBackStack() }
                )
            }

            composable(Rutas.AdminMenu.ruta) {
                MenuAdminScreen(
                    vm = menuAdminViewModel,
                    onNuevoProductoClick = { navController.navigate(Rutas.AdminFormularioProducto.crearRuta(null)) },
                    onEditarProductoClick = { id -> navController.navigate(Rutas.AdminFormularioProducto.crearRuta(id)) }
                )
            }

            composable(
                route = Rutas.AdminFormularioProducto.ruta,
                arguments = listOf(navArgument("productoId") { type = NavType.IntType; defaultValue = -1 })
            ) { backStackEntry ->
                val idArg = backStackEntry.arguments?.getInt("productoId")
                val id = if (idArg == null || idArg == -1) null else idArg
                FormularioProductoScreen(
                    productoId = id,
                    vm = menuAdminViewModel,
                    onBackClick = { navController.popBackStack() }
                )
            }

            composable(Rutas.AdminAjustes.ruta) {
                AjustesScreen(
                    usuario = usuarioActivo,
                    rolActual = rolSeleccionado,
                    preferenciasStore = preferenciasStore,
                    modoOscuro = modoOscuro,
                    notificaciones = notificaciones,
                    recibirPedidos = recibirPedidos,
                    onCambiarPerfilClick = {
                        val nuevoRol = if (rolSeleccionado == Rol.ADMIN) Rol.CLIENTE else Rol.ADMIN
                        sesionViewModel.cambiarRolManual(nuevoRol)
                        if (nuevoRol == Rol.ADMIN) {
                            navController.navigate(Rutas.AdminPanel.ruta) {
                                popUpTo(0) { inclusive = true }
                            }
                        } else {
                            navController.navigate(Rutas.ClienteMenu.ruta) {
                                popUpTo(0) { inclusive = true }
                            }
                        }
                    },
                    onCerrarSesionClick = {
                        sesionViewModel.cerrarSesion()
                        navController.navigate(Rutas.Login.ruta) {
                            popUpTo(0) { inclusive = true }
                        }
                    }
                )
            }
        }
    }
}
