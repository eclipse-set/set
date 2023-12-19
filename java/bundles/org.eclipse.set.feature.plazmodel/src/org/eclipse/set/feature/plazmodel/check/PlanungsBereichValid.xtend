/**
 * Copyright (c) 2022 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.plazmodel.check

import org.eclipse.set.feature.plazmodel.check.PlazCheck
import java.util.List
import org.eclipse.set.model.plazmodel.PlazError
import org.eclipse.set.ppmodel.extensions.container.MultiContainer_AttributeGroup
import org.eclipse.set.model.plazmodel.PlazFactory
import org.osgi.service.component.annotations.Component
import static extension org.eclipse.set.ppmodel.extensions.PlanProSchnittstelleExtensions.*
import org.eclipse.set.toolboxmodel.Basisobjekte.Ur_Objekt
import org.eclipse.set.toolboxmodel.Signale.Signal
import org.eclipse.set.toolboxmodel.Signale.Signal_Rahmen
import org.eclipse.set.toolboxmodel.Geodaten.GEO_Kante
import org.eclipse.set.toolboxmodel.Geodaten.TOP_Kante
import org.eclipse.set.toolboxmodel.Bahnsteig.Bahnsteig_Anlage
import org.eclipse.set.toolboxmodel.Bahnsteig.Bahnsteig_Kante
import org.eclipse.set.toolboxmodel.Signale.Signal_Befestigung
import org.eclipse.set.toolboxmodel.Weichen_und_Gleissperren.W_Kr_Anlage
import org.eclipse.set.toolboxmodel.Weichen_und_Gleissperren.W_Kr_Gsp_Element
import org.eclipse.set.toolboxmodel.Weichen_und_Gleissperren.W_Kr_Gsp_Komponente
import org.eclipse.set.toolboxmodel.Ansteuerung_Element.Aussenelementansteuerung
import java.util.Set
import java.util.Map
import org.apache.commons.text.StringSubstitutor
import org.eclipse.set.model.validationreport.ValidationSeverity

/**
 * Validates that Object isn't  planning- und consideration- region same time
 * 
 * @author Truong
 */
@Component(immediate=true)
class PlanungsBereichValid extends AbstractPlazContainerCheck implements PlazCheck {
	Set<Ur_Objekt> planningsObjects = newHashSet

	static val relevantTypeObjects = #[
		Signal,
		Signal_Rahmen,
		Signal_Befestigung,
		GEO_Kante,
		TOP_Kante,
		Bahnsteig_Anlage,
		Bahnsteig_Kante,
		W_Kr_Anlage,
		W_Kr_Gsp_Element,
		W_Kr_Gsp_Komponente,
		Aussenelementansteuerung
	]

	override List<PlazError> run(MultiContainer_AttributeGroup container) {
		modelSession.planProSchnittstelle.LSTPlanungGruppe.orElse(null)?.forEach [
			val objects = LSTPlanungEinzel?.LSTObjektePlanungsbereich?.
				IDLSTObjektPlanungsbereich
			if (objects.nullOrEmpty) {
				return
			}
			planningsObjects.addAll(objects)
		]
		if (planningsObjects.nullOrEmpty) {
			return #[]
		}
		return container.urObjekt.filter(
			obj |
				relevantTypeObjects.exists[isInstance(obj)]
		).flatMap[checkObject].toList
	}

	private def Iterable<PlazError> checkObject(Ur_Objekt object) {
		val guid = object.identitaet?.wert
		if (guid === null) {
			return #[]
		}

		val isPlanning = isPlanningObject(object)
		val mismatchedObjects = object.referencedObjects.filter [
			isPlanningObject(it) !== isPlanning
		].filterNull

		return mismatchedObjects.map [
			val err = PlazFactory.eINSTANCE.createPlazError
			err.message = transformErroMsg(
				Map.of("GUID", guid, //
				"TYP", it.eClass.name, //
				"REF_GUID", identitaet?.wert)
			)
			err.type = "Planungs-/Betrachtungsbereich"
			err.object = object
			err.severity = ValidationSeverity.WARNING
			return err
		]

	}

	private def Set<Ur_Objekt> getReferencedObjects(Ur_Objekt source) {
		return source.eCrossReferences.filter(Ur_Objekt).filter [ obj |
			relevantTypeObjects.exists[isInstance(obj) && obj != source]
		].filterNull.toSet
	}

	private def boolean isPlanningObject(Ur_Objekt parent) {
		return planningsObjects.contains(parent)
	}

	override checkType() {
		return "Planungs-/Betrachtungsbereich"
	}

	override getDescription() {
		return "Objekte sind eindeutig im Planungs- oder Betrachtungsbereich verortet."
	}

	override getGeneralErrMsg() {
		return "Das Objekt {GUID} verweist auf das zugehörige Objekt {TYP} {REF_GUID}, die Objekte liegen aber uneinheitlich in Planungs- und Betrachtungsbereich."
	}

	override transformErroMsg(Map<String, String> params) {
		return StringSubstitutor.replace(getGeneralErrMsg(), params, "{", "}"); // $NON-NLS-1$//$NON-NLS-2$
	}
}
