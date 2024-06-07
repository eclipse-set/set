/**
 * Copyright (c) 2017 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.projectdata.ppimport;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

import org.eclipse.set.basis.constants.Events;
import org.eclipse.set.basis.exceptions.UserAbortion;
import org.eclipse.set.basis.files.ToolboxFileRole;
import org.eclipse.set.core.fileservice.ToolboxIDResolver;
import org.eclipse.set.feature.projectdata.ppimport.ImportModelControl.ImportTarget;
import org.eclipse.set.feature.projectdata.utils.ServiceProvider;
import org.eclipse.set.model.planpro.PlanPro.Container_AttributeGroup;
import org.eclipse.set.ppmodel.extensions.PlanProSchnittstelleExtensions;
import org.eclipse.set.utils.BasePart;
import org.eclipse.set.utils.RefreshAction;
import org.eclipse.set.utils.SelectableAction;
import org.eclipse.set.utils.events.ContainerDataChanged;
import org.eclipse.set.utils.events.EditingCompleted;
import org.eclipse.set.utils.events.ToolboxEvents;
import org.eclipse.set.utils.widgets.SelectionCombo;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Shell;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.inject.Inject;

/**
 * Import PlanPro models.
 * 
 * @author Schaefer
 */
public class PlanProImportPart extends BasePart {

	static final Logger logger = LoggerFactory
			.getLogger(PlanProImportPart.class);

	private Button importButton;

	protected ModelInformationGroup modelInformationGroup;

	@Inject
	private ServiceProvider serviceProvider;

	private ImportModelControl importInitial;
	private ImportModelControl importFinal;
	private ImportModelControl importModel;

	private ImportLayoutControlGroup importLayout;

	/**
	 * 
	 */
	@Inject
	public PlanProImportPart() {
		super();
	}

	@Override
	protected void createView(final Composite parent) {
		importModel = new ImportModelControl(serviceProvider, getModelSession(),
				ImportTarget.ALL, this::updateImportButton);
		importInitial = new ImportModelControl(serviceProvider,
				getModelSession(), ImportTarget.INITIAL,
				this::updateImportButton);
		importFinal = new ImportModelControl(serviceProvider, getModelSession(),
				ImportTarget.FINAL, this::updateImportButton);

		if (PlanProSchnittstelleExtensions
				.isPlanning(getModelSession().getPlanProSchnittstelle())) {
			modelInformationGroup = new ModelInformationGroup(getModelSession(),
					serviceProvider.messages);
			modelInformationGroup.createInfoGroup(parent);
			createImportGroup(parent, getToolboxShell());

			importLayout = new ImportLayoutControlGroup(serviceProvider,
					getModelSession(), getBroker());
			importLayout.createControl(parent, getToolboxShell(),
					ToolboxFileRole.THIRD_PLANNING);
		} else {
			modelInformationGroup.createNotSupportedInfo(parent);
		}
	}

