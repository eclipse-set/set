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

import static org.eclipse.set.ppmodel.extensions.AusgabeFachdatenExtensions.getAusgabeFachdaten;
import static org.eclipse.set.ppmodel.extensions.PlanProSchnittstelleExtensions.*;
import static org.eclipse.set.ppmodel.extensions.PlanungEinzelExtensions.getAusgabeFachdaten;
import static org.eclipse.set.ppmodel.extensions.PlanungGruppeExtensions.getPlanungGruppe;
import static org.eclipse.set.ppmodel.extensions.PlanungGruppeExtensions.getUntergewerkArt;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.ReplaceCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.set.basis.IModelSession;
import org.eclipse.set.basis.Pair;
import org.eclipse.set.basis.files.ToolboxFile;
import org.eclipse.set.basis.guid.Guid;
import org.eclipse.set.core.fileservice.ToolboxIDResolver;
import org.eclipse.set.feature.projectdata.ppimport.control.ImportModelControl.ImportTarget;
import org.eclipse.set.feature.projectdata.utils.ImportComboFileField;
import org.eclipse.set.feature.projectdata.utils.ServiceProvider;
import org.eclipse.set.model.planpro.Basisobjekte.Anhang;
import org.eclipse.set.model.planpro.Basisobjekte.BasisobjekteFactory;
import org.eclipse.set.model.planpro.Basisobjekte.BasisobjektePackage;
import org.eclipse.set.model.planpro.Basisobjekte.Ur_Objekt;
import org.eclipse.set.model.planpro.PlanPro.Ausgabe_Fachdaten;
import org.eclipse.set.model.planpro.PlanPro.ENUMUntergewerkArt;
import org.eclipse.set.model.planpro.PlanPro.LST_Zustand;
import org.eclipse.set.model.planpro.PlanPro.PlanProFactory;
import org.eclipse.set.model.planpro.PlanPro.PlanProPackage;
import org.eclipse.set.model.planpro.PlanPro.PlanPro_Schnittstelle;
import org.eclipse.set.model.planpro.PlanPro.Planung_Einzel;
import org.eclipse.set.model.planpro.PlanPro.Planung_Gruppe;
import org.eclipse.set.model.planpro.PlanPro.Planung_Projekt;
import org.eclipse.set.model.planpro.PlanPro.Untergewerk_Art_TypeClass;
import org.eclipse.set.ppmodel.extensions.PlanungEinzelExtensions;
import org.eclipse.set.ppmodel.extensions.utils.IterableExtensions;
import org.eclipse.set.utils.widgets.MultiSelectionCombo;
import org.eclipse.set.utils.widgets.SelectionCombo;
import org.eclipse.swt.widgets.Shell;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;

/**
 *
 * 
 */
public class ImportModelHandler {
	static final Logger logger = LoggerFactory
			.getLogger(ImportModelHandler.class);
	private final ImportComboFileField comboField;
	private final PlanPro_Schnittstelle modelToImport;
	private final ImportTarget target;
	private final MultiSelectionCombo<SubworkComboSelection> subworkCombo;
	private final SelectionCombo<ContainerComboSelection> containerCombo;
	private final ServiceProvider serviceProvider;

	/**
	 * @param comboField
	 *            the {@link ImportComboFileField}
	 * @param modelToImport
	 *            the import data source
	 * @param target
	 *            the import target
	 * @param serviceProvider
	 *            the {@link ServiceProvider}
	 */
	public ImportModelHandler(final ImportComboFileField comboField,
			final PlanPro_Schnittstelle modelToImport,
			final ImportTarget target, final ServiceProvider serviceProvider) {
		this.comboField = comboField;
		this.modelToImport = modelToImport;
		this.target = target;
		this.subworkCombo = comboField.getSubworkCombo();
		this.containerCombo = comboField.getContainerCombo();
		this.serviceProvider = serviceProvider;
	}

