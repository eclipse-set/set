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
import java.util.List;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITableColorProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
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
				return selectionCombo.getSelectedItems().contains(strElement);
			}
			return false;
		}
	}

	/**
	 * Constructor
	 * 
	 * @param parent
	 *            the parent
	 * 
	 */
	public MultiSelectionCombo(final Composite parent) {
		super(parent);
		selectedItems = new ArrayList<>();
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
		if (getTableCombo().isDisposed()
				|| getTableCombo().getTable().isDisposed()) {
			return;
		}
		this.values = values;
		this.setInput(values.getItems());
		select(values.getDefault());
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
	 * @return the selected items string
	 */
	public List<String> getSelectedItems() {
		return selectedItems;
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
	@SuppressWarnings("boxing")
	public List<T> getSelectionValues() {
		return selectedItems.stream().map(item -> values.getIndex(item))
				.map(index -> values.getValue(index)).toList();
	}

	private ISelectionChangedListener getSelectionChangedListener() {
		return new ISelectionChangedListener() {
			@Override
			public void selectionChanged(final SelectionChangedEvent event) {
				if (event
						.getSelection() instanceof final IStructuredSelection selection
						&& selection
								.getFirstElement() instanceof final String item) {
					toogleSelection(item);
					if (event
							.getSource() instanceof final TableComboViewer viewer) {
						viewer.getTableCombo()
								.setText(String.join(", ", selectedItems)); //$NON-NLS-1$
						viewer.update(item, null);
					}
				}
			}

			private void toogleSelection(final String item) {
				if (values.getIndex(item) == values.getDefault()) {
					selectedItems.clear();
				} else if (selectedItems.contains(item)) {
					selectedItems.remove(item);
				} else {
					selectedItems.add(item);
				}
			}
		};
	}

}
