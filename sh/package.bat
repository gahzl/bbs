@echo off
@echo [INFO] package war 

cd /d %~dp0
call cd /d ..
del /q "src\main\webapp\WEB-INF\lib\*" 
call mvn clean package -DskipTests 
cd sh
pause