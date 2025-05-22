
set DOCKER_FILE_NAME=davidohhana/newsai_llm_accessor:latest
set DOCKER_FILE_VERSION=davidohhana/newsai_llm_accessor:1.0

call docker build . -t %DOCKER_FILE_NAME%

echo.
echo build %DOCKER_FILE_NAME% finsh
echo.


docker buildx build --platform linux/amd64,linux/arm64 -t %DOCKER_FILE_NAME% -t %DOCKER_FILE_VERSION% --push .

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

@REM set DOCKER_FILE_NAME=davidohhana/newsai_llm_accessor:1.0

@REM call docker build . -t %DOCKER_FILE_NAME%

@REM echo.
@REM echo build %DOCKER_FILE_NAME% finsh
@REM echo.

@REM call docker push %DOCKER_FILE_NAME%

@REM echo.
@REM echo uplode %DOCKER_FILE_NAME% finsh
@REM echo.