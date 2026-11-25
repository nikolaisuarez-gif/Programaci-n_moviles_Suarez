package com.tecsup.centrotareas.data

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext

class TareasRepositoryLocal : TareasRepository {
    override fun progresoTareaLarga(): Flow<Float> = flow {
        Log.d("HILO", "Dentro del Repository: ${Thread.currentThread().name}")
        repeat(100) { indice ->
            delay(40)
            emit((indice + 1) / 100f)
        }
    }.flowOn(Dispatchers.Default)

    override suspend fun cargarPerfil(): String {
        delay(2_000)
        return "Ana Torres (ficticia)"
    }

    override suspend fun cargarPedidos(simularError: Boolean): List<String> {
        delay(3_000)
        if (simularError) throw java.io.IOException("Sin conexión")
        return listOf("Pedido 101", "Pedido 102", "Pedido 103")
    }

    override suspend fun contarPrimos(hasta: Int): Int = withContext(Dispatchers.Default) {
        var cantidad = 0
        for (numero in 2..hasta) {
            currentCoroutineContext().ensureActive()
            if (esPrimo(numero)) cantidad++
        }
        cantidad
    }

    override fun contarPrimosConProgreso(hasta: Int): Flow<AvancePrimos> = flow {
        var cantidad = 0
        val total = (hasta - 1).coerceAtLeast(1)
        val intervalo = (total / 100).coerceAtLeast(1)
        for (numero in 2..hasta) {
            currentCoroutineContext().ensureActive()
            if (esPrimo(numero)) cantidad++
            if ((numero - 1) % intervalo == 0 || numero == hasta) {
                emit(AvancePrimos((numero - 1).toFloat() / total))
            }
        }
        emit(AvancePrimos(1f, cantidad))
    }.flowOn(Dispatchers.Default)

    private fun esPrimo(numero: Int): Boolean {
        if (numero < 2) return false
        var divisor = 2
        while (divisor * divisor <= numero) {
            if (numero % divisor == 0) return false
            divisor++
        }
        return true
    }
}
