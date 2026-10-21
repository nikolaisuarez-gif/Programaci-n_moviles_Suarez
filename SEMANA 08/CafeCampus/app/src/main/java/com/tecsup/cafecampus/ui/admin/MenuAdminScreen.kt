package com.tecsup.cafecampus.ui.admin

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tecsup.cafecampus.ui.components.AppTopBar
import com.tecsup.cafecampus.ui.components.ProductoIcono
import com.tecsup.cafecampus.ui.theme.AdminPrimary
import com.tecsup.cafecampus.viewmodel.MenuAdminViewModel

@Composable
fun MenuAdminScreen(
    vm: MenuAdminViewModel,
    onNuevoProductoClick: () -> Unit,
    onEditarProductoClick: (Int) -> Unit
) {
    val productos by vm.productos.collectAsState()
    val catFiltro by vm.categoriaFiltro.collectAsState()
    val busqueda by vm.busqueda.collectAsState()

    val categorias = listOf("Todo", "Desayunos", "Almuerzos", "Bebidas")

    Scaffold(
        topBar = { AppTopBar(title = "Gestión del menú") },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onNuevoProductoClick,
                containerColor = AdminPrimary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Nuevo producto")
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 20.dp)
        ) {
            OutlinedTextField(
                value = busqueda,
                onValueChange = { vm.onBusquedaChange(it) },
                placeholder = { Text("Buscar producto en el menú...") },
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

            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                items(categorias) { cat ->
                    FilterChip(
                        selected = catFiltro == cat,
                        onClick = { vm.seleccionarCategoria(cat) },
                        label = { Text(cat) }
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (productos.isEmpty()) {
                Text(
                    text = if (busqueda.isNotEmpty()) "No se encontraron productos para '$busqueda'" else "No hay productos en esta categoría",
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.outline,
                    modifier = Modifier.padding(top = 16.dp)
                )
            } else {
                LazyColumn(
                    contentPadding = PaddingValues(bottom = 80.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(productos, key = { it.id }) { p ->
                        Card(
                            shape = RoundedCornerShape(12.dp),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.padding(12.dp)
                            ) {
                                ProductoIcono(icono = p.icono, size = 48.dp)

                                Spacer(modifier = Modifier.padding(horizontal = 6.dp))

                                Column(modifier = Modifier.weight(1f)) {
                                    Text(text = p.nombre, fontWeight = FontWeight.Bold, fontSize = 15.sp)
                                    Text(text = "S/ %.2f".format(p.precio), color = MaterialTheme.colorScheme.primary, fontSize = 13.sp)
                                    Text(
                                        text = if (p.disponible) "Disponible" else "Agotado",
                                        fontSize = 11.sp,
                                        color = if (p.disponible) MaterialTheme.colorScheme.outline else MaterialTheme.colorScheme.error
                                    )
                                }

                                Switch(
                                    checked = p.disponible,
                                    onCheckedChange = { vm.cambiarDisponibilidad(p.id, it) }
                                )

                                IconButton(onClick = { onEditarProductoClick(p.id) }) {
                                    Icon(imageVector = Icons.Default.Edit, contentDescription = "Editar", tint = MaterialTheme.colorScheme.outline)
                                }

                                IconButton(onClick = { vm.eliminarProducto(p) }) {
                                    Icon(imageVector = Icons.Default.Delete, contentDescription = "Eliminar", tint = MaterialTheme.colorScheme.error)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
