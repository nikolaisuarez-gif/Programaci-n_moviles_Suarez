package com.tecsup.murocampus.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tecsup.murocampus.viewmodel.PostsViewModel
import com.tecsup.murocampus.viewmodel.UiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaDetalle(postId: Int, viewModel: PostsViewModel, onBack: () -> Unit) {
    val estado by viewModel.detalle.collectAsStateWithLifecycle()
    BackHandler(onBack = onBack)

    Scaffold(topBar = { TopAppBar(title = { Text("MuroCampus") }) }) { padding ->
        when (val actual = estado) {
            UiState.Cargando -> Cargando("Cargando publicación…")
            is UiState.Error -> ErrorConReintento(actual.mensaje) { viewModel.reintentarDetalle(postId) }
            is UiState.Exito -> {
                LazyColumn(
                    modifier = Modifier.fillMaxSize().padding(padding),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    item {
                        Text("Usuario ${actual.datos.post.userId}", color = MaterialTheme.colorScheme.primary,
                            style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.Bold)
                        Text(actual.datos.post.title, style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 6.dp, bottom = 10.dp))
                        Text(actual.datos.post.body, style = MaterialTheme.typography.bodyLarge)
                        Text("Comentarios (${actual.datos.comentarios.size})",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(top = 20.dp, bottom = 4.dp))
                    }
                    items(actual.datos.comentarios, key = { it.id }) { comentario ->
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                        ) {
                            Column(Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                                Text(comentario.name, fontWeight = FontWeight.SemiBold)
                                Text(comentario.email, color = MaterialTheme.colorScheme.primary,
                                    style = MaterialTheme.typography.labelMedium)
                                Text(comentario.body, style = MaterialTheme.typography.bodySmall)
                            }
                        }
                    }
                }
            }
        }
    }
}
