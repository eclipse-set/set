/**
 * Copyright (c) 2018 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.core.services.action;

import org.eclipse.set.basis.ActionProvider;

/**
 * Service to access the action provider.
 * 
 * @author Schaefer
 */
public interface ActionService {

	/**
	 * @param actionProvider
	 *            the action provider
	 */
	void setActionProvider(ActionProvider actionProvider);

	/**
	 * Update the actions.
	 */
	void update();
}
