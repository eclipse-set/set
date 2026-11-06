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
import java.util.SortedSet
import org.eclipse.set.basis.IModelSession
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

import static extension org.eclipse.set.ppmodel.extensions.PlanProSchnittstelleExtensions.*

/**
 * Validates that Object and the referenced objects are in same region planing/consideration
 * 
 * @author Truong
 */
@Component(immediate=true)
class PlanungsBereichValid extends AbstractPlazContainerCheck implements PlazCheck {
	// Use a SortedSet to speed up checks for whether a GUID is in planing region.
	SortedSet<String> planningsObjectGuids = newTreeSet(
		String.CASE_INSENSITIVE_ORDER)
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
	
	override List<PlazError> run(IModelSession modelSession) {
		val result = super.run(modelSession)
		// Clear temporary list after check
		planningsObjectGuids.clear
		return result;
	}

	override List<PlazError> run(MultiContainer_AttributeGroup container) {

		modelSession.planProSchnittstelle.LSTPlanungGruppe.orElse(null)?.forEach [
			val objects = LSTPlanungEinzel?.LSTObjektePlanungsbereich?.
				IDLSTObjektPlanungsbereich?.filterNull?.map[wert]
			if (objects.nullOrEmpty) {
				return
			}
			planningsObjectGuids.addAll(objects)
		]

		if (planningsObjectGuids.nullOrEmpty) {
			return #[]
		}

		val relevantObjects = container.urObjekt.filter [ obj |
			relevantTypeObjects.exists[isInstance(obj)]
		]
		val objectWithReferences = relevantObjects.toMap([it], [
			eAllContents.filter(Zeiger_TypeClass).toSet
		]).filter [ obj, references |
			!references.nullOrEmpty
		]

		return relevantObjects.flatMap[checkObject(objectWithReferences)].toList
	}

	private def Iterable<PlazError> checkObject(Ur_Objekt source,
		Map<Ur_Objekt, Set<Zeiger_TypeClass>> objectWithReferencesMap) {
		val guid = source.identitaet?.wert
		if (guid === null) {
			return #[]
		}
		val isPlanning = isPlanningObject(source)

		val falsyReferences = objectWithReferencesMap.entrySet.filter [ entry |
			entry.key !== source
		].filter [ entry |
			entry.value.exists[wert == source.identitaet.wert]
		].filter [ entry |
			isPlanningObject(entry.key) !== isPlanning
		].map [ entry |
			entry.value.filter[wert == guid].map[ref|entry.key -> ref]
		].flatten

		return falsyReferences.map [
			val err = PlazFactory.eINSTANCE.createPlazError
			err.message = '''Das Objekt «it.key.eClass.name» «it.key.identitaet?.wert» verweist auf das zugehörige Objekt «source.eClass.name» «guid», die Objekte liegen aber uneinheitlich in Planungs- und Betrachtungsbereich.'''
			err.type = checkType
			err.object = it.value
			err.severity = ValidationSeverity.WARNING
			return err
		]

	}

	private def boolean isPlanningObject(Ur_Objekt parent) {
		return planningsObjectGuids.contains(parent.identitaet.wert)
	}

	override checkType() {
		return "Planungs-/Betrachtungsbereich"
	}

	override getDescription() {
		return "Objekte sind eindeutig im Planungs- oder Betrachtungsbereich verortet."
	}

	override getGeneralErrMsg() {
		return "Objekte und zugehörige, referenzierte Objekt liegen uneinheitlich in Planungs- und Betrachtungsbereich."
	}
}
