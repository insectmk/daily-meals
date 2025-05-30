@echo off
echo Generating CHANGELOG.md...
git cliff -o CHANGELOG.md
if %errorlevel% equ 0 (
    echo CHANGELOG.md generated successfully.
) else (
    echo Failed to generate CHANGELOG.md.
)
pause