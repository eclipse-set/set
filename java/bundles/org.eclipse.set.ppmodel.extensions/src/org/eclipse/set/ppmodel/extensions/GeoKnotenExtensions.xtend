/**
 * Copyright (c) 2015 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.ppmodel.extensions

import com.google.common.base.Predicate
import java.math.BigDecimal
import java.util.List
import java.util.Set
import org.eclipse.set.basis.Lists
import org.eclipse.set.basis.geometry.GeoPosition
import org.eclipse.set.basis.geometry.GeometryException
import org.eclipse.set.model.planpro.BasisTypen.ENUMWirkrichtung
import org.eclipse.set.model.planpro.Basisobjekte.Basis_Objekt
import org.eclipse.set.model.planpro.Geodaten.ENUMGEOKoordinatensystem
import org.eclipse.set.model.planpro.Geodaten.GEO_Kante
import org.eclipse.set.model.planpro.Geodaten.GEO_Knoten
import org.eclipse.set.model.planpro.Geodaten.GEO_Punkt
import org.eclipse.set.model.planpro.Geodaten.Strecke
import org.eclipse.set.model.planpro.Geodaten.TOP_Kante
import org.locationtech.jts.geom.Coordinate
import org.slf4j.Logger
import org.slf4j.LoggerFactory

import static java.math.BigDecimal.ZERO
import static org.eclipse.set.ppmodel.extensions.utils.Debug.*

import static extension org.eclipse.set.ppmodel.extensions.GeoKanteExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.GeoPunktExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.utils.CollectionExtensions.*

/**
 * This class extends {@link GEO_Knoten}.
 * 
 * @author Schaefer
 */
class GeoKnotenExtensions extends BasisObjektExtensions {

	static final Logger logger = LoggerFactory.getLogger(
		typeof(GeoKnotenExtensions));

	/**
	 * @param knoten this GEO Knoten
	 * 
	 * @returns the GEO-Punkte for this GEO-Knoten
	 */
	def static List<GEO_Punkt> getGeoPunkte(
		GEO_Knoten knoten
	) {
		return knoten.container.GEOPunkt.filter [
			IDGEOKnoten !== null && IDGEOKnoten?.wert == knoten.identitaet.wert
		].toList
	}

	/**
	 * @param knoten this GEO Knoten
	 * @param parentKante the Kante (Strecke, TOP_Kante, Trasse_Kante)
	 * 
	 * @returns all GEO Kante which are in line with this GEO Knoten
	 * and with the given GeoArt
	 */
	def static List<GEO_Kante> getGeoKantenOnParentKante(
		GEO_Knoten knoten,
		Basis_Objekt parentKante
	) {
		return knoten.container.GEOKante.filter [ geoKante |
			knoten.isKnoten(geoKante) && geoKante.parentKante == parentKante
		].toList
	}
	
	/**
	 * @param knoten this GEO Knoten
	 * @param geoKante the GEO Kante
	 * 
	 * @returns whether this GEO Knoten is one of the GEO Knoten of the
	 * given GEO Kante
	 */
	def static boolean isKnoten(
		GEO_Knoten knoten,
		GEO_Kante geoKante
	) {
		return geoKante.geoKnotenA == knoten || geoKante.geoKnotenB == knoten
	}

	/**
	 * @param knoten this GEO Knoten
	 * 
	 * @returns the coordinate of this GEO Knoten
	 */
	def static Coordinate getCoordinate(GEO_Knoten geoKnoten) {
		val List<GEO_Punkt> geoPunkte = geoKnoten.geoPunkte
		val GEO_Punkt geoPunkt = geoPunkte.getGeoPunkt(geoKnoten)
		return geoPunkt.coordinate
	}

	def static ENUMGEOKoordinatensystem getCRS(GEO_Knoten geoKnoten) {
		val crs = geoKnoten.geoPunkte.map [
			GEOPunktAllg?.GEOKoordinatensystem?.wert
		].toSet.uniqueOrNull
		return crs !== null
			? crs
			: ENUMGEOKoordinatensystem.ENUMGEO_KOORDINATENSYSTEM_SONSTIGE
	}

	def static GEO_Punkt getGeoPunkt(List<GEO_Punkt> geoPunkte,
		GEO_Knoten geoKnoten) {
		if (geoPunkte.size() != 1) {
			throw new GeometryException(
				String.format("Ambiguous Geo Punkte (%d) for Geo Knoten %s",
					Integer.valueOf(geoPunkte.size()),
					geoKnoten.getIdentitaet().getWert()))
		}
		return geoPunkte.get(0)
	}

