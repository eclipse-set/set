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

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.set.basis.IModelSession;
import org.eclipse.set.basis.constants.PlanProFileNature;
import org.eclipse.set.basis.constants.ValidationResult;
import org.eclipse.set.basis.constants.ValidationResult.FileValidateState;
import org.eclipse.set.basis.files.ToolboxFile;
import org.eclipse.set.basis.files.ToolboxFileAC;
import org.eclipse.set.basis.files.ToolboxFileRole;
import org.eclipse.set.basis.guid.Guid;
import org.eclipse.set.feature.projectdata.utils.ServiceProvider;
import org.eclipse.set.feature.validation.utils.ValidationOutcome;
import org.eclipse.set.model.planpro.PlanPro.PlanPro_Schnittstelle;
import org.eclipse.set.ppmodel.extensions.PlanProSchnittstelleExtensions;
import org.eclipse.set.utils.widgets.ComboValues;
import org.eclipse.set.utils.widgets.Option;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Shell;

/**
 * Control for import data from anothe project
 * 
 * @author Truong
 */
public class ImportControl {
	/**
	 * Target to import of the control
	 */
	public enum ImportTarget {
		/**
		 * Import whole sub work (include INITIAL and FINAL container)
		 */
		ALL,

		/**
		 * Import to current initial container of selected sub work
		 */
		INITIAL,
		/**
		 * Import to current final container of selected sub work
		 */
		FINAL
	}

	private PlanPro_Schnittstelle modelToImport;

	private final Map<String, byte[]> attachmentSource;
	private ImportComboFileField comboField;
	private Option option;
	private final ServiceProvider serviceProvider;

	private final IModelSession modelSession;
	private final ImportTarget importType;

	private boolean imported;

	private ImportHandler importHandler;
	private final Runnable handleControlChange;

	/**
	 * @param serviceProvider
	 *            {@link ServiceProvider}
	 * @param modelSession
	 *            the session
	 * @param importType
	 *            {@link ImportTarget}
	 * @param hanldeControlChange
	 *            call back when control change
	 */
	public ImportControl(final ServiceProvider serviceProvider,
			final IModelSession modelSession, final ImportTarget importType,
			final Runnable hanldeControlChange) {
		this.serviceProvider = serviceProvider;
		this.modelSession = modelSession;
		this.importType = importType;
		this.setImported(false);
		this.handleControlChange = hanldeControlChange;
		this.attachmentSource = new HashMap<>();
	}

	/**
	 * @return true, if the control valis
	 */
	public boolean isValid() {
		return comboField.isValid();
	}

	/**
	 * The import target
	 * 
	 * @return which data will be replace through import
	 */
	public ImportTarget getImportTarget() {
		return importType;
	}

	/**
	 * 
	 */
	public void resetControl() {
		comboField.getText().setText(""); //$NON-NLS-1$
		comboField.setDefaultCombo();
		option.getButton().setSelection(false);
		comboField.setEnabled(false);
		imported = false;
	}

	/**
	 * @return true, if this control is enable
	 */
	public boolean isEnabled() {
		return option.getButton().isEnabled() && comboField.isEnabled();
	}

	/**
	 * Do import data
	 * 
	 * @param shell
	 *            the {@link Shell}
	 */
	public void doImport(final Shell shell) {
		if (modelToImport == null || importHandler == null || !isEnabled()) {
			return;
		}
		imported = importHandler.doImport(modelSession, attachmentSource,
				shell);
	}

	/**
	 * @param parent
	 *            the parat
	 * @param text
	 *            the enable check box text
	 * @param shell
	 *            the shell
	 * @param role
	 *            the {@link ToolboxFileRole}
	 */
	public void createControl(final Group parent, final String text,
			final Shell shell, final ToolboxFileRole role) {
		option = createImportOption(parent, text);
		comboField = createImportFileFieldCombo(parent, shell, role);
	}

	private Option createImportOption(final Composite parent,
			final String label) {
		final Option checkbox = new Option(parent);
		checkbox.getLabel().setText(label);

		// toggle file field with option button
		checkbox.getButton().addSelectionListener(new SelectionListener() {

			@Override
			public void widgetDefaultSelected(final SelectionEvent e) {
				if (comboField == null) {
					return;
				}
				comboField.setEnabled(checkbox.getButton().getSelection());
				handleControlChange.run();
			}

			@Override
			public void widgetSelected(final SelectionEvent e) {
				widgetDefaultSelected(e);
			}
		});
		return checkbox;
	}

