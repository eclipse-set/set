::@echo off
::
:: Copyright (c) 2017 Scheidt & Bachmann System Technik GmbH.
:: All rights reserved.
::
:: @author Schaefer, Schneider
::

:: Purpose:
:: Create pdf from xml-fo

:: Configuration
IF NOT DEFINED FOP_HOME SET FOP_HOME=%UserProfile%\bin\fop-2.1
set ROOT_DIR=%~dp0/../../org.eclipse.set.application.feature/rootdir
set EXAMPLE_DIR=%~dp0
set FOP_CONFIG_FILE=%ROOT_DIR%/pdf-export/fop.xconf
set FOP_FILE=%EXAMPLE_DIR%/output.fo
set OUTPUT=%EXAMPLE_DIR%/output.pdf
set LOGFILE=%EXAMPLE_DIR%/fop.log

setlocal
cd /d %ROOT_DIR%
cmd /c call %FOP_HOME%\fop.bat -c %FOP_CONFIG_FILE% -s %FOP_FILE% -pdfprofile PDF/A-1a -pdf %OUTPUT% > %LOGFILE% 2>&1

pause
