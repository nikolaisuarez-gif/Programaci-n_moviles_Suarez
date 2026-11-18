# Registro de prompts — mejora IA

La asistencia de IA usada en esta entrega fue Codex (OpenAI) dentro del entorno de desarrollo. Las propuestas se revisaron y adaptaron al código existente; no se atribuyen respuestas ni código a Gemini.

| Prompt que escribí | Qué propuso la IA | Qué adapté o descarté | Por qué |
| --- | --- | --- | --- |
| Agrega un filtro por categoría para el mapa y la lista sin romper MVVM. | Mantener la categoría seleccionada en el ViewModel y exponer una lista derivada con `combine`. | El Composable solo muestra chips y consume el flujo de lugares filtrado. | La View no consulta repositorios ni filtra los datos por su cuenta. |
| Agrega un radio de cercanía de 100, 200 o 500 metros alrededor de mi ubicación. | Filtrar por distancia en el ViewModel y dibujar un `Circle` en el mapa. | Se ofrecen opciones explícitas de 100, 200 y 500 m y una opción para quitar el límite. | El estado del filtro y la selección de lugares permanecen en el ViewModel. |
| Activa el punto azul y el botón nativo de ubicación solo cuando haya permiso. | Configurar `isMyLocationEnabled` y `myLocationButtonEnabled` con el estado de ubicación. | Se habilitan cuando el estado es `Lista`, que solo se alcanza tras validar permiso y recibir posición. | Evita activar el control antes de que exista una ubicación autorizada. |
| Agrega seguimiento en vivo con un botón Iniciar/Detener. | Exponer actualizaciones del Repository como `Flow` usando `callbackFlow` y `requestLocationUpdates`. | El Repository valida Play Services, permiso y GPS; `awaitClose` cancela las actualizaciones y el ViewModel gestiona el trabajo. | El Context y el cliente de Google quedan fuera del ViewModel y se libera el callback al detener o cerrar el flujo. |
| Guarda el último lugar seleccionado y recupéralo al abrir CampusGPS. | Persistir el identificador con DataStore y observarlo desde el ViewModel. | Se añadió `PreferenciasRepository` y una implementación DataStore; la restauración acepta solo IDs existentes. | Mantiene las preferencias detrás de una interfaz y permite al ViewModel seguir sin Context. |
| Haz que el usuario pueda cambiar entre tema claro y oscuro desde CampusGPS. | Agregar un control visible y conectar el tema de Compose con `MapStyleOptions`. | Se añadió el botón Modo oscuro/Modo claro en la barra superior; el mapa recibe el mismo estado y cambia su estilo junto con Material 3. | El modo oscuro se puede activar dentro de la app y no depende de cambiar la configuración global del dispositivo. |

## Revisión de arquitectura

- Los filtros, el seguimiento y el último lugar se controlan desde el ViewModel.
- Los accesos a ubicación y DataStore viven en implementaciones de Repository.
- Las pantallas Compose dibujan el estado recibido; no calculan distancias ni acceden a repositorios.
- La clave de Google Maps se mantiene en `local.properties` y nunca se incluye en prompts.
