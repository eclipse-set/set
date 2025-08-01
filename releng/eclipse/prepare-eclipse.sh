#! /bin/bash
#
# Copyright (c) 2025 Scheidt & Bachmann System Technik GmbH.
# All rights reserved.
#
# @author Heine
#
# Synopsis: prepare eclipse ide
#
# Purpose:
# Downloads eclipse ide with correct version and prepares it for development
finish() {
  read -p "Script finished. Press any key to proceed..." x
  exit $1
}

if [ -z "$ECLIPSE_HOME" ]; then
  echo "Error: ECLIPSE_HOME not found"
  exit 1
fi

askForInstall=""
if [ ! -d "$ECLIPSE_HOME" ]; then
  askForInstall="Your eclipse home is not existing."
elif [ ! "$(ls -A "$ECLIPSE_HOME" 2>/dev/null)" ]; then
  askForInstall="Your eclipse home is empty."
elif [ ! -f "$ECLIPSE_HOME/eclipse" ]; then
  echo "Error: Your eclipse home is existing but does not contain eclipse. Please provide another eclipse home"
  finish 1
fi

if [ ! -z "$askForInstall" ]; then
  echo "$askForInstall"
  read -p "Do you want to install eclipse there? (y or n)" install

  if [ "$install" != "y" ]; then
    echo "Error: ECLIPSE_HOME invalid"
    finish 1
  fi

  if [[ "$OSTYPE" == "linux-gnu" ]]; then
    echo "Downloading eclipse for Linux..."
    mkdir ./tmp-eclipse-download
    curl -o ./tmp-eclipse-download/eclipse.tar.gz https://mirrors.dotsrc.org/eclipse/technology/epp/downloads/release/2025-03/R/eclipse-java-2025-03-R-linux-gtk-x86_64.tar.gz
    tar -xzf ./tmp-eclipse-download/eclipse.tar.gz --directory ./tmp-eclipse-download --warning=no-unknown-keyword
    sleep 5s
    mkdir -p $ECLIPSE_HOME
    mv ./tmp-eclipse-download/eclipse/* $ECLIPSE_HOME
    rm -R ./tmp-eclipse-download
  fi

  # Check for macOS (darwin is the OS type identifier used by macOS)
  if [[ "$OSTYPE" == "darwin"* ]]; then
    echo "Downloading eclipse for macOS..."
    curl -o ./eclipse.dmg https://mirror.dkm.cz/eclipse/technology/epp/downloads/release/2025-03/R/eclipse-java-2025-03-R-macosx-cocoa-x86_64.dmg
    echo "TODO: How to install eclipse under macos... Please install or unpack the downloaded eclipse.dmg by yourself to $ECLIPSE_HOME and restart the script."
    finish 1
  fi

  # Check for Windows (Windows Subsystem for Linux, or WSL, uses a linux-gnu like OS type)
  if [[ "$OSTYPE" == "msys" || "$OSTYPE" == "cygwin" || "$OSTYPE" == "win32" ]]; then
    echo "Downloading eclipse for Windows..."
    mkdir ./tmp-eclipse-download
    curl -o ./tmp-eclipse-download/eclipse.zip https://ftp.snt.utwente.nl/pub/software/eclipse/technology/epp/downloads/release/2025-03/R/eclipse-java-2025-03-R-win32-x86_64.zip
    unzip -q ./tmp-eclipse-download/eclipse.zip -d ./tmp-eclipse-download
    mkdir -p $ECLIPSE_HOME
    mv ./tmp-eclipse-download/eclipse/* $ECLIPSE_HOME
    rm -R ./tmp-eclipse-download
  fi
fi

eclipseBuildId=$(cat $ECLIPSE_HOME/configuration/config.ini | grep eclipse.buildId)
if [ "$eclipseBuildId" != "eclipse.buildId=4.35.0.20250306-0811" ]; then
  echo "You are using a different version than the preferred version 2025-03 (4.35) of eclipse: ${eclipseBuildId}."
  echo "You can rerun this script with an nonexisting ECLIPSE_HOME and this script will download the correct version of eclipse for you."
  read -p "Do you want to continue with the current eclipse? (y or n) " continue
  if [ "$continue" != "y" ]; then
    finish 1
  fi 
fi

cd $(dirname "$0")
echo "Install eclipse plugins now..."
./install_eclipse_plugin.sh

echo "Prepare development now..."
node ./development_prepare.js

finish 0