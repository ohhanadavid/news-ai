@echo off

call mvn clean install -DskipTests=true

set DOCKER_FILE_NAME=davidohhana/newsai-data_manager:1.0

set DOCKER_FILE_LATEST=davidohhana/newsai-data_manager:latest


call docker build . -t %DOCKER_FILE_LATEST%

echo.
echo build %DOCKER_FILE_LATEST% finsh
echo.

REM שימוש ב buildx (אם לא קיים, צור אחד)
@REM docker buildx create --use 1>nul 2>nul

REM בניית גרסה multi-arch עם latest ו-1.0 ודחיפה
echo.
echo 🚀 Building multi-arch image for %DOCKER_FILE_LATEST% and %DOCKER_FILE_NAME%

docker buildx build --platform linux/amd64,linux/arm64 -t %DOCKER_FILE_LATEST% -t %DOCKER_FILE_NAME% --push .

echo.
echo ✅ Build and push finished for:
echo - %DOCKER_FILE_LATEST%
echo - %DOCKER_FILE_NAME%
echo.

@REM call docker push %DOCKER_FILE_LATEST%
@REM call docker push %DOCKER_FILE_NAME%

pause

@REM call docker build . -t %DOCKER_FILE_LATEST%

@REM echo.
@REM echo build %DOCKER_FILE_LATEST% finsh
@REM echo.

@REM call docker push %DOCKER_FILE_LATEST%

@REM echo.
@REM echo uplode %DOCKER_FILE_LATEST% finsh
@REM echo.

@REM call docker build . -t %DOCKER_FILE_NAME%

@REM echo.
@REM echo build %DOCKER_FILE_NAME% finsh
@REM echo.

@REM call docker push %DOCKER_FILE_NAME%

@REM echo.
@REM echo uplode %DOCKER_FILE_NAME% finsh
@REM echo.

