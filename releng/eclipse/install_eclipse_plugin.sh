#! /bin/bash
#
# Copyright (c) 2025 Scheidt & Bachmann System Technik GmbH.
# All rights reserved.
#
# @author Truong
#
# Synopsis: install eclipse ide plugin
#
# Purpose:
# Install requirement eclipse ide plugin
if [ -z "$ECLIPSE_HOME" ]; then
  echo "Error: ECLIPSE_HOME not found"
  exit 1
fi
ECLIPSE_EXEC="$ECLIPSE_HOME/eclipse"
UPDATE_SITES="https://download.eclipse.org/eclipse/updates/4.32,
  https://download.eclipse.org/releases/2024-06,
  https://download.eclipse.org/ecoretools/updates/releases/3.5.1/2023-06/,
  https://checkstyle.org/eclipse-cs-update-site"
PLUGINS="org.eclipse.platform.feature.group,
  org.eclipse.rcp.feature.group,
  org.eclipse.pde.feature.group,
  org.eclipse.equinox.sdk.feature.group,
  org.eclipse.xtext.sdk.feature.group,
  org.eclipse.emf.sdk.feature.group,
  org.eclipse.emf.validation.sdk.feature.group,
  org.eclipse.emf.ecoretools.design.feature.group,
  org.eclipse.m2e.pde.feature.feature.group,
  net.sf.eclipsecs.feature.group,
  org.eclipse.swtbot.eclipse.feature.group,
  org.eclipse.e4.core.tools.feature.source.feature.group"

"$ECLIPSE_EXEC" \
  -nosplash \
  -application org.eclipse.equinox.p2.director \
  -repository "$UPDATE_SITES" \
  -installIU "$PLUGINS"

if [ $? -eq 0 ]; then
  echo "Install Plugins success"
else
  echo "Install Plugins error"
  exit 1
fi