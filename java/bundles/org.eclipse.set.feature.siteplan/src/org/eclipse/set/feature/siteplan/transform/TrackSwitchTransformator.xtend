/**
 * Copyright (c) 2021 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.siteplan.transform

import java.io.IOException
import org.eclipse.set.basis.geometry.GeometryException
import org.eclipse.set.feature.siteplan.TrackSwitchMetadataProvider
import org.eclipse.set.feature.siteplan.positionservice.PositionService
import org.eclipse.set.feature.siteplan.trackservice.TrackService
import org.eclipse.set.model.siteplan.ContinuousTrackSegment
import org.eclipse.set.model.siteplan.Coordinate
import org.eclipse.set.model.siteplan.Label
import org.eclipse.set.model.siteplan.LeftRight
import org.eclipse.set.model.siteplan.SiteplanFactory
import org.eclipse.set.model.siteplan.SiteplanPackage
import org.eclipse.set.model.siteplan.TrackSwitchComponent
import org.eclipse.set.model.siteplan.TurnoutOperatingMode
import org.eclipse.set.toolboxmodel.BasisTypen.ENUMLinksRechts
import org.eclipse.set.toolboxmodel.Basisobjekte.Punkt_Objekt
import org.eclipse.set.toolboxmodel.Signale.Signal
import org.eclipse.set.toolboxmodel.Weichen_und_Gleissperren.ENUMWKrArt
import org.eclipse.set.toolboxmodel.Weichen_und_Gleissperren.ENUMWKrGspStellart
import org.eclipse.set.toolboxmodel.Weichen_und_Gleissperren.W_Kr_Anlage
import org.eclipse.set.toolboxmodel.Weichen_und_Gleissperren.W_Kr_Gsp_Element
import org.osgi.service.component.annotations.Component
import org.osgi.service.component.annotations.Reference

import static org.eclipse.set.feature.siteplan.transform.TrackSwitchLeg.*

import static extension org.eclipse.set.feature.siteplan.transform.TransformUtils.*
import static extension org.eclipse.set.ppmodel.extensions.GeoKnotenExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.PunktObjektTopKanteExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.SignalExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.TopKanteExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.TopKnotenExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.WKrAnlageExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.WKrGspElementExtensions.*

/**
 * Transforms a track switch from the PlanPro model to a siteplan TrackSwitch
 * @author Stuecker
 */
@Component(service=Transformator)
class TrackSwitchTransformator extends BaseTransformator<W_Kr_Anlage> {
	@Reference
	TrackService trackService
	
	@Reference
	PositionService positionService

	static val ERROR_NO_TRACK_SWITCH_LEG = "Weichenschenkel der Weiche nicht bestimmbar."

	TrackSwitchMetadataProvider trackSwitchMetadataProvider;

	new() {
		trackSwitchMetadataProvider = new TrackSwitchMetadataProvider();
		try {
			trackSwitchMetadataProvider.initialize()
		} catch (IOException exc) {
			/* ignore failure */
		}
	}

