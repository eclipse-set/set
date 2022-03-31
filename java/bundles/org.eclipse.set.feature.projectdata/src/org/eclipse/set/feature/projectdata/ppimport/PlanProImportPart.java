/**
 * Copyright (c) 2017 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.projectdata.ppimport;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

import javax.inject.Inject;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.set.basis.IModelSession;
import org.eclipse.set.basis.constants.Events;
import org.eclipse.set.basis.constants.PlanProFileNature;
import org.eclipse.set.basis.exceptions.UserAbortion;
import org.eclipse.set.basis.files.ToolboxFileAC;
import org.eclipse.set.basis.files.ToolboxFileRole;
import org.eclipse.set.core.services.dialog.DialogService;
import org.eclipse.set.core.services.files.ToolboxFileService;
import org.eclipse.set.ppmodel.extensions.PlanProSchnittstelleExtensions;
import org.eclipse.set.ppmodel.extensions.PlanungEinzelExtensions;
import org.eclipse.set.ppmodel.extensions.PlanungProjektExtensions;
import org.eclipse.set.utils.RefreshAction;
import org.eclipse.set.utils.SelectableAction;
import org.eclipse.set.utils.events.ContainerDataChanged;
import org.eclipse.set.utils.events.EditingCompleted;
import org.eclipse.set.utils.events.ToolboxEvents;
import org.eclipse.set.utils.widgets.ComboValues;
import org.eclipse.set.utils.widgets.FileFieldCombo;
import org.eclipse.set.utils.widgets.Option;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Shell;

import de.scheidtbachmann.planpro.model.model1902.PlanPro.Container_AttributeGroup;
import de.scheidtbachmann.planpro.model.model1902.PlanPro.LST_Zustand;
import de.scheidtbachmann.planpro.model.model1902.PlanPro.PlanProPackage;
import de.scheidtbachmann.planpro.model.model1902.PlanPro.PlanPro_Schnittstelle;

/**
 * Import PlanPro models.
 * 
 * @author Schaefer
 */
public class PlanProImportPart extends ImportMergePart<IModelSession> {

	private static Container_AttributeGroup getContainer(
			final FileFieldCombo<ImportComboSelection> fileFieldCombo,
			final PlanPro_Schnittstelle model) {
		if (fileFieldCombo.getComboValue() == ImportComboSelection.START) {
			return PlanungEinzelExtensions
					.LSTZustandStart(PlanungProjektExtensions
							.getPlanungGruppe(PlanProSchnittstelleExtensions
									.LSTPlanungProjekt(model))
							.getLSTPlanungEinzel())
					.getContainer();
		}
		if (fileFieldCombo.getComboValue() == ImportComboSelection.ZIEL) {
			return PlanungEinzelExtensions
					.LSTZustandZiel(PlanungProjektExtensions
							.getPlanungGruppe(PlanProSchnittstelleExtensions
									.LSTPlanungProjekt(model))
							.getLSTPlanungEinzel())
					.getContainer();
		}
		if (fileFieldCombo
				.getComboValue() == ImportComboSelection.ZUSTAND_INFORMATION) {
			return model.getLSTZustand().getContainer();
		}
		throw new IllegalArgumentException(
				fileFieldCombo.getComboValue().toString());
	}

	private static boolean isValid(
			final FileFieldCombo<ImportComboSelection> fileField) {
		return !fileField.isEnabled()
				|| !fileField.getText().getText().isEmpty() && fileField
						.getComboValue() != ImportComboSelection.NOT_SELECTED;
	}

	@Inject
	private DialogService dialogService;

	@Inject
	private ToolboxFileService fileService;

	private Button importButton;

	private PlanPro_Schnittstelle modelStart;

	private PlanPro_Schnittstelle modelZiel;

	private FileFieldCombo<ImportComboSelection> startField;

	private Option startOption;

	private FileFieldCombo<ImportComboSelection> zielField;

	private Option zielOption;

