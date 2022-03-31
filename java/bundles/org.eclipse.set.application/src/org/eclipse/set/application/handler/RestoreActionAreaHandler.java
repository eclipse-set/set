/**
 * Copyright (c) 2021 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */

package org.eclipse.set.application.handler;

import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.CanExecute;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.set.core.services.part.ToolboxPartService;

/**
 * Restores the action area if it is not visible.
 * 
 * @author Schaefer
 */
public class RestoreActionAreaHandler {

	@Inject
	ToolboxPartService toolboxPartService;

	@CanExecute
	private boolean canExecute() {
		return !toolboxPartService.isActionAreaVisible();
	}

	@Execute
	private void execute() {
		if (!toolboxPartService.isActionAreaVisible()) {
			toolboxPartService.showActionArea();
		}
	}
}
