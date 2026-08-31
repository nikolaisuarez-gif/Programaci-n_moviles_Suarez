import java.util.Scanner

fun main() {
    val sc = Scanner(System.`in`)

    println("=========================================")
    println("      SISTEMA DE VENTAS EN CUOTAS       ")
    println("=========================================")
    
    print("Ingrese el nombre del producto: ")
    val producto = sc.nextLine()

    var precio = 0.0
    while (precio <= 0) {
        print("Ingrese el precio unitario: ")
        precio = sc.nextDouble()
        if (precio <= 0) println("El precio debe ser mayor a 0.")
    }

    var cantidad = 0
    while (cantidad <= 0) {
        print("Ingrese la cantidad: ")
        cantidad = sc.nextInt()
        if (cantidad <= 0) println("La cantidad debe ser mayor a 0.")
    }

    var cuotas = 0
    var interesPorcentaje = 0.0
    while (cuotas != 6 && cuotas != 12 && cuotas != 24) {
        print("Ingrese número de cuotas (6, 12 o 24): ")
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
    val montoInicial = precio * cantidad
    val montoInteres = montoInicial * interesPorcentaje
    val montoTotal = montoInicial + montoInteres
    val pagoMensual = montoTotal / cuotas

    println("\n=========================================")
    println("           RESUMEN DE COMPRA            ")
    println("=========================================")
    println("Producto:            $producto")
    println("Precio Unitario:     S/ %.2f".format(precio))
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
        println("Cuota $i: S/ %.2f".format(pagoMensual))
    }
    println("=========================================")
}
