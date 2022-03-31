/**
 * Copyright (c) 2016 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.utils;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import com.google.common.base.CharMatcher;

/**
 * Helper class for creating swt specific text fields.
 * 
 * @author rumpf
 *
 */
public class Textfields {

	/**
	 * creates a label formular dialog entry
	 * 
	 * @param container
	 *            the parent container
	 * @param labelText
	 *            the label text
	 * @param style
	 *            the text field style
	 * @return the text field
	 */
	public static Text createDialogEntry(final Composite container,
			final String labelText, final TextfieldStyle style) {
		final Label label = new Label(container, SWT.NONE);
		label.setText(labelText);
		final Text textfield;
		if (style == TextfieldStyle.ALL_DIGITS) {
			textfield = Textfields.createDigitOnlyTextField(container);
		} else if (style == TextfieldStyle.INTEGER) {
			textfield = Textfields.createIntegerOnlyTextField(container);
		} else {
			textfield = new Text(container, SWT.BORDER);
		}
		final GridData gridData = new GridData();
		gridData.grabExcessHorizontalSpace = true;
		gridData.horizontalAlignment = GridData.FILL;
		gridData.horizontalSpan = 1;
		textfield.setLayoutData(gridData);
		return textfield;
	}

	/**
	 * creates a digit only text field
	 * 
	 * @param container
	 *            the parent container
	 * @return the text field
	 */
	public static Text createDigitOnlyTextField(final Composite container) {
		final Text field = new Text(container, SWT.BORDER);
		field.addVerifyListener(new VerifyListener() {

			@Override
			public void verifyText(final VerifyEvent e) {
				final Text text = (Text) e.getSource();

				// get old text and create new text by using the
				// VerifyEvent.text
				final String oldS = text.getText();
				final String newS = oldS.substring(0, e.start) + e.text
						+ oldS.substring(e.end);

				final boolean isDigit = CharMatcher.inRange('0', '9')
						.matchesAllOf(newS);

				if (!isDigit) {
					e.doit = false;
				}
			}
		});
		return field;
	}

	/**
	 * creates an integer only text field
	 * 
	 * @param container
	 *            the parent container
	 * @return the text field
	 */
	public static Text createIntegerOnlyTextField(final Composite container) {
		final Text field = new Text(container, SWT.BORDER);
		field.addVerifyListener(new VerifyListener() {

			@Override
			public void verifyText(final VerifyEvent e) {
				final Text text = (Text) e.getSource();

				// get old text and create new text by using the
				// VerifyEvent.text
				final String oldS = text.getText();
				final String newS = oldS.substring(0, e.start) + e.text
						+ oldS.substring(e.end);

				boolean isInteger = true;
				try {
					Integer.parseInt(newS);
				} catch (final NumberFormatException ex) {
					isInteger = false;
				}

				if (!isInteger) {
					e.doit = false;
				}
			}
		});
		return field;
	}

	private Textfields() {
		super();
	}

}
