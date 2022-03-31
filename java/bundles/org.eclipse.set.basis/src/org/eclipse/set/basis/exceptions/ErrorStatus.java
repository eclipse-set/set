/**
 * Copyright (c) 2019 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.basis.exceptions;

import org.eclipse.core.runtime.IStatus;

/**
 * Extends {@link IStatus} with an error title and user message.
 * 
 * @author schaefer
 */
public interface ErrorStatus {

	/**
	 * @return the title intended to use as the title of the error dialog
	 */
	String getErrorTitle();

	/**
	 * @return the {@link IStatus}
	 */
	IStatus getStatus();

	/**
	 * @return a localized high level, user message (in contrast to the
	 *         {@link IStatus#getMessage()}, which is intended to be used as a
	 *         technical explanation)
	 */
	String getUserMessage();
}
