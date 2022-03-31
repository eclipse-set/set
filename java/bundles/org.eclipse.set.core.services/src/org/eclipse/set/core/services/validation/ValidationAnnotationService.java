/**
 * Copyright (c) 2021 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */

package org.eclipse.set.core.services.validation;

import java.nio.file.Path;
import java.util.Optional;

import org.eclipse.set.model.validationreport.ValidationReport;

/**
 * Create annotation model for validation problems.
 * 
 * @author Schaefer
 */
public interface ValidationAnnotationService {

	/**
	 * Create the validation annotation model. The implementation defines how
	 * and when the result is propagated.
	 * 
	 * @param path
	 *            the path to the document for the annotations
	 * @param validationReport
	 *            the report to be transformed
	 */
	public void createModel(Path path, ValidationReport validationReport);

	/**
	 * @return the latest annotation result (if present)
	 */
	public Optional<AnnotationResult> getResult();
}
