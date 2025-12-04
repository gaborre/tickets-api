@echo off
setlocal enabledelayedexpansion

echo ============================================================
echo   Construyendo y ejecutando tickets-api
echo ============================================================
echo.

REM -------------------------------
REM 1. Limpiar y construir con Maven
REM -------------------------------
echo [1/5] Ejecutando mvn clean...
call mvn clean
if errorlevel 1 (
    echo Error: mvn clean falló.
    exit /b 1
)

echo [2/5] Ejecutando mvn install...
call mvn install
if errorlevel 1 (
    echo Error: mvn install falló.
    exit /b 1
)

REM -------------------------------
REM 2. Eliminar imagen previa
REM -------------------------------
echo.
echo [3/5] Borrando imagen previa tickets-api si existe...
call docker rmi -f tickets-api >nul 2>&1
echo Imagen previa eliminada (o no existía).

REM -------------------------------
REM 3. Build de la imagen Docker
REM -------------------------------
echo.
echo [4/5] Construyendo nueva imagen Docker...
call docker build -t tickets-api .
if errorlevel 1 (
    echo Error: docker build falló.
    exit /b 1
)

REM -------------------------------
REM 4. Ejecutar contenedor
REM -------------------------------
echo.
echo [5/5] Ejecutando contenedor en modo detach...
call docker run -p 8080:8080 --name tickets-api-container tickets-api
if errorlevel 1 (
    echo Error: docker run falló.
    exit /b 1
)

echo.
echo ============================================================
echo  Contenedor iniciado correctamente.
echo  Nombre del contenedor: tickets-api-container
echo  Aplicación disponible en: http://localhost:8080/
echo ============================================================
echo.

echo Comandos útiles:
echo - Ver logs:   docker logs -f tickets-api-container
echo - Parar:      docker stop tickets-api-container
echo - Eliminar:   docker rm tickets-api-container
echo - Eliminar imagen: docker rmi -f tickets-api
echo.

endlocal
exit /b 0
