/**
 * Copyright (c) 2018 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.integration;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.set.core.services.merge.MergeService.Configuration;
import org.eclipse.set.core.services.merge.MergeService.Context;
import org.eclipse.set.core.services.merge.MergeService.ContextProvider;
import org.eclipse.set.core.services.name.NameService;
import org.eclipse.set.toolboxmodel.PlanPro.Container_AttributeGroup;

/**
 * Provides PlanPro merge context.
 * 
 * @author Schaefer
 */
public class PlanProMergeContextProvider implements ContextProvider {

	private final Configuration configuration;

	/**
	 * @param nameService
	 *            the name service
	 */
	public PlanProMergeContextProvider(final NameService nameService) {
		configuration = new PlanProMergeConfiguration(nameService);
	}

	@Override
	public Context getContext(final EObject primaryContainer,
			final EObject secondaryContainer) {
		return new PlanProMergeContext(configuration,
				(Container_AttributeGroup) primaryContainer,
				(Container_AttributeGroup) secondaryContainer);
	}
}
