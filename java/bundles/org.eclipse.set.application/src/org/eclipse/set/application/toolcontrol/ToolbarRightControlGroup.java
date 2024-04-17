/**
 * Copyright (c) 2024 DB InfraGO AG and others
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 */
package org.eclipse.set.application.toolcontrol;

import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.set.application.placearea.PlaceAreaSelectionControl;
import org.eclipse.set.application.tabletype.TableTypeSelectionControl;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

import jakarta.annotation.PostConstruct;

/**
 * Container for control on right side of toolbar
 * 
 * @author Truong
 */
public class ToolbarRightControlGroup {
	Composite composite;

	ServiceProvider serviceProvider;

	@PostConstruct
	private void postConstruct(final Composite parent,
			final IEclipseContext context) {
		serviceProvider = ContextInjectionFactory.make(ServiceProvider.class,
				context);
		composite = new Composite(parent, SWT.NONE);
		final GridLayout gridLayout = new GridLayout(2, false);
		composite.setLayout(gridLayout);
		composite.setLayoutData(new GridData(SWT.FILL, SWT.TOP, false, false));
		initControl();
	}

	@SuppressWarnings("unused")
	private void initControl() {
		final TableTypeSelectionControl tableTypeSelectionControl = new TableTypeSelectionControl(
				composite, serviceProvider);
		final PlaceAreaSelectionControl placeAreaSelectionControl = new PlaceAreaSelectionControl(
				composite, serviceProvider);
	}
}
