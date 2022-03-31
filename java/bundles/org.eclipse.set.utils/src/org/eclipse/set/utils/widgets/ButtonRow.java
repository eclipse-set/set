/**
 * Copyright (c) 2016 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.utils.widgets;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.internal.layout.Row;

/**
 * A row with buttons.
 * 
 * @author Schaefer
 */
public class ButtonRow {

	private static final float WIDTH_FACTOR = 0.2F;
	private int maxWidth;
	private final Composite row;

	/**
	 * @param parent
	 *            the parent
	 */
	public ButtonRow(final Composite parent) {
		row = new Composite(parent, SWT.NONE);
		final Composite space = new Composite(row, SWT.NONE);
		space.setLayout(new FillLayout());
		setLayout();
	}

	/**
	 * @param text
	 *            the button text
	 * 
	 * @return the button
	 */
	public Button add(final String text) {
		final Button button = new Button(row, SWT.NONE);
		button.setText(text);

		// layout
		final Point size = button.computeSize(SWT.DEFAULT, SWT.DEFAULT);
		if (maxWidth < size.x) {
			maxWidth = size.x;
		}
		setLayout();

		return button;
	}

	/**
	 * @return the buttons of this row
	 */
	public Button[] getButtons() {
		return (Button[]) row.getChildren();
	}

	/**
	 * @return the composite of this button row
	 */
	public Composite getComposite() {
		return row;
	}

	private void setLayout() {
		final int size = row.getChildren().length;
		// RAP_IMPROVE substitution of cellLayout with GridLayout
		// decision to keep or remove getInfo() needed
		// -> affects button width on attachment table
		final GridLayout layout = new GridLayout();
		layout.numColumns = size;
		// for (int i = 1; i < size; i++) {
		// layout.setColumn(i, getInfo(i));
		// } //for cellLayout
		row.setLayout(layout);
	}

	/**
	 * Not used locally due to fix in setLayout() therefore private -> protected
	 * 
	 * @param i
	 *            index of row
	 * @return a Row
	 */
	protected Row getInfo(final int i) {
		final Control control = row.getChildren()[i];
		final Point size = control.computeSize(SWT.DEFAULT, SWT.DEFAULT);
		size.x = Math.round(size.x + maxWidth * WIDTH_FACTOR);
		return Row.fixed(size.x);
	}
}
