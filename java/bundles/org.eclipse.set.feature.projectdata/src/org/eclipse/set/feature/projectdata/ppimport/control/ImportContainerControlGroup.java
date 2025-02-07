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
package org.eclipse.set.feature.projectdata.ppimport.control;

import java.util.List;
import java.util.stream.Stream;

import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.set.basis.IModelSession;
import org.eclipse.set.basis.exceptions.UserAbortion;
import org.eclipse.set.basis.files.ToolboxFileRole;
import org.eclipse.set.feature.projectdata.ppimport.control.ImportModelControl.ImportTarget;
import org.eclipse.set.feature.projectdata.utils.AbstractImportGroup;
import org.eclipse.set.feature.projectdata.utils.ImportComboFileField;
import org.eclipse.set.feature.projectdata.utils.ServiceProvider;
import org.eclipse.set.utils.widgets.Option;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Shell;

/**
 * 
 */
public class ImportContainerControlGroup extends AbstractImportGroup {

	private ImportModelControl importInitial;
	private ImportModelControl importFinal;

	private Option initialOption;
	private Option finalOption;

	/**
	 * @param serviceProvider
	 *            the {@link ServiceProvider}
	 * @param modelSession
	 *            the {@link IModelSession}
	 * @param broker
	 *            the {@link IEventBroker}
	 * 
	 */
	public ImportContainerControlGroup(final ServiceProvider serviceProvider,
			final IModelSession modelSession, final IEventBroker broker) {
		super(serviceProvider, modelSession, broker);
	}

	@Override
	public void createControl(final Composite parent, final Shell shell,
			final ToolboxFileRole role) {
		final Group group = createGroup(parent, shell,
				serviceProvider.messages.PlanProImportPart_importContainerGroup);
		createImportOption(group, ImportTarget.INITIAL);
		createImportContainerControl(group, shell,
				ToolboxFileRole.IMPORT_INITIAL_STATE, ImportTarget.INITIAL);

		createImportOption(group, ImportTarget.FINAL);
		createImportContainerControl(group, shell,
				ToolboxFileRole.IMPORT_FINAL_STATE, ImportTarget.FINAL);
		createImportButton(group,
				serviceProvider.messages.PlanpRoImportPart_importContainerButton);
	}

	private void createImportContainerControl(final Composite parent,
			final Shell shell, final ToolboxFileRole role,
			final ImportTarget importTarget) {
		final ImportModelControl importControl = new ImportModelControl(
				serviceProvider, modelSession, importTarget,
				this::updateImportButton);
		importControl.createControl(parent, shell, role);
		if (importTarget == ImportTarget.INITIAL) {
			importInitial = importControl;
		} else {
			importFinal = importControl;
		}
	}

	private Option createImportOption(final Composite parent,
			final ImportTarget target) {
		final Option checkbox = new Option(parent);
		checkbox.getLabel().setText(getOptionLabel(target));
		// toggle file field with option button
		checkbox.getButton().addSelectionListener(new SelectionListener() {

			@Override
			public void widgetDefaultSelected(final SelectionEvent e) {
				final ImportComboFileField comboField = getComboField(target);
				if (comboField == null) {
					return;
				}
				comboField.setEnabled(checkbox.getButton().getSelection());
				updateImportButton();
			}

			@Override
			public void widgetSelected(final SelectionEvent e) {
				widgetDefaultSelected(e);
			}
		});
		if (target == ImportTarget.INITIAL) {
			initialOption = checkbox;
		} else {
			finalOption = checkbox;
		}
		return checkbox;
	}

	private String getOptionLabel(final ImportTarget target) {
		switch (target) {
			case INITIAL:
				return serviceProvider.messages.PlanProImportPart_importInitial;
			case FINAL:
				return serviceProvider.messages.PlanProImportPart_importFinal;
			default:
				throw new IllegalArgumentException();

		}
	}

	private ImportComboFileField getComboField(final ImportTarget target) {
		switch (target) {
			case INITIAL:
				return importInitial.getComboField();
			case FINAL:
				return importFinal.getComboField();
			default:
				throw new IllegalArgumentException();

		}
	}

	@Override
	public void doImport(final Shell shell) {
		final List<ImportModelControl> controlList = List.of(importInitial,
				importFinal);
		controlList.forEach(control -> control.doImport(shell));
		final boolean isSomethingImported = controlList.stream()
				.anyMatch(ImportModelControl::isImported);
		try {
			if (isSomethingImported) {
				updateModelAfterImport(shell);
			}
		} catch (IllegalArgumentException | NullPointerException e) {
			throw new RuntimeException(e);
		} catch (final UserAbortion e) {
			// We ignore an user abortion
		} finally {
			if (modelSession.isDirty()) {
				modelSession.revert();
			}
		}
	}

	@Override
	public void updateImportButton() {
		final List<ImportModelControl> enableImport = Stream
				.of(importInitial, importFinal)
				.filter(i -> i.isEnabled())
				.toList();
		importButton.setEnabled(!enableImport.isEmpty()
				&& enableImport.stream().allMatch(i -> i.isValid()));
	}

	@Override
	protected void resetGroup() {
		importFinal.resetControl();
		importInitial.resetControl();
		initialOption.getButton().setSelection(false);
		finalOption.getButton().setSelection(false);
		importButton.setEnabled(false);
		importFinal.getComboField().setEnabled(false);
		importInitial.getComboField().setEnabled(false);
	}

}
