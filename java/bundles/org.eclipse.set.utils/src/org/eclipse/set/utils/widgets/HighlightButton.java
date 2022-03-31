/**
 * Copyright (c) 2019 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.utils.widgets;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;

/**
 * A button that can be highlighted.
 * 
 * @author Schaefer
 */
public class HighlightButton {

	private static final String HIGHLIGHT_MARK = "> "; //$NON-NLS-1$

	private final Button button;

	private boolean highlight = false;

	/**
	 * @param parent
	 *            the parent
	 * @param style
	 *            the style of the button
	 */
	public HighlightButton(final Composite parent, final int style) {
		button = new Button(parent, style);
	}

	/**
	 * @return the button
	 */
	public Button getButton() {
		return button;
	}

	/**
	 * @param value
	 *            whether to highlight the button or not
	 */
	public void setHighlight(final boolean value) {
		if (highlight == value) {
			return;
		}
		highlight = value;
		if (value == true) {
			button.setBackground(
					Display.getCurrent().getSystemColor(SWT.COLOR_DARK_GRAY));
			button.setForeground(
					Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));
			button.setText(HIGHLIGHT_MARK + button.getText());
		} else {
			button.setBackground(null);
			button.setForeground(null);
			button.setText(button.getText().replaceFirst(HIGHLIGHT_MARK, "")); //$NON-NLS-1$
		}
	}
}
