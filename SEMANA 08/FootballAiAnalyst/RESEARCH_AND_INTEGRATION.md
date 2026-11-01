# Investigación e Integración de APIs Gratuitas de Fútbol
## Proyecto: FOOTBALL AI ANALYST

Este documento contiene la investigación comparativa de proveedores de datos deportivos gratuitos, el análisis de sus planes sin costo, límites de solicitudes y la arquitectura elegida para la aplicación Android **Football AI Analyst**.

---

## 1. Comparativa de APIs Gratuitas de Fútbol

Se investigaron los principales proveedores de datos deportivos del mercado para evaluar la disponibilidad de estadísticas clave (corners, faltas, tarjetas, tiros, alineaciones y resultados en vivo) en sus planes gratuitos sin suscripciones obligatorias ni tarjetas de crédito.

| Proveedor API | Plan Gratuito | Límites de Solicitud | Ligas Disponibles | Estadísticas Incluidas (Corners, Faltas, Tarjetas) | Requiere Tarjeta de Crédito |
| :--- | :--- | :--- | :--- | :--- | :--- |
| **API-Football (v3)** | Gratis (100% Permanente) | 100 peticiones / día (10 req/min) | Más de 1000 ligas y copas del mundo | **Sí** (Corners, Faltas, Tarjetas Amarillas/Rojas, Tiros al arco, Posesión, Alineaciones, Árbitros) | **No** |
| **football-data.org (v4)** | Gratis (100% Permanente) | 10 peticiones / minuto | Ligas Top (Premier League, La Liga, Serie A, Champions, etc.) | Resultados en vivo, Calendario, Clasificación, Goleadores (Corners/Faltas limitado en free) | **No** |
| **TheSportsDB (v1/v2)** | Gratis (Key pública `3` / Free Dev) | Sin límite estricto para desarrollo | Ligas mundiales principales | Información de equipos, escudos, historial de partidos, eventos generales | **No** |
| **Sportmonks** | Prueba Temporal | 14 días de prueba | Limitado | Requiere actualización a plan de pago | Sí |

---

## 2. Elección de la Arquitectura de APIs

Para lograr que **FOOTBALL AI ANALYST** funcione con **cero costo de suscripción** y la mayor cantidad de estadísticas reales, se implementó un **enfoque híbrido complementario**:

1. **API-Football (API-Sports v3) [Proveedor Principal de Estadísticas en Vivo y Detalle]:**
   - **Razón de elección:** Ofrece el desglose completo de estadísticas de partido en vivo: **corners, faltas, tarjetas amarillas/rojas, disparos dentro/fuera del área, posesión, alineaciones tácticas y árbitros**.
   - **Límite Gratuito:** 100 solicitudes diarias gratuitas con renovación automática cada 24 horas.

2. **football-data.org (v4) & TheSportsDB [Proveedores Secundarios de Respaldo]:**
   - **Razón de elección:** Permite consultar calendarios de partidos, tablas de posiciones y escudos de equipos con 10 peticiones por minuto de forma ilimitada sin consumir la cuota de 100 llamadas de API-Football.

---

## 3. Guía para Obtener las Claves API Gratuitas

### A. Obtener API Key de API-Football (Gratis - 100 peticiones/día)
1. Ingresar al sitio oficial: `https://dashboard.api-football.com/register`
2. Crear una cuenta gratuita completando correo y contraseña (no requiere tarjeta).
3. Confirmar la cuenta mediante el enlace enviado a tu correo.
4. Ir a la sección **Account / API Access** en el panel de control.
5. Copiar la clave API (ejemplo: `a1b2c3d4e5f6...`).

### B. Obtener API Key de football-data.org (Gratis - 10 req/minuto)
1. Ingresar a: `https://www.football-data.org/client/register`
2. Completar nombre y correo electrónico.
3. Se enviará instantáneamente la API Token a tu bandeja de entrada sin costo alguno.

---

## 4. Estadísticas Soportadas en la Aplicación

La aplicación **Football AI Analyst** consulta y muestra en tiempo real:

- **Tiros e Intentos:** Tiros totales, tiros a puerta, tiros fuera del área.
- **Disciplina:** Tarjetas amarillas, tarjetas rojas, faltas cometidas.
- **Acciones de Juego:** Saques de esquina (Corners), fuera de juego, posesión de balón (%).
- **Información del Partido:** Árbitro principal, estadio, alineaciones tácticas y eventos en tiempo real.
- **Análisis de IA:** Evaluación probabilística basada en las métricas reales del encuentro.
