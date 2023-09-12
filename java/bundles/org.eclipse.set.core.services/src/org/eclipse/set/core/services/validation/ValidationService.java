/**
 * Copyright (c) 2017 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.core.services.validation;

import java.nio.file.Path;
import java.util.function.Function;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.set.basis.ResourceLoader;
import org.eclipse.set.basis.constants.ValidationResult;
import org.eclipse.set.basis.files.ToolboxFile;

/**
 * Provides methods for validation.
 * 
 * @author Schaefer
 */
public interface ValidationService {

	/**
	 * @param <T>
	 *            the type of the object to be validated
	 * @param toolboxFile
	 *            the toolbox file
	 * @param resourceLoader
	 *            the resource loader
	 * @param supplier
	 *            supplies the object to be validated
	 * @param result
	 *            the object collecting the validation results
	 * 
	 * @return the object to be validated
	 */
	<T extends EObject> T checkLoad(ToolboxFile toolboxFile,
			ResourceLoader resourceLoader, Function<Resource, T> supplier,
			ValidationResult result);

	/**
	 * @param toolboxFile
	 *            the toolbox file
	 * @param result
	 *            the object collecting the validation results
	 * @param prototype
	 *            a prototype for creating custom validation problems
	 * 
	 * @return the validation result
	 */
	ValidationResult customValidation(ToolboxFile toolboxFile,
			ValidationResult result);

	/**
	 * @param <T>
	 *            the type of the object to be validated
	 * @param object
	 *            the object to be validated
	 * @param result
	 *            the object collecting the validation results
	 * 
	 * @return the validation result
	 */
	<T extends EObject> ValidationResult emfValidation(T object,
			ValidationResult result);

	/**
	 * @param sourcePath
	 *            the path of source to validate
	 * @param result
	 *            the object collecting the validation results
	 * 
	 * @return the validation result
	 */
	ValidationResult xsdValidation(Path sourcePath, ValidationResult result);

	/**
	 * @param result
	 *            the validation result
	 * @param toolboxFile
	 *            the thoolbox file
	 * @return the validation result
	 */
	ValidationResult validateSource(ValidationResult result,
			ToolboxFile toolboxFile);
}
