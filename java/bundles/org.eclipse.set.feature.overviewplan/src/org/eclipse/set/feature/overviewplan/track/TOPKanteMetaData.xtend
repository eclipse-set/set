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

import java.util.Collections
import java.util.List
import java.util.Map
import org.eclipse.set.toolboxmodel.Geodaten.ENUMGEOForm
import org.eclipse.set.toolboxmodel.Geodaten.ENUMTOPAnschluss
import org.eclipse.set.toolboxmodel.Geodaten.GEO_Kante
import org.eclipse.set.toolboxmodel.Geodaten.TOP_Kante
import org.eclipse.set.toolboxmodel.Geodaten.TOP_Knoten
import org.eclipse.set.toolboxmodel.Weichen_und_Gleissperren.W_Kr_Gsp_Komponente

import static org.eclipse.set.toolboxmodel.Geodaten.ENUMTOPAnschluss.*

import static extension org.eclipse.set.ppmodel.extensions.GeoKanteExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.TopKanteExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.TopKnotenExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.WKrGspElementExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.PunktObjektExtensions.*
import static extension org.eclipse.set.utils.collection.MapExtensions.*
import static org.eclipse.set.feature.overviewplan.track.MetaDataCache.*

class TOPKanteMetaData {
	TOP_Kante topEdge
	TOP_Knoten topNodeA
	TOP_Knoten topNodeB
	Map<TOP_Knoten, List<TOPKanteMetaData>> intersectTopEdges = newHashMap
	Map<TOP_Knoten, TOPKanteMetaData> continuousTopEdge = newHashMap
	Map<TOP_Knoten, List<TOPKanteMetaData>> leftTopEdge = newHashMap
	Map<TOP_Knoten, List<TOPKanteMetaData>> rightTopEdge = newHashMap
	Map<TOP_Knoten, Boolean> changeLeftRightNode = newHashMap
	
	new(TOP_Kante topKante) {
		this.topEdge = topKante
		topNodeA = topKante?.IDTOPKnotenA
		topNodeB = topKante?.IDTOPKnotenB
		changeLeftRightNode.put(topNodeA, false)
		changeLeftRightNode.put(topNodeB, isChangeLeftRightEdge ? true : false)
	}

	def TOP_Kante getTopEdge() {
		return topEdge
	}

	def TOP_Knoten getTopNodeA() {
		return topNodeA
	}

	def TOP_Knoten getTopNodeB() {
		return topNodeB
	}

	def TOP_Knoten getNextTopNode(TOP_Knoten topNode) {
		if (topNode !== topNodeA && topNode !== topNodeB) {
			throw new IllegalArgumentException('''TOP_Knoten: «topNode.identitaet.wert» doesn't belong to TOP_Katen: «topEdge.identitaet.wert»''')
		}
		return #[topNodeA, topNodeB].findFirst[it !== topNode]
	}

	def ENUMTOPAnschluss getTopConnectorAt(TOP_Knoten topNode) {
		return topEdge?.getTOPAnschluss(topNode)
	}

	def Boolean getChangeLeftRightAt(TOP_Knoten topNode) {
		return changeLeftRightNode.get(topNode)
	}

	def void setChangeLeftRightAt(TOP_Knoten node, boolean value) {
		changeLeftRightNode.put(node, value)
		val nextNode = node.nextTopNode
		changeLeftRightNode.put(nextNode,
			isChangeLeftRightEdge ? !value : value)
	}

	def List<Pair<TOP_Knoten, TOPKanteMetaData>> getContinuousEdges() {
		return getEdges[continuousEdgeAt].filter[value !== null].toList
	}

	def TOPKanteMetaData getContinuousEdgeAt(TOP_Knoten topNode) {
		return continuousTopEdge.getValue(topNode, [topNode.continuousEdge])
	}

	def List<Pair<TOP_Knoten, List<TOPKanteMetaData>>> getLeftEdges() {
		return getEdges[leftEdgeAt].filter [
			!value.nullOrEmpty
		].toList
	}

	def List<TOPKanteMetaData> getLeftEdgeAt(TOP_Knoten topNode) {
		return leftTopEdge.getValue(topNode, [
			topNode.defineLeftRightEdge
			return leftTopEdge.get(topNode)
		])
	}

