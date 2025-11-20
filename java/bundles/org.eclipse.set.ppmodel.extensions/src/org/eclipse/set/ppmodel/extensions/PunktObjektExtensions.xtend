/**
 * Copyright (c) 2016 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.ppmodel.extensions

import com.google.common.collect.Range
import java.math.BigDecimal
import java.util.List
import java.util.Optional
import org.eclipse.set.basis.constants.ToolboxConstants
import org.eclipse.set.basis.graph.DirectedEdge
import org.eclipse.set.basis.graph.TopPoint
import org.eclipse.set.core.services.Services
import org.eclipse.set.model.planpro.BasisTypen.ENUMWirkrichtung
import org.eclipse.set.model.planpro.Basisobjekte.Bereich_Objekt
import org.eclipse.set.model.planpro.Basisobjekte.Bereich_Objekt_Teilbereich_AttributeGroup
import org.eclipse.set.model.planpro.Basisobjekte.Punkt_Objekt
import org.eclipse.set.model.planpro.Basisobjekte.Punkt_Objekt_Strecke_AttributeGroup
import org.eclipse.set.model.planpro.Basisobjekte.Punkt_Objekt_TOP_Kante_AttributeGroup
import org.eclipse.set.model.planpro.Geodaten.Strecke
import org.eclipse.set.model.planpro.Geodaten.TOP_Kante
import org.eclipse.set.model.planpro.Geodaten.TOP_Knoten
import org.eclipse.set.model.planpro.Ortung.FMA_Komponente
import org.eclipse.set.model.planpro.Signalbegriffe_Ril_301.Ra12
import org.eclipse.set.model.planpro.Signale.Signal
import org.locationtech.jts.geom.Coordinate
import org.slf4j.Logger
import org.slf4j.LoggerFactory

import static org.eclipse.set.ppmodel.extensions.geometry.GEOKanteGeometryExtensions.*

import static extension org.eclipse.set.ppmodel.extensions.BereichObjektExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.ENUMWirkrichtungExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.PunktObjektTopKanteExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.SignalExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.utils.CollectionExtensions.*
import static extension org.eclipse.set.utils.math.BigDecimalExtensions.*

/**
 * This class extends {@link Punkt_Objekt}.
 * 
 * @author Schaefer
 */
class PunktObjektExtensions extends BasisObjektExtensions {
	static val Logger logger = LoggerFactory.getLogger(
		typeof(PunktObjektExtensions))

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
			IDTOPKante?.wert == topKante.identitaet.wert
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
		return punktObjekt.singlePoints.get(0).coordinate.getCoordinate
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

