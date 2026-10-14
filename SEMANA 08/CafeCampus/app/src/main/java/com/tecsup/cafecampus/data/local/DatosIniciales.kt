package com.tecsup.cafecampus.data.local

import com.tecsup.cafecampus.data.local.entity.Producto
import com.tecsup.cafecampus.data.local.entity.Usuario
import com.tecsup.cafecampus.model.Rol

object DatosIniciales {
    suspend fun sembrar(db: CafeDatabase) {
        // Se carga solo la primera vez
        if (db.productoDao().contar() == 0) {
            val productos = listOf(
                Producto(nombre = "Café americano", descripcion = "Café pasado recién hecho 100% peruano", categoria = "Bebidas", precio = 4.0, disponible = true, icono = "cafe"),
                Producto(nombre = "Sándwich de pollo", descripcion = "Pechuga deshilachada con mayonesa de la casa", categoria = "Desayunos", precio = 9.0, disponible = true, icono = "pan"),
                Producto(nombre = "Menú del día", descripcion = "Entrada + segundo + refresco. Cambia cada día según el menú de cocina.", categoria = "Almuerzos", precio = 12.0, disponible = true, icono = "plato"),
                Producto(nombre = "Jugo de naranja", descripcion = "Jugo natural recién exprimido", categoria = "Bebidas", precio = 5.0, disponible = true, icono = "jugo"),
                Producto(nombre = "Empanada de carne", descripcion = "Empanada horneada rellena de carne con pasas", categoria = "Desayunos", precio = 4.5, disponible = true, icono = "pan"),
                Producto(nombre = "Chicha morada", descripcion = "Chicha casera con piña y canela", categoria = "Bebidas", precio = 3.5, disponible = true, icono = "botella")
            )
            productos.forEach { db.productoDao().insertar(it) }
        }

        if (db.usuarioDao().contar() == 0) {
            val estudiante = Usuario(
                nombre = "Juan Pérez",
                correo = "juan@campus.test",
                clave = "1234",
                rol = Rol.CLIENTE.name
            )
            val admin = Usuario(
                nombre = "Administrador Cafe",
                correo = "admin@cafe.test",
                clave = "admin",
                rol = Rol.ADMIN.name
            )
            db.usuarioDao().insertar(estudiante)
            db.usuarioDao().insertar(admin)
        }
    }
}
