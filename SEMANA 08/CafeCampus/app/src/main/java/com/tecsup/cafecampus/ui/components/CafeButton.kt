package com.tecsup.cafecampus.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CafeButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    isOutlined: Boolean = false,
    containerColor: Color? = null
) {
    if (isOutlined) {
        OutlinedButton(
            onClick = onClick,
            enabled = enabled,
            shape = RoundedCornerShape(10.dp),
            modifier = modifier
                .fillMaxWidth()
                .height(48.dp)
        ) {
            Text(
                text = text,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
    } else {
        Button(
            onClick = onClick,
            enabled = enabled,
            shape = RoundedCornerShape(10.dp),
            colors = if (containerColor != null) ButtonDefaults.buttonColors(containerColor = containerColor) else ButtonDefaults.buttonColors(),
            modifier = modifier
                .fillMaxWidth()
                .height(48.dp)
        ) {
            Text(
                text = text,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
