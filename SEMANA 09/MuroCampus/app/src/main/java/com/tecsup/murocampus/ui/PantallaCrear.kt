package com.tecsup.murocampus.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tecsup.murocampus.viewmodel.PostsViewModel
import com.tecsup.murocampus.viewmodel.UiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaCrear(viewModel: PostsViewModel, onBack: () -> Unit) {
    var titulo by remember { mutableStateOf("") }
    var contenido by remember { mutableStateOf("") }
    val estado by viewModel.publicacion.collectAsStateWithLifecycle()
    BackHandler(onBack = onBack)

    LaunchedEffect(estado) {
        if (estado is UiState.Exito) {
            titulo = ""
            contenido = ""
        }
    }

    Scaffold(topBar = { TopAppBar(title = { Text("MuroCampus") }) }) { padding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(padding).padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedTextField(
                value = titulo,
                onValueChange = { titulo = it },
                label = { Text("Título") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            OutlinedTextField(
                value = contenido,
                onValueChange = { contenido = it },
                label = { Text("Contenido") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3
            )
            Button(
                onClick = { viewModel.publicar(titulo, contenido) },
                enabled = titulo.isNotBlank() && contenido.isNotBlank() && estado !is UiState.Cargando,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(if (estado is UiState.Cargando) "Publicando…" else "Publicar")
            }
            when (val actual = estado) {
                null -> Unit
                UiState.Cargando -> Cargando("Publicando…")
                is UiState.Error -> Text(actual.mensaje, color = androidx.compose.material3.MaterialTheme.colorScheme.error)
                is UiState.Exito -> Text("Publicado con id ${actual.datos.id}",
                    color = androidx.compose.material3.MaterialTheme.colorScheme.primary)
            }
        }
    }
}