	/**
	 * Do import data
	 * 
	 * @param modelSession
	 *            the {@link IModelSession}
	 * @param mediaToImport
	 *            the source of import attachment
	 * @param shell
	 *            the {@link Shell}
	 * @return true, if import successful
	 */
	public boolean doImport(final IModelSession modelSession,
			final Map<String, byte[]> mediaToImport, final Shell shell) {
		if (modelToImport == null) {
			return false;
		}

		final PlanPro_Schnittstelle source = modelSession
				.getPlanProSchnittstelle();
		final EditingDomain editingDomain = modelSession.getEditingDomain();
		final Optional<Iterable<Planung_Gruppe>> sourcePlanungGruppes = getLSTPlanungGruppe(
				source);
		if (sourcePlanungGruppes.isEmpty()) {
			return false;
		}

		final List<Pair<Planung_Gruppe, Ausgabe_Fachdaten>> importDatas = new ArrayList<>();
		// Fall import single state model
		if (!isPlanning(modelToImport)) {
			importDatas.add(getImportData(shell));
		} else {
			importDatas.addAll(getImportDatas());
		}

		return importDatas.stream().filter(data -> {
			final ENUMUntergewerkArt subworkType = data.getSecond()
					.getUntergewerkArt()
					.getWert();
			final Optional<Planung_Gruppe> sourceGruppe = getPlanungGruppe(
					source, subworkType);
			final Optional<Ausgabe_Fachdaten> sourceSubwork = sourceGruppe
					.isEmpty() ? getAusgabeFachdaten(source, subworkType)
							: getAusgabeFachdaten(source, sourceGruppe);
			final Optional<LST_Zustand> sourceContainer = getSourceContainer(
					sourceSubwork);
			// When import source already exist, then ask the user to overwrite
			if ((sourceGruppe.isPresent() || sourceSubwork.isPresent()
					|| sourceContainer.isPresent() && !sourceContainer.get()
							.getContainer()
							.eContents()
							.isEmpty())
					&& !serviceProvider.dialogService
							.confirmOverwriteOperationalData(shell)) {
				return false;

			}
			importPlaningGroup(source, sourceGruppe,
					EcoreUtil.copy(data.getFirst()), editingDomain);
			importSubwork(source, sourceSubwork, sourceContainer,
					EcoreUtil.copy(data.getSecond()), editingDomain);
			if (modelSession.getToolboxFile().getFormat().isZippedPlanPro()) {
				importAttachment(modelSession.getToolboxFile(),
						getImportAttachments(data), mediaToImport);
			}
			updateGuidAfterImport(source, data, editingDomain);
			return true;
		}).count() > 0;
	}

	private void importPlaningGroup(final PlanPro_Schnittstelle source,
			final Optional<Planung_Gruppe> sourceGroup,
			final Planung_Gruppe importGroup,
			final EditingDomain editingDomian) {

		// Only import to planing group, when current model not
		// exsit group with this subwork type or import whole subwork
		if (target != ImportTarget.ALL && sourceGroup.isPresent()) {
			return;
		}
		doImportCommand(editingDomian, sourceGroup.orElse(null), importGroup,
				source.getLSTPlanung()
						.getObjektmanagement()
						.getLSTPlanungProjekt()
						.getFirst(),
				PlanProPackage.eINSTANCE.getPlanung_Projekt_LSTPlanungGruppe());
	}

