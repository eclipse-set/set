/**
 * Copyright (c) 2022 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.siteplan.transform

import java.math.BigDecimal
import java.math.RoundingMode
import java.util.List
import org.eclipse.set.basis.constants.ToolboxConstants
import org.eclipse.set.core.services.geometry.GeoKanteGeometryService
import org.eclipse.set.feature.siteplan.TrackSwitchMetadata
import org.eclipse.set.feature.siteplan.positionservice.PositionService
import org.eclipse.set.model.planpro.BasisTypen.ENUMLinksRechts
import org.eclipse.set.model.planpro.BasisTypen.ENUMWirkrichtung
import org.eclipse.set.model.planpro.Geodaten.ENUMTOPAnschluss
import org.eclipse.set.model.planpro.Geodaten.TOP_Kante
import org.eclipse.set.model.planpro.Geodaten.TOP_Knoten
import org.eclipse.set.model.planpro.Weichen_und_Gleissperren.ENUMWKrArt
import org.eclipse.set.model.planpro.Weichen_und_Gleissperren.W_Kr_Gsp_Komponente
import org.eclipse.set.model.siteplan.Coordinate
import org.eclipse.set.model.siteplan.Position

import static org.eclipse.set.model.planpro.Geodaten.ENUMTOPAnschluss.*

import static extension org.eclipse.set.ppmodel.extensions.PunktObjektExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.PunktObjektTopKanteExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.TopKanteExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.TopKnotenExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.WKrAnlageExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.WKrGspElementExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.WKrGspKomponenteExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.utils.IterableExtensions.*

/**
 * Helper class defining a leg of a track switch
 * 
 * @author Stuecker
 */
class TrackSwitchLeg {
	// Number of points on a track switch leg
	val TRACK_SWITCH_POINTS = 10

	// Default length of a track switch if it could not be identified
	val DEFAULT_TRACKSWITCH_LEG_LENGTH = BigDecimal.valueOf(20)

	TOP_Kante topKante;
	TOP_Knoten topKnoten;
	ENUMTOPAnschluss connection;
	BigDecimal length = DEFAULT_TRACKSWITCH_LEG_LENGTH;
	BigDecimal start = BigDecimal.ZERO;

	/**
	 * @return the length of the leg
	 */
	def BigDecimal getLength() {
		return length;
	}

	/**
	 * @return top kante of the leg
	 */
	def TOP_Kante getTOPKante() {
		return topKante
	}

	/**
	 * @return the conection side of the leg
	 */
	def ENUMTOPAnschluss getConnection() {
		return connection
	}

	/**
	 * Determines a list of points on the track switch leg
	 * 
	 * @param geometryService a GeoKanteGeometryService
	 * @return a list of points on the leg
	 */
	def Coordinate[] getCoordinates(GeoKanteGeometryService geometryService,
		PositionService positionService) {
		// Ensure that the leg ends within the TOP_Kante
		var legLength = (start + length).min(
			topKante.TOPKanteAllg.TOPLaenge.wert)
		// To avoid the last section have length greater then leg length
		val offset = BigDecimal.valueOf(0.0001)
		val sectionLength = (legLength - start).divide(
			BigDecimal.valueOf(TRACK_SWITCH_POINTS - 1),
			ToolboxConstants.ROUNDING_TO_PLACE, RoundingMode.HALF_DOWN) - offset
		val result = newArrayList
		for (var int i = 0; i < TRACK_SWITCH_POINTS; i++) {
			result.add(
				geometryService.getCoordinate(topKante, topKnoten,
					start + sectionLength * BigDecimal.valueOf(i),
					BigDecimal.ZERO, null));
		}
		return result.filterNull.map [ coordinate |
			positionService.transformPosition(coordinate)
		]
	}

	/**
	 * Finds the TOP Knoten position of the track switch leg
	 * 
	 * @param geometryService a GeoKanteGeometryService
	 * @return the position
	 */
	def Position getNodeCoordinate(GeoKanteGeometryService geometryService,
		PositionService positionService) {
		return positionService.transformPosition(
			geometryService.getCoordinate(topKante, topKnoten, BigDecimal.ZERO,
				BigDecimal.ZERO, null))
	}

