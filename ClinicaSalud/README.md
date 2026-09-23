# Clínica Salud+ - Tarea Evaluación Semanas 1-6 | NIKOLAI SUAREZ
**Curso:** Programación en Móviles | **Docente:** Juan León S. | **Modalidad:** Individual

## Requerimientos Funcionales (extraídos de las interfaces)
RF1 - Navegación secundaria: Drawer lateral que envuelve el Scaffold con 3 destinos (Inicio, Mis citas, Historial médico) accesible con ícono ? en Inicio. Si no envuelve, no cubre toda la pantalla.
RF2 - Inicio: LazyRow con 4 chips de especialidad (Todos/Cardiología/Pediatría/Dermatología) y LazyColumn con 3 médicos, cada tarjeta con nombre, especialidad y calificación.
RF3 - Agendar cita: Pantalla Agendar con selección única de fecha (3 opciones) y hora (3 opciones) usando FilterChip como RadioButton (una sola variable mutableStateOf).
RF4 - Confirmación y Mis citas: Resumen de cita (médico, fecha, hora) y lista Mis citas con LazyColumn mostrando estado Confirmada (verde) / Completada (gris) diferenciado.

## Tecnologías
Kotlin, Jetpack Compose, Material3, navigation-compose:2.7.7, Scaffold con topBar + content con padding + drawer, LazyColumn/LazyRow, remember/mutableStateOf (SIN ViewModel).

## Flujo
Inicio -> Perfil del médico (pasa nombre/esp por navArgument) -> Agendar (elige fecha/hora) -> Confirmación -> Ver Mis citas. Drawer permite saltar a Mis citas/Historial en cualquier momento.

## Cómo probar
Android Studio -> Sync Now -> Run en emulador. Verificar que el padding deja el hueco central visible y que el Drawer no tapa el contenido.
