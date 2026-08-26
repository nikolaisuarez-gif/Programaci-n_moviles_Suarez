<<<<<<< HEAD
# Programaci-n_moviles_Suarez
<img width="527" height="777" alt="image" src="https://github.com/user-attachments/assets/cb99c35c-075f-4046-87e7-33c044c04d13" />
=======
# Laboratorio 02: Carrito de Compras en Kotlin

**Estudiante:** Nikolai Suarez  
**Curso:** Programación de Aplicaciones Móviles

## Descripción del Proyecto
Este programa es una simulación de un carrito de compras desarrollado en Kotlin para consola. Permite gestionar productos mediante una `data class`, realizar cálculos de subtotal, IGV (18%) y aplicar descuentos dinámicos basados en el monto total de la compra. Además, incluye funcionalidades avanzadas como la búsqueda de productos y la eliminación de ítems con actualización automática de totales.

## Funcionalidades Implementadas
- **Modelo de datos:** Uso de `data class Producto`.
- **Cálculos:** Funciones para Subtotal, IGV, Total y Descuento.
- **Reporte:** Detalle del carrito con columnas alineadas usando `String.format`.
- **Lógica:** Aplicación de descuentos con `when` y detección del producto más caro.
- **Reto Adicional:** Búsqueda con `.find` y eliminación con `.removeIf`.

## Análisis: Parte 2 (val vs var)
- **¿Por qué nombre y precio son `val` pero cantidad es `var`?**  
  `nombre` y `precio` son `val` porque representan la identidad básica del producto que no cambia durante la sesión. `cantidad` es `var` porque es un valor mutable; el usuario puede agregar o quitar unidades del mismo producto en el carrito.
  
- **¿Qué pasaría si intentas cambiar el precio después de crear el producto?**  
  El compilador arrojaría un error indicando que una propiedad definida como `val` no puede ser reasignada (es de solo lectura).

## Resultado de Ejecución
*(Inserta aquí la captura de tu consola de Android Studio)*
>>>>>>> b4017b0 (Sincronizando proyecto)
