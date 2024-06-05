/**
 * Copyright (c) 2021 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.siteplan.transform

import java.util.ArrayList
import java.util.HashSet
import java.util.List
import org.eclipse.set.feature.siteplan.positionservice.PositionService
import org.eclipse.set.feature.siteplan.trackservice.GEOKanteCoordinate
import org.eclipse.set.feature.siteplan.trackservice.TrackService
import org.eclipse.set.model.planpro.BasisTypen.ENUMLinksRechts
import org.eclipse.set.model.planpro.BasisTypen.ENUMWirkrichtung
import org.eclipse.set.model.planpro.Basisobjekte.Punkt_Objekt
import org.eclipse.set.model.planpro.Gleis.ENUMGleisart
import org.eclipse.set.model.planpro.Gleis.Gleis_Art
import org.eclipse.set.model.planpro.Signale.ENUMBefestigungArt
import org.eclipse.set.model.planpro.Signale.ENUMSignalBefestigungsart
import org.eclipse.set.model.planpro.Signale.Signal
import org.eclipse.set.model.planpro.Signale.Signal_Befestigung
import org.eclipse.set.model.siteplan.Direction
import org.eclipse.set.model.siteplan.SignalMount
import org.eclipse.set.model.siteplan.SignalMountType
import org.eclipse.set.model.siteplan.SignalRole
import org.eclipse.set.model.siteplan.SignalScreen
import org.eclipse.set.model.siteplan.SignalSystem
import org.eclipse.set.model.siteplan.SiteplanFactory
import org.eclipse.set.model.siteplan.SiteplanPackage
import org.osgi.service.component.annotations.Component
import org.osgi.service.component.annotations.Reference

import static extension org.eclipse.set.feature.siteplan.transform.TransformUtils.*
import static extension org.eclipse.set.ppmodel.extensions.PunktObjektTopKanteExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.SignalBefestigungExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.SignalExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.SignalRahmenExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.TopKanteExtensions.*

/**
 * Transforms PlanPro Signals to Siteplan Signals/SignalMounts
 * 
 * @author Stuecker
 */
@Component(service=Transformator)
class SignalTransformator extends BaseTransformator<Signal> {
	@Reference
	TrackService trackService

	@Reference
	PositionService positionService

	static val ERROR_NO_LATERAL_POSITION = "Seitliche Positionierung des Signals '%s' nicht bestimmbar."
	static val ERROR_FAILED_TRANSFORM = "Fehler bei der Signaltransformation."

