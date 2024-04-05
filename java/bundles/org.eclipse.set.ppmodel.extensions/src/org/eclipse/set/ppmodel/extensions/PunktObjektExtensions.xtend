/**
 * Copyright (c) 2016 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.ppmodel.extensions

import org.eclipse.set.model.planpro.BasisTypen.ENUMWirkrichtung
import org.eclipse.set.model.planpro.Basisobjekte.Punkt_Objekt
import org.eclipse.set.model.planpro.Basisobjekte.Punkt_Objekt_TOP_Kante_AttributeGroup
import org.eclipse.set.model.planpro.Geodaten.TOP_Kante
import org.eclipse.set.model.planpro.Geodaten.TOP_Knoten
import org.eclipse.set.model.planpro.Ortung.FMA_Komponente
import org.eclipse.set.model.planpro.Signalbegriffe_Ril_301.Ra12
import org.eclipse.set.model.planpro.Signale.Signal
import java.util.List
import org.eclipse.set.basis.graph.DirectedEdge
import org.locationtech.jts.geom.Coordinate

import static extension org.eclipse.set.ppmodel.extensions.ENUMWirkrichtungExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.PunktObjektTopKanteExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.SignalExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.utils.CollectionExtensions.*

/**
 * This class extends {@link Punkt_Objekt}.
 * 
 * @author Schaefer
 */
class PunktObjektExtensions extends BasisObjektExtensions {

	/**
	 * @param punktObjekt this Punkt Objekt
	 * 
	 * @return list of TOP-Kanten this Punkt Objekt is located on
	 */
	def static List<TOP_Kante> getTopKanten(Punkt_Objekt punktObjekt) {
		return punktObjekt.singlePoints.map[topKante]
	}

	/**
	 * @param punktObjekt this Punkt Objekt
	 * 
	 * @return whether this Punkt Objekt is a FMA Komponente with a Achszählpunkt group
	 */
	def static boolean isAchszaehlpunkt(Punkt_Objekt punktObjekt) {
		if (punktObjekt instanceof FMA_Komponente) {
			return punktObjekt.FMAKomponenteAchszaehlpunkt !== null
		}
		return false
	}

	/**
	 * @param punktObjekt this Punkt Objekt
	 * 
	 * @return the TOP Knoten this Punkt Objekt is located at, or
	 * <code>null</code> if the Punkt Objekt is not located at the end of an
	 * edge
	 */
	def static TOP_Knoten getTopKnoten(Punkt_Objekt punktObjekt) {
		return punktObjekt.singlePoints.map[topKnoten].flatten.toSet.
			uniqueOrNull
	}

	/**
	 * @param punktObjekt this Punkt Objekt
	 * @param topKante a TOP Kante
	 * 
	 * @return the Wirkrichtung of this Punkt Objekt on the given TOP Kante
	 */
	def static ENUMWirkrichtung getWirkrichtung(Punkt_Objekt punktObjekt,
		TOP_Kante topKante) {
		val wirkrichtungen = punktObjekt.singlePoints.filter [
			IDTOPKante?.value.identitaet?.wert == topKante.identitaet.wert
		].map[wirkrichtung?.wert].toSet
		if (wirkrichtungen.size == 0) {
			throw new IllegalArgumentException(
				'''Punkt Objekt «punktObjekt.identitaet.wert» not located on TOP-Kante «topKante.identitaet.wert»'''
			)
		}
		return wirkrichtungen.get(0)
	}

	/**
	 * @param punktObjekt this Punkt Objekt
	 * @param edge a directed edge with this Punkt Objekt
	 * 
	 * @return whether this Punkt Objekt is in Wirkrichtung of the given edge
	 */
	def static boolean isInWirkrichtung(Punkt_Objekt punktObjekt,
		DirectedEdge<TOP_Kante, ?, Punkt_Objekt> edge) {
		return punktObjekt.getWirkrichtung(edge.element).
			isInWirkrichtung(edge.forwards)
	}

	/**
	 * @param punktObjekt this Punkt Objekt
	 * 
	 * @return the unique single point of this Punkt Objekt
	 */
	def static Punkt_Objekt_TOP_Kante_AttributeGroup getSinglePoint(
		Punkt_Objekt punktObjekt) {
		return punktObjekt.singlePoints.unique
	}

	/**
	 * @param punktObjekt this Punkt Objekt
	 * 
	 * @return the single points of this Punkt Objekt
	 */
	def static List<Punkt_Objekt_TOP_Kante_AttributeGroup> getSinglePoints(
		Punkt_Objekt punktObjekt) {
		return punktObjekt.punktObjektTOPKante
	}

	/**
	 * @param punktObjekt this Punkt Objekt
	 * 
	 * @return whether this Punkt Objekt is a Grenzzeichen
	 */
	def static boolean isGrenzzeichen(Punkt_Objekt punktObjekt) {
		if (punktObjekt instanceof Signal) {
			return punktObjekt.hasSignalbegriffID(Ra12)
		}
		return false
	}

	/**
	 * Returns the first coordinate found for this Punkt Objekt on a single point.
	 * 
	 * @param punktObjekt this Punkt Objekt
	 * 
	 * @returns the coordinate of the Punkt Objekt
	 */
	def static Coordinate getCoordinate(Punkt_Objekt punktObjekt) {
		// we ignore all coordinates but the first
		return punktObjekt.singlePoints.get(0).coordinate.
			getCoordinate
	}

	/**
	 * Returns the rotation for the Punkt_Objekt on the siteplan (counterclockwise
	 * in degrees) at the first coordinate found for the single point
	 * of the Punkt_Objekt
	 * 
	 * @param object this Punkt_Objekt
	 * 
	 * @returns the rotation
	 */
	def static double rotation(Punkt_Objekt object) {
		val point = object.singlePoints.get(0)
		var direction = point.wirkrichtung?.wert
		if (direction === null) {
			// For a Punkt_Objekt without a direction, assume it is along the axis
			direction = ENUMWirkrichtung.ENUM_WIRKRICHTUNG_IN
		}
		return point.getCoordinate(direction).getEffectiveRotation
	}
}
