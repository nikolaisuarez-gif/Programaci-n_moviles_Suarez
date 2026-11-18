package com.tecsup.cafecampus.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tecsup.cafecampus.model.EstadoPedido
import com.tecsup.cafecampus.ui.theme.StatusCancelado
import com.tecsup.cafecampus.ui.theme.StatusEntregado
import com.tecsup.cafecampus.ui.theme.StatusListo
import com.tecsup.cafecampus.ui.theme.StatusPreparando
import com.tecsup.cafecampus.ui.theme.StatusRecibido

@Composable
fun EstadoChip(
    estadoStr: String,
    modifier: Modifier = Modifier
) {
    val estado = try {
        EstadoPedido.valueOf(estadoStr)
    } catch (e: Exception) {
        EstadoPedido.RECIBIDO
    }

    val (bgColor, textColor, label) = when (estado) {
        EstadoPedido.RECIBIDO -> Triple(StatusRecibido.copy(alpha = 0.15f), StatusRecibido, "Recibido")
        EstadoPedido.PREPARANDO -> Triple(StatusPreparando.copy(alpha = 0.15f), StatusPreparando, "Preparando")
        EstadoPedido.LISTO -> Triple(StatusListo.copy(alpha = 0.15f), StatusListo, "Listo")
        EstadoPedido.ENTREGADO -> Triple(StatusEntregado.copy(alpha = 0.15f), StatusEntregado, "Entregado")
        EstadoPedido.CANCELADO -> Triple(StatusCancelado.copy(alpha = 0.15f), StatusCancelado, "Cancelado")
    }

    Box(
        modifier = modifier
            .background(color = bgColor, shape = RoundedCornerShape(16.dp))
            .padding(horizontal = 10.dp, vertical = 4.dp)
    ) {
        Text(
            text = label,
            color = textColor,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold
        )
    }
}
