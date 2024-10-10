/**
 * Copyright (c) 2019 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.core.services.validation;

import org.eclipse.set.basis.constants.ValidationResult;
import org.eclipse.set.basis.files.ToolboxFile;

/**
 * Describes custom validation.
 * 
 * @author Schaefer
 */
public interface CustomValidator {
	/**
	 * Which type of file is being validated
	 */
	enum FileType {
		Layout, Model
	}

	/**
	 * @return a class to inject messages to
	 */
	Class<?> getMessageProviderClass();

	/**
	 * @param messageProvider
	 *            the message provider with the injected messages
	 */
	void setMessagesProvider(Object messageProvider);

	/**
	 * @param toolboxFile
	 *            the toolbox file
	 * @param result
	 *            the object collecting the validation results
	 * @param type
	 *            type of file being validated
	 * @param prototype
	 *            a prototype for creating custom validation problems
	 */
	void validate(ToolboxFile toolboxFile, ValidationResult result,
			FileType type);

	/**
	 * @return type of validation
	 */
	String validationType();

}
