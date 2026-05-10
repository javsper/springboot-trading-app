@echo off
setlocal EnableExtensions
cd /d "%~dp0."

REM ==========================================================================
REM  springboot-trading — start Spring Boot + Angular (optional Kafka via Docker)
REM
REM  Prerequisites (not started by this script):
REM    - PostgreSQL with database trading_db_dev, user postgres
REM    - POSTGRES_PW set (see below)
REM    - JDK 17, Maven wrapper (mvnw.cmd), Node.js/npm for the frontend
REM    - lib\tws-api.jar present (see README)
REM
REM  Usage (CMD, from repo folder):
REM    set POSTGRES_PW=yourpassword
REM    run-all.bat
REM    run-all.bat full
REM
REM  Usage (PowerShell, from repo folder — note .\ prefix is required):
REM    $env:POSTGRES_PW = 'yourpassword'
REM    .\run-all.bat
REM    .\run-all.bat full
REM    (Or run .\run-all.ps1 — thin wrapper that forwards arguments.)
REM
REM  Modes:
REM    run-all.bat local    Profile "local" — no Kafka/TWS required at startup ^(default^)
REM    run-all.bat full     Docker Compose Kafka first, then default Spring profile
REM ==========================================================================

set "MODE=%~1"
if "%MODE%"=="" set "MODE=local"

if /i "%MODE%"=="local" goto MODE_LOCAL
if /i "%MODE%"=="full" goto MODE_FULL

echo Unknown argument: %MODE%
echo Use "local" or "full".
exit /b 1

:MODE_LOCAL
set "SPRING_PROFILES_ACTIVE=local"
goto AFTER_MODE

:MODE_FULL
set "SPRING_PROFILES_ACTIVE="
where docker >nul 2>&1
if errorlevel 1 (
  echo ERROR: Docker not found in PATH. Install Docker Desktop or use: run-all.bat local
  pause
  exit /b 1
)
echo Starting Kafka ^(Docker Compose^)...
docker compose up -d
if errorlevel 1 (
  pause
  exit /b 1
)
echo Waiting for Kafka to become reachable...
timeout /t 8 /nobreak >nul

:AFTER_MODE
if not defined POSTGRES_PW (
  echo ERROR: POSTGRES_PW is not set.
  echo   Example: set POSTGRES_PW=yourpassword
  echo   Then run this script again.
  pause
  exit /b 1
)

if not defined AUTOTRADE_DELTA_VALUE set AUTOTRADE_DELTA_VALUE=0.3
if not defined AUTOTRADE_ORDER_LIMIT_VALUE set AUTOTRADE_ORDER_LIMIT_VALUE=1000
if not defined AUTOTRADE_SPREAD_SIZE set AUTOTRADE_SPREAD_SIZE=2
if not defined AUTOTRADE_QUANTITY_OF_STRATEGIES set AUTOTRADE_QUANTITY_OF_STRATEGIES=1

where npm >nul 2>&1
if errorlevel 1 (
  echo ERROR: npm not found. Install Node.js and add it to PATH.
  pause
  exit /b 1
)

if not exist "springboot-trading-frontend\node_modules" (
  echo Installing frontend dependencies ^(npm install^)...
  pushd springboot-trading-frontend
  call npm install
  if errorlevel 1 (
    popd
    pause
    exit /b 1
  )
  popd
)

echo Starting Spring Boot ^(new window^)...
start "springboot-trading API" cmd /k cd /d "%CD%" ^&^& call mvnw.cmd -pl springboot-trading-web -am spring-boot:run

timeout /t 2 /nobreak >nul

echo Starting Angular dev server ^(new window^)...
start "springboot-trading UI" cmd /k cd /d "%CD%\springboot-trading-frontend" ^&^& npm start

echo.
echo --- Started ---
echo Backend:  http://localhost:8080/
echo Frontend: http://localhost:4200/
if /i "%MODE%"=="full" echo Kafka:    Docker Compose ^(zookeeper + kafka^)
if /i "%MODE%"=="local" echo Profile:  local ^(Kafka listeners off at startup^)
echo.
echo Close the API/UI windows to stop those processes.
echo For full mode, stop Kafka with: docker compose down
pause
