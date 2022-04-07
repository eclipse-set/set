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
import org.eclipse.set.core.services.merge.MergeService.Responsibility;
import org.eclipse.set.toolboxmodel.PlanPro.Container_AttributeGroup;

/**
 * The PlanPro merge context.
 * 
 * @author Schaefer
 */
public class PlanProMergeContext implements Context {

	private final Configuration configuration;
	private final EObject primaryContainer;
	private final PlanProResponsibility responsibility;
	private final EObject secondaryContainer;

	/**
	 * @param configuration
	 *            the PlanPro merge configuration
	 * @param primaryContainer
	 *            the primary container
	 * @param secondaryContainer
	 *            the secondary container
	 */
	public PlanProMergeContext(final Configuration configuration,
			final Container_AttributeGroup primaryContainer,
			final Container_AttributeGroup secondaryContainer) {
		this.configuration = configuration;
		this.primaryContainer = primaryContainer;
		this.secondaryContainer = secondaryContainer;
		responsibility = new PlanProResponsibility(primaryContainer,
				secondaryContainer);
	}

	@Override
	public Configuration getConfiguration() {
		return configuration;
	}

	@Override
	public EObject getPrimaryContainer() {
		return primaryContainer;
	}

	@Override
	public Responsibility getResponsibility() {
		return responsibility;
	}

	@Override
	public EObject getSecondaryContainer() {
		return secondaryContainer;
	}
}