	def List<Pair<TOP_Knoten, List<TOPKanteMetaData>>> getRightEdges() {
		return getEdges[rightEdgeAt].filter [
			!value.nullOrEmpty
		].toList
	}

	private def <T> List<Pair<TOP_Knoten, T>> getEdges(
		(TOP_Knoten)=>T getFunction) {
		return #[topNodeA, topNodeB].map[new Pair(it, getFunction.apply(it))]
	}

	def List<TOPKanteMetaData> getRightEdgeAt(TOP_Knoten topNode) {
		return rightTopEdge.getValue(topNode, [
			topNode.defineLeftRightEdge
			return rightTopEdge.get(topNode)
		])
	}

	def List<TOPKanteMetaData> getIntersectEdgeAt(TOP_Knoten topNode) {
		return intersectTopEdges.getValue(topNode, [topNode.interstectEdges])
	}

	private def List<TOPKanteMetaData> getInterstectEdges(TOP_Knoten topNode) {
		val intersectKanten = topNode?.topKanten?.filter[it !== topEdge].map [
				val value = getMetaData(it)
				if (value !== null) {
					return value
				}
				val metadata = new TOPKanteMetaData(it)
				setMetaData(it, metadata)
				return metadata
		].toList
		if (intersectKanten.size < 3) {
			intersectTopEdges.put(topNode, intersectKanten)
			return intersectKanten
		}
		throw new IllegalArgumentException('''TOP_Knoten: «topNode.identitaet.wert» reference to more than three TOP_Kanten''')
	}

	private def TOPKanteMetaData getContinuousEdge(TOP_Knoten topNode) {
		val intersectEdges = getIntersectEdgeAt(topNode)
		if (intersectEdges.nullOrEmpty) {
			return null
		}
		if (continuousTopEdge.get(topNode) !== null) {
			return continuousTopEdge.get(topNode)
		}
		if (intersectEdges.exists[it.continuousTopEdge.get(topNode) !== null]) {
			val continuous = intersectEdges.findFirst [
				it.continuousTopEdge.get(topNode) === this
			]
			if (continuous !== null) {
				continuousTopEdge.put(topNode, continuous)
				return continuous
			}
			return null
		}

		// Try to find out continous edge throud MainTrack or GEO_Radius.
		val continuous = intersectEdges.filter[!isAlreadyDefine(topNode)].
			toList.getContinuousEdge(topNode)
		if (continuous === null) {
			return null
		}
		continuousTopEdge.put(topNode, continuous)
		// Set continuous edge of continuous edge to this edge
		continuous.continuousTopEdge.put(topNode, this)
		return continuous
	}

	private def TOPKanteMetaData getContinuousEdge(
		List<TOPKanteMetaData> intersectEdges, TOP_Knoten topNode) {
		if (intersectEdges.nullOrEmpty) {
			return null
		}

		if (intersectEdges.size === 1) {
			return intersectEdges.get(0)
		}

		if (!isLegalConnection(topNode, intersectEdges)) {
			throw new IllegalArgumentException('''Illegal TOP_Anschluss by TOP_Knoten: «topNode.identitaet.wert»''')
		}

		val component = topNode.WKrGspElement?.WKrGspKomponenten.filter [
			topKanten.exists[intersectEdges.map[topEdge].contains(it)]
		]

		if (component.empty || component.size > 1) {
			throw new IllegalArgumentException('''By TOP_Knoten: {«topNode.identitaet.wert»} doesn't exist TrackSwtich or The Switch reference to wrong TOP_Kante''')
		}

		val mainConnector = component.get(0).mainTrackConnector
		if (mainConnector !== null) {
			return getContinousEdgeAt(intersectEdges, topNode, mainConnector)
		}
		return getContinousEdgeAt(intersectEdges, topNode)

	}

