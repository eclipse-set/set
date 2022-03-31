::@echo off
::
:: Copyright (c) 2017 Scheidt & Bachmann System Technik GmbH.
:: All rights reserved.
::
:: @author Schaefer, Schneider
::

:: Purpose:
:: Create xml-fo from table document + table template

:: Configuration
IF NOT DEFINED FOP_HOME SET FOP_HOME=%UserProfile%\bin\fop-2.1
set ROOT_DIR=%~dp0/../../org.eclipse.set.feature/rootdir
set EXAMPLE_DIR=%~dp0
set TABLE_DOCUMENT=%EXAMPLE_DIR%/ssld-fop.xml
set TABLE_TEMPLATE=%ROOT_DIR%/pdf-export/ssld_vorlage.xsl

setlocal
cd /d %ROOT_DIR%
cmd /c call %FOP_HOME%\fop.bat -xml %TABLE_DOCUMENT% -xsl %TABLE_TEMPLATE% -foout %EXAMPLE_DIR%/output.fo

:: pause
