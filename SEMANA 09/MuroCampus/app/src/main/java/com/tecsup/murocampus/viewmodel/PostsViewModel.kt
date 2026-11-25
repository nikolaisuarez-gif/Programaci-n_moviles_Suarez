package com.tecsup.murocampus.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tecsup.murocampus.data.PostRepository
import com.tecsup.murocampus.model.Comentario
import com.tecsup.murocampus.model.NuevoPost
import com.tecsup.murocampus.model.Post
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class DetalleUi(val post: Post, val comentarios: List<Comentario>)

class PostsViewModel(private val repo: PostRepository) : ViewModel() {
    private val _lista = MutableStateFlow<UiState<List<Post>>>(UiState.Cargando)
    val lista = _lista.asStateFlow()

    private val _detalle = MutableStateFlow<UiState<DetalleUi>>(UiState.Cargando)
    val detalle = _detalle.asStateFlow()

    private val _publicacion = MutableStateFlow<UiState<Post>?>(null)
    val publicacion = _publicacion.asStateFlow()

    private val _eliminacion = MutableStateFlow<UiState<Unit>?>(null)
    val eliminacion = _eliminacion.asStateFlow()

    private val _usuarioFiltro = MutableStateFlow<Int?>(null)
    val usuarioFiltro = _usuarioFiltro.asStateFlow()

    init { cargar() }

    fun cargar() {
        viewModelScope.launch {
            _lista.value = UiState.Cargando
            val resultado = _usuarioFiltro.value?.let(repo::porUsuario) ?: repo.listar()
            _lista.value = resultado.fold(
                onSuccess = { UiState.Exito(it) },
                onFailure = { UiState.Error(mensajeDe(it)) }
            )
        }
    }

    fun filtrar(userId: Int?) {
        if (_usuarioFiltro.value == userId) return
        _usuarioFiltro.value = userId
        cargar()
    }

    fun abrir(id: Int) {
        viewModelScope.launch {
            _detalle.value = UiState.Cargando
            _eliminacion.value = null
            val resultado = coroutineScope {
                val post = async { repo.detalle(id) }
                val comentarios = async { repo.comentarios(id) }
                val postResult = post.await()
                val comentariosResult = comentarios.await()
                postResult.fold(
                    onSuccess = { datos ->
                        comentariosResult.fold(
                            onSuccess = { Result.success(DetalleUi(datos, it)) },
                            onFailure = { Result.failure(it) }
                        )
                    },
                    onFailure = { Result.failure(it) }
                )
            }
            _detalle.value = resultado.fold(
                onSuccess = { UiState.Exito(it) },
                onFailure = { UiState.Error(mensajeDe(it)) }
            )
        }
    }

    fun reintentarDetalle(id: Int) = abrir(id)

    fun borrarPublicacion(id: Int) {
        viewModelScope.launch {
            _eliminacion.value = UiState.Cargando
            _eliminacion.value = repo.borrar(id).fold(
                onSuccess = { UiState.Exito(Unit) },
                onFailure = { UiState.Error(mensajeDe(it)) }
            )
        }
    }

    fun reintentarBorrado(id: Int) = borrarPublicacion(id)

    fun publicar(titulo: String, texto: String) {
        if (titulo.isBlank() || texto.isBlank()) return
        viewModelScope.launch {
            _publicacion.value = UiState.Cargando
            _publicacion.value = repo.crear(NuevoPost(userId = 1, title = titulo.trim(), body = texto.trim()))
                .fold(
                    onSuccess = { UiState.Exito(it) },
                    onFailure = { UiState.Error(mensajeDe(it)) }
                )
        }
    }

    fun limpiarResultadoPublicacion() { _publicacion.value = null }
}
