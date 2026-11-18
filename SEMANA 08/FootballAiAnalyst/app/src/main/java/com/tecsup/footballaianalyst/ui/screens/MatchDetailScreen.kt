package com.tecsup.footballaianalyst.ui.screens

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
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.tecsup.footballaianalyst.model.Match
import com.tecsup.footballaianalyst.ui.components.AiAnalysisCard
import com.tecsup.footballaianalyst.ui.components.LiveBadge
import com.tecsup.footballaianalyst.ui.components.StatsBar
import com.tecsup.footballaianalyst.ui.theme.AccentCyan
import com.tecsup.footballaianalyst.viewmodel.MatchDetailViewModel

@Composable
fun MatchDetailScreen(
    match: Match,
    apiKey: String,
    vm: MatchDetailViewModel,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val stats by vm.statistics.collectAsState()
    val aiAnalysis by vm.aiAnalysis.collectAsState()
    val lineups by vm.lineups.collectAsState()

    LaunchedEffect(match.id, apiKey) {
        vm.loadMatchDetails(match, apiKey)
    }

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
                    text = "${match.homeTeam.name} vs ${match.awayTeam.name}",
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f)
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
            // Header del Partido
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(20.dp)
                ) {
                    LiveBadge(status = match.status, elapsedMinutes = match.elapsedMinutes)

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.weight(1f)
                        ) {
                            AsyncImage(
                                model = match.homeTeam.logo,
                                contentDescription = null,
                                modifier = Modifier.size(54.dp),
                                contentScale = ContentScale.Fit
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(text = match.homeTeam.name, fontWeight = FontWeight.Bold, fontSize = 15.sp, textAlign = TextAlign.Center)
                        }

                        Text(
                            text = "${match.homeScore ?: 0} - ${match.awayScore ?: 0}",
                            fontSize = 32.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = AccentCyan
                        )

                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.weight(1f)
                        ) {
                            AsyncImage(
                                model = match.awayTeam.logo,
                                contentDescription = null,
                                modifier = Modifier.size(54.dp),
                                contentScale = ContentScale.Fit
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(text = match.awayTeam.name, fontWeight = FontWeight.Bold, fontSize = 15.sp, textAlign = TextAlign.Center)
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(text = "🏟️ ${match.stadium ?: "Estadio Principal"} | ⚖️ ${match.referee ?: "Árbitro Oficial"}", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Análisis de Inteligencia Artificial
            if (aiAnalysis != null) {
                AiAnalysisCard(match = match, aiAnalysis = aiAnalysis!!)
                Spacer(modifier = Modifier.height(16.dp))
            }

            // Estadísticas Detalladas en Vivo (Corners, Faltas, Tarjetas, Tiros, Posesión)
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Text(
                        text = "📊 Estadísticas Oficiales del Partido",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    val s = stats
                    if (s != null) {
                        StatsBar(title = "🚩 Saques de Esquina (Corners)", homeValue = s.homeCorners, awayValue = s.awayCorners)
                        StatsBar(title = "⚠️ Faltas Cometidas", homeValue = s.homeFouls, awayValue = s.awayFouls)
                        StatsBar(title = "🟨 Tarjetas Amarillas", homeValue = s.homeYellowCards, awayValue = s.awayYellowCards)
                        StatsBar(title = "🟥 Tarjetas Rojas", homeValue = s.homeRedCards, awayValue = s.awayRedCards)
                        StatsBar(title = "🎯 Tiros al Arco", homeValue = s.homeShotsOnTarget, awayValue = s.awayShotsOnTarget)
                        StatsBar(title = "👟 Tiros Fuera", homeValue = s.homeShotsOffTarget, awayValue = s.awayShotsOffTarget)
                        StatsBar(title = "⚽ Posesión de Balón", homeValue = s.homePossessionPercent, awayValue = s.awayPossessionPercent, suffix = "%")
                        StatsBar(title = "🚩 Fueras de Juego (Offsides)", homeValue = s.homeOffsides, awayValue = s.awayOffsides)
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Alineaciones Tácticas
            if (lineups != null) {
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(18.dp)) {
                        Text(
                            text = "📋 Alineaciones & Formación Táctica",
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(text = lineups!!.homeLineup.team.name, fontWeight = FontWeight.Bold, color = AccentCyan)
                                Text(text = "Formación: ${lineups!!.homeLineup.formation}", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                Text(text = "Entrenador: ${lineups!!.homeLineup.coach}", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)

                                Spacer(modifier = Modifier.height(8.dp))
                                lineups!!.homeLineup.startingEleven.forEach { p ->
                                    Text(text = "${p.number}. ${p.name} (${p.position})", fontSize = 12.sp)
                                }
                            }

                            Spacer(modifier = Modifier.width(16.dp))

                            Column(modifier = Modifier.weight(1f)) {
                                Text(text = lineups!!.awayLineup.team.name, fontWeight = FontWeight.Bold, color = AccentCyan)
                                Text(text = "Formación: ${lineups!!.awayLineup.formation}", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                Text(text = "Entrenador: ${lineups!!.awayLineup.coach}", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)

                                Spacer(modifier = Modifier.height(8.dp))
                                lineups!!.awayLineup.startingEleven.forEach { p ->
                                    Text(text = "${p.number}. ${p.name} (${p.position})", fontSize = 12.sp)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
