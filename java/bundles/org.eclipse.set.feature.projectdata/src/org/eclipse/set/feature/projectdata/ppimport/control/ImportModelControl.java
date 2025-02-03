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

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.set.basis.IModelSession;
import org.eclipse.set.basis.constants.PlanProFileNature;
import org.eclipse.set.basis.files.ToolboxFile;
import org.eclipse.set.basis.files.ToolboxFileRole;
import org.eclipse.set.basis.guid.Guid;
import org.eclipse.set.core.services.modelloader.ModelLoader.ModelContents;
import org.eclipse.set.feature.projectdata.ppimport.ContainerComboSelection;
import org.eclipse.set.feature.projectdata.ppimport.ImportModelHandler;
import org.eclipse.set.feature.projectdata.ppimport.SubworkComboSelection;
import org.eclipse.set.feature.projectdata.utils.AbstractImportControl;
import org.eclipse.set.feature.projectdata.utils.ImportComboFileField;
import org.eclipse.set.feature.projectdata.utils.ServiceProvider;
import org.eclipse.set.model.planpro.PlanPro.PlanPro_Schnittstelle;
import org.eclipse.set.ppmodel.extensions.PlanProSchnittstelleExtensions;
import org.eclipse.set.utils.widgets.ComboValues;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

/**
 * Control for import data from another project
 * 
 * @author Truong
 */
public class ImportModelControl extends AbstractImportControl {
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

	private final ImportTarget importType;

	private ImportModelHandler importHandler;

	private final Runnable handleControlChange;

	/**
	 * @param serviceProvider
	 *            {@link ServiceProvider}
	 * @param modelSession
	 *            the session
	 * @param importType
	 *            {@link ImportTarget}
	 * @param handleControlChange
	 *            call back when control change
	 */
	public ImportModelControl(final ServiceProvider serviceProvider,
			final IModelSession modelSession, final ImportTarget importType,
			final Runnable handleControlChange) {
		super(serviceProvider, modelSession);
		this.importType = importType;
		this.setImported(false);
		this.attachmentSource = new HashMap<>();
		this.handleControlChange = handleControlChange;
	}

	/**
	 * @return true, if the control valis
	 */
	@Override
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
	 * Rest control to default
	 */
	@Override
	public void resetControl() {
		modelToImport = null;
		attachmentSource.clear();
		comboField.getText().setText(""); //$NON-NLS-1$
		comboField.setDefaultCombo();
		setImported(false);
	}

	/**
	 * @return true, if this control is enable
	 */
	@Override
	public boolean isEnabled() {
		return comboField.isEnabled();
	}

	/**
	 * Do import data
	 * 
	 * @param shell
	 *            the {@link Shell}
	 */
	@Override
	public void doImport(final Shell shell) {
		if (modelToImport == null || importHandler == null || !isEnabled()) {
			return;
		}
		setImported(
				importHandler.doImport(modelSession, attachmentSource, shell));
	}

	@Override
	public void createControl(final Composite parent, final Shell shell,
			final ToolboxFileRole role) {
		comboField = createImportFileFieldCombo(parent, shell, role);
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
			modelToImport = t.schnittStelle();
			importHandler = new ImportModelHandler(comboField, modelToImport,
					importType, serviceProvider);
		}, shell, role));
		fileFieldCombo.setDefaultCombo();
		fileFieldCombo.setEnabled(false);

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

	@Override
	protected Boolean validatePath(final ToolboxFile toolboxFile,
			final Consumer<ModelContents> storeModel, final Shell shell) {
		final Boolean isValid = super.validatePath(toolboxFile, storeModel,
				shell);
		if (toolboxFile.getFormat().isZippedPlanPro()) {
			try {
				toolboxFile.getAllMedia().stream().forEach(guid -> {
					try {
						attachmentSource.put(guid,
								toolboxFile.getMedia(Guid.create(guid)));
					} catch (final IOException e) {
						throw new RuntimeException(e);
					}
				});
			} catch (final IOException e) {
				throw new RuntimeException(e);
			}
		}
		return isValid;
	}

	@Override
	protected ModifyListener selectedFileHandle() {
		return e -> {
			if (e.getSource() instanceof final Text text
					&& text.getText().isBlank() || modelToImport == null) {
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
}
