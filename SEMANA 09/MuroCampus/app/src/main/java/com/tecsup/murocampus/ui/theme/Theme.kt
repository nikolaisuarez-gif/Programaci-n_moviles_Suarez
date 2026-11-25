package com.tecsup.murocampus.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val MuroColors = lightColorScheme(
    primary = Color(0xFF1976A8),
    onPrimary = Color.White,
    secondary = Color(0xFF2783B4),
    background = Color(0xFFFFFCFF),
    surface = Color(0xFFFFFCFF),
    error = Color(0xFFB3261E)
)

@Composable
fun MuroCampusTheme(content: @Composable () -> Unit) {
    MaterialTheme(colorScheme = MuroColors, content = content)
}
