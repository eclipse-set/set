/**
 * Copyright (c) 2023 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.overviewplan.transformator

import java.util.List
import org.eclipse.set.feature.overviewplan.service.TrackService
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
import static extension org.eclipse.set.ppmodel.extensions.WKrGspKomponenteExtensions.*

/**
 * 
 */
class TOPKanteMetaData {
	TOP_Kante edge
	TOP_Knoten nodeA
	TOP_Knoten nodeB
	
	new() {
		edge = null
		nodeA = null
		nodeB = null
	}

	new(TOP_Kante topKante) {
		this.edge = topKante
		this.nodeA = topKante?.IDTOPKnotenA
		this.nodeB = topKante?.IDTOPKnotenB
	}

	def TOP_Kante getTopKante() {
		return edge
	}

	def TOP_Knoten getTOPKnotenA() {
		return nodeA
	}

	def TOP_Knoten getTOPKnotenB() {
		return nodeB
	}

	def ENUMTOPAnschluss getTOPAnschlussAt(TOP_Knoten node) {
		return edge?.getTOPAnschluss(node)
	}

	def List<W_Kr_Gsp_Komponente> getTrackSwitchAt(TOP_Knoten node) {
		return node?.WKrGspElement?.WKrGspKomponenten
	}

	def List<TOPKanteMetaData> getConnectEdgeAt(TrackService trackService,
		TOP_Knoten node) {
		val result = node?.topKanten?.filter[it !== topKante].map [
			trackService.getTOPKanteMetaData(it)
		].toList
		if (result.size < 3) {
			return result
		}
		throw new IllegalArgumentException('''TOP_Knoten: «node.identitaet.wert» verweiss auf mehr als 3 TOP_Kanten''')
	}

	def TOPKanteMetaData getContinuosEdgeAt(List<TOPKanteMetaData> connectEdges,
		TOP_Knoten node) {
		if (connectEdges.size < 2) {
			return connectEdges.get(0)
		}

		if (!node.isLegalConnection(connectEdges)) {
			throw new IllegalArgumentException('''Bei TOP_Knoten: {«node.identitaet.wert»} sind die TOP_Anschluss der gehörigen TOP_Kanten falsch''')
		}

		val wKrGspKomponenten = node.trackSwitchAt.filter [
			TOPKante.exists[connectEdges.map[topKante].contains(it)]
		]

		if (wKrGspKomponenten.empty || wKrGspKomponenten.size > 1) {
			throw new IllegalArgumentException('''Bei TOP_Knoten: {«node.identitaet.wert»} gibt es keine Weiche oder die Weiche verweiss auf falsche TOP_Kante ''')
		}

		val mainDirection = wKrGspKomponenten.get(0).mainDirection
		if (mainDirection !== null) {
			return connectEdges.getContinousEdgeAt(node, mainDirection)
		}
		return connectEdges.getContinousEdgeAt(node)
	}

	private def boolean isLegalConnection(TOP_Knoten node,
		List<TOPKanteMetaData> connectEdges) {
		val clone = newArrayList()
		clone.addAll(connectEdges)
		clone.add(this)
		val connectTypes = clone.map[topKante.getTOPAnschluss(node)]
		return connectTypes.toSet.size === 3 &&
			connectTypes.containsAll(
				#[ENUMTOP_ANSCHLUSS_LINKS, ENUMTOP_ANSCHLUSS_RECHTS,
					ENUMTOP_ANSCHLUSS_SPITZE])
	}

	private def ENUMTOPAnschluss getMainDirection(
		W_Kr_Gsp_Komponente wKrGspKomponente) {
		switch (wKrGspKomponente.zungenpaar?.stammgleis?.wert) {
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
		List<TOPKanteMetaData> connectEdges, TOP_Knoten node,
		ENUMTOPAnschluss mainDirection) {
		val connectTypeAtNode = node.TOPAnschlussAt
		if (connectTypeAtNode === ENUMTOP_ANSCHLUSS_SPITZE) {
			return connectEdges.findFirst [
				it.getTOPAnschlussAt(node) === mainDirection
			]
		}
		if (mainDirection === connectTypeAtNode) {
			return connectEdges.findFirst [
				it.getTOPAnschlussAt(node) ===
					ENUMTOPAnschluss.ENUMTOP_ANSCHLUSS_SPITZE
			]
		}
		return null
	}

	/**
	 * Find continous edge through node radius of {@link GEO_Kante}
	 */
	private def TOPKanteMetaData getContinousEdgeAt(
		List<TOPKanteMetaData> connectEdges, TOP_Knoten node) {
		val connectTypeAtNode = node.TOPAnschlussAt

		var continuous = new TOPKanteMetaData
		val straightEdges = connectEdges.filter [
			topKante.getGeoKanteAtKnoten(node)?.GEOKanteAllg?.GEOForm?.wert ===
				ENUMGEOForm.ENUMGEO_FORM_GERADE
		]
		// When connect edges exist straight edge, then take this edge
		// When both of connect edges are straight, than take one of this 
		if (straightEdges.size >= 1) {
			continuous = straightEdges.get(0)
		} else {
			val geoNode = node.GEOKnoten
			continuous = connectEdges.max [ e1, e2 |
				val firstRadius = e1.topKante.getGeoKanteAtKnoten(node).
					getRadiusAtKnoten(geoNode)
				val secondRadius = e2.topKante.getGeoKanteAtKnoten(node).
					getRadiusAtKnoten(geoNode)
				return Double.compare(firstRadius, secondRadius)
			]
		}

		if (connectTypeAtNode === ENUMTOP_ANSCHLUSS_SPITZE ||
			continuous.getTOPAnschlussAt(node) === ENUMTOP_ANSCHLUSS_SPITZE) {
			return continuous
		}

		return null
	}


}