	private ImportComboFileField createImportFileFieldCombo(
			final Composite parent, final Shell shell,
			final ToolboxFileRole role) {
		final ImportComboFileField fileFieldCombo = new ImportComboFileField(
				parent, serviceProvider.dialogService.getModelFileFilters(),
				serviceProvider.dialogService, serviceProvider.messages);

		fileFieldCombo.getComposite()
				.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		fileFieldCombo.getButton().setText(
				serviceProvider.messages.PlanProImportPart_fileFieldButtonText);
		fileFieldCombo.setPathValidation(path -> validatePath(path, t -> {
			modelToImport = t;
			importHandler = new ImportHandler(comboField, modelToImport,
					importType, serviceProvider);
		}, shell, role));
		fileFieldCombo.setEnabled(option.getButton().getSelection());
		fileFieldCombo.setDefaultCombo();

		// Add listener for file field combo
		fileFieldCombo.getText().addModifyListener(selectedFileHandle());
		fileFieldCombo.getSubworkCombo()
				.addSelectionChangedListener(selectionSubworkHanlder());
		fileFieldCombo.getContainerCombo()
				.addSelectionListener(selectionContainerHandle());
		return fileFieldCombo;
	}

	/**
	 * @return the {@link ImportComboFileField}
	 */
	public ImportComboFileField getComboField() {
		return comboField;
	}

	private Boolean validatePath(final Path path,
			final Consumer<PlanPro_Schnittstelle> storeModel, final Shell shell,
			final ToolboxFileRole role) {
		try (ToolboxFileAC toolboxFile = serviceProvider.fileService
				.loadAC(path, role)) {
			toolboxFile.get().setTemporaryDirectory(modelSession.getTempDir());
			return validatePath(toolboxFile.get(), storeModel, shell);
		} catch (final IOException e) {
			throw new RuntimeException(e);
		}
	}

	private Boolean validatePath(final ToolboxFile toolboxFile,
			final Consumer<PlanPro_Schnittstelle> storeModel, final Shell shell)
			throws IOException {
		final List<ValidationResult> validationResults = new ArrayList<>();

		final PlanPro_Schnittstelle model = serviceProvider.modelLoader
				.loadModelSync(toolboxFile, validationResults::add, shell);
		storeModel.accept(model);
		if (toolboxFile.getFormat().isZippedPlanPro()) {
			toolboxFile.getAllMedia().stream().forEach(guid -> {
				try {
					attachmentSource.put(guid,
							toolboxFile.getMedia(Guid.create(guid)));
				} catch (final IOException e) {
					throw new RuntimeException(e);
				}
			});
		}

		final FileValidateState fileValidateState = ValidationOutcome
				.getFileValidateState(validationResults);
		switch (fileValidateState) {
		case VALID: {
			return Boolean.valueOf(true);
		}
		case INCOMPLETE: {
			serviceProvider.dialogService.openInformation(shell,
					serviceProvider.messages.PlanProImportPart_incompletePlanProFile,
					String.format(
							serviceProvider.messages.PlanProImportPart_incompletePlanProFileMessage,
							toolboxFile.getPath()));
			return Boolean.valueOf(true);
		}
		case INVALID: {
			// XSD-invalid file, unable to load
			return Boolean.valueOf(serviceProvider.dialogService
					.loadInvalidModel(shell, toolboxFile.getPath().toString()));
		}
		default:
			return Boolean.valueOf(false);
		}

	}

	private ModifyListener selectedFileHandle() {
		return e -> {
			if (modelToImport == null) {
				return;
			}

			// Set values for subword- and container- value after file loaded
			if (PlanProSchnittstelleExtensions.isPlanning(modelToImport)) {
				setComboValues(PlanProFileNature.PLANNING);
			} else {
				setComboValues(PlanProFileNature.INFORMATION_STATE);
			}
			handleControlChange.run();
		};
	}

	private ISelectionChangedListener selectionSubworkHanlder() {
		return event -> {
			if (!comboField.getContainerCombo().isDisposed()) {
				comboField.getContainerCombo().setEnabled(
						!comboField.isNotSelected(comboField.getSubworkCombo())
								&& comboField.getContainerCombo()
										.getItems().length > 1);
			} else {
				handleControlChange.run();
			}
		};
	}

	private SelectionListener selectionContainerHandle() {
		return new SelectionListener() {

			@Override
			public void widgetSelected(final SelectionEvent e) {
				widgetDefaultSelected(e);
			}

			@Override
			public void widgetDefaultSelected(final SelectionEvent e) {
				// Update isValid when choose container
				handleControlChange.run();
			}
		};
	}

	private void setComboValues(final PlanProFileNature fileNature) {
		final ComboValues<SubworkComboSelection> subworkValues = SubworkComboSelection
				.getComboValues(modelToImport, fileNature,
						serviceProvider.messages);
		comboField.setSubworkComboValues(subworkValues);

		final ComboValues<ContainerComboSelection> containerValues = ContainerComboSelection
				.getComboValues(fileNature, serviceProvider.messages);
		comboField.setContainerComboValues(containerValues);
	}

	/**
	 * @return true, if data is imported
	 */
	public boolean isImported() {
		return imported;
	}

	/**
	 * @param imported
	 *            set imported status
	 */
	public void setImported(final boolean imported) {
		this.imported = imported;
	}
}
