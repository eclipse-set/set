/**
 * Copyright (c) 2022 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.plazmodel.service;

import org.eclipse.set.basis.IModelSession;
import org.eclipse.set.model.plazmodel.PlazReport;

/**
 * Service for running PlaZ model checks
 * 
 * @author Stuecker
 *
 */
public interface PlazModelService {
	/**
	 * @param modelSession
	 *            the model session
	 * @return a report containing issues found by PlaZ Model
	 */
	public PlazReport runPlazModel(final IModelSession modelSession);
}
