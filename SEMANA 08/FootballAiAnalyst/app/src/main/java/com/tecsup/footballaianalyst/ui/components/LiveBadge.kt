package com.tecsup.footballaianalyst.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tecsup.footballaianalyst.ui.theme.AccentGreen
import com.tecsup.footballaianalyst.ui.theme.AccentRed

@Composable
fun LiveBadge(
    status: String,
    elapsedMinutes: Int? = null,
    modifier: Modifier = Modifier
) {
    val isLive = status == "LIVE"
    val bgColor = if (isLive) AccentRed else Color(0xFF475569)
    val textVal = if (isLive) "EN VIVO ${elapsedMinutes?.let { "$it'" } ?: ""}" else "FINALIZADO"

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .clip(RoundedCornerShape(20.dp))
            .background(bgColor)
            .padding(horizontal = 10.dp, vertical = 4.dp)
    ) {
        if (isLive) {
            Spacer(
                modifier = Modifier
                    .size(8.dp)
                    .clip(CircleShape)
                    .background(Color.White)
            )
            Spacer(modifier = Modifier.width(6.dp))
        }
        Text(
            text = textVal,
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 11.sp
        )
    }
}