	private void importSubwork(final PlanPro_Schnittstelle source,
			final Optional<Ausgabe_Fachdaten> sourceSubwork,
			final Optional<LST_Zustand> sourceState,
			final Ausgabe_Fachdaten importSubwork,
			final EditingDomain editingDomain) {
		// Whenn source model not contain import subwork type
		// or import whole subwork
		if (target == ImportTarget.ALL || sourceSubwork.isEmpty()
				|| sourceState.isEmpty()) {
			doImportCommand(editingDomain, sourceSubwork.orElse(null),
					importSubwork, source.getLSTPlanung().getFachdaten(),
					PlanProPackage.eINSTANCE
							.getFachdaten_AttributeGroup_AusgabeFachdaten());
			return;
		}

		LST_Zustand importState = importSubwork.getLSTZustandStart();
		if (!importState.getContainer().eAllContents().hasNext()) {
			importState = importSubwork.getLSTZustandZiel();
		}

		doImportCommand(editingDomain, sourceState.get().getContainer(),
				importState.getContainer(), sourceState.get(),
				PlanProPackage.eINSTANCE.getLST_Zustand_Container());
	}

	private static List<Anhang> getImportAttachments(
			final Pair<Planung_Gruppe, Ausgabe_Fachdaten> importData) {
		final List<Anhang> importAttachments = Lists.newArrayList(Iterators
				.filter(importData.getFirst().eAllContents(), Anhang.class));
		importAttachments.addAll(Lists.newArrayList(Iterators
				.filter(importData.getSecond().eAllContents(), Anhang.class)));
		return Lists
				.newArrayList(IterableExtensions.distinctBy(importAttachments,
						attachment -> attachment.getIdentitaet().getWert()));
	}

	private static void importAttachment(final ToolboxFile sourceToolboxfile,
			final List<Anhang> importAttachments,
			final Map<String, byte[]> importAttachmentSource) {
		importAttachments.forEach(attachment -> {
			final String guid = attachment.getIdentitaet().getWert();
			final byte[] value = importAttachmentSource.get(guid);
			if (guid == null || value == null) {
				return;
			}
			try {
				sourceToolboxfile.createMedia(Guid.create(guid), value);
			} catch (final IOException e) {
				throw new RuntimeException(e);
			}
		});
	}

	private Optional<LST_Zustand> getSourceContainer(
			final Optional<Ausgabe_Fachdaten> sourceSubwork) {
		if (sourceSubwork.isEmpty()) {
			return Optional.empty();
		}
		switch (target) {
			case INITIAL:
				return Optional.of(sourceSubwork.get().getLSTZustandStart());
			case FINAL:
				return Optional.of(sourceSubwork.get().getLSTZustandZiel());
			default:
				return Optional.empty();
		}
	}

	private Pair<Planung_Gruppe, Ausgabe_Fachdaten> getImportData(
			final Shell shell) {
		// Subwork not exist by single state model, the users must choose
		// one
		final ENUMUntergewerkArt untergewerkArt = chooseSubworkType(shell);
		final PlanPro_Schnittstelle newSchnitStelle = transformSingleState(
				modelToImport);
		ToolboxIDResolver.resolveIDReferences(newSchnitStelle);
		final Ausgabe_Fachdaten ausgabeFachdaten = newSchnitStelle
				.getLSTPlanung()
				.getFachdaten()
				.getAusgabeFachdaten()
				.get(0);
		final Planung_Gruppe planungGruppe = newSchnitStelle.getLSTPlanung()
				.getObjektmanagement()
				.getLSTPlanungProjekt()
				.get(0)
				.getLSTPlanungGruppe()
				.get(0);
		try {
			// Set new subwork type in Planung_Gruppe and Ausgabe_Fachdaten
			setSubworkType(planungGruppe, List.of(
					PlanProPackage.eINSTANCE.getPlanung_Gruppe_PlanungGAllg(),
					PlanProPackage.eINSTANCE
							.getPlanung_G_Allg_AttributeGroup_UntergewerkArt()),
					untergewerkArt);
			setSubworkType(ausgabeFachdaten,
					List.of(PlanProPackage.eINSTANCE
							.getAusgabe_Fachdaten_UntergewerkArt()),
					untergewerkArt);
			return new Pair<>(planungGruppe, ausgabeFachdaten);
		} catch (final NullPointerException e) {
			throw new RuntimeException();
		}

	}

