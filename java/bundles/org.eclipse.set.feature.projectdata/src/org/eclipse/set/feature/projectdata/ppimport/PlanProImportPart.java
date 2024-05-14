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
import org.eclipse.set.feature.projectdata.ppimport.ImportControl.ImportTarget;
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

	private ImportControl importInitial;
	private ImportControl importFinal;
	private ImportControl importModel;

	/**
	 * 
	 */
	@Inject
	public PlanProImportPart() {
		super();
	}

	@Override
	protected void createView(final Composite parent) {
		importModel = new ImportControl(serviceProvider, getModelSession(),
				ImportTarget.ALL, this::updateImportButton);
		importInitial = new ImportControl(serviceProvider, getModelSession(),
				ImportTarget.INITIAL, this::updateImportButton);
		importFinal = new ImportControl(serviceProvider, getModelSession(),
				ImportTarget.FINAL, this::updateImportButton);

		if (PlanProSchnittstelleExtensions
				.isPlanning(getModelSession().getPlanProSchnittstelle())) {
			modelInformationGroup = new ModelInformationGroup(getModelSession(),
					serviceProvider.messages);
			modelInformationGroup.createInfoGroup(parent);
			createImportGroup(parent, getToolboxShell());
			createImportButton(parent);
		} else {
			modelInformationGroup.createNotSupportedInfo(parent);
		}
	}

	private void createImportGroup(final Composite parent, final Shell shell) {
		final Group group = new Group(parent, SWT.SHADOW_ETCHED_IN);
		group.setText(serviceProvider.messages.PlanProImportPart_importGroup);
		group.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false));
		group.setLayout(new GridLayout());

		importModel.createControl(group,
				serviceProvider.messages.PlanProImportPart_importSubwork, shell,
				ToolboxFileRole.SECONDARY_PLANNING);
		importModel.getComboField().getContainerCombo().dispose();

		importInitial.createControl(group,
				serviceProvider.messages.PlanProImportPart_importStart, shell,
				ToolboxFileRole.IMPORT_INITIAL_STATE);

		importFinal.createControl(group,
				serviceProvider.messages.PlanProImportPart_importZiel, shell,
				ToolboxFileRole.IMPORT_FINAL_STATE);
	}

	private void createImportButton(final Composite parent) {
		importButton = new Button(parent, SWT.NONE);
		importButton.setText(getViewTitle());
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
			final List<ImportControl> controlList = List.of(importModel,
					importInitial, importFinal);
			final List<ImportControl> enableControl = controlList.stream()
					.filter(c -> c.isEnabled()).toList();

			// The import data from each control should be different
			if (isExistSameImportData(enableControl)) {
				getDialogService().openInformation(shell, getViewTitle(),
						serviceProvider.messages.PlanProImportPart_ImportSameData);
				return;
			}
			controlList.forEach(control -> control.doImport(shell));
			final boolean isSomethingImported = controlList.stream()
					.anyMatch(ImportControl::isImported);
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
		final List<ImportControl> enableImport = Stream
				.of(importModel, importInitial, importFinal)
				.filter(i -> i.isEnabled()).toList();
		importButton.setEnabled(!enableImport.isEmpty()
				&& enableImport.stream().allMatch(i -> i.isValid()));
	}

	private static boolean isExistSameImportData(
			final List<ImportControl> controls) {
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

	private static Comparator<ImportControl> importDataComparator() {
		return (first, second) -> {
			final List<String> firstSubworkSelections = first.getComboField()
					.getSubworkCombo().getSelectValuesString();
			final List<String> secondSubworkSelections = second.getComboField()
					.getSubworkCombo().getSelectValuesString();
			if (firstSubworkSelections.stream()
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
