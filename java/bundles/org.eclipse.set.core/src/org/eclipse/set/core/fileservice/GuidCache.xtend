/**
 * Copyright (c) 2022 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.core.fileservice

import java.util.HashMap
import java.util.Map
import org.eclipse.emf.ecore.EObject
import org.eclipse.set.model.planpro.Basisobjekte.Ur_Objekt
import org.eclipse.set.model.planpro.PlanPro.Ausgabe_Fachdaten
import org.eclipse.set.model.planpro.PlanPro.Container_AttributeGroup
import org.eclipse.set.model.planpro.PlanPro.PlanPro_Schnittstelle
import org.eclipse.set.core.fileservice.GuidCache.ContainerType
import org.eclipse.set.model.planpro.Layoutinformationen.PlanPro_Layoutinfo

/**
 * Guid to Object cache for faster lookups
 * 
 * @author Stuecker
 */
class GuidCache {
	enum ContainerType {
		Single,
		Initial,
		Planning,
		Global,
		Layout
	}

	final Map<String, EObject> globalGuidToObjectMap = new HashMap()
	final Map<String, EObject> singleGuidToObjectMap = new HashMap()
	final Map<String, EObject> initialGuidToObjectMap = new HashMap()
	final Map<String, EObject> planningGuidToObjectMap = new HashMap()
	final Map<String, EObject> layoutGuidToObjectMap = new HashMap()

	/**
	 * @param guid the GUID
	 * @param source the source object which references the guid
	 * @return the referenced object or null if it cannot be found  
	 */
	def EObject get(String guid, EObject source) {
		val container = getContainerType(source);
		switch (container) {
			case ContainerType.Initial:
				return initialGuidToObjectMap.get(guid)
			case ContainerType.Planning:
				return planningGuidToObjectMap.get(guid)
			case ContainerType.Single:
				return singleGuidToObjectMap.get(guid)
			case ContainerType.Global:
				return globalGuidToObjectMap.get(guid)
			case ContainerType.Layout:
				return layoutGuidToObjectMap.get(guid)
			default:
				return null
		}
	}

	/**
	 * Initialize the cache
	 * 
	 * @param planProSchnittstelle the PlanPro_Schnittstelle
	 */
	def void prepare(PlanPro_Schnittstelle planProSchnittstelle) {
		prepare(planProSchnittstelle, ContainerType.Global,
			globalGuidToObjectMap);
		prepare(planProSchnittstelle, ContainerType.Single,
			singleGuidToObjectMap);
		prepare(planProSchnittstelle, ContainerType.Initial,
			initialGuidToObjectMap);
		prepare(planProSchnittstelle, ContainerType.Planning,
			planningGuidToObjectMap);
	}

	/**
	 * Initialize the cache
	 * 
	 * @param planProSchnittstelle the PlanPro_Schnittstelle
	 */
	def void prepare(PlanPro_Layoutinfo layout) {
		prepare(layout, layoutGuidToObjectMap);
	}

	private def void prepare(PlanPro_Layoutinfo layout,
		Map<String, EObject> map) {
		(getCommonObjects(layout) +
			(layout?.eContents?.filter(Ur_Objekt) ?: #[])).forEach [
			map.put(it?.identitaet?.wert, it)
		]
	}

	private def void prepare(PlanPro_Schnittstelle planProSchnittstelle,
		ContainerType containerType, Map<String, EObject> map) {
		(getCommonObjects(planProSchnittstelle) +
			(getObjectList(planProSchnittstelle, containerType) ?: #[])).forEach [
			map.put(it?.identitaet?.wert, it)
		]
	}

	private def Iterable<Ur_Objekt> getObjectList(
		PlanPro_Schnittstelle planProSchnittstelle,
		ContainerType containerType) {
		switch (containerType) {
			case Single: {
				return planProSchnittstelle?.getLSTZustand?.getContainer?.
					eContents?.filter(Ur_Objekt)
			}
			case Planning: {
				return planProSchnittstelle?.getLSTPlanung?.fachdaten?.
					ausgabeFachdaten?.map[LSTZustandZiel?.container]?.filterNull?.
					flatMap [
						eContents
					]?.filter(Ur_Objekt)
			}
			case Initial: {
				return planProSchnittstelle?.getLSTPlanung?.fachdaten?.
					ausgabeFachdaten?.map [
						LSTZustandStart?.container
					]?.filterNull?.flatMap [
						eContents
					]?.filter(Ur_Objekt)
			}
			case Global: {
				return planProSchnittstelle?.getLSTPlanung?.fachdaten?.
					ausgabeFachdaten?.flatMap [
						#[LSTZustandStart, LSTZustandZiel].map[container].
							filterNull.flatMap [
								eContents
							]
					]?.filter(Ur_Objekt)
			}
			case Layout: {
				return null // planProSchnittstelle?.planpro_layoutinfo?.eContents?.filter(Ur_Objekt)
			}
		}
		return null
	}

	private def Iterable<Ur_Objekt> getCommonObjects(EObject object) {
		// Do not recurse into object containers
		if (object instanceof Container_AttributeGroup) return #[]
		// Ignore non-Ur_Objekts (which cannot be referred to by GUID)
		if (!(object instanceof Ur_Objekt))
			return object.eContents.flatMap [
				getCommonObjects
			]
		// Recurse into contents 
		return #[object as Ur_Objekt] + object.eContents.flatMap [
			getCommonObjects
		]
	}

	private def ContainerType getContainerType(EObject referenceObject) {
		// Recurse upwards along the element tree until either:
		// The top level node has been reached (-> Global type)
		// or A Container_AttributeGroup has been reached (-> Signle/Initial/Planning type)  
		if (referenceObject === null) return ContainerType::Global
		if (!(referenceObject instanceof Container_AttributeGroup))
			return getContainerType(referenceObject.eContainer())

		// referenceObject is now of type Container_AttributeGroup
		// Check whether it is contained within Ausgabe_Fachdaten (Initial/Planning) or PlanPro_Schnittstelle (Single)
		var EObject lstState = referenceObject.eContainer
		var EObject lstStateContainer = lstState.eContainer
		if (!(lstStateContainer instanceof Ausgabe_Fachdaten))
			return ContainerType::Single

		// Find whether it is contained in a LST_Zustand_Start (Initial), LST_Zustand_Ziel (Planning) reference 
		var Ausgabe_Fachdaten ausgabeFachdaten = (lstStateContainer as Ausgabe_Fachdaten)
		if (ausgabeFachdaten.getLSTZustandStart() === lstState) {
			return ContainerType::Initial
		} else if (ausgabeFachdaten.getLSTZustandZiel() === lstState) {
			return ContainerType::Planning
		}

		return ContainerType::Layout
	}
}
