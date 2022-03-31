/**
 * Copyright (c) 2018 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.integration;

import java.util.LinkedHashSet;
import java.util.Set;

import org.eclipse.emf.ecp.view.spi.custom.model.ECPHardcodedReferences;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VFeaturePathDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VViewFactory;

import org.eclipse.set.model.integrationview.IntegrationviewPackage;

/**
 * Custom domain model references for exit merge mode control.
 * 
 * @author Schaefer
 */
public class ExitMergeModeReferences implements ECPHardcodedReferences {

	@Override
	public Set<VDomainModelReference> getNeededDomainModelReferences() {
		final Set<VDomainModelReference> references = new LinkedHashSet<>();
		final VFeaturePathDomainModelReference reference = VViewFactory.eINSTANCE
				.createFeaturePathDomainModelReference();
		reference.setDomainModelEFeature(
				IntegrationviewPackage.eINSTANCE.getIntegrationView_CompositePlanning());
		references.add(reference);
		return references;
	}
}
