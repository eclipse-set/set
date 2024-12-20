/**
 * Copyright (c) 2021 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.ppmodel.extensions

import java.math.BigDecimal
import java.util.List
import org.eclipse.set.basis.geometry.GeoPosition
import org.eclipse.set.model.planpro.BasisTypen.ENUMWirkrichtung
import org.eclipse.set.model.planpro.Geodaten.GEO_Kante
import org.eclipse.set.model.planpro.Geodaten.Strecke
import org.eclipse.set.model.planpro.Geodaten.Strecke_Punkt

import static extension org.eclipse.set.ppmodel.extensions.GeoKnotenExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.StreckePunktExtensions.*
import org.locationtech.jts.geom.Coordinate

/**
 * Extensions for {@link Strecke}.
 * 
 * @author Stuecker
 */
class StreckeExtensions extends BasisObjektExtensions {
	/**
	 * @param Strecke the route to consider
	 * @return all GEO_Kanten of the route
	 */
	def static List<GEO_Kante> getGeoKanten(
		Strecke strecke
	) {
		return strecke.container.GEOKante.filter [ k |
			k.IDGEOArt?.wert == strecke.identitaet.wert
		].toList
	}

	/**
	 * @param Strecke the route to consider
	 * @return all Strecke_Punkt of the route 
	 */
	def static Iterable<Strecke_Punkt> getStreckenPunkte(Strecke strecke) {
		return strecke.container.streckePunkt.filter [ k |
			k.IDStrecke?.wert == strecke.identitaet.wert
		]
	}

	def static GeoPosition getKilometerCoordinate(Strecke strecke,
		BigDecimal kilometer) {

		val startEnd = strecke.startEnd
		if (startEnd === null)
			return null

		val start = startEnd.get(0)

		return start.geoKnoten.getCoordinate(
			null,
			strecke,
			kilometer - start.streckeMeter.wert,
			BigDecimal.ZERO,
			ENUMWirkrichtung.ENUM_WIRKRICHTUNG_IN
		)
	}

	/**
	 * @param Strecke the route to consider
	 * @return an array containing the start and end points of the route 
	 * 		   or null. The array contains exactly two Strecke_Punkt #[start, end]
	 */
	def static Strecke_Punkt[] getStartEnd(Strecke strecke) {
		// End points of a Strecke are specified to have exactly
		// one outgoing GEO_Kante
		val startEnd = strecke.streckenPunkte.filter [ sp |
			val edges = sp?.geoKnoten?.geoKanten
			return edges !== null && edges.size == 1
		]

		if (startEnd.size < 2)
			return null

		val pointA = startEnd.get(0)
		val pointB = startEnd.get(1)

		if (pointA.streckeMeter.wert < pointB.streckeMeter.wert)
			return startEnd
		else
			return #[pointB, pointA]
	}

	def static BigDecimal getStreckeLength(Strecke strecke) {
		return strecke.bereichObjektTeilbereich.map [
			begrenzungB.wert - begrenzungA.wert
		].reduce[p1, p2|p1 + p2]
	}
}
