/**
 * Copyright (c) 2018 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.application.actionservice;

import org.osgi.service.component.annotations.Component;

import org.eclipse.set.basis.ActionProvider;
import org.eclipse.set.core.services.action.ActionService;

/**
 * Implementation for {@link ActionService}.
 * 
 * @author Schaefer
 */
@Component
public class ActionServiceImpl implements ActionService {

	private ActionProvider actionProvider;

	@Override
	public void setActionProvider(final ActionProvider actionProvider) {
		this.actionProvider = actionProvider;
	}

	@Override
	public void update() {
		actionProvider.update();
	}
}
