package com.tecsup.footballaianalyst.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.tecsup.footballaianalyst.model.TeamStanding
import com.tecsup.footballaianalyst.ui.theme.AccentCyan

@Composable
fun StandingsScreen(
    modifier: Modifier = Modifier
) {
    val standings = listOf(
        TeamStanding(1, com.tecsup.footballaianalyst.model.Team(1, "Real Madrid", "https://media.api-sports.io/football/teams/541.png"), 27, 10, 8, 3, 0, 24, 7, 17, "WWWDW"),
        TeamStanding(2, com.tecsup.footballaianalyst.model.Team(2, "FC Barcelona", "https://media.api-sports.io/football/teams/529.png"), 25, 10, 8, 1, 1, 28, 10, 18, "WWLWW"),
        TeamStanding(3, com.tecsup.footballaianalyst.model.Team(3, "Atlético de Madrid", "https://media.api-sports.io/football/teams/530.png"), 20, 10, 5, 5, 0, 16, 6, 10, "DWDWD"),
        TeamStanding(4, com.tecsup.footballaianalyst.model.Team(4, "Villarreal CF", "https://media.api-sports.io/football/teams/533.png"), 18, 10, 5, 3, 2, 19, 17, 2, "WLWLD")
    )

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
                    text = "🏆 Tabla de Clasificación",
                    fontSize = 20.sp,
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
                .padding(16.dp)
        ) {
            Card(
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp)
                    ) {
                        Text(text = "#", fontWeight = FontWeight.Bold, fontSize = 12.sp, modifier = Modifier.width(24.dp))
                        Text(text = "Equipo", fontWeight = FontWeight.Bold, fontSize = 12.sp, modifier = Modifier.weight(1f))
                        Text(text = "PJ", fontWeight = FontWeight.Bold, fontSize = 12.sp, modifier = Modifier.width(30.dp), textAlign = TextAlign.Center)
                        Text(text = "DIF", fontWeight = FontWeight.Bold, fontSize = 12.sp, modifier = Modifier.width(36.dp), textAlign = TextAlign.Center)
                        Text(text = "PTS", fontWeight = FontWeight.Bold, fontSize = 12.sp, modifier = Modifier.width(36.dp), textAlign = TextAlign.Center)
                    }

                    HorizontalDivider()

                    LazyColumn {
                        items(standings) { item ->
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 10.dp)
                            ) {
                                Text(text = "${item.rank}", fontWeight = FontWeight.Bold, fontSize = 13.sp, modifier = Modifier.width(24.dp))
                                AsyncImage(model = item.team.logo, contentDescription = null, modifier = Modifier.size(22.dp))
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(text = item.team.name, fontSize = 13.sp, fontWeight = FontWeight.SemiBold, modifier = Modifier.weight(1f))
                                Text(text = "${item.playedGames}", fontSize = 12.sp, modifier = Modifier.width(30.dp), textAlign = TextAlign.Center)
                                Text(text = "${item.goalDifference}", fontSize = 12.sp, modifier = Modifier.width(36.dp), textAlign = TextAlign.Center)
                                Text(text = "${item.points}", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = AccentCyan, modifier = Modifier.width(36.dp), textAlign = TextAlign.Center)
                            }
                        }
                    }
                }
            }
        }
    }
}
