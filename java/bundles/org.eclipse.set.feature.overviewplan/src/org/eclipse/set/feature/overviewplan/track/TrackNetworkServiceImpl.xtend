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
import java.util.Collections
import java.util.List
import org.eclipse.set.ppmodel.extensions.container.MultiContainer_AttributeGroup
import org.eclipse.set.model.planpro.Geodaten.TOP_Kante
import org.eclipse.set.model.planpro.Geodaten.TOP_Knoten
import org.osgi.service.component.annotations.Component
import org.slf4j.Logger
import org.slf4j.LoggerFactory

import static extension org.eclipse.set.ppmodel.extensions.TopKnotenExtensions.*
import org.apache.commons.lang3.Range
import java.util.Set

@Component
class TrackNetworkServiceImpl implements TrackNetworkService {

	static final Logger logger = LoggerFactory.getLogger(
		TrackNetworkServiceImpl)
	List<OverviewplanTrack> tracksCache = newArrayList
	TopKnotenPosition topKnotenPosition

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
		return topKanten.findFirst[identitaet?.wert == guid]?.TOPKanteMetaData
	}

	override getTOPKanteMetaData(TOP_Knoten topKnoten) {
		return topKnoten.topKanten.map[TOPKanteMetaData]
	}

	override setupTrackNet(MultiContainer_AttributeGroup container) {
		val md = container.TOPKante.get(0)?.TOPKanteMetaData
		if (md === null) {
			return
		}
		val firstTrack = md.defineTrack(0)
		firstTrack.fixedLvl = true
		if (container.isMissingTOPKanteMetaData) {
			logger.warn("This model give more than one gleis network")
			container.setupAnotherTrackNet
		}
		val topEdges = container.TOPKante.map[MetaDataCache.getMetaData(it)].
			filterNull.toList
		val topGraph = new TopKanteDirectedGraph
		tracksCache.forEach[track|track.topEdges.forEach[topGraph.addEdge(it)]]
		topEdges.forEach[topGraph.findTopKanteLength(it)]

		topKnotenPosition = new TopKnotenPosition(this,
			topEdges.get(0).topNodeA)
		if (tracksCache.size === 1) {
			return
		}
		defineTrackLvl
	}

	private def boolean isMissingTOPKanteMetaData(
		MultiContainer_AttributeGroup container) {
		return container.TOPKante.exists [
			MetaDataCache.getMetaData(it) === null
		]
	}

	private def void setupAnotherTrackNet(
		MultiContainer_AttributeGroup container) {
		if (!container.isMissingTOPKanteMetaData) {
			return
		}
		logger.warn("It give more than one track network")
		val clone = tracksCache.clone
		val cloneMd = MetaDataCache.getAllMetaData(container)
		tracksCache = new ArrayList
		MetaDataCache.clearCache(container)
		val md = container.TOPKante.findFirst [
			MetaDataCache.getMetaData(it) === null
		].TOPKanteMetaData
		md.defineTrack(0)
		val newMetadataSet = MetaDataCache.getAllMetaData(container)

		// When another track network fewer track and edge than track network from before,
		// then restore track network from before
		if (tracksCache.size < clone.size &&
			newMetadataSet.size < cloneMd.size) {
			tracksCache = new ArrayList
			tracksCache.addAll(clone)
			MetaDataCache.clearCache(container)
			cloneMd.forEach[MetaDataCache.setMetaData(topEdge, it)]
		}
	}

	private def OverviewplanTrack defineTrack(TOPKanteMetaData md, int lvl) {
		var track = tracksCache.findFirst[topEdges.contains(md)]

		if (track !== null) {
			return track
		}

		track = new OverviewplanTrack(md)
		track.lvl = lvl
		tracksCache.add(track)

		track.defineTrackSide(md, md.topNodeA)
		track.defineTrackSide(md, md.topNodeB)
		return track
	}

	/**
	 * Find out all connect track lie on left or right side of the track
	 */
	private def void defineTrackSide(OverviewplanTrack track,
		TOPKanteMetaData md, TOP_Knoten topNode) {
		val isChangeLeftRight = md.getChangeLeftRightAt(topNode)
		md.getIntersectEdgeAt(topNode).forEach [ intersect |
			intersect.setChangeLeftRightAt(topNode, isChangeLeftRight)
		]
		val leftSide = md.getLeftEdgeAt(topNode)
		leftSide?.forEach [ leftIntersect |
			val leftTrack = leftIntersect.defineTrack(track.lvl + 1)
			if (track.lvl + 1 > leftTrack.lvl) {
				leftTrack.lvl = track.lvl + 1
			}
			leftTrack.rightTracks.put(topNode, track)
			track.leftTracks.put(topNode, leftTrack)
		]

		val rightSide = md.getRightEdgeAt(topNode)
		rightSide?.forEach [ rightIntersect |
			val rightTrack = rightIntersect.defineTrack(track.lvl - 1)
			if (track.lvl - 1 < rightTrack.lvl) {
				rightTrack.lvl = track.lvl - 1
			}

			rightTrack.leftTracks.put(topNode, track)
			track.rightTracks.put(topNode, rightTrack)
		]

		val continuous = md.getContinuousEdgeAt(topNode)
		if (continuous !== null) {
			track.defineTrackSide(continuous,
				continuous.getNextTopNode(topNode))
		}
	}

	private def void defineTrackLvl() {
		val topNodeHorizontalCoors = topKnotenPosition.
			findAllTOPNodeHorizontalCoor
		val tracksPositionRange = tracksCache.map [
			val nodePositions = topNodes.map [ node |
				topNodeHorizontalCoors.get(node)
			]
			if (nodePositions.nullOrEmpty || nodePositions.contains(null)) {
				println("TEST")
			}
			return new Pair(it, Range.of(nodePositions.min, nodePositions.max))
		]
		tracksPositionRange.forEach [ current |
			current.defineTrackLvl(tracksPositionRange.filter [
				it.key !== current.key && it.key.lvl * current.key.lvl >= 0
			].toList)
		]
	}

	/**
	 * Define track level through find and fix collistion between tracks
	 */
	private def void defineTrackLvl(
		Pair<OverviewplanTrack, Range<Double>> current,
		List<Pair<OverviewplanTrack, Range<Double>>> compareTracks) {

		val tmpLvl = current.key.lvl
		val collisionsTracks = compareTracks.map [
			new Pair(it, current.detectCollision(it))
		].filter[value !== CollisionType.NONE].toList
		collisionsTracks.forEach [
			val collisionTrack = key
			val collisionType = value
			if (current.key.lvl !== tmpLvl) {
				return
			}
			val difference = Math.abs(collisionTrack.key.lvl -
				current.key.lvl) + 1
			switch (collisionType) {
				case LEFT: {
					if (!collisionTrack.key.changeTrackLvl(difference, true)) {
						current.key.changeTrackLvl(difference, false)
					}
				}
				case RIGHT: {
					if (!collisionTrack.key.changeTrackLvl(difference, false)) {
						current.key.changeTrackLvl(difference, true)
					}
				}
				// When one track lie in another track,
				// then change level outer track, when this track level is not fixed
				case CONTAINS: {
					if (current.value.containsRange(collisionTrack.value)) {
						if (!current.key.changeTrackLvl(difference, true)) {
							collisionTrack.key.changeTrackLvl(difference, false)
						}
					} else {
						if (!collisionTrack.key.changeTrackLvl(difference,
							true)) {
							current.key.changeTrackLvl(difference, false)
						}
					}
				}
				case OVERLAP: {
					// When two track overlap,
					// then change the track without side track in direction
					// of change level (when increase then look at left side tracks,
					// when decrease then look at right side tracks).
					// Else change level one of them
					val emptySideTrack = #[current.key, collisionTrack.key].
						findFirst [
							current.key.lvl > 0 ? leftTracks.
								empty : rightTracks.empty
						]
					if (emptySideTrack !== null) {
						var fixed = false
						if (current.key === emptySideTrack) {
							fixed = collisionTrack.key.
								changeTrackLvl(difference, true)
						} else {
							fixed = current.key.changeTrackLvl(difference, true)
						}
						if (fixed) {
							return
						}
					}
					if (!collisionTrack.key.changeTrackLvl(difference, true)) {
						current.key.changeTrackLvl(difference, true)
					}
				}
				default:
					return
			}
		]

		if (current.key.lvl !== tmpLvl) {
			current.defineTrackLvl(compareTracks)
		}
	}

	/**
	 * When a track change level, then check all side tracks levvel
	 * whether still relevant, when not then increase/decrease level of the track 
	 */
	private def boolean changeTrackLvl(OverviewplanTrack track, int value,
		boolean isIncrease) {
		if (track.fixedLvl) {
			return false
		}
		track.lvl += value * (isIncrease ? 1 : -1)
		val nextSideTracks = isIncrease ? track.leftTracks : track.rightTracks
		if (!nextSideTracks.values.forall [
			isIncrease ? lvl > track.lvl : lvl < track.lvl
		]) {
			val sideTracks = track.getSideTracks(isIncrease)
			sideTracks.forEach[lvl += value * (isIncrease ? 1 : -1)]
		}
		return true
	}

	/**
	 * Check if two track are collision
	 */
	private def CollisionType detectCollision(
		Pair<OverviewplanTrack, Range<Double>> source,
		Pair<OverviewplanTrack, Range<Double>> target) {
		val leftTracks = source.key.getSideTracks(true)
		val rightTracks = source.key.getSideTracks(false)

		// Level of left track small or equals this track
		if (leftTracks.contains(target.key) &&
			target.key.lvl <= source.key.lvl) {
			return CollisionType.LEFT
		}

		// Level of right track greater or equals this track
		if ((rightTracks.contains(target.key) &&
			target.key.lvl >= source.key.lvl)) {
			return CollisionType.RIGHT
		}
		val sourceRange = source.value
		val targetRange = target.value

		// Not consider if both of track not lie on same side with level 0 track
		if (source.key.lvl * target.key.lvl < 0) {
			return CollisionType.NONE
		}

		val isTargetLvlGreater = Math.abs(source.key.lvl) <=
			Math.abs(target.key.lvl)

		// When both of tracks have same start and end point
		if (sourceRange.isStartedBy(targetRange.minimum) &&
			sourceRange.isEndedBy(targetRange.maximum)) {
			return source.key.lvl === target.key.lvl ? CollisionType.
				CONTAINS : CollisionType.NONE
		}

		// When the target track lie complete in source track,
		// so sourceTrack.minimun < targetTrack.minimum and targetTrack.maximum < sourTrack.maximum
		// and the target track level is greate than source track, when source track level > 0
		// or the target track level is smaller than source track, when source track level < 0
		if (sourceRange.containsRange(targetRange) &&
			(source.key.lvl > 0 ? !leftTracks.contains(
				target.key) : !rightTracks.contains(target.key)) &&
			isTargetLvlGreater) {
			return CollisionType.CONTAINS
		}

		// Opposite from over condition, source track lie complete in targetTrack
		if (targetRange.containsRange(sourceRange) && (source.key.lvl > 0
			? !rightTracks.contains(target.key)
			: !leftTracks.contains(target.key)) && !isTargetLvlGreater) {
			return CollisionType.CONTAINS
		}

		// When exsist minimum one point of target track lie on source track
		if ((sourceRange.isOverlappedBy(targetRange)) &&
			source.key.lvl == target.key.lvl) {
			return CollisionType.OVERLAP
		}
		return CollisionType.NONE
	}

	private enum CollisionType {
		// When track A is left track of track B and A.lvl < B.lvl
		LEFT,
		// When track A is right track of track B and A.lvl > B.lvl
		RIGHT,
		// When start and end of track A lie inside track B and A.lvl > B.lvl
		OVERLAP,
		// When start or end of track A lie instide track B and A.lvl = B.lvl
		CONTAINS,
		NONE
	}

	/**
	 * Get all tracks lie on left or right side of a track
	 */
	private def Set<OverviewplanTrack> getSideTracks(OverviewplanTrack mdTrack,
		boolean isLeftSide) {
		val sideTrack = isLeftSide ? mdTrack.leftTracks : mdTrack.rightTracks
		if (sideTrack.values.empty) {
			return Collections.emptySet
		}
		val result = newHashSet
		result.addAll(sideTrack.values)
		sideTrack.values.forEach [
			result.addAll(getSideTracks(isLeftSide))
		]
		return result
	}

	override getTrack(TOPKanteMetaData md) {
		return tracksCache.findFirst[topEdges.contains(md)]
	}

	override clean(MultiContainer_AttributeGroup container) {
		MetaDataCache.clearCache(container)
		tracksCache = newArrayList
		topKnotenPosition = null
	}

	override getTOPNodePosition(TOP_Knoten node, OverviewplanTrack track) {
		return topKnotenPosition.getTopNodePosition(node, track)
	}

}
