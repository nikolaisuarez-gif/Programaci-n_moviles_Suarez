import java.util.Scanner

fun main() {
    val sc = Scanner(System.`in`)

    println("--- REGISTRO DE PRODUCTO ---")
    
    print("Ingrese el nombre del producto: ")
    val producto = sc.nextLine()

    print("Ingrese el precio del producto: ")
    val precio = sc.nextDouble()

    print("Ingrese la cantidad: ")
    val cantidad = sc.nextInt()

    // Selección de cuotas e interés
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
                println("Error: Las cuotas solo pueden ser 6, 12 o 24. Intente de nuevo.")
                0.0
            }
        }
    }

    println("\nCuotas seleccionadas: $cuotas")
    println("Interés aplicado: ${interesPorcentaje * 100}%")
}
