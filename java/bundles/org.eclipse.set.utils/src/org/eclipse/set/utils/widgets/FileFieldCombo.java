/**
 * Copyright (c) 2018 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.utils.widgets;

import java.util.List;

import org.eclipse.set.basis.files.ToolboxFileFilter;
import org.eclipse.set.core.services.dialog.DialogService;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;

/**
 * A file field with an additional combo box.
 * 
 * @param <T>
 *            the type of the combo box values
 * 
 * @author Schaefer
 */
public class FileFieldCombo<T> extends FileField {

	private ComboValues<T> values;

	Combo combo;

	/**
	 * @param parent
	 *            a composite control which will be the parent of the new
	 *            instance (cannot be null)
	 * @param filters
	 *            the used toolbox file filters
	 * @param style
	 *            the style for the combo box
	 * @param dialogService
	 *            the dialog Service
	 */
	public FileFieldCombo(final Composite parent,
			final List<ToolboxFileFilter> filters, final int style,
			final DialogService dialogService) {
		super(parent, filters, dialogService);
		composite.setLayout(new GridLayout(3, false));
		combo = new Combo(composite, style);
	}

	/**
	 * @return the combo control
	 */
	public Combo getCombo() {
		return combo;
	}

	/**
	 * @return the value of the combo box
	 */
	public T getComboValue() {
		return values.getValue(combo.getSelectionIndex());
	}

	/**
	 * @param value
	 *            the value to select
	 */
	public void select(final T value) {
		combo.select(values.getIndex(value));
	}

	/**
	 * @param enabled
	 *            whether the combo box shall be enabled
	 */
	public void setComboEnabled(final boolean enabled) {
		combo.setEnabled(enabled);
	}

	/**
	 * @param values
	 *            the values of the combo box
	 */
	public void setComboValues(final ComboValues<T> values) {
		this.values = values;
		combo.setItems(values.getComboValues());
		combo.select(values.getDefaultIndex());
	}
}
