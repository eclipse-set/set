/**
 * Copyright (c) 2017 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.emfforms.basisattribute;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecp.common.spi.UniqueSetting;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VFeaturePathDomainModelReference;
import org.eclipse.emfforms.internal.core.services.mappingprovider.defaultheuristic.EMFFormsMappingProviderDefaultHeuristic;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.core.services.databinding.emf.EMFFormsDatabindingEMF;
import org.eclipse.emfforms.spi.core.services.mappingprovider.EMFFormsMappingProvider;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import org.eclipse.set.toolboxmodel.PlanPro.PlanPro_Schnittstelle;

/**
 * This implementation of the {@link EMFFormsMappingProvider} links the Wert
 * field of the basis attribute to the control displaying the basis attribute.
 * 
 * @author Schaefer
 * 
 * @usage production
 */
@Component(service = EMFFormsMappingProvider.class, immediate = true)
public class BasisAttributeMappingProvider
		extends EMFFormsMappingProviderDefaultHeuristic {

	private static final double PRIORITY = 100;

	private static Set<UniqueSetting> getWertMappingFor(final Setting setting) {
		final Set<UniqueSetting> result = new HashSet<>();

		final Object object = setting.get(true);
		if (object instanceof EObject) {
			final EObject basisAttribute = (EObject) object;
			result.add(UniqueSetting.createSetting(basisAttribute,
					BasisAttributeSetting.getWertFeature(basisAttribute)));
		}

		return result;
	}

	@Override
	public Set<UniqueSetting> getMappingFor(
			final VDomainModelReference domainModelReference,
			final EObject domainObject) {
		if (!isLinked(domainObject)) {
			return new HashSet<>();
		}
		final Set<UniqueSetting> result = new HashSet<>();
		final Set<UniqueSetting> originalMapping = super.getMappingFor(
				domainModelReference, domainObject);
		result.addAll(originalMapping);
		result.addAll(getWertMappingFor(
				originalMapping.iterator().next().getSetting()));
		return result;
	}

	@Override
	public double isApplicable(final VDomainModelReference domainModelReference,
			final EObject domainObject) {
		if (!(domainModelReference instanceof VFeaturePathDomainModelReference)) {
			return NOT_APPLICABLE;
		}
		final VFeaturePathDomainModelReference vFeaturePathDomainModelReference = (VFeaturePathDomainModelReference) domainModelReference;
		final EStructuralFeature domainModelEFeature = vFeaturePathDomainModelReference
				.getDomainModelEFeature();
		if (domainModelEFeature == null) {
			return NOT_APPLICABLE;
		}
		if (BasisAttributeSetting
				.hasWertFeature(domainModelEFeature.getEType())) {
			return PRIORITY;
		}
		return NOT_APPLICABLE;
	}

	private boolean isLinked(final EObject object) {
		if (object == null) {
			return false;
		}
		if (object instanceof PlanPro_Schnittstelle) {
			return true;
		}
		return isLinked(object.eContainer());
	}

	@Override
	@Reference(unbind = "-")
	protected void setEMFFormsDatabinding(
			final EMFFormsDatabindingEMF emfFormsDatabinding) {
		super.setEMFFormsDatabinding(emfFormsDatabinding);
	}

	@Override
	@Reference(unbind = "-")
	protected void setReportService(final ReportService reportService) {
		super.setReportService(reportService);
	}
}
