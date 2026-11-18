package com.suarez.campusgps.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.suarez.campusgps.data.UbicacionRepository
import com.suarez.campusgps.data.LugaresRepository
import com.suarez.campusgps.model.LugarConDistancia
import com.suarez.campusgps.model.Posicion
import com.suarez.campusgps.util.distanciaMetros
import com.suarez.campusgps.model.UbicacionUi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MapaViewModel(private val ubicacionRepo: UbicacionRepository, lugaresRepo: LugaresRepository) : ViewModel() {
    private val _estado = MutableStateFlow<UbicacionUi>(UbicacionUi.Inicial)
    val estado = _estado.asStateFlow()
    private val _lugares = MutableStateFlow(lugaresRepo.obtenerLugares().map { LugarConDistancia(it, null) })
    val lugares = _lugares.asStateFlow()

    fun buscar() {
        viewModelScope.launch {
            _estado.value = UbicacionUi.Buscando
            ubicacionRepo.obtenerPosicion().onSuccess {
                actualizarDistancias(it)
                _estado.value = UbicacionUi.Lista(it)
            }
                .onFailure { _estado.value = UbicacionUi.Error(it.message ?: "No se pudo obtener la ubicación") }
        }
    }

    fun permisoDenegado() {
        _estado.value = UbicacionUi.Error("Sin permiso de ubicación no puedo mostrar dónde estás")
    }

    private fun actualizarDistancias(posicion: Posicion) {
        _lugares.value = _lugares.value
            .map { item -> item.copy(metros = distanciaMetros(posicion, item.lugar)) }
            .sortedBy { it.metros }
    }

    class Factory(private val repo: UbicacionRepository, private val lugaresRepo: LugaresRepository) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T = MapaViewModel(repo, lugaresRepo) as T
    }
}
