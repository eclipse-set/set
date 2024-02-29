/**
 * Copyright (c) 2016 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.application.handler;

import java.nio.file.Paths;

import jakarta.inject.Inject;
import jakarta.inject.Named;

import org.eclipse.e4.core.di.annotations.Execute;

import org.eclipse.set.core.services.part.ToolboxPartService;

/**
 * Can execute a pdf-view.
 * 
 * @author Schaefer
 */
public class ViewPdfHandler {

	@Inject
	ToolboxPartService partService;

	@Execute
	private void execute(
			@Named("org.eclipse.set.application.commandparameter.file") final String file) {
		partService.showPdfPart(Paths.get(file));
	}
}
