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

    private val _nombreRegistro = MutableStateFlow("")
    val nombreRegistro: StateFlow<String> = _nombreRegistro.asStateFlow()

    private val _correoRegistro = MutableStateFlow("")
    val correoRegistro: StateFlow<String> = _correoRegistro.asStateFlow()

    private val _claveRegistro = MutableStateFlow("")
    val claveRegistro: StateFlow<String> = _claveRegistro.asStateFlow()

    private val _confirmarClaveRegistro = MutableStateFlow("")
    val confirmarClaveRegistro: StateFlow<String> = _confirmarClaveRegistro.asStateFlow()

    private val _errorRegistro = MutableStateFlow<String?>(null)
    val errorRegistro: StateFlow<String?> = _errorRegistro.asStateFlow()

    fun onNombreRegistroChange(valor: String) {
        _nombreRegistro.value = valor
        _errorRegistro.value = null
    }

    fun onCorreoRegistroChange(valor: String) {
        _correoRegistro.value = valor
        _errorRegistro.value = null
    }

    fun onClaveRegistroChange(valor: String) {
        _claveRegistro.value = valor
        _errorRegistro.value = null
    }

    fun onConfirmarClaveRegistroChange(valor: String) {
        _confirmarClaveRegistro.value = valor
        _errorRegistro.value = null
    }

    fun registrarCliente(onSuccess: (Rol) -> Unit) {
        val nombre = _nombreRegistro.value.trim()
        val correo = _correoRegistro.value.trim()
        val clave = _claveRegistro.value.trim()
        val confirmar = _confirmarClaveRegistro.value.trim()

        if (nombre.isEmpty() || correo.isEmpty() || clave.isEmpty()) {
            _errorRegistro.value = "Ingresa tu nombre, correo y contraseña"
            return
        }
        if (!correo.contains("@") || !correo.contains(".")) {
            _errorRegistro.value = "Ingresa un correo o Gmail válido"
            return
        }
        if (clave.length < 4) {
            _errorRegistro.value = "La contraseña debe tener al menos 4 caracteres"
            return
        }
        if (clave != confirmar) {
            _errorRegistro.value = "Las contraseñas no coinciden"
            return
        }

        viewModelScope.launch {
            val registrado = repo.registrarCliente(nombre, correo, clave)
            if (registrado != null) {
                _usuarioActivo.value = registrado
                _rolSeleccionado.value = Rol.CLIENTE
                _correo.value = correo
                _clave.value = clave
                _errorRegistro.value = null
                _nombreRegistro.value = ""
                _correoRegistro.value = ""
                _claveRegistro.value = ""
                _confirmarClaveRegistro.value = ""
                onSuccess(Rol.CLIENTE)
            } else {
                _errorRegistro.value = "Este correo ya se encuentra registrado"
            }
        }
    }

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
