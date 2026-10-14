package com.tecsup.cafecampus.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tecsup.cafecampus.data.local.entity.Producto
import com.tecsup.cafecampus.data.repository.CafeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MenuAdminViewModel(private val repo: CafeRepository) : ViewModel() {

    private val _categoriaFiltro = MutableStateFlow("Todo")
    val categoriaFiltro: StateFlow<String> = _categoriaFiltro.asStateFlow()

    val productos: StateFlow<List<Producto>> = combine(
        repo.observarProductos(),
        _categoriaFiltro
    ) { lista, cat ->
        if (cat == "Todo") lista else lista.filter { it.categoria.equals(cat, ignoreCase = true) }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubsubscribed(5000),
        initialValue = emptyList()
    )

    fun seleccionarCategoria(cat: String) {
        _categoriaFiltro.value = cat
    }

    fun guardarProducto(p: Producto, onResult: () -> Unit) {
        viewModelScope.launch {
            repo.guardarProducto(p)
            onResult()
        }
    }

    fun eliminarProducto(p: Producto) {
        viewModelScope.launch {
            repo.eliminarProducto(p)
        }
    }

    fun cambiarDisponibilidad(id: Int, disponible: Boolean) {
        viewModelScope.launch {
            repo.cambiarDisponibilidad(id, disponible)
        }
    }
}
