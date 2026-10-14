# CafeCampus

Laboratorio de Programación en Móviles - Semana 08
Alumno: Nikolai Suarez
Tecsup

## Descripción
Proyecto de aplicación móvil CafeCampus desarrollado con Kotlin, Jetpack Compose, Room Database, ViewModel y DataStore Preferences. La aplicación gestiona los pedidos de la cafetería para dos tipos de usuario: Estudiante (Cliente) y Administrador.

## Estructura del proyecto

com.tecsup.cafecampus
├── MainActivity.kt
├── CafeApp.kt
├── data
│   ├── local
│   │   ├── entity
│   │   │   ├── Producto.kt
│   │   │   ├── Usuario.kt
│   │   │   ├── Pedido.kt
│   │   │   └── DetallePedido.kt
│   │   ├── dao
│   │   │   ├── ProductoDao.kt
│   │   │   ├── UsuarioDao.kt
│   │   │   └── PedidoDao.kt
│   │   ├── relation
│   │   │   └── PedidoConDetalle.kt
│   │   ├── CafeDatabase.kt
│   │   └── DatosIniciales.kt
│   ├── repository
│   │   ├── CafeRepository.kt
│   │   └── CafeRepositoryRoom.kt
│   └── prefs
│       └── PreferenciasStore.kt
├── model
│   ├── Rol.kt
│   ├── EstadoPedido.kt
│   └── ItemCarrito.kt
├── viewmodel
│   ├── CafeViewModelFactory.kt
│   ├── MenuViewModel.kt
│   ├── SesionViewModel.kt
│   ├── PedidosClienteViewModel.kt
│   ├── PanelAdminViewModel.kt
│   ├── PedidosAdminViewModel.kt
│   └── MenuAdminViewModel.kt
└── ui
    ├── theme
    │   ├── Color.kt
    │   ├── Type.kt
    │   └── Theme.kt
    ├── components
    │   ├── AppTopBar.kt
    │   ├── CafeButton.kt
    │   ├── CafeTextField.kt
    │   ├── EstadoChip.kt
    │   ├── ProductoCard.kt
    │   ├── ProductoIcono.kt
    │   ├── PedidoCard.kt
    │   └── StepperEstado.kt
    ├── navigation
    │   ├── Rutas.kt
    │   └── NavCafe.kt
    ├── cliente
    │   ├── SplashScreen.kt
    │   ├── LoginScreen.kt
    │   ├── MenuScreen.kt
    │   ├── DetalleProductoScreen.kt
    │   ├── ConfirmarPedidoScreen.kt
    │   ├── SeguimientoScreen.kt
    │   └── MisPedidosScreen.kt
    └── admin
        ├── PanelScreen.kt
        ├── PedidosAdminScreen.kt
        ├── DetallePedidoAdminScreen.kt
        ├── MenuAdminScreen.kt
        ├── FormularioProductoScreen.kt
        └── AjustesScreen.kt

## Base de datos

1. usuarios: id, nombre, correo, clave, rol
2. productos: id, nombre, descripcion, categoria, precio, disponible, icono
3. pedidos: id, clientId, estado, horaRecojo, pago, nota, total, fecha
4. detalle_pedido: id, pedidoId, productoId, nombreProducto, cantidad, precioUnitario

## Historial de commits

1. chore: importa proyecto base CafeCampus
2. feat(data): agrega entidades Usuario, Pedido y DetallePedido
3. feat(data): agrega DAOs y relacion PedidoConDetalle
4. feat(data): registra entidades y siembra datos iniciales en CafeDatabase
5. feat(data): implementa CafeRepositoryRoom con todas las operaciones
6. feat(data): agrega PreferenciasStore con DataStore
7. feat(viewmodel): agrega SesionViewModel y login con selector de rol
8. feat(ui): agrega detalle de producto y carrito en MenuViewModel
9. feat(ui): agrega confirmar pedido, seguimiento y mis pedidos
10. feat(admin): agrega panel de control y gestion de pedidos por estado
11. feat(admin): agrega detalle de pedido, gestion de menu y formulario
12. feat(admin): agrega ajustes con DataStore y cambio de perfil
13. docs: agrega README con estructura y capturas
