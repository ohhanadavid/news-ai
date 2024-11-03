@echo off
setlocal enabledelayedexpansion

REM Specify the root directory containing all your Maven projects
set rootDir=%~dp0



cd /d "%rootDir%\data_manager"
echo $~dp0
REM Loop through each directory and run Maven clean install if pom.xml exists
call mvn clean install -Dmaven.test.skip=true

@REM cd /d "%rootDir%\user_accessor"
@REM echo $~dp0
@REM REM Loop through each directory and run Maven clean install if pom.xml exists
@REM call mvn clean install -Dmaven.test.skip=true


@REM cd /d "%rootDir%\newsdata_io_accessor"
@REM echo $~dp0
@REM REM Loop through each directory and run Maven clean install if pom.xml exists
@REM call mvn clean install -Dmaven.test.skip=true


@REM cd /d "%rootDir%\mail_Sender_engine"
@REM echo $~dp0
@REM REM Loop through each directory and run Maven clean install if pom.xml exists
@REM call mvn clean install -Dmaven.test.skip=true



@REM newsaimanager
@REM useraccessor
@REM newsdataioaccessor
@REM mailsenderengine
@REM geminiaccessor

@REM cd /d "%rootDir%"
@REM call docker-compose build    newsaimanager 
@REM call docker-compose up 

endlocal
