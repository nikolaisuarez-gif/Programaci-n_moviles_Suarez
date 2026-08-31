import java.util.Scanner

fun main() {
    val sc = Scanner(System.`in`)

    println("--- REGISTRO DE PRODUCTO ---")
    
    print("Ingrese el nombre del producto: ")
    val producto = sc.nextLine()

    var precio = 0.0
    while (precio <= 0) {
        print("Ingrese el precio del producto (debe ser > 0): ")
        precio = sc.nextDouble()
    }

    var cantidad = 0
    while (cantidad <= 0) {
        print("Ingrese la cantidad (debe ser > 0): ")
        cantidad = sc.nextInt()
    }

    var cuotas = 0
    var interesPorcentaje = 0.0
    while (cuotas != 6 && cuotas != 12 && cuotas != 24) {
        print("Ingrese el número de cuotas (6, 12 o 24): ")
        cuotas = sc.nextInt()
        interesPorcentaje = when (cuotas) {
            6 -> 0.20
            12 -> 0.40
            24 -> 0.60
            else -> {
                println("Error: Las cuotas solo pueden ser 6, 12 o 24.")
                0.0
            }
        }
    }

    // Cálculos
    val montoInicial = precio * cantidad
    val montoInteres = montoInicial * interesPorcentaje
    val montoTotal = montoInicial + montoInteres
    val pagoMensual = montoTotal / cuotas

    println("\n--- RESUMEN DE CÁLCULOS ---")
    println("Monto Inicial: S/ $montoInicial")
    println("Monto del Interés: S/ $montoInteres")
    println("Monto Total a Pagar: S/ $montoTotal")
    println("Pago Mensual: S/ $pagoMensual")
}
