/**
 * Copyright (c) 2022 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.siteplan.transform

import java.util.List
import org.eclipse.set.core.services.geometry.GeoKanteGeometryService
import org.eclipse.set.feature.siteplan.TrackSwitchMetadata
import org.eclipse.set.feature.siteplan.positionservice.PositionService
import org.eclipse.set.model.planpro.BasisTypen.ENUMLinksRechts
import org.eclipse.set.model.planpro.BasisTypen.ENUMWirkrichtung
import org.eclipse.set.model.planpro.Geodaten.ENUMTOPAnschluss
import org.eclipse.set.model.planpro.Geodaten.TOP_Kante
import org.eclipse.set.model.planpro.Geodaten.TOP_Knoten
import org.eclipse.set.model.planpro.Weichen_und_Gleissperren.W_Kr_Gsp_Komponente
import org.eclipse.set.model.siteplan.Coordinate
import org.eclipse.set.model.siteplan.Position

import static org.eclipse.set.model.planpro.Geodaten.ENUMTOPAnschluss.*

import static extension org.eclipse.set.ppmodel.extensions.PunktObjektExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.TopKanteExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.TopKnotenExtensions.*

/**
 * Helper class defining a leg of a track switch
 * 
 * @author Stuecker
 */
class TrackSwitchLeg {
	// Number of points on a track switch leg
	val TRACK_SWITCH_POINTS = 10

	// Default length of a track switch if it could not be identified
	val DEFAULT_TRACKSWITCH_LEG_LENGTH = 20

	TOP_Kante topKante;
	TOP_Knoten topKnoten;
	ENUMTOPAnschluss connection;
	double length = DEFAULT_TRACKSWITCH_LEG_LENGTH;
	double start = 0;

	/**
	 * @return the length of the leg
	 */
	def double getLength() {
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
	def Coordinate[] getCoordinates(GeoKanteGeometryService geometryService, PositionService positionService) {
		// Ensure that the leg ends within the TOP_Kante
		var legLength = Math.min(start + length,
			topKante.TOPKanteAllg.TOPLaenge.wert.doubleValue)
		val sectionLength = (legLength - start) / (TRACK_SWITCH_POINTS - 1)
		val result = newArrayList
		for (var int i = 0; i < TRACK_SWITCH_POINTS; i++) {
			result.add(
				geometryService.getCoordinate(topKante, topKnoten,
					start + sectionLength * i, 0, null));
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
	def Position getNodeCoordinate(GeoKanteGeometryService geometryService, PositionService positionService) {
		return positionService.transformPosition(
			geometryService.getCoordinate(topKante, topKnoten, 0, 0, null))
	}

	/**
	 * Finds a position on the track switch leg
	 * 
	 * @param distance the distance from the start of the leg
	 * @param lateralDistance the lateral distance from the track
	 * @param geometryService a GeoKanteGeometryService
	 * @return the position
	 */
	def Position getCoordinate(double distance, double lateralDistance,
		GeoKanteGeometryService geometryService, PositionService positionService) {
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
			if (metadata !== null && metadata.trackSwitchLength !== null) {
				if (legIndex == 0)
					leg.length = metadata.trackSwitchLength.mainLeg
				else
					leg.length = metadata.trackSwitchLength.sideLeg
			}
			val component = components.head
			leg.topKnoten = component.topKnoten
			if (leg.topKnoten === null ||
				leg.topKnoten.getTrackSwitchLegs.length - 1 < legIndex) {
				return null;
			}
			leg.topKante = leg.topKnoten.getTrackSwitchLegs.get(legIndex)
			leg.connection = getTOPAnschluss(leg.topKante, leg.topKnoten)
		} else {
			if (metadata !== null) {
				leg.length = metadata.rightCrossing.crossing.mainLeg
				if (metadata.rightCrossing.crossingTriangle !== null)
					leg.start = metadata.rightCrossing.crossingTriangle.
						mainLeg - leg.length
			}
			val component = components.get(legIndex)
			leg.topKante = component.crossingLeg
			leg.topKnoten = component.topKnoten
		}

		if (leg.topKante === null || leg.topKnoten === null) {
			return null
		}
		return leg;
	}

	private static def TOP_Kante getCrossingLeg(W_Kr_Gsp_Komponente component) {
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