	// Signals not visible in the siteplan
	static val SITEPLAN_HIDDEN_SCREENS = #["Ra12", "Ts1", "Ts2", "Ts3", "Zp6",
		"Zp7", "Zp8", "El1", "El1v", "El2", "El3", "El4", "El5", "El6", "Ra13",
		"Wn7", "OzZf", "OzICE", "OzFak", "OzZugl", "Ne7a", "Ne7b", "Bue4",
		"Bue5", "OzAutoHET", "OzHET", "OzAutoET", "OzET"]
	// Default lateral distances of signals
	static val double PUNKT_OBJEKT_LATERAL_DISTANCE_IN_STATION = 2.25
	static val double PUNKT_OBJEKT_LATERAL_DISTANCE_OTHER = 3.50

	var List<SignalInfo> signalinfo

	override initializeTransform() {
		signalinfo = new ArrayList<SignalInfo>
	}

	override transform(Signal signal) {
		val si = new SignalInfo
		si.signals = #[signal].toSet
		si.mounts = signal.signalRahmen?.map[signalBefestigung]?.toSet ?:
			newHashSet
		signalinfo.add(si)
	}

	override finalizeTransform() {
		// For each mount, find all attached mounts
		// If two signals have a common final mount, they are mounted to the same
		// Signal_Befestigung structure (for example a foundation). Therefore merge the signalinfos
		val mergedSignalInfo = new ArrayList<SignalInfo>
		signalinfo.forEach [ si |
			try {
				val signalMounts = si.mounts.map [ mount |
					mount?.signalBefestigungen
				].flatten
				val merged = mergedSignalInfo.findFirst [ msi |
					msi.mounts.contains(signalMounts.lastOrNull)
				]
				if (merged === null) {
					val mounts = new HashSet<Signal_Befestigung>()
					mounts += si.mounts
					mounts += signalMounts
					si.mounts = mounts
					mergedSignalInfo.add(si)
				} else {
					merged.signals += si.signals
					merged.mounts += signalMounts
				}
			} catch (Exception exc) {
				recordError(si.signals.head?.identitaet?.wert,
					ERROR_FAILED_TRANSFORM)
			}
		]

		// Filter empty signal mounts
		val signals = mergedSignalInfo.filter[signals.length != 0]

		// Transform into siteplan signals
		signals.forEach [
			try {
				val signal = it.transform
				if (signal !== null) {
					signal.addSiteplanElement(
						SiteplanPackage.eINSTANCE.siteplanState_Signals)
					}
			} catch (Exception exc) {
				recordError(it.signals.head?.identitaet?.wert,
					ERROR_FAILED_TRANSFORM)
			}
		]
	}

	/**
	 * Transforms a PlanPro Signal to a Siteplan Signal
	 * 
	 * @param signalInfo the PlanPro signal
	 * @param feature the GeoPoint associated with the signal
	 * @returns a siteplan signal or null on failure
	 */
	def SignalMount transform(SignalInfo signalInfo) {
		var GEOKanteCoordinate point = getSignalObjectCoordinate(
			signalInfo.firstSignal)
		val effectiveRotation = point.effectiveRotation
		if (signalInfo.baseMount !== null) {
			// As a Signal_Befestigung does not specify a direction, 
			// use the direction of the first signal
			point = getSignalObjectCoordinate(signalInfo.baseMount)
		}

		val signalMount = SiteplanFactory.eINSTANCE.createSignalMount()
		signalMount.guid = signalInfo.signalGuid
		signalMount.position = positionService.transformPosition(point)
		// Signals are rotated according to their effective rotation
		signalMount.position.rotation = effectiveRotation

		signalMount.mountType = signalInfo.mountType
		// Transform each attached signal
		signalMount.attachedSignals.addAll(signalInfo.signals.map [
			transformAttachedSignal(it, signalInfo, signalMount)
		].filter[!screen.empty])

		// Discard empty signals
		if (signalMount.attachedSignals.empty)
			return null

		return signalMount
	}

	private def transformAttachedSignal(Signal ppsignal, SignalInfo signalInfo,
		SignalMount signalMount) {
		val it = SiteplanFactory.eINSTANCE.createSignal
		guid = ppsignal.identitaet.wert
		label = ppsignal.bezeichnung?.label
		role = ppsignal.signalRole
		system = ppsignal.signalSystem
		signalDirection = ppsignal.transformSignalDirection

		lateralDistance.addAll(ppsignal.punktObjektTOPKante.map [
			seitlicherAbstand?.wert
		])
		ppsignal.transformPunktObjektStrecke(it)

		// Determine mount offset & direction for Signalbrücke/Signalausleger	
		val coordinate = getSignalObjectCoordinate(ppsignal)
		mountPosition = positionService.transformPosition(coordinate);

		screen += ppsignal.signalScreen.filter [
			!SITEPLAN_HIDDEN_SCREENS.contains(screen)
		]

		return it
	}

	def Direction transformSignalDirection(Signal signal) {
		val wirkrichtung = signal.punktObjektTOPKante?.get(0).wirkrichtung.wert
		switch (wirkrichtung) {
			case ENUM_WIRKRICHTUNG_IN:
				return Direction.FORWARD
			case ENUM_WIRKRICHTUNG_GEGEN:
				return Direction.OPPOSITE
			default:
				return null
		}
	}

	def SignalMountType getMountType(SignalInfo info) {
		var mounts = info.mounts.sortBy[identitaet.wert].map [
			signalBefestigungAllg.befestigungArt.wert
		]

		if (mounts.length != 0) {
			// Filter for a specific mounting type 
			val specificMounts = mounts.map[mapToSiteplanMountType].filter [
				it != SignalMountType.SONDERKONSTRUKTION
			]

			if (specificMounts.length != 0) {
				var mount = specificMounts.head
				// If multiple signals are attached to a MAST, convert into MEHRERE_MASTEN  
				if (mount === SignalMountType.MAST && info.signals.length > 1) {
					mount = SignalMountType.MEHRERE_MASTEN
				}
				return mount

			}
			return SignalMountType.SONDERKONSTRUKTION
		}

		// Use the signal for mounting type		
		val signal = info.signals.sortBy[identitaet.wert].head

		return signal?.signalReal?.signalBefestigungsart?.wert?.
			mapToSiteplanMountType
	}

	def GEOKanteCoordinate getSignalObjectCoordinate(Punkt_Objekt punktObjekt) {
		val singlePoint = punktObjekt.punktObjektTOPKante.get(0);
		val distance = singlePoint.abstand.wert.doubleValue
		val direction = singlePoint.wirkrichtung?.wert
		val topKante = singlePoint.topKante
		val geoKante = trackService.getGeoKanteAt(topKante, topKante.TOPKnotenA,
			distance);
		var double lateralDistance = 0
		if (singlePoint.seitlicherAbstand?.wert !== null) {
			lateralDistance = singlePoint.seitlicherAbstand.wert.doubleValue
		} else {
			// Determine the track type 
			val segment = geoKante.getContainingSegment(distance)
			val trackType = segment.bereichObjekte.filter(Gleis_Art).map [
				gleisart?.wert
			].filterNull

			// Determine the object distance according to the local track type
			if (trackType.empty) {
				// No local track type. Default to 0 and record an error
				lateralDistance = 0
				val guid = punktObjekt.identitaet.wert
				val coordinate = geoKante.getCoordinate(distance,
					lateralDistance, direction)
				recordError(guid,
					String.format(ERROR_NO_LATERAL_POSITION, guid),
					positionService.transformPosition(coordinate))
				return coordinate
			}

			if (trackType.head === ENUMGleisart.ENUM_GLEISART_STRECKENGLEIS)
				lateralDistance = PUNKT_OBJEKT_LATERAL_DISTANCE_OTHER
			else
				lateralDistance = PUNKT_OBJEKT_LATERAL_DISTANCE_IN_STATION

			// If the object should be positioned to the left of the track, invert the lateral distance
			if (singlePoint.seitlicheLage?.wert ===
				ENUMLinksRechts.ENUM_LINKS_RECHTS_LINKS) {
				lateralDistance = -lateralDistance
			}
		}
		if (direction === ENUMWirkrichtung.ENUM_WIRKRICHTUNG_BEIDE) {
			// For Punkt_Objekte with a bilateral direction fall back to ENUM_WIRKRICHTUNG_IN
			// to orient the Punkt_Objekt along the track axis
			return geoKante.getCoordinate(distance, lateralDistance,
				ENUMWirkrichtung.ENUM_WIRKRICHTUNG_IN);
		}
		return geoKante.getCoordinate(distance, lateralDistance, direction);
	}

	def SignalMountType mapToSiteplanMountType(ENUMBefestigungArt mount) {
		switch (mount) {
			case ENUM_BEFESTIGUNG_ART_ANDERE,
			case ENUM_BEFESTIGUNG_ART_ANDERE_SONDERKONSTRUKTION,
			case ENUM_BEFESTIGUNG_ART_BAHNSTEIG,
			case ENUM_BEFESTIGUNG_ART_FUNDAMENT,
			case ENUM_BEFESTIGUNG_ART_KONSTRUKTIONSTEIL:
				return SignalMountType.SONDERKONSTRUKTION
			case ENUM_BEFESTIGUNG_ART_PRELLBOCK:
				return SignalMountType.GLEISABSCHLUSS
			case ENUM_BEFESTIGUNG_ART_WAND:
				return SignalMountType.WANDKONSTRUKTION
			case ENUM_BEFESTIGUNG_ART_DACH_DECKE:
				return SignalMountType.DECKENKONSTRUKTION
			case ENUM_BEFESTIGUNG_ART_SCHIENENFUSS:
				return SignalMountType.SCHIENENFUSS
			case ENUM_BEFESTIGUNG_ART_PFOSTEN_HOCH,
			case ENUM_BEFESTIGUNG_ART_PFOSTEN_NIEDRIG:
				return SignalMountType.PFOSTEN
			case ENUM_BEFESTIGUNG_ART_SONSTIGE,
			case ENUM_BEFESTIGUNG_ART_ARBEITSBUEHNE,
			case ENUM_BEFESTIGUNG_ART_PFAHL,
			case ENUM_BEFESTIGUNG_ART_OL_KETTENWERK,
			case ENUM_BEFESTIGUNG_ART_OL_MAST,
			case ENUM_BEFESTIGUNG_ART_RAHMEN,
			case ENUM_BEFESTIGUNG_ART_REGELANORDNUNG_MAST_HOCH,
			case ENUM_BEFESTIGUNG_ART_REGELANORDNUNG_MAST_NIEDRIG,
			case ENUM_BEFESTIGUNG_ART_REGELANORDNUNG_SONSTIGE_HOCH,
			case ENUM_BEFESTIGUNG_ART_REGELANORDNUNG_SONSTIGE_NIEDRIG,
			case ENUM_BEFESTIGUNG_ART_SONDERANORDNUNG_MAST_HOCH,
			case ENUM_BEFESTIGUNG_ART_SONDERANORDNUNG_MAST_NIEDRIG:
				return SignalMountType.MAST
			case ENUM_BEFESTIGUNG_ART_SIGNALAUSLEGER:
				return SignalMountType.SIGNALAUSLEGER_LINKS
			case ENUM_BEFESTIGUNG_ART_SIGNALBRUECKE:
				return SignalMountType.SIGNALBRUECKE
		}
	}

	def SignalMountType mapToSiteplanMountType(
		ENUMSignalBefestigungsart mount) {
		switch (mount) {
			case ENUM_SIGNAL_BEFESTIGUNGSART_ANDERE,
			case ENUM_SIGNAL_BEFESTIGUNGSART_SONDERKONSTRUKTION,
			case ENUM_SIGNAL_BEFESTIGUNGSART_FUNDAMENT:
				return SignalMountType.SONDERKONSTRUKTION
			case ENUM_SIGNAL_BEFESTIGUNGSART_MAST:
				return SignalMountType.MAST
			case ENUM_SIGNAL_BEFESTIGUNGSART_SIGNALAUSLEGER:
				return SignalMountType.SIGNALAUSLEGER_LINKS
			case ENUM_SIGNAL_BEFESTIGUNGSART_SIGNALBRUECKE:
				return SignalMountType.SIGNALBRUECKE
		}
	}

	private static def SignalRole getSignalRole(Signal signal) {
		val art = signal.signalReal?.signalRealAktivSchirm?.signalArt?.wert

		switch (art) {
			case ENUM_SIGNAL_ART_HAUPTSIGNAL,
			case ENUM_SIGNAL_ART_HAUPTSPERRSIGNAL,
			case ENUM_SIGNAL_ART_HAUPTSPERRSIGNAL_NE_14_LS:
				return SignalRole.MAIN
			case ENUM_SIGNAL_ART_VORSIGNAL,
			case ENUM_SIGNAL_ART_VORSIGNALWIEDERHOLER:
				return SignalRole.PRE
			case ENUM_SIGNAL_ART_MEHRABSCHNITTSSIGNAL,
			case ENUM_SIGNAL_ART_MEHRABSCHNITTSSPERRSIGNAL:
				return SignalRole.MULTI_SECTION
			case ENUM_SIGNAL_ART_ZUGDECKUNGSSIGNAL:
				return SignalRole.TRAIN_COVER
			case ENUM_SIGNAL_ART_SPERRSIGNAL:
				return SignalRole.LOCK
			case ENUM_SIGNAL_ART_ANDERE:
				return SignalRole.NONE
		}
	}

	private static def SignalSystem getSignalSystem(Signal signal) {
		val art = signal.signalReal?.signalRealAktivSchirm?.signalsystem?.wert

		switch (art) {
			case ENUM_SIGNALSYSTEM_HL:
				return SignalSystem.HL
			case ENUM_SIGNALSYSTEM_HV:
				return SignalSystem.HV
			case ENUM_SIGNALSYSTEM_KS:
				return SignalSystem.KS
			case ENUM_SIGNALSYSTEM_SONSTIGE:
				return SignalSystem.NONE
			case ENUM_SIGNALSYSTEM_SV:
				return SignalSystem.SV
		}
	}

	private static def List<SignalScreen> getSignalScreen(Signal signal) {
		return signal.signalRahmen.map [ rahmen |
			rahmen.signalbegriffe.map [ begriff |
				val signalScreen = SiteplanFactory.eINSTANCE.
					createSignalScreen()
				signalScreen.frameType = rahmen.rahmenArt.wert.toString
				signalScreen.screen = begriff.signalbegriffID.eClass.name
				signalScreen.switched = begriff?.signalSignalbegriffAllg?.
					geschaltet?.wert ?: false
				return signalScreen
			]
		].flatten.toList

	}

}
