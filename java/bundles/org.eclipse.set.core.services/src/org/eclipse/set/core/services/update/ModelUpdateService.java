/**
 * Copyright (c) 2020 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */

package org.eclipse.set.core.services.update;

import java.util.List;

import org.eclipse.emf.ecore.EObject;

/**
 * Manage udate of Session
 * 
 * @author Truong
 *
 */
public interface ModelUpdateService {

	/**
	 * @param model
	 *            the model
	 */
	void add(EObject model);

	/**
	 * @return the {@link ConcreteModelUpdateService}s
	 */
	List<ConcreteModelUpdateService> getConcreteModelUpdateServices();
}
