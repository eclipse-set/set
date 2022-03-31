/**
 * Copyright (c) 2018 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.validation.report;

import java.util.LinkedHashSet;
import java.util.Set;

import org.eclipse.emf.ecp.view.spi.custom.model.ECPHardcodedReferences;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VFeaturePathDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VViewFactory;

import org.eclipse.set.model.validationreport.ValidationreportPackage;

/**
 * Custom domain model references for refresh control.
 * 
 * @author Schaefer
 */
public class RefreshActionReferences implements ECPHardcodedReferences {

	@Override
	public Set<VDomainModelReference> getNeededDomainModelReferences() {
		final Set<VDomainModelReference> references = new LinkedHashSet<>();
		final VFeaturePathDomainModelReference reference = VViewFactory.eINSTANCE
				.createFeaturePathDomainModelReference();
		reference.setDomainModelEFeature(ValidationreportPackage.eINSTANCE
				.getValidationReport_Problems());
		references.add(reference);
		return references;
	}

}
