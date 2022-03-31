/**
 * Copyright (c) 2019 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.utils.exception;

import java.util.function.Consumer;

import org.eclipse.set.basis.extensions.Exceptions;
import org.eclipse.set.core.services.dialog.DialogService;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

/**
 * Implementation of an exception handler, using a {@link DialogService} for
 * reporting the problem to the user.
 * 
 * @author Schaefer
 */
public class ExceptionHandler implements Consumer<Exception> {

	final DialogService dialogService;
	final Shell shell;

	/**
	 * @param shell
	 *            the shell used as a parent for the error dialog
	 * @param dialogService
	 *            the dialog service
	 */
	public ExceptionHandler(final Shell shell,
			final DialogService dialogService) {
		this.dialogService = dialogService;
		this.shell = shell;
	}

	@Override
	public void accept(final Exception e) {
		if (!Exceptions.isCausedByThreadDeath(e)) {
			Display.getDefault().syncExec(new Runnable() {
				@Override
				public void run() {
					dialogService.error(shell, e);
				}
			});
		}
	}
}