	package def static GeoPosition getCoordinate(
		GEO_Knoten startGeoKnoten,
		GEO_Kante lastGeoKante,
		Basis_Objekt parentEdge,
		BigDecimal abstand,
		BigDecimal seitlicherAbstand,
		ENUMWirkrichtung wirkrichtung
	) {
		// Betrachtung Startpunkt
		var List<GEO_Kante> geoKantenOnStart
		if (parentEdge instanceof TOP_Kante) {
			geoKantenOnStart = startGeoKnoten.
				getGeoKantenOnParentKante(parentEdge).toList
		} else {
			geoKantenOnStart = startGeoKnoten.geoKanten.toList
		}
		val List<GEO_Kante> geoKantenOnStartWithoutLoops = Lists.filter(
			geoKantenOnStart,
			new Predicate<GEO_Kante>() {
				override apply(GEO_Kante input) {
					val boolean isLoop = input.isLoop
					if (isLoop) {
						logger.error(
							"Ignore GEO Kante Loop {}",
							input.getIdentitaet().getWert()
						)
					}
					return !isLoop
				}
			}
		)

		return getCoordinate(startGeoKnoten, lastGeoKante,
			geoKantenOnStartWithoutLoops, parentEdge, abstand,
			seitlicherAbstand, wirkrichtung);
	}

	private def static GeoPosition getCoordinate(
		GEO_Knoten startGeoKnoten,
		GEO_Kante lastGeoKante,
		List<GEO_Kante> geoKantenOnStart,
		Basis_Objekt parentEdge,
		BigDecimal abstand,
		BigDecimal seitlicherAbstand,
		ENUMWirkrichtung wirkrichtung
	) {
		geoKantenOnStart.remove(lastGeoKante); // don't go back
		if (geoKantenOnStart.size() != 1) {
			logger.debug("Start GEO Knoten: {}", debugString(startGeoKnoten))
			logger.debug("TOP Kante/Strecke: {}", debugString(parentEdge))
			logger.debug("Last GEO Kante: {}", debugString(lastGeoKante))
			logger.debug("Next GEO Kanten: {}", debugString(geoKantenOnStart))
			logger.debug("Abstand: {}", abstand)
			throw new GeometryException(
				String.format("No GEO Kanten continuation at %s",
					startGeoKnoten.getIdentitaet().getWert()))
		}
		val GEO_Kante geoKante = geoKantenOnStart.get(0)
		val BigDecimal geoKanteLength = geoKante.GEOKanteAllg.GEOLaenge.wert
			
		if (abstand <= geoKanteLength) {
			return geoKante.getCoordinate(startGeoKnoten, abstand,
				seitlicherAbstand, wirkrichtung);
		}
		return geoKante.getOpposite(startGeoKnoten).getCoordinate(
			geoKante,
			parentEdge,
			abstand - geoKanteLength,
			seitlicherAbstand,
			wirkrichtung
		)
	}

	/**
	 * Returns all GEO_Kanten on a TOP_Kante with their distance from a specific GEO_Knoten
	 * @param startGeoKnoten the GEO_Knoten to start from
	 * @param topKante the TOP_Kante to consider GEO_Kanten from
	 */
	def static Iterable<Pair<GEO_Kante, BigDecimal>> getGeoKantenWithDistance(
		GEO_Knoten startGeoKnoten,
		TOP_Kante topKante
	) {
		val geoKanten = topKante.container.GEOKante.filter [ k |
			k.topKante == topKante
		].toList
		return getGeoKantenWithDistance(startGeoKnoten, null, geoKanten, ZERO)
	}

	private def static Iterable<Pair<GEO_Kante, BigDecimal>> getGeoKantenWithDistance(
		GEO_Knoten startGeoKnoten,
		GEO_Kante lastGeoKante,
		List<GEO_Kante> geoKanten,
		BigDecimal distance
	) {
		// Remove previous node
		geoKanten.remove(lastGeoKante)

		// Check if all GEO_Kanten have been considered
		if (geoKanten.empty) {
			return #[]
		}

		// Find the next edge
		val edges = geoKanten.filter[geoKante|startGeoKnoten.isKnoten(geoKante)]
		if (edges.size() != 1) {
			logger.debug("Start GEO Knoten: {}", debugString(startGeoKnoten))
			logger.debug("Last GEO Kante: {}", debugString(lastGeoKante))
			logger.debug("Next GEO Kanten: {}", debugString(edges))
			throw new GeometryException(
				String.format("No GEO Kanten continuation at %s",
					startGeoKnoten.getIdentitaet().getWert()))
		}
		val GEO_Kante geoKante = edges.get(0)
		val BigDecimal geoKanteLength = geoKante.GEOKanteAllg.GEOLaenge.wert

		return #[geoKante -> distance] +
			geoKante.getOpposite(startGeoKnoten).getGeoKantenWithDistance(
				geoKante,
				geoKanten,
				distance + geoKanteLength
			)
	}

	/**
	 * @param knoten this GEO_Knoten
	 * 
	 * @returns all GEO_Kanten directly attached to this GEO_Knoten
	 */
	def static Iterable<GEO_Kante> getGeoKanten(GEO_Knoten geoKnoten) {
		return geoKnoten.container.GEOKante.filter [ kante |
			kante.IDGEOKnotenA?.wert == geoKnoten.identitaet.wert ||
				kante.IDGEOKnotenB?.wert == geoKnoten.identitaet.wert
		]
	}

	def static Set<TOP_Kante> getTopKanten(GEO_Knoten geoKnoten) {
		return geoKnoten.geoKanten.map[topKante].toSet
	}

}
