package com.tecsup.cafecampus.ui.cliente

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ConfirmationNumber
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocalShipping
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tecsup.cafecampus.data.prefs.PreferenciasStore
import com.tecsup.cafecampus.ui.components.AppTopBar
import com.tecsup.cafecampus.ui.components.CafeButton
import com.tecsup.cafecampus.ui.components.ProductoIcono
import com.tecsup.cafecampus.viewmodel.MenuViewModel
import com.tecsup.cafecampus.viewmodel.PedidosClienteViewModel
import kotlinx.coroutines.launch

@Composable
fun ConfirmarPedidoScreen(
    clienteId: Int,
    menuVm: MenuViewModel,
    pedidosVm: PedidosClienteViewModel,
    preferenciasStore: PreferenciasStore,
    onBackClick: () -> Unit,
    onPedidoConfirmado: (Long) -> Unit
) {
    val carrito by menuVm.carrito.collectAsState()
    val horaRecojo by pedidosVm.horaRecojo.collectAsState()
    val pago by pedidosVm.pago.collectAsState()
    val nota by pedidosVm.nota.collectAsState()
    val cuponUsado by preferenciasStore.cuponProfeApruebemeUsado(clienteId).collectAsState(initial = false)
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    val horasDisponibles = listOf("Ahora", "11:30", "12:00", "12:30")
    val metodosPago = listOf("CAJA" to "Pagar en caja", "YAPE" to "Yape / Plin")
    val opcionesDelivery = listOf(
        "RECOJO" to "Recojo en local (S/ 0.00)",
        "CERCANO" to "Mismo / Distrito vecino (+S/ 3.00)",
        "LEJANO" to "Distrito no vecino (+S/ 10.00)"
    )

    var tipoDelivery by remember { mutableStateOf("RECOJO") }
    var incluirBolsa by remember { mutableStateOf(false) }
    var numeroYape by remember { mutableStateOf("923026303") }

    var codigoCuponTexto by remember { mutableStateOf("") }
    var cuponAplicado by remember { mutableStateOf(false) }
    var mensajeCupon by remember { mutableStateOf<String?>(null) }
    var esErrorCupon by remember { mutableStateOf(false) }

    val subtotal = menuVm.totalCarrito
    val descuentoCupon = if (cuponAplicado && !cuponUsado) subtotal * 0.30 else 0.0
    val costoBolsa = if (incluirBolsa) 0.40 else 0.00
    val costoDelivery = when (tipoDelivery) {
        "CERCANO" -> 3.00
        "LEJANO" -> 10.00
        else -> 0.00
    }
    val recargoYape = if (pago == "YAPE") 0.50 else 0.00
    val total = (subtotal - descuentoCupon + costoBolsa + costoDelivery + recargoYape).coerceAtLeast(0.0)

    Scaffold(
        topBar = {
            AppTopBar(
                title = "Confirmar pedido",
                onBackClick = onBackClick
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(20.dp)
        ) {
            // Card de Alerta de Tiempo Estimado de Recojo
            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(14.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onPrimaryContainer,
                        modifier = Modifier.size(28.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(
                            text = if (tipoDelivery == "RECOJO") "Tiempo estimado de recojo" else "Tiempo estimado de entrega",
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = if (horaRecojo == "Ahora") "Tu pedido estará listo en aprox. 15 a 20 min." else "Tu pedido estará listo a las $horaRecojo h.",
                            fontSize = 13.sp,
                            color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.9f)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Resumen de Productos
            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    if (carrito.isEmpty()) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 24.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.ShoppingCart,
                                contentDescription = null,
                                modifier = Modifier.size(48.dp),
                                tint = MaterialTheme.colorScheme.outline
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "No hay productos seleccionados",
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp,
                                color = MaterialTheme.colorScheme.error,
                                textAlign = TextAlign.Center
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "Agrega productos desde el menú para realizar un pedido.",
                                fontSize = 13.sp,
                                color = MaterialTheme.colorScheme.outline,
                                textAlign = TextAlign.Center
                            )
                        }
                    } else {
                        carrito.forEachIndexed { index, item ->
                            if (index > 0) {
                                HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
                            }
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 6.dp)
                            ) {
                                ProductoIcono(icono = item.producto.icono, nombre = item.producto.nombre, size = 40.dp)

                                Spacer(modifier = Modifier.width(10.dp))

                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = item.producto.nombre,
                                        fontWeight = FontWeight.SemiBold,
                                        fontSize = 15.sp
                                    )
                                    Text(
                                        text = "S/ %.2f c/u".format(item.producto.precio),
                                        fontSize = 12.sp,
                                        color = MaterialTheme.colorScheme.outline
                                    )
                                }

                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(2.dp)
                                ) {
                                    IconButton(
                                        onClick = { menuVm.modificarCantidad(item.producto.id, item.cantidad - 1) },
                                        modifier = Modifier.size(32.dp)
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Remove,
                                            contentDescription = "Disminuir cantidad",
                                            tint = MaterialTheme.colorScheme.primary
                                        )
                                    }

                                    Text(
                                        text = "${item.cantidad}",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 14.sp,
                                        modifier = Modifier.padding(horizontal = 4.dp)
                                    )

                                    IconButton(
                                        onClick = { menuVm.modificarCantidad(item.producto.id, item.cantidad + 1) },
                                        modifier = Modifier.size(32.dp)
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Add,
                                            contentDescription = "Aumentar cantidad",
                                            tint = MaterialTheme.colorScheme.primary
                                        )
                                    }
                                }

                                Spacer(modifier = Modifier.width(8.dp))

                                Text(
                                    text = "S/ %.2f".format(item.producto.precio * item.cantidad),
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Modalidad de Entrega (Delivery vs Recojo)
            Text(text = "Modalidad de entrega", fontWeight = FontWeight.Bold, fontSize = 16.sp)
            Column(modifier = Modifier.padding(vertical = 8.dp)) {
                opcionesDelivery.forEach { (clave, label) ->
                    FilterChip(
                        selected = tipoDelivery == clave,
                        onClick = { tipoDelivery = clave },
                        label = { Text(label) },
                        leadingIcon = {
                            Icon(imageVector = Icons.Default.LocalShipping, contentDescription = null, modifier = Modifier.size(18.dp))
                        },
                        modifier = Modifier.padding(bottom = 6.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Hora de recojo o entrega
            Text(text = "Hora aproximada", fontWeight = FontWeight.Bold, fontSize = 16.sp)
            Row(modifier = Modifier.padding(vertical = 8.dp)) {
                horasDisponibles.forEach { h ->
                    FilterChip(
                        selected = horaRecojo == h,
                        onClick = { pedidosVm.setHoraRecojo(h) },
                        label = { Text(h) },
                        modifier = Modifier.padding(end = 8.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Forma de pago
            Text(text = "Forma de pago", fontWeight = FontWeight.Bold, fontSize = 16.sp)
            Row(modifier = Modifier.padding(vertical = 8.dp)) {
                metodosPago.forEach { (clave, label) ->
                    FilterChip(
                        selected = pago == clave,
                        onClick = { pedidosVm.setPago(clave) },
                        label = { Text(label) },
                        modifier = Modifier.padding(end = 8.dp)
                    )
                }
            }

            if (pago == "YAPE") {
                val yapeUrl = "https://qr.yape.pe/pay?phone=$numeroYape&amount=%.2f&note=Pedido%%20CafeCampus".format(total)

                Spacer(modifier = Modifier.height(8.dp))

                Card(
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF6B127C)),
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Text(
                            text = "💜 Pago con Yape / Plin",
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            color = Color.White
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = "Monto exacto a pagar: S/ %.2f".format(total),
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            color = Color(0xFFFFD700)
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        Card(
                            colors = CardDefaults.cardColors(containerColor = Color.White),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier.padding(8.dp)
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier.padding(16.dp)
                            ) {
                                com.tecsup.cafecampus.ui.components.QrCodeImage(
                                    contenido = yapeUrl,
                                    modifier = Modifier.size(180.dp)
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = "Escanea el QR con tu app de Yape",
                                    fontSize = 12.sp,
                                    color = Color.Black,
                                    fontWeight = FontWeight.Medium
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "Número Yape: $numeroYape",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Text(
                            text = "Titular: CafeCampus",
                            fontSize = 12.sp,
                            color = Color.White.copy(alpha = 0.8f)
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        OutlinedTextField(
                            value = numeroYape,
                            onValueChange = { if (it.length <= 9) numeroYape = it },
                            label = { Text("Número de destino Yape", color = Color.White.copy(alpha = 0.8f)) },
                            singleLine = true,
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedTextColor = Color.White,
                                unfocusedTextColor = Color.White,
                                focusedBorderColor = Color.White,
                                unfocusedBorderColor = Color.White.copy(alpha = 0.6f)
                            ),
                            modifier = Modifier.fillMaxWidth(0.9f)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Cupón de descuento
            Text(text = "Cupón de descuento", fontWeight = FontWeight.Bold, fontSize = 16.sp)
            Spacer(modifier = Modifier.height(6.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedTextField(
                    value = codigoCuponTexto,
                    onValueChange = {
                        codigoCuponTexto = it
                        mensajeCupon = null
                    },
                    placeholder = { Text("Ej: profe_apruebeme") },
                    singleLine = true,
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.weight(1f)
                )

                Spacer(modifier = Modifier.width(8.dp))

                Button(
                    onClick = {
                        val codigoLimpio = codigoCuponTexto.trim().lowercase()
                        if (codigoLimpio == "profe_apruebeme") {
                            if (cuponUsado) {
                                mensajeCupon = "El cupón 'profe_apruebeme' ya fue utilizado en esta cuenta."
                                esErrorCupon = true
                                cuponAplicado = false
                            } else {
                                cuponAplicado = true
                                mensajeCupon = "¡Cupón aplicado! 30% de descuento concedido"
                                esErrorCupon = false
                            }
                        } else {
                            mensajeCupon = "Cupón inválido"
                            esErrorCupon = true
                            cuponAplicado = false
                        }
                    },
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.height(56.dp)
                ) {
                    Icon(imageVector = Icons.Default.ConfirmationNumber, contentDescription = null)
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Aplicar")
                }
            }

            if (mensajeCupon != null) {
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = mensajeCupon!!,
                    color = if (esErrorCupon) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Medium
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Opción de Bolsa para llevar
            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(horizontal = 14.dp, vertical = 10.dp)
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text("¿Deseas bolsa para llevar?", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                        Text("Se agregarán S/ 0.40 al total de tu pedido", fontSize = 12.sp, color = MaterialTheme.colorScheme.outline)
                    }
                    Switch(
                        checked = incluirBolsa,
                        onCheckedChange = { incluirBolsa = it }
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Descripción / Notas del pedido
            Text(text = "Descripción o especificaciones del pedido", fontWeight = FontWeight.Bold, fontSize = 16.sp)
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Indica detalles específicos para tu preparación (ej: sin hielo, dirección para delivery, etc.):",
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.outline
            )
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = nota,
                onValueChange = { pedidosVm.setNota(it) },
                placeholder = { Text("Ej: Con mucha azúcar, bien caliente, dirección de envío, etc.") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp),
                maxLines = 3,
                shape = RoundedCornerShape(10.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) {
                Row(modifier = Modifier.fillMaxWidth()) {
                    Text("Subtotal", color = MaterialTheme.colorScheme.outline)
                    Spacer(modifier = Modifier.weight(1f))
                    Text("S/ %.2f".format(subtotal))
                }
                if (descuentoCupon > 0) {
                    Spacer(modifier = Modifier.height(4.dp))
                    Row(modifier = Modifier.fillMaxWidth()) {
                        Text("Descuento Cupón (30%)", color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.SemiBold)
                        Spacer(modifier = Modifier.weight(1f))
                        Text("-S/ %.2f".format(descuentoCupon), color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)
                    }
                }
                if (incluirBolsa) {
                    Spacer(modifier = Modifier.height(4.dp))
                    Row(modifier = Modifier.fillMaxWidth()) {
                        Text("Bolsa para llevar (+S/ 0.40)", color = MaterialTheme.colorScheme.outline)
                        Spacer(modifier = Modifier.weight(1f))
                        Text("S/ 0.40")
                    }
                }
                if (costoDelivery > 0) {
                    Spacer(modifier = Modifier.height(4.dp))
                    Row(modifier = Modifier.fillMaxWidth()) {
                        Text("Costo de Delivery (${if (tipoDelivery == "CERCANO") "Distrito cercano" else "Distrito lejano"})", color = MaterialTheme.colorScheme.outline)
                        Spacer(modifier = Modifier.weight(1f))
                        Text("S/ %.2f".format(costoDelivery))
                    }
                }
                if (pago == "YAPE") {
                    Spacer(modifier = Modifier.height(4.dp))
                    Row(modifier = Modifier.fillMaxWidth()) {
                        Text("Recargo Yape / Plin (+S/ 0.50)", color = MaterialTheme.colorScheme.outline)
                        Spacer(modifier = Modifier.weight(1f))
                        Text("S/ 0.50")
                    }
                }
                HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
                Row(modifier = Modifier.fillMaxWidth()) {
                    Text("Total a pagar", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                    Spacer(modifier = Modifier.weight(1f))
                    Text(
                        text = "S/ %.2f".format(total),
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            CafeButton(
                text = "Confirmar pedido",
                enabled = carrito.isNotEmpty(),
                onClick = {
                    if (carrito.isEmpty()) {
                        Toast.makeText(context, "No hay productos seleccionados", Toast.LENGTH_SHORT).show()
                    } else {
                        if (cuponAplicado) {
                            scope.launch {
                                preferenciasStore.marcarCuponProfeApruebemeUsado(clienteId)
                            }
                        }
                        pedidosVm.crearPedido(clienteId, carrito) { pedidoId ->
                            menuVm.limpiarCarrito()
                            pedidosVm.setNota("")
                            onPedidoConfirmado(pedidoId)
                        }
                    }
                }
            )

            Spacer(modifier = Modifier.height(12.dp))

            CafeButton(
                text = "Seguir pidiendo",
                isOutlined = true,
                onClick = onBackClick
            )
        }
    }
}
