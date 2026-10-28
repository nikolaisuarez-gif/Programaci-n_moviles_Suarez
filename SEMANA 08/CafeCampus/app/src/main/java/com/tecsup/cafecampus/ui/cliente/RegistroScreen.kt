package com.tecsup.cafecampus.ui.cliente

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tecsup.cafecampus.model.Rol
import com.tecsup.cafecampus.ui.components.AppTopBar
import com.tecsup.cafecampus.ui.components.CafeButton
import com.tecsup.cafecampus.ui.components.CafeTextField
import com.tecsup.cafecampus.viewmodel.SesionViewModel

@Composable
fun RegistroScreen(
    vm: SesionViewModel,
    onRegistroExitoso: (Rol) -> Unit,
    onIrALoginClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val nombre by vm.nombreRegistro.collectAsState()
    val correo by vm.correoRegistro.collectAsState()
    val clave by vm.claveRegistro.collectAsState()
    val confirmarClave by vm.confirmarClaveRegistro.collectAsState()
    val errorRegistro by vm.errorRegistro.collectAsState()

    Scaffold(
        topBar = {
            AppTopBar(
                title = "Registro de Cliente",
                onBackClick = onIrALoginClick
            )
        }
    ) { paddingValues ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Crear Cuenta",
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.fillMaxWidth()
            )

            Text(
                text = "Regístrate como cliente con tu nombre, Gmail y contraseña",
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.outline,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp)
            )

            CafeTextField(
                value = nombre,
                onValueChange = { vm.onNombreRegistroChange(it) },
                label = "Nombre completo",
                leadingIcon = {
                    Icon(imageVector = Icons.Default.Person, contentDescription = null)
                }
            )

            Spacer(modifier = Modifier.height(14.dp))

            CafeTextField(
                value = correo,
                onValueChange = { vm.onCorreoRegistroChange(it) },
                label = "Correo / Gmail",
                leadingIcon = {
                    Icon(imageVector = Icons.Default.Email, contentDescription = null)
                }
            )

            Spacer(modifier = Modifier.height(14.dp))

            CafeTextField(
                value = clave,
                onValueChange = { vm.onClaveRegistroChange(it) },
                label = "Contraseña",
                visualTransformation = PasswordVisualTransformation(),
                leadingIcon = {
                    Icon(imageVector = Icons.Default.Lock, contentDescription = null)
                }
            )

            Spacer(modifier = Modifier.height(14.dp))

            CafeTextField(
                value = confirmarClave,
                onValueChange = { vm.onConfirmarClaveRegistroChange(it) },
                label = "Confirmar contraseña",
                visualTransformation = PasswordVisualTransformation(),
                leadingIcon = {
                    Icon(imageVector = Icons.Default.Lock, contentDescription = null)
                }
            )

            if (errorRegistro != null) {
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = errorRegistro!!,
                    color = MaterialTheme.colorScheme.error,
                    fontSize = 13.sp,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            Spacer(modifier = Modifier.height(28.dp))

            CafeButton(
                text = "Registrarse",
                onClick = {
                    vm.registrarCliente { rol -> onRegistroExitoso(rol) }
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            TextButton(onClick = onIrALoginClick) {
                Text(
                    text = "¿Ya tienes cuenta? Inicia sesión",
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 14.sp
                )
            }
        }
    }
}
