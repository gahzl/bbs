@echo off
@echo [INFO] elipse:eclipse

cd /d %~dp0
call cd /d ..
call mvn eclipse:clean eclipse:eclipse -DdownloadSources=true
cd sh
pause
