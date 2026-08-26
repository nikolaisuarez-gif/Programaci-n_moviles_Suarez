package com.suarez.lab02carritokotlin

import java.util.Scanner

data class Producto(
    val nombre: String,
    val precio: Double,
    var cantidad: Int
)

fun main() {
    val sc = Scanner(System.`in`)

    println("=========================================")
    println(" CARRITO DE COMPRAS - TIENDA TECSUP ")
    println("=========================================")

    print("Nombre del cliente: ")
    val cliente = sc.nextLine()
    val carrito = mutableListOf<Producto>()

    val productos = listOf(
        Producto("Laptop HP", 2500.0, 1),
        Producto("Mouse Logitech", 45.5, 2),
        Producto("Teclado Mecánico", 150.0, 1),
        Producto("Monitor 24''", 600.0, 1)
    )

    println("\n--- PRODUCTOS DISPONIBLES ---")
    productos.forEachIndexed { i, p ->
        println("${i + 1}. ${p.nombre} - S/ ${p.precio}")
    }

    print("\n¿Cuántos desea agregar?: ")
    val n = sc.nextInt()

    repeat(n) {
        print("Elija producto (1-4): ")
        val op = sc.nextInt()

        if (op in 1..4) {
            print("Cantidad: ")
            val cant = sc.nextInt()
            val p = productos[op - 1]
            carrito.add(Producto(p.nombre, p.precio, cant))
            println("Producto agregado: ${p.nombre}")
        } else println("Opción inválida.")
    }

    println("\nCliente: $cliente")
    println("Cantidad de productos: ${carrito.size}")
    mostrarDetalle(carrito)
    imprimirTotales(carrito)

    sc.nextLine() // Limpiar buffer

    println("\n--- ELIMINAR PRODUCTO ---")
    print("Nombre del producto a eliminar: ")
    val eliminar = sc.nextLine()

    if (carrito.removeIf { it.nombre.equals(eliminar, true) })
        println("Producto '$eliminar' eliminado.")
    else
        println("Producto '$eliminar' no encontrado.")

    println("\n--- CARRITO ACTUALIZADO ---")
    mostrarDetalle(carrito)
    imprimirTotales(carrito)

    sc.close()
}

fun calcularSubtotal(lista: List<Producto>): Double {
    var subtotal = 0.0
    for (p in lista) subtotal += p.precio * p.cantidad
    return subtotal
}

fun calcularIGV(subtotal: Double) = subtotal * 0.18

fun calcularTotal(subtotal: Double, igv: Double) = subtotal + igv

fun calcularDescuento(total: Double) = when {
    total > 5000 -> total * 0.10
    total > 3000 -> total * 0.05
    else -> 0.0
}

fun imprimirTotales(carrito: List<Producto>) {
    val subtotal = calcularSubtotal(carrito)
    val igv = calcularIGV(subtotal)
    val total = calcularTotal(subtotal, igv)
    val descuento = calcularDescuento(total)

    println("\nSubtotal:       S/ %8.2f".format(subtotal))
    println("IGV (18%%):      S/ %8.2f".format(igv))
    println("TOTAL:          S/ %8.2f".format(total))
    if (descuento > 0) {
        println("Descuento:     -S/ %8.2f".format(descuento))
    }
    println("TOTAL FINAL:    S/ %8.2f".format(total - descuento))

    val caro = carrito.maxByOrNull { it.precio }
    if (caro != null) println("Producto mas caro: ${caro.nombre}")
}

fun mostrarDetalle(lista: List<Producto>) {
    println("\n--------- DETALLE DEL CARRITO ---------")
    for ((i, p) in lista.withIndex()) {
        val importe = p.precio * p.cantidad
        println(String.format("%d. %-20s x%d S/ %8.2f", i + 1, p.nombre, p.cantidad, importe))
    }
    println("---------------------------------------")
}

fun buscarProducto(lista: List<Producto>, nombre: String): Producto? {
    return lista.find { it.nombre.equals(nombre, true) }
}
