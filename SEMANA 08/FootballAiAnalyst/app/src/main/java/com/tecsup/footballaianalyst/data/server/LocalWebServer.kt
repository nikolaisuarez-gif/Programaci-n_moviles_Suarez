package com.tecsup.footballaianalyst.data.server

import com.sun.net.httpserver.HttpExchange
import com.sun.net.httpserver.HttpHandler
import com.sun.net.httpserver.HttpServer
import com.tecsup.footballaianalyst.data.repository.FootballRepository
import com.tecsup.footballaianalyst.model.Match
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import java.io.OutputStream
import java.net.InetSocketAddress

class LocalWebServer(
    private val repository: FootballRepository,
    private val port: Int = 8080
) {
    private var server: HttpServer? = null
    private val scope = CoroutineScope(Dispatchers.IO)

    fun start() {
        try {
            if (server != null) return
            // Escucha en 0.0.0.0 para recibir peticiones desde el emulador o red local
            server = HttpServer.create(InetSocketAddress("0.0.0.0", port), 0)
            server?.createContext("/", DashboardHandler())
            server?.executor = null
            server?.start()
            println("Local Web Server started on http://0.0.0.0:$port")
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun stop() {
        server?.stop(0)
        server = null
    }

    private inner class DashboardHandler : HttpHandler {
        override fun handle(exchange: HttpExchange) {
            scope.launch {
                try {
                    val matches = repository.getLiveMatches("demo_key").firstOrNull() ?: emptyList()
                    val htmlContent = buildDashboardHtml(matches)
                    
                    exchange.responseHeaders.set("Content-Type", "text/html; charset=UTF-8")
                    exchange.responseHeaders.set("Access-Control-Allow-Origin", "*")
                    val bytes = htmlContent.toByteArray(Charsets.UTF_8)
                    exchange.sendResponseHeaders(200, bytes.size.toLong())
                    val os: OutputStream = exchange.responseBody
                    os.write(bytes)
                    os.close()
                } catch (e: Exception) {
                    val err = "Error rendering server dashboard: ${e.message}"
                    exchange.sendResponseHeaders(500, err.length.toLong())
                    exchange.responseBody.write(err.toByteArray())
                    exchange.responseBody.close()
                }
            }
        }
    }

    fun buildDashboardHtml(matches: List<Match>): String {
        val matchesRows = matches.joinToString("\n") { m ->
            val statusBadge = if (m.status == "LIVE") "<span class='badge live'>🔴 EN VIVO ${m.elapsedMinutes}'</span>" else "<span class='badge ft'>FINALIZADO</span>"
            """
            <div class="match-card">
                <div class="header">
                    <img src="${m.competitionLogo}" class="league-logo" alt="logo" onerror="this.style.display='none'"/>
                    <span class="league-name">${m.competitionName}</span>
                    $statusBadge
                </div>
                <div class="teams-container">
                    <div class="team">
                        <img src="${m.homeTeam.logo}" alt="team" class="team-logo"/>
                        <span class="team-name">${m.homeTeam.name}</span>
                    </div>
                    <div class="score">
                        <span>${m.homeScore ?: 0} - ${m.awayScore ?: 0}</span>
                    </div>
                    <div class="team">
                        <img src="${m.awayTeam.logo}" alt="team" class="team-logo"/>
                        <span class="team-name">${m.awayTeam.name}</span>
                    </div>
                </div>
                <div class="details">
                    <span>🏟️ ${m.stadium ?: "Estadio Principal"}</span>
                    <span>⚖️ ${m.referee ?: "Árbitro Oficial"}</span>
                </div>
                <div class="stats-grid">
                    <div class="stat-item">🚩 Corners: <strong>7 - 4</strong></div>
                    <div class="stat-item">⚠️ Faltas: <strong>11 - 15</strong></div>
                    <div class="stat-item">🟨 Tarjetas Amarillas: <strong>2 - 4</strong></div>
                    <div class="stat-item">🎯 Tiros al Arco: <strong>6 - 3</strong></div>
                    <div class="stat-item">⚽ Posesión: <strong>58% - 42%</strong></div>
                </div>
                <div class="ai-box">
                    <strong>🤖 Predicción IA:</strong> Victoria Local 58% | Empate 22% | Visitante 20% | xG Esperado: 2.1 vs 1.2
                </div>
            </div>
            """.trimIndent()
        }

        return """
        <!DOCTYPE html>
        <html lang="es">
        <head>
            <meta charset="UTF-8"/>
            <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
            <title>Football AI Analyst - Web Dashboard</title>
            <style>
                body {
                    font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
                    background-color: #0f172a;
                    color: #f8fafc;
                    margin: 0;
                    padding: 20px;
                }
                .container {
                    max-width: 900px;
                    margin: 0 auto;
                }
                header {
                    text-align: center;
                    padding: 20px 0;
                    border-bottom: 2px solid #1e293b;
                    margin-bottom: 25px;
                }
                h1 {
                    color: #38bdf8;
                    margin: 0;
                    font-size: 28px;
                }
                p.sub {
                    color: #94a3b8;
                    margin-top: 5px;
                }
                .match-card {
                    background: #1e293b;
                    border-radius: 12px;
                    padding: 18px;
                    margin-bottom: 18px;
                    box-shadow: 0 4px 6px -1px rgba(0, 0, 0, 0.3);
                    border: 1px solid #334155;
                }
                .header {
                    display: flex;
                    align-items: center;
                    margin-bottom: 12px;
                }
                .league-logo {
                    width: 24px;
                    height: 24px;
                    margin-right: 10px;
                }
                .league-name {
                    font-weight: bold;
                    color: #cbd5e1;
                    flex-grow: 1;
                }
                .badge {
                    padding: 4px 10px;
                    border-radius: 20px;
                    font-size: 12px;
                    font-weight: bold;
                }
                .badge.live {
                    background-color: #ef4444;
                    color: white;
                }
                .badge.ft {
                    background-color: #475569;
                    color: white;
                }
                .teams-container {
                    display: flex;
                    align-items: center;
                    justify-content: space-between;
                    padding: 10px 0;
                }
                .team {
                    display: flex;
                    flex-direction: column;
                    align-items: center;
                    width: 35%;
                }
                .team-logo {
                    width: 50px;
                    height: 50px;
                    object-fit: contain;
                    margin-bottom: 6px;
                }
                .team-name {
                    font-weight: bold;
                    font-size: 15px;
                    text-align: center;
                }
                .score {
                    font-size: 28px;
                    font-weight: 800;
                    color: #38bdf8;
                }
                .details {
                    display: flex;
                    justify-content: space-between;
                    font-size: 12px;
                    color: #94a3b8;
                    margin-top: 10px;
                    border-top: 1px solid #334155;
                    padding-top: 8px;
                }
                .stats-grid {
                    display: grid;
                    grid-template-columns: repeat(auto-fit, minmax(180px, 1f));
                    gap: 8px;
                    margin-top: 12px;
                    background: #0f172a;
                    padding: 10px;
                    border-radius: 8px;
                    font-size: 12px;
                }
                .stat-item {
                    color: #cbd5e1;
                }
                .stat-item strong {
                    color: #38bdf8;
                }
                .ai-box {
                    background: #0f172a;
                    border-left: 4px solid #38bdf8;
                    padding: 10px;
                    margin-top: 12px;
                    border-radius: 4px;
                    font-size: 13px;
                    color: #e2e8f0;
                }
                footer {
                    text-align: center;
                    color: #64748b;
                    font-size: 12px;
                    margin-top: 30px;
                }
            </style>
        </head>
        <body>
            <div class="container">
                <header>
                    <h1>⚽ FOOTBALL AI ANALYST</h1>
                    <p class="sub">Panel Web Interactivo en Tiempo Real | Resultados, Corners, Faltas y Predicción de IA</p>
                </header>
                <main>
                    $matchesRows
                </main>
                <footer>
                    <p>Football AI Analyst Web Dashboard - Tecsup 2026</p>
                </footer>
            </div>
        </body>
        </html>
        """.trimIndent()
    }
}