	override void transform(W_Kr_Anlage trackswitch) {
		val result = SiteplanFactory.eINSTANCE.createTrackSwitch
		result.guid = trackswitch.identitaet.wert
		result.design = '''«trackswitch.WKrAnlageAllg.WKrArt.wert» «trackswitch.WKrAnlageAllg.WKrGrundform.wert»'''
		val metadata = trackSwitchMetadataProvider.getTrackSwitchMetadata(
			trackswitch.WKrAnlageAllg.WKrArt.wert,
			trackswitch.WKrAnlageAllg.WKrGrundform.wert
		)

		val elements = trackswitch.WKrGspElemente

		// Find the legs of the first element		
		val firstElement = elements.head
		val components = firstElement.WKrGspKomponenten
		if (components.length === 0) {
			recordError(trackswitch.identitaet?.wert, ERROR_NO_TRACK_SWITCH_LEG)
			return
		}
		val legA = getLeg(components, metadata, 0)
		val legB = getLeg(components, metadata, 1)
		if (legA === null || legB === null) {
			recordError(trackswitch.identitaet?.wert, ERROR_NO_TRACK_SWITCH_LEG)
			return
		}
		val component = transformElement(trackswitch, firstElement, legA, legB)
		result.components.add(component)
		// Some crossing track switches contain two elements, add the second element
		// as well as the continuous segments 
		if (elements.length >= 2) {
			val secondElement = elements.get(1)
			val secondComponents = secondElement.WKrGspKomponenten
			var legC = getLeg(secondComponents, metadata, 0)
			var legD = getLeg(secondComponents, metadata, 1)
			if (legC.TOPKante === legA.TOPKante) {
				val temp = legC
				legC = legD
				legD = temp
			}
			if (legC === null || legD === null) {
				recordError(trackswitch.identitaet?.wert,
					ERROR_NO_TRACK_SWITCH_LEG)
				return
			}

			result.components.add(
				transformElement(trackswitch, secondElement, legC, legD))

			// Add continuous segments:
			if (trackswitch.WKrAnlageAllg.WKrArt.wert ==
				ENUMWKrArt.ENUMW_KR_ART_DKW) {
				// DKW:
				// - start of legA <-> start of legC
				// - start of legB <-> start of legD
				result.continuousSegments.add(getContinousSegment(legA, legC))
				result.continuousSegments.add(getContinousSegment(legB, legD))
			} else if (trackswitch.WKrAnlageAllg.WKrArt.wert ==
				ENUMWKrArt.ENUMW_KR_ART_EKW) {
				// EKW: (legA and legB  / legC and legD start at the same point)
				// - start of legA <-> start of legC
				result.continuousSegments.add(getContinousSegment(legA, legC))
			}
		}

		result.addSiteplanElement(
			SiteplanPackage.eINSTANCE.siteplanState_TrackSwitches)
	}

	def ContinuousTrackSegment getContinousSegment(TrackSwitchLeg legStart,
		TrackSwitchLeg legEnd) {
		val result = SiteplanFactory.eINSTANCE.createContinuousTrackSegment
		result.start = legStart.getNodeCoordinate(trackService, positionService)
		result.end = legEnd.getNodeCoordinate(trackService, positionService)
		return result
	}

	def TrackSwitchComponent transformElement(W_Kr_Anlage anlage,
		W_Kr_Gsp_Element element, TrackSwitchLeg mainLeg,
		TrackSwitchLeg sideLeg) {
		val result = SiteplanFactory.eINSTANCE.createTrackSwitchComponent
		result.guid = element.identitaet.wert
		val components = element.WKrGspKomponenten
		components.forEach [
			it.transformPunktObjektStrecke(result)
		]

		result.mainLeg = mainLeg.transformLeg
		result.sideLeg = sideLeg.transformLeg
		result.label = transformLabel(element)
		result.start = mainLeg.getCoordinate(0, 0, trackService,
			positionService)
		result.labelPosition = sideLeg.getCoordinate(sideLeg.length / 2, 0,
			trackService, positionService)

		val pointDetector = components.get(0)?.zungenpaar?.
			zungenpruefkontaktAnzahl?.wert
		if (pointDetector !== null)
			result.pointDetectorCount = pointDetector.intValue

		result.operatingMode = transform(
			element.WKrGspElementAllg?.WKrGspStellart?.wert)
		result.preferredLocation = element.weicheElement?.weicheVorzugslage?.
			wert === ENUMLinksRechts.ENUM_LINKS_RECHTS_LINKS
			? LeftRight.LEFT
			: LeftRight.RIGHT

		return result
	}

	private def org.eclipse.set.model.siteplan.TrackSwitchLeg transformLeg(
		TrackSwitchLeg leg) {
		val result = SiteplanFactory.eINSTANCE.createTrackSwitchLeg
		switch (leg.connection) {
			case ENUMTOP_ANSCHLUSS_LINKS:
				result.connection = LeftRight.LEFT
			case ENUMTOP_ANSCHLUSS_RECHTS:
				result.connection = LeftRight.RIGHT
			default:
				result.connection = null
		}
		result.coordinates.addAll(leg.getCoordinates(trackService, positionService))
		return result
	}

