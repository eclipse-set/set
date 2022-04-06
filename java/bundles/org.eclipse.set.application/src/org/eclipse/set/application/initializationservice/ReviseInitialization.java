/**
 * Copyright (c) 2020 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.application.initializationservice;

import org.eclipse.set.core.services.initialization.InitializationStep;
import org.eclipse.set.ppmodel.extensions.DocumentRootExtensions;
import org.eclipse.set.ppmodel.extensions.PlanProSchnittstelleExtensions;
import org.osgi.service.component.annotations.Component;

import org.eclipse.set.toolboxmodel.PlanPro.DocumentRoot;

/**
 * Revise the initialization (this should always be done as the last step).
 * 
 * @author Schaefer
 */
@Component(immediate = true)
public class ReviseInitialization implements InitializationStep {

	@Override
	public int getPriority() {
		return 0;
	}

	@Override
	public <M, C extends Configuration> void init(final M model,
			final C configuaration) {
		if (!(model instanceof DocumentRoot)) {
			return;
		}

		final DocumentRoot documentRoot = (DocumentRoot) model;

		// revise
		DocumentRootExtensions.fix(documentRoot);
		PlanProSchnittstelleExtensions
				.fix(documentRoot.getPlanProSchnittstelle());

	}
}
