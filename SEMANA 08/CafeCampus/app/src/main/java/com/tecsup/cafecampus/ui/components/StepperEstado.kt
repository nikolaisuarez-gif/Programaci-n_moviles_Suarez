package com.tecsup.cafecampus.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tecsup.cafecampus.model.EstadoPedido

@Composable
fun StepperEstado(
    estadoActual: EstadoPedido,
    modifier: Modifier = Modifier
) {
    val pasos = listOf(
        Pair(EstadoPedido.RECIBIDO, "Recibido" to "Tu pedido llegó a la cocina"),
        Pair(EstadoPedido.PREPARANDO, "Preparando" to "Estamos cocinando tu pedido"),
        Pair(EstadoPedido.LISTO, "Listo" to "Puedes pasar a recogerlo"),
        Pair(EstadoPedido.ENTREGADO, "Entregado" to "¡Buen provecho!")
    )

    val orden = listOf(
        EstadoPedido.RECIBIDO,
        EstadoPedido.PREPARANDO,
        EstadoPedido.LISTO,
        EstadoPedido.ENTREGADO
    )

    val indiceActual = orden.indexOf(estadoActual)

    Column(modifier = modifier.fillMaxWidth()) {
        pasos.forEachIndexed { index, (estado, textos) ->
            val estaCompletado = indiceActual >= index
            val esActual = indiceActual == index

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(28.dp)
                        .clip(CircleShape)
                        .background(
                            if (estaCompletado) MaterialTheme.colorScheme.primary
                            else MaterialTheme.colorScheme.outlineVariant
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    if (estaCompletado) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.width(12.dp))

                Column {
                    Text(
                        text = textos.first,
                        fontWeight = if (esActual) FontWeight.Bold else FontWeight.Medium,
                        fontSize = 15.sp,
                        color = if (estaCompletado) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.outline
                    )
                    Text(
                        text = textos.second,
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.outline
                    )
                }
            }

            if (index < pasos.size - 1) {
                Box(
                    modifier = Modifier
                        .padding(start = 13.dp)
                        .width(2.dp)
                        .height(20.dp)
                        .background(
                            if (indiceActual > index) MaterialTheme.colorScheme.primary
                            else MaterialTheme.colorScheme.outlineVariant
                        )
                )
            }
        }
    }
}
