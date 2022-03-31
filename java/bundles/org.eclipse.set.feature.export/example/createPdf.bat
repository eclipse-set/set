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
:: set TABLE=sslz
set TABLE=schriftfeld
IF NOT DEFINED FOP_HOME SET FOP_HOME=%UserProfile%\bin\fop-2.1
set ROOT_DIR=%~dp0/../../org.eclipse.set.feature/rootdir
set EXAMPLE_DIR=%~dp0
set FOP_CONFIG_FILE=%ROOT_DIR%/pdf-export/fop.xconf
set TABLE_DOCUMENT=%EXAMPLE_DIR%/%TABLE%-fop.xml
set TABLE_TEMPLATE=%ROOT_DIR%/pdf-export/%TABLE%_vorlage.xsl
set OUTPUT=%EXAMPLE_DIR%/output.pdf
set LOGFILE=%EXAMPLE_DIR%/fop.log
::set PDF_PROFILE=-pdfprofile PDF/A-1a
set PDF_PROFILE=

setlocal
cd /d %ROOT_DIR%
:: cmd /c call %FOP_HOME%\fop.bat -c %FOP_CONFIG_FILE% -xml %TABLE_DOCUMENT% -xsl %TABLE_TEMPLATE% %PDF_PROFILE% -pdf %OUTPUT% > %LOGFILE% 2>&1
cmd /c call %FOP_HOME%\fop.bat -x -d -v -c %FOP_CONFIG_FILE% -xml %TABLE_DOCUMENT% -xsl %TABLE_TEMPLATE% %PDF_PROFILE% -pdf %OUTPUT%

"C:\Program Files (x86)\Adobe\Acrobat Reader DC\Reader\AcroRd32.exe" %OUTPUT%
:: pause
