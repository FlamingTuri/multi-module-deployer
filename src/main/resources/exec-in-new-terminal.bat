@echo off

:: Cd to script current directory
cd /D "%~dp0"

set commands=%*

:: TODO: regex not working
::echo %commands% | >nul findstr /r c:"^".*"$" && (
    ::echo fail
::) || (
    :: removes leading and trailing " if commands are wrapped between them
    ::echo success
    ::set commands="%commands%"
::)S

:: Runs the command in a new terminal
start cmd.exe @cmd /k "%commands:~1,-1%"
