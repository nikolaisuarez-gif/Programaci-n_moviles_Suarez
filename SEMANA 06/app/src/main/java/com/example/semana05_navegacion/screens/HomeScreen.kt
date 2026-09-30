package com.example.semana05_navegacion.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.semana05_navegacion.navigation.Screen
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Spacer(modifier = Modifier.height(16.dp))
                NavigationDrawerItem(
                    icon = { Icon(Icons.Default.Home, contentDescription = "Inicio") },
                    label = { Text("Inicio") },
                    selected = false,
                    onClick = {
                        scope.launch { drawerState.close() }
                        navController.navigate(Screen.Home.route) {
                            popUpTo(Screen.Home.route) { inclusive = true }
                        }
                    }
                )
                NavigationDrawerItem(
                    icon = { Icon(Icons.Default.ShoppingBag, contentDescription = "Mis pedidos") },
                    label = { Text("Mis pedidos") },
                    selected = false,
                    onClick = {
                        scope.launch { drawerState.close() }
                        navController.navigate(Screen.List.route)
                    }
                )
                NavigationDrawerItem(
                    icon = { Icon(Icons.Default.Favorite, contentDescription = "Favoritos") },
                    label = { Text("Favoritos") },
                    selected = false,
                    onClick = {
                        scope.launch { drawerState.close() }
                        navController.navigate(Screen.List.route)
                    }
                )
                NavigationDrawerItem(
                    icon = { Icon(Icons.Default.Person, contentDescription = "Perfil") },
                    label = { Text("Perfil") },
                    selected = false,
                    onClick = {
                        scope.launch { drawerState.close() }
                        navController.navigate(Screen.Profile.route)
                    }
                )
            }
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("TECSUP Store") },
                    navigationIcon = {
                        IconButton(onClick = { scope.launch { drawerState.open() } }) {
                            Icon(
                                imageVector = Icons.Default.Menu,
                                contentDescription = "Menú Principal"
                            )
                        }
                    }
                )
            }
        ) { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(24.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Pantalla TECSUP Store",
                    style = MaterialTheme.typography.headlineMedium
                )
                Spacer(modifier = Modifier.height(32.dp))
                Button(
                    onClick = { navController.navigate(Screen.List.route) },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Ver catálogo de productos")
                }
                Spacer(modifier = Modifier.height(12.dp))
                OutlinedButton(
                    onClick = { navController.navigate(Screen.Profile.route) },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Mi perfil")
                }
            }
        }
    }
}
