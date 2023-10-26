/**
 * Copyright (c) 2022 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.plazmodel.check;

import java.util.List;
import java.util.Map;

import org.eclipse.set.basis.IModelSession;
import org.eclipse.set.model.plazmodel.PlazError;

/**
 * Interface for a PlaZ Model validation for a specific container
 * 
 * @author Stuecker
 */
public interface PlazCheck {
	/**
	 * Runs the PlaZ model check on a specific container
	 * 
	 * @param modelSession
	 *            the model session
	 * @return a list of validation messages
	 */
	List<PlazError> run(final IModelSession modelSession);

	/**
	 * @return type of plaz check
	 */
	String checkType();

	/**
	 * @return check description
	 */
	String getDescription();

	/**
	 * @return general error messages
	 */
	String getGeneralErrMsg();

	/**
	 * IMPROVE: this function should change to default function, when
	 * xtend-plugin update is. Currently xtend version (2.27) isn't support
	 * default function
	 * 
	 * Transform general error message to individual messages
	 * 
	 * @param params
	 *            name and value of parameter in general erro message
	 * @return individual error messages
	 */
	String transformErroMsg(final Map<String, String> params);
}
