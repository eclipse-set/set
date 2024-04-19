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
import org.eclipse.set.utils.widgets.FileField;
import org.eclipse.set.utils.widgets.MultiSelectionCombo;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

/**
 * A file field with two addition combo box to choose Subwork type and target
 * container for import model
 * 
 * @author Truong
 */
public class ImportComboFileField extends FileField {
	private final MultiSelectionCombo<SubworkComboSelection> subworkCombo;
	private final MultiSelectionCombo<ContainerComboSelection> containerCombo;

	/**
	 * @param parent
	 *            a composite control which will be the parent of the new
	 *            instance (cannot be null)
	 * @param filters
	 *            the used toolbox file filters
	 * @param dialogService
	 *            the dialog Service
	 */
	public ImportComboFileField(final Composite parent,
			final List<ToolboxFileFilter> filters,
			final DialogService dialogService) {
		super(parent, filters, dialogService);
		composite.setLayout(new GridLayout(4, false));
		subworkCombo = new MultiSelectionCombo<>(composite);
		containerCombo = new MultiSelectionCombo<>(composite);
	}

	/**
	 * @return combo for selection subwork
	 */
	public MultiSelectionCombo<SubworkComboSelection> getSubworkCombo() {
		return subworkCombo;
	}

	/**
	 * @return combo for selection container
	 */
	public MultiSelectionCombo<ContainerComboSelection> getContainerCombo() {
		return containerCombo;
	}

	/**
	 * Set combos values and state to default
	 * 
	 * @param messages
	 *            the messages
	 */
	public void setDefaultCombo(final Messages messages) {
		if (!subworkCombo.isDisposed()) {

			subworkCombo.setComboValues(SubworkComboSelection
					.getComboValues(null, PlanProFileNature.INVALID, messages));
			subworkCombo.setEnabled(false);
			subworkCombo.select(0);
		}

		if (!containerCombo.isDisposed()) {
			containerCombo.setComboValues(ContainerComboSelection
					.getComboValues(PlanProFileNature.INVALID, messages));

			containerCombo.setEnabled(false);
			containerCombo.select(0);
		}
	}

	/**
	 * Check if combo is selected
	 * 
	 * @param combo
	 *            the combo to check
	 * @return true, if combo is selected (value not equal NOT_SELECTD_SUBWORK
	 *         or NOT_SELECTED)
	 */
	public boolean isNotSelected(final TableComboViewer combo) {
		if (combo.equals(subworkCombo)) {
			return subworkCombo.getSelectedItems().isEmpty();
		}

		if (combo.equals(containerCombo)) {
			return containerCombo.getSelectedItems().isEmpty();
		}

		throw new IllegalArgumentException(
				"The file field combo not contain this combo"); //$NON-NLS-1$
	}
}
