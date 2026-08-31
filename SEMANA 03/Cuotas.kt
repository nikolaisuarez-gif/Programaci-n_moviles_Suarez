import java.util.Scanner

fun main() {
    val sc = Scanner(System.`in`)

    // Definimos el catálogo predeterminado
    val nombres = listOf("PS5 Pro", "iPhone 18 Pro Max", "Laptop Razer Blade", "Audífonos Sony XM5")
    val precios = listOf(3500.0, 6800.0, 9500.0, 1400.0)

    println("=========================================")
    println("      SISTEMA DE VENTAS EN CUOTAS       ")
    println("=========================================")
    
    // Mostramos las opciones al usuario
    println("CATÁLOGO DE PRODUCTOS:")
    for (i in nombres.indices) {
        println("${i + 1}. ${nombres[i]} - S/ ${precios[i]}")
    }

    var seleccion = 0
    while (seleccion !in 1..nombres.size) {
        print("\nSeleccione el número del producto (1-${nombres.size}): ")
        seleccion = sc.nextInt()
        if (seleccion !in 1..nombres.size) println("Error: Opción no válida.")
    }

    val productoSeleccionado = nombres[seleccion - 1]
    val precioUnitario = precios[seleccion - 1]

    var cantidad = 0
    while (cantidad <= 0) {
        print("¿Cuántas unidades de '$productoSeleccionado' desea?: ")
        cantidad = sc.nextInt()
        if (cantidad <= 0) println("La cantidad debe ser mayor a 0.")
    }

    var cuotas = 0
    var interesPorcentaje = 0.0
    while (cuotas != 6 && cuotas != 12 && cuotas != 24) {
        print("Elija el plan de cuotas (6, 12 o 24): ")
        cuotas = sc.nextInt()
        interesPorcentaje = when (cuotas) {
            6 -> 0.20
            12 -> 0.40
            24 -> 0.60
            else -> {
                println("Opción no válida. Solo se permite 6, 12 o 24 cuotas.")
                0.0
            }
        }
    }

    // Cálculos Finales
    val montoInicial = precioUnitario * cantidad
    val montoInteres = montoInicial * interesPorcentaje
    val montoTotal = montoInicial + montoInteres
    val pagoMensual = montoTotal / cuotas

    println("\n=========================================")
    println("           RESUMEN DE COMPRA            ")
    println("=========================================")
    println("Producto:            $productoSeleccionado")
    println("Precio Unitario:     S/ %.2f".format(precioUnitario))
    println("Cantidad:            $cantidad")
    println("Monto Inicial:       S/ %.2f".format(montoInicial))
    println("Interés Aplicado:    ${(interesPorcentaje * 100).toInt()}%")
    println("Monto del Interés:   S/ %.2f".format(montoInteres))
    println("Monto Total a Pagar: S/ %.2f".format(montoTotal))
    println("Número de Cuotas:    $cuotas")
    println("Pago Mensual:        S/ %.2f".format(pagoMensual))

    println("\n=========================================")
    println("          CALENDARIO DE PAGOS           ")
    println("=========================================")
    for (i in 1..cuotas) {
        println("Mes $i: S/ %.2f".format(pagoMensual))
    }
    println("=========================================")
    println("¡Gracias por su compra en Tecsup!")
}
