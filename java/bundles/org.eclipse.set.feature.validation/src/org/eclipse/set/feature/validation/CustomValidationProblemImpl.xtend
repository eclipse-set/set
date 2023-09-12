/**
 * Copyright (c) 2019 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.validation

import org.eclipse.set.basis.exceptions.CustomValidationProblem
import org.eclipse.set.model.validationreport.ValidationSeverity
import org.eclipse.xtend.lib.annotations.Accessors
import org.eclipse.set.model.validationreport.ObjectScope
import org.eclipse.set.model.validationreport.ObjectState

/** 
 * Implementation of {@link CustomValidationProblem} for nil test.
 * 
 * @author Schaefer
 */
@Accessors
class CustomValidationProblemImpl implements CustomValidationProblem {

	new() {
	}

	new(String message, ValidationSeverity severity, String type, String objectArt, ObjectScope objectScope, ObjectState objectState) {
		setMessage(message)
		setSeverity(severity)
		setType(type)
		setObjectArt(objectArt)
		setObjectScope(objectScope)
		setObjectState(objectState)
	}

	int lineNumber = 0
	String message
	ValidationSeverity severity
	String type
	String objectArt
	ObjectScope objectScope
	ObjectState objectState
	String attributeName
}
