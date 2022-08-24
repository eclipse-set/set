/**
 * Copyright (c) 2019 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.application.handler;

import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.set.basis.constants.ToolboxConstants;
import org.eclipse.set.core.services.part.ToolboxPartService;

/**
 * News and release notes.
 * 
 * @author Schaefer
 */
public class ToolboxNews {

	@Inject
	private ToolboxPartService partService;

	@Execute
	private void execute() {
		partService.showPart(ToolboxConstants.WEB_NEWS_PART_ID);
	}
}
