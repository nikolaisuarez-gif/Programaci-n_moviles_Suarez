package com.tecsup.footballaianalyst.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
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
import com.tecsup.footballaianalyst.model.AiAnalysis
import com.tecsup.footballaianalyst.model.Match
import com.tecsup.footballaianalyst.ui.theme.AccentCyan
import com.tecsup.footballaianalyst.ui.theme.AccentGreen
import com.tecsup.footballaianalyst.ui.theme.AccentYellow

@Composable
fun AiAnalysisCard(
    match: Match,
    aiAnalysis: AiAnalysis,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = BorderStroke(1.dp, AccentCyan),
        modifier = modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(18.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "🤖 Predicción y Análisis IA de Partido",
                    fontWeight = FontWeight.Bold,
                    fontSize = 17.sp,
                    color = AccentCyan
                )
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Barra de Probabilidades de Resultado (Victoria Local / Empate / Visitante)
            Text(
                text = "Probabilidades de Victoria:",
                fontSize = 13.sp,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(6.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(12.dp)
                    .clip(RoundedCornerShape(6.dp))
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(aiAnalysis.homeWinProbabilityPercent.toFloat().coerceAtLeast(1f))
                        .background(AccentCyan)
                )
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(aiAnalysis.drawProbabilityPercent.toFloat().coerceAtLeast(1f))
                        .background(AccentYellow)
                )
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(aiAnalysis.awayWinProbabilityPercent.toFloat().coerceAtLeast(1f))
                        .background(AccentGreen)
                )
            }

            Spacer(modifier = Modifier.height(6.dp))

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "${match.homeTeam.name}: ${aiAnalysis.homeWinProbabilityPercent}%", fontSize = 11.sp, color = AccentCyan, fontWeight = FontWeight.Bold)
                Text(text = "Empate: ${aiAnalysis.drawProbabilityPercent}%", fontSize = 11.sp, color = AccentYellow, fontWeight = FontWeight.Bold)
                Text(text = "${match.awayTeam.name}: ${aiAnalysis.awayWinProbabilityPercent}%", fontSize = 11.sp, color = AccentGreen, fontWeight = FontWeight.Bold)
            }

            HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp))

            Text(
                text = "📌 " + aiAnalysis.tacticalInsight,
                fontSize = 13.sp,
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "⚽ " + aiAnalysis.keyStatPrediction,
                fontSize = 13.sp,
                fontWeight = FontWeight.SemiBold,
                color = AccentCyan
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "🚩 " + aiAnalysis.cornerPrediction,
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "🟨 Risk de Disciplina: " + aiAnalysis.disciplineRiskLevel,
                fontSize = 12.sp,
                color = AccentYellow,
                fontWeight = FontWeight.Medium
            )
        }
    }
}
