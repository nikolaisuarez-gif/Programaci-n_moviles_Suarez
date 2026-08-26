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

    // Agregar productos
    carrito.add(Producto("Laptop HP", 2500.0, 1))
    carrito.add(Producto("Mouse Logitech", 45.5, 2))
    carrito.add(Producto("Teclado Mecánico", 150.0, 1))
    carrito.add(Producto("Monitor 24''", 600.0, 1))

    // Parte 4: Mostrar detalle y totales
    mostrarDetalle(carrito)

    val subtotal = calcularSubtotal(carrito)
    val igv = calcularIGV(subtotal)
    val totalSinDescuento = calcularTotal(subtotal, igv)

    println(String.format("%-25s S/ %8.2f", "Subtotal:", subtotal))
    println(String.format("%-25s S/ %8.2f", "IGV (18%):", igv))
    println(String.format("%-25s S/ %8.2f", "TOTAL A PAGAR:", totalSinDescuento))
    println("---------------------------------------")

    // Parte 5: Producto más caro y Descuento
    val masCaro = carrito.maxByOrNull { it.precio }
    if (masCaro != null) {
        println("Producto mas caro: ${masCaro.nombre} " +
                String.format("(S/ %.2f)", masCaro.precio))
    }

    val descuento = calcularDescuento(totalSinDescuento)
    val totalFinal = totalSinDescuento - descuento

    if (descuento > 0) {
        println(String.format("Descuento aplicado: S/ %.2f", descuento))
    } else {
        println("Descuento aplicado: S/ 0.00 (No aplica)")
    }
    
    println(String.format("TOTAL CON DESCUENTO: S/ %.2f", totalFinal))
    println("=========================================")
}

// Funciones de Cálculo (Parte 3)
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

// Función de Reporte (Parte 4)
fun mostrarDetalle(productos: List<Producto>) {
    println("--------- DETALLE DEL CARRITO ---------")
    println(String.format("Cantidad de productos: %d", productos.size))
    var i = 1
    for (p in productos) {
        val importe = p.precio * p.cantidad
        println(String.format("%d. %-20s x%d S/ %8.2f",
            i, p.nombre, p.cantidad, importe))
        i++
    }
    println("---------------------------------------")
}

// Lógica adicional (Parte 5)
fun calcularDescuento(total: Double): Double {
    return when {
        total > 5000 -> total * 0.10
        total > 3000 -> total * 0.05
        else -> 0.0
    }
}
