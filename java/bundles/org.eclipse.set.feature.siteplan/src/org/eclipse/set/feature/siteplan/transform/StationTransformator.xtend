/**
 * Copyright (c) 2022 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.siteplan.transform

import org.eclipse.set.feature.siteplan.positionservice.PositionService
import org.eclipse.set.feature.siteplan.trackservice.TrackService
import org.eclipse.set.model.siteplan.Platform
import org.eclipse.set.model.siteplan.SiteplanFactory
import org.eclipse.set.model.siteplan.SiteplanPackage
import org.eclipse.set.model.planpro.Bahnsteig.Bahnsteig_Anlage
import org.eclipse.set.model.planpro.Bahnsteig.Bahnsteig_Kante
import org.eclipse.set.model.planpro.BasisTypen.ENUMLinksRechts
import org.eclipse.set.model.planpro.BasisTypen.ENUMWirkrichtung
import org.osgi.service.component.annotations.Component
import org.osgi.service.component.annotations.Reference

import static extension org.eclipse.set.ppmodel.extensions.BasisAttributExtensions.*

/**
 * Transforms a Bahnsteig_Anlage from the PlanPro model to a siteplan Station
 * 
 * @author Stuecker
 */
@Component(service=Transformator)
class StationTransformator extends BaseTransformator<Bahnsteig_Anlage> {
	@Reference
	TrackService trackService

	@Reference
	PositionService positionService

	static val STATION_TRACK_DISTANCE = 1.7

	override void transform(Bahnsteig_Anlage station) {
		val result = SiteplanFactory.eINSTANCE.createStation
		result.guid = station.identitaet?.wert
		result.label = SiteplanFactory.eINSTANCE.createLabel()
		result.label.text = station.bezeichnung?.bezeichnungBahnsteigAnlage?.
			wert
		result.platforms.addAll(station.container.bahnsteigKante.filter [
			IDBahnsteigAnlage.value === station
		].map[transform])
		result.addSiteplanElement(
			SiteplanPackage.eINSTANCE.siteplanState_Stations)
	}

	def Platform transform(Bahnsteig_Kante platform) {
		val result = SiteplanFactory.eINSTANCE.createPlatform
		result.guid = platform.identitaet?.wert
		result.label = SiteplanFactory.eINSTANCE.createLabel()
		result.label.text = platform.bezeichnung?.bezeichnungBahnsteigKante?.
			wert
		platform.bereichObjektTeilbereich.forEach [ tb |
			var lateralDistance = STATION_TRACK_DISTANCE
			// Determine the side of the tracks
			if (platform.bahnsteigKanteAllg.lageZumGleis.wert ===
				ENUMLinksRechts.ENUM_LINKS_RECHTS_LINKS)
				lateralDistance = -lateralDistance
			if (tb.richtungsbezug.wert ===
				ENUMWirkrichtung.ENUM_WIRKRICHTUNG_GEGEN)
				lateralDistance = -lateralDistance

			// Find start and end points	
			val topKante = tb?.IDTOPKante?.value
			val start = trackService.getCoordinate(topKante,
				topKante?.IDTOPKnotenA?.value, tb.begrenzungA.wert.doubleValue,
				lateralDistance, tb.richtungsbezug.wert)
			val end = trackService.getCoordinate(topKante,
				topKante?.IDTOPKnotenA?.value, tb.begrenzungB.wert.doubleValue,
				lateralDistance, tb.richtungsbezug.wert)
			result.points.add(positionService.transformPosition(start))
			result.points.add(positionService.transformPosition(end))
		]
		return result
	}
}
