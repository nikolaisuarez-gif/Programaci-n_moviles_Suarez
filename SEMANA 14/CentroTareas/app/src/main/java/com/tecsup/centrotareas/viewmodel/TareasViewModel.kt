package com.tecsup.centrotareas.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tecsup.centrotareas.data.TareasRepository
import com.tecsup.centrotareas.model.CargaUi
import com.tecsup.centrotareas.model.PrimosUi
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class TareasViewModel(private val repo: TareasRepository) : ViewModel() {
    private val _mensaje = MutableStateFlow("Listo")
    val mensaje: StateFlow<String> = _mensaje.asStateFlow()

    private val _progreso = MutableStateFlow(0f)
    val progreso: StateFlow<Float> = _progreso.asStateFlow()

    private var trabajo: Job? = null
    private val _corriendo = MutableStateFlow(false)
    val corriendo: StateFlow<Boolean> = _corriendo.asStateFlow()

    fun tareaLarga() {
        if (_corriendo.value) return

        _mensaje.value = "Trabajando..."
        _progreso.value = 0f
        _corriendo.value = true
        trabajo = viewModelScope.launch {
            try {
                Log.d("HILO", "Antes: ${Thread.currentThread().name}")
                repo.progresoTareaLarga().collect { avance ->
                    _progreso.value = avance
                }
                Log.d("HILO", "Después: ${Thread.currentThread().name}")
                _mensaje.value = "Terminó"
            } finally {
                _corriendo.value = false
                trabajo = null
            }
        }
    }

    fun cancelar() {
        trabajo?.cancel()
        _mensaje.value = "Cancelado"
    }

    private val _primos = MutableStateFlow<PrimosUi>(PrimosUi.Inicial)
    val primos: StateFlow<PrimosUi> = _primos.asStateFlow()
    private val _progresoPrimos = MutableStateFlow(0f)
    val progresoPrimos: StateFlow<Float> = _progresoPrimos.asStateFlow()
    private var trabajoPrimos: Job? = null

    fun contar(hasta: Int) {
        if (hasta < 2 || _primos.value == PrimosUi.Calculando) return
        _primos.value = PrimosUi.Calculando
        _progresoPrimos.value = 0f
        trabajoPrimos = viewModelScope.launch {
            val inicio = System.currentTimeMillis()
            try {
                var cantidad = 0
                repo.contarPrimosConProgreso(hasta).collect { avance ->
                    _progresoPrimos.value = avance.porcentaje
                    avance.cantidadFinal?.let { cantidad = it }
                }
                _primos.value = PrimosUi.Listo(cantidad, System.currentTimeMillis() - inicio)
            } catch (e: CancellationException) {
                _primos.value = PrimosUi.Cancelado
                throw e
            } finally {
                trabajoPrimos = null
            }
        }
    }

    fun cancelarPrimos() {
        trabajoPrimos?.cancel()
    }

    private val _carga = MutableStateFlow<CargaUi>(CargaUi.Inicial)
    val carga: StateFlow<CargaUi> = _carga.asStateFlow()
    private val _simularError = MutableStateFlow(false)
    val simularError: StateFlow<Boolean> = _simularError.asStateFlow()
    private var trabajoCarga: Job? = null

    fun cambiarSimularError(valor: Boolean) {
        _simularError.value = valor
    }

    fun cargarUnaPorUna() = iniciarCarga("Una por una", paralelo = false)

    fun cargarALaVez() = iniciarCarga("A la vez", paralelo = true)

    private fun iniciarCarga(modo: String, paralelo: Boolean) {
        if (_carga.value == CargaUi.Cargando) return
        _carga.value = CargaUi.Cargando
        val simularErrorAhora = _simularError.value
        trabajoCarga = viewModelScope.launch {
            val inicio = System.currentTimeMillis()
            try {
                val (perfil, pedidos) = if (paralelo) {
                    coroutineScope {
                        val perfilAsync = async { repo.cargarPerfil() }
                        val pedidosAsync = async { repo.cargarPedidos(simularError = simularErrorAhora) }
                        perfilAsync.await() to pedidosAsync.await()
                    }
                } else {
                    repo.cargarPerfil() to repo.cargarPedidos(simularError = simularErrorAhora)
                }
                _carga.value = CargaUi.Exito(
                    modo = modo,
                    perfil = perfil,
                    pedidos = pedidos.size,
                    milisegundos = System.currentTimeMillis() - inicio
                )
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                _carga.value = CargaUi.Error("No se pudo cargar: ${e.message}")
            } finally {
                trabajoCarga = null
            }
        }
    }
}
