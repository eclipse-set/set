/**
 * Copyright (c) 2017 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.emfforms.labelservice;

import java.util.function.Supplier;

import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.databinding.observable.value.WritableValue;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VElement;
import org.eclipse.emf.ecp.view.spi.model.VFeaturePathDomainModelReference;
import org.eclipse.emfforms.internal.core.services.label.EMFFormsLabelProviderImpl;
import org.eclipse.emfforms.spi.core.services.label.NoLabelFoundException;
import org.eclipse.set.core.services.Services;
import org.eclipse.set.core.services.viewmodel.ToolboxViewModelService;
import org.eclipse.set.utils.VDomainModelReferenceExtensions;
import org.eclipse.set.utils.emfforms.Annotations;

/**
 * Provides specialist descriptions for rendered PlanPro model elements.
 * 
 * @author Schaefer
 */
public class ToolboxLabelProvider extends EMFFormsLabelProviderImpl {

	private static final String ALTERNATE_DISPLAY_NAME_SUPPLIER = "AlternateDisplayNameSupplier"; //$NON-NLS-1$

	private static final String GEN_MODEL_PACKAGE_NS_URI = "http://www.eclipse.org/emf/2002/GenModel"; //$NON-NLS-1$

	private static final String KEY_DOCUMENTATION = "documentation"; //$NON-NLS-1$

	private static String getDescription(
			final EStructuralFeature domainModelEFeature) {
		if (domainModelEFeature == null) {
			return null;
		}
		return Annotations.getValue(domainModelEFeature,
				GEN_MODEL_PACKAGE_NS_URI, KEY_DOCUMENTATION);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public IObservableValue getDescription(
			final VDomainModelReference domainModelReference,
			final EObject rootObject) throws NoLabelFoundException {
		if (domainModelReference instanceof VFeaturePathDomainModelReference) {
			final VFeaturePathDomainModelReference featurePathDomainModelReference = (VFeaturePathDomainModelReference) domainModelReference;
			String documentation = getDescription(
					featurePathDomainModelReference.getDomainModelEFeature());
			if (documentation == null && VDomainModelReferenceExtensions
					.isValueField(domainModelReference)) {
				final EObject object = VDomainModelReferenceExtensions
						.resolve(domainModelReference, rootObject);
				documentation = getDescription(object.eContainingFeature());
			}

			return new WritableValue<>(documentation, String.class);
		}
		return new WritableValue<>(null, String.class);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public IObservableValue getDisplayName(
			final VDomainModelReference domainModelReference,
			final EObject rootObject) throws NoLabelFoundException {
		final EObject container = domainModelReference.eContainer();
		if (container instanceof VElement) {
			final VElement vElement = (VElement) container;
			final String key = Annotations.getViewModelValue(vElement,
					ALTERNATE_DISPLAY_NAME_SUPPLIER);
			if (key != null) {
				final ToolboxViewModelService viewModelService = Services
						.getToolboxViewModelService();
				@SuppressWarnings("unchecked")
				final Supplier<String> displayNameSupplier = (Supplier<String>) viewModelService
						.get(key);
				if (displayNameSupplier == null) {
					throw new IllegalArgumentException(
							"No supplier for key=\"" + key + "\""); //$NON-NLS-1$ //$NON-NLS-2$
				}
				return new WritableValue<>(displayNameSupplier.get(),
						String.class);
			}
		}
		return super.getDisplayName(domainModelReference, rootObject);
	}
}
