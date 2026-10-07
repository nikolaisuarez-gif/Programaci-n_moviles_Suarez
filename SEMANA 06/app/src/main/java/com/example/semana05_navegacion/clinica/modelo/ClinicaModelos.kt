package com.example.semana05_navegacion.clinica.modelo

data class Medico(
    val id: Int,
    val nombre: String,
    val especialidad: String,
    val calificacion: Double,
    val resenas: Int,
    val experiencia: String,
    val biografia: String
)

data class CitaMedica(
    val id: Int,
    val medicoNombre: String,
    val especialidad: String,
    val fecha: String,
    val hora: String,
    val estado: String // "Confirmada" o "Completada"
)

val especialidadesClinica = listOf("Cardiología", "Pediatría", "Dermatología")

val listaMedicosFake = listOf(
    Medico(
        id = 1,
        nombre = "Dra. Ana Torres",
        especialidad = "Cardióloga",
        calificacion = 4.9,
        resenas = 128,
        experiencia = "12 años exp.",
        biografia = "Especialista en arritmias e hipertensión, formación en la Clínica Mayo."
    ),
    Medico(
        id = 2,
        nombre = "Dr. Luis Vega",
        especialidad = "Pediatra",
        calificacion = 4.7,
        resenas = 95,
        experiencia = "8 años exp.",
        biografia = "Atención integral infantil y desarrollo del lactante."
    ),
    Medico(
        id = 3,
        nombre = "Dra. Rosa Díaz",
        especialidad = "Dermatóloga",
        calificacion = 4.8,
        resenas = 110,
        experiencia = "10 años exp.",
        biografia = "Especialista en dermatología clínica y procedimientos quirúrgicos menores."
    )
)

val listaCitasIniciales = listOf(
    CitaMedica(
        id = 1,
        medicoNombre = "Dra. Ana Torres",
        especialidad = "Cardióloga",
        fecha = "Viernes 27",
        hora = "10:30 am",
        estado = "Confirmada"
    ),
    CitaMedica(
        id = 2,
        medicoNombre = "Dr. Luis Vega",
        especialidad = "Pediatra",
        fecha = "Miércoles 15",
        hora = "3:00 pm",
        estado = "Completada"
    )
)
