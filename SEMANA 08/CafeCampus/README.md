# CafeCampus

Laboratorio de Programación en Móviles - Semana 08
Alumno: Nikolai Suarez
Tecsup

## Descripción General del Proyecto

CafeCampus es una aplicación móvil desarrollada con Kotlin, Jetpack Compose, Room Database, ViewModel y DataStore Preferences. La aplicación gestiona los pedidos de la cafetería del campus para dos tipos de usuario, Estudiante y Administrador.

## Preguntas Técnicas y Arquitectura

### ¿Por qué el Repository es una interface?

El repositorio se define como una interfaz para aplicar el principio de inversión de dependencias y desacoplar la lógica de negocio de la fuente de datos real. Al usar una interfaz como CafeRepository, las vistas y los ViewModels interactúan únicamente con los métodos definidos en el contrato sin preocuparse por cómo ni dónde se guarda la información. Esto permite realizar pruebas unitarias utilizando simuladores de datos sin tocar la base de datos real, y también nos da la flexibilidad de cambiar la tecnología de almacenamiento en el futuro sin modificar la interfaz de usuario.

### ¿Por qué crearPedido es suspend y observarPedidosCliente es Flow?

La función crearPedido es suspendida porque representa una operación puntual de una sola ejecución. Cuando el usuario confirma su compra, la tarea se ejecuta de forma asíncrona en segundo plano para no congelar la pantalla y termina en cuanto el pedido queda registrado en la base de datos. Por otro lado, observarPedidosCliente devuelve un Flow debido a que los pedidos cambian de estado constantemente, como cuando pasan de pendiente a en preparación o listo. El Flow establece un canal de transmisión continua de datos que notifica automáticamente a la pantalla cada vez que hay una actualización en la base de datos en tiempo real.

### ¿Qué pasaría si dos perfiles usaran bases de datos distintas?

Si el perfil de estudiante y el perfil de administrador utilizaran bases de datos independientes, la aplicación perdería la sincronización por completo. Cuando un alumno realizara un pedido en su base de datos local, el administrador nunca podría ver la solicitud en su panel porque su base de datos estaría aislada. De igual manera, si el administrador modificara un precio o agregara un producto en su pantalla, dichos cambios no se reflejarían en el menú del estudiante. Ambos perfiles necesitan compartir la misma fuente de datos para que los pedidos y el inventario permanezcan perfectamente coordinados.

### ¿Qué debería cambiar si mañana los datos viven en un servidor?

Gracias al uso de la interfaz CafeRepository, la capa de presentación y la interfaz de usuario no tendrían que sufrir ningún cambio. Lo único que tendríamos que modificar es la implementación del repositorio. Se crearía una nueva clase que se conecte a las APIs REST del servidor utilizando librerías como Retrofit o Ktor. La base de datos local Room podría mantenerse como una capa de caché para soporte fuera de línea, o bien reemplazarse por llamadas directas al servidor. Toda la lógica de las pantallas y los ViewModels seguiría funcionando exactamente igual porque seguirían consumiendo la misma interfaz.

## Estructura del Sistema y Base de Datos

La aplicación organiza sus datos locales a través de cuatro entidades principales en Room. La tabla usuarios almacena identificador, nombre, correo, clave y rol. La tabla productos guarda la información del menú con identificador, nombre, descripción, categoría, precio, disponibilidad e ícono. La tabla pedidos registra cada orden con su estado actual, hora de recojo, método de pago, notas adicionales, total y fecha. Finalmente, la tabla detalle pedido vincula los productos individuales con cada pedido realizado.
