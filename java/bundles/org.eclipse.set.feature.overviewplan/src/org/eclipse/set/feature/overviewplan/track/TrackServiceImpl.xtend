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
package org.eclipse.set.feature.overviewplan.track

import java.util.ArrayList
import java.util.List
import org.eclipse.set.ppmodel.extensions.container.MultiContainer_AttributeGroup
import org.eclipse.set.toolboxmodel.Geodaten.TOP_Kante
import org.eclipse.set.toolboxmodel.Geodaten.TOP_Knoten
import org.osgi.service.component.annotations.Component
import org.slf4j.Logger
import org.slf4j.LoggerFactory

import static extension org.eclipse.set.ppmodel.extensions.TopKnotenExtensions.*

@Component
class TrackServiceImpl implements TrackService {

	static final Logger logger = LoggerFactory.getLogger(TrackServiceImpl)
	List<OverviewplanTrack> tracksCache = newArrayList

	override getTracksCache() {
		return tracksCache
	}

	override getTOPKanteMetaData(TOP_Kante topKante) {
		val value = MetaDataCache.getMetaData(topKante)
		if (value !== null) {
			return value
		}
		
		val metadata = new TOPKanteMetaData(topKante)
		MetaDataCache.setMetaData(topKante, metadata)
		return metadata
	}

	override getTOPKanteMetaData(List<TOP_Kante> topKanten, String guid) {
		return topKanten.findFirst[identitaet.wert === guid]?.TOPKanteMetaData
	}

	override getTOPKanteMetaData(TOP_Knoten topKnoten) {
		return topKnoten.topKanten.map[TOPKanteMetaData]
	}

	override setupTrackNet(MultiContainer_AttributeGroup container) {
		MetaDataCache.clearCache(container)
		val md = container.TOPKante.get(0)?.TOPKanteMetaData
		if (md === null) {
			return
		}
		md.defineTrack

		container.setupAnotherTrackNetz
	}

	private def boolean isMissingTOPKanteMetaData(
		MultiContainer_AttributeGroup container) {
		return container.TOPKante.exists [
			MetaDataCache.getMetaData(it) === null
		]
	}

	private def void setupAnotherTrackNetz(
		MultiContainer_AttributeGroup container) {
		if (!container.isMissingTOPKanteMetaData) {
			return
		}
		logger.warn("It give more than one track network")
		val clone = tracksCache.clone
		tracksCache = new ArrayList
		val md = container.TOPKante.findFirst [
			MetaDataCache.getMetaData(it) === null
		].TOPKanteMetaData
		md.defineTrack
		if (tracksCache.size < clone.size) {
			tracksCache = new ArrayList
			tracksCache.addAll(clone)
		}
		container.setupAnotherTrackNetz
	}

	private def OverviewplanTrack defineTrack(TOPKanteMetaData md) {
		var track = tracksCache.findFirst[topEdges.contains(md)]
		if (track !== null) {
			return track
		}

		track = new OverviewplanTrack(md)
		tracksCache.add(track)
		track.getNextTopEdge(md, md.topNodeA)
		track.getNextTopEdge(md, md.topNodeB)

		track.defineTrackSide(md, md.topNodeA)
		track.defineTrackSide(md, md.topNodeB)
		return track
	}

	private def void defineTrackSide(OverviewplanTrack track,
		TOPKanteMetaData md, TOP_Knoten topNode) {
		val isChangeLeftRight = md.getChangeLeftRightAt(topNode)
		md.getIntersectEdgeAt(topNode).forEach [ intersect |
			intersect.setChangeLeftRightAt(topNode, isChangeLeftRight)
		]
		val leftSide = md.getLeftEdgeAt(topNode)
		leftSide?.forEach [ leftIntersect |
			val lefTrack = leftIntersect.defineTrack
			track.leftTracks.put(topNode, lefTrack)
		]

		val rightSide = md.getRightEdgeAt(topNode)
		rightSide?.forEach [ rightIntersect |
			val rightTrack = rightIntersect.defineTrack
			track.rightTracks.put(topNode, rightTrack)
		]

		val continuous = md.getContinuousEdgeAt(topNode)
		if (continuous !== null) {
			track.defineTrackSide(continuous,
				continuous.getNextTopNode(topNode))
		}
	}

	private def void getNextTopEdge(OverviewplanTrack track,
		TOPKanteMetaData md, TOP_Knoten topNode) {
		if (md === null) {
			return
		}

		val continuous = md.getContinuousEdgeAt(topNode)
		if (continuous === null || track.topEdges.contains(continuous)) {
			return
		}
		track.addTrackSections(continuous)
		track.getNextTopEdge(continuous, continuous.getNextTopNode(topNode))
	}
}