	/**
	 * Create the part.
	 */
	@Inject
	public PlanProImportPart() {
		super(IModelSession.class);
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
				modelImport(parent.getShell());
			}
		});
	}

	private void createImportControls(final Group parent, final String text,
			final Consumer<PlanPro_Schnittstelle> storeModel,
			final Supplier<PlanPro_Schnittstelle> modelProvider,
			final Consumer<FileFieldCombo<ImportComboSelection>> setField,
			final Consumer<Option> setOption, final Shell shell,
			final ToolboxFileRole role) {
		// the option button
		final Option option = new Option(parent);
		option.getLabel().setText(text);
		setOption.accept(option);

		// the file field combo
		final FileFieldCombo<ImportComboSelection> fileFieldCombo = new FileFieldCombo<>(
				parent, dialogService.getModelFileFilters(), SWT.READ_ONLY,
				dialogService);
		fileFieldCombo.getComposite()
				.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		fileFieldCombo.getButton()
				.setText(messages.PlanProImportPart_fileFieldButtonText);
		fileFieldCombo.setPathValidation(
				path -> testPath(path, storeModel, shell, role));
		fileFieldCombo.setEnabled(option.getButton().getSelection());
		final ComboValues<ImportComboSelection> comboValues = ImportComboSelection
				.getComboValues(PlanProFileNature.INVALID, messages);
		fileFieldCombo.setComboValues(comboValues);
		fileFieldCombo.setComboEnabled(comboValues.getItems().length > 1);
		setField.accept(fileFieldCombo);

		// toggle file field with option button
		option.getButton().addSelectionListener(new SelectionListener() {

			@Override
			public void widgetDefaultSelected(final SelectionEvent e) {
				fileFieldCombo.setEnabled(option.getButton().getSelection());
				fileFieldCombo
						.setComboEnabled(option.getButton().getSelection());
				updateImportButton();
			}

			@Override
			public void widgetSelected(final SelectionEvent e) {
				fileFieldCombo.setEnabled(option.getButton().getSelection());
				fileFieldCombo
						.setComboEnabled(option.getButton().getSelection());
				updateImportButton();
			}
		});

		// connect file field with import button and combo
		fileFieldCombo.getText().addModifyListener(e -> {
			updateImportButton();
			updateCombo(fileFieldCombo, modelProvider);
		});
		fileFieldCombo.getCombo().addSelectionListener(new SelectionListener() {

			@Override
			public void widgetDefaultSelected(final SelectionEvent e) {
				updateImportButton();
			}

			@Override
			public void widgetSelected(final SelectionEvent e) {
				updateImportButton();
			}
		});
	}

	private void createImportGroup(final Composite parent, final Shell shell) {
		final Group group = new Group(parent, SWT.SHADOW_ETCHED_IN);
		group.setText(messages.PlanProImportPart_importGroup);
		group.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false));
		group.setLayout(new GridLayout());

		createImportControls(group, messages.PlanProImportPart_importStart,
				model -> modelStart = model, () -> modelStart,
				fileField -> startField = fileField,
				option -> startOption = option, shell,
				ToolboxFileRole.IMPORT_INITIAL_STATE);
		createImportControls(group, messages.PlanProImportPart_importZiel,
				model -> modelZiel = model, () -> modelZiel,
				fileField -> zielField = fileField,
				option -> zielOption = option, shell,
				ToolboxFileRole.IMPORT_FINAL_STATE);
	}

	private boolean isImportable() {
		return isValid(startField) && isValid(zielField)
				&& (startField.isEnabled() || zielField.isEnabled());
	}

	private boolean modelImport(final LST_Zustand zustand,
			final Container_AttributeGroup container, final Shell parent) {
		if (zustand.getContainer().eContents().size() > 0) {
			if (!getDialogService().confirmOverwriteOperationalData(parent)) {
				return false;
			}
		}
		final Container_AttributeGroup containerCopy = EcoreUtil
				.copy(container);
		final EditingDomain editingDomain = session.getEditingDomain();
		final Command command = SetCommand.create(editingDomain, zustand,
				PlanProPackage.eINSTANCE.getLST_Zustand_Container(),
				containerCopy);
		editingDomain.getCommandStack().execute(command);
		return true;
	}

	private void resetImportGroup() {
		modelStart = null;
		modelZiel = null;
		startField.getText().setText(""); //$NON-NLS-1$
		zielField.getText().setText(""); //$NON-NLS-1$
		startField.setEnabled(false);
		zielField.setEnabled(false);
		startField.setComboValues(ImportComboSelection
				.getComboValues(PlanProFileNature.INVALID, messages));
		zielField.setComboValues(ImportComboSelection
				.getComboValues(PlanProFileNature.INVALID, messages));
		startField.getCombo().setEnabled(false);
		zielField.getCombo().setEnabled(false);
		startOption.getButton().setSelection(false);
		zielOption.getButton().setSelection(false);
		updateImportButton();
	}

	private Boolean testPath(final Path path,
			final Consumer<PlanPro_Schnittstelle> storeModel, final Shell shell,
			final ToolboxFileRole role) {
		try (ToolboxFileAC toolboxFile = fileService.loadAC(path, role)) {
			toolboxFile.get()
					.setTemporaryDirectory(getModelSession().getTempDir());
			return testPath(toolboxFile.get(), storeModel, shell);
		} catch (final IOException e) {
			throw new RuntimeException(e);
		}
	}

	private void updateCombo(
			final FileFieldCombo<ImportComboSelection> fileFieldCombo,
			final Supplier<PlanPro_Schnittstelle> modelProvider) {
		final PlanPro_Schnittstelle schnittstelle = modelProvider.get();
		if (schnittstelle == null) {
			return;
		}
		if (PlanProSchnittstelleExtensions.isPlanning(schnittstelle)) {
			final ComboValues<ImportComboSelection> comboValues = ImportComboSelection
					.getComboValues(PlanProFileNature.PLANNING, messages);
			fileFieldCombo.setComboValues(comboValues);
			fileFieldCombo.setComboEnabled(comboValues.getItems().length > 1);
		} else {
			final ComboValues<ImportComboSelection> comboValues = ImportComboSelection
					.getComboValues(PlanProFileNature.INFORMATION_STATE,
							messages);
			fileFieldCombo.setComboValues(comboValues);
			fileFieldCombo.setComboEnabled(comboValues.getItems().length > 1);
		}
	}

	@Override
	protected void createView(final Composite parent) {
		if (isPlanning()) {
			createInfoGroup(parent);
			createImportGroup(parent, getToolboxShell());
			createImportButton(parent);
		} else {
			createNotSupportedInfo(parent);
		}
	}

	@Override
	protected SelectableAction getOutdatedAction() {
		return new RefreshAction(this, e -> {
			updateInfoGroup();
			setOutdated(false);
		});
	}

	@Override
	protected void handleContainerDataChanged(final ContainerDataChanged e) {
		setOutdated(true);
	}

	protected void modelImport(final Shell shell) {
		final PlanPro_Schnittstelle sessionModel = session
				.getPlanProSchnittstelle();
		boolean imported = false;
		if (startField.isEnabled()) {
			final boolean result = modelImport(PlanungEinzelExtensions
					.LSTZustandStart(PlanungProjektExtensions
							.getPlanungGruppe(PlanProSchnittstelleExtensions
									.LSTPlanungProjekt(sessionModel))
							.getLSTPlanungEinzel()),
					getContainer(startField, modelStart), shell);
			imported = imported || result;
		}
		if (zielField.isEnabled()) {
			final boolean result = modelImport(PlanungEinzelExtensions
					.LSTZustandZiel(PlanungProjektExtensions
							.getPlanungGruppe(PlanProSchnittstelleExtensions
									.LSTPlanungProjekt(sessionModel))
							.getLSTPlanungEinzel()),
					getContainer(zielField, modelZiel), shell);
			imported = imported || result;
		}
		if (imported) {
			try {
				PlanProSchnittstelleExtensions.updateForImport(
						session.getPlanProSchnittstelle(),
						session.getEditingDomain(), startField.isEnabled(),
						zielField.isEnabled());
				session.save(shell);

				getDialogService().reportImported(shell);

				resetImportGroup();

				ToolboxEvents.send(getBroker(), new EditingCompleted());

				getBroker().send(Events.MODEL_CHANGED,
						session.getPlanProSchnittstelle());
			} catch (final UserAbortion e) {
				// We ignore an user abortion
			} finally {
				// if the session is dirty now, saving failed and we revert any
				// changes from the import
				if (session.isDirty()) {
					session.revert();
				}
			}
		}
	}

	@Override
	protected void updateViewContainerDataChanged(
			final List<Container_AttributeGroup> container) {
		if (isOutdated()) {
			updateInfoGroup();
			setOutdated(false);
		}
	}

	void updateImportButton() {
		importButton.setEnabled(isImportable());
	}
}
