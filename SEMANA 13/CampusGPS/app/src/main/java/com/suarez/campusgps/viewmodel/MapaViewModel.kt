package com.suarez.campusgps.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.suarez.campusgps.data.ErrorUbicacion
import com.suarez.campusgps.data.LugaresRepository
import com.suarez.campusgps.data.UbicacionRepository
import com.suarez.campusgps.model.Lugar
import com.suarez.campusgps.model.LugarConDistancia
import com.suarez.campusgps.model.TipoError
import com.suarez.campusgps.model.UbicacionUi
import com.suarez.campusgps.util.distanciaMetros
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MapaViewModel(
    private val ubicacionRepo: UbicacionRepository,
    private val lugaresRepo: LugaresRepository
) : ViewModel() {
    private val _estado = MutableStateFlow<UbicacionUi>(UbicacionUi.Inicial)
    val estado: StateFlow<UbicacionUi> = _estado.asStateFlow()

    private val _todosLosLugares = MutableStateFlow(lugaresRepo.obtenerLugares().map { LugarConDistancia(it, null) })
    private val _categoria = MutableStateFlow<String?>(null)
    val categoria: StateFlow<String?> = _categoria.asStateFlow()
    val categorias: List<String> = listOf("Todas") + lugaresRepo.obtenerLugares().map { it.categoria }.distinct().sorted()
    val lugares: StateFlow<List<LugarConDistancia>> = combine(_todosLosLugares, _categoria) { lista, categoria ->
        if (categoria == null) lista else lista.filter { it.lugar.categoria == categoria }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), _todosLosLugares.value)

    private val _seleccionado = MutableStateFlow<Int?>(null)
    val seleccionado: StateFlow<Int?> = _seleccionado.asStateFlow()

    val masCercano: StateFlow<Lugar?> = lugares
        .map { lista -> lista.firstOrNull { it.metros != null }?.lugar }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), null)

    fun buscar() {
        viewModelScope.launch {
            _estado.value = UbicacionUi.Buscando
            ubicacionRepo.obtenerPosicion()
                .onSuccess { posicion ->
                    actualizarDistancias(posicion)
                    _estado.value = UbicacionUi.Lista(posicion)
                }
                .onFailure { error ->
                    val tipo = (error as? ErrorUbicacion)?.tipo ?: TipoError.OTRO
                    _estado.value = UbicacionUi.Error(
                        error.message ?: "No se pudo obtener la ubicación.", tipo
                    )
                }
        }
    }

    fun permisoDenegado() {
        _estado.value = UbicacionUi.Error(
            "Sin permiso de ubicación no puedo mostrar dónde estás", TipoError.SIN_PERMISO
        )
    }

    fun seleccionar(id: Int?) { _seleccionado.value = id }
    fun filtrarCategoria(categoria: String?) { _categoria.value = categoria }

    private fun actualizarDistancias(pos: com.suarez.campusgps.model.Posicion) {
        _todosLosLugares.value = lugaresRepo.obtenerLugares()
            .map { LugarConDistancia(it, distanciaMetros(pos, it)) }
            .sortedBy { it.metros }
    }

    class Factory(
        private val ubicacionRepo: UbicacionRepository,
        private val lugaresRepo: LugaresRepository
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (!modelClass.isAssignableFrom(MapaViewModel::class.java)) error("ViewModel desconocido")
            return MapaViewModel(ubicacionRepo, lugaresRepo) as T
        }
    }
}
