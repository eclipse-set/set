/**
 * Copyright (c) 2022 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.plazmodel.check

import java.util.List
import java.util.Map
import java.util.Set
import org.eclipse.set.basis.constants.Events
import org.eclipse.set.model.planpro.Ansteuerung_Element.Aussenelementansteuerung
import org.eclipse.set.model.planpro.Bahnsteig.Bahnsteig_Anlage
import org.eclipse.set.model.planpro.Bahnsteig.Bahnsteig_Kante
import org.eclipse.set.model.planpro.BasisTypen.Zeiger_TypeClass
import org.eclipse.set.model.planpro.Basisobjekte.Ur_Objekt
import org.eclipse.set.model.planpro.Geodaten.GEO_Kante
import org.eclipse.set.model.planpro.Geodaten.TOP_Kante
import org.eclipse.set.model.planpro.Signale.Signal
import org.eclipse.set.model.planpro.Signale.Signal_Befestigung
import org.eclipse.set.model.planpro.Signale.Signal_Rahmen
import org.eclipse.set.model.planpro.Weichen_und_Gleissperren.W_Kr_Anlage
import org.eclipse.set.model.planpro.Weichen_und_Gleissperren.W_Kr_Gsp_Element
import org.eclipse.set.model.planpro.Weichen_und_Gleissperren.W_Kr_Gsp_Komponente
import org.eclipse.set.model.plazmodel.PlazError
import org.eclipse.set.model.plazmodel.PlazFactory
import org.eclipse.set.model.validationreport.ValidationSeverity
import org.eclipse.set.ppmodel.extensions.container.MultiContainer_AttributeGroup
import org.osgi.service.component.annotations.Component
import org.osgi.service.event.Event
import org.osgi.service.event.EventConstants
import org.osgi.service.event.EventHandler

import static extension org.eclipse.set.ppmodel.extensions.PlanProSchnittstelleExtensions.*

/**
 * Validates that Object and the referenced objects are in same region planing/consideration
 * 
 * @author Truong
 */
@Component(immediate=true, property=#[EventConstants.EVENT_TOPIC + "=" +
	Events.CLOSE_SESSION,
	EventConstants.EVENT_TOPIC + "=" + Events.MODEL_CHANGED])
class PlanungsBereichValid extends AbstractPlazContainerCheck implements PlazCheck, EventHandler {
	Set<String> planningsObjectGuids = newHashSet
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
				IDLSTObjektPlanungsbereich?.filterNull?.map[wert]
			if (objects.nullOrEmpty) {
				return
			}
			planningsObjectGuids.addAll(objects)
		]

		val objectWithReferences = container.urObjekt.filter [ obj |
			relevantTypeObjects.exists[isInstance(obj)]
		].toMap([it], [eAllContents.filter(Zeiger_TypeClass).toSet]).filter [ obj, references |
			!references.nullOrEmpty
		]

		if (planningsObjectGuids.nullOrEmpty) {
			return #[]
		}
		return container.urObjekt.filter [ obj |
			relevantTypeObjects.exists[isInstance(obj)]
		].flatMap[checkObject(objectWithReferences)].toList
	}

	private def Iterable<PlazError> checkObject(Ur_Objekt source,
		Map<Ur_Objekt, Set<Zeiger_TypeClass>> objectWithReferencesMap) {
		val guid = source.identitaet?.wert
		if (guid === null) {
			return #[]
		}

		val isPlanning = isPlanningObject(source)

		val mismatchedObjects = objectWithReferencesMap.filter [ obj, references |
			obj !== source && references.exists[wert == source.identitaet.wert]
		].filter [ obj, referneces |
			isPlanningObject(obj) !== isPlanning
		].keySet.filterNull
		return mismatchedObjects.map [
			val err = PlazFactory.eINSTANCE.createPlazError
			err.message = transformErrorMsg(
				Map.of("GUID", guid, //
				"TYP", it.eClass.name, //
				"REF_GUID", identitaet?.wert)
			)
			err.type = "Planungs-/Betrachtungsbereich"
			err.object = source
			err.severity = ValidationSeverity.WARNING
			return err
		]

	}

	private def boolean isPlanningObject(Ur_Objekt parent) {
		return planningsObjectGuids.exists[it == parent.identitaet.wert]
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

	override handleEvent(Event event) {
		if (event.topic == Events.CLOSE_SESSION ||
			event.topic === Events.MODEL_CHANGED) {
			planningsObjectGuids.clear
		}
	}

}
