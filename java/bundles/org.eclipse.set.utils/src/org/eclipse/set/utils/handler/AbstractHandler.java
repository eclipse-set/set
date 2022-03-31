/**
 * Copyright (c) 2017 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.utils.handler;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.swt.widgets.Shell;

import org.eclipse.set.basis.exceptions.UserAbortion;
import org.eclipse.set.core.services.dialog.DialogService;

/**
 * Abstract handler for common functions like error handling.
 * 
 * @author Schaefer
 */
public abstract class AbstractHandler {

	protected static void reportError(final Shell shell, final Exception e,
			final DialogService dialogService, final String title,
			final String message,
			@SuppressWarnings("unused") final String cause) {
		Throwable reportedException = e;
		if (e instanceof InvocationTargetException) {
			reportedException = ((InvocationTargetException) e)
					.getTargetException();
		}
		if (reportedException instanceof UserAbortion) {
			// We don't report an user abortion
			return;
		}
		// use new error API
		dialogService.error(shell, title, message, e);
	}
}
