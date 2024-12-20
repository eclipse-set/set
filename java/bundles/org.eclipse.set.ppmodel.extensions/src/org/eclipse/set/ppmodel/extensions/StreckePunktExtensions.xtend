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
import org.eclipse.set.model.planpro.Geodaten.GEO_Knoten
import org.eclipse.set.model.planpro.Geodaten.Strecke_Punkt
import org.locationtech.jts.geom.Coordinate

import static extension org.eclipse.set.ppmodel.extensions.GeoKnotenExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.GeoKanteExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.utils.IterableExtensions.*
import org.eclipse.set.core.services.Services
import org.eclipse.set.model.planpro.Geodaten.Strecke

/**
 * Extensions for {@link Strecke_Punkt}.
 * 
 * @author Stuecker
 */
class StreckePunktExtensions extends BasisObjektExtensions {
	/**
	 * @param routePoint a Strecke_Punkt
	 * @returns the GEO_Knoten for the route point
	 */
	static def GEO_Knoten getGeoKnoten(Strecke_Punkt routePoint) {
		return routePoint.IDGEOKnoten?.value
	}

	static def Coordinate getStreckPunktCoordinate(Strecke_Punkt routePoint) {
		return routePoint.geoKnoten.coordinate
	}

	static def BigDecimal getStreckePunktTopDistance(Strecke_Punkt routePoint) {
		val geoKnoten = routePoint.geoKnoten
		val geoKanten = routePoint.container.GEOKante.filter [
			parentKante instanceof Strecke &&
				parentKante === routePoint.IDStrecke.value
		].filter[geoKnotenA === geoKnoten || geoKnotenB === geoKnoten].
			firstOrNull
		if (geoKanten === null) {
			return null
		}
		val metadata = Services.geometryService.getGeoKanteMetaData(geoKanten)
		if (geoKanten.geoKnotenA === geoKnoten) {
			return metadata.start
		}

		if (geoKanten.geoKnotenB === geoKnoten) {
			return metadata.end
		}
		throw new IllegalArgumentException(
			"Route point isn't reference to GEO_Knoten of a GEO_Kante")
	}
}
