package com.suarez.lab04nikolai

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color

@Composable
fun PantallaCarrito() {
    // Estados del formulario
    var nombre by remember { mutableStateOf("") }
    var precio by remember { mutableStateOf("") }
    var cantidad by remember { mutableStateOf("") }

    // La lista observable de productos
    val productos = remember { mutableStateListOf<Producto>() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Campo nombre
        OutlinedTextField(
            value = nombre,
            onValueChange = { nombre = it },
            label = { Text("Nombre") },
            modifier = Modifier.fillMaxWidth()
        )

        // Fila para precio y cantidad
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedTextField(
                value = precio,
                onValueChange = { precio = it },
                label = { Text("Precio") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                modifier = Modifier.weight(1f)
            )

            OutlinedTextField(
                value = cantidad,
                onValueChange = { cantidad = it },
                label = { Text("Cantidad") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.weight(1f)
            )
        }

        // Botón AGREGAR
        Button(
            onClick = {
                val precioNum = precio.toDoubleOrNull() ?: 0.0
                val cantidadNum = cantidad.toIntOrNull() ?: 0

                if (nombre.isNotBlank() && precioNum > 0 && cantidadNum > 0) {
                    productos.add(Producto(nombre = nombre, precio = precioNum, cantidad = cantidadNum))
                    // Limpiar los campos
                    nombre = ""
                    precio = ""
                    cantidad = ""
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("AGREGAR")
        }


        Text(
            text = "Productos: ${productos.size}",
            style = MaterialTheme.typography.titleMedium
        )

        //
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(productos) { producto ->
                TarjetaProducto(
                    producto = producto,
                    onEliminar = { productos.remove(producto) }
                )
            }
        }
    }
}


@Composable
fun TarjetaProducto(producto: Producto, onEliminar: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = producto.nombre,
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
                )
                Text(
                    text = "S/ ${producto.precio} x ${producto.cantidad}",
                    style = MaterialTheme.typography.bodyMedium.copy(color = Color.Gray)
                )
            }
            // Importe calculado (precio × cantidad)
            val importe = producto.precio * producto.cantidad
            Text(
                text = "S/ ${String.format("%.2f", importe)}",
                style = MaterialTheme.typography.titleMedium
            )
            // Botón eliminar como texto en vez de Icon
            TextButton(onClick = onEliminar) {
                Text("Eliminar", color = Color.Red)
            }
        }
    }
}