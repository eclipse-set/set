/**
 * Copyright (c) 2017 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.projectdata.ppimport;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.set.basis.Pair;
import org.eclipse.set.basis.constants.Events;
import org.eclipse.set.basis.exceptions.UserAbortion;
import org.eclipse.set.basis.files.ToolboxFileRole;
import org.eclipse.set.feature.projectdata.ppimport.ImportControl.ImportTarget;
import org.eclipse.set.feature.projectdata.utils.ServiceProvider;
import org.eclipse.set.ppmodel.extensions.PlanProSchnittstelleExtensions;
import org.eclipse.set.ppmodel.extensions.PlanungEinzelExtensions;
import org.eclipse.set.ppmodel.extensions.PlanungGruppeExtensions;
import org.eclipse.set.toolboxmodel.PlanPro.Ausgabe_Fachdaten;
import org.eclipse.set.toolboxmodel.PlanPro.Container_AttributeGroup;
import org.eclipse.set.toolboxmodel.PlanPro.ENUMUntergewerkArt;
import org.eclipse.set.toolboxmodel.PlanPro.LST_Zustand;
import org.eclipse.set.toolboxmodel.PlanPro.PlanProFactory;
import org.eclipse.set.toolboxmodel.PlanPro.PlanProPackage;
import org.eclipse.set.toolboxmodel.PlanPro.PlanPro_Schnittstelle;
import org.eclipse.set.toolboxmodel.PlanPro.Planung_Gruppe;
import org.eclipse.set.toolboxmodel.PlanPro.Untergewerk_Art_TypeClass;
import org.eclipse.set.utils.BasePart;
import org.eclipse.set.utils.RefreshAction;
import org.eclipse.set.utils.SelectableAction;
import org.eclipse.set.utils.events.ContainerDataChanged;
import org.eclipse.set.utils.events.EditingCompleted;
import org.eclipse.set.utils.events.ToolboxEvents;
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
				ImportTarget.MODEL);
		importInitial = new ImportControl(serviceProvider, getModelSession(),
				ImportTarget.INITIAL);
		importFinal = new ImportControl(serviceProvider, getModelSession(),
				ImportTarget.FINAL);

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
				ToolboxFileRole.SECONDARY_PLANNING,
				comboField -> Boolean.valueOf(!comboField.isEnabled()
						|| !comboField.getText().getText().isEmpty()
								&& !comboField.getSubworkCombo()
										.getSelectionValue().getName()
										.equals(SubworkComboSelection.NOT_SELECTED_SUBWORK)),
				this::updateImportButton);
		importModel.comboField.getContainerCombo().dispose();

		final Function<ImportComboFileField, Boolean> checkComboValidFunc = comboField -> Boolean
				.valueOf(!comboField.isEnabled() || !comboField.getText()
						.getText().isEmpty()
						&& !comboField.getSubworkCombo().getSelectionValue()
								.getName()
								.equals(SubworkComboSelection.NOT_SELECTED_SUBWORK)
						&& !comboField.getContainerCombo().getSelectionValue()
								.equals(ContainerComboSelection.NOT_SELECTED));

		importInitial.createControl(group,
				serviceProvider.messages.PlanProImportPart_importStart, shell,
				ToolboxFileRole.IMPORT_INITIAL_STATE, checkComboValidFunc,
				this::updateImportButton);

		importFinal.createControl(group,
				serviceProvider.messages.PlanProImportPart_importZiel, shell,
				ToolboxFileRole.IMPORT_FINAL_STATE, checkComboValidFunc,
				this::updateImportButton);
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
		boolean isSomethingImported = false;
		try {
			final List<ImportControl> of = List.of(importModel, importInitial,
					importFinal);
			for (final ImportControl control : of) {
				final boolean imported = importModel(control, shell);
				isSomethingImported = imported || isSomethingImported;
			}

			if (isSomethingImported) {
				// IMPROVE: Here should we the invalid id references again
				// resolve after we emf model modification
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

	private boolean importModel(final ImportControl importControl,
			final Shell shell) {
		if (!importControl.isEnabled()) {
			return false;
		}

		Function<Ausgabe_Fachdaten, LST_Zustand> getContainerFunc = null;
		String dialogMessage = ""; //$NON-NLS-1$
		switch (importControl.getImportTarget()) {
		case INITIAL:
			getContainerFunc = Ausgabe_Fachdaten::getLSTZustandStart;
			dialogMessage = serviceProvider.messages.ContainerValues_Start;
			break;
		case FINAL:
			getContainerFunc = Ausgabe_Fachdaten::getLSTZustandZiel;
			dialogMessage = serviceProvider.messages.ContainerValues_Ziel;
			break;
		default:
			break;
		}

		// When data is already import then skip import from this control
		if (importModel.isEnabled()
				&& importControl.getImportTarget() != ImportTarget.MODEL
				&& importModel.isSameImportData(importControl)) {
			getDialogService().openInformation(shell, getViewTitle(),
					String.format(
							serviceProvider.messages.PlanProImportPart_ImportSameData,
							dialogMessage));
			return false;
		}

		final boolean result = importModel(importControl, getContainerFunc,
				shell);
		ModelImportUtils.updateForImport(
				getModelSession().getPlanProSchnittstelle(),
				getModelSession().getEditingDomain(), importControl);
		return result;
	}

	private boolean importModel(final ImportControl importControl,
			final Function<Ausgabe_Fachdaten, LST_Zustand> getContainerFunc,
			final Shell shell)
			throws NullPointerException, IllegalArgumentException {
		final PlanPro_Schnittstelle planProSchnittstelle = getModelSession()
				.getPlanProSchnittstelle();
		final Optional<Iterable<Planung_Gruppe>> lstPlanungGruppe = PlanProSchnittstelleExtensions
				.getLSTPlanungGruppe(planProSchnittstelle);
		if (lstPlanungGruppe.isEmpty()) {
			return false;
		}
		final String selectedSubwork = getSelectedSubwork(importControl);
		if (selectedSubwork == null || selectedSubwork.isBlank()) {
			logger.error("Missing subwork to import"); //$NON-NLS-1$
			return false;
		}
		final Optional<Planung_Gruppe> sourcePlanungGruppe = PlanungGruppeExtensions
				.getPlanungGruppe(planProSchnittstelle, selectedSubwork);

		final Pair<Planung_Gruppe, Ausgabe_Fachdaten> selectedData = importControl
				.getSelectedData();

		// Fall current model doen't contains this subwork type
		if (sourcePlanungGruppe.isEmpty()) {
			return ModelImportUtils.doImportSubworkCommands(getModelSession(),
					importControl, null);
		}
		// Fall current project already contains selected sub work
		return replaceData(importControl, getContainerFunc, shell,
				sourcePlanungGruppe.get(), selectedData);
	}

	private boolean replaceData(final ImportControl importControl,
			final Function<Ausgabe_Fachdaten, LST_Zustand> getContainerFunc,
			final Shell shell, final Planung_Gruppe sourcePlanungGruppe,
			final Pair<Planung_Gruppe, Ausgabe_Fachdaten> selectedData) {
		final Ausgabe_Fachdaten sourceAusgabeFachdaten = PlanungEinzelExtensions
				.getAusgabeFachdaten(sourcePlanungGruppe.getLSTPlanungEinzel());
		final LST_Zustand sourceZustand = getContainerFunc == null ? null
				: getContainerFunc.apply(sourceAusgabeFachdaten);
		// Fall replace whole subwork
		if (sourceZustand == null) {
			if (getDialogService().confirmOverwriteOperationalData(shell)) {
				return ModelImportUtils.doImportSubworkCommands(
						getModelSession(), importControl, new Pair<>(
								sourcePlanungGruppe, sourceAusgabeFachdaten));
			}
			return false;
		}
		if (!sourceZustand.getContainer().eContents().isEmpty()
				&& !getDialogService().confirmOverwriteOperationalData(shell)) {
			return false;
		}

		// By create new project only in Planung_Gruppe set subwork type,
		// and it is missing in Ausgabe_Fachdaten
		if (sourceAusgabeFachdaten.getUntergewerkArt() == null) {
			sourceAusgabeFachdaten.setUntergewerkArt(EcoreUtil
					.copy(selectedData.getSecond().getUntergewerkArt()));
		}
		return ModelImportUtils.doImportContainerCommand(getModelSession(),
				sourceZustand, importControl, importControl.comboField
						.getContainerCombo().getSelectionValue());

	}

	private String getSelectedSubwork(final ImportControl importControl) {
		String selectedSubwork = importControl.comboField.getSubworkCombo()
				.getSelectionValue().getLiteral();
		final Pair<Planung_Gruppe, Ausgabe_Fachdaten> selectedData = importControl
				.getSelectedData();
		// Fall missing sub work, then the user must choose one
		if (selectedSubwork == null || selectedSubwork.isEmpty()
				|| selectedSubwork.equals(
						serviceProvider.messages.PlanproImportPart_Subwork_Notset)) {
			final List<String> subworkType = Arrays
					.stream(ENUMUntergewerkArt.values())
					.map(e -> e.getLiteral()).toList();
			selectedSubwork = getDialogService().selectValueDialog(
					getToolboxShell(), getViewTitle(),
					serviceProvider.messages.PlanProImportPart_SelectSubworkMessage,
					serviceProvider.messages.PlanProImportPart_SelectSubworkLabel,
					subworkType);
			if (selectedSubwork == null || selectedSubwork.isEmpty()) {
				return null;
			}
			final ENUMUntergewerkArt enumUntergewerkArt = ENUMUntergewerkArt
					.get(selectedSubwork);

			// Set new subwork for import control
			final SubworkComboSelection subworkComboSelection = new SubworkComboSelection(
					0, enumUntergewerkArt.getName(),
					enumUntergewerkArt.getLiteral());
			importControl.comboField.getSubworkCombo()
					.setComboValues(SubworkComboSelection.getComboValues(
							serviceProvider.messages,
							List.of(subworkComboSelection)));
			importControl.comboField.getSubworkCombo().select(0);

			try {
				// Set new subwork type in Planung_Gruppe and Ausgabe_Fachdaten
				setSubworkType(selectedData.getFirst(), List.of(
						PlanProPackage.eINSTANCE
								.getPlanung_Gruppe_PlanungGAllg(),
						PlanProPackage.eINSTANCE
								.getPlanung_G_Allg_AttributeGroup_UntergewerkArt()),
						enumUntergewerkArt);
				setSubworkType(selectedData.getSecond(),
						List.of(PlanProPackage.eINSTANCE
								.getAusgabe_Fachdaten_UntergewerkArt()),
						enumUntergewerkArt);
			} catch (final NullPointerException e) {
				throw new RuntimeException();
			}

		}
		return selectedSubwork;
	}

	private static void setSubworkType(final EObject parent,
			final List<EReference> referencesToGetSubworkType,
			final ENUMUntergewerkArt type) {
		EObject containerObject = parent;
		for (int i = 0; i < referencesToGetSubworkType.size(); i++) {
			final EReference reference = referencesToGetSubworkType.get(i);
			final EClass eClass = reference.getEReferenceType();
			final Optional<EObject> object = Optional
					.ofNullable((EObject) containerObject.eGet(reference));
			if (object.isEmpty()) {
				final EObject eObject = PlanProFactory.eINSTANCE.create(eClass);
				containerObject.eSet(reference, eObject);
				containerObject = eObject;
			} else {
				containerObject = object.get();
			}

			if (i == referencesToGetSubworkType.size() - 1
					&& containerObject instanceof final Untergewerk_Art_TypeClass untergewerkArtTyeClass) {
				untergewerkArtTyeClass.setWert(type);
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
