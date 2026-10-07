package com.tecsup.mibodega.ui.cliente.screens.entrega

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.tecsup.mibodega.ui.componentes.BotonPrimario
import com.tecsup.mibodega.ui.componentes.CampoTexto
import com.tecsup.mibodega.ui.theme.VerdeBodega

/**
 * Pantalla 6: Dirección y Pago (Datos de Entrega).
 * Formulario para ingresar dirección de entrega y seleccionar método de pago.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatosEntregaScreen(
    subtotal: Double = 0.0,
    delivery: Double = 4.00,
    onVolver: () -> Unit,
    onConfirmarPedido: (nombre: String, telefono: String, direccion: String, referencia: String, metodoPago: String) -> Unit
) {
    var nombre by remember { mutableStateOf("Juan Pérez") }
    var telefono by remember { mutableStateOf("987 654 321") }
    var direccion by remember { mutableStateOf("Av. Los Olivos 123") }
    var referencia by remember { mutableStateOf("Frente al parque") }

    val metodosPago = listOf("Efectivo al entregar", "Yape", "Plin", "Tarjeta de Crédito / Débito")
    var metodoPagoSeleccionado by remember { mutableStateOf(metodosPago[0]) }

    val totalCalculado = subtotal + delivery

    Column(
        modifier = Modifier
            .fillMaxSize()
            .safeDrawingPadding()
    ) {
        TopAppBar(
            title = { Text("Datos de entrega", fontWeight = FontWeight.Bold) },
            navigationIcon = {
                IconButton(onClick = onVolver) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                }
            }
        )

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 8.dp)
        ) {
            CampoTexto(
                etiqueta = "Nombre",
                valor = nombre,
                onValorCambia = { nombre = it },
                placeholder = "Juan Pérez"
            )

            Spacer(Modifier.height(14.dp))

            CampoTexto(
                etiqueta = "Teléfono",
                valor = telefono,
                onValorCambia = { telefono = it },
                placeholder = "987 654 321"
            )

            Spacer(Modifier.height(14.dp))

            CampoTexto(
                etiqueta = "Dirección",
                valor = direccion,
                onValorCambia = { direccion = it },
                placeholder = "Av. Los Olivos 123"
            )

            Spacer(Modifier.height(14.dp))

            CampoTexto(
                etiqueta = "Referencia",
                valor = referencia,
                onValorCambia = { referencia = it },
                placeholder = "Frente al parque"
            )

            Spacer(Modifier.height(20.dp))

            Text(
                text = "Método de pago",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            metodosPago.forEach { metodo ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { metodoPagoSeleccionado = metodo }
                        .padding(vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = (metodo == metodoPagoSeleccionado),
                        onClick = { metodoPagoSeleccionado = metodo },
                        colors = RadioButtonDefaults.colors(selectedColor = VerdeBodega)
                    )
                    Text(
                        text = metodo,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }
            }

            Spacer(Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Total a pagar:", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                Text("S/ %.2f".format(totalCalculado), style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = VerdeBodega)
            }

            Spacer(Modifier.height(20.dp))

            BotonPrimario(
                texto = "Confirmar pedido",
                onClick = {
                    onConfirmarPedido(nombre, telefono, direccion, referencia, metodoPagoSeleccionado)
                }
            )

            Spacer(Modifier.height(16.dp))
        }
    }
}
