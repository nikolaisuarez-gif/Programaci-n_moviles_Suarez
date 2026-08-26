package com.Suarez.lab02carritokotlinconIA

/**
 * DATA CLASS PRODUCTO
 * nombre y precio son 'val' porque no deben cambiar (inmutables).
 * cantidad es 'var' porque el usuario puede decidir llevar más después (mutable).
 * Si intentamos cambiar el precio (val), el programa dará un error de compilación.
 */
data class Producto(
    val nombre: String,
    val precio: Double,
    var cantidad: Int
)

fun main() {
    println("=========================================")
    println(" CARRITO DE COMPRAS - TIENDA TECSUP ")
    println("=========================================")

    val nombreCliente = "Nikolai Suarez"
    val carrito = mutableListOf<Producto>()

    println("Cliente: $nombreCliente")
    println()

    // 2. Agregar productos iniciales
    carrito.add(Producto("Laptop HP", 2500.0, 1))
    carrito.add(Producto("Mouse Logitech", 45.5, 2))
    carrito.add(Producto("Teclado Gamer", 120.0, 1))
    carrito.add(Producto("Monitor LG", 850.0, 1))

    println("PRODUCTOS AGREGADOS:")
    for (producto in carrito) {
        println("- Producto agregado: ${producto.nombre}")
    }
    println()

    // 4. Mostrar detalle inicial
    mostrarDetalle(carrito)
    
    // 3, 5 y 6. Cálculos, producto caro y totales
    imprimirResumen(carrito)

    // 7. Reto: Buscar producto
    println("\n--- BUSQUEDA DE PRODUCTO ---")
    val aBuscar = "laptop hp"
    val encontrado = buscarProducto(carrito, aBuscar)
    if (encontrado != null) {
        println("Encontrado: ${encontrado.nombre} (S/ ${encontrado.precio})")
    } else {
        println("El producto '$aBuscar' no se encuentra.")
    }

    // 7. Reto: Eliminar producto (Mouse Logitech)
    println("\n--- ELIMINANDO PRODUCTO (Mouse Logitech) ---")
    carrito.removeIf { it.nombre.equals("Mouse Logitech", ignoreCase = true) }

    // Mostrar detalle actualizado
    println("REPORTE FINAL ACTUALIZADO:")
    mostrarDetalle(carrito)
    imprimirResumen(carrito)

    println("=========================================")
}

// --- FUNCIONES DE CÁLCULO (Parte 3) ---

fun calcularSubtotal(productos: List<Producto>): Double {
    var subtotal = 0.0
    for (p in productos) {
        subtotal += p.precio * p.cantidad
    }
    return subtotal
}

fun calcularIGV(subtotal: Double): Double = subtotal * 0.18

fun calcularTotal(subtotal: Double, igv: Double): Double = subtotal + igv

// --- LÓGICA DE DESCUENTO (Parte 6) ---

fun calcularDescuento(total: Double): Double {
    return when {
        total > 5000 -> total * 0.10
        total > 3000 -> total * 0.05
        else -> 0.0
    }
}

// --- REPORTE DEL CARRITO (Parte 4) ---

fun mostrarDetalle(productos: List<Producto>) {
    println("--------- DETALLE DEL CARRITO ---------")
    println("Cantidad de productos: ${productos.size}")
    var i = 1
    for (p in productos) {
        val importe = p.precio * p.cantidad
        println(String.format("%d. %-20s x%d S/ %8.2f", i, p.nombre, p.cantidad, importe))
        i++
    }
    println("---------------------------------------")
}

fun imprimirResumen(carrito: List<Producto>) {
    val sub = calcularSubtotal(carrito)
    val igv = calcularIGV(sub)
    val totalSin = calcularTotal(sub, igv)
    val desc = calcularDescuento(totalSin)
    val totalFinal = totalSin - desc

    println(String.format("%-25s S/ %8.2f", "Subtotal:", sub))
    println(String.format("%-25s S/ %8.2f", "IGV (18%):", igv))
    println(String.format("%-25s S/ %8.2f", "TOTAL A PAGAR:", totalSin))
    
    if (desc > 0) {
        println(String.format("%-25s S/ %8.2f", "Descuento aplicado:", desc))
        println(String.format("%-25s S/ %8.2f", "TOTAL CON DESCUENTO:", totalFinal))
    }

    // Parte 5: Producto más caro
    val masCaro = carrito.maxByOrNull { it.precio }
    if (masCaro != null) {
        println(String.format("Producto mas caro: %s (S/ %.2f)", masCaro.nombre, masCaro.precio))
    }
}

// --- RETO: BUSCAR (Parte 7) ---

fun buscarProducto(productos: List<Producto>, nombre: String): Producto? {
    return productos.find { it.nombre.equals(nombre, ignoreCase = true) }
}
