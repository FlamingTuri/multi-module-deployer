@echo off

:: cd to script current directory
cd /D "%~dp0"

set commands=%1

:: runs the command in a new terminal
start cmd.exe @cmd /k "%commands:~1,-1%"
