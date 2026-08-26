package com.Suarez.lab02carritokotlinconIA

/**
 * PRODUCTO FÍSICO
 * Suma un costo de envío fijo al importe.
 */
class ProductoFisico(nombre: String, precio: Double, cantidad: Int, val costoEnvio: Double) 
    : Producto(nombre, precio, cantidad) {
    
    override fun calcularImporte(): Double {
        return super.calcularImporte() + costoEnvio
    }
}

/**
 * PRODUCTO DIGITAL
 * Aplica un descuento automático del 10% por ser descarga.
 */
class ProductoDigital(nombre: String, precio: Double, cantidad: Int) 
    : Producto(nombre, precio, cantidad) {
    
    override fun calcularImporte(): Double {
        return super.calcularImporte() * 0.90
    }
}
