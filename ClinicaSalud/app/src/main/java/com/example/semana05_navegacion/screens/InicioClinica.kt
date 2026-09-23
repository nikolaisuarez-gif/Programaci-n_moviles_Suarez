package com.example.semana05_navegacion.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.semana05_navegacion.navigation.Screen
import kotlinx.coroutines.launch

data class Medico(val nombre:String, val esp:String, val calif:String)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InicioClinica(nav: NavController){
    var filtro by remember { mutableStateOf("Todos") }
    val medicos = listOf(
        Medico("Dra. Ana Torres","Cardiología","4.9"),
        Medico("Dr. Luis Vega","Pediatría","4.7"),
        Medico("Dra. Rosa Diaz","Dermatología","4.8")
    )
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet{
                Text("Juan Pérez", style=MaterialTheme.typography.titleMedium, modifier=Modifier.padding(16.dp))
                Text("Paciente", style=MaterialTheme.typography.labelSmall, modifier=Modifier.padding(horizontal=16.dp))
                HorizontalDivider(Modifier.padding(16.dp))
                NavigationDrawerItem(label={Text("Inicio")}, selected=true, onClick={ scope.launch{ drawerState.close() }})
                NavigationDrawerItem(label={Text("Mis citas")}, selected=false, onClick={ scope.launch{ drawerState.close() }; nav.navigate(Screen.MisCitas.route)})
                NavigationDrawerItem(label={Text("Historial médico")}, selected=false, onClick={ scope.launch{ drawerState.close() }; nav.navigate(Screen.Historial.route)})
                NavigationDrawerItem(label={Text("Perfil")}, selected=false, onClick={})
            }
        }
    ){
        Scaffold(
            topBar = {
                TopAppBar(
                    title={Text("Clínica Salud+")},
                    navigationIcon={
                        IconButton(onClick={ scope.launch{ drawerState.open()}}){
                            Icon(Icons.Filled.Menu, contentDescription="Menú")
                        }
                    }
                )
            }
        ){ padding ->
            Column(Modifier.padding(padding).padding(16.dp)){
                Text("Hola, Juan", style=MaterialTheme.typography.titleSmall)
                Spacer(Modifier.height(8.dp))
                LazyRow(horizontalArrangement=Arrangement.spacedBy(8.dp)){
                    items(listOf("Todos","Cardiología","Pediatría","Dermatología")){ chip ->
                        FilterChip(selected= filtro==chip, onClick={ filtro=chip }, label={Text(chip)})
                    }
                }
                Spacer(Modifier.height(12.dp))
                Text("Médicos disponibles", style=MaterialTheme.typography.titleSmall)
                Spacer(Modifier.height(8.dp))
                LazyColumn(verticalArrangement=Arrangement.spacedBy(10.dp)){
                    items(medicos.filter{ filtro=="Todos" || it.esp==filtro }){ med ->
                        Card(Modifier.fillMaxWidth().clickable{ nav.navigate(Screen.PerfilMedico.createRoute(med.nombre, med.esp))}){
                            ListItem(
                                headlineContent={Text(med.nombre)},
                                supportingContent={Text(med.esp)},
                                trailingContent={ Row{ Icon(Icons.Filled.Star, null, tint=MaterialTheme.colorScheme.primary); Text(med.calif)}}
                            )
                        }
                    }
                }
            }
        }
    }
}
