@echo off
echo ====================================================
echo  ABRIENDO FOOTBALL AI ANALYST EN GOOGLE CHROME...
echo ====================================================

:: Redirecciona el puerto del emulador Android a Windows para http://localhost:8080
adb forward tcp:8080 tcp:8080 2>nul

:: Abre el Dashboard en el navegador prederminado o Google Chrome
start http://localhost:8080
start "" "%~dp0web\index.html"

echo.
echo Panel Web abierto correctamente.
pause
