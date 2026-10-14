package com.tecsup.cafecampus.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Coffee
import androidx.compose.material.icons.filled.Fastfood
import androidx.compose.material.icons.filled.FreeBreakfast
import androidx.compose.material.icons.filled.LocalBar
import androidx.compose.material.icons.filled.LocalCafe
import androidx.compose.material.icons.filled.LocalDrink
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun ProductoIcono(
    icono: String,
    size: Dp = 48.dp,
    modifier: Modifier = Modifier
) {
    val vectorIcon = when (icono.lowercase()) {
        "cafe" -> Icons.Default.LocalCafe
        "plato" -> Icons.Default.Restaurant
        "jugo" -> Icons.Default.LocalDrink
        "pan" -> Icons.Default.FreeBreakfast
        "botella" -> Icons.Default.LocalBar
        "dulce" -> Icons.Default.Coffee
        "sandwich" -> Icons.Default.Fastfood
        else -> Icons.Default.Restaurant
    }

    Box(
        modifier = modifier
            .size(size)
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.primaryContainer),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = vectorIcon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(8.dp)
        )
    }
}
