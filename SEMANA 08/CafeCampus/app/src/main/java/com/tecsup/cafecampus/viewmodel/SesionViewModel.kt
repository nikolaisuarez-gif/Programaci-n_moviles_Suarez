package com.tecsup.cafecampus.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tecsup.cafecampus.data.local.entity.Usuario
import com.tecsup.cafecampus.data.repository.CafeRepository
import com.tecsup.cafecampus.model.Rol
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SesionViewModel(private val repo: CafeRepository) : ViewModel() {

    private val _rolSeleccionado = MutableStateFlow(Rol.CLIENTE)
    val rolSeleccionado: StateFlow<Rol> = _rolSeleccionado.asStateFlow()

    private val _correo = MutableStateFlow("juan@campus.test")
    val correo: StateFlow<String> = _correo.asStateFlow()

    private val _clave = MutableStateFlow("1234")
    val clave: StateFlow<String> = _clave.asStateFlow()

    private val _usuarioActivo = MutableStateFlow<Usuario?>(null)
    val usuarioActivo: StateFlow<Usuario?> = _usuarioActivo.asStateFlow()

    private val _errorLogin = MutableStateFlow<String?>(null)
    val errorLogin: StateFlow<String?> = _errorLogin.asStateFlow()

    fun seleccionarRol(rol: Rol) {
        _rolSeleccionado.value = rol
        _errorLogin.value = null
        if (rol == Rol.CLIENTE) {
            _correo.value = "juan@campus.test"
            _clave.value = "1234"
        } else {
            _correo.value = "admin@cafe.test"
            _clave.value = "admin"
        }
    }

    fun onCorreoChange(nuevo: String) {
        _correo.value = nuevo
        _errorLogin.value = null
    }

    fun onClaveChange(nuevo: String) {
        _clave.value = nuevo
        _errorLogin.value = null
    }

    fun login(onSuccess: (Rol) -> Unit) {
        viewModelScope.launch {
            val user = repo.login(_correo.value.trim(), _clave.value.trim())
            if (user != null) {
                _usuarioActivo.value = user
                val rolUser = if (user.rol == Rol.ADMIN.name) Rol.ADMIN else Rol.CLIENTE
                _rolSeleccionado.value = rolUser
                onSuccess(rolUser)
            } else {
                _errorLogin.value = "Correo o contraseña incorrectos"
            }
        }
    }

    fun cerrarSesion() {
        _usuarioActivo.value = null
        seleccionarRol(Rol.CLIENTE)
    }

    fun cambiarRolManual(nuevoRol: Rol) {
        _rolSeleccionado.value = nuevoRol
    }
}
