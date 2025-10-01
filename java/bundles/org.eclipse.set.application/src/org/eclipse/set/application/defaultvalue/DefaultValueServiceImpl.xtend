/**
 * Copyright (c) 2017 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.application.defaultvalue

import org.eclipse.set.model.planpro.PlanPro.ENUMUntergewerkArt
import org.eclipse.set.model.planpro.PlanPro.PlanProFactory
import org.eclipse.set.model.planpro.PlanPro.PlanPro_Schnittstelle
import jakarta.inject.Inject
import org.eclipse.core.runtime.Assert
import org.eclipse.set.core.services.defaultvalue.DefaultValueService
import org.eclipse.set.core.services.version.PlanProVersionService

import static extension org.eclipse.set.ppmodel.extensions.PlanProSchnittstelleExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.PlanungProjektExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.PlanungEinzelExtensions.*

/**
 * Implementation of {@link DefaultValueService}.
 * 
 * @author Schaefer
 */
class DefaultValueServiceImpl implements DefaultValueService {

	@Inject
	PlanProVersionService versionService

	override setDefaultValues(PlanPro_Schnittstelle planProSchnittstelle) {
		Assert.isNotNull(planProSchnittstelle)

		if (planProSchnittstelle.LSTPlanung === null) {
			planProSchnittstelle.LSTPlanung = PlanProFactory.eINSTANCE.
				createLST_Planung_AttributeGroup
		}

		if (planProSchnittstelle.LSTPlanungProjekt.LSTPlanungGruppe === null) {
			planProSchnittstelle.
				LSTPlanungProjekt.LSTPlanungGruppe = PlanProFactory.eINSTANCE.
				createPlanung_Gruppe
		}

		if (planProSchnittstelle.LSTPlanungProjekt.leadingPlanungGruppe.
			LSTPlanungEinzel === null) {
			planProSchnittstelle.LSTPlanungProjekt.
				leadingPlanungGruppe.LSTPlanungEinzel = PlanProFactory.eINSTANCE.
				createPlanung_Einzel
		}

		if (planProSchnittstelle.LSTPlanungProjekt.leadingPlanungGruppe.
			LSTPlanungEinzel.planungEAllg === null) {
			planProSchnittstelle.LSTPlanungProjekt.leadingPlanungGruppe.
				LSTPlanungEinzel.planungEAllg = PlanProFactory.eINSTANCE.
				createPlanung_E_Allg_AttributeGroup
		}

		if (planProSchnittstelle.LSTPlanungProjekt.leadingPlanungGruppe.
			LSTPlanungEinzel.planungEAllg.informativ === null) {
			planProSchnittstelle.LSTPlanungProjekt.leadingPlanungGruppe.
				LSTPlanungEinzel.planungEAllg.informativ = PlanProFactory.
				eINSTANCE.createInformativ_TypeClass
		}

		if (planProSchnittstelle.LSTPlanungProjekt.leadingPlanungGruppe.
			LSTPlanungEinzel.planungEAllg.informativ.wert === null) {
			planProSchnittstelle.LSTPlanungProjekt.leadingPlanungGruppe.
				LSTPlanungEinzel.planungEAllg.informativ.wert = false
		}

		if (planProSchnittstelle.LSTPlanungProjekt.leadingPlanungGruppe.
			planungGAllg === null) {
			planProSchnittstelle.LSTPlanungProjekt.
				leadingPlanungGruppe.planungGAllg = PlanProFactory.eINSTANCE.
				createPlanung_G_Allg_AttributeGroup
		}
		val planungGAllg = planProSchnittstelle.LSTPlanungProjekt.leadingPlanungGruppe.
			planungGAllg
		if (planungGAllg.verantwortlicheStelleDB === null) {
			planungGAllg.verantwortlicheStelleDB = PlanProFactory.eINSTANCE.
				createVerantwortliche_Stelle_DB_TypeClass
		}

		if (planungGAllg.verantwortlicheStelleDB.wert === null) {
			planungGAllg.verantwortlicheStelleDB.wert = "DB InfraGO"
		}

		if (planungGAllg.planProXSDVersion === null) {
			planungGAllg.planProXSDVersion = PlanProFactory.eINSTANCE.
				createPlanPro_XSD_Version_TypeClass
		}

		if (planungGAllg.planProXSDVersion.wert === null) {
			val versionInfo = versionService.createSupportedVersion()
			planungGAllg.planProXSDVersion.wert = versionInfo.planPro
		}

		if (planungGAllg.untergewerkArt === null) {
			planungGAllg.untergewerkArt = PlanProFactory.eINSTANCE.
				createUntergewerk_Art_TypeClass
		}

		if (planungGAllg.untergewerkArt.wert === null) {
			planungGAllg.untergewerkArt.wert = ENUMUntergewerkArt.
				ENUM_UNTERGEWERK_ART_ESTW
		}

		val ausgabeFachdaten = planProSchnittstelle.LSTPlanungProjekt.
			leadingPlanungGruppe.LSTPlanungEinzel.ausgabeFachdaten

		if (ausgabeFachdaten?.untergewerkArt === null) {
			ausgabeFachdaten.untergewerkArt = PlanProFactory.eINSTANCE.
				createUntergewerk_Art_TypeClass

		}
		// Subwork by Planing Group and Technicaldata must be same
		ausgabeFachdaten.untergewerkArt.wert = planungGAllg.untergewerkArt.wert
	}
}
