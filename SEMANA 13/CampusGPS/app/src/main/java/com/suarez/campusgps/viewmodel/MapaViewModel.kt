package com.suarez.campusgps.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.suarez.campusgps.data.UbicacionRepository
import com.suarez.campusgps.model.UbicacionUi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MapaViewModel(private val ubicacionRepo: UbicacionRepository) : ViewModel() {
    private val _estado = MutableStateFlow<UbicacionUi>(UbicacionUi.Inicial)
    val estado = _estado.asStateFlow()

    fun buscar() {
        viewModelScope.launch {
            _estado.value = UbicacionUi.Buscando
            ubicacionRepo.obtenerPosicion().onSuccess { _estado.value = UbicacionUi.Lista(it) }
                .onFailure { _estado.value = UbicacionUi.Error(it.message ?: "No se pudo obtener la ubicación") }
        }
    }

    fun permisoDenegado() {
        _estado.value = UbicacionUi.Error("Sin permiso de ubicación no puedo mostrar dónde estás")
    }

    class Factory(private val repo: UbicacionRepository) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T = MapaViewModel(repo) as T
    }
}