	def static List<Pair<String, List<String>>> getStreckeAndKm(
		Punkt_Objekt po) {
		val cache = po.getCache(po.container.cacheString,
			ToolboxConstants.CacheId.POINT_OBJECT_ROUTE_KM)
		val poGuid = po.identitaet.wert
		if (cache.getIfPresent(poGuid) !== null) {
			return cache.getIfPresent(
				poGuid) as List<Pair<String, List<String>>>
		}

		if (po.punktObjektStrecke.nullOrEmpty) {
			cache.set(poGuid, #[])
			return #[]
		}

		val getStreckeFunc = [ Punkt_Objekt_Strecke_AttributeGroup pos |
			pos.IDStrecke?.value?.bezeichnung?.bezeichnungStrecke?.wert ?: ""
		]

		if (!isFindGeometryComplete) {
			return po.punktObjektStrecke.map [ pos |
				getStreckeFunc.apply(pos) -> #[]
			].toList
		}
		if (po.punktObjektStrecke.size === 1) {
			val result = #[getStreckeFunc.apply(po.punktObjektStrecke.first) ->
				#[po.punktObjektStrecke.first.streckeKm.wert]]
			cache.set(poGuid, result)
			return result
		}

		val kmMassgebends = po.punktObjektStrecke.filter [
			kmMassgebend?.wert === true
		]
		if (!kmMassgebends.nullOrEmpty) {
			val result = kmMassgebends.map [
				getStreckeFunc.apply(it) -> #[streckeKm.wert]
			].toList
			cache.set(poGuid, result)
			return result
		}

		val routeThroughBereichObjekt = po.singlePoint.
			streckenThroughBereichObjekt

		val result = routeThroughBereichObjekt.map [ route |
			route.bezeichnung?.bezeichnungStrecke?.wert ?: "" ->
				po.getStreckeKm(#[route])
		].toList
		cache.set(poGuid, result)
		return result

	}

	def static List<String> getStreckeKm(Punkt_Objekt po,
		List<Strecke> routeThroughBereichObjekt) {
		val cache = po.getCache(po.container.cacheString,
			ToolboxConstants.CacheId.POINT_OBJECT_ROUTE_KM)
		val poGuid = po.identitaet.wert
		if (cache.getIfPresent(poGuid) !== null) {
			val cachedValue = cache.getIfPresent(
				poGuid) as List<Pair<String, List<String>>>
			return cachedValue.flatMap[value].toList
		}

		val kmMassgebend = po.punktObjektStrecke.filter [
			kmMassgebend?.wert === true
		]
		if (!kmMassgebend.nullOrEmpty) {
			return kmMassgebend.map[streckeKm.wert].toList
		}

		if (!isFindGeometryComplete) {
			return null
		}

		val result = routeThroughBereichObjekt.map [ route |
			try {
				return route ->
					po.singlePoint.getStreckeKmThroughProjection(route).
						toTableDecimal
			} catch (Exception e) {
				logger.error(
					"Can't find the Signal route km through projection point on route",
					e)
				return route -> po.punktObjektStrecke.findFirst [ pos |
					pos.IDStrecke.value == route
				]?.streckeKm?.wert
			}
		].filterNull.toList

		if (result.isNullOrEmpty) {
			cache.set(poGuid, po.punktObjektStrecke.map [
				IDStrecke.value.bezeichnung.bezeichnungStrecke ->
					#[streckeKm.wert].toList
			].toList)
			return po.punktObjektStrecke.map[streckeKm.wert].toList
		}
		cache.set(poGuid, result.map[key -> #[value].toList].toList)
		return result.map[value].toList
	}

	def static List<Strecke> getStreckenThroughBereichObjekt(Punkt_Objekt po) {
		return po.singlePoints.flatMap[streckenThroughBereichObjekt].toList
	}

	def static Optional<Range<BigDecimal>> distanceToBereichObjekt(
		Punkt_Objekt po, Bereich_Objekt bo) {
		return distanceToBereichObjekt(po, bo, Optional.empty)
	}

	def static Optional<Range<BigDecimal>> distanceToBereichObjekt(
		Punkt_Objekt po, Bereich_Objekt bo, boolean inDirection) {
		return distanceToBereichObjekt(po, bo,
			Optional.of(Boolean.valueOf(inDirection)))
	}

	private def static Optional<Range<BigDecimal>> distanceToBereichObjekt(
		Punkt_Objekt po, Bereich_Objekt bo, Optional<Boolean> inDirection) {
		if (bo.contains(po)) {
			return Optional.of(Range.singleton(BigDecimal.ZERO))
		}
		val poTopPoint = new TopPoint(po)
		val boTopPoints = bo.toTopPoints.flatten
		val shortestDistances = boTopPoints.map [ point |
			inDirection.isEmpty
				? Services.topGraphService.
				findShortestDistance(poTopPoint, point)
				: Services.topGraphService.
				findShortestDistanceInDirection(poTopPoint, point,
					inDirection.get.booleanValue)
		].filter[isPresent].map[get]
		if (shortestDistances.isNullOrEmpty) {
			return Optional.empty
		}
		return Optional.of(
			Range.closed(shortestDistances.min, shortestDistances.max))
	}

	def static Range<BigDecimal> distanceToTeilBereichObjekt(Punkt_Objekt po,
		Bereich_Objekt_Teilbereich_AttributeGroup botb) {
		if (botb.contains(po.singlePoint)) {
			return Range.singleton(BigDecimal.ZERO);
		}
		val poTopPoint = new TopPoint(po)
		val distances = botb.toTopPoints.map [
			Services.topGraphService.findShortestDistance(poTopPoint, it)
		].filter[isPresent].map[get]

		if (distances.isNullOrEmpty) {
			return null
		}
		return Range.closed(distances.min, distances.max)
	}

	def static boolean existLateralDistance(Punkt_Objekt po) {
		return po.singlePoints.exists[seitlicherAbstand?.wert !== null]
	}
}
