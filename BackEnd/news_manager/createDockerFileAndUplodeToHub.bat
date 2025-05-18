
call mvn clean install -DskipTests=true

set DOCKER_FILE_NAME=davidohhana/newsai_news-manager:latest

call docker build . -t %DOCKER_FILE_NAME%

echo.
echo build %DOCKER_FILE_NAME% finsh
echo.

call docker push %DOCKER_FILE_NAME%

echo.
echo uplode %DOCKER_FILE_NAME% finsh
echo.

set DOCKER_FILE_NAME=davidohhana/newsai_news-manager:1.1

call docker build . -t %DOCKER_FILE_NAME%

echo.
echo build %DOCKER_FILE_NAME% finsh
echo.

call docker push %DOCKER_FILE_NAME%

echo.
echo uplode %DOCKER_FILE_NAME% finsh
echo.