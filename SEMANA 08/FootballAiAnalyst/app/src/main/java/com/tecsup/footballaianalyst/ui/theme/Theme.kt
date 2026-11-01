package com.tecsup.footballaianalyst.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(
    primary = AccentCyan,
    onPrimary = DarkBackground,
    primaryContainer = DarkSurfaceVariant,
    onPrimaryContainer = TextWhite,
    secondary = AccentGreen,
    background = DarkBackground,
    surface = DarkSurface,
    onSurface = TextWhite,
    onSurfaceVariant = TextMuted,
    error = AccentRed
)

@Composable
fun FootballAiTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = DarkColorScheme,
        content = content
    )
}
