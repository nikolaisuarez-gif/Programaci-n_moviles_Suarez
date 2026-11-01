package com.tecsup.footballaianalyst.model

data class AiAnalysis(
    val matchId: Int,
    val homeWinProbabilityPercent: Int,
    val drawProbabilityPercent: Int,
    val awayWinProbabilityPercent: Int,
    val expectedGoalsHome: Double,
    val expectedGoalsAway: Double,
    val tacticalInsight: String,
    val keyStatPrediction: String,
    val cornerPrediction: String,
    val disciplineRiskLevel: String // "ALTO (Faltas/Tarjetas)", "MEDIO", "BAJO"
)
