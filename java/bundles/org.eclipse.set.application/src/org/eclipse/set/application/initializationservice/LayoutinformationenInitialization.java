/**
 * Copyright (c) 2024 DB InfraGO AG and others
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 */
package org.eclipse.set.application.initializationservice;

import org.eclipse.set.basis.guid.Guid;
import org.eclipse.set.core.services.initialization.InitializationStep;
import org.eclipse.set.model.planpro.Basisobjekte.BasisobjekteFactory;
import org.eclipse.set.model.planpro.Basisobjekte.Identitaet_TypeClass;
import org.eclipse.set.model.planpro.Layoutinformationen.DocumentRoot;
import org.eclipse.set.model.planpro.Layoutinformationen.LayoutinformationenFactory;
import org.eclipse.set.model.planpro.Layoutinformationen.PlanPro_Layoutinfo;
import org.osgi.service.component.annotations.Component;

/**
 * Layoutinformationen specific initialization step.
 * 
 * @author mariusheine
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
		final Identitaet_TypeClass layoutInfoIdentitaet = BasisobjekteFactory.eINSTANCE
				.createIdentitaet_TypeClass();
		layoutInfoIdentitaet.setWert(Guid.create().toString());
		layoutinfo.setIdentitaet(layoutInfoIdentitaet);
		documentRoot.setPlanProLayoutinfo(layoutinfo);
	}
}
