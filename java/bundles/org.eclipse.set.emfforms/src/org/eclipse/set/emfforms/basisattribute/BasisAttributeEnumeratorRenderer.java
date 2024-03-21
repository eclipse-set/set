/**
 * Copyright (c) 2017 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.emfforms.basisattribute;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Optional;

import jakarta.inject.Inject;

import org.eclipse.emf.common.util.Enumerator;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emf.ecp.view.template.model.VTViewTemplateProvider;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.core.services.databinding.EMFFormsDatabinding;
import org.eclipse.emfforms.spi.core.services.editsupport.EMFFormsEditSupport;
import org.eclipse.emfforms.spi.core.services.label.EMFFormsLabelProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.eclipse.set.model.planpro.BasisTypen.BasisAttribut_AttributeGroup;

/**
 * Render a {@link BasisAttribut_AttributeGroup} enum element.
 * 
 * @author Schaefer
 */
public class BasisAttributeEnumeratorRenderer
		extends AbstractBasisAttributeEnumeratorRenderer<Enumerator> {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(BasisAttributeEnumeratorRenderer.class);

	/**
	 * @param type
	 *            the type to test
	 * 
	 * @return whether this renderer can render an element with the given domain
	 *         type
	 */
	public static boolean isApplicable(final EClassifier type) {
		return BasisAttributeSetting.isWertFeatureTypeAssignableTo(type,
				Enumerator.class);
	}

	/**
	 * @param vElement
	 *            the view model element to be rendered
	 * @param viewContext
	 *            the view context
	 * @param reportService
	 *            The {@link ReportService}
	 * @param emfFormsDatabinding
	 *            The {@link EMFFormsDatabinding}
	 * @param emfFormsLabelProvider
	 *            The {@link EMFFormsLabelProvider}
	 * @param vtViewTemplateProvider
	 *            The {@link VTViewTemplateProvider}
	 * @param emfFormsEditSupport
	 *            The {@link EMFFormsEditSupport}
	 */
	@Inject
	public BasisAttributeEnumeratorRenderer(final VControl vElement,
			final ViewModelContext viewContext,
			final ReportService reportService,
			final EMFFormsDatabinding emfFormsDatabinding,
			final EMFFormsLabelProvider emfFormsLabelProvider,
			final VTViewTemplateProvider vtViewTemplateProvider,
			final EMFFormsEditSupport emfFormsEditSupport) {
		super(Enumerator.class, vElement, viewContext, reportService,
				emfFormsDatabinding, emfFormsLabelProvider,
				vtViewTemplateProvider, emfFormsEditSupport);
		LOGGER.debug("Render {}", vElement); //$NON-NLS-1$
	}

	@Override
	public BasisAttributeSetting<Enumerator> getBasisAttributeSetting() {
		return basisAttributeSetting;
	}

	@Override
	public Optional<Enumerator> valueOf(final String representation) {
		final EClassifier type = basisAttributeSetting.getWertFeature()
				.getEType();
		try {
			final Method getMethod = type.getInstanceClass().getMethod("get", //$NON-NLS-1$
					String.class);
			return Optional
					.of((Enumerator) getMethod.invoke(null, representation));
		} catch (final Exception e) {
			return Optional.empty();
		}
	}

	@Override
	protected List<Object> getInputValues() {
		final List<Object> inputValues = super.getInputValues();
		inputValues.add(0, EMPTY_VALUE);
		return inputValues;
	}
}
