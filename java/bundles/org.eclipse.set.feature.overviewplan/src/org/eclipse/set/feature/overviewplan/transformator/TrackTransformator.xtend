/**
 * Copyright (c) 2023 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.overviewplan.transformator

import org.eclipse.set.feature.overviewplan.service.TOPKanteMetaData
import org.eclipse.set.feature.overviewplan.service.TrackService
import org.eclipse.set.feature.siteplan.transform.BaseTransformator
import org.eclipse.set.feature.siteplan.transform.Transformator
import org.eclipse.set.model.siteplan.SiteplanFactory
import org.eclipse.set.model.siteplan.Track
import org.eclipse.set.toolboxmodel.Geodaten.TOP_Kante
import org.eclipse.set.toolboxmodel.Geodaten.TOP_Knoten
import org.osgi.service.component.annotations.Component
import org.osgi.service.component.annotations.Reference

@Component(service=Transformator)
class TrackTransformator extends BaseTransformator<TOP_Kante> {

	@Reference
	TrackService trackService

	override transform(TOP_Kante topKante) {
		val track = SiteplanFactory.eINSTANCE.createTrack()
		if (state.tracks.flatMap[sections].exists [
			guid === topKante.identitaet.wert
		]) {
			return
		}
		val md = trackService.getTOPKanteMetaData(topKante)
		#[md.topNodeA, md.topNodeB].forEach [
			track.transformSection(md, it)
		]

		track.guid = topKante.identitaet.wert
		state.tracks.add(track)

	}

	private def void transformSection(Track track, TOPKanteMetaData md,
		TOP_Knoten node) {
		if (md === null) {
			return
		}
		val section = SiteplanFactory.eINSTANCE.createTrackSection
		section.guid = md.topEdge.identitaet.wert

		track.sections.add(section)

		val alreadyAddSection = track.sections.map[guid]
		val connectEdges = md.getIntersectEdgeAt(node)
		if (connectEdges.empty || connectEdges.exists [
			alreadyAddSection.contains(topEdge.identitaet.wert)
		]) {
			return
		}
		val continuosEdge = md.getContinuousEdgeAt(node)
		transformSection(track, continuosEdge, md.getNextTopNode(node))
	}
}
