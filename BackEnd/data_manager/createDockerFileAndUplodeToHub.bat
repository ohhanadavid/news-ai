
call mvn clean install -DskipTests=true

set DOCKER_FILE_NAME=davidohhana/newsai-data_manager:1.0

set DOCKER_FILE_LATEST=davidohhana/newsai-data_manager:latest

call docker build . -t %DOCKER_FILE_LATEST%

echo.
echo build %DOCKER_FILE_LATEST% finsh
echo.

call docker push %DOCKER_FILE_LATEST%

echo.
echo uplode %DOCKER_FILE_LATEST% finsh
echo.

call docker build . -t %DOCKER_FILE_NAME%

echo.
echo build %DOCKER_FILE_NAME% finsh
echo.

call docker push %DOCKER_FILE_NAME%

echo.
echo uplode %DOCKER_FILE_NAME% finsh
echo.

