@echo off

echo Current directory: %cd%
echo Building frontend...
call npm run build || goto :build_failed

echo.
echo Build finished.
echo.

:: מעבר לתיקיית static
pushd ..\..\BackEnd\newsAiGateway\src\main\resources\static || goto :path_not_found

echo Cleaning old static files...
del /Q *.*
for /d %%i in (*) do rmdir /s /q "%%i"

echo Copying new frontend build...
xcopy /e /y /i ..\..\..\..\..\..\FrontEnd\news-ai\dist\*.* .\

popd
echo.
echo Copy complete!

:: מעבר להרצת docker
pushd ..\..\BackEnd\newsAiGateway || goto :path_not_found
call createDockerFileAndUplodeToHub.bat || goto :docker_failed
popd

echo.
echo ✅ Docker image created and uploaded to hub.
goto :eof

:path_not_found
echo ❌ Error: One of the paths was not found. Please check the directory structure.
exit /b 1

:build_failed
echo ❌ Build failed. Aborting process.
exit /b 1

:docker_failed
echo ❌ Docker script failed. Please check the script file.
exit /b 1
