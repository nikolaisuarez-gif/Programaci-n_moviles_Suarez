package com.tecsup.footballaianalyst.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FormatListNumbered
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.SportsSoccer
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.tecsup.footballaianalyst.model.Match
import com.tecsup.footballaianalyst.ui.screens.HomeScreen
import com.tecsup.footballaianalyst.ui.screens.MatchDetailScreen
import com.tecsup.footballaianalyst.ui.screens.SettingsScreen
import com.tecsup.footballaianalyst.ui.screens.StandingsScreen
import com.tecsup.footballaianalyst.viewmodel.FootballViewModel
import com.tecsup.footballaianalyst.viewmodel.MatchDetailViewModel
import com.tecsup.footballaianalyst.viewmodel.SettingsViewModel

@Composable
fun NavFootball(
    footballVm: FootballViewModel,
    matchDetailVm: MatchDetailViewModel,
    settingsVm: SettingsViewModel,
    navController: NavHostController = rememberNavController()
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination?.route
    var selectedMatchForDetail by remember { mutableStateOf<Match?>(null) }
    val apiKey by settingsVm.apiFootballKey.collectAsState()

    val showBottomBar = currentDestination in listOf(Routes.Home.route, Routes.Standings.route, Routes.Settings.route)

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                NavigationBar {
                    NavigationBarItem(
                        icon = { Icon(imageVector = Icons.Default.SportsSoccer, contentDescription = null) },
                        label = { Text("Partidos") },
                        selected = currentDestination == Routes.Home.route,
                        onClick = {
                            navController.navigate(Routes.Home.route) {
                                popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                                launchSingleTop = true; restoreState = true
                            }
                        }
                    )
                    NavigationBarItem(
                        icon = { Icon(imageVector = Icons.Default.FormatListNumbered, contentDescription = null) },
                        label = { Text("Tablas") },
                        selected = currentDestination == Routes.Standings.route,
                        onClick = {
                            navController.navigate(Routes.Standings.route) {
                                popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                                launchSingleTop = true; restoreState = true
                            }
                        }
                    )
                    NavigationBarItem(
                        icon = { Icon(imageVector = Icons.Default.Settings, contentDescription = null) },
                        label = { Text("APIs / Web") },
                        selected = currentDestination == Routes.Settings.route,
                        onClick = {
                            navController.navigate(Routes.Settings.route) {
                                popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                                launchSingleTop = true; restoreState = true
                            }
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Routes.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Routes.Home.route) {
                HomeScreen(
                    vm = footballVm,
                    onMatchClick = { match ->
                        selectedMatchForDetail = match
                        navController.navigate(Routes.MatchDetail.route)
                    },
                    onOpenSettingsClick = {
                        navController.navigate(Routes.Settings.route)
                    }
                )
            }

            composable(Routes.Standings.route) {
                StandingsScreen()
            }

            composable(Routes.Settings.route) {
                SettingsScreen(
                    vm = settingsVm,
                    onBackClick = { navController.popBackStack() }
                )
            }

            composable(Routes.MatchDetail.route) {
                val match = selectedMatchForDetail
                if (match != null) {
                    MatchDetailScreen(
                        match = match,
                        apiKey = apiKey,
                        vm = matchDetailVm,
                        onBackClick = { navController.popBackStack() }
                    )
                }
            }
        }
    }
}
