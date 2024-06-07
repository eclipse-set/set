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
package org.eclipse.set.feature.projectdata.ppimport;

import java.util.List;

import org.eclipse.nebula.jface.tablecomboviewer.TableComboViewer;
import org.eclipse.set.basis.constants.PlanProFileNature;
import org.eclipse.set.basis.files.ToolboxFileFilter;
import org.eclipse.set.core.services.dialog.DialogService;
import org.eclipse.set.feature.projectdata.Messages;
import org.eclipse.set.utils.widgets.ComboValues;
import org.eclipse.set.utils.widgets.FileField;
import org.eclipse.set.utils.widgets.MultiSelectionCombo;
import org.eclipse.set.utils.widgets.SelectionCombo;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;

/**
 * A file field with two addition combo box to choose Subwork type and target
 * container for import model
 * 
 * @author Truong
 */
public class ImportComboFileField extends FileField {
	private final MultiSelectionCombo<SubworkComboSelection> subworkCombo;

	// Container Combo is use only by import to single container, therefore this
	// combo shouldn't multi selection
	private final SelectionCombo<ContainerComboSelection> containerCombo;
	private final Messages messages;

	/**
	 * @param parent
	 *            a composite control which will be the parent of the new
	 *            instance (cannot be null)
	 * @param filters
	 *            the used toolbox file filters
	 * @param dialogService
	 *            the dialog Service
	 * @param messages
	 *            the messages container
	 */
	public ImportComboFileField(final Composite parent,
			final List<ToolboxFileFilter> filters,
			final DialogService dialogService, final Messages messages) {
		super(parent, filters, dialogService);
		composite.setLayout(new GridLayout(4, false));
		subworkCombo = new MultiSelectionCombo<>(composite,
				messages.PlanProImportPart_Subwork_All);
		containerCombo = new SelectionCombo<>(composite, SWT.NONE);
		this.messages = messages;
	}

	@Override
	public void setEnabled(final boolean value) {
		super.setEnabled(value);
		updateSubworkComboState();
		updateContainerComboState();
	}

	/**
	 * @return combo for selection subwork
	 */
	public MultiSelectionCombo<SubworkComboSelection> getSubworkCombo() {
		return subworkCombo;
	}

	/**
	 * Set values for subwork combo
	 * 
	 * @param values
	 *            the combo values
	 */
	public void setSubworkComboValues(
			final ComboValues<SubworkComboSelection> values) {
		subworkCombo.setComboValues(values);
		updateSubworkComboState();
	}

	private void updateSubworkComboState() {
		if (subworkCombo.isDisposed()) {
			return;
		}

		subworkCombo.setEnabled(isEnabled()
				&& subworkCombo.getComboValues() != null
				&& subworkCombo.getComboValues().getComboValues().length > 1);
	}

	/**
	 * @return combo for selection container
	 */
	public SelectionCombo<ContainerComboSelection> getContainerCombo() {
		return containerCombo;
	}

	/**
	 * Set value for container combo
	 * 
	 * @param values
	 *            the combo values
	 */
	public void setContainerComboValues(
			final ComboValues<ContainerComboSelection> values) {
		if (containerCombo.isDisposed()) {
			return;
		}
		containerCombo.setComboValues(values);
		updateContainerComboState();
	}

	private void updateContainerComboState() {
		if (containerCombo.isDisposed()) {
			return;
		}
		containerCombo.setEnabled(isEnabled() && containerCombo.isEnabled()
				&& containerCombo.getComboValues() != null
				&& containerCombo.getComboValues().getComboValues().length > 1
				&& !isNotSelected(subworkCombo));
	}

	/**
	 * Set combos values and state to default
	 */
	public void setDefaultCombo() {
		if (!subworkCombo.isDisposed()) {
			final ComboValues<SubworkComboSelection> comboValues = SubworkComboSelection
					.getComboValues(null, PlanProFileNature.INVALID, messages);
			subworkCombo.setComboValues(comboValues);
			subworkCombo.add(comboValues.getMaxLengthItem());
			subworkCombo.setEnabled(false);
			subworkCombo.select(0);
		}

		if (!containerCombo.isDisposed()) {
			final ComboValues<ContainerComboSelection> comboValues = ContainerComboSelection
					.getComboValues(PlanProFileNature.INVALID, messages);
			containerCombo.setComboValues(comboValues);
			containerCombo.add(comboValues.getMaxLengthItem());
			containerCombo.setEnabled(false);
			containerCombo.select(0);
		}
	}

	/**
	 * Combo file field is valid, when this file field is active and avaible
	 * combo have selected value
	 * 
	 * @return whether combo file field is valid
	 */
	public boolean isValid() {
		return isEnabled() && !getText().getText().isEmpty()
				&& !subworkCombo.isDisposed() && !isNotSelected(subworkCombo)
				&& (containerCombo.isDisposed()
						|| !isNotSelected(containerCombo));
	}

	/**
	 * Check if combo is selected
	 * 
	 * @param combo
	 *            the combo to check
	 * @return true, if at least one value was selected
	 */
	public boolean isNotSelected(final TableComboViewer combo) {
		// When the model to import missing subwork type or this is a single
		// state model
		if (subworkCombo.getItems().length == 1
				&& subworkCombo.getComboValues().getValue(0).getName()
						.equals(SubworkComboSelection.NOT_SET_SUBWORK)) {
			return false;
		}
		return subworkCombo.getSelectionValues().isEmpty();
	}

	/**
	 * Check if combo is selected
	 * 
	 * @param combo
	 *            the combo to check
	 * @return true, if combo is selected (value not equal NOT_SELECTD_SUBWORK
	 *         or NOT_SELECTED)
	 */
	public boolean isNotSelected(final Combo combo) {
		return containerCombo.getSelectionValue()
				.equals(ContainerComboSelection.NOT_SELECTED);
	}
}
