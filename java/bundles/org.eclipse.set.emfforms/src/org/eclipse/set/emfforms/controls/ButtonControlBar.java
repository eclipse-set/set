/**
 * Copyright (c) 2019 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.emfforms.controls;

import java.util.List;

import org.eclipse.emf.ecp.view.spi.custom.swt.ECPAbstractCustomControlSWT;
import org.eclipse.emf.ecp.view.spi.model.VElement;
import org.eclipse.emf.ecp.view.spi.renderer.NoPropertyDescriptorFoundExeption;
import org.eclipse.emf.ecp.view.spi.renderer.NoRendererFoundException;
import org.eclipse.emfforms.spi.swt.core.layout.GridDescriptionFactory;
import org.eclipse.emfforms.spi.swt.core.layout.SWTGridCell;
import org.eclipse.emfforms.spi.swt.core.layout.SWTGridDescription;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.set.core.services.Services;
import org.eclipse.set.core.services.viewmodel.ToolboxViewModelService;
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
 * A custom button control bar using {@link ButtonAction}s.
 * 
 * @author Schaefer
 */
public class ButtonControlBar extends ECPAbstractCustomControlSWT {

	private ToolboxViewModelService toolboxViewModelService;

	@Override
	public SWTGridDescription getGridDescription() {
		return GridDescriptionFactory.INSTANCE.createSimpleGrid(1, 1, null);
	}

	@Override
	public Control renderControl(final SWTGridCell cell, final Composite parent)
			throws NoRendererFoundException, NoPropertyDescriptorFoundExeption {
		// retrieve button actions keys
		toolboxViewModelService = Services.getToolboxViewModelService();
		final List<String> buttonActionKeys = Annotations.getViewModelValues(
				getCustomControl(), ButtonControl.BUTTON_ACTION_KEY);

		// create the control
		final Composite composite = new Composite(parent, SWT.NONE);
		GridLayoutFactory.fillDefaults().numColumns(buttonActionKeys.size() + 1)
				.applyTo(composite);

		// create the buttons
		buttonActionKeys.forEach(key -> createButton(key, composite));

		return composite;
	}

	private void createButton(final String key, final Composite parent) {
		// retrieve button action
		final ButtonAction buttonAction = (ButtonAction) toolboxViewModelService
				.get(key);

		// test if button action exists
		if (buttonAction == null) {
			throw new IllegalArgumentException(
					"Button action \"" + key + "\" not found."); //$NON-NLS-1$ //$NON-NLS-2$
		}

		// create the button
		final Button button = new Button(parent, SWT.PUSH);
		button.setText(buttonAction.getText());
		final RendererContextImpl rendererContext = new RendererContextImpl();
		rendererContext.put(Button.class, button);
		rendererContext.put(VElement.class, getCustomControl());
		buttonAction.register(rendererContext);

		// call the action
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
