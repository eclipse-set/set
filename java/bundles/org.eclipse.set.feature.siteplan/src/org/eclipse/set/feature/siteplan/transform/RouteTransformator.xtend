/**
 * Copyright (c) 2021 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.siteplan.transform

import org.eclipse.set.basis.geometry.GeometryException
import org.eclipse.set.feature.siteplan.positionservice.PositionService
import org.eclipse.set.feature.siteplan.trackservice.TrackService
import org.eclipse.set.model.siteplan.KMMarker
import org.eclipse.set.model.siteplan.RouteSection
import org.eclipse.set.model.siteplan.SiteplanFactory
import org.eclipse.set.model.siteplan.SiteplanPackage
import org.eclipse.set.toolboxmodel.Geodaten.GEO_Kante
import org.eclipse.set.toolboxmodel.Geodaten.Strecke
import org.osgi.service.component.annotations.Component
import org.osgi.service.component.annotations.Reference

import static extension org.eclipse.set.ppmodel.extensions.GeoKanteExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.StreckeExtensions.*

/**
 * Transforms a Strecke from the PlanPro model to a siteplan route
 * 
 * @author Stuecker
 */
@Component(service=Transformator)
class RouteTransformator extends BaseTransformator<Strecke> {
	@Reference
	TrackService trackService

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
		val crs = geoKante.CRS
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
		return trackService.getStreckeKilometers(strecke).map [
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

}
