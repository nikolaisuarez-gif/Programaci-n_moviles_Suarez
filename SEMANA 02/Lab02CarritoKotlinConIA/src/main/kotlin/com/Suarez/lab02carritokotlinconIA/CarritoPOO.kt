package com.Suarez.lab02carritokotlinconIA

class CarritoCompras {
    private val listaProductos = mutableListOf<Producto>()

    fun agregar(p: Producto) {
        listaProductos.add(p)
        println("Agregado: ${p.nombre}")
    }

    fun eliminar(nombre: String) {
        listaProductos.removeIf { it.nombre.equals(nombre, ignoreCase = true) }
    }

    fun calcularSubtotal(): Double {
        return listaProductos.sumOf { it.calcularImporte() }
    }

    fun mostrarTicket() {
        println("--------- TICKET DE VENTA (POO) ---------")
        for (p in listaProductos) {
            val tipo = if (p is ProductoFisico) "Físico" else "Digital"
            println(String.format("%-15s (%-7s) x%d S/ %8.2f", p.nombre, tipo, p.cantidad, p.calcularImporte()))
        }
        val sub = calcularSubtotal()
        val igv = sub * 0.18
        println("-----------------------------------------")
        println(String.format("SUBTOTAL: S/ %.2f", sub))
        println(String.format("IGV (18%%): S/ %.2f", igv))
        println(String.format("TOTAL: S/ %.2f", sub + igv))
    }
}

fun main() {
    val miCarrito = CarritoCompras()

    // Demostración de POLIMORFISMO: agregamos diferentes tipos de productos
    miCarrito.agregar(ProductoFisico("Laptop Pro", 3000.0, 1, 50.0)) // +50 envío
    miCarrito.agregar(ProductoDigital("Curso Kotlin", 100.0, 1))      // -10% descuento
    miCarrito.agregar(ProductoFisico("Mouse Pro", 80.0, 2, 10.0))    // +10 envío

    println()
    miCarrito.mostrarTicket()
}
