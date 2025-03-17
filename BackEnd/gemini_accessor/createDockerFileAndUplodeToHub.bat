
set DOCKER_FILE_NAME=davidohhana/newsai_llm_accessor:latest

call docker build . -t %DOCKER_FILE_NAME%

echo.
echo build %DOCKER_FILE_NAME% finsh
echo.

call docker push %DOCKER_FILE_NAME%

echo.
echo uplode %DOCKER_FILE_NAME% finsh
echo.

set DOCKER_FILE_NAME=davidohhana/newsai_llm_accessor:1.0

call docker build . -t %DOCKER_FILE_NAME%

echo.
echo build %DOCKER_FILE_NAME% finsh
echo.

call docker push %DOCKER_FILE_NAME%

echo.
echo uplode %DOCKER_FILE_NAME% finsh
echo.