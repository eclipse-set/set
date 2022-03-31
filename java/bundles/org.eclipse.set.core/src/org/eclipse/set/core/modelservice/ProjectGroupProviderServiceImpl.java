/**
 * Copyright (c) 2021 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */

package org.eclipse.set.core.modelservice;

import org.eclipse.set.core.services.planningaccess.PlanningAccessService;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import de.scheidtbachmann.planpro.model.model1902.PlanPro.Planung_Projekt;
import de.scheidtbachmann.planpro.model.modelservice.ProjectGroupProviderService;

/**
 * Implementation of {@link ProjectGroupProviderService}.
 * 
 * @author Schaefer
 */
@Component
public class ProjectGroupProviderServiceImpl
		implements ProjectGroupProviderService {

	@Reference
	PlanningAccessService planningAccessService;

	@Override
	public Object getGroup(final Object parent) {
		return planningAccessService.getPlanungGruppe((Planung_Projekt) parent);
	}

	@Override
	public Object getProject(final Object parent) {
		// we currently don't use this
		throw new UnsupportedOperationException();
	}
}