	private def Label transformLabel(W_Kr_Gsp_Element element) {
		val result = SiteplanFactory.eINSTANCE.createLabel
		val label = getLabel(element.bezeichnung)
		result.text = label.text
		val mode = element.weicheElement?.weicheBetriebsart?.wert
		if (mode === null) {
			return result
		}
		switch (mode) {
			case ENUM_WEICHE_BETRIEBSART_LINKS: {
				result.text = label.text + " (L)"
				return result
			}
			case ENUM_WEICHE_BETRIEBSART_RECHTS: {
				result.text = label.text + " (R)"
				return result
			}
			default:
				return result
		}
	}

	/**
	 * Transforms a PlanPro Signal to a Siteplan TrackSwitchEndMarker if it is a Ra_12 Signal
	 * 
	 * @param signal a PlanPro Signal
	 * @returns a siteplan TrackSwitchEndMarker or null on failure
	 */
	def void transform(Signal signal) {
		if (!signal.signalbegriffe.map[signalbegriffID.eClass.name].
			contains("Ra12"))
			return

		val result = SiteplanFactory.eINSTANCE.createTrackSwitchEndMarker
		val topKanteLocations = signal.punktObjektEdgeCoordinates
		if (topKanteLocations.length >= 2) {
			result.legACoordinate = topKanteLocations.get(0)
			result.legBCoordinate = topKanteLocations.get(1)
			synchronized (state) {
				state.trackSwitchEndMarkers.add(result)
			}

		}
	}

	/**
	 * @param object a Punkt_Objekt 
	 * @return the coordinates on the TOP_Kante that the Punkt_Objekt is located on
	 */
	private def Iterable<Coordinate> getPunktObjektEdgeCoordinates(
		Punkt_Objekt object) {
		return object.punktObjektTOPKante.map [ coordinate |
			val crs = coordinate.topKante.TOPKnotenA.GEOKnoten.CRS
			try {
				val topKante = coordinate.topKante
				val abstand = coordinate.abstand.wert.doubleValue
				val direction = coordinate.wirkrichtung?.wert
				return positionService.transformCoordinate(
					topKante.getCoordinate(abstand, 0, direction).
						geometricInformation, crs)
			} catch (GeometryException exc) {
				return null
			}
		].filterNull
	}

	private def TurnoutOperatingMode transform(
		ENUMWKrGspStellart operatingMode) {
		switch (operatingMode) {
			case ENUMWKrGspStellart.
				ENUMW_KR_GSP_STELLART_ELEKTRISCH_FERNGESTELLT:
				return TurnoutOperatingMode.ELECTRIC_REMOTE
			case ENUMWKrGspStellart.
				ENUMW_KR_GSP_STELLART_ELEKTRISCH_ORTSGESTELLT:
				return TurnoutOperatingMode.ELECTRIC_LOCAL
			case ENUMWKrGspStellart.
				ENUMW_KR_GSP_STELLART_MECHANISCH_FERNGESTELLT:
				return TurnoutOperatingMode.MECHANICAL_REMOTE
			case ENUMWKrGspStellart.
				ENUMW_KR_GSP_STELLART_MECHANISCH_ORTSGESTELLT:
				return TurnoutOperatingMode.MECHANICAL_LOCAL
			case ENUMWKrGspStellart.ENUMW_KR_GSP_STELLART_NICHT_STELLBAR:
				return TurnoutOperatingMode.NON_OPERATIONAL
			case ENUMWKrGspStellart.ENUMW_KR_GSP_STELLART_RUECKFALLWEICHE:
				return TurnoutOperatingMode.TRAILABLE
			case ENUMWKrGspStellart.ENUMW_KR_GSP_STELLART_SONSTIGE:
				return TurnoutOperatingMode.OTHER
			case ENUMWKrGspStellart.ENUMW_KR_GSP_STELLART_STILLGELEGT_LINKS:
				return TurnoutOperatingMode.DEAD_LEFT
			case ENUMWKrGspStellart.ENUMW_KR_GSP_STELLART_STILLGELEGT_RECHTS:
				return TurnoutOperatingMode.DEAD_RIGHT
			default:
				return TurnoutOperatingMode.UNDEFINED
		}
	}
}
