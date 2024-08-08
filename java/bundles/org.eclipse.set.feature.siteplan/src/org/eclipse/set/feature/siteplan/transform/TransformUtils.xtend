/**
 * Copyright (c) 2021 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.siteplan.transform

import java.util.List
import org.eclipse.emf.common.util.EList
import org.eclipse.set.model.siteplan.Coordinate
import org.eclipse.set.model.siteplan.Label
import org.eclipse.set.model.siteplan.RouteObject
import org.eclipse.set.model.siteplan.Siteplan
import org.eclipse.set.model.siteplan.SiteplanFactory
import org.eclipse.set.model.planpro.BasisTypen.Bezeichnung_Element_AttributeGroup
import org.eclipse.set.model.planpro.Basisobjekte.Punkt_Objekt
import org.eclipse.set.model.planpro.Basisobjekte.Punkt_Objekt_Strecke_AttributeGroup
import org.eclipse.set.model.planpro.PlanPro.PlanPro_Schnittstelle

import static extension org.eclipse.set.ppmodel.extensions.PlanProSchnittstelleExtensions.*

/**
 * Utility functions for siteplan transformers
 * 
 * @author Stuecker
 */
class TransformUtils {
	static val UNKNOWN_ROUTE = "Unbekannte Strecke"
	public static List<Thread> transformThreads = newLinkedList
	
	/**
	 * Transforms a PlanPro Bezeichnung_Element_AttributeGroup to a Siteplan Label
	 * 
	 * @param labelelement the PlanPro Bezeichnung_Element_AttributeGroup or null
	 * @return a siteplan label or null if no Bezeichnung_Element_AttributeGroup was given
	 */
	static def Label getLabel(Bezeichnung_Element_AttributeGroup labelelement) {
		val labeltext = labelelement?.bezeichnungLageplanKurz?.wert
		if (labeltext === null)
			return null
		val label = SiteplanFactory.eINSTANCE.createLabel()
		label.text = labeltext
		return label

	}

	/** 
	 * Calculates the distance between two Coordinates
	 * @param position the first position
	 * @param position2 the second  position
	 * @return the distance between the two positions 
	 */
	static def double distanceTo(Coordinate position, Coordinate position2) {
		val xdistance = (position.x - position2.x)
		val ydistance = (position.y - position2.y)
		return Math.sqrt(xdistance * xdistance + ydistance * ydistance)
	}
	
	static def void transformPunktObjektStrecke(
		Punkt_Objekt punktObjekt,
		RouteObject routeObject
	) {
		punktObjekt.punktObjektStrecke.transformPunktObjektStrecke(routeObject)
	}
	
	static def void transformPunktObjektStrecke(
		EList<Punkt_Objekt_Strecke_AttributeGroup> punktObjektStrecke,
		RouteObject routeObject
	)
	{
		if (punktObjektStrecke === null) {
			return
		}
		routeObject.routeLocations.addAll(punktObjektStrecke.map[
			val location = SiteplanFactory.eINSTANCE.createRouteLocation
			location.km = it.streckeKm?.wert
			location.route = it.IDStrecke?.value?.bezeichnung?.bezeichnungStrecke?.wert ?: UNKNOWN_ROUTE
			return location
		])
	}
	
	static def void transformPlanningRegion(Siteplan siteplan, PlanPro_Schnittstelle schnittstelle) {
		val projects = schnittstelle.LSTPlanungGruppe
		if (projects.isPresent) {
			projects.get.forEach [
				val objectManagement = SiteplanFactory.eINSTANCE.createObjectManagement
				objectManagement.planningGroupID = identitaet?.wert
				LSTPlanungEinzel?.LSTObjektePlanungsbereich?.IDLSTObjektPlanungsbereich?.forEach[
						if (!objectManagement.planningObjectIDs.contains(wert)) {
							objectManagement.planningObjectIDs.add(wert)
						}
					]
				siteplan.objectManagement.add(objectManagement)
			]
		}
	}
	
	static def <T> void createTransformatorThread(List<T> transformators, 
		String threadName, int threadNumber, (T) => void runFunction
	) {
		
		val transformatorsSize = transformators.size
		val threadList = <Thread>newLinkedList
		for (var i = 0; i < threadNumber; i++) {
			val sublist = transformators.subList(i * transformatorsSize / threadNumber,
					(i + 1) * transformatorsSize / threadNumber
			)
			val thread = new Thread(threadName + "_" + i) {
				override run() {
					var index = 0
					while (!Thread.interrupted &&
						index < sublist.size
					) {
						runFunction.apply(sublist.get(index))
						index++
					}
				}
			}
			
			transformThreads.add(thread)
			threadList.add(thread)
			thread.start
		}
		
		threadList.forEach[
			try {
				if (it.alive && !it.interrupted) {
					it.join		
				}
			} catch (InterruptedException exc) {
				Thread.currentThread.interrupt
			}
		]
	}
}
