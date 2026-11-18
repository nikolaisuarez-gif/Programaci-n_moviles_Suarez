package com.suarez.campusgps.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val CampusBlue = Color(0xFF1976B9)
private val CampusNavy = Color(0xFF173A55)
private val CampusGreen = Color(0xFF2E9D50)

private val Colors = lightColorScheme(
    primary = CampusBlue,
    onPrimary = Color.White,
    secondary = CampusNavy,
    tertiary = CampusGreen,
    background = Color(0xFFF8FAFC),
    surface = Color.White,
    error = Color(0xFFB3261E)
)

@Composable
fun CampusGpsTheme(content: @Composable () -> Unit) {
    MaterialTheme(colorScheme = Colors, content = content)
}
