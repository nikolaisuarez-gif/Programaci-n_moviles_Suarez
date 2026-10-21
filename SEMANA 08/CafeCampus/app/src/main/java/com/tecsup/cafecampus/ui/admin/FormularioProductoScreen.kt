package com.tecsup.cafecampus.ui.admin

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tecsup.cafecampus.data.local.entity.Producto
import com.tecsup.cafecampus.ui.components.AppTopBar
import com.tecsup.cafecampus.ui.components.CafeButton
import com.tecsup.cafecampus.ui.components.CafeTextField
import com.tecsup.cafecampus.ui.components.ProductoIcono
import com.tecsup.cafecampus.ui.theme.AdminPrimary
import com.tecsup.cafecampus.viewmodel.MenuAdminViewModel

@Composable
fun FormularioProductoScreen(
    productoId: Int?,
    vm: MenuAdminViewModel,
    onBackClick: () -> Unit
) {
    val productos by vm.productos.collectAsState()
    val productoExistente = productos.find { it.id == productoId }

    var nombre by remember(productoExistente) { mutableStateOf(productoExistente?.nombre ?: "") }
    var categoria by remember(productoExistente) { mutableStateOf(productoExistente?.categoria ?: "Desayunos") }
    var precioStr by remember(productoExistente) { mutableStateOf(productoExistente?.precio?.toString() ?: "4.50") }
    var descripcion by remember(productoExistente) { mutableStateOf(productoExistente?.descripcion ?: "") }
    var disponible by remember(productoExistente) { mutableStateOf(productoExistente?.disponible ?: true) }
    var icono by remember(productoExistente) { mutableStateOf(productoExistente?.icono ?: "pan") }

    val iconosDisponibles = listOf("pan", "plato", "cafe", "jugo", "botella", "dulce", "sandwich")
    val categoriasDisponibles = listOf("Desayunos", "Almuerzos", "Bebidas", "Snacks")

    Scaffold(
        topBar = {
            AppTopBar(
                title = if (productoId == null) "Nuevo producto" else "Editar producto",
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
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f)),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    ProductoIcono(icono = icono, nombre = nombre, size = 60.dp)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(text = "Toca para elegir icono", fontSize = 12.sp, color = MaterialTheme.colorScheme.outline)
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                iconosDisponibles.forEach { ic ->
                    FilterChip(
                        selected = icono == ic,
                        onClick = { icono = ic },
                        label = { Text(ic) }
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            CafeTextField(
                value = nombre,
                onValueChange = { nombre = it },
                label = "Nombre del producto"
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text("Categoría", fontWeight = FontWeight.Bold, fontSize = 14.sp)
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.padding(vertical = 4.dp)) {
                categoriasDisponibles.forEach { cat ->
                    FilterChip(
                        selected = categoria == cat,
                        onClick = { categoria = cat },
                        label = { Text(cat) }
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            CafeTextField(
                value = precioStr,
                onValueChange = { precioStr = it },
                label = "Precio (S/)"
            )

            Spacer(modifier = Modifier.height(12.dp))

            CafeTextField(
                value = descripcion,
                onValueChange = { descripcion = it },
                label = "Descripción",
                singleLine = false,
                maxLines = 3
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "Disponible en el menú",
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.weight(1f)
                )
                Switch(
                    checked = disponible,
                    onCheckedChange = { disponible = it }
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            CafeButton(
                text = "Guardar producto",
                containerColor = AdminPrimary,
                onClick = {
                    val p = Producto(
                        id = productoExistente?.id ?: 0,
                        nombre = nombre.ifBlank { "Producto" },
                        descripcion = descripcion,
                        categoria = categoria,
                        precio = precioStr.toDoubleOrNull() ?: 0.0,
                        disponible = disponible,
                        icono = icono
                    )
                    vm.guardarProducto(p) { onBackClick() }
                }
            )

            Spacer(modifier = Modifier.height(8.dp))

            CafeButton(
                text = "Cancelar",
                isOutlined = true,
                onClick = onBackClick
            )
        }
    }
}
