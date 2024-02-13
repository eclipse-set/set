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

import static org.eclipse.set.ppmodel.extensions.PlanungGruppeExtensions.getLaufendeNummerAusgabe;
import static org.eclipse.set.ppmodel.extensions.PlanungGruppeExtensions.getPlanungGruppe;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.ReplaceCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.set.basis.IModelSession;
import org.eclipse.set.basis.Pair;
import org.eclipse.set.basis.guid.Guid;
import org.eclipse.set.feature.projectdata.ppimport.ImportControl.ImportTarget;
import org.eclipse.set.ppmodel.extensions.PlanungEinzelExtensions;
import org.eclipse.set.toolboxmodel.Basisobjekte.BasisobjektePackage;
import org.eclipse.set.toolboxmodel.Basisobjekte.Ur_Objekt;
import org.eclipse.set.toolboxmodel.PlanPro.Ausgabe_Fachdaten;
import org.eclipse.set.toolboxmodel.PlanPro.Container_AttributeGroup;
import org.eclipse.set.toolboxmodel.PlanPro.LST_Zustand;
import org.eclipse.set.toolboxmodel.PlanPro.PlanProPackage;
import org.eclipse.set.toolboxmodel.PlanPro.PlanPro_Schnittstelle;
import org.eclipse.set.toolboxmodel.PlanPro.Planung_Einzel;
import org.eclipse.set.toolboxmodel.PlanPro.Planung_Gruppe;
import org.eclipse.set.toolboxmodel.PlanPro.Planung_Projekt;
import org.eclipse.set.toolboxmodel.transform.IDReferenceUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 */
public class ModelImportUtils {
	static final Logger logger = LoggerFactory
			.getLogger(ModelImportUtils.class);

	private ModelImportUtils() {
	}

	/**
	 * Create command for add/replace
	 * {@link Ausgabe_Fachdaten}/{@link Planung_Gruppe} into current project
	 * 
	 * @param modelSession
	 *            the session
	 * @param importControl
	 *            the import control
	 * @param oldData
	 *            the old date should be replace
	 * @return command to import new data
	 * @throws NullPointerException
	 *             {@link NullPointerException}
	 * @throws IllegalArgumentException
	 *             {@link IllegalArgumentException}
	 */
	public static boolean doImportSubworkCommands(
			final IModelSession modelSession, final ImportControl importControl,
			final Pair<Planung_Gruppe, Ausgabe_Fachdaten> oldData)
			throws NullPointerException, IllegalArgumentException {
		final EditingDomain editingDomain = modelSession.getEditingDomain();
		final PlanPro_Schnittstelle schnittstelle = modelSession
				.getPlanProSchnittstelle();
		final Pair<Planung_Gruppe, Ausgabe_Fachdaten> newData = importControl
				.getSelectedData();
		// Import technical data command
		final Ausgabe_Fachdaten copy = EcoreUtil.copy(newData.getSecond());
		boolean isSomethingImported = false;
		final boolean importedSubwork = doImportCommand(editingDomain,
				oldData == null ? null : oldData.getSecond(), copy,
				schnittstelle.getLSTPlanung().getFachdaten(),
				PlanProPackage.eINSTANCE
						.getFachdaten_AttributeGroup_AusgabeFachdaten());

		IDReferenceUtils.retargetIDReferences(newData.getSecond(), copy,
				importControl.schnittstelle.getWzkInvalidIDReferences(),
				schnittstelle.getWzkInvalidIDReferences());
		isSomethingImported = isSomethingImported || importedSubwork;
		// Import plan group command
		final Planung_Projekt owner = oldData == null
				? schnittstelle.getLSTPlanung().getObjektmanagement()
						.getLSTPlanungProjekt().get(0)
				: (Planung_Projekt) oldData.getFirst().eContainer();
		final boolean importPlanung = doImportCommand(editingDomain,
				oldData == null ? null : oldData.getFirst(), newData.getFirst(),
				owner,
				PlanProPackage.eINSTANCE.getPlanung_Projekt_LSTPlanungGruppe());
		return isSomethingImported || importPlanung;
	}

