package com.Suarez.lab02carritokotlinconIA

/**
 * CLASE PADRE (HERENCIA)
 * Usamos 'open' para que otras clases puedan heredar de ella.
 */
open class Producto(
    val nombre: String,
    val precio: Double,
    var cantidad: Int
) {
    /**
     * MÉTODO POLIMÓRFICO
     * Las clases hijas lo sobrescribirán para cambiar el cálculo.
     */
    open fun calcularImporte(): Double {
        return precio * cantidad
    }
}
