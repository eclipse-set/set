/**
 * Copyright (c) 2019 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.basis.exceptions;

import org.eclipse.set.model.validationreport.ObjectScope;
import org.eclipse.set.model.validationreport.ObjectState;
import org.eclipse.set.model.validationreport.ValidationSeverity;

/**
 * Describes how to exchange custom validation problems.
 * 
 * @author Schaefer
 */
public interface CustomValidationProblem {

	/**
	 * @return the line number of the problem
	 */
	int getLineNumber();

	/**
	 * @return the problem message
	 */
	String getMessage();

	/**
	 * @return the Object Art
	 */
	String getObjectArt();

	/**
	 * @return the Object Scope
	 */
	ObjectScope getObjectScope();

	/**
	 * @return the Object State
	 */
	ObjectState getObjectState();

	/**
	 * @return the validation severity
	 */
	ValidationSeverity getSeverity();

	/**
	 * @return the problem type
	 */
	String getType();

	/**
	 * @return the attribute name for the problem
	 */
	String getAttributeName();
}