	/**
	 * Create import command for relevant {@link LST_Zustand}
	 * 
	 * @param modelSession
	 *            the session
	 * @param oldZustand
	 *            the {@link LST_Zustand} should be replace
	 * @param importControl
	 *            the import control
	 * @param containerValue
	 *            which {@link LST_Zustand} (Initial/Final) of importResource
	 *            was selected
	 * @return {@link Command} command to replace oldZustand
	 * @throws NullPointerException
	 *             {@link NullPointerException}
	 * @throws IllegalArgumentException
	 *             {@link IllegalArgumentException}
	 */
	public static boolean doImportContainerCommand(
			final IModelSession modelSession, final LST_Zustand oldZustand,
			final ImportControl importControl,
			final ContainerComboSelection containerValue)
			throws NullPointerException, IllegalArgumentException {
		if (containerValue == null) {
			logger.error("Container to import isn't selected"); //$NON-NLS-1$
			throw new IllegalArgumentException();
		}
		final EditingDomain editingDomain = modelSession.getEditingDomain();
		LST_Zustand importZuStand = null;
		final Ausgabe_Fachdaten importResource = importControl.getSelectedData()
				.getSecond();
		switch (containerValue) {
		case START, ZUSTAND_INFORMATION:
			importZuStand = importResource.getLSTZustandStart();
			break;
		case ZIEL:
			importZuStand = importResource.getLSTZustandZiel();
			break;
		default:
			logger.error("Unexpected value: {}", containerValue); //$NON-NLS-1$
			throw new IllegalArgumentException();
		}

		final Container_AttributeGroup copy = EcoreUtil
				.copy(importZuStand.getContainer());

		editingDomain.getCommandStack()
				.execute(SetCommand.create(editingDomain, oldZustand,
						PlanProPackage.eINSTANCE.getLST_Zustand_Container(),
						copy));
		IDReferenceUtils.retargetIDReferences(importZuStand.getContainer(),
				copy, importControl.schnittstelle.getWzkInvalidIDReferences(),
				modelSession.getPlanProSchnittstelle()
						.getWzkInvalidIDReferences());

		return true;
	}

	private static boolean doImportCommand(final EditingDomain editingDomain,
			final Ur_Objekt oldValue, final Ur_Objekt newValue,
			final EObject owner, final EReference feature) {
		if (newValue == null) {
			return false;
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
			return true;
		}
		return false;

	}

	/**
	 * Update Guid for relevant object
	 * 
	 * @param schnittstelle
	 *            the {@link PlanPro_Schnittstelle}
	 * @param editingDomain
	 *            the {@link EditingDomain}
	 * @param importControl
	 *            the {@link ImportControl}
	 * @throws IllegalArgumentException
	 *             {@link IllegalArgumentException}
	 * @throws NullPointerException
	 *             {@link NullPointerException}
	 */
	@SuppressWarnings("nls")
	public static void updateForImport(
			final PlanPro_Schnittstelle schnittstelle,
			final EditingDomain editingDomain,
			final ImportControl importControl)
			throws IllegalArgumentException, NullPointerException {
		final SubworkComboSelection subworkValue = importControl.comboField
				.getSubworkCombo().getSelectionValue();
		if (subworkValue.getName()
				.equals(SubworkComboSelection.NOT_SELECTED_SUBWORK)
				|| subworkValue.getName()
						.equals(SubworkComboSelection.NOT_SET_SUBWORK)) {
			logger.error("Subwork type isn't selected");
			throw new IllegalArgumentException();
		}

		final Optional<Planung_Gruppe> planungGruppe = getPlanungGruppe(
				schnittstelle, subworkValue.getLiteral());

		if (planungGruppe.isEmpty()) {
			logger.error("The Project doesn't contain subwork type {}",
					subworkValue.getLiteral());
			throw new IllegalArgumentException();
		}

		// increase Ausgabe Nummer
		final String seriNumberStr = getLaufendeNummerAusgabe(
				planungGruppe.get()).orElse("0");
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
				planungGruppe.get().getLSTPlanungEinzel().getPlanungEAllg()
						.getLaufendeNummerAusgabe(),
				PlanProPackage.eINSTANCE
						.getLaufende_Nummer_Ausgabe_TypeClass_Wert(),
				String.format("%02d", Integer.valueOf(serNumberInt)));
		editingDomain.getCommandStack().execute(setSeriNumberCommand);

		// new GUIDs for PlanungProjeKt
		editingDomain.getCommandStack()
				.execute(createSetGuidCommand(editingDomain,
						(Planung_Projekt) planungGruppe.get().eContainer()));

		// new GUIDs for PlanungGruppe
		editingDomain.getCommandStack().execute(
				createSetGuidCommand(editingDomain, planungGruppe.get()));

		// new GUIDs for LSTPlanungEinzel
		editingDomain.getCommandStack().execute(createSetGuidCommand(
				editingDomain, planungGruppe.get().getLSTPlanungEinzel()));

		final Planung_Einzel lstPlanungEinzel = planungGruppe.get()
				.getLSTPlanungEinzel();
		// new GUIDs for LST_Zustand_Start
		if (importControl.getImportTarget() == ImportTarget.MODEL
				|| importControl.getImportTarget() == ImportTarget.INITIAL) {
			editingDomain.getCommandStack().execute(createSetGuidCommand(
					editingDomain,
					PlanungEinzelExtensions.LSTZustandStart(lstPlanungEinzel)));
		}

		// new GUIDs for LST_Zustand_Ziel
		if (importControl.getImportTarget() == ImportTarget.MODEL
				|| importControl.getImportTarget() == ImportTarget.FINAL) {
			editingDomain.getCommandStack().execute(createSetGuidCommand(
					editingDomain,
					PlanungEinzelExtensions.LSTZustandZiel(lstPlanungEinzel)));
		}
	}

	private static Command createSetGuidCommand(
			final EditingDomain editingDomain, final Ur_Objekt owner) {
		return SetCommand.create(editingDomain, owner.getIdentitaet(),
				BasisobjektePackage.eINSTANCE.getIdentitaet_TypeClass_Wert(),
				Guid.create().toString());
	}
}
