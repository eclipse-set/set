/**
 * Copyright (c) 2020 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */

package org.eclipse.set.core.services.update;

import org.eclipse.emf.ecore.EObject;

/**
 * Update Model service
 * 
 * @author Truong
 */

public interface ConcreteModelUpdateService {

	/**
	 * @param model
	 *            the model
	 */
	void add(EObject model);
}
