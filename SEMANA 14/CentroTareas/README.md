# CentroTareas

Aplicación Android de práctica para observar el bloqueo del hilo principal y resolver tareas largas con corrutinas, `StateFlow`, cancelación y el patrón MVVM + Repository. Los datos de perfil y pedidos son ficticios y las demoras se simulan con `delay`.

## Estructura de paquetes

```text
com.tecsup.centrotareas/
├── MainActivity.kt
├── model/
│   └── Estados.kt
├── data/
│   ├── TareasRepository.kt
│   └── TareasRepositoryLocal.kt
├── viewmodel/
│   ├── TareasViewModel.kt
│   └── TareasViewModelFactory.kt
└── ui/
    ├── PantallaTareas.kt
    └── theme/
        └── Theme.kt
```

## Arquitectura MVVM

```text
PantallaTareas (View)
   │ eventos y collectAsState()
   ▼
TareasViewModel ── StateFlow ──► PantallaTareas
   │
   ▼
TareasRepository (interface)
   │
   ▼
TareasRepositoryLocal
```

La pantalla solo muestra estados y comunica eventos. El ViewModel coordina las corrutinas en `viewModelScope` y depende de la interfaz del Repository. El Repository contiene las operaciones simuladas y el cálculo de primos.

## Observación del Paso 1

Al tocar **Tarea larga (MAL)**, la pantalla deja de responder durante cuatro segundos: el botón **Tócame** no incrementa el contador y el mensaje no se actualiza en pantalla. Aunque el ViewModel asigna `Trabajando...`, `Thread.sleep(4000)` bloquea el hilo principal, que es el que Compose necesita para dibujar. Cuando termina la espera, la interfaz puede procesar el estado pendiente y muestra `Terminó`. Si la espera durara diez segundos, Android podría mostrar un diálogo de “La aplicación no responde” (ANR) y permitir cerrarla.

En el paso siguiente se reemplaza el bloqueo por una corrutina cancelable; el commit del Paso 1 conserva el ejemplo intencional y el código final ya no contiene `Thread.sleep`.

## Capturas

### Tarea con progreso y pantalla respondiendo

[Insertar captura aquí]

### Tarea cancelada

[Insertar captura aquí]

### Cálculo de números primos

[Insertar captura aquí]

### Carga una por una y carga a la vez

[Insertar captura aquí]

### Error simulado y reintento

[Insertar captura aquí]

## Para pensar

### 1. ¿Por qué no se veía `Trabajando...` en el Paso 1?

La asignación cambia el estado en memoria, pero `Thread.sleep` bloquea el hilo principal antes de que Compose pueda recomponer y dibujar. Al quedar libre el hilo, el valor ya es `Terminó`, por eso el mensaje intermedio no llega a verse.

### 2. ¿Qué diferencia hay entre `Thread.sleep` y `delay`?

`Thread.sleep` bloquea físicamente el hilo que lo ejecuta. `delay` suspende la corrutina sin bloquear el hilo; este queda disponible para atender la interfaz u otras corrutinas. Además, `delay` coopera con la cancelación de corrutinas.

### 3. ¿Por qué `Dispatchers.Default` para los primos y `Dispatchers.IO` para la red?

Contar primos consume CPU, por eso corresponde `Dispatchers.Default`, pensado para cálculos. Las operaciones bloqueantes de red o disco corresponden a `Dispatchers.IO`, que está optimizado para esperas de entrada/salida. En este laboratorio las cargas se simulan con `delay`, así que no se bloquea un hilo de I/O.

### 4. ¿Qué pasaría con la corrutina si el usuario cierra la pantalla? ¿Qué papel cumple `viewModelScope`?

Al destruirse el ViewModel, `viewModelScope` cancela automáticamente las corrutinas que lanzó. Así la tarea no sigue trabajando cuando la pantalla y su ViewModel ya no existen.

### 5. ¿Por qué `async` tarda 3 s y no 5 s? ¿Qué pasa si una de las dos cargas falla?

Las cargas en paralelo empiezan juntas: perfil tarda dos segundos y pedidos tres, así que el tiempo total se aproxima al mayor de ambos, tres segundos. En secuencia se suman y tarda alrededor de cinco segundos. Si una carga paralela falla, la estructura de `coroutineScope` cancela su tarea hermana; el ViewModel relanza `CancellationException` y representa otros errores con `CargaUi.Error` para que la interfaz muestre **Reintentar**.

## Ejecución

Abre esta carpeta en Android Studio, sincroniza Gradle y ejecuta la configuración `app` en un emulador o dispositivo Android.
