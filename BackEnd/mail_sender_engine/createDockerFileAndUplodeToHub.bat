
call mvn clean install -DskipTests=true

set DOCKER_FILE_NAME=davidohhana/mailsender_engine:latest

call docker build . -t %DOCKER_FILE_NAME%

echo %DOCKER_FILE_NAME% finished building

call docker push %DOCKER_FILE_NAME%

set DOCKER_FILE_NAME=davidohhana/mailsender_engine:1.0

call docker build . -t %DOCKER_FILE_NAME%

call docker push %DOCKER_FILE_NAME%