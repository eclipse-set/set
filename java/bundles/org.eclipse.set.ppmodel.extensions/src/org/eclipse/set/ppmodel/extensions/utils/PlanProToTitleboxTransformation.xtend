/**
 * Copyright (c) 2017 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.ppmodel.extensions.utils

import org.eclipse.set.model.planpro.PlanPro.PlanPro_Schnittstelle
import org.eclipse.set.model.planpro.PlanPro.Planung_E_Allg_AttributeGroup
import org.eclipse.set.model.planpro.PlanPro.Planung_Einzel
import org.eclipse.set.model.planpro.PlanPro.Planung_G_Schriftfeld_AttributeGroup
import org.eclipse.set.model.planpro.PlanPro.Planung_Gruppe
import org.eclipse.set.model.titlebox.Titlebox
import org.eclipse.set.model.titlebox.TitleboxFactory
import org.eclipse.set.utils.ToolboxConfiguration

import static extension org.eclipse.set.model.titlebox.extensions.TitleboxExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.PlanProSchnittstelleExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.PlanungProjektExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.utils.XMLGregorianCalendarExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.utils.IterableExtensions.*

/**
 * Transformation from PlanPro Schnittstelle to Titlebox.
 * 
 * @author Schaefer
 */
class PlanProToTitleboxTransformation {

	static final String EMPTY_EDITION_NUMBER = ""; // $NON-NLS-1$
	/**
	 * Date format with days, month and year (two digits)
	 */
	static final String DATE_FORMAT = "dd.MM.yy"; // $NON-NLS-1$
	static final String DATE_FORMAT_LONG = "dd.MM.yyyy"; // $NON-NLS-1$
	/**
	 * Date format with month and year (two digits)
	 */
	public static final String DATE_FORMAT_SHORT = "MM/yy"; // $NON-NLS-1$
	static final String EMPTY_PLANNING_NUMBER = "<Plannummer DB AG>"

	private new() {
	}

	/**
	 * Creates a new transformation.
	 */
	def static PlanProToTitleboxTransformation create() {
		return new PlanProToTitleboxTransformation
	}

	/**
	 * Transforms a PlanPro Schnittstelle to a Titlebox.
	 */
	def Titlebox create TitleboxFactory.eINSTANCE.createTitlebox transform(
		PlanPro_Schnittstelle schnittstelle, TableNameInfo tableName) {
		resetFields
		addFieldsFrom(
			schnittstelle?.LSTPlanungProjekt?.planungGruppe?.
				planungGSchriftfeld,
			tableName
		)
		val planungEinzel = schnittstelle?.planungEinzel

		val lastPlanungEErstellung = planungEinzel?.planungEHandlung?.
			planungEErstellung?.lastOrNull
		it.set(83,
			lastPlanungEErstellung?.datum?.wert?.toString(DATE_FORMAT_LONG) ?:
				"")
		it.set(84,
			lastPlanungEErstellung?.handelnder?.akteurAllg?.nameAkteur?.wert ?:
				"")

		val lastPlanungPruefung = planungEinzel?.planungEHandlung?.
			planungEPruefung?.lastOrNull
		it.set(88,
			lastPlanungPruefung?.datum?.wert?.toString(DATE_FORMAT_LONG) ?: "")
		it.set(89,
			lastPlanungPruefung?.handelnder?.akteurAllg?.nameAkteur?.wert ?:
				"")

		val lastPlanungFreigabe = planungEinzel?.planungEHandlung?.
			planungEFreigabe?.lastOrNull
		it.set(91,
			lastPlanungFreigabe?.datum?.wert?.toString(DATE_FORMAT_LONG) ?: "")
		it.set(92,
			lastPlanungFreigabe?.handelnder?.akteurAllg?.nameAkteur?.wert ?:
				"")

		val lastPlanungAbnahme = planungEinzel?.planungEHandlung?.
			planungEAbnahme?.lastOrNull
		val lastPlanungUebernahme = planungEinzel?.planungEHandlung?.
			planungEUebernahme?.lastOrNull

		val planungAllgemein = schnittstelle?.planungAllgemein;
		val idPlanungBasis = schnittstelle?.referenzPlanungBasis;

		if (idPlanungBasis !== null) {
			it.set(34, schnittstelle.referenzPlanungBasis)
			it.set(35, planungEinzel?.bauzustandPlanungEinzel)
			it.set(36,
				lastPlanungEErstellung?.handelnder?.akteurAllg?.
					nameAkteur5?.wert ?: "")
			it.set(43,
				lastPlanungEErstellung?.datum?.wert?.toString(
					DATE_FORMAT_SHORT) ?: "")
			it.set(37,
				lastPlanungPruefung?.handelnder?.akteurAllg?.
					nameAkteur5?.wert ?: "")
			it.set(44,
				lastPlanungPruefung?.datum?.wert?.toString(DATE_FORMAT_SHORT) ?:
					"")
			it.set(38,
				lastPlanungAbnahme?.handelnder?.akteurAllg?.
					nameAkteur5?.wert ?: "")
			it.set(45,
				lastPlanungAbnahme?.datum?.wert?.toString(DATE_FORMAT_SHORT) ?:
					"")
			it.set(39,
				lastPlanungUebernahme?.handelnder?.akteurAllg?.
					nameAkteur5?.wert ?: "")
			it.set(46,
				lastPlanungUebernahme?.datum?.wert?.toString(
					DATE_FORMAT_SHORT) ?: "")
		}

		val version = ToolboxConfiguration.toolboxVersion

		it.set(
			23, '''Geplant mit «schnittstelle?.planProSchnittstelleAllg?.werkzeugName?.wert ?: ""» «schnittstelle?.planProSchnittstelleAllg?.werkzeugVersion?.wert ?: ""»
		Visualisiert mit «ToolboxConfiguration.shortName» «version.shortVersion»''')
		it.set(56, planungAllgemein?.indexAusgabe?.wert)
		it.set(57, planungAllgemein?.bauzustandPlanungAllgemein)
		it.set(58,
			lastPlanungEErstellung?.handelnder?.akteurAllg?.
				nameAkteur5?.wert ?: "")
		it.set(70,
			lastPlanungEErstellung?.datum?.wert?.toString(DATE_FORMAT_SHORT) ?:
				"")
		it.set(59,
			lastPlanungPruefung?.handelnder?.akteurAllg?.nameAkteur5?.wert ?:
				"")
		it.set(71,
			lastPlanungPruefung?.datum?.wert?.toString(DATE_FORMAT_SHORT) ?: "")
		it.set(60,
			lastPlanungAbnahme?.handelnder?.akteurAllg?.nameAkteur5?.wert ?:
				"")
		it.set(72,
			lastPlanungAbnahme?.datum?.wert?.toString(DATE_FORMAT_SHORT) ?: "")
		it.set(61,
			lastPlanungUebernahme?.handelnder?.akteurAllg?.nameAkteur5?.wert ?:
				"")
		it.set(73,
			lastPlanungUebernahme?.datum?.wert?.toString(DATE_FORMAT_SHORT) ?:
				"")

		it.set(48, planungAllgemein.buildLastEditionNumber)

		it.set(
			74, '''«schnittstelle.oertlichkeit»«tableName?.planningNumber ?: EMPTY_PLANNING_NUMBER»''')

		it.set(99, planungAllgemein.buildLastEditionNumber)

		it.set(62, lastPlanungEErstellung?.datum?.wert?.toString(DATE_FORMAT) ?:
			"")

		if (ToolboxConfiguration.pdfExportTestFilling) {
			fillEmptyFieldsWithAddresses
		}

		return
	}

	private def String getOertlichkeit(PlanPro_Schnittstelle schnittstelle) {
		var fuehrendeOertlichkeit = schnittstelle?.LSTPlanungProjekt?.
			planungGruppe?.fuehrendeOertlichkeit?.wert
		if (fuehrendeOertlichkeit === null) {
			return ""
		}
		while (fuehrendeOertlichkeit.length < 5) {
			fuehrendeOertlichkeit = '''«fuehrendeOertlichkeit»_'''
		}
		return '''«fuehrendeOertlichkeit».'''
	}

	private def String getBauzustandPlanungAllgemein(
		Planung_E_Allg_AttributeGroup planung) {
		return String.format("%s", // $NON-NLS-1$
		planung?.getBauzustandKurzbezeichnung()?.getWert() ?: "");
	}

	private def String getBauzustandPlanungEinzel(Planung_Einzel planung) {
		return getBauzustandPlanungAllgemein(planung?.planungEAllg)
	}

	private def String getReferenzPlanungBasis(
		PlanPro_Schnittstelle schnittstelle) {
		return schnittstelle?.planungEinzel?.referenzPlanungBasis?.wert
	}

	/**
	 * @return last edition number
	 */
	private def String buildLastEditionNumber(
		Planung_E_Allg_AttributeGroup planungEAllg) {
		if (planungEAllg !== null) {
			return String.format("%s.%s", // $NON-NLS-1$
			planungEAllg?.getIndexAusgabe()?.getWert() ?: "",
				planungEAllg?.getLaufendeNummerAusgabe()?.getWert() ?: "");
		}
		return EMPTY_EDITION_NUMBER;
	}

	def Planung_E_Allg_AttributeGroup getPlanungAllgemein(
		PlanPro_Schnittstelle schnittstelle) {
		return schnittstelle?.planungEinzel?.planungEAllg
	}

	def Planung_Gruppe getPlanungGruppe(PlanPro_Schnittstelle schnittstelle) {
		return schnittstelle?.LSTPlanungProjekt?.planungGruppe
	}

	def Planung_Einzel getPlanungEinzel(PlanPro_Schnittstelle schnittstelle) {
		return schnittstelle?.LSTPlanungProjekt?.planungGruppe?.LSTPlanungEinzel
	}

	private def void addFieldsFrom(Titlebox titlebox,
		Planung_G_Schriftfeld_AttributeGroup schriftfeld,
		TableNameInfo tableName) {
		titlebox.set(85, schriftfeld.compileField85(tableName))
		titlebox.set(93, schriftfeld?.planungsbuero?.nameOrganisation?.wert)
	}

	private def String compileField85(
		Planung_G_Schriftfeld_AttributeGroup schriftfeld,
		TableNameInfo tableName) {
		return '''
			«schriftfeld?.bezeichnungAnlage?.wert»
			«schriftfeld?.bezeichnungUnteranlage?.wert»
			«tableName?.getFullDisplayName ?: "<Planart>"»
		'''
	}
}
