/**
 * Copyright (c) 2016 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.ppmodel.extensions

import com.google.common.collect.Lists
import de.scheidtbachmann.planpro.model.model1902.BasisTypen.ENUMWirkrichtung
import de.scheidtbachmann.planpro.model.model1902.Basisobjekte.Punkt_Objekt
import de.scheidtbachmann.planpro.model.model1902.Basisobjekte.Punkt_Objekt_TOP_Kante_AttributeGroup
import de.scheidtbachmann.planpro.model.model1902.Basisobjekte.Ur_Objekt
import de.scheidtbachmann.planpro.model.model1902.Geodaten.TOP_Kante
import de.scheidtbachmann.planpro.model.model1902.Geodaten.TOP_Knoten
import java.math.BigDecimal
import java.util.HashSet
import java.util.List
import java.util.Set
import org.eclipse.set.ppmodel.extensions.utils.Distance
import org.eclipse.set.ppmodel.extensions.utils.SymbolArrangement
import org.locationtech.jts.geom.Coordinate

import static extension org.eclipse.set.ppmodel.extensions.TopKanteExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.ZeigerExtensions.*

/**
 * Extensions for {@link Punkt_Objekt_TOP_Kante_AttributeGroup} aka single
 * point objects (dt. Einzelpunktobjekte) aka single points (dt. Einzelpunkte).
 * 
 * @author Schaefer
 */
class PunktObjektTopKanteExtensions extends BasisObjektExtensions {

	/**
	 * @param singlePoint this single point
	 * 
	 * @return an (artificial) identity string
	 */
	def static String getIdentitaet(
		Punkt_Objekt_TOP_Kante_AttributeGroup singlePoint
	) {
		val container = singlePoint.eContainer as Ur_Objekt
		val containerId = container?.identitaet?.wert
		val containedPots = container.eContents.filter(
			Punkt_Objekt_TOP_Kante_AttributeGroup).toList
		val index = containedPots.indexOf(singlePoint)

		return '''«containerId»/«index»'''
	}

	/**
	 * @param singlePoint this single point
	 * 
	 * @return the TOP Kante of this single point
	 */
	def static TOP_Kante getTopKante(
		Punkt_Objekt_TOP_Kante_AttributeGroup singlePoint
	) {
		return singlePoint.IDTOPKante.resolve(TOP_Kante)
	}

	/**
	 * @param singlePoint this single point
	 * @param direction the direction to use
	 * 
	 * @return the position of this single point
	 */
	def static SymbolArrangement<Coordinate> getCoordinate(
		Punkt_Objekt_TOP_Kante_AttributeGroup singlePoint,
		ENUMWirkrichtung direction
	) {
		val topKante = singlePoint.topKante
		val abstand = singlePoint.abstand.wert.doubleValue
		val lateralDistance = (singlePoint?.seitlicherAbstand?.wert ?:
			BigDecimal.ZERO).doubleValue
		return topKante.getCoordinate(abstand, lateralDistance, direction)
	}

	/**
	 * @param singlePoint this single point
	 * 
	 * @return the position of this single point
	 */
	def static SymbolArrangement<Coordinate> getCoordinate(
		Punkt_Objekt_TOP_Kante_AttributeGroup singlePoint
	) {
		val direction = singlePoint.wirkrichtung?.wert
		return getCoordinate(singlePoint, direction)
	}

	/**
	 * @param singlePoint this single point
	 * 
	 * @return the set of TOP Knoten this single point is located on
	 */
	def static Set<TOP_Knoten> getTopKnoten(
		Punkt_Objekt_TOP_Kante_AttributeGroup singlePoint
	) {
		val result = new HashSet

		val topKante = singlePoint.topKante
		val abstand = topKante.getAbstand(singlePoint)
		val length = topKante.TOPKanteAllg.TOPLaenge.wert.doubleValue

		if (Distance.compare(abstand, 0) == 0) {
			result.add(topKante.TOPKnotenA)
		}

		if (Distance.compare(abstand, length) == 0) {
			result.add(topKante.TOPKnotenB)
		}

		return result
	}

	/**
	 * @param singlePoint this single point
	 * 
	 * @return the Punkt Objekt containing this single point
	 */
	def static Punkt_Objekt getPunktObjekt(
		Punkt_Objekt_TOP_Kante_AttributeGroup singlePoint
	) {
		return singlePoint.eContainer as Punkt_Objekt
	}

	/**
	 * @param singlePoint this single point
	 * 
	 * @return a list with this single point
	 */
	def static List<Punkt_Objekt_TOP_Kante_AttributeGroup> getSinglePoints(
		Punkt_Objekt_TOP_Kante_AttributeGroup singlePoint
	) {
		return Lists.newArrayList(singlePoint)
	}

	/**
	 * @param singlePoint this single point
	 * 
	 * @return two positions defining a line perpendicular to the TOP_Kante at the offset of the point
	 */
	def static Pair<SymbolArrangement<Coordinate>, SymbolArrangement<Coordinate>> getPerpendicularLineAtPoint(
		Punkt_Objekt_TOP_Kante_AttributeGroup singlePoint
	) {
		val topKante = singlePoint.topKante
		val abstand = singlePoint.abstand.wert.doubleValue
		val wirkrichtung = singlePoint.wirkrichtung?.wert

		// Determine two coordinates, each with an offset of 1 unit to the TOP_Kante 
		val firstCoordinate = topKante.getCoordinate(abstand, -1, wirkrichtung)
		val secondCoordinate = topKante.getCoordinate(abstand, 1, wirkrichtung)

		return firstCoordinate -> secondCoordinate
	}
}
