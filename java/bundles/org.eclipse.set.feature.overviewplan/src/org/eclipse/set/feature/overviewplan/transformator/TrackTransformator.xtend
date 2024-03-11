/**
 * Copyright (c) 2024 DB InfraGO AG and others
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 */
package org.eclipse.set.feature.overviewplan.transformator

import java.util.Map
import org.apache.commons.lang3.Range
import org.eclipse.set.feature.overviewplan.track.OverviewplanTrack
import org.eclipse.set.feature.overviewplan.track.TOPKanteMetaData
import org.eclipse.set.feature.overviewplan.track.TrackNetworkService
import org.eclipse.set.feature.siteplan.SiteplanConstants
import org.eclipse.set.feature.siteplan.transform.BaseTransformator
import org.eclipse.set.feature.siteplan.transform.Transformator
import org.eclipse.set.model.siteplan.Position
import org.eclipse.set.model.siteplan.SiteplanFactory
import org.eclipse.set.model.siteplan.Track
import org.eclipse.set.toolboxmodel.Geodaten.TOP_Kante
import org.osgi.service.component.annotations.Component
import org.osgi.service.component.annotations.Reference

@Component(service=Transformator)
class TrackTransformator extends BaseTransformator<TOP_Kante> {

	@Reference
	TrackNetworkService trackService
	static Map<OverviewplanTrack, Track> mdToTrack = newHashMap
	static double edge_length_factor = 10000
	static double track_lvl_distance_factor = 1000

	override transform(TOP_Kante topKante) {
		if (state.tracks.flatMap[sections].exists [
			guid === topKante.identitaet.wert
		]) {
			return
		}
		val md = trackService.getTOPKanteMetaData(topKante)
		md.transformTrack

		state.tracks.flatMap[sections].indexed.forEach [
			var color = SiteplanConstants.TOP_KANTEN_COLOR.get(value.guid)
			if (color === null) {
				color = '''hsl(«(key + 1) * 137.5», 100%, 65%)'''
				SiteplanConstants.TOP_KANTEN_COLOR.put(value.guid, color)
			}
			value.color = color
		]
	}

	private def void transformTrack(TOPKanteMetaData md) {
		val mdTrack = trackService.tracksCache.findFirst[topEdges.contains(md)]
		if (mdTrack === null) {
			throw new IllegalArgumentException('''Doesn't exist the track contain the TOP_Kante: «md.topEdge.identitaet.wert»''')
		}
		if (mdToTrack.containsKey(mdTrack)) {
			return
		}
		val track = SiteplanFactory.eINSTANCE.createTrack
		track.guid = '''«state.tracks.size + 1»'''
		state.tracks.add(track)
		mdToTrack.put(mdTrack, track)
		mdTrack.topEdges.forEach[createTrackSection(track, mdTrack)]
	}

	private def void createTrackSection(TOPKanteMetaData md, Track track,
		OverviewplanTrack mdTrack) {
		val section = SiteplanFactory.eINSTANCE.createTrackSection
		section.guid = md.topEdge.identitaet.wert
		track.sections.add(section)
		#[md.topNodeA, md.topNodeB].forEach [
			val segment = SiteplanFactory.eINSTANCE.createTrackSegment
			segment.guid = identitaet.wert
			val nodePosition = trackService.getTOPNodePosition(it, mdTrack)
			segment.positions.add(createPosition(nodePosition.x, nodePosition.y))
			// Create extra point at connect node to
			//  two track connect with a digonal line (45 rad)
			if (nodePosition.y !== mdTrack.lvl) {
				val nextNodePosition = trackService.getTOPNodePosition(
					md.getNextTopNode(it), mdTrack)
				val connectTrack = md.getIntersectEdgeAt(it).map [ intersect |
					trackService.getTrack(intersect)
				].toSet
				if (connectTrack.size > 1) {
					throw new IllegalArgumentException('''By TOP_Knoten: «identitaet.wert» exist more than two track''')
				}
				val edgeRange = Range.of(nodePosition.x, nextNodePosition.x)
				val offSetX = Math.abs(nodePosition.y - mdTrack.lvl)/10
				var transformX = nodePosition.x + offSetX
				if (!edgeRange.contains(transformX)) {
					transformX = nodePosition.x - offSetX
				}
				segment.positions.add(
					createPosition(transformX,
						mdTrack.lvl))
			}
			section.segments.add(segment)
		]
	}
	
	private def Position createPosition(double x, double y) {
		val position = SiteplanFactory.eINSTANCE.createPosition
		position.x = x * edge_length_factor
		position.y = y * track_lvl_distance_factor
		return position
	}
}
