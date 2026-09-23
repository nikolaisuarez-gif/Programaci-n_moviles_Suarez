package com.example.semana05_navegacion.clinicasalud.navigation

sealed class ClinicaScreen(val route: String) {
    object Inicio : ClinicaScreen("clinica_inicio")
    object MisCitas : ClinicaScreen("clinica_mis_citas")
    object HistorialMedico : ClinicaScreen("clinica_historial_medico")
    object Perfil : ClinicaScreen("clinica_perfil")

    object DoctorDetail : ClinicaScreen("clinica_doctor_detail/{doctorId}") {
        fun createRoute(doctorId: Int): String = "clinica_doctor_detail/$doctorId"
    }

    object AgendarCita : ClinicaScreen("clinica_agendar_cita/{doctorId}") {
        fun createRoute(doctorId: Int): String = "clinica_agendar_cita/$doctorId"
    }

    object ConfirmacionCita : ClinicaScreen("clinica_confirmacion_cita/{doctorId}/{fecha}/{hora}") {
        fun createRoute(doctorId: Int, fecha: String, hora: String): String =
            "clinica_confirmacion_cita/$doctorId/$fecha/$hora"
    }
}
