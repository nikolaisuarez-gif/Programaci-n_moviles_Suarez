package com.tecsup.cafecampus.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.tecsup.cafecampus.R

@Composable
fun ProductoIcono(
    icono: String,
    nombre: String = "",
    size: Dp = 48.dp,
    shape: Shape = RoundedCornerShape(12.dp),
    modifier: Modifier = Modifier
) {
    val drawableRes = obtenerDrawableProducto(nombre, icono)

    if (drawableRes != null) {
        Image(
            painter = painterResource(id = drawableRes),
            contentDescription = nombre.ifEmpty { icono },
            contentScale = ContentScale.Crop,
            modifier = modifier
                .size(size)
                .clip(shape)
        )
    } else {
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
}

fun obtenerDrawableProducto(nombre: String, icono: String): Int? {
    val nameLower = nombre.lowercase()
    val iconLower = icono.lowercase()
    return when {
        nameLower.contains("café") || nameLower.contains("cafe") || iconLower == "cafe_americano" -> R.drawable.cafe_americano
        nameLower.contains("sándwich") || nameLower.contains("sandwich") || nameLower.contains("pollo") || iconLower == "sandwich_pollo" -> R.drawable.sandwich_pollo
        nameLower.contains("menú") || nameLower.contains("menu") || iconLower == "menu_dia" -> R.drawable.menu_dia
        nameLower.contains("jugo") || nameLower.contains("naranja") || iconLower == "jugo_naranja" -> R.drawable.jugo_naranja
        nameLower.contains("empanada") || iconLower == "empanada_carne" -> R.drawable.empanada_carne
        nameLower.contains("chicha") || nameLower.contains("morada") || iconLower == "chicha_morada" -> R.drawable.chicha_morada
        iconLower == "cafe" -> R.drawable.cafe_americano
        iconLower == "jugo" -> R.drawable.jugo_naranja
        iconLower == "plato" -> R.drawable.menu_dia
        iconLower == "botella" -> R.drawable.chicha_morada
        else -> null
    }
}
