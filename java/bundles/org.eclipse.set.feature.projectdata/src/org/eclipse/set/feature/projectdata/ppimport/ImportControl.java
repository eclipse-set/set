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
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

import org.eclipse.set.basis.IModelSession;
import org.eclipse.set.basis.Pair;
import org.eclipse.set.basis.constants.PlanProFileNature;
import org.eclipse.set.basis.constants.ValidationResult;
import org.eclipse.set.basis.constants.ValidationResult.FileValidateState;
import org.eclipse.set.basis.files.ToolboxFile;
import org.eclipse.set.basis.files.ToolboxFileAC;
import org.eclipse.set.basis.files.ToolboxFileRole;
import org.eclipse.set.feature.projectdata.utils.ServiceProvider;
import org.eclipse.set.feature.validation.utils.ValidationOutcome;
import org.eclipse.set.ppmodel.extensions.PlanProSchnittstelleExtensions;
import org.eclipse.set.ppmodel.extensions.PlanungEinzelExtensions;
import org.eclipse.set.ppmodel.extensions.PlanungGruppeExtensions;
import org.eclipse.set.toolboxmodel.PlanPro.Ausgabe_Fachdaten;
import org.eclipse.set.toolboxmodel.PlanPro.PlanPro_Schnittstelle;
import org.eclipse.set.toolboxmodel.PlanPro.Planung_Einzel;
import org.eclipse.set.toolboxmodel.PlanPro.Planung_Gruppe;
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
		MODEL,

		/**
		 * Import to current initial container of selected sub work
		 */
		INITIAL,
		/**
		 * Import to current final container of selected sub work
		 */
		FINAL
	}

	PlanPro_Schnittstelle schnittstelle;
	ImportComboFileField comboField;

	private Function<ImportComboFileField, Boolean> comboValidCheck;
	private boolean isValid = false;
	Option option;
	private final ServiceProvider serviceProvider;

	private final IModelSession modelSession;
	private final ImportTarget importType;

	private Pair<Planung_Gruppe, Ausgabe_Fachdaten> selectedData = null;

	/**
	 * @param serviceProvider
	 *            {@link ServiceProvider}
	 * @param modelSession
	 *            the session
	 * @param importType
	 *            {@link ImportTarget}
	 */
	public ImportControl(final ServiceProvider serviceProvider,
			final IModelSession modelSession, final ImportTarget importType) {
		this.serviceProvider = serviceProvider;
		this.modelSession = modelSession;
		this.importType = importType;
	}

	/**
	 * @return true, if the control valis
	 */
	public boolean isValid() {
		return isValid;
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
		comboField.setDefaultCombo(serviceProvider.messages);
		option.getButton().setSelection(false);
		comboField.setEnabled(false);
	}

	/**
	 * @return true, if this control is enable
	 */
	public boolean isEnabled() {
		return option.getButton().isEnabled() && comboField.isEnabled();
	}

	/**
	 * Get {@link Planung_Gruppe} and {@link Ausgabe_Fachdaten} from import
	 * model with selected subwork
	 * 
	 * @return Pair<Planung_Gruppe, Ausgabe_Fachdaten>
	 */
	public Pair<Planung_Gruppe, Ausgabe_Fachdaten> getSelectedData() {
		if (selectedData != null) {
			return selectedData;
		}
		final SubworkComboSelection subworkValue = comboField.getSubworkCombo()
				.getSelectionValue();
		if (subworkValue.getLiteral()
				.equals(serviceProvider.messages.ContainerValues_NotSelected)
				|| schnittstelle == null) {
			return null;
		}

		// Fall import single state model
		if (!PlanProSchnittstelleExtensions.isPlanning(schnittstelle)) {
			final PlanPro_Schnittstelle newSchnitStelle = PlanProSchnittstelleExtensions
					.transformSingleState(schnittstelle);
			final Ausgabe_Fachdaten ausgabeFachdaten = newSchnitStelle
					.getLSTPlanung().getFachdaten().getAusgabeFachdaten()
					.get(0);
			final Planung_Gruppe planungGruppe = newSchnitStelle.getLSTPlanung()
					.getObjektmanagement().getLSTPlanungProjekt().get(0)
					.getLSTPlanungGruppe().get(0);
			selectedData = new Pair<>(planungGruppe, ausgabeFachdaten);

			// Replace singe state model with normal model
			schnittstelle = newSchnitStelle;
			return selectedData;
		}

		final Optional<Planung_Gruppe> planungGruppe = PlanungGruppeExtensions
				.getPlanungGruppe(schnittstelle, subworkValue.getLiteral());
		if (planungGruppe.isEmpty()) {
			throw new IllegalArgumentException(
					String.format("The model not contain sub work type: %s", //$NON-NLS-1$
							subworkValue.getLiteral()));
		}
		final Planung_Einzel planungEinzel = planungGruppe.get()
				.getLSTPlanungEinzel();

		selectedData = new Pair<>(planungGruppe.get(),
				PlanungEinzelExtensions.getAusgabeFachdaten(planungEinzel));
		return selectedData;

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
	 * @param validCheck
	 *            the function to check if the control valid
	 * @param comboUpdateHandle
	 *            the update function after selection of combo
	 */
	public void createControl(final Group parent, final String text,
			final Shell shell, final ToolboxFileRole role,
			final Function<ImportComboFileField, Boolean> validCheck,
			final Runnable comboUpdateHandle) {
		option = createImportOption(parent, text, comboUpdateHandle);
		comboField = createImportFileFieldCombo(parent, shell, role,
				comboUpdateHandle);
		comboValidCheck = validCheck;
	}

	/**
	 * Check if the second control contain same import data like this control
	 * 
	 * @param anotherImportControl
	 *            the second import control
	 * @return true, if two control contain same data
	 */
	public boolean isSameImportData(final ImportControl anotherImportControl) {

		if (this == anotherImportControl) {
			throw new IllegalArgumentException();
		}

		// Not considered import to initial or final
		if (importType != ImportTarget.MODEL && anotherImportControl
				.getImportTarget() != ImportTarget.MODEL) {
			return false;
		}
		if (!schnittstelle.getIdentitaet().getWert().equals(
				anotherImportControl.schnittstelle.getIdentitaet().getWert())) {
			return false;
		}

		if (!comboField.getSubworkCombo().getSelectionValue()
				.equals(anotherImportControl.comboField.getSubworkCombo()
						.getSelectionValue())) {
			return false;
		}

		return comboField.getContainerCombo().isDisposed()
				|| anotherImportControl.comboField.getContainerCombo()
						.isDisposed()
				|| comboField.getContainerCombo()
						.getSelectionValue() == anotherImportControl.comboField
								.getContainerCombo().getSelectionValue();
	}

	private Option createImportOption(final Composite parent,
			final String label, final Runnable comboUpdateHandle) {
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
				isValid = comboValidCheck.apply(comboField).booleanValue();
				comboUpdateHandle.run();
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
			final ToolboxFileRole role, final Runnable comboUpdateHandle) {
		final ImportComboFileField fileFieldCombo = new ImportComboFileField(
				parent, serviceProvider.dialogService.getModelFileFilters(),
				serviceProvider.dialogService);

		fileFieldCombo.getComposite()
				.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		fileFieldCombo.getButton().setText(
				serviceProvider.messages.PlanProImportPart_fileFieldButtonText);
		fileFieldCombo.setPathValidation(path -> validatePath(path,
				t -> schnittstelle = t, shell, role));
		fileFieldCombo.setEnabled(option.getButton().getSelection());
		fileFieldCombo.setDefaultCombo(serviceProvider.messages);

		// Add listener for file field combo
		fileFieldCombo.getText()
				.addModifyListener(selectedFileHandle(comboUpdateHandle));
		fileFieldCombo.getSubworkCombo().addSelectionListener(
				selectionSubworkHandle(comboUpdateHandle));
		fileFieldCombo.getContainerCombo().addSelectionListener(
				selectionContainerHandle(comboUpdateHandle));
		return fileFieldCombo;
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
			final Consumer<PlanPro_Schnittstelle> storeModel,
			final Shell shell) {
		final List<ValidationResult> validationResults = new ArrayList<>();

		final PlanPro_Schnittstelle model = serviceProvider.modelLoader
				.loadModelSync(toolboxFile, validationResults::add, shell);
		storeModel.accept(model);
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

	private ModifyListener selectedFileHandle(
			final Runnable comboUpdateHandle) {
		return e -> {
			if (schnittstelle == null) {
				return;
			}

			// Set values for subword- and container- value after file loaded
			if (PlanProSchnittstelleExtensions.isPlanning(schnittstelle)) {
				setComboValues(PlanProFileNature.PLANNING);
			} else {
				setComboValues(PlanProFileNature.INFORMATION_STATE);
				// By single state project given't option for choose subwork or
				// container
				isValid = comboValidCheck.apply(comboField).booleanValue();
				comboUpdateHandle.run();
			}
		};
	}

	private SelectionListener selectionSubworkHandle(
			final Runnable comboUpdateHandle) {
		return new SelectionListener() {

			@Override
			public void widgetSelected(final SelectionEvent e) {
				widgetDefaultSelected(e);
			}

			@Override
			public void widgetDefaultSelected(final SelectionEvent e) {
				// Only active choose container combo, when subwork combo was
				// selected
				if (!comboField.getContainerCombo().isDisposed()) {
					comboField.getContainerCombo()
							.setEnabled(!comboField
									.isNotSelected(comboField.getSubworkCombo())
									&& comboField.getContainerCombo()
											.getItems().length > 1);
				} else {
					isValid = comboValidCheck.apply(comboField).booleanValue();
					comboUpdateHandle.run();
				}

			}
		};
	}

	private SelectionListener selectionContainerHandle(
			final Runnable comboUpdateHandle) {
		return new SelectionListener() {

			@Override
			public void widgetSelected(final SelectionEvent e) {
				widgetDefaultSelected(e);
			}

			@Override
			public void widgetDefaultSelected(final SelectionEvent e) {
				// Update isValid when choose container
				isValid = comboValidCheck.apply(comboField).booleanValue();
				comboUpdateHandle.run();
			}
		};
	}

	private void setComboValues(final PlanProFileNature fileNature) {
		final ComboValues<SubworkComboSelection> subworkValues = SubworkComboSelection
				.getComboValues(schnittstelle, fileNature,
						serviceProvider.messages);
		comboField.getSubworkCombo().setComboValues(subworkValues);
		comboField.getSubworkCombo()
				.setEnabled(subworkValues.getItems().length > 1);

		final ComboValues<ContainerComboSelection> containerValues = ContainerComboSelection
				.getComboValues(fileNature, serviceProvider.messages);
		if (!comboField.getContainerCombo().isDisposed()) {
			comboField.getContainerCombo().setComboValues(containerValues);

			comboField.getContainerCombo().setEnabled(
					containerValues.getItems().length > 1 && !comboField
							.isNotSelected(comboField.getSubworkCombo()));
		}
	}
}
