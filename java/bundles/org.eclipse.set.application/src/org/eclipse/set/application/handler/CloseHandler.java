/**
 * Copyright (c) 2015 DB Netz AG and others.
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
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.swt.widgets.Shell;

import org.eclipse.set.basis.IModelSession;
import org.eclipse.set.core.services.dialog.DialogService;

/**
 * Dieses Steuerungsprogramm setzt die Session zurück
 * 
 * @author Bleidiessel
 */
public class CloseHandler {

	@CanExecute
	private static boolean canExecute(
			@Optional final IModelSession modelSession) {
		return modelSession != null;
	}

	@Inject
	private DialogService dialogService;

	@Execute
	private void execute(final IModelSession modelSession, final Shell shell) {
		if (modelSession != null) {
			if (modelSession.isDirty()) {
				if (!dialogService.confirmCloseUnsaved(shell)) {
					return;
				}
			}
			modelSession.close();
		}
	}
}
