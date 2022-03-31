/**
 * Copyright (c) 2017 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.emfforms.basisattribute;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emf.ecp.view.template.model.VTViewTemplateProvider;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.core.services.databinding.EMFFormsDatabinding;
import org.eclipse.emfforms.spi.core.services.editsupport.EMFFormsEditSupport;
import org.eclipse.emfforms.spi.core.services.label.EMFFormsLabelProvider;
import org.eclipse.emfforms.spi.localization.LocalizationServiceHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.scheidtbachmann.planpro.model.model1902.BasisTypen.BasisAttribut_AttributeGroup;

/**
 * Render a {@link BasisAttribut_AttributeGroup} boolean object element.
 * 
 * @author Schaefer
 */
public class BasisAttributeBooleanRenderer
		extends AbstractBasisAttributeEnumeratorRenderer<Boolean> {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(BasisAttributeBooleanRenderer.class);
	private static final String NO = "BasisAttributeBooleanRenderer_No"; //$NON-NLS-1$
	private static final String YES = "BasisAttributeBooleanRenderer_Yes"; //$NON-NLS-1$

	/**
	 * @param type
	 *            the type to test
	 * 
	 * @return whether this renderer can render an element with the given domain
	 *         type
	 */
	public static boolean isApplicable(final EClassifier type) {
		return BasisAttributeSetting.isWertFeatureTypeAssignableTo(type,
				Boolean.class);
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
	public BasisAttributeBooleanRenderer(final VControl vElement,
			final ViewModelContext viewContext,
			final ReportService reportService,
			final EMFFormsDatabinding emfFormsDatabinding,
			final EMFFormsLabelProvider emfFormsLabelProvider,
			final VTViewTemplateProvider vtViewTemplateProvider,
			final EMFFormsEditSupport emfFormsEditSupport) {
		super(Boolean.class, vElement, viewContext, reportService,
				emfFormsDatabinding, emfFormsLabelProvider,
				vtViewTemplateProvider, emfFormsEditSupport);
		LOGGER.debug("Render {}", vElement); //$NON-NLS-1$
	}

	@Override
	public BasisAttributeSetting<Boolean> getBasisAttributeSetting() {
		return basisAttributeSetting;
	}

	@Override
	public Optional<Boolean> valueOf(final String representation) {
		return Optional.ofNullable(representation)
				.map(r -> Boolean.valueOf(Boolean.parseBoolean(r)));
	}

	@Override
	protected List<Object> getInputValues() {
		final List<Object> inputValues = new ArrayList<>();
		inputValues.add(EMPTY_VALUE);
		inputValues.add(Boolean.FALSE);
		inputValues.add(Boolean.TRUE);
		return inputValues;
	}

	@Override
	protected String getLabel(final Object element) {
		if (element == EMPTY_VALUE) {
			return EMPTY_VALUE_LABEL;
		}
		if (Boolean.FALSE.equals(element)) {
			return LocalizationServiceHelper.getString(getClass(), NO);
		}
		if (Boolean.TRUE.equals(element)) {
			return LocalizationServiceHelper.getString(getClass(), YES);
		}
		throw new IllegalArgumentException(element.toString());
	}
}