	private List<Pair<Planung_Gruppe, Ausgabe_Fachdaten>> getImportDatas() {
		final List<SubworkComboSelection> selectedSubworks = subworkCombo
				.getSelectionValues();

		if (selectedSubworks.isEmpty()) {
			return Collections.emptyList();
		}

		return selectedSubworks.stream().map(subwork -> {
			final Optional<Planung_Gruppe> planungGruppe = getPlanungGruppe(
					modelToImport, subwork.getLiteral());
			if (planungGruppe.isEmpty()) {
				throw new IllegalArgumentException(
						String.format("The model not contain sub work type: %s", //$NON-NLS-1$
								subwork.getLiteral()));
			}
			final Planung_Einzel planungEinzel = planungGruppe.get()
					.getLSTPlanungEinzel();
			final Ausgabe_Fachdaten ausgabeFachdaten = getAusgabeFachdaten(
					planungEinzel);
			return new Pair<>(planungGruppe.get(),
					filterImportContainer(ausgabeFachdaten));
		}).toList();
	}

	private ENUMUntergewerkArt chooseSubworkType(final Shell shell) {
		final List<String> subworkTypes = Arrays
				.stream(ENUMUntergewerkArt.values())
				.map(e -> e.getLiteral())
				.toList();
		final String selectedType = serviceProvider.dialogService
				.selectValueDialog(shell,
						serviceProvider.messages.PlanProImportDescriptionService_ViewName,
						serviceProvider.messages.PlanProImportPart_SelectSubworkMessage,
						serviceProvider.messages.PlanProImportPart_SelectSubworkLabel,
						subworkTypes);

		return ENUMUntergewerkArt.get(selectedType);
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

	private Ausgabe_Fachdaten filterImportContainer(
			final Ausgabe_Fachdaten subwork) {
		if (target == ImportTarget.ALL) {
			return subwork;
		}

		// Import both container initial and final
		if (containerCombo.isDisposed()
				|| comboField.isNotSelected(containerCombo)) {
			return subwork;
		}

		final ContainerComboSelection selectionValue = containerCombo
				.getSelectionValue();
		final Ausgabe_Fachdaten newSubwork = EcoreUtil.copy(subwork);
		final LST_Zustand emptyZustand = PlanProFactory.eINSTANCE
				.createLST_Zustand();
		emptyZustand.setContainer(
				PlanProFactory.eINSTANCE.createContainer_AttributeGroup());
		emptyZustand.setIdentitaet(
				BasisobjekteFactory.eINSTANCE.createIdentitaet_TypeClass());
		emptyZustand.getIdentitaet().setWert(Guid.create().toString());
		switch (selectionValue) {
			case START, ZUSTAND_INFORMATION: {
				newSubwork.setLSTZustandZiel(emptyZustand);
				break;
			}
			case ZIEL: {
				newSubwork.setLSTZustandStart(emptyZustand);
				break;
			}
			default:
				logger.error("Unexpected value: {}", selectionValue); //$NON-NLS-1$
				throw new IllegalArgumentException();
		}
		return newSubwork;
	}

	private static <T> void doImportCommand(final EditingDomain editingDomain,
			final T oldValue, final T newValue, final EObject owner,
			final EReference feature) {
		if (newValue == null) {
			return;
		}
		Command command = null;
		if (owner.eGet(feature) instanceof Collection) {
			if (oldValue == null) {
				command = AddCommand.create(editingDomain, owner, feature,
						newValue);
			} else {
				command = ReplaceCommand.create(editingDomain, owner, feature,
						oldValue, Collections.singleton(newValue));
			}
		} else {
			command = SetCommand.create(editingDomain, owner, feature,
					newValue);
		}
		if (command != null) {
			editingDomain.getCommandStack().execute(command);
		}
	}

	@SuppressWarnings("nls")
	private void updateGuidAfterImport(final PlanPro_Schnittstelle source,
			final Pair<Planung_Gruppe, Ausgabe_Fachdaten> dataToImport,
			final EditingDomain editingDomain) {
		final ENUMUntergewerkArt untergewerkArt = getUntergewerkArt(
				dataToImport.getFirst());
		final Optional<Planung_Gruppe> newPlaningGroup = target == ImportTarget.ALL
				? getPlanungGruppe(source, untergewerkArt,
						dataToImport.getFirst().getIdentitaet().getWert())
				: getPlanungGruppe(source, untergewerkArt);
		final Optional<Ausgabe_Fachdaten> newSubwork = newPlaningGroup.isEmpty()
				? getAusgabeFachdaten(source, untergewerkArt)
				: getAusgabeFachdaten(source, newPlaningGroup.get());

		final boolean importSubworkSuccess = target == ImportTarget.ALL
				&& newSubwork.isPresent()
				&& !newSubwork.get()
						.getIdentitaet()
						.getWert()
						.equals(dataToImport.getSecond()
								.getIdentitaet()
								.getWert());
		if (newPlaningGroup.isEmpty() || newSubwork.isEmpty()
				|| importSubworkSuccess) {
			logger.error("The Import process wasn't successfull");
			throw new IllegalArgumentException();
		}

		// increase Ausgabe_number
		final String seriNumberStr = getLaufendeNummerAusgabe(source)
				.orElse("0");
		int serNumberInt = 0;
		try {
			serNumberInt = Integer.parseInt(seriNumberStr);
		} catch (final NumberFormatException e) {
			logger.error(
					"LaufendeNummerAusgabe={} is no number. Zero is assumed for updateForImport.",
					seriNumberStr);
		}
		serNumberInt++;
		final Command setSeriNumberCommand = SetCommand.create(editingDomain,
				newPlaningGroup.get()
						.getLSTPlanungEinzel()
						.getPlanungEAllg()
						.getLaufendeNummerAusgabe(),
				PlanProPackage.eINSTANCE
						.getLaufende_Nummer_Ausgabe_TypeClass_Wert(),
				String.format("%02d", Integer.valueOf(serNumberInt)));
		editingDomain.getCommandStack().execute(setSeriNumberCommand);

		// new GUIDs for PlanungProjeKt
		editingDomain.getCommandStack()
				.execute(createSetGuidCommand(editingDomain,
						(Planung_Projekt) newPlaningGroup.get().eContainer()));

		// new GUIDs for PlanungGruppe
		editingDomain.getCommandStack()
				.execute(createSetGuidCommand(editingDomain,
						newPlaningGroup.get()));

		// new GUIDs for LSTPlanungEinzel
		editingDomain.getCommandStack()
				.execute(createSetGuidCommand(editingDomain,
						newPlaningGroup.get().getLSTPlanungEinzel()));

		final Planung_Einzel lstPlanungEinzel = newPlaningGroup.get()
				.getLSTPlanungEinzel();
		// new GUIDs for LST_Zustand_Start
		if (target == ImportTarget.ALL || target == ImportTarget.INITIAL) {
			editingDomain.getCommandStack()
					.execute(createSetGuidCommand(editingDomain,
							PlanungEinzelExtensions
									.LSTZustandStart(lstPlanungEinzel)));
		}

		// new GUIDs for LST_Zustand_Ziel
		if (target == ImportTarget.ALL || target == ImportTarget.FINAL) {
			editingDomain.getCommandStack()
					.execute(createSetGuidCommand(editingDomain,
							PlanungEinzelExtensions
									.LSTZustandZiel(lstPlanungEinzel)));
		}
	}

	private static Command createSetGuidCommand(
			final EditingDomain editingDomain, final Ur_Objekt owner) {
		return SetCommand.create(editingDomain, owner.getIdentitaet(),
				BasisobjektePackage.eINSTANCE.getIdentitaet_TypeClass_Wert(),
				Guid.create().toString());
	}
}
