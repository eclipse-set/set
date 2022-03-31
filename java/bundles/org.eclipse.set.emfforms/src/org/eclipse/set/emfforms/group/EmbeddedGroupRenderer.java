/**
 * Copyright (c) 2021 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.emfforms.group;

import javax.inject.Inject;

import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emf.ecp.view.template.model.VTViewTemplateProvider;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.core.services.databinding.EMFFormsDatabinding;
import org.eclipse.emfforms.spi.core.services.label.EMFFormsLabelProvider;
import org.eclipse.set.utils.emfforms.Annotations;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;

/**
 * A specialized {@link GroupRenderer}, which can render an optional or
 * mandatory, hierarchical domain element into the parent composite, without
 * introducing additional composites.
 * 
 * Unlike GroupRenderer this renderer directly inserts the domain element view
 * as if the element was directly present in the view.
 * 
 * @author Stuecker
 */
public class EmbeddedGroupRenderer extends GroupRenderer {
	/**
	 * Annotation to force the label to be hidden
	 */
	private static final String FORCE_HIDE_LABEL = "forceHideLabel"; //$NON-NLS-1$

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
	 */
	@Inject
	public EmbeddedGroupRenderer(final VControl vElement,
			final ViewModelContext viewContext,
			final ReportService reportService,
			final EMFFormsDatabinding emfFormsDatabinding,
			final EMFFormsLabelProvider emfFormsLabelProvider,
			final VTViewTemplateProvider vtViewTemplateProvider) {
		super(vElement, viewContext, reportService, emfFormsDatabinding,
				emfFormsLabelProvider, vtViewTemplateProvider);
	}

	@Override
	protected Composite createSubElementContainer(final Composite parent) {
		// When using a separate sub view for a control, setting the associated
		// label to None does not remove the label entirely, but simply hides
		// its content. This leaves behind a small indentation, which can be
		// removed via FORCE_HIDE_LABEL
		final boolean hideLabel = Boolean.parseBoolean(
				Annotations.getViewModelValue(getVElement(), FORCE_HIDE_LABEL));
		if (hideLabel) {
			// Properly hide the associated label for this element
			// The label will be the last element created before this element is
			// created
			final Control[] children = parent.getChildren();
			final Label label = (Label) children[children.length - 1];
			final GridData layoutData = (GridData) label.getLayoutData();
			layoutData.exclude = true;
		}

		// Re-use the parent element as a container
		return parent;
	}
}
