SET dir=%~dp0
SET /a cnt=0
echo dir is: %dir%
cd /d %dir%
for /R %dir% %%i in (*.java) do (
    set /a cnt=cnt+1
    echo %%~xni>>1.txt
)
echo %cnt%
pause