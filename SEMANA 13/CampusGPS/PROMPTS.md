# Registro de prompts — mejora IA

La asistencia de IA usada en esta entrega fue Codex (OpenAI) dentro del entorno de desarrollo. No se atribuyen respuestas ni código a Gemini.

## Prompt de implementación

> Actúa como un desarrollador Android Senior experto en Kotlin, Jetpack Compose y arquitectura MVVM. Revisa CampusGPS y agrega mejoras de la fase con IA dentro de `SEMANA 13/CampusGPS`, manteniendo la estructura del profesor, el paquete `com.suarez.campusgps` y cambios separados en commits claros.

## Solicitud concreta del estudiante

> En la carpeta de semana 13, lo que vaya en mejora con IA lo pones allí. Recuerda commits y push a este repo.

## Cambios elaborados con asistencia

1. Filtro de lugares por categoría (`feat(ia): filtra lugares por categoria`): el ViewModel expone las categorías disponibles y filtra la lista y los marcadores observados por Compose.
2. Radio de cercanía ajustable (`feat(ia): agrega filtro por radio de cercania`): el ViewModel limita los lugares según distancia; el mapa dibuja el radio alrededor de la posición actual.
3. Controles nativos de ubicación (`feat(ia): activa controles nativos de ubicacion`): el control de ubicación de Google Maps solo se activa cuando el estado indica que el permiso está concedido y hay una ubicación cargada.
4. Seguimiento en vivo (`feat(ia): agrega seguimiento de ubicacion en vivo`): el Repository expone ubicaciones como Flow con callbackFlow y el ViewModel permite iniciar y detener las actualizaciones, sin llevar Context a la capa de presentación.
5. Último lugar visitado (`feat(ia): guarda ultimo lugar con DataStore`): una interfaz de preferencias y su implementación DataStore guardan la selección; el ViewModel la restaura al iniciar sin depender de Android Context.

## Revisión

Se compila el proyecto con Gradle para detectar incompatibilidades de Kotlin/Compose. Las propuestas se adaptaron al código existente y se registraron en commits separados. La clave de Google Maps debe estar autorizada para `com.suarez.campusgps` para que el mapa cargue mosaicos.
