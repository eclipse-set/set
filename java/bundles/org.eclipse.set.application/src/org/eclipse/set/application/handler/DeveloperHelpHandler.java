/**
 * Copyright (c) 2021 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.application.handler;

import jakarta.inject.Inject;

import org.eclipse.e4.core.di.annotations.Evaluate;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.set.basis.constants.ToolboxConstants;
import org.eclipse.set.core.services.part.ToolboxPartService;
import org.eclipse.set.utils.ToolboxConfiguration;

/**
 * This handler controls an developer help dialog.
 * 
 * @author Peters
 */
public final class DeveloperHelpHandler {

	@Inject
	ToolboxPartService partService;

	@Execute
	private void execute() {
		partService.showPart(ToolboxConstants.WEB_DEVELOPER_HELP_PART_ID);
	}

	/**
	 * @return whether the developer help should be visible
	 */
	@Evaluate
	public static boolean isDeveloperHelpVisible() {
		return ToolboxConfiguration.isDevelopmentMode();
	}
}