	private void createImportGroup(final Composite parent, final Shell shell) {
		final Group group = new Group(parent, SWT.SHADOW_ETCHED_IN);
		group.setText(serviceProvider.messages.PlanProImportPart_importGroup);
		group.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false));
		group.setLayout(new GridLayout());

		importModel.createControl(group, shell,
				ToolboxFileRole.SECONDARY_PLANNING);
		importModel.getComboField().getContainerCombo().dispose();

		importInitial.createControl(group, shell,
				ToolboxFileRole.IMPORT_INITIAL_STATE);

		importFinal.createControl(group, shell,
				ToolboxFileRole.IMPORT_FINAL_STATE);
		createImportButton(group);

	}

	private void createImportButton(final Composite parent) {
		importButton = new Button(parent, SWT.NONE);
		importButton.setText(
				serviceProvider.messages.PlanpRoImportPart_importModelButton);
		updateImportButton();
		importButton.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetDefaultSelected(final SelectionEvent e) {
				modelImport(parent.getShell());
			}

			@Override
			public void widgetSelected(final SelectionEvent e) {
				widgetDefaultSelected(e);
			}
		});
	}

	protected void modelImport(final Shell shell) {
		try {
			final List<ImportModelControl> controlList = List.of(importModel,
					importInitial, importFinal);
			final List<ImportModelControl> enableControl = controlList.stream()
					.filter(c -> c.isEnabled()).toList();

			// The import data from each control should be different
			if (isExistSameImportData(enableControl)) {
				getDialogService().openInformation(shell, getViewTitle(),
						serviceProvider.messages.PlanProImportPart_ImportSameData);
				return;
			}
			controlList.forEach(control -> control.doImport(shell));
			final boolean isSomethingImported = controlList.stream()
					.anyMatch(ImportModelControl::isImported);
			if (isSomethingImported) {
				ToolboxIDResolver.resolveIDReferences(
						getModelSession().getPlanProSchnittstelle());
				getModelSession().save(shell);
				getDialogService().reportImported(shell);
				resetImportGroup();
				ToolboxEvents.send(getBroker(), new EditingCompleted());
				getBroker().send(Events.MODEL_CHANGED,
						getModelSession().getPlanProSchnittstelle());
			}
		} catch (IllegalArgumentException | NullPointerException e) {
			throw new RuntimeException(e);
		} catch (final UserAbortion e) {
			// We ignore an user abortion
		} finally {
			if (getModelSession().isDirty()) {
				getModelSession().revert();
			}
		}
	}

	private void resetImportGroup() {
		importModel.resetControl();
		importInitial.resetControl();
		importFinal.resetControl();
		importButton.setEnabled(false);
	}

	void updateImportButton() {
		final List<ImportModelControl> enableImport = Stream
				.of(importModel, importInitial, importFinal)
				.filter(i -> i.isEnabled()).toList();
		importButton.setEnabled(!enableImport.isEmpty()
				&& enableImport.stream().allMatch(i -> i.isValid()));
	}

	private boolean isExistSameImportData(
			final List<ImportModelControl> controls) {
		if (controls.size() < 2) {
			return false;
		}
		for (int i = 0; i < controls.size() - 1; i++) {
			for (int j = i + 1; j < controls.size(); j++) {
				final boolean isSame = importDataComparator()
						.compare(controls.get(i), controls.get(j)) > 0;
				if (isSame) {
					return isSame;
				}
			}
		}
		return false;
	}

	private Comparator<ImportModelControl> importDataComparator() {
		return (first, second) -> {
			final List<String> firstSubworkSelections = first.getComboField()
					.getSubworkCombo().getSelectValuesString();
			final List<String> secondSubworkSelections = second.getComboField()
					.getSubworkCombo().getSelectValuesString();
			// Single state plan willn't considered
			if (firstSubworkSelections.stream().filter(
					subwork -> !serviceProvider.messages.PlanproImportPart_Subwork_Notset
							.equals(subwork))
					.noneMatch(secondSubworkSelections::contains)) {
				return 0;
			}

			final SelectionCombo<ContainerComboSelection> firstContainerCombo = first
					.getComboField().getContainerCombo();
			final SelectionCombo<ContainerComboSelection> secondContainerCombo = second
					.getComboField().getContainerCombo();

			if (firstContainerCombo.isDisposed()
					|| secondContainerCombo.isDisposed()) {
				return 1;
			}
			return firstContainerCombo.getSelectionValue()
					.equals(secondContainerCombo.getSelectionValue()) ? 1 : 0;
		};
	}

	@Override
	protected SelectableAction getOutdatedAction() {
		return new RefreshAction(this, e -> {
			modelInformationGroup.updateInfoGroup();
			setOutdated(false);
		});
	}

	@Override
	protected void handleContainerDataChanged(final ContainerDataChanged e) {
		setOutdated(true);
	}

	@Override
	protected void updateViewContainerDataChanged(
			final List<Container_AttributeGroup> container) {
		if (isOutdated()) {
			modelInformationGroup.updateInfoGroup();
			setOutdated(false);
		}
	}
}
