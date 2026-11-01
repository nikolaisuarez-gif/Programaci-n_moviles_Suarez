package com.tecsup.footballaianalyst.model

data class TeamStats(
    val team: Team,
    val competitionName: String,
    val season: String,
    val sampleMatchesCount: Int, // p.ej. 5, 10, 20 o temporada completa
    val locationFilter: String, // "ALL", "HOME", "AWAY"
    
    // Récord de partidos
    val totalMatches: Int,
    val wins: Int,
    val draws: Int,
    val losses: Int,
    val winRatePercent: Int,
    
    // Goles
    val goalsForPerMatch: Double,
    val goalsAgainstPerMatch: Double,
    val totalGoalsFor: Int,
    val totalGoalsAgainst: Int,
    
    // Saques de esquina (Corners)
    val cornersForPerMatch: Double,
    val cornersAgainstPerMatch: Double,
    
    // Faltas y Tarjetas
    val foulsPerMatch: Double,
    val yellowCardsPerMatch: Double,
    val redCardsPerMatch: Double,
    
    // Tiros y Posesión
    val totalShotsPerMatch: Double,
    val shotsOnTargetPerMatch: Double,
    val averagePossessionPercent: Double,
    
    // Porcentajes Históricos de Ocurrencia de Goles y Corners
    val over15GoalsPercent: Int,
    val over25GoalsPercent: Int,
    val over35GoalsPercent: Int,
    val over85CornersPercent: Int,
    val over95CornersPercent: Int,
    val over105CornersPercent: Int
)

data class TeamComparison(
    val teamA: TeamStats,
    val teamB: TeamStats,
    val aiPrediction: AiPredictionResult
)

data class AiPredictionResult(
    val teamAWinProb: Int,
    val drawProb: Int,
    val teamBWinProb: Int,
    val expectedGoalsA: Double,
    val expectedGoalsB: Double,
    val predictedCorners: Double,
    val over25GoalsProbability: Int,
    val over95CornersProbability: Int,
    val tacticalAnalysis: String,
    val sampleSummary: String
)
