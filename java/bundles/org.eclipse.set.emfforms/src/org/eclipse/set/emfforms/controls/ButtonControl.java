/**
 * Copyright (c) 2018 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.emfforms.controls;

import org.eclipse.emf.ecp.view.spi.custom.swt.ECPAbstractCustomControlSWT;
import org.eclipse.emf.ecp.view.spi.renderer.NoPropertyDescriptorFoundExeption;
import org.eclipse.emf.ecp.view.spi.renderer.NoRendererFoundException;
import org.eclipse.emfforms.spi.swt.core.layout.GridDescriptionFactory;
import org.eclipse.emfforms.spi.swt.core.layout.SWTGridCell;
import org.eclipse.emfforms.spi.swt.core.layout.SWTGridDescription;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import org.eclipse.set.core.services.Services;
import org.eclipse.set.core.services.viewmodel.ToolboxViewModelService;
import org.eclipse.set.emfforms.utils.RendererContextImpl;
import org.eclipse.set.utils.ButtonAction;
import org.eclipse.set.utils.emfforms.Annotations;

/**
 * A custom button control using a {@link ButtonAction}.
 * 
 * @author Schaefer
 */
public class ButtonControl extends ECPAbstractCustomControlSWT {

	/**
	 * The key for a button action annotation.
	 */
	public static final String BUTTON_ACTION_KEY = "buttonActionKey"; //$NON-NLS-1$

	@Override
	public SWTGridDescription getGridDescription() {
		return GridDescriptionFactory.INSTANCE.createSimpleGrid(1, 1, null);
	}

	@Override
	public Control renderControl(final SWTGridCell cell, final Composite parent)
			throws NoRendererFoundException, NoPropertyDescriptorFoundExeption {
		// retrieve button action
		final ToolboxViewModelService toolboxViewModelService = Services
				.getToolboxViewModelService();
		final String buttonActionKey = Annotations
				.getViewModelValue(getCustomControl(), BUTTON_ACTION_KEY);
		final ButtonAction buttonAction = (ButtonAction) toolboxViewModelService
				.get(buttonActionKey);

		// the button parameters
		final String buttonText = buttonAction.getText();
		final int buttonWidth = buttonAction.getWidth();

		// the button
		final Button button = new Button(parent, SWT.PUSH);
		button.setText(buttonText);
		GridDataFactory.swtDefaults().minSize(buttonWidth, 0)
				.hint(buttonWidth, SWT.DEFAULT).applyTo(button);
		final RendererContextImpl rendererContext = new RendererContextImpl();
		rendererContext.put(Button.class, button);
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

		return button;
	}

	@Override
	protected void disposeCustomControl() {
		// do nothing
	}

	@Override
	protected void handleContentValidation() {
		// do nothing
	}
}
