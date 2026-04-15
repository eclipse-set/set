/**
 * Copyright (c) 2021 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.siteplan.transform

import java.math.BigDecimal
import java.util.List
import org.eclipse.set.basis.geometry.GEOKanteCoordinate
import org.eclipse.set.basis.geometry.GEOKanteMetadata
import org.eclipse.set.basis.geometry.GeometryException
import org.eclipse.set.core.services.geometry.GeoKanteGeometryService
import org.eclipse.set.feature.siteplan.positionservice.PositionService
import org.eclipse.set.model.planpro.BasisTypen.ENUMWirkrichtung
import org.eclipse.set.model.planpro.Geodaten.GEO_Kante
import org.eclipse.set.model.planpro.Geodaten.GEO_Knoten
import org.eclipse.set.model.planpro.Geodaten.Strecke
import org.eclipse.set.model.siteplan.KMMarker
import org.eclipse.set.model.siteplan.RouteSection
import org.eclipse.set.model.siteplan.SiteplanFactory
import org.eclipse.set.model.siteplan.SiteplanPackage
import org.osgi.service.component.annotations.Component
import org.osgi.service.component.annotations.Reference

import static extension org.eclipse.set.ppmodel.extensions.GeoKanteExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.GeoKnotenExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.StreckeExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.StreckePunktExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.geometry.GEOKanteGeometryExtensions.*

/**
 * Transforms a Strecke from the PlanPro model to a siteplan route
 * 
 * @author Stuecker
 */
@Component(service=Transformator)
class RouteTransformator extends BaseTransformator<Strecke> {
	static val double STRECKE_KM_SPACING = 100
	@Reference
	GeoKanteGeometryService geometryService

	@Reference
	PositionService positionService

	/**
	 * Transforms a PlanPro GEO_Kante to a Siteplan track section
	 * 
	 * @param geoKante the PlanPro GEO_Kante
	 * @param startDistance the distance from the start of the TOP_Kante to the GEO_Kante
	 * @param coordinates a list of coordinates for this section
	 * @return a Siteplan route or null on failure
	 */
	private def RouteSection transform(GEO_Kante geoKante) {
		val it = SiteplanFactory.eINSTANCE.createRouteSection
		guid = geoKante.identitaet.wert
		shape = TrackTransformator.transformGeoForm(
			geoKante.GEOKanteAllg.GEOForm)
		val crs = geoKante.geoKnotenA.CRS
		positions.addAll(geoKante.geometry.coordinates.map [
			positionService.transformCoordinate(it, crs)
		])
		return it
	}

	/**
	 * Transforms a PlanPro Strecke to a Siteplan route
	 * 
	 * @param strecke the PlanPro Strecke
	 * @returns a Siteplan route or null on failure
	 */
	override void transform(Strecke strecke) {
		val track = SiteplanFactory.eINSTANCE.createRoute()
		track.guid = strecke.identitaet.wert
		try {
			strecke.geoKanten.forEach [
				try {
					track.sections.add(transform(it))
				} catch (GeometryException exc) {
					throw new RuntimeException(exc)
				}
			]
		} catch (Exception exc) {
			return;
		}
		strecke.transformKMMarkers.forEach [ marker |
			track.markers.add(marker)
		]
		track.addSiteplanElement(SiteplanPackage.eINSTANCE.siteplanState_Routes)
	}

	/**
	 * Transforms a PlanPro Strecke to a List of KMMarkers
	 * 
	 * @param strecke the PlanPro Strecke
	 * @return a Siteplan route or null on failure
	 */
	private def Iterable<KMMarker> transformKMMarkers(Strecke strecke) {
		return getStreckeKilometers(strecke).map [
			try {
				val kmMarker = SiteplanFactory.eINSTANCE.createKMMarker()
				kmMarker.position = positionService.transformPosition(it.key)
				kmMarker.value = it.value.intValue
				return kmMarker
			} catch (GeometryException exc) {
				return null
			}
		].filterNull
	}

	private def List<Pair<GEOKanteCoordinate, Double>> getStreckeKilometers(
		Strecke strecke) {
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
		var BigDecimal geoDistance = BigDecimal.valueOf(startMeter)

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
				geoKnoten, geometryService.getGeometry(geoKante), geoKnoten.CRS)

			// For every 100m on this GEO_Kante, determine the point
			while (offset <= metadata.end.doubleValue) {
				// If we've reached the end of the Strecke, return
				if (offset >= endMeter)
					return result

				// Add the point to the result
				try {
					result.add(
						geometryService.getCoordinate(metadata,
							BigDecimal.valueOf(offset), BigDecimal.ZERO,
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
}
