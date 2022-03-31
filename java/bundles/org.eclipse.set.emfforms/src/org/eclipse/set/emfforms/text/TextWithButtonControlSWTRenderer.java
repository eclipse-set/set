/**
 * Copyright (c) 2018 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.emfforms.text;

import javax.inject.Inject;

import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.core.swt.renderer.TextControlSWTRenderer;
import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emf.ecp.view.spi.model.VElement;
import org.eclipse.emf.ecp.view.template.model.VTViewTemplateProvider;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.core.services.databinding.EMFFormsDatabinding;
import org.eclipse.emfforms.spi.core.services.editsupport.EMFFormsEditSupport;
import org.eclipse.emfforms.spi.core.services.label.EMFFormsLabelProvider;
import org.eclipse.emfforms.spi.swt.core.layout.SWTGridCell;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.set.core.services.Services;
import org.eclipse.set.core.services.viewmodel.ToolboxViewModelService;
import org.eclipse.set.emfforms.controls.ButtonControl;
import org.eclipse.set.emfforms.utils.RendererContextImpl;
import org.eclipse.set.utils.ButtonAction;
import org.eclipse.set.utils.emfforms.Annotations;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

/**
 * Renders a text box with a button.
 * 
 * @author Schaefer
 */
public class TextWithButtonControlSWTRenderer extends TextControlSWTRenderer {

	final ButtonAction buttonAction;

	/**
	 * @param vElement
	 *            the element to be rendered
	 * @param viewContext
	 *            the view context
	 * @param reportService
	 *            the report service
	 * @param emfFormsDatabinding
	 *            the databinding service
	 * @param emfFormsLabelProvider
	 *            the label provider
	 * @param vtViewTemplateProvider
	 *            the template provider
	 * @param emfFormsEditSupport
	 *            the EMF Forms edit support
	 */
	@Inject
	public TextWithButtonControlSWTRenderer(final VControl vElement,
			final ViewModelContext viewContext,
			final ReportService reportService,
			final EMFFormsDatabinding emfFormsDatabinding,
			final EMFFormsLabelProvider emfFormsLabelProvider,
			final VTViewTemplateProvider vtViewTemplateProvider,
			final EMFFormsEditSupport emfFormsEditSupport) {
		super(vElement, viewContext, reportService, emfFormsDatabinding,
				emfFormsLabelProvider, vtViewTemplateProvider,
				emfFormsEditSupport);
		final ToolboxViewModelService toolboxViewModelService = Services
				.getToolboxViewModelService();
		final String buttonActionKey = Annotations.getViewModelValue(vElement,
				ButtonControl.BUTTON_ACTION_KEY);
		if (buttonActionKey == null) {
			throw new IllegalArgumentException(
					"No " + ButtonControl.BUTTON_ACTION_KEY + " for " //$NON-NLS-1$ //$NON-NLS-2$
							+ vElement.getName());
		}
		buttonAction = (ButtonAction) toolboxViewModelService
				.get(buttonActionKey);
		if (buttonAction == null) {
			throw new IllegalArgumentException("No button action for key \"" //$NON-NLS-1$
					+ buttonActionKey + "\" found."); //$NON-NLS-1$
		}
	}

	@Override
	protected Control createSWTControl(final Composite parent) {
		// the control for the text box and button
		final Composite filenameControl = new Composite(parent, SWT.NONE);
		GridLayoutFactory.fillDefaults().numColumns(2).applyTo(filenameControl);
		GridDataFactory.fillDefaults().grab(true, false)
				.align(SWT.FILL, SWT.BEGINNING).applyTo(filenameControl);

		// the text box
		final Control textBox = super.createSWTControl(filenameControl);

		// the button parameters
		final String buttonText = buttonAction.getText();
		final int buttonWidth = buttonAction.getWidth();

		// the button
		final Button button = new Button(filenameControl, SWT.PUSH);
		button.setText(buttonText);
		GridDataFactory.swtDefaults().minSize(buttonWidth, 0)
				.hint(buttonWidth, SWT.DEFAULT).applyTo(button);
		final RendererContextImpl rendererContext = new RendererContextImpl();
		rendererContext.put(Button.class, button);
		rendererContext.put(VElement.class, getVElement());
		buttonAction.register(rendererContext);

		// the action
		button.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetDefaultSelected(final SelectionEvent e) {
				buttonAction.selected(e);
			}

			@Override
			public void widgetSelected(final SelectionEvent e) {
				buttonAction.selected(e);
			}
		});

		return textBox;
	}

	@Override
	protected void setControlEnabled(final SWTGridCell gridCell,
			final Control control, final boolean enabled) {
		// we do not want to enable read only text fields here
		super.setControlEnabled(gridCell, control,
				enabled && !getVElement().isReadonly());
	}
}
