/**
 * Copyright (c) 2021 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.siteplan.trackservice

import java.math.BigDecimal
import java.util.ArrayList
import java.util.List
import org.eclipse.set.basis.cache.Cache
import org.eclipse.set.basis.geometry.GeometryException
import org.eclipse.set.core.services.Services
import org.eclipse.set.ppmodel.extensions.TopKanteExtensions
import org.eclipse.set.ppmodel.extensions.TopKnotenExtensions
import org.eclipse.set.toolboxmodel.BasisTypen.ENUMWirkrichtung
import org.eclipse.set.toolboxmodel.Basisobjekte.Bereich_Objekt
import org.eclipse.set.toolboxmodel.Basisobjekte.Punkt_Objekt
import org.eclipse.set.toolboxmodel.Basisobjekte.Punkt_Objekt_TOP_Kante_AttributeGroup
import org.eclipse.set.toolboxmodel.Geodaten.GEO_Kante
import org.eclipse.set.toolboxmodel.Geodaten.GEO_Knoten
import org.eclipse.set.toolboxmodel.Geodaten.Strecke
import org.eclipse.set.toolboxmodel.Geodaten.TOP_Kante
import org.eclipse.set.toolboxmodel.Geodaten.TOP_Knoten
import org.osgi.service.component.annotations.Component
import org.slf4j.Logger
import org.slf4j.LoggerFactory

import static extension org.eclipse.set.ppmodel.extensions.BasisAttributExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.GeoKanteExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.GeoKnotenExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.PunktObjektTopKanteExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.StreckeExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.StreckePunktExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.TopKanteExtensions.*

@Component
class TrackServiceImpl implements TrackService {
	// Acceptable tolerance between the length of all GEO_Kante on a TOP_Kante
	// and the length of the TOP_Kante
	static val double GEO_LENGTH_DEVIATION_TOLERANCE = 0.001
	// The same tolerance, but as a relative value based on the length of the TOP_Kante
	// a value of 0.01 is equivalent to an acceptable deviation of 1% of the TOP_Kante length
	static val double GEO_LENGTH_DEVIATION_TOLERANCE_RELATIVE = 0.0001
	// The spacing between km markers on a Strecke in meters
	static val double STRECKE_KM_SPACING = 100

	static final Logger logger = LoggerFactory.getLogger(
		typeof(TrackServiceImpl));

	override GEOKanteCoordinate getCoordinate(Punkt_Objekt punktObjekt) {
		val singlePoint = punktObjekt.punktObjektTOPKante.get(0)
		return getCoordinateAt(singlePoint, 0.0);
	}

	override GEOKanteCoordinate getCoordinateAt(Punkt_Objekt punktObjekt,
		double distance) {
		val singlePoint = punktObjekt.punktObjektTOPKante.get(0)
		return getCoordinateAt(singlePoint, distance);
	}

	override GEOKanteCoordinate getCoordinateAt(
		Punkt_Objekt_TOP_Kante_AttributeGroup singlePoint, double distance) {
		if (singlePoint === null) {
			return null
		}
		val punktDistance = singlePoint.abstand.wert.doubleValue + distance
		val direction = singlePoint.wirkrichtung?.wert
		val topKante = singlePoint.topKante

		if (topKante === null) {
			return null
		}
		val geoKante = getGeoKanteAt(topKante, topKante.TOPKnotenA,
			punktDistance);

		val lateralDistance = (singlePoint?.seitlicherAbstand?.wert ?:
			BigDecimal.ZERO).doubleValue
		if (direction === ENUMWirkrichtung.ENUM_WIRKRICHTUNG_BEIDE ||
			direction === null) {
			// For Punkt_Objekte with a bilateral or no direction fall back to ENUM_WIRKRICHTUNG_IN
			// to orient the Punkt_Objekt along the track axis
			return geoKante.getCoordinate(punktDistance, lateralDistance,
				ENUMWirkrichtung.ENUM_WIRKRICHTUNG_IN);
		}
		return geoKante.getCoordinate(punktDistance, lateralDistance,
			direction);
	}

	override GEOKanteCoordinate getCoordinate(TOP_Kante topKante,
		TOP_Knoten start, double distance, double lateralDistance,
		ENUMWirkrichtung wirkrichtung) {
		val geoKante = getGeoKanteAt(topKante, start, distance);
		return geoKante?.getCoordinate(distance, lateralDistance, wirkrichtung);
	}

	override GEOKanteMetadata getGeoKanteAt(TOP_Kante topKante,
		TOP_Knoten topKnoten, double distance) {
		val geoMetadata = getTOPKanteMetadata(topKante, topKnoten);
		return geoMetadata.stream().filter [ md |
			md.getStart() <= distance && md.getEnd() >= distance
		].findFirst().orElse(null);
	}

	/**
	 * The id of the used cache for a caching map for mapping TOP_Kante 
	 * instances (with their associated starting point) to an ordered list 
	 * of GEO_Kante with metadata information
	 */
	static final String METADATA_CACHE_ID = "toolbox.cache.siteplan.trackservice";

	private def Cache getCache() {
		// Cache objects are of type List<GEOKanteMetadata>>
		return Services.cacheService.getCache(METADATA_CACHE_ID)
	}

	/**
	 * Returns a list of GEOKanteMetadata objects for a given TOP_Kante/TOP_Knoten pair
	 * 
	 * If possible a cached value is returned, otherwise the value is calculated and added to the cache
	 * 
	 * @param topKante the TOP_Kante
	 * @param topKnoten the TOP_Knoten to start from
	 * @return a list of GEOKanteMetadata for the TOP_Kante
	 */
	def List<GEOKanteMetadata> getTOPKanteMetadata(TOP_Kante topKante,
		TOP_Knoten topKnoten) {
		val key = topKante.identitaet.wert + topKnoten.identitaet.wert;
		val value = cache.getIfPresent(key)
		if (value !== null) {
			return value as List<GEOKanteMetadata>
		}
		val bereichObjekte = topKnoten.container.bereichObjekt.toList
		val metadata = getTOPKanteMetadata(topKante, topKnoten, bereichObjekte)
		cache.set(key, metadata)
		return metadata
	}

	/**
	 * Calculates the GEOKanteMetadata objects for a given TOP_Kante
	 * 
	 * @param topKante the TOP_Kante
	 * @param topKnoten the TOP_Knoten to start from
	 * @param bereichObjekte a list of Bereich_Objekte to consider for matching to the individual GEO_Kanten
	 * @return a list of GEOKanteMetadata for the TOP_Kante
	 */
	def List<GEOKanteMetadata> getTOPKanteMetadata(TOP_Kante topKante,
		TOP_Knoten topKnoten, List<Bereich_Objekt> bereichObjekte) {

		val distanceScalingFactor = getTOPKanteScalingFactor(topKante)
		var double distance = 0;
		var geoKnoten = TopKnotenExtensions.getGEOKnoten(topKnoten);
		var GEO_Kante geoKante = null;
		val geoKanteMetadata = new ArrayList<GEOKanteMetadata>();

		// Traverse the TOP_Kante from the first GEO_Knoten along until the end of the TOP_Kante is reached
		while (true) {
			val List<GEO_Kante> geoKanten = geoKnoten.
				getGeoKantenOnTopKante(topKante);
			geoKanten.remove(geoKante);
			if (geoKanten.empty) {
				// If there was exactly one GEO_Kante, then we've reached the end of the TOP_Kante
				return geoKanteMetadata
			}
			geoKante = geoKanten.get(0)
			// Adjust the length of the GEO_Kante on the TOP_Kante
			val geoKanteLength = geoKante.GEOKanteAllg.GEOLaenge.wert.
				doubleValue * (1 / distanceScalingFactor)
			geoKanteMetadata.add(
				new GEOKanteMetadata(geoKante, distance, geoKanteLength,
					bereichObjekte, topKante, geoKnoten));
			distance += geoKanteLength

			// Get the next GEO_Knoten (on the other end of the GEO_Kante)
			geoKnoten = geoKante.getOpposite(geoKnoten);
		}
	}

	private static def double getTOPKanteScalingFactor(TOP_Kante topKante) {
		// In some planning data there is a minor deviation between the length of a 
		// TOP_Kante and the total length of all GEO_Kanten on the TOP_Kante
		// As objects are positioned on a TOP_Kante but a GEO_Kante is used
		// to determine the geographical position, calculate a scaling factor
		val geoKantenOnTopKante = TopKanteExtensions.getGeoKanten(topKante)
		val geoLength = geoKantenOnTopKante.fold(0.0, [ l, k |
			l + k.GEOKanteAllg.GEOLaenge.wert.doubleValue
		])
		val topLength = topKante.TOPKanteAllg.TOPLaenge.wert.doubleValue
		val difference = Math.abs(geoLength - topLength)
		val tolerance = Math.max(GEO_LENGTH_DEVIATION_TOLERANCE,
			topLength * GEO_LENGTH_DEVIATION_TOLERANCE_RELATIVE)
		// Warn if the length difference is too big
		if (difference > tolerance) {
			logger.debug("lengthTopKante={}", Double.valueOf(topLength))
			logger.debug("lengthGeoKanten={}", Double.valueOf(geoLength))
			logger.debug(
				"geoKantenOnTopKante={}",
				Integer.valueOf(geoKantenOnTopKante.size())
			)
			logger.warn(
				"Difference of GEO Kanten length and TOP Kante length for TOP Kante {} greater than tolerance {} ({}).",
				topKante.getIdentitaet().getWert(),
				Double.valueOf(tolerance),
				Double.valueOf(difference)
			);
		}
		return geoLength / topLength
	}

	override getGeoKanten(TOP_Kante topKante) {
		return getTOPKanteMetadata(topKante, topKante.TOPKnotenA)
	}

	override getStreckeKilometers(Strecke strecke) {
		val startEnd = strecke.startEnd
		if (startEnd === null)
			return #[]

		val start = startEnd.get(0)
		val end = startEnd.get(1)

		val startMeter = start.streckeMeter.wert.intValue
		val endMeter = end.streckeMeter.wert.intValue
		// Determine next multiple of STRECKE_KM_SPACING  
		var offset = startMeter +
			(STRECKE_KM_SPACING - startMeter % STRECKE_KM_SPACING)

		val result = newArrayList
		var GEO_Kante geoKante = null
		var GEO_Knoten geoKnoten = start.geoKnoten
		var double geoDistance = startMeter

		// Traverse the GEO_Kanten on this Strecke
		while (true) {
			val List<GEO_Kante> geoKanten = geoKnoten.geoKanten.toList
			geoKanten.remove(geoKante)
			if (geoKanten.empty) {
				// If there was exactly one GEO_Kante, then we've reached the end of the Strecke
				return result
			}
			geoKante = geoKanten.get(0)
			// Find the metadata for the current GEO_Kante
			val metadata = new GEOKanteMetadata(geoKante, geoDistance,
				geoKnoten)

			// For every 100m on this GEO_Kante, determine the point
			while (offset <= metadata.end) {
				// If we've reached the end of the Strecke, return
				if (offset >= endMeter)
					return result

				// Add the point to the result
				try {
					result.add(
						metadata.getCoordinate(offset, 0.0,
							ENUMWirkrichtung.ENUM_WIRKRICHTUNG_IN) -> offset)
					offset += STRECKE_KM_SPACING
				} catch (GeometryException exc) {
					// Try creating the next point
					offset += STRECKE_KM_SPACING
				}
			}

			// Get the next GEO_Knoten (on the other end of the GEO_Kante)			
			geoDistance += metadata.length
			geoKnoten = geoKante.getOpposite(geoKnoten)
		}
	}

	override clearMetaData() {
		cache.invalidate()
	}

}
