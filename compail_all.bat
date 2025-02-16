@echo off
setlocal enabledelayedexpansion

echo Compiling Maven projects in directories containing pom.xml...

for /d %%d in (*) do (
    if exist "%%d\createDockerFileAndUplodeToHub.bat" (
        echo.
        echo Compiling project in directory: %%d
        pushd "%%d"
        call createDockerFileAndUplodeToHub.bat
        if !errorlevel! neq 0 (
            echo Maven build failed for project in %%d
        ) else (
            echo Maven build successful for project in %%d
        )
        popd
    )
)


pause