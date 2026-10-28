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

    fun repetirPedido(detalles: List<com.tecsup.cafecampus.data.local.entity.DetallePedido>) {
        val productosExistentes = productos.value
        detalles.forEach { detalle ->
            val producto = productosExistentes.find { it.id == detalle.productoId }
                ?: Producto(
                    id = detalle.productoId,
                    nombre = detalle.nombreProducto,
                    precio = detalle.precioUnitario,
                    categoria = "General",
                    descripcion = "",
                    icono = "plato"
                )
            agregarAlCarrito(producto, detalle.cantidad)
        }
    }

    val totalCarrito: Double
        get() {
            val items = _carrito.value
            var baseTotal = items.sumOf { it.producto.precio * it.cantidad }

            // Aplica la promo si se compran juntos Chicha Morada y Empanada (S/ 5.50 el par en vez de S/ 8.00)
            val cantidadChicha = items.filter {
                it.producto.nombre.lowercase().contains("chicha") && !it.producto.nombre.lowercase().contains("combo")
            }.sumOf { it.cantidad }

            val cantidadEmpanada = items.filter {
                it.producto.nombre.lowercase().contains("empanada") && !it.producto.nombre.lowercase().contains("combo")
            }.sumOf { it.cantidad }

            val parejasPromo = minOf(cantidadChicha, cantidadEmpanada)
            if (parejasPromo > 0) {
                val descuentoPareja = parejasPromo * 2.50
                baseTotal -= descuentoPareja
            }

            return maxOf(0.0, baseTotal)
        }

    val cantidadTotalItems: Int
        get() = _carrito.value.sumOf { it.cantidad }
}
