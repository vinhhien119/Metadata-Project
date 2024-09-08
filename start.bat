@echo off

rem Start MySQL database spring-boot application in a new CMD window
start cmd /k mvn spring-boot:run

rem Wait for application to load before launching UI
timeout /t 16 /nobreak

rem Start UI website in a new CMD window
start cmd /k "cd UI && npm start"
