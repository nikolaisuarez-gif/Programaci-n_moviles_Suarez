package com.tecsup.cafecampus.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tecsup.cafecampus.data.local.relation.PedidoConDetalle
import com.tecsup.cafecampus.data.repository.CafeRepository
import com.tecsup.cafecampus.model.ItemCarrito
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PedidosClienteViewModel(private val repo: CafeRepository) : ViewModel() {

    private val _horaRecojo = MutableStateFlow("Ahora")
    val horaRecojo: StateFlow<String> = _horaRecojo.asStateFlow()

    private val _pago = MutableStateFlow("CAJA")
    val pago: StateFlow<String> = _pago.asStateFlow()

    private val _nota = MutableStateFlow("")
    val nota: StateFlow<String> = _nota.asStateFlow()

    fun setHoraRecojo(hora: String) {
        _horaRecojo.value = hora
    }

    fun setPago(metodo: String) {
        _pago.value = metodo
    }

    fun setNota(texto: String) {
        _nota.value = texto
    }

    fun crearPedido(
        clientId: Int,
        items: List<ItemCarrito>,
        onSuccess: (Long) -> Unit
    ) {
        viewModelScope.launch {
            val id = repo.crearPedido(
                clientId = clientId,
                items = items,
                horaRecojo = _horaRecojo.value,
                pago = _pago.value,
                nota = _nota.value
            )
            onSuccess(id)
        }
    }

    fun observarPedidosCliente(clientId: Int): Flow<List<PedidoConDetalle>> {
        return repo.observarPedidosCliente(clientId)
    }

    fun observarPedido(pedidoId: Int): Flow<PedidoConDetalle?> {
        return repo.observarPedido(pedidoId)
    }
}
