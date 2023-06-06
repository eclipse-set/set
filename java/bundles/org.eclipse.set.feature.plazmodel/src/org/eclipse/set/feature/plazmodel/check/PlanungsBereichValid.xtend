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

/**
 * Validates that Object isn't  planning- und consideration- region same time
 * 
 * @author Truong
 */
@Component(immediate=true)
class PlanungsBereichValid extends AbstractPlazContainerCheck implements PlazCheck {
	Set<String> planningsObjectID = newHashSet
	Map<Ur_Objekt, Boolean> alreadyValidatedObjects = newHashMap
	static val relevantObjects = #[
		Signal, Signal_Rahmen, Signal_Befestigung, 
		GEO_Kante, TOP_Kante, Bahnsteig_Anlage, Bahnsteig_Kante, W_Kr_Anlage,
		W_Kr_Gsp_Element, W_Kr_Gsp_Komponente, Aussenelementansteuerung
	]

	override List<PlazError> run(MultiContainer_AttributeGroup container) {
		modelSession.planProSchnittstelle.LSTPlanungGruppe.orElse(null)?.forEach[
			LSTPlanungEinzel?.LSTObjektePlanungsbereich?.IDLSTObjektPlanungsbereich?.forEach[
				val guid = identitaet?.wert
				if (guid !== null && !planningsObjectID.contains(identitaet.wert)) {
					planningsObjectID.add(guid)
				}
			]
		]
		
		return container.allContents
			.filter(Ur_Objekt)
			.filter(obj | relevantObjects.exists[it.isInstance(obj)])
			.map[
				val guid = it.identitaet?.wert
				if (guid !== null && !isPlanningObject(planningsObjectID.contains(guid))) {
					val err = PlazFactory.eINSTANCE.createPlazError
					err.message = '''Bestandteile des Objekts «identitaet.wert» sind sowohl im Planungs- als auch im Betrachtungsbereich verortet.'''
					err.type = "Planungs-/Betrachtungsbereich"
					err.object = it
					return err
				} 
			]
			.filterNull.toList
	}
	
	private def boolean isPlanningObject(Ur_Objekt parent, boolean isPlanningObject) {
		val alreadyValidate = alreadyValidatedObjects.get(parent)
		if (alreadyValidate !== null) {
			return alreadyValidate
		}
		val parentGUID = parent.identitaet?.wert
		if (parentGUID !== null && planningsObjectID.contains(parentGUID) !== isPlanningObject) {
			alreadyValidatedObjects.put(parent, false)
			return false
		} 
		
		val refObj = !parent.eCrossReferences
			.filter(Ur_Objekt)
			.filter[obj | relevantObjects.exists[
				val objGuid = obj.identitaet?.wert
				return objGuid !== null && isInstance(obj) && parentGUID !== objGuid
			]]
			.exists[!isPlanningObject(isPlanningObject)]
		alreadyValidatedObjects.put(parent, refObj)
		return refObj
		
		
	}
	
	override checkType() {
		return "Planungs-/Betrachtungsbereich"
	}
	
	override getDescription() {
		return "Objekte sind entweder im Planungs- oder Betrachtungsbereich verortet."
	}
	
}