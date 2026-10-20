package com.tecsup.cafecampus.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tecsup.cafecampus.data.local.relation.PedidoConDetalle
import com.tecsup.cafecampus.data.repository.CafeRepository
import com.tecsup.cafecampus.model.EstadoPedido
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class PedidosAdminViewModel(private val repo: CafeRepository) : ViewModel() {

    private val _estadoFiltro = MutableStateFlow(EstadoPedido.RECIBIDO)
    val estadoFiltro: StateFlow<EstadoPedido> = _estadoFiltro.asStateFlow()

    @OptIn(ExperimentalCoroutinesApi::class)
    val pedidosPorEstado: StateFlow<List<PedidoConDetalle>> = _estadoFiltro
        .flatMapLatest { repo.observarPedidosPorEstado(it) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun seleccionarEstado(estado: EstadoPedido) {
        _estadoFiltro.value = estado
    }

    fun cambiarEstado(pedidoId: Int, nuevoEstado: EstadoPedido) {
        viewModelScope.launch {
            repo.cambiarEstado(pedidoId, nuevoEstado)
        }
    }

    fun observarPedido(pedidoId: Int) = repo.observarPedido(pedidoId)
}
