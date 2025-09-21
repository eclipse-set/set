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
if [ ! -f "$ECLIPSE_EXEC" ]; then
  echo "Error: No eclipse executable found in $ECLIPSE_HOME"
  exit 1
fi

UPDATE_SITES="https://download.eclipse.org/eclipse/updates/4.35,
  https://download.eclipse.org/releases/2025-03,
  https://download.eclipse.org/ecoretools/updates/releases/3.5.2/2023-06/,
  https://checkstyle.org/eclipse-cs-update-site,
  http://download.eclipse.org/ecp/releases/releases_127"
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
  org.eclipse.e4.core.tools.feature.source.feature.group,
  org.eclipse.emf.ecp.emfforms.sdk.feature.feature.group"

"$ECLIPSE_EXEC" \
  -nosplash \
  -application org.eclipse.equinox.p2.director \
  -repository "$UPDATE_SITES" \
  -installIU "$PLUGINS" \
  | grep -v 'DEBUG' # hide DEBUG log statements

if [ $? -eq 0 ]; then
  echo "Install Plugins success"
else
  echo "Install Plugins error"
  exit 1
fi
