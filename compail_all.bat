@echo off
setlocal enabledelayedexpansion

echo Compiling Maven projects in directories containing pom.xml...

for /d %%d in (*) do (
    if exist "%%d\pom.xml" (
        echo.
        echo Compiling project in directory: %%d
        pushd "%%d"
        call mvn clean install -Dmaven.test.skip=true
        if !errorlevel! neq 0 (
            echo Maven build failed for project in %%d
        ) else (
            echo Maven build successful for project in %%d
        )
        popd
    )
)

echo.
echo All Maven projects have been processed.
cd /d "%rootDir%"
call docker-compose build
call docker-compose up

pause