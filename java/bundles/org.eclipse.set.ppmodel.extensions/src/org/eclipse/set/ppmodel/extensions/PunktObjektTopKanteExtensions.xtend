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
import java.math.BigDecimal
import java.util.HashSet
import java.util.List
import java.util.Set
import org.eclipse.set.basis.geometry.GeoPosition
import org.eclipse.set.basis.graph.TopPoint
import org.eclipse.set.core.services.Services
import org.eclipse.set.model.planpro.BasisTypen.ENUMWirkrichtung
import org.eclipse.set.model.planpro.Basisobjekte.Bereich_Objekt
import org.eclipse.set.model.planpro.Basisobjekte.Punkt_Objekt
import org.eclipse.set.model.planpro.Basisobjekte.Punkt_Objekt_TOP_Kante_AttributeGroup
import org.eclipse.set.model.planpro.Basisobjekte.Ur_Objekt
import org.eclipse.set.model.planpro.Geodaten.Strecke
import org.eclipse.set.model.planpro.Geodaten.TOP_Kante
import org.eclipse.set.model.planpro.Geodaten.TOP_Knoten
import org.eclipse.set.ppmodel.extensions.utils.Distance
import org.locationtech.jts.geom.Coordinate

import static org.eclipse.set.model.planpro.BasisTypen.ENUMWirkrichtung.*

import static extension org.eclipse.set.ppmodel.extensions.BereichObjektExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.StreckeExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.StreckePunktExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.TopKanteExtensions.*

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
		return singlePoint.IDTOPKante?.value
	}

	/**
	 * @param singlePoint this single point
	 * @param direction the direction to use
	 * 
	 * @return the position of this single point
	 */
	def static GeoPosition getCoordinate(
		Punkt_Objekt_TOP_Kante_AttributeGroup singlePoint,
		ENUMWirkrichtung direction
	) {
		val topKante = singlePoint.topKante
		val abstand = singlePoint.abstand.wert
		val lateralDistance = singlePoint?.seitlicherAbstand?.wert ?:
			BigDecimal.ZERO
		return topKante.getCoordinate(abstand, lateralDistance, direction)
	}

	/**
	 * @param singlePoint this single point
	 * 
	 * @return the position of this single point
	 */
	def static GeoPosition getCoordinate(
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
		val comparator = new Distance()
		val result = new HashSet

		val topKante = singlePoint.topKante
		if (topKante === null) {
			return result
		}
		val abstand = topKante.getAbstand(singlePoint)
		val length = topKante.TOPKanteAllg.TOPLaenge.wert

		if (comparator.compare(abstand, BigDecimal.ZERO) == 0) {
			result.add(topKante.TOPKnotenA)
		}

		if (comparator.compare(abstand, length) == 0) {
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
	def static Pair<GeoPosition, GeoPosition> getPerpendicularLineAtPoint(
		Punkt_Objekt_TOP_Kante_AttributeGroup singlePoint
	) {
		val topKante = singlePoint.topKante
		val abstand = singlePoint.abstand.wert
		val wirkrichtung = singlePoint.wirkrichtung?.wert

		// Determine two coordinates, each with an offset of 1 unit to the TOP_Kante 
		val firstCoordinate = topKante.getCoordinate(abstand,
			BigDecimal.ONE.negate, wirkrichtung)
		val secondCoordinate = topKante.getCoordinate(abstand, BigDecimal.ONE,
			wirkrichtung)

		return firstCoordinate -> secondCoordinate
	}

	def static boolean isWirkrichtungTopDirection(
		Punkt_Objekt_TOP_Kante_AttributeGroup singlePoint) {
		val wirkrichtung = singlePoint?.wirkrichtung?.wert

		if (wirkrichtung !== null &&
			(wirkrichtung === ENUM_WIRKRICHTUNG_GEGEN ||
				wirkrichtung === ENUM_WIRKRICHTUNG_IN)) {
			return wirkrichtung === ENUM_WIRKRICHTUNG_IN
		}

		val seitlicheLage = singlePoint?.seitlicheLage?.wert
		switch seitlicheLage {
			case ENUM_LINKS_RECHTS_LINKS:
				return false
			case ENUM_LINKS_RECHTS_RECHTS:
				return true
			default:
				throw new UnsupportedOperationException(
				'''POTK: «singlePoint.identitaet» has no wirkrichtung''')
		}
	}

	def static boolean isBelongToBereichObjekt(
		Punkt_Objekt_TOP_Kante_AttributeGroup singlePoint, Bereich_Objekt bo) {
		return bo.bereichObjektTeilbereich.exists [ botb |
			val limitA = (botb.begrenzungA?.wert ?: 0).doubleValue
			val limitB = (botb.begrenzungB?.wert ?: 0).doubleValue
			val position = (singlePoint?.abstand?.wert ?: 0).doubleValue
			return limitA < position && position < limitB ||
				limitB < position && position < limitA

		]
	}
	
	
	/**
	 * Find relevant routes in area of a point object
	 * 
	 * @param potk the Punkt_Objekt_Top_Kante
	 */
	def static List<Strecke> getStreckenThroughBereichObjekt(
		Punkt_Objekt_TOP_Kante_AttributeGroup potk) {
		val topPoint = new TopPoint(potk)
		return potk.container.strecke.filter [ route |
			route.bereichObjektTeilbereich.filter [
				topKante === topPoint.edge
			].exists [
				topPoint.distance >= begrenzungA.wert &&
					topPoint.distance <= begrenzungB.wert
			]
		].filterNull.toList
	}
	
	/**
	 * Find the kilometer mark of the projection of a point object on a route.
	 * 
	 * @param potk the Punkt_Objekt_Top_Kante
	 * @param strecke the Strecke
	 */
	def static dispatch BigDecimal getStreckeKmThroughtProjection(
		Punkt_Objekt_TOP_Kante_AttributeGroup potk, Strecke strecke) {
		val potkCoordinate = Services.pointObjectPositionService.
			getCoordinate(potk)
		return potkCoordinate.coordinate.getStreckeKmThroughtProjection(strecke)
	}
	
	/**
	 * Find the kilometer mark of the coordinate on a route.
	 * 
	 * @param coordinate the coodinate
	 * @param strecke the Strecke
	 */
	def static dispatch BigDecimal getStreckeKmThroughtProjection(Coordinate coordinate, Strecke strecke) {
		val projectionPointAndDistance = Services.geometryService.
			getProjectionCoordinateOnStrecke(coordinate, strecke)
		val nearstRoutePoint = strecke.streckenPunkte.map [
			it -> streckePunktTopDistance
		].minBy[(projectionPointAndDistance.second - it.value).abs]

		val nearsRoutePointMeter = nearstRoutePoint.key.streckeMeter.wert
		val distance = nearstRoutePoint.value -
			projectionPointAndDistance.second
		val projectionmeter = nearsRoutePointMeter - distance
		return (projectionmeter / BigDecimal.valueOf(1000))
	} 
}
