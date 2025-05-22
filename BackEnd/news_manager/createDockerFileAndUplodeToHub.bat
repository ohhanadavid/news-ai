
call mvn clean install -DskipTests=true

set DOCKER_FILE_NAME=davidohhana/newsai_news-manager:latest

call docker build . -t %DOCKER_FILE_NAME%

set DOCKER_FILE_VERSION=davidohhana/newsai_news-manager:1.2

call docker build . -t %DOCKER_FILE_NAME%

echo %DOCKER_FILE_NAME% finished building

docker buildx build --platform linux/amd64,linux/arm64 -t %DOCKER_FILE_VERSION% -t %DOCKER_FILE_NAME% --push .

echo.
echo ✅ Build and push finished for:
echo - %DOCKER_FILE_LATEST%
echo - %DOCKER_FILE_NAME%
echo.

@REM call docker push %DOCKER_FILE_LATEST%
@REM call docker push %DOCKER_FILE_NAME%

pause


@REM call docker push %DOCKER_FILE_NAME%

@REM echo.
@REM echo uplode %DOCKER_FILE_NAME% finsh
@REM echo.

@REM set DOCKER_FILE_NAME=davidohhana/newsai_news-manager:1.2

@REM call docker build . -t %DOCKER_FILE_NAME%

@REM echo.
@REM echo build %DOCKER_FILE_NAME% finsh
@REM echo.

@REM call docker push %DOCKER_FILE_NAME%

@REM echo.
@REM echo uplode %DOCKER_FILE_NAME% finsh
@REM echo.