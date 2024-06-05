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
package org.eclipse.set.utils.widgets;

import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;

/**
 * Combo with {@link ComboValues} als items
 * 
 * @param <T>
 *            type of the values
 * 
 * @author Truong
 */
public class SelectionCombo<T> extends Combo {

	private ComboValues<T> values;

	/**
	 * Constructor
	 * 
	 * @param parent
	 *            the parent
	 * @param style
	 *            the style
	 */
	public SelectionCombo(final Composite parent, final int style) {
		super(parent, style);
	}

	/**
	 * Get combo values
	 * 
	 * @return values
	 */
	public ComboValues<T> getComboValues() {
		return values;
	}

	/**
	 * Get selected values
	 * 
	 * @return selected value
	 */
	public T getSelectionValue() {
		return values.getValue(getSelectionIndex());
	}

	/**
	 * Set values for this combo
	 * 
	 * @param values
	 *            the values
	 */
	public void setComboValues(final ComboValues<T> values) {
		if (isDisposed()) {
			return;
		}
		this.values = values;
		setItems(values.getComboValues());
		select(values.getDefaultIndex());
		setEnabled(values.getComboValues().length > 1);
	}

	@Override
	protected void checkSubclass() {
		// Subclass is not allow for Combo widget.
	}
}