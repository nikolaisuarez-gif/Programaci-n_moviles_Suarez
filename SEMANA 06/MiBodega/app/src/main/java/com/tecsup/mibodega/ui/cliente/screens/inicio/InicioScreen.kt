package com.tecsup.mibodega.ui.cliente.screens.inicio

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Receipt
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
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
import com.tecsup.mibodega.ui.cliente.modelo.Producto
import com.tecsup.mibodega.ui.cliente.modelo.listaCategorias
import com.tecsup.mibodega.ui.cliente.modelo.listaProductosFake
import com.tecsup.mibodega.ui.componentes.ProductoCard
import com.tecsup.mibodega.ui.theme.GrisClaro
import com.tecsup.mibodega.ui.theme.VerdeBodega

/**
 * Pantalla 3: Inicio / Productos (mockup "Cliente").
 * Scaffold con TopAppBar, BottomBar con 4 pestañas funcionales (Inicio, Categorías, Pedidos, Perfil),
 * LazyRow de categorías y buscador en tiempo real.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InicioScreen(
    productos: List<Producto> = listaProductosFake,
    cantidadCarrito: Int,
    onVerCarrito: () -> Unit,
    onProductoClick: (Producto) -> Unit,
    onAgregarProducto: (Producto) -> Unit
) {
    var seccionSeleccionada by remember { mutableStateOf(0) }
    var categoriaSeleccionada by remember { mutableStateOf(listaCategorias.first()) }
    var textoBusqueda by remember { mutableStateOf("") }

    val productosFiltrados = productos.filter { producto ->
        val coincideCategoria = (categoriaSeleccionada == "Todos" || producto.categoria == categoriaSeleccionada)
        val coincideBusqueda = textoBusqueda.isBlank() || producto.nombre.contains(textoBusqueda, ignoreCase = true)
        coincideCategoria && coincideBusqueda
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Mi Bodega", fontWeight = FontWeight.Bold) },
                actions = {
                    IconButton(onClick = onVerCarrito) {
                        BadgedBox(
                            badge = {
                                if (cantidadCarrito > 0) {
                                    Badge { Text("$cantidadCarrito") }
                                }
                            }
                        ) {
                            Icon(Icons.Default.ShoppingCart, contentDescription = "Carrito")
                        }
                    }
                }
            )
        },
        bottomBar = {
            BarraInferior(
                seleccionado = seccionSeleccionada,
                onSeleccionar = { seccionSeleccionada = it }
            )
        }
    ) { paddingInterno ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingInterno)
                .padding(horizontal = 16.dp)
        ) {
            when (seccionSeleccionada) {
                0 -> {
                    // Pestaña INICIO
                    OutlinedTextField(
                        value = textoBusqueda,
                        onValueChange = { textoBusqueda = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 6.dp),
                        placeholder = { Text("Buscar productos...") },
                        leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                        trailingIcon = {
                            if (textoBusqueda.isNotEmpty()) {
                                IconButton(onClick = { textoBusqueda = "" }) {
                                    Icon(Icons.Default.Clear, contentDescription = "Limpiar")
                                }
                            }
                        },
                        singleLine = true,
                        shape = RoundedCornerShape(12.dp)
                    )

                    Text(
                        text = "Productos destacados",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(top = 8.dp, bottom = 4.dp)
                    )

                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        contentPadding = PaddingValues(vertical = 6.dp)
                    ) {
                        items(listaCategorias) { categoria ->
                            ChipCategoria(
                                texto = categoria,
                                seleccionado = categoria == categoriaSeleccionada,
                                onClick = { categoriaSeleccionada = categoria }
                            )
                        }
                    }

                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        contentPadding = PaddingValues(vertical = 8.dp),
                        modifier = Modifier.fillMaxSize()
                    ) {
                        items(productosFiltrados) { producto ->
                            ProductoCard(
                                producto = producto,
                                onClick = { onProductoClick(producto) },
                                onAgregar = { onAgregarProducto(producto) }
                            )
                        }
                    }
                }
                1 -> {
                    // Pestaña CATEGORÍAS
                    VistaCategorias(
                        productos = productos,
                        onProductoClick = onProductoClick,
                        onAgregarProducto = onAgregarProducto
                    )
                }
                2 -> {
                    // Pestaña PEDIDOS
                    VistaPedidos()
                }
                3 -> {
                    // Pestaña PERFIL
                    VistaPerfil()
                }
            }
        }
    }
}

@Composable
private fun ChipCategoria(
    texto: String,
    seleccionado: Boolean,
    onClick: () -> Unit
) {
    val fondo = if (seleccionado) VerdeBodega else GrisClaro
    val contenido = if (seleccionado) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface

    Row(
        modifier = Modifier
            .background(fondo, RoundedCornerShape(20.dp))
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Text(text = texto, color = contenido, fontWeight = FontWeight.Medium)
    }
}

@Composable
private fun BarraInferior(
    seleccionado: Int,
    onSeleccionar: (Int) -> Unit
) {
    val items = listOf(
        Triple("Inicio", Icons.Default.Home, 0),
        Triple("Categorías", Icons.Default.List, 1),
        Triple("Pedidos", Icons.Default.Receipt, 2),
        Triple("Perfil", Icons.Default.Person, 3)
    )
    NavigationBar {
        items.forEach { (etiqueta, icono, indice) ->
            NavigationBarItem(
                selected = seleccionado == indice,
                onClick = { onSeleccionar(indice) },
                icon = { Icon(icono, contentDescription = etiqueta) },
                label = { Text(etiqueta) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = VerdeBodega,
                    selectedTextColor = VerdeBodega
                )
            )
        }
    }
}

@Composable
private fun VistaCategorias(
    productos: List<Producto>,
    onProductoClick: (Producto) -> Unit,
    onAgregarProducto: (Producto) -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {
        Text(
            text = "Explorar por Categoría",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(vertical = 12.dp)
        )

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(bottom = 16.dp)
        ) {
            items(listaCategorias.filter { it != "Todos" }) { cat ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Text(
                            text = cat,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = VerdeBodega,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        val prodsCat = productos.filter { it.categoria == cat }
                        prodsCat.forEach { producto ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable { onProductoClick(producto) }
                                    .padding(vertical = 6.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(text = producto.nombre, fontWeight = FontWeight.Medium)
                                    Text(text = "S/ %.2f".format(producto.precio), style = MaterialTheme.typography.bodySmall, color = VerdeBodega)
                                }
                                IconButton(onClick = { onAgregarProducto(producto) }) {
                                    Icon(Icons.Default.ShoppingCart, contentDescription = "Agregar", tint = VerdeBodega)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun VistaPedidos() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 12.dp)
    ) {
        Text(
            text = "Mis Pedidos",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "Pedido #1024", fontWeight = FontWeight.Bold)
                    Text(text = "En proceso", color = VerdeBodega, fontWeight = FontWeight.Bold)
                }
                Spacer(Modifier.height(6.dp))
                Text(text = "Fecha: 07 de Octubre, 2026", style = MaterialTheme.typography.bodySmall)
                Text(text = "Total: S/ 25.90", fontWeight = FontWeight.SemiBold, color = VerdeBodega)
                Text(text = "Dirección: Av. Los Olivos 123", style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}

@Composable
private fun VistaPerfil() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(80.dp)
                .background(VerdeBodega, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(Icons.Default.Person, contentDescription = "Perfil", tint = MaterialTheme.colorScheme.onPrimary, modifier = Modifier.size(48.dp))
        }

        Spacer(Modifier.height(12.dp))

        Text(text = "Juan Pérez", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
        Text(text = "juan.perez@tecsup.edu.pe", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)

        Spacer(Modifier.height(24.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = "Datos de contacto", fontWeight = FontWeight.Bold, modifier = Modifier.padding(bottom = 8.dp))
                Text(text = "Teléfono: 987 654 321", style = MaterialTheme.typography.bodyMedium)
                Spacer(Modifier.height(4.dp))
                Text(text = "Dirección habitual: Av. Los Olivos 123", style = MaterialTheme.typography.bodyMedium)
                Spacer(Modifier.height(4.dp))
                Text(text = "Referencia: Frente al parque", style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}
