/**
 * Copyright (c) 2020 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.application.initializationservice;

import java.util.Date;
import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.eclipse.set.basis.guid.Guid;
import org.eclipse.set.core.services.initialization.InitializationStep;
import org.eclipse.set.ppmodel.extensions.PlanProSchnittstelleExtensions;
import org.eclipse.set.utils.ToolboxConfiguration;
import org.eclipse.set.utils.ToolboxVersion;
import org.osgi.service.component.annotations.Component;

import org.eclipse.set.toolboxmodel.Basisobjekte.BasisobjekteFactory;
import org.eclipse.set.toolboxmodel.Basisobjekte.Identitaet_TypeClass;
import org.eclipse.set.toolboxmodel.PlanPro.DocumentRoot;
import org.eclipse.set.toolboxmodel.PlanPro.PlanPro_Schnittstelle;
import org.eclipse.set.toolboxmodel.PlanPro.PlanPro_Schnittstelle_Allg_AttributeGroup;

/**
 * Common PlanPro initialization step for all use cases.
 * 
 * @author Schaefer
 */
@Component(immediate = true)
public class CommonPlanProInitialization implements InitializationStep {

	private static final int PRIORITY = 100;

	private static XMLGregorianCalendar currentTime() {
		final GregorianCalendar gc = new GregorianCalendar();
		gc.setTime(new Date());
		XMLGregorianCalendar xc = null;
		try {
			xc = DatatypeFactory.newInstance().newXMLGregorianCalendar(gc);
		} catch (final DatatypeConfigurationException e) {
			throw new RuntimeException(e);
		}

		return xc;
	}

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

		// PlanPro Schnittstelle
		final DocumentRoot documentRoot = (DocumentRoot) model;
		final PlanPro_Schnittstelle planProSchnittstelle = PlanProSchnittstelleExtensions
				.createEmptyModel();
		documentRoot.setPlanProSchnittstelle(planProSchnittstelle);

		// Erzeugung Zeitstempel
		final PlanPro_Schnittstelle_Allg_AttributeGroup planProSchnittstelleAllg = planProSchnittstelle
				.getPlanProSchnittstelleAllg();
		planProSchnittstelleAllg.getErzeugungZeitstempel()
				.setWert(currentTime());

		// Werkzeug Name
		planProSchnittstelleAllg.getWerkzeugName().setWert(
				configuaration.getBrandingService().getNames().getToolName());

		// Werkzeug Version
		final ToolboxVersion version = ToolboxConfiguration.getToolboxVersion();
		planProSchnittstelleAllg.getWerkzeugVersion()
				.setWert(version.getShortVersion());

		// Schnittstelle Identität
		final Identitaet_TypeClass id = BasisobjekteFactory.eINSTANCE
				.createIdentitaet_TypeClass();
		id.setWert(Guid.create().toString());
		planProSchnittstelle.setIdentitaet(id);
	}
}
