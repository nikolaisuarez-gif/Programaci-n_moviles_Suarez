# Mejoras desarrolladas con IA

Registro de las solicitudes usadas para planear las mejoras en la rama `mejora-ia`. La propuesta se revisó para mantener MVVM + Repository y el comportamiento de simulación de JSONPlaceholder.

| Prompt que escribí | Qué respondió la IA | Qué cambié o descarté | Por qué |
|---|---|---|---|
| Implementa la mejora de borrar una publicación con `@DELETE("posts/{id}")`, `Response<Unit>` y una acción desde el detalle. | Propuso agregar la ruta, propagar su resultado por el Repository y el ViewModel, y presentar el estado en la pantalla de detalle. | El Repository valida `isSuccessful` y convierte respuestas no exitosas en fallo; el ViewModel expone estados para borrar y reintentar; la pantalla explica que el borrado es simulado. | JSONPlaceholder no persiste cambios y el ViewModel no debe conocer Retrofit ni `HttpException`. |
| Agrega búsqueda por título sobre publicaciones ya descargadas y administra su estado desde el ViewModel. | Propuso guardar la consulta en un `StateFlow` y filtrar los resultados en memoria. | La búsqueda no provoca otra llamada HTTP y no cambia el filtro por usuario existente. | Cumple el requisito de búsqueda local y mantiene la separación de capas. |
| Añade un interceptor OkHttp que envíe `Accept: application/json` a todas las peticiones. | Propuso modificar cada llamada individual o configurar el encabezado en el cliente HTTP. | Se eligió un interceptor del cliente OkHttp, antes del interceptor de logging. | Un único punto cubre GET, POST y DELETE; no se duplica lógica en `PostApi`. |

## Revisión de arquitectura

- La pantalla solo invoca métodos del ViewModel.
- El ViewModel solo depende de `PostRepository` y de `UiState`.
- La interpretación del código HTTP de DELETE se queda en `PostRepositoryRemote`.
- El botón de eliminar reporta la respuesta simulada; no quita el elemento de la lista como si el servidor guardara el cambio.
