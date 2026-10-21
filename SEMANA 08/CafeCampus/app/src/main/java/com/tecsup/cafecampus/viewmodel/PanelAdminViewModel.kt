package com.tecsup.cafecampus.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tecsup.cafecampus.data.local.relation.PedidoConDetalle
import com.tecsup.cafecampus.data.repository.CafeRepository
import com.tecsup.cafecampus.model.EstadoPedido
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import java.util.Calendar

class PanelAdminViewModel(private val repo: CafeRepository) : ViewModel() {

    private val inicioDia: Long
        get() {
            val cal = Calendar.getInstance()
            cal.set(Calendar.HOUR_OF_DAY, 0)
            cal.set(Calendar.MINUTE, 0)
            cal.set(Calendar.SECOND, 0)
            cal.set(Calendar.MILLISECOND, 0)
            return cal.timeInMillis
        }

    val ventasHoy: StateFlow<Double> = repo.observarVentasHoy(inicioDia)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = 0.0
        )

    val recibidosCount: StateFlow<Int> = repo.observarCantidadPorEstado(EstadoPedido.RECIBIDO)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = 0
        )

    val preparandoCount: StateFlow<Int> = repo.observarCantidadPorEstado(EstadoPedido.PREPARANDO)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = 0
        )

    val listoCount: StateFlow<Int> = repo.observarCantidadPorEstado(EstadoPedido.LISTO)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = 0
        )

    val pedidosRecientes: StateFlow<List<PedidoConDetalle>> = repo.observarPedidosPorEstado(EstadoPedido.RECIBIDO)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    val pedidosPorHora: StateFlow<List<Pair<String, Int>>> = repo.observarPedidosHoy(inicioDia)
        .map { listaPedidos ->
            val horasBase = listOf(9, 10, 11, 12, 13, 14, 15, 16)
            val cal = Calendar.getInstance()
            val conteoMap = horasBase.associateWith { 0 }.toMutableMap()

            listaPedidos.forEach { p ->
                cal.timeInMillis = p.fecha
                val h = cal.get(Calendar.HOUR_OF_DAY)
                if (conteoMap.containsKey(h)) {
                    conteoMap[h] = (conteoMap[h] ?: 0) + 1
                } else if (h < 9) {
                    conteoMap[9] = (conteoMap[9] ?: 0) + 1
                } else if (h > 16) {
                    conteoMap[16] = (conteoMap[16] ?: 0) + 1
                }
            }

            horasBase.map { h -> "${h}h" to (conteoMap[h] ?: 0) }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = listOf("9h" to 0, "10h" to 0, "11h" to 0, "12h" to 0, "13h" to 0, "14h" to 0, "15h" to 0, "16h" to 0)
        )
}
