/**
 * Copyright (c) 2022 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.emfforms.controls;

import java.util.function.Function;

import org.eclipse.emf.ecp.view.model.common.AbstractGridCell.Alignment;
import org.eclipse.emf.ecp.view.spi.custom.swt.ECPAbstractCustomControlSWT;
import org.eclipse.emf.ecp.view.spi.renderer.NoPropertyDescriptorFoundExeption;
import org.eclipse.emf.ecp.view.spi.renderer.NoRendererFoundException;
import org.eclipse.emfforms.spi.swt.core.layout.GridDescriptionFactory;
import org.eclipse.emfforms.spi.swt.core.layout.SWTGridCell;
import org.eclipse.emfforms.spi.swt.core.layout.SWTGridDescription;
import org.eclipse.set.core.services.Services;
import org.eclipse.set.core.services.viewmodel.ToolboxViewModelService;
import org.eclipse.set.utils.emfforms.Annotations;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

/**
 * A dummy control to externally inject a control into an EMFForms viewmodel.
 * 
 * Requires an annotation 'injectedControlKey' and a corresponding callback
 * provided by {@link ToolboxViewModelService#get(String)}
 * 
 * @author Stuecker
 */
public class InjectedControl extends ECPAbstractCustomControlSWT {

	/**
	 * The key for a button action annotation.
	 */
	public static final String INJECTED_CONTROL_KEY = "injectedControlKey"; //$NON-NLS-1$

	private final SWTGridDescription gridDescription;
	private final SWTGridCell gridCell;

	/**
	 * Constructor
	 */
	public InjectedControl() {
		super();
		gridDescription = GridDescriptionFactory.INSTANCE.createSimpleGrid(1, 1,
				null);
		gridCell = gridDescription.getGrid().get(0);
	}

	@Override
	public SWTGridDescription getGridDescription() {
		return gridDescription;
	}

	@Override
	public Control renderControl(final SWTGridCell cell, final Composite parent)
			throws NoRendererFoundException, NoPropertyDescriptorFoundExeption {
		// retrieve button action
		final ToolboxViewModelService toolboxViewModelService = Services
				.getToolboxViewModelService();
		final String injectionKey = Annotations
				.getViewModelValue(getCustomControl(), INJECTED_CONTROL_KEY);
		@SuppressWarnings("unchecked")
		final Function<Composite, Control> createFunction = (Function<Composite, Control>) toolboxViewModelService
				.get(injectionKey);

		final Control control = createFunction.apply(parent);

		// Apply layout from GridData to the GridCell (if present)
		final Object layoutData = control.getLayoutData();
		if (layoutData instanceof final GridData gridData) {
			applyGridDataLayout(gridData);
		}

		return control;
	}

	private void applyGridDataLayout(final GridData gridData) {
		gridCell.setHorizontalAlignment(
				swtAlignmentToAlignment(gridData.horizontalAlignment,
						gridCell.getHorizontalAlignment()));
		gridCell.setHorizontalGrab(gridData.grabExcessHorizontalSpace);
		gridCell.setHorizontalSpan(gridData.horizontalSpan);
		gridCell.setHorizontalFill(gridData.horizontalAlignment == SWT.FILL);
		gridCell.setPreferredSize(gridData.widthHint, gridData.heightHint);
		gridCell.setVerticalAlignment(swtAlignmentToAlignment(
				gridData.verticalAlignment, gridCell.getVerticalAlignment()));
		gridCell.setVerticalGrab(gridData.grabExcessVerticalSpace);
		gridCell.setVerticalFill(gridData.verticalAlignment == SWT.FILL);
	}

	/**
	 * Translates a GridData SWT alignment to an EMF Forms alignment
	 * 
	 * @param alignment
	 *            the grid data alignment
	 * @param defaultValue
	 *            the default to return
	 * @return the translated alignment or default if no translation was found
	 */
	private static Alignment swtAlignmentToAlignment(final int alignment,
			final Alignment defaultValue) {
		switch (alignment) {
			case SWT.BEGINNING:
			case SWT.LEFT:
				return Alignment.BEGINNING;
			case SWT.CENTER:
				return Alignment.CENTER;
			case SWT.END:
			case SWT.RIGHT:
				return Alignment.END;
			default:
				return defaultValue;
		}
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
