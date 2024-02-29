/**
 * Copyright (c) 2016 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.application.toolcontrol;

import jakarta.annotation.PostConstruct;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

/**
 * A spacer for a toolbar.
 * 
 * @author Schaefer
 */
public class ToolbarSpacerControl {

	@PostConstruct
	private static void postConstruct(final Composite parent) {
		final Composite space = new Composite(parent, SWT.NONE);
		space.setLayout(new GridLayout());
	}
}
