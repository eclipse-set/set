/**
 * Copyright (c) 2015 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.basis.ui.utils;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;

/**
 * This utility class provides common UI methods.
 * 
 * @author Schaefer
 */
public class UiUtils {

	private static final float SCALE_X = 1.5f;
	private static final float SCALE_Y = 1.0f;

	/**
	 * Normalize the button size to common conventions.
	 * 
	 * @param button
	 *            the button
	 */
	public static void normalize(final Button button) {
		final Point size = button.computeSize(SWT.DEFAULT, SWT.DEFAULT);
		float x = size.x * SCALE_X;
		final float y = size.y * SCALE_Y;
		x = Math.max(x, 3 * y);
		final Point newSize = new Point(Math.round(x), Math.round(y));
		button.setSize(newSize);
		if (button.getParent().getLayout() instanceof GridLayout) {
			final GridData layoutData = new GridData(newSize.x, newSize.y);
			button.setLayoutData(layoutData);
		}
	}

}
