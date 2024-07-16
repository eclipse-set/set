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
import org.eclipse.set.model.planpro.Layoutinformationen.DocumentRoot;
import org.eclipse.set.model.planpro.Layoutinformationen.LayoutinformationenFactory;
import org.eclipse.set.model.planpro.Layoutinformationen.PlanPro_Layoutinfo;
import org.osgi.service.component.annotations.Component;

/**
 * Common PlanPro initialization step for all use cases.
 * 
 * @author Schaefer
 */
@Component(immediate = true)
public class LayoutinformationenInitialization implements InitializationStep {

	private static final int PRIORITY = 90;

	@Override
	public int getPriority() {
		return PRIORITY;
	}

	@Override
	public <M, C extends Configuration> void init(final M model,
			final C configuaration) {
		if (!(model instanceof DocumentRoot)) {
			return;
		}

		// PlanPro Layoutinfo
		final DocumentRoot documentRoot = (DocumentRoot) model;
		final PlanPro_Layoutinfo layoutinfo = LayoutinformationenFactory.eINSTANCE
				.createPlanPro_Layoutinfo();
		documentRoot.setPlanProLayoutinfo(layoutinfo);
	}
}
