package com.suarez.lab02carritokotlin

data class Producto(
    val nombre: String,
    val precio: Double,
    var cantidad: Int
)

fun main() {
    println("=========================================")
    println(" CARRITO DE COMPRAS - TIENDA TECSUP ")
    println("=========================================")

    val nombreCliente = "Juan Leon"
    val carrito = mutableListOf<Producto>()

    println("Cliente: $nombreCliente")
    println()

    // Parte 2: Agregar productos
    carrito.add(Producto("Laptop HP", 2500.0, 1))
    carrito.add(Producto("Mouse Logitech", 45.5, 2))
    carrito.add(Producto("Teclado Mecánico", 150.0, 1))
    carrito.add(Producto("Monitor 24''", 600.0, 1))

    // Parte 4: Mostrar detalle
    mostrarDetalle(carrito)

    // Parte 3 y 5: Cálculos y Reporte Inicial
    imprimirTotales(carrito)

    // VI. Reto adicional: Buscar producto
    println("\n--- BUSQUEDA DE PRODUCTO ---")
    val busqueda = "Laptop HP"
    val encontrado = buscarProducto(carrito, busqueda)
    if (encontrado != null) {
        println("Producto encontrado: ${encontrado.nombre} - Precio: S/ ${encontrado.precio}")
    } else {
        println("El producto '$busqueda' no existe en el carrito.")
    }

    // VI. Reto adicional: Eliminar producto
    println("\n--- ELIMINANDO PRODUCTO (Mouse Logitech) ---")
    carrito.removeIf { it.nombre == "Mouse Logitech" }
    
    // Mostrar detalle y totales actualizados
    mostrarDetalle(carrito)
    imprimirTotales(carrito)
    
    println("=========================================")
}

// Funciones de Cálculo
fun calcularSubtotal(productos: List<Producto>): Double {
    var subtotal = 0.0
    for (p in productos) {
        subtotal += p.precio * p.cantidad
    }
    return subtotal
}

fun calcularIGV(subtotal: Double): Double {
    return subtotal * 0.18
}

fun calcularTotal(subtotal: Double, igv: Double): Double {
    return subtotal + igv
}

fun calcularDescuento(total: Double): Double {
    return when {
        total > 5000 -> total * 0.10
        total > 3000 -> total * 0.05
        else -> 0.0
    }
}

// Función para imprimir todos los totales (reutilizable)
fun imprimirTotales(carrito: List<Producto>) {
    val subtotal = calcularSubtotal(carrito)
    val igv = calcularIGV(subtotal)
    val totalSinDescuento = calcularTotal(subtotal, igv)
    val descuento = calcularDescuento(totalSinDescuento)
    val totalFinal = totalSinDescuento - descuento

    println(String.format("%-25s S/ %8.2f", "Subtotal:", subtotal))
    println(String.format("%-25s S/ %8.2f", "IGV (18%):", igv))
    println(String.format("%-25s S/ %8.2f", "TOTAL A PAGAR:", totalSinDescuento))
    
    if (descuento > 0) {
        println(String.format("Descuento aplicado:    - S/ %8.2f", descuento))
    }
    println(String.format("TOTAL CON DESCUENTO:     S/ %8.2f", totalFinal))

    val masCaro = carrito.maxByOrNull { it.precio }
    if (masCaro != null) {
        println("Producto mas caro: ${masCaro.nombre} (S/ ${masCaro.precio})")
    }
}

// Función de Reporte
fun mostrarDetalle(productos: List<Producto>) {
    println("--------- DETALLE DEL CARRITO ---------")
    var i = 1
    for (p in productos) {
        val importe = p.precio * p.cantidad
        println(String.format("%d. %-20s x%d S/ %8.2f",
            i, p.nombre, p.cantidad, importe))
        i++
    }
    println("---------------------------------------")
}

// Reto Adicional: Buscar
fun buscarProducto(productos: List<Producto>, nombre: String): Producto? {
    return productos.find { it.nombre.equals(nombre, ignoreCase = true) }
}
