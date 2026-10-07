package com.example.semana05_navegacion.fit.modelo

data class ClaseGym(
    val id: Int,
    val nombre: String,
    val horario: String,
    val sala: String,
    val duracion: String,
    val cuposDisponibles: Int,
    val cuposTotales: Int,
    val descripcion: String,
    val filtro: String // "Hoy" o "Esta semana"
)

data class ReservaGym(
    val id: Int,
    val claseNombre: String,
    val horario: String,
    val sala: String,
    val estado: String // "Confirmada" o "Completada"
)

val listaFiltrosFit = listOf("Hoy", "Esta semana")

val listaClasesFitFake = listOf(
    ClaseGym(
        id = 1,
        nombre = "Yoga funcional",
        horario = "Hoy, 7:00 am",
        sala = "Sala 2",
        duracion = "50 min",
        cuposDisponibles = 5,
        cuposTotales = 12,
        descripcion = "Mejora flexibilidad, postura y fuerza corporal con posturas dinámicas.",
        filtro = "Hoy"
    ),
    ClaseGym(
        id = 2,
        nombre = "Cross Training",
        horario = "Hoy, 6:00 pm",
        sala = "Sala 1",
        duracion = "45 min",
        cuposDisponibles = 8,
        cuposTotales = 12,
        descripcion = "Entrenamiento funcional de alta intensidad. Cupos limitados.",
        filtro = "Hoy"
    ),
    ClaseGym(
        id = 3,
        nombre = "Spinning",
        horario = "Hoy, 7:30 pm",
        sala = "Sala 3",
        duracion = "45 min",
        cuposDisponibles = 3,
        cuposTotales = 15,
        descripcion = "Cardio intenso en bicicleta estática con intervalos de velocidad.",
        filtro = "Esta semana"
    )
)

val listaReservasInicialesFit = listOf(
    ReservaGym(
        id = 1,
        claseNombre = "Cross Training",
        horario = "Hoy, 6:00 pm",
        sala = "Sala 1",
        estado = "Confirmada"
    ),
    ReservaGym(
        id = 2,
        claseNombre = "Yoga funcional",
        horario = "Ayer, 7:00 am",
        sala = "Sala 2",
        estado = "Completada"
    )
)
