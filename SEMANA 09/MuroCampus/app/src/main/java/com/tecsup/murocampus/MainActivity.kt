package com.tecsup.murocampus

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.tecsup.murocampus.data.PostRepositoryRemote
import com.tecsup.murocampus.data.remote.RetrofitClient
import com.tecsup.murocampus.ui.PantallaCrear
import com.tecsup.murocampus.ui.PantallaDetalle
import com.tecsup.murocampus.ui.PantallaLista
import com.tecsup.murocampus.ui.theme.MuroCampusTheme
import com.tecsup.murocampus.viewmodel.PostsViewModel
import com.tecsup.murocampus.viewmodel.PostsViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MuroCampusTheme {
                val factory = remember { PostsViewModelFactory(PostRepositoryRemote(RetrofitClient.api)) }
                val postsViewModel: PostsViewModel = viewModel(factory = factory)
                var pantalla by rememberSaveable { mutableStateOf("lista") }
                var postSeleccionado by rememberSaveable { mutableIntStateOf(1) }

                when (pantalla) {
                    "detalle" -> PantallaDetalle(postSeleccionado, postsViewModel) { pantalla = "lista" }
                    "crear" -> PantallaCrear(postsViewModel) {
                        postsViewModel.limpiarResultadoPublicacion()
                        pantalla = "lista"
                    }
                    else -> PantallaLista(
                        viewModel = postsViewModel,
                        onSeleccionar = { id ->
                            postSeleccionado = id
                            postsViewModel.abrir(id)
                            pantalla = "detalle"
                        },
                        onCrear = {
                            postsViewModel.limpiarResultadoPublicacion()
                            pantalla = "crear"
                        }
                    )
                }
            }
        }
    }
}
