package com.tecsup.footballaianalyst.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.tecsup.footballaianalyst.model.Team
import com.tecsup.footballaianalyst.ui.theme.AccentCyan
import com.tecsup.footballaianalyst.ui.theme.AccentGreen
import com.tecsup.footballaianalyst.ui.theme.AccentRed
import com.tecsup.footballaianalyst.ui.theme.AccentYellow
import com.tecsup.footballaianalyst.viewmodel.TeamStatsViewModel

@Composable
fun TeamDetailScreen(
    team: Team,
    vm: TeamStatsViewModel,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val stats by vm.teamStats.collectAsState()
    val sampleSize by vm.sampleSize.collectAsState()
    val locationFilter by vm.locationFilter.collectAsState()

    Scaffold(
        topBar = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 10.dp)
            ) {
                IconButton(onClick = onBackClick) {
                    Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                }
                Text(
                    text = "Estadísticas de " + team.name,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
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
            // Header de Equipo
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(18.dp)
                ) {
                    AsyncImage(
                        model = team.logo,
                        contentDescription = null,
                        modifier = Modifier.size(56.dp),
                        contentScale = ContentScale.Fit
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Column {
                        Text(
                            text = team.name,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = stats?.competitionName ?: "Temporada 2024/2025",
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Selector de Muestra de Partidos (5, 10, 20 Partidos)
            Text(text = "Muestra de Partidos:", fontWeight = FontWeight.Bold, fontSize = 14.sp)
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.padding(vertical = 6.dp)) {
                listOf(5, 10, 20).forEach { size ->
                    FilterChip(
                        selected = sampleSize == size,
                        onClick = { vm.setSampleSize(size) },
                        label = { Text("Últimos $size partidos") }
                    )
                }
            }

            // Selector de Ubicación (Todos / Solo Local / Solo Visitante)
            Text(text = "Filtro de Localía:", fontWeight = FontWeight.Bold, fontSize = 14.sp)
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.padding(vertical = 6.dp)) {
                listOf("ALL" to "Todos los partidos", "HOME" to "Solo Local", "AWAY" to "Solo Visitante").forEach { (key, label) ->
                    FilterChip(
                        selected = locationFilter == key,
                        onClick = { vm.setLocationFilter(key) },
                        label = { Text(label) }
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            val s = stats
            if (s == null) {
                Text("Cargando estadísticas de equipo...", color = MaterialTheme.colorScheme.onSurfaceVariant)
            } else {
                // Muestra utilizada
                Text(
                    text = "📌 Estadísticas calculadas en base a los últimos ${s.sampleMatchesCount} partidos (${if (s.locationFilter == "HOME") "Solo Local" else if (s.locationFilter == "AWAY") "Solo Visitante" else "General"}).",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = AccentCyan
                )

                Spacer(modifier = Modifier.height(14.dp))

                // Récord de Resultados (W/D/L)
                Card(
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(text = "🏆 Rendimiento y Victorias", fontWeight = FontWeight.Bold, fontSize = 15.sp)
                        Spacer(modifier = Modifier.height(10.dp))
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text("Victorias", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                Text("${s.wins}", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = AccentGreen)
                            }
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text("Empates", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                Text("${s.draws}", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = AccentYellow)
                            }
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text("Derrotas", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                Text("${s.losses}", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = AccentRed)
                            }
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text("% Victoria", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                Text("${s.winRatePercent}%", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = AccentCyan)
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Estadísticas Obligatorias por Partido
                Card(
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(text = "📊 Promedios Obligatorios por Partido", fontWeight = FontWeight.Bold, fontSize = 15.sp)
                        Spacer(modifier = Modifier.height(10.dp))

                        StatRowItem(label = "🚩 Corners a Favor por partido", value = "%.1f".format(s.cornersForPerMatch))
                        StatRowItem(label = "🚩 Corners en Contra por partido", value = "%.1f".format(s.cornersAgainstPerMatch))
                        HorizontalDivider(modifier = Modifier.padding(vertical = 6.dp))

                        StatRowItem(label = "⚽ Goles a Favor por partido", value = "%.2f".format(s.goalsForPerMatch))
                        StatRowItem(label = "⚽ Goles en Contra por partido", value = "%.2f".format(s.goalsAgainstPerMatch))
                        HorizontalDivider(modifier = Modifier.padding(vertical = 6.dp))

                        StatRowItem(label = "⚠️ Faltas Cometidas por partido", value = "%.1f".format(s.foulsPerMatch))
                        StatRowItem(label = "🟨 Tarjetas Amarillas por partido", value = "%.1f".format(s.yellowCardsPerMatch))
                        StatRowItem(label = "🟥 Tarjetas Rojas por partido", value = "%.2f".format(s.redCardsPerMatch))
                        HorizontalDivider(modifier = Modifier.padding(vertical = 6.dp))

                        StatRowItem(label = "🎯 Tiros Totales por partido", value = "%.1f".format(s.totalShotsPerMatch))
                        StatRowItem(label = "🎯 Tiros a Puerta por partido", value = "%.1f".format(s.shotsOnTargetPerMatch))
                        StatRowItem(label = "⚽ Posesión Promedio", value = "%.1f%%".format(s.averagePossessionPercent))
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Porcentajes Históricos de Ocurrencia (Overs Goles y Corners)
                Card(
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(text = "📈 Ocurrencia Histórica de Goles y Corners", fontWeight = FontWeight.Bold, fontSize = 15.sp)
                        Spacer(modifier = Modifier.height(10.dp))

                        StatRowItem(label = "% Partidos con Más de 1.5 Goles", value = "${s.over15GoalsPercent}%")
                        StatRowItem(label = "% Partidos con Más de 2.5 Goles", value = "${s.over25GoalsPercent}%")
                        StatRowItem(label = "% Partidos con Más de 3.5 Goles", value = "${s.over35GoalsPercent}%")
                        HorizontalDivider(modifier = Modifier.padding(vertical = 6.dp))

                        StatRowItem(label = "% Partidos con Más de 8.5 Corners", value = "${s.over85CornersPercent}%")
                        StatRowItem(label = "% Partidos con Más de 9.5 Corners", value = "${s.over95CornersPercent}%")
                        StatRowItem(label = "% Partidos con Más de 10.5 Corners", value = "${s.over105CornersPercent}%")
                    }
                }
            }
        }
    }
}

@Composable
private fun StatRowItem(label: String, value: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Text(text = label, fontSize = 13.sp, color = MaterialTheme.colorScheme.onSurface, modifier = Modifier.weight(1f))
        Text(text = value, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = AccentCyan)
    }
}
