@echo off
@echo [INFO] Install jar into local repository

cd /d %~dp0
call cd /d ..
call mvn clean install -DskipTests 
cd sh
pause