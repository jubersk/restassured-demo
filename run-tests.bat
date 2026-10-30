@echo off
setlocal

echo ========================================
echo Running tests and generating Allure report...
echo ========================================
call mvn clean test allure:report
if errorlevel 1 (
    echo.
    echo Tests or report generation failed. Stopping.
    exit /b 1
)

echo.
echo ========================================
echo Done!
echo Report is ready at: target\site\allure-maven-plugin\index.html
echo You can now double-click the file to view it.
echo ========================================
endlocal