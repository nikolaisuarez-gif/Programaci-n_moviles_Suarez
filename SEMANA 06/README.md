# Laboratorio 05/06: Navegación y Scaffold en Jetpack Compose

**Estudiante:** Nikolai Suarez  
**Curso:** Programación de Móviles  
**Docente:** Juan León  
**Tecnología:** Kotlin, Jetpack Compose, Material 3, Navigation Compose  

---

## 📌 Requerimientos Funcionales (RF)

### 🏥 OPCIÓN A — Clínica Salud+
1. **RF-01 (Navegación por NavigationDrawer):** La aplicación debe contar con un menú lateral (`ModalNavigationDrawer`) con avatar e iniciales del paciente (**NS - Nikolai Suarez**), permitiendo navegar entre *Inicio*, *Mis citas*, *Historial médico* y *Perfil*.
2. **RF-02 (Filtrado y Selección de Médicos):** La pantalla de Inicio debe permitir filtrar médicos por especialidad (*Cardiología*, *Pediatría*, *Dermatología*) mediante un `LazyRow` y mostrar la lista de médicos en un `LazyColumn` con su nombre, especialidad y calificación con estrellas (★).
3. **RF-03 (Agendamiento de Citas con Selección Única):** El usuario puede seleccionar un médico y agendar una cita seleccionando una fecha y una hora mediante chips interactivos de selección única (comportamiento `RadioButton`). El botón *"Confirmar cita"* permanecerá deshabilitado hasta seleccionar ambos datos.
4. **RF-04 (Gestión y Cancelación de Citas):** El sistema debe listar las citas agendadas en la pantalla *Mis Citas* diferenciando visualmente el estado (*Confirmada* / *Completada*). Se debe permitir cancelar una cita confirmada mediante un cuadro de diálogo de confirmación (`AlertDialog`).

---

### 🏋️‍♂️ OPCIÓN B — TECSUP Fit
1. **RF-05 (Navegación por BottomBar):** La aplicación debe incluir un `NavigationBar` fijo inferior con 4 pestañas (*Inicio*, *Reservas*, *Rutinas*, *Perfil*), resaltando visualmente el ícono de la pantalla activa.
2. **RF-06 (Catálogo y Detalle de Clases):** La pantalla principal muestra las clases de gimnasio disponibles con sus filtros (*Hoy*, *Esta semana*). Al seleccionar una clase, se navega al detalle mostrando descripción, horario, sala y cupos disponibles en tiempo real.
3. **RF-07 (Reserva de Cupos):** El usuario puede reservar un cupo en una clase seleccionada. Al confirmar la reserva, el sistema descuenta un cupo disponible y redirige al comprobante de confirmación.
4. **RF-08 (Gestión de Reservas y Perfil):** En la pestaña *Reservas*, se listan las clases reservadas con estado (*Confirmada* / *Completada*) y opción de cancelación con `AlertDialog`. La pestaña *Perfil* muestra los datos de **Nikolai Suarez** y sus estadísticas de asistencia.

---

## 🛠️ Estructura Técnica y Arquitectura
- **Sin ViewModel / Sin MVVM:** Manejo de estado 100% mediante `remember` y `mutableStateOf` con elevación de estado (*State Hoisting*).
- **Material 3:** Implementación de `Scaffold`, `TopAppBar`, `ModalNavigationDrawer` y `NavigationBar`.
