/**
 * Copyright (c) 2020 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.application.initializationservice;

import org.eclipse.set.basis.ProjectInitializationData;
import org.eclipse.set.core.services.initialization.InitializationStep;
import org.eclipse.set.ppmodel.extensions.PlanProSchnittstelleExtensions;
import org.eclipse.set.ppmodel.extensions.PlanungProjektExtensions;
import org.osgi.service.component.annotations.Component;

import org.eclipse.set.model.planpro.PlanPro.DocumentRoot;
import org.eclipse.set.model.planpro.PlanPro.Fuehrende_Oertlichkeit_TypeClass;
import org.eclipse.set.model.planpro.PlanPro.PlanProFactory;
import org.eclipse.set.model.planpro.PlanPro.PlanPro_Schnittstelle;
import org.eclipse.set.model.planpro.PlanPro.Planung_E_Allg_AttributeGroup;

/**
 * PlanPro specific initialization step.
 * 
 * @author Schaefer
 */
@Component(immediate = true)
public class PlanProSpecificInitialization implements InitializationStep {

	private static final int PRIORITY = 90;

	@Override
	public int getPriority() {
		return PRIORITY;
	}

	@Override
	public <M, C extends Configuration> void init(final M model,
			final C configuaration) {
		if (!(model instanceof DocumentRoot) || configuaration == null
				|| !(configuaration
						.getData() instanceof ProjectInitializationData)) {
			return;
		}

		final DocumentRoot documentRoot = (DocumentRoot) model;
		final ProjectInitializationData projectInitializationData = (ProjectInitializationData) configuaration
				.getData();

		// set data from projectInitializationData
		final PlanPro_Schnittstelle planProSchnittstelle = documentRoot
				.getPlanProSchnittstelle();
		final Planung_E_Allg_AttributeGroup planungAllgemein = PlanProSchnittstelleExtensions
				.getPlanungAllgemein(planProSchnittstelle);
		planungAllgemein.getBauzustandKurzbezeichnung()
				.setWert(projectInitializationData.getBauzustand());
		planungAllgemein.getLaufendeNummerAusgabe()
				.setWert(projectInitializationData.getLfdNummer());
		planungAllgemein.getIndexAusgabe()
				.setWert(projectInitializationData.getIndex());
		final Fuehrende_Oertlichkeit_TypeClass fuehrendeOertlichkeit = PlanProFactory.eINSTANCE
				.createFuehrende_Oertlichkeit_TypeClass();
		fuehrendeOertlichkeit
				.setWert(projectInitializationData.getFuehrendeOertlichkeit());
		PlanungProjektExtensions
				.getLeadingPlanungGruppe(PlanProSchnittstelleExtensions
						.LSTPlanungProjekt(planProSchnittstelle))
				.setFuehrendeOertlichkeit(fuehrendeOertlichkeit);
	}
}
