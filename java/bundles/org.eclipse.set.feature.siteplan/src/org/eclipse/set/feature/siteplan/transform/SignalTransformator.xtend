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
import java.util.List
import org.eclipse.set.basis.geometry.GEOKanteCoordinate
import org.eclipse.set.core.services.geometry.PointObjectPositionService
import org.eclipse.set.feature.siteplan.positionservice.PositionService
import org.eclipse.set.model.planpro.Signale.ENUMBefestigungArt
import org.eclipse.set.model.planpro.Signale.ENUMSignalBefestigungsart
import org.eclipse.set.model.planpro.Signale.Signal
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
import org.eclipse.set.model.planpro.Signale.ENUMRahmenArt
import java.util.Set

import static extension org.eclipse.set.feature.siteplan.transform.TransformUtils.*
import static extension org.eclipse.set.ppmodel.extensions.SignalBefestigungExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.SignalExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.SignalRahmenExtensions.*
import org.eclipse.set.model.planpro.Signale.Signal_Befestigung

/**
 * Transforms PlanPro Signals to Siteplan Signals/SignalMounts
 * 
 * @author Stuecker
 */
@Component(service=Transformator)
class SignalTransformator extends BaseTransformator<Signal> {
	@Reference
	PointObjectPositionService pointObjectPositionService

	@Reference
	PositionService positionService

	static val ERROR_FAILED_TRANSFORM = "Fehler bei der Signaltransformation."