	/**
	 * Finds a position on the track switch leg
	 * 
	 * @param distance the distance from the start of the leg
	 * @param lateralDistance the lateral distance from the track
	 * @param geometryService a GeoKanteGeometryService
	 * @return the position
	 */
	def Position getCoordinate(BigDecimal distance, BigDecimal lateralDistance,
		GeoKanteGeometryService geometryService,
		PositionService positionService) {
		return positionService.transformPosition(
			geometryService.getCoordinate(topKante, topKnoten, start + distance,
				lateralDistance, ENUMWirkrichtung.ENUM_WIRKRICHTUNG_IN))
	}

	/**
	 * Determines the track switch leg for a given set of components and an index
	 * 
	 * Note: This does not yet account for the following (due to unclear requirements):
	 * - Which leg is the main leg and which leg is the side leg?
	 * 	Implementation: First leg is main leg, second leg is side leg
	 * 
	 * - Which side of a crossing is which?
	 * 	Implementation: All sides are the right side of a crossing 
	 * 
	 * @param components the the distance from the start of the leg
	 * @param legIndex the index of the leg (either 0 or 1)
	 * @param metadata a TrackSwitchMetadata for the track switch or null
	 * @return the track switch leg
	 */
	static def TrackSwitchLeg getLeg(List<W_Kr_Gsp_Komponente> components,
		TrackSwitchMetadata metadata, int legIndex) {
		val leg = new TrackSwitchLeg

		if (components.length == 1) {
			val component = components.head
			leg.topKnoten = component.topKnoten
			if ((leg.topKnoten === null ||
				leg.topKnoten.getTrackSwitchLegs.length - 1 < legIndex) &&
				!isCrossType(component)) {
				return null;
			}
			leg.determineLegLength(component, metadata, legIndex)
		} else {
			val component = components.get(legIndex)
			leg.topKnoten = component.topKnoten
			leg.determineLegLength(component, metadata, legIndex)
		}

		if (leg.topKante === null || leg.topKnoten === null) {
			return null
		}
		return leg;
	}

	private def determineLegLength(W_Kr_Gsp_Komponente component,
		TrackSwitchMetadata metadata, int legIndex) {
		val trackSwitch = component.WKrGspElement.WKrAnlage
		val switchType = trackSwitch.WKrAnlageAllg.WKrArt.wert
		switch (switchType) {
			case ENUMW_KR_ART_KR,
			case ENUMW_KR_ART_FLACHKREUZUNG: {
				determineCrossSwitchLegLength(component, metadata, legIndex)
			}
			case ENUMW_KR_ART_DKW: {
				topKante = component.crossingLeg
				if (metadata !== null) {
					length = BigDecimal.valueOf(
						metadata.rightCrossing.crossing.mainLeg)
					if (metadata.rightCrossing.crossingTriangle !== null)
						start = BigDecimal.valueOf(
							metadata.rightCrossing.crossingTriangle.mainLeg) -
							length
				} else {
					length = topKante?.TOPKanteAllg?.TOPLaenge.wert /
						BigDecimal.valueOf(2)
				}
			}
			case ENUMW_KR_ART_EKW: {
				if (legIndex === 0) {
					topKante = component.crossingLeg
					length = component.getEKWLegLength(metadata)
					// The crossing leg should start at cross point between the switches
					start = length
				} else {
					val currentGspElement = component.WKrGspElement
					val gspAnlage = currentGspElement.WKrAnlage
					val anotherGspElement = gspAnlage.WKrGspElemente.findFirst [
						it !== currentGspElement
					]
					val anotherGspComponent = anotherGspElement?.
						WKrGspKomponenten.firstOrNull
					if (anotherGspComponent === null) {
						throw new IllegalArgumentException(
							gspAnlage.identitaet.wert)
					}
					topKnoten = anotherGspComponent.topKnoten
					length = anotherGspComponent.getEKWLegLength(metadata)
					topKante = anotherGspComponent.crossingLeg
				}
			}
			default: {
				topKante = topKnoten.getTrackSwitchLegs.get(legIndex)
				connection = getTOPAnschluss(topKante, topKnoten)
				if (metadata.trackSwitchLength !== null) {
					if (legIndex == 0)
						length = BigDecimal.valueOf(
							metadata.trackSwitchLength.mainLeg)
					else
						length = BigDecimal.valueOf(
							metadata.trackSwitchLength.sideLeg)
				}

			}
		}

		if (length === null || length === BigDecimal.ZERO) {
			length = DEFAULT_TRACKSWITCH_LEG_LENGTH
		}
	}

