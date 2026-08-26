package com.Suarez.lab02carritokotlinconIA

import java.util.Scanner

data class Producto(
    val nombre: String,
    val precio: Double,
    var cantidad: Int
)

fun main() {
    val lector = Scanner(System.`in`)
    val carrito = mutableListOf<Producto>()
    
    // Catálogo de productos disponibles
    val catalogo = listOf(
        Producto("Laptop HP", 2500.0, 0),
        Producto("Mouse Logitech", 45.5, 0),
        Producto("Teclado Gamer", 120.0, 0),
        Producto("Monitor LG", 850.0, 0),
        Producto("Audifonos Sony", 250.0, 0)
    )

    println("=========================================")
    println(" CARRITO DE COMPRAS - TIENDA TECSUP ")
    println("=========================================")
    
    // 1. Registro del Cliente
    print("Ingrese su nombre completo: ")
    val nombreCliente = lector.nextLine()
    println("\n¡Bienvenido/a $nombreCliente!")

    var opcion: Int
    do {
        println("\n--- MENÚ PRINCIPAL ---")
        println("1. Ver catálogo y agregar al carrito")
        println("2. Ver mi carrito")
        println("3. Buscar producto en el carrito")
        println("4. Eliminar producto del carrito")
        println("5. Finalizar compra (Checkout)")
        println("0. Salir")
        print("Seleccione una opción: ")
        
        opcion = lector.nextInt()
        lector.nextLine() // Limpiar el buffer

        when (opcion) {
            1 -> {
                println("\n--- CATÁLOGO ---")
                catalogo.forEachIndexed { index, p -> 
                    println("${index + 1}. ${p.nombre} (S/ ${p.precio})") 
                }
                print("Elija el número del producto: ")
                val indice = lector.nextInt() - 1
                if (indice in catalogo.indices) {
                    print("¿Cuántas unidades de '${catalogo[indice].nombre}' desea? ")
                    val cant = lector.nextInt()
                    if (cant > 0) {
                        // Agregamos una copia con la cantidad elegida
                        val pElegido = catalogo[indice].copy(cantidad = cant)
                        carrito.add(pElegido)
                        println("¡Agregado con éxito!")
                    } else println("Cantidad no válida.")
                } else println("Opción incorrecta.")
            }
            2 -> {
                if (carrito.isEmpty()) println("El carrito está vacío.")
                else {
                    mostrarDetalle(carrito)
                    imprimirResumen(carrito)
                }
            }
            3 -> {
                print("Nombre del producto a buscar: ")
                val nombre = lector.nextLine()
                val hallado = buscarProducto(carrito, nombre)
                if (hallado != null) {
                    println("Encontrado: ${hallado.nombre} x${hallado.cantidad} - S/ ${hallado.precio}")
                } else println("No se encontró '$nombre' en tu carrito.")
            }
            4 -> {
                print("Nombre del producto a eliminar: ")
                val eliminar = lector.nextLine()
                val removido = carrito.removeIf { it.nombre.equals(eliminar, ignoreCase = true) }
                if (removido) println("Producto eliminado.")
                else println("No se encontró el producto.")
            }
            5 -> {
                if (carrito.isEmpty()) {
                    println("No puedes finalizar si el carrito está vacío.")
                } else {
                    println("\n--- TICKET FINAL ---")
                    println("Cliente: $nombreCliente")
                    mostrarDetalle(carrito)
                    imprimirResumen(carrito)
                    println("¡Gracias por su compra!")
                    opcion = 0 // Salir del loop
                }
            }
        }
    } while (opcion != 0)

    println("\nPrograma finalizado. ¡Vuelve pronto!")
}

// --- FUNCIONES DE LÓGICA (Manteniendo la estructura anterior) ---

fun calcularSubtotal(productos: List<Producto>): Double {
    return productos.sumOf { it.precio * it.cantidad }
}

fun calcularIGV(subtotal: Double): Double = subtotal * 0.18

fun calcularTotal(subtotal: Double, igv: Double): Double = subtotal + igv

fun calcularDescuento(total: Double): Double {
    return when {
        total > 5000 -> total * 0.10
        total > 3000 -> total * 0.05
        else -> 0.0
    }
}

fun mostrarDetalle(productos: List<Producto>) {
    println("--------- DETALLE DEL CARRITO ---------")
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
}

fun buscarProducto(productos: List<Producto>, nombre: String): Producto? {
    return productos.find { it.nombre.equals(nombre, ignoreCase = true) }
}
