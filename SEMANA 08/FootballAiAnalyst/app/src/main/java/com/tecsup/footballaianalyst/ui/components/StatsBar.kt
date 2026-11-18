package com.tecsup.footballaianalyst.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tecsup.footballaianalyst.ui.theme.AccentCyan
import com.tecsup.footballaianalyst.ui.theme.AccentGreen

@Composable
fun StatsBar(
    title: String,
    homeValue: Int,
    awayValue: Int,
    suffix: String = "",
    modifier: Modifier = Modifier
) {
    val total = (homeValue + awayValue).coerceAtLeast(1)
    val homeWeight = (homeValue.toFloat() / total).coerceIn(0.05f, 0.95f)

    Column(modifier = modifier.fillMaxWidth().padding(vertical = 6.dp)) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "$homeValue$suffix",
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                color = AccentCyan
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = title,
                fontSize = 13.sp,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = "$awayValue$suffix",
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                color = AccentGreen
            )
        }

        Spacer(modifier = Modifier.height(4.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
                .clip(RoundedCornerShape(4.dp))
                .background(MaterialTheme.colorScheme.primaryContainer)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(homeWeight)
                    .background(AccentCyan)
            )
            Spacer(modifier = Modifier.width(2.dp))
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f - homeWeight)
                    .background(AccentGreen)
            )
        }
    }
}
