
call mvn clean install -DskipTests=true

set DOCKER_FILE_NAME=davidohhana/newsai_gateway:latest

call docker build . -t %DOCKER_FILE_NAME%
echo.
echo %DOCKER_FILE_NAME% build docker finsh
echo.
call docker push %DOCKER_FILE_NAME%
echo.
echo %DOCKER_FILE_NAME% up docker finsh
echo.
set DOCKER_FILE_NAME=davidohhana/newsai_gateway:1.0

call docker build . -t %DOCKER_FILE_NAME%
echo.
echo %DOCKER_FILE_NAME% build docker finsh
echo.
call docker push %DOCKER_FILE_NAME%
echo.
echo %DOCKER_FILE_NAME% up docker finsh
echo.