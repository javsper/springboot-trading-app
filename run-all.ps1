# Starts Spring Boot + Angular via run-all.bat (same modes: local | full).
# PowerShell requires .\ to run scripts in the current directory.
#
# Examples:
#   $env:POSTGRES_PW = 'yourpassword'
#   .\run-all.ps1
#   .\run-all.ps1 full

& "$PSScriptRoot\run-all.bat" @args