	private static def boolean isCrossType(W_Kr_Gsp_Komponente component) {
		val anlage = component?.WKrGspElement?.WKrAnlage
		val type = anlage?.WKrAnlageAllg?.WKrArt?.wert
		return type === ENUMWKrArt.ENUMW_KR_ART_KR ||
			type === ENUMWKrArt.ENUMW_KR_ART_FLACHKREUZUNG
	}

	private def determineCrossSwitchLegLength(W_Kr_Gsp_Komponente component,
		TrackSwitchMetadata metadata, int index) {
		val singlePoint = component.singlePoints.get(index)
		if (singlePoint.wirkrichtung.wert === ENUMWirkrichtung.ENUM_WIRKRICHTUNG_GEGEN) {
			topKnoten = singlePoint.topKante.TOPKnotenB
			start = singlePoint.topKante.laenge - singlePoint.abstand.wert
		} else {
			start = singlePoint?.abstand?.wert
			topKnoten = singlePoint?.topKante.TOPKnotenA
		}
		
		topKante = singlePoint?.IDTOPKante?.value
		if (metadata !== null) {
			val crossingSide = singlePoint.wirkrichtung.wert ==
					ENUMWirkrichtung.ENUM_WIRKRICHTUNG_GEGEN ? metadata.
					rightCrossing : metadata.leftCrossing
			if (crossingSide !== null) {
				length = BigDecimal.valueOf(crossingSide.crossing.mainLeg)
			}
		}
	}

	// The leg of a EKW switch is only to cross point between to switch 
	private def BigDecimal getEKWLegLength(W_Kr_Gsp_Komponente component,
		TrackSwitchMetadata metadata) {
		if (metadata !== null) {
			return BigDecimal.valueOf(metadata.rightCrossing.crossing.mainLeg)

		}
		val crossingLeg = component.crossingLeg
		val straightEdge = component.topKanten.findFirst[it !== crossingLeg]
		return (straightEdge?.TOPKanteAllg?.TOPLaenge?.wert ?: BigDecimal.ZERO).
			divide(BigDecimal.TWO, ToolboxConstants.ROUNDING_TO_PLACE,
				RoundingMode.DOWN)
	}

	protected static def TOP_Kante getCrossingLeg(
		W_Kr_Gsp_Komponente component) {
		val topKnoten = component.topKnoten
		val crossingSide = component?.zungenpaar?.kreuzungsgleis?.wert
		if (crossingSide == ENUMLinksRechts.ENUM_LINKS_RECHTS_RECHTS) {
			return topKnoten.topKanten.filter [
				getTOPAnschluss(topKnoten) == ENUMTOP_ANSCHLUSS_RECHTS
			].head
		} else if (crossingSide == ENUMLinksRechts.ENUM_LINKS_RECHTS_LINKS) {
			return topKnoten.topKanten.filter [
				getTOPAnschluss(topKnoten) == ENUMTOP_ANSCHLUSS_LINKS
			].head
		}
	}

	private static def Iterable<TOP_Kante> getTrackSwitchLegs(
		TOP_Knoten trackSwitchTopNode) {
		return trackSwitchTopNode.topKanten.filter [
			val anschluss = getTOPAnschluss(trackSwitchTopNode)
			anschluss == ENUMTOP_ANSCHLUSS_LINKS ||
				anschluss == ENUMTOP_ANSCHLUSS_RECHTS
		]
	}
}
