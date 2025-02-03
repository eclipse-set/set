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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITableColorProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.nebula.jface.tablecomboviewer.TableComboViewer;
import org.eclipse.nebula.widgets.tablecombo.TableCombo;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;

/**
 * Multiselection combo with {@link ComboValues} als items
 * 
 * @author Truong
 * @param <T>
 *            type of the values
 */
public class MultiSelectionCombo<T> extends TableComboViewer {
	private ComboValues<T> values;
	private final List<String> selectedItems;
	private final String selectionAllLabel;

	private static class MultiSelectionLabelProvider<T> extends LabelProvider
			implements ITableLabelProvider, ITableColorProvider {

		MultiSelectionCombo<T> selectionCombo;

		public MultiSelectionLabelProvider(
				final MultiSelectionCombo<T> selectionCombo) {
			this.selectionCombo = selectionCombo;
		}

		@Override
		public Image getColumnImage(final Object element,
				final int columnIndex) {
			return null;
		}

		@Override
		public String getColumnText(final Object element,
				final int columnIndex) {
			if (element instanceof final String strElement) {
				return strElement;
			}
			return null;
		}

		@Override
		public Color getForeground(final Object element,
				final int columnIndex) {
			return null;
		}

		@Override
		public Color getBackground(final Object element,
				final int columnIndex) {
			if (isSelected(element)) {
				return Display.getCurrent().getSystemColor(SWT.COLOR_GRAY);
			}
			return Display.getCurrent().getSystemColor(SWT.COLOR_WHITE);
		}

		private boolean isSelected(final Object element) {
			if (element instanceof final String strElement) {
				return selectionCombo.getSelectValuesString()
						.contains(strElement);
			}
			return false;
		}
	}

	/**
	 * Constructor
	 * 
	 * @param parent
	 *            the parent
	 * @param selectionAllLabel
	 *            the selection all label
	 */
	public MultiSelectionCombo(final Composite parent,
			final String selectionAllLabel) {
		this(parent, selectionAllLabel,
				SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);
	}

	/**
	 * Constructor
	 * 
	 * @param parent
	 *            the parent
	 * @param selectionAllLabel
	 *            the selection all label
	 * @param style
	 *            SWT style
	 */
	public MultiSelectionCombo(final Composite parent,
			final String selectionAllLabel, final int style) {
		super(parent, style);
		selectedItems = new ArrayList<>();
		this.selectionAllLabel = selectionAllLabel;
		this.setContentProvider(ArrayContentProvider.getInstance());
		this.getTableCombo().setClosePopupAfterSelection(false);
		this.setLabelProvider(new MultiSelectionLabelProvider<>(this));
		this.addSelectionChangedListener(getSelectionChangedListener());
		getTableCombo().setEditable(false);
		getTableCombo().setShowColorWithinSelection(false);
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
	 * Set values for this combo
	 * 
	 * @param values
	 *            the values
	 */
	public void setComboValues(final ComboValues<T> values) {
		removeAll();
		if (getTableCombo().isDisposed()
				|| getTableCombo().getTable().isDisposed()) {
			return;
		}
		this.values = values;
		final List<String> items = new LinkedList<>();
		final String[] comboValues = values.getComboValues();
		items.addAll(Arrays.asList(comboValues));
		if (comboValues.length > 2) {
			items.add(selectionAllLabel);
		}

		if (comboValues.length == 1) {
			getTableCombo().setEnabled(false);
			selectedItems.add(values.getDefaultValue());
		}
		this.setInput(items.toArray(new String[0]));
		select(values.getDefaultIndex());
	}

	/**
	 * @param selectionIndex
	 *            the index
	 */
	public void select(final int selectionIndex) {
		select(new int[] { selectionIndex });
	}

	/**
	 * @param selectionIndexes
	 *            the indexes
	 */
	public void select(final int[] selectionIndexes) {
		doSelect(selectionIndexes);
	}

	/**
	 * @return true, if the combo is dispose
	 */
	public boolean isDisposed() {
		return getTableCombo().isDisposed()
				|| getTableCombo().getTable().isDisposed();
	}

	/**
	 * Reference for {@link TableCombo#setEnabled(boolean)}
	 * 
	 * @param value
	 *            the value
	 */
	public void setEnabled(final boolean value) {
		getTableCombo().setEnabled(value);
	}

	/**
	 * @return the selected values string
	 */
	public List<String> getSelectValuesString() {
		if (values != null && values.size() < 2) {
			return selectedItems;
		}
		return selectedItems.stream()
				.filter(item -> !item.equals(values.getDefaultValue()))
				.toList();
	}

	/**
	 * @return {@link TableCombo#getItems()}
	 */
	public String[] getItems() {
		return getTableCombo().getItems();
	}

	/**
	 * @return selection values
	 */
	public List<T> getSelectionValues() {
		if (values != null && values.size() < 2) {
			return List.of(values.getValue(values.getDefaultIndex()));
		}
		return selectedItems.stream()
				.filter(item -> !item.equals(values.getDefaultValue()))
				.map(item -> values.getValue(item))
				.toList();
	}

	/**
	 * Remove all combo values
	 */
	public void removeAll() {
		getTableCombo().clearSelection();
		getTableCombo().getTable().removeAll();
		selectedItems.clear();
	}

	private ISelectionChangedListener getSelectionChangedListener() {
		return event -> {
			if (event
					.getSelection() instanceof final IStructuredSelection selection
					&& selection
							.getFirstElement() instanceof final String item) {
				toogleSelection(item);
				if (event
						.getSource() instanceof final TableComboViewer viewer) {
					updateViewer(item, viewer);
				}
			}
		};
	}

	private void updateViewer(final String selectionItem,
			final TableComboViewer viewer) {
		viewer.getTableCombo().setText(getComboText());
		if (selectionItem.equals(selectionAllLabel)
				|| selectionItem.equals(values.getDefaultValue())) {
			for (final String i : values.getComboValues()) {
				viewer.update(i, null);
			}
		} else {
			viewer.update(selectionItem, null);
		}
	}

	/**
	 * @return combo text
	 */
	public String getComboText() {
		if (selectedItems.size() == 1
				&& selectedItems.getFirst().equals(values.getDefaultValue())) {
			return values.getDefaultValue();
		}
		return String.join(", ", //$NON-NLS-1$
				selectedItems.stream()
						.filter(i -> !i.equals(values.getDefaultValue()))
						.toList());
	}

	private void toogleSelection(final String item) {
		final List<String> comboValues = new ArrayList<>();
		comboValues.addAll(Arrays.asList(values.getComboValues()));
		// Remove default value, by select all
		if (comboValues.size() > 1) {
			comboValues.remove(values.getDefaultValue());
		}
		if (item.equals(values.getDefaultValue())
				|| item.equals(selectionAllLabel)
						&& selectedItems.containsAll(comboValues)) {
			selectedItems.clear();
			selectedItems.add(values.getDefaultValue());
		} else if (item.equals(selectionAllLabel)) {
			comboValues.forEach(ele -> {
				if (!selectedItems.contains(ele)) {
					selectedItems.add(ele);
				}
			});
		} else if (selectedItems.contains(item)) {
			selectedItems.remove(item);
			if (selectedItems.isEmpty()) {
				selectedItems.add(values.getDefaultValue());
			}
		} else {
			selectedItems.add(item);
		}
	}
}