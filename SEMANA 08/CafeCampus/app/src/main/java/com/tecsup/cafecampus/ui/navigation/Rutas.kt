package com.tecsup.cafecampus.ui.navigation

sealed class Rutas(val ruta: String) {
    object Splash : Rutas("splash")
    object Login : Rutas("login")
    object Registro : Rutas("registro")

    // Cliente
    object ClienteMenu : Rutas("cliente_menu")
    object ClienteDetalleProducto : Rutas("cliente_detalle_producto/{productoId}") {
        fun crearRuta(productoId: Int) = "cliente_detalle_producto/$productoId"
    }
    object ClienteConfirmarPedido : Rutas("cliente_confirmar_pedido")
    object ClienteSeguimiento : Rutas("cliente_seguimiento/{pedidoId}") {
        fun crearRuta(pedidoId: Int) = "cliente_seguimiento/$pedidoId"
    }
    object ClienteMisPedidos : Rutas("cliente_mis_pedidos")

    // Admin
    object AdminPanel : Rutas("admin_panel")
    object AdminPedidos : Rutas("admin_pedidos")
    object AdminDetallePedido : Rutas("admin_detalle_pedido/{pedidoId}") {
        fun crearRuta(pedidoId: Int) = "admin_detalle_pedido/$pedidoId"
    }
    object AdminMenu : Rutas("admin_menu")
    object AdminFormularioProducto : Rutas("admin_formulario_producto?productoId={productoId}") {
        fun crearRuta(productoId: Int? = null) = if (productoId != null) "admin_formulario_producto?productoId=$productoId" else "admin_formulario_producto"
    }
    object AdminAjustes : Rutas("admin_ajustes")
}
