/**
 * Copyright (c) 2019 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.core.dialogservice

import org.eclipse.set.basis.exceptions.ErrorStatus
import org.eclipse.core.runtime.IStatus
import org.eclipse.xtend.lib.annotations.Accessors

/**
 * Implementation of {@link ErrorStatus}.
 * 
 * @author Schaefer
 */
class ErrorStatusImpl implements ErrorStatus {
	
	@Accessors
	String errorTitle
	
	@Accessors
	String userMessage
	
	@Accessors
	IStatus status
}
