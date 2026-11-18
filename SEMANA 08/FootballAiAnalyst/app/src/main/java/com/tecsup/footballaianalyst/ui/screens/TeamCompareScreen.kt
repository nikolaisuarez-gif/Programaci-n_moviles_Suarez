package com.tecsup.footballaianalyst.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.tecsup.footballaianalyst.model.Team
import com.tecsup.footballaianalyst.ui.theme.AccentCyan
import com.tecsup.footballaianalyst.ui.theme.AccentGreen
import com.tecsup.footballaianalyst.ui.theme.AccentYellow
import com.tecsup.footballaianalyst.viewmodel.TeamCompareViewModel

@Composable
fun TeamCompareScreen(
    vm: TeamCompareViewModel,
    popularTeams: List<Team>,
    modifier: Modifier = Modifier
) {
    val teamA by vm.teamA.collectAsState()
    val teamB by vm.teamB.collectAsState()
    val comparison by vm.comparison.collectAsState()
    val sampleSize by vm.sampleSize.collectAsState()

    var showTeamASelector by remember { mutableStateOf(false) }
    var showTeamBSelector by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.surface)
                    .padding(16.dp)
            ) {
                Text(
                    text = "⚔️ Comparador de Equipos & Pronóstico IA",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = AccentCyan
                )
            }
        }
    ) { paddingValues ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            // Selección de Equipo A vs Equipo B
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Card(
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    modifier = Modifier.weight(1f)
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(14.dp)
                    ) {
                        if (teamA != null) {
                            AsyncImage(model = teamA!!.logo, contentDescription = null, modifier = Modifier.size(44.dp))
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(text = teamA!!.name, fontWeight = FontWeight.Bold, fontSize = 14.sp, textAlign = TextAlign.Center)
                        } else {
                            Text("Seleccionar Equipo A", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Button(onClick = { showTeamASelector = true }) { Text("Cambiar A") }
                    }
                }

                Spacer(modifier = Modifier.width(12.dp))

                Card(
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    modifier = Modifier.weight(1f)
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(14.dp)
                    ) {
                        if (teamB != null) {
                            AsyncImage(model = teamB!!.logo, contentDescription = null, modifier = Modifier.size(44.dp))
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(text = teamB!!.name, fontWeight = FontWeight.Bold, fontSize = 14.sp, textAlign = TextAlign.Center)
                        } else {
                            Text("Seleccionar Equipo B", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Button(onClick = { showTeamBSelector = true }) { Text("Cambiar B") }
                    }
                }
            }

            if (showTeamASelector) {
                Spacer(modifier = Modifier.height(10.dp))
                Text("Selecciona Equipo A:", fontWeight = FontWeight.Bold)
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    popularTeams.take(4).forEach { t ->
                        FilterChip(selected = teamA?.id == t.id, onClick = { vm.setTeamA(t); showTeamASelector = false }, label = { Text(t.name.take(10)) })
                    }
                }
            }

            if (showTeamBSelector) {
                Spacer(modifier = Modifier.height(10.dp))
                Text("Selecciona Equipo B:", fontWeight = FontWeight.Bold)
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    popularTeams.drop(1).take(4).forEach { t ->
                        FilterChip(selected = teamB?.id == t.id, onClick = { vm.setTeamB(t); showTeamBSelector = false }, label = { Text(t.name.take(10)) })
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Muestra de partidos
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                listOf(5, 10, 20).forEach { size ->
                    FilterChip(
                        selected = sampleSize == size,
                        onClick = { vm.setSampleSize(size) },
                        label = { Text("Últimos $size partidos") }
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            val comp = comparison
            if (comp == null) {
                Text(
                    text = "Selecciona dos equipos para comparar sus promedios de córners, goles, faltas, tarjetas y probabilidad de victoria con IA.",
                    fontSize = 13.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(vertical = 20.dp)
                )
            } else {
                val stA = comp.teamA
                val stB = comp.teamB
                val ai = comp.aiPrediction

                // Predicción de IA basada en las estadísticas históricas
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(18.dp)) {
                        Text(
                            text = "🤖 Predicción IA basada en los últimos ${stA.sampleMatchesCount} partidos",
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp,
                            color = AccentCyan
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        // Barra de probabilidades
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(12.dp)
                                .clip(RoundedCornerShape(6.dp))
                        ) {
                            Box(modifier = Modifier.fillMaxHeight().weight(ai.teamAWinProb.toFloat()).background(AccentCyan))
                            Box(modifier = Modifier.fillMaxHeight().weight(ai.drawProb.toFloat()).background(AccentYellow))
                            Box(modifier = Modifier.fillMaxHeight().weight(ai.teamBWinProb.toFloat()).background(AccentGreen))
                        }

                        Spacer(modifier = Modifier.height(6.dp))

                        Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                            Text("${stA.team.name}: ${ai.teamAWinProb}%", fontSize = 11.sp, color = AccentCyan, fontWeight = FontWeight.Bold)
                            Text("Empate: ${ai.drawProb}%", fontSize = 11.sp, color = AccentYellow, fontWeight = FontWeight.Bold)
                            Text("${stB.team.name}: ${ai.teamBWinProb}%", fontSize = 11.sp, color = AccentGreen, fontWeight = FontWeight.Bold)
                        }

                        HorizontalDivider(modifier = Modifier.padding(vertical = 10.dp))

                        Text(text = "⚽ Goles Esperados (xG): ${stA.team.name} ${ai.expectedGoalsA} - ${ai.expectedGoalsB} ${stB.team.name}", fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
                        Text(text = "🚩 Corners Proyectados: ${ai.predictedCorners} corners en el partido", fontSize = 13.sp, color = AccentCyan)
                        Text(text = "📊 Probabilidad > 2.5 Goles: ${ai.over25GoalsProbability}%", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        Text(text = "🚩 Probabilidad > 9.5 Corners: ${ai.over95CornersProbability}%", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Tabla Comparativa Lado a Lado
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(text = "📊 Tabla Comparativa Directa", fontWeight = FontWeight.Bold, fontSize = 15.sp)
                        Spacer(modifier = Modifier.height(10.dp))

                        CompareRow(label = "% Victorias", valA = "${stA.winRatePercent}%", valB = "${stB.winRatePercent}%")
                        CompareRow(label = "Goles A Favor / partido", valA = "%.2f".format(stA.goalsForPerMatch), valB = "%.2f".format(stB.goalsForPerMatch))
                        CompareRow(label = "Goles En Contra / partido", valA = "%.2f".format(stA.goalsAgainstPerMatch), valB = "%.2f".format(stB.goalsAgainstPerMatch))
                        HorizontalDivider(modifier = Modifier.padding(vertical = 6.dp))

                        CompareRow(label = "Corners A Favor / partido", valA = "%.1f".format(stA.cornersForPerMatch), valB = "%.1f".format(stB.cornersForPerMatch))
                        CompareRow(label = "Corners En Contra / partido", valA = "%.1f".format(stA.cornersAgainstPerMatch), valB = "%.1f".format(stB.cornersAgainstPerMatch))
                        HorizontalDivider(modifier = Modifier.padding(vertical = 6.dp))

                        CompareRow(label = "Faltas / partido", valA = "%.1f".format(stA.foulsPerMatch), valB = "%.1f".format(stB.foulsPerMatch))
                        CompareRow(label = "Tarjetas Amarillas / partido", valA = "%.1f".format(stA.yellowCardsPerMatch), valB = "%.1f".format(stB.yellowCardsPerMatch))
                        CompareRow(label = "Posesión Promedio", valA = "%.1f%%".format(stA.averagePossessionPercent), valB = "%.1f%%".format(stB.averagePossessionPercent))
                        HorizontalDivider(modifier = Modifier.padding(vertical = 6.dp))

                        CompareRow(label = "% Partidos > 2.5 Goles", valA = "${stA.over25GoalsPercent}%", valB = "${stB.over25GoalsPercent}%")
                        CompareRow(label = "% Partidos > 9.5 Corners", valA = "${stA.over95CornersPercent}%", valB = "${stB.over95CornersPercent}%")
                    }
                }
            }
        }
    }
}

@Composable
private fun CompareRow(label: String, valA: String, valB: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Text(text = valA, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = AccentCyan, modifier = Modifier.width(60.dp))
        Text(text = label, fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurface, modifier = Modifier.weight(1f), textAlign = TextAlign.Center)
        Text(text = valB, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = AccentGreen, modifier = Modifier.width(60.dp), textAlign = TextAlign.End)
    }
}
