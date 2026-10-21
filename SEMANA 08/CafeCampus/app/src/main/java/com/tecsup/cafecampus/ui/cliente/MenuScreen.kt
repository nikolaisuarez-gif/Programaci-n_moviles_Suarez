package com.tecsup.cafecampus.ui.cliente

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tecsup.cafecampus.ui.components.ProductoCard
import com.tecsup.cafecampus.viewmodel.MenuViewModel

@Composable
fun MenuScreen(
    vm: MenuViewModel,
    nombreUsuario: String = "Juan",
    onProductoClick: (Int) -> Unit,
    onCarritoClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val productos by vm.productos.collectAsState()
    val busqueda by vm.busqueda.collectAsState()
    val catSeleccionada by vm.categoriaSeleccionada.collectAsState()
    val carrito by vm.carrito.collectAsState()

    val categorias = listOf("Todo", "Desayunos", "Almuerzos", "Bebidas")
    val totalItems = carrito.sumOf { it.cantidad }

    Scaffold(
        topBar = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 12.dp)
            ) {
                Column {
                    Text(
                        text = "¡Hola, $nombreUsuario!",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "¿Qué se te antoja hoy?",
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.outline
                    )
                }

                Spacer(modifier = Modifier.weight(1f))

                IconButton(onClick = onCarritoClick) {
                    BadgedBox(
                        badge = {
                            if (totalItems > 0) {
                                Badge { Text("$totalItems") }
                            }
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.ShoppingCart,
                            contentDescription = "Carrito",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 20.dp)
        ) {
            OutlinedTextField(
                value = busqueda,
                onValueChange = { vm.onBusquedaChange(it) },
                placeholder = { Text("Buscar en el menú...") },
                leadingIcon = { Icon(imageVector = Icons.Default.Search, contentDescription = null) },
                trailingIcon = {
                    if (busqueda.isNotEmpty()) {
                        IconButton(onClick = { vm.onBusquedaChange("") }) {
                            Icon(imageVector = Icons.Default.Close, contentDescription = "Limpiar búsqueda")
                        }
                    }
                },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(categorias) { cat ->
                    FilterChip(
                        selected = catSeleccionada == cat,
                        onClick = { vm.seleccionarCategoria(cat) },
                        label = { Text(cat) }
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = if (busqueda.isNotEmpty()) "Resultados de búsqueda" else "Populares hoy",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(12.dp))

            if (productos.isEmpty()) {
                Text(
                    text = if (busqueda.isNotEmpty()) "No se encontraron productos que coincidan con '$busqueda'" else "No hay productos disponibles",
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.outline,
                    modifier = Modifier.padding(top = 12.dp)
                )
            } else {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    contentPadding = PaddingValues(bottom = 16.dp)
                ) {
                    items(productos, key = { it.id }) { p ->
                        ProductoCard(
                            producto = p,
                            onClick = { onProductoClick(p.id) },
                            onAgregarClick = { vm.agregarAlCarrito(p, 1) }
                        )
                    }
                }
            }
        }
    }
}

@androidx.compose.ui.tooling.preview.Preview(showBackground = true)
@Composable
fun MenuGridPreview() {
    val sampleProductos = listOf(
        com.tecsup.cafecampus.data.local.entity.Producto(id = 1, nombre = "Café americano", descripcion = "", categoria = "Bebidas", precio = 4.0, icono = "cafe_americano"),
        com.tecsup.cafecampus.data.local.entity.Producto(id = 2, nombre = "Sándwich de pollo", descripcion = "", categoria = "Desayunos", precio = 9.0, icono = "sandwich_pollo"),
        com.tecsup.cafecampus.data.local.entity.Producto(id = 3, nombre = "Menú del día", descripcion = "", categoria = "Almuerzos", precio = 12.0, icono = "menu_dia"),
        com.tecsup.cafecampus.data.local.entity.Producto(id = 4, nombre = "Jugo de naranja", descripcion = "", categoria = "Bebidas", precio = 5.0, icono = "jugo_naranja"),
        com.tecsup.cafecampus.data.local.entity.Producto(id = 5, nombre = "Empanada de carne", descripcion = "", categoria = "Desayunos", precio = 4.5, icono = "empanada_carne"),
        com.tecsup.cafecampus.data.local.entity.Producto(id = 6, nombre = "Chicha morada", descripcion = "", categoria = "Bebidas", precio = 3.5, icono = "chicha_morada")
    )

    com.tecsup.cafecampus.ui.theme.CafeTheme(darkTheme = true) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.padding(16.dp)
        ) {
            items(sampleProductos) { p ->
                ProductoCard(
                    producto = p,
                    onClick = {},
                    onAgregarClick = {}
                )
            }
        }
    }
}
