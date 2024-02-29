/**
 * Copyright (c) 2020 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.emfforms.text;

import jakarta.inject.Inject;

import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.core.swt.renderer.TextControlSWTRenderer;
import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emf.ecp.view.template.model.VTViewTemplateProvider;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.core.services.databinding.EMFFormsDatabinding;
import org.eclipse.emfforms.spi.core.services.editsupport.EMFFormsEditSupport;
import org.eclipse.emfforms.spi.core.services.label.EMFFormsLabelProvider;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

/**
 * Renders texts in multiline field
 * 
 * @author Truong
 *
 */
// IMPROVE: Move to ppt?
// IMPROVE: Rename to something with "multiline" in the name?
// IMPROVE: Service should be renamed to "<RendererName>Service".
public class TextFieldRenderer extends TextControlSWTRenderer {

	/**
	 * Default constructor.
	 *
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
	public TextFieldRenderer(final VControl vElement,
			final ViewModelContext viewContext,
			final ReportService reportService,
			final EMFFormsDatabinding emfFormsDatabinding,
			final EMFFormsLabelProvider emfFormsLabelProvider,
			final VTViewTemplateProvider vtViewTemplateProvider,
			final EMFFormsEditSupport emfFormsEditSupport) {
		super(vElement, viewContext, reportService, emfFormsDatabinding,
				emfFormsLabelProvider, vtViewTemplateProvider,
				emfFormsEditSupport);
	}

	@Override
	protected Control createSWTControl(final Composite parent) {
		final Composite main = (Composite) super.createSWTControl(parent);
		final GridDataFactory gdf = GridDataFactory.fillDefaults()
				.align(SWT.FILL, SWT.CENTER).grab(true, true).span(1, 1);
		gdf.hint(50, 100);
		gdf.applyTo(main.getChildren()[0]);
		return main;
	}

	@Override
	protected int getTextWidgetStyle() {
		final int textStyle = SWT.MULTI | SWT.WRAP | SWT.V_SCROLL | SWT.H_SCROLL
				| SWT.BORDER | getDefaultAlignment();
		return textStyle;
	}

}
