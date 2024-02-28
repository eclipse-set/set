/**
 * Copyright (c) 2016 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.application.handler;

import jakarta.inject.Inject;

import org.eclipse.e4.core.di.annotations.CanExecute;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.model.application.MApplication;

import org.eclipse.set.basis.IModelSession;
import org.eclipse.set.core.services.part.ToolboxPartService;

/**
 * Display the details view.
 * 
 * @author Schaefer
 */
public class DetailsHandler {

	private static String ID_DETAILS = "org.eclipse.set.application.overview"; //$NON-NLS-1$

	@CanExecute
	private static boolean canExecute(final MApplication application) {
		final IModelSession session = application.getContext()
				.get(IModelSession.class);
		return session != null;
	}

	@Inject
	ToolboxPartService partService;

	@Execute
	private void execute() {
		partService.showPart(ID_DETAILS);
	}
}