	private def boolean isLegalConnection(TOP_Knoten node,
		List<TOPKanteMetaData> intersectEdges) {
		val clone = newArrayList()
		clone.addAll(intersectEdges)
		clone.add(this)
		val connectTypes = clone.map[it.getTopConnectorAt(node)]
		return connectTypes.toSet.size === 3 &&
			connectTypes.containsAll(
				#[ENUMTOP_ANSCHLUSS_LINKS, ENUMTOP_ANSCHLUSS_RECHTS,
					ENUMTOP_ANSCHLUSS_SPITZE])
	}

	private def ENUMTOPAnschluss getMainTrackConnector(
		W_Kr_Gsp_Komponente component) {
		switch (component.zungenpaar?.stammgleis?.wert) {
			case ENUM_LINKS_RECHTS_LINKS:
				return ENUMTOP_ANSCHLUSS_LINKS
			case ENUM_LINKS_RECHTS_RECHTS:
				return ENUMTOP_ANSCHLUSS_RECHTS
			default:
				return null
		}
	}

	/**
	 * Find continous edge through main track
	 */
	private def TOPKanteMetaData getContinousEdgeAt(
		List<TOPKanteMetaData> intersectEdges, TOP_Knoten node,
		ENUMTOPAnschluss mainDirection) {
		val connectTypeAtNode = node.topConnectorAt
		if (connectTypeAtNode === ENUMTOP_ANSCHLUSS_SPITZE) {
			return intersectEdges.findFirst [
				it.getTopConnectorAt(node) === mainDirection
			]
		}
		if (mainDirection === connectTypeAtNode) {
			return intersectEdges.findFirst [
				it.getTopConnectorAt(node) ===
					ENUMTOPAnschluss.ENUMTOP_ANSCHLUSS_SPITZE
			]
		}
		return null
	}

	/**
	 * Find continous edge through geoform of  {@link GEO_Kante}
	 */
	private def TOPKanteMetaData getContinousEdgeAt(
		List<TOPKanteMetaData> intersectEdges, TOP_Knoten topNode) {
		val edgeConnector = topNode.topConnectorAt

		val intersectStraightEdges = intersectEdges.filter [
			it.topEdge.getGeoKanteAtKnoten(topNode)?.GEOKanteAllg?.GEOForm?.
				wert === ENUMGEOForm.ENUMGEO_FORM_GERADE
		]
		// When both of intersect edges are straight and current edge straight to,
		// then choose one of intersect egde through routing rule 
		if (intersectStraightEdges.size > 1) {
			val isStraight = topEdge.getGeoKanteAtKnoten(topNode)?.
				GEOKanteAllg?.GEOForm?.wert === ENUMGEOForm.ENUMGEO_FORM_GERADE
			return isStraight ? selectMatchEdge(topNode, intersectEdges) : null
		}
		// When intersect edges exist straight edge, then take this edge		
		if (intersectStraightEdges.size === 1) {
			return intersectStraightEdges.get(0)
		}

		return getContinuousEdgeAt(topNode, intersectEdges, edgeConnector)
	}

	/**
	 * Find continous edge through node radius of  {@link GEO_Kante}
	 */
	private def TOPKanteMetaData getContinuousEdgeAt(TOP_Knoten topNode,
		List<TOPKanteMetaData> intersectEdges, ENUMTOPAnschluss edgeConnector) {
		val geoNode = topNode.GEOKnoten
		val currentRadius = topEdge.getGeoKanteAtKnoten(topNode).
			getRadiusAtKnoten(geoNode)

		val intersectRadius = intersectEdges.map [
			new Pair(it, it.topEdge.getGeoKanteAtKnoten(topNode).
				getRadiusAtKnoten(geoNode))
		]

		// When exist intersect edge with node radius equal current edge,
		// or intersect edges radius are equal but current edge is note
		val sameRadius = intersectRadius.filter[value === currentRadius]
		if (sameRadius.size > 1) {
			return selectMatchEdge(topNode, intersectEdges)
		}

		if (sameRadius.size === 1) {
			return sameRadius.get(0).key
		}

		if (intersectRadius.map[value].toSet.size === 1) {
			return null
		}

		val continuous = intersectEdges.min [ e1, e2 |
			val firstRadius = e1.topEdge.getGeoKanteAtKnoten(topNode).
				getRadiusAtKnoten(geoNode)
			val secondRadius = e2.topEdge.getGeoKanteAtKnoten(topNode).
				getRadiusAtKnoten(geoNode)
			return Double.compare(Math.abs(currentRadius - firstRadius),
				Math.abs(currentRadius - secondRadius))
		]

		if (edgeConnector === ENUMTOP_ANSCHLUSS_SPITZE ||
			continuous?.getTopConnectorAt(topNode) ===
				ENUMTOP_ANSCHLUSS_SPITZE) {
			return continuous
		}

		return null
	}

	private def TOPKanteMetaData selectMatchEdge(TOP_Knoten topNode,
		List<TOPKanteMetaData> intersectEdges) {
		val edgeConnector = topNode.topConnectorAt
		if (edgeConnector === ENUMTOP_ANSCHLUSS_SPITZE) {
			return intersectEdges.get(0)
		}
		return intersectEdges.findFirst [
			it.getTopConnectorAt(topNode) === ENUMTOP_ANSCHLUSS_SPITZE
		]
	}

	def void defineLeftRightEdge(TOP_Knoten topNode) {
		val intersectEdges = getIntersectEdgeAt(topNode)
		if (intersectEdges.empty) {
			return
		}
		val continuousEdge = getContinuousEdgeAt(topNode)
		intersectEdges.filter [
			it !== continuousEdge && !it.isAlreadyDefine(topNode)
		].forEach [
			this.defineLeftRightEdge(it, topNode)
		]
	}

	private def isAlreadyDefine(TOPKanteMetaData intersect,
		TOP_Knoten topNode) {
		val leftEdges = leftTopEdge.getOrDefault(topNode, Collections.emptyList)
		val rightEdges = rightTopEdge.getOrDefault(topNode,
			Collections.emptyList)
		if (leftEdges.empty && rightEdges.empty) {
			return false
		}

		return leftEdges.contains(intersect) || rightEdges.contains(intersect)
	}

	private def void defineLeftRightEdge(TOPKanteMetaData intersect,
		TOP_Knoten topNode) {
		// Define left, right edge through intersect edge, also when this edge is left side of intersect edge,
		// then intersect edge is right side of this edge
		val isLeftOfIntersect = intersect.leftTopEdge.get(topNode)?.findFirst [
			it === this
		] !== null
		val isRightOfIntersect = intersect.rightTopEdge.get(topNode)?.findFirst [
			it === this
		] !== null
		if (isLeftOfIntersect) {
			rightTopEdge.add(topNode, intersect)
		} else if (isRightOfIntersect) {
			leftTopEdge.add(topNode, intersect)
		} else {
			intersect.defineLeftRightEdgeWithConnector(topNode)
		}
	}

	// Define left, right edge through connector type at TOP_Knoten
	private def void defineLeftRightEdgeWithConnector(
		TOPKanteMetaData intersect, TOP_Knoten topNode) {
		val edgeConnector = topNode.topConnectorAt
		val intersectConnector = intersect.getTopConnectorAt(topNode)
		var isLeft = false
		switch (edgeConnector) {
			case ENUMTOP_ANSCHLUSS_SPITZE:
				isLeft = intersectConnector === ENUMTOP_ANSCHLUSS_LINKS
			case ENUMTOP_ANSCHLUSS_LINKS,
			case ENUMTOP_ANSCHLUSS_RECHTS:
				isLeft = edgeConnector === ENUMTOP_ANSCHLUSS_RECHTS
			default:
				return
		}
		if (changeLeftRightNode.get(topNode)) {
			isLeft = !isLeft
		}
		// We can here define poistion for all edges at this TOP_Knoten,
		// after know one of position 
		if (isLeft) {
			leftTopEdge.add(topNode, intersect)
			continuousTopEdge.get(topNode)?.leftTopEdge?.add(topNode, intersect)
			intersect.rightTopEdge.add(topNode, this)
		} else {
			rightTopEdge.add(topNode, intersect)
			continuousTopEdge.get(topNode)?.rightTopEdge?.add(topNode,
				intersect)
			intersect.leftTopEdge.add(topNode, this)
		}
	}

	def boolean isChangeLeftRightEdge() {
		val connector = #[topNodeA.topConnectorAt, topNodeB.topConnectorAt]
		return connector.forall[it === ENUMTOP_ANSCHLUSS_SPITZE] ||
			connector.forall [
				it === ENUMTOP_ANSCHLUSS_LINKS ||
					it === ENUMTOP_ANSCHLUSS_RECHTS
			]
	}
}
