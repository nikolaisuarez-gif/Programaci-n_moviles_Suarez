package com.suarez.campusgps.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val CampusBlue = Color(0xFF1976B9)
private val CampusNavy = Color(0xFF173A55)
private val CampusGreen = Color(0xFF2E9D50)

private val LightColors = lightColorScheme(
    primary = CampusBlue,
    onPrimary = Color.White,
    secondary = CampusNavy,
    tertiary = CampusGreen,
    background = Color(0xFFF8FAFC),
    surface = Color.White,
    error = Color(0xFFB3261E)
)

private val DarkColors = darkColorScheme(
    primary = Color(0xFF90CAF9),
    onPrimary = Color(0xFF003258),
    secondary = Color(0xFFB7C9D9),
    tertiary = Color(0xFF81C784),
    background = Color(0xFF101820),
    surface = Color(0xFF18232D),
    surfaceVariant = Color(0xFF293743),
    onSurfaceVariant = Color(0xFFD1DCE5),
    error = Color(0xFFFFB4AB)
)

@Composable
fun CampusGpsTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    MaterialTheme(colorScheme = if (darkTheme) DarkColors else LightColors, content = content)
}
