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

    println("\nProducto registrado: $producto")
    println("Precio unitario: S/ $precio")
    println("Cantidad: $cantidad")
}