	// Signals not visible in the siteplan
	static val SITEPLAN_HIDDEN_SCREENS = #["Ra12", "Ts1", "Ts2", "Ts3", "Zp6",
		"Zp7", "Zp8", "El1", "El1v", "El2", "El3", "El4", "El5", "El6", "Ra13",
		"Wn7", "OzZf", "OzICE", "OzFak", "OzZugl", "Ne7a", "Ne7b", "Bue4",
		"Bue5", "OzAutoHET", "OzHET", "OzAutoET", "OzET"]

	var List<SignalInfo> signalinfo

	override initializeTransform() {
		signalinfo = new ArrayList<SignalInfo>
	}

	override transform(Signal signal) {
		val si = new SignalInfo
		si.signals = #[signal].toSet
		
		// add all mounts connected to SignalRahmen of this signal
		si.mounts = signal.signalRahmen?.map[signalBefestigung]?.toSet ?:
			newHashSet
		// add all parents (recursively) to si.mounts.
		val mountsWithParents = si.mounts.map [ mount |
					mount?.signalBefestigungen].flatten
		si.mounts = newHashSet(mountsWithParents)
		
		si.baseMount = signal.findBaseMount(si.mounts)
		signalinfo.add(si)
	}
	
	
	/**
	 * for a given signal, together with all mounts associated to that signal, returns the root mount if the signal.
	 * The root mount has no parents (so it is not attached to anything). 
	 * Think of it as the foundation in most signal assemblies
	 * A signal may have multiple of these mounts. 
	 * If so, return (an arbitrary) one of these, where the Schirm of that signal is attached to.
	 * the rootMount of a signal is used in the Lageplan to determine the geo-position where that signal should be drawn.
	 * 
	 * If mounts form a loop, return mount farthest away from schirm.
	 */
	private static def Signal_Befestigung findBaseMount(Signal signal, Set<Signal_Befestigung> mounts) {
		val mounts_with_no_parents = mounts.filter[signalBefestigung === null]		
		val mounts_with_schirm = signal.signalRahmen?.filter[rahmenArt?.getWert() === ENUMRahmenArt.ENUM_RAHMEN_ART_SCHIRM].map[signalBefestigung].filterNull

		//original definition: (0 mounts with no parent)
		if (mounts_with_no_parents.isEmpty) {
			return null
		}
		// original definition: (1 mount with no parent)
		// return any mount with no parent, if no mount with schirm exist. (same result as in previous implementation)
		if (mounts_with_no_parents.size() == 1 || mounts_with_schirm.isEmpty) {
			return mounts_with_no_parents.head
		}
		
		// If more then one mount like that exists, return the one with a schirm.	
		// If there are multiple ones, take any	
		var mount = mounts_with_schirm.head
		var visited = newHashSet()
		
		// walk up to root mount. If mounts form a loop, return mount farthest away from schirm.
		while (mount.signalBefestigung !== null && !visited.contains(mount.signalBefestigung)) {
			visited.add(mount)
			mount = mount.signalBefestigung
		}
		
		return mount
	}

	override finalizeTransform() {
		// For each mount, find all attached mounts
		// If two signals have a common final mount, they are mounted to the same
		// Signal_Befestigung structure (for example a foundation). Therefore merge the signalinfos
		val mergedSignalInfo = new ArrayList<SignalInfo>
		signalinfo.forEach [ si |
			try {
				val mergeWith = mergedSignalInfo.findFirst [ msi |
					msi.mounts.contains(si.baseMount)
				]
				if (mergeWith === null) {
					mergedSignalInfo.add(si)
				} else {
					mergeWith.signals += si.signals
					mergeWith.mounts += si.mounts
				}
			} catch (Exception exc) {
				System.out.println("Failed transformation: "+exc);
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
		var GEOKanteCoordinate point = pointObjectPositionService.getCoordinate(
			signalInfo.firstSignal)
		val effectiveRotation = point.effectiveRotation
		if (signalInfo.baseMount !== null) {
			// As a Signal_Befestigung does not specify a direction, 
			// use the direction of the first signal
			point = pointObjectPositionService.getCoordinate(
				signalInfo.baseMount)
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
		val coordinate = pointObjectPositionService.getCoordinate(ppsignal);
		mountPosition = positionService.transformPosition(coordinate);
		mountPosition.rotation = coordinate.effectiveRotation

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

	/**
	 * Original Definition:
	 * - if there are only Sonderkonstruktionen as mounts, return SONDERKONSTRUKTION
	 * - if there is one mount != Sonderkonstruktion, return that mounts BefestigungsArt.
	 * - if there is more then one mount != Sonderkonstruktion, take the mount with the lowest GUID:
 	 *		- then: if mount is mast and there are more then 1 signals attached to this assembly, return MehrereMasten
	 * - if there are no mounts:
	 * 		- take the signal with the lowest guid, and take the befestigungsArt from the signalReal
	 * 		- if signal/signalReal/signalReal.signalBefestigungsart null: return null
	 * 
	 * New Definition:
	 *  (ASSUMPTION: no Arbeitsbuehne without a Bridge / Ausleger (see changes in mapToSiteplanMountType))
	 * - if any specific mount is an SignalAuslegerLinks, return SignalAuslegerLinks.
	 */
	def SignalMountType getMountType(SignalInfo info) {
		var mounts = info.mounts.sortBy[identitaet.wert].map [
			signalBefestigungAllg.befestigungArt.wert
		]

		if (mounts.length != 0) {
			// Filter for a specific mounting type 
			val specificMounts = mounts.map[mapToSiteplanMountType].filter [
				it != SignalMountType.SONDERKONSTRUKTION
			]

			if (specificMounts.length == 1) {
				return specificMounts.head;
			}

			if (specificMounts.length > 1) {
				// there might be many different combinations, obviously.
				// I attempt to keep behaviour as before (even if not correct) and only change behaviour for SignalAusleger
				
				// SignalAusleger -> ... anything  		=> should be SignalAusleger
				// Fundament -> SignalAusleger 			=> should be SignalAusleger
				// Fundament -> Mast
				
				// new logic for any assembly containing a SignalAusleger. Same for all other cases
				if (!specificMounts.filter[it === SignalMountType.SIGNALAUSLEGER_LINKS].isEmpty) {
					return SignalMountType.SIGNALAUSLEGER_LINKS;
				}
				if (!specificMounts.filter[it === SignalMountType.SIGNALBRUECKE].isEmpty) {
					return SignalMountType.SIGNALBRUECKE;
				}
				
				
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

	def SignalMountType mapToSiteplanMountType(ENUMBefestigungArt mount) {
		switch (mount) {
			case ENUM_BEFESTIGUNG_ART_ANDERE,
			case ENUM_BEFESTIGUNG_ART_ANDERE_SONDERKONSTRUKTION,
			case ENUM_BEFESTIGUNG_ART_BAHNSTEIG,
			case ENUM_BEFESTIGUNG_ART_FUNDAMENT,
			case ENUM_BEFESTIGUNG_ART_ARBEITSBUEHNE,
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
				return SignalRole.SONSTIGE
			default:
				return signal.signalReal?.signalRealAktivSchirm !== null &&
					art === null ? return SignalRole.NONE : SignalRole.SONSTIGE
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
