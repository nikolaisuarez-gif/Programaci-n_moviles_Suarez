package com.tecsup.cafecampus.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tecsup.cafecampus.data.local.entity.Producto
import com.tecsup.cafecampus.data.repository.CafeRepository
import com.tecsup.cafecampus.model.ItemCarrito
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

class MenuViewModel(private val repo: CafeRepository) : ViewModel() {

    private val _busqueda = MutableStateFlow("")
    val busqueda: StateFlow<String> = _busqueda.asStateFlow()

    private val _categoriaSeleccionada = MutableStateFlow("Todo")
    val categoriaSeleccionada: StateFlow<String> = _categoriaSeleccionada.asStateFlow()

    private val _carrito = MutableStateFlow<List<ItemCarrito>>(emptyList())
    val carrito: StateFlow<List<ItemCarrito>> = _carrito.asStateFlow()

    val productos: StateFlow<List<Producto>> = combine(
        repo.observarProductos(),
        _busqueda,
        _categoriaSeleccionada
    ) { lista, query, cat ->
        lista.filter { p ->
            val coincideCat = cat == "Todo" || p.categoria.equals(cat, ignoreCase = true)
            val coincideQuery = query.isEmpty() || p.nombre.contains(query, ignoreCase = true) || p.descripcion.contains(query, ignoreCase = true)
            coincideCat && coincideQuery
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    fun onBusquedaChange(nueva: String) {
        _busqueda.value = nueva
    }

    fun seleccionarCategoria(categoria: String) {
        _categoriaSeleccionada.value = categoria
    }

    fun agregarAlCarrito(producto: Producto, cantidad: Int = 1) {
        val actual = _carrito.value.toMutableList()
        val index = actual.indexOfFirst { it.producto.id == producto.id }
        if (index >= 0) {
            val item = actual[index]
            actual[index] = item.copy(cantidad = item.cantidad + cantidad)
        } else {
            actual.add(ItemCarrito(producto, cantidad))
        }
        _carrito.value = actual
    }

    fun quitarDelCarrito(producto: Producto) {
        _carrito.value = _carrito.value.filter { it.producto.id != producto.id }
    }

    fun modificarCantidad(productoId: Int, nuevaCantidad: Int) {
        if (nuevaCantidad <= 0) {
            _carrito.value = _carrito.value.filter { it.producto.id != productoId }
        } else {
            _carrito.value = _carrito.value.map {
                if (it.producto.id == productoId) it.copy(cantidad = nuevaCantidad) else it
            }
        }
    }

    fun limpiarCarrito() {
        _carrito.value = emptyList()
    }

    val totalCarrito: Double
        get() = _carrito.value.sumOf { it.producto.precio * it.cantidad }

    val cantidadTotalItems: Int
        get() = _carrito.value.sumOf { it.cantidad }
}
