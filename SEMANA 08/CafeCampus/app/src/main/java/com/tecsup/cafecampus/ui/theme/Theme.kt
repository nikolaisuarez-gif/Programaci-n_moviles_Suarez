package com.tecsup.cafecampus.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.tecsup.cafecampus.model.Rol

private val LightClienteColorScheme = lightColorScheme(
    primary = ClientePrimary,
    onPrimary = ClienteOnPrimary,
    primaryContainer = ClienteContainer,
    onPrimaryContainer = ClienteOnContainer,
    background = ClienteBackground,
    surface = Color.White
)

private val LightAdminColorScheme = lightColorScheme(
    primary = AdminPrimary,
    onPrimary = AdminOnPrimary,
    primaryContainer = AdminContainer,
    onPrimaryContainer = AdminOnContainer,
    background = AdminBackground,
    surface = Color.White
)

private val DarkColorScheme = darkColorScheme(
    primary = ClientePrimary,
    onPrimary = Color.White,
    primaryContainer = Color(0xFF9A3412),
    onPrimaryContainer = Color(0xFFFFEDD5),
    background = DarkBackground,
    surface = DarkSurface,
    onSurface = DarkOnSurface
)

@Composable
fun CafeTheme(
    rol: Rol = Rol.CLIENTE,
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        darkTheme -> DarkColorScheme
        rol == Rol.ADMIN -> LightAdminColorScheme
        else -> LightClienteColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
