package com.tecsup.murocampus.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tecsup.murocampus.viewmodel.PostsViewModel
import com.tecsup.murocampus.viewmodel.UiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaLista(
    viewModel: PostsViewModel,
    onSeleccionar: (Int) -> Unit,
    onCrear: () -> Unit
) {
    val estado by viewModel.lista.collectAsStateWithLifecycle()
    val filtro by viewModel.usuarioFiltro.collectAsStateWithLifecycle()
    val busqueda by viewModel.busqueda.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("MuroCampus") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        },
        floatingActionButton = {
            androidx.compose.material3.FloatingActionButton(onClick = onCrear) { Text("+") }
        }
    ) { padding ->
        Column(Modifier.fillMaxSize().padding(padding)) {
            OutlinedTextField(
                value = busqueda,
                onValueChange = viewModel::buscar,
                label = { Text("Buscar por título") },
                modifier = Modifier.fillMaxWidth().padding(horizontal = 12.dp, vertical = 4.dp),
                singleLine = true
            )
            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                item {
                    FilterChip(selected = filtro == null, onClick = { viewModel.filtrar(null) }, label = { Text("Todos") })
                }
                items((1..10).toList()) { userId ->
                    FilterChip(
                        selected = filtro == userId,
                        onClick = { viewModel.filtrar(userId) },
                        label = { Text("Usuario $userId") }
                    )
                }
            }

            when (val actual = estado) {
                UiState.Cargando -> Cargando()
                is UiState.Error -> ErrorConReintento(actual.mensaje, viewModel::cargar)
                is UiState.Exito -> {
                    val publicaciones = actual.datos.filter {
                        it.title.contains(busqueda.trim(), ignoreCase = true)
                    }
                    if (publicaciones.isEmpty()) {
                        Text("Sin publicaciones", modifier = Modifier.padding(20.dp),
                            style = MaterialTheme.typography.bodyLarge)
                    } else {
                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            items(publicaciones, key = { it.id }) { post ->
                                TarjetaPost(post, onClick = { onSeleccionar(post.id) })
                            }
                        }
                    }
                }
            }
        }
    }
}
