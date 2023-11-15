/**
 * Copyright (c) 2015 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.ppmodel.extensions

import java.math.BigDecimal
import java.util.Collections
import java.util.List
import java.util.Set
import org.eclipse.core.runtime.Assert
import org.eclipse.set.basis.graph.Digraph
import org.eclipse.set.basis.graph.Digraphs
import org.eclipse.set.ppmodel.extensions.utils.DirectedTopKante
import org.eclipse.set.ppmodel.extensions.utils.TopRouting
import org.eclipse.set.toolboxmodel.Ansteuerung_Element.Stellelement
import org.eclipse.set.toolboxmodel.Ansteuerung_Element.Unterbringung
import org.eclipse.set.toolboxmodel.Basisobjekte.Punkt_Objekt
import org.eclipse.set.toolboxmodel.Basisobjekte.Punkt_Objekt_TOP_Kante_AttributeGroup
import org.eclipse.set.toolboxmodel.Fahrstrasse.Fstr_Zug_Rangier
import org.eclipse.set.toolboxmodel.Flankenschutz.Fla_Schutz
import org.eclipse.set.toolboxmodel.Geodaten.TOP_Kante
import org.eclipse.set.toolboxmodel.Geodaten.TOP_Knoten
import org.eclipse.set.toolboxmodel.Gleis.Gleis_Bezeichnung
import org.eclipse.set.toolboxmodel.Ortung.Schaltmittel_Zuordnung
import org.eclipse.set.toolboxmodel.Signalbegriffe_Ril_301.Zs3v
import org.eclipse.set.toolboxmodel.Signalbegriffe_Struktur.Signalbegriff_ID_TypeClass
import org.eclipse.set.toolboxmodel.Signale.Signal
import org.eclipse.set.toolboxmodel.Signale.Signal_Befestigung
import org.eclipse.set.toolboxmodel.Signale.Signal_Rahmen
import org.eclipse.set.toolboxmodel.Signale.Signal_Signalbegriff
import org.eclipse.set.toolboxmodel.Weichen_und_Gleissperren.W_Kr_Gsp_Element
import org.slf4j.Logger
import org.slf4j.LoggerFactory

import static org.eclipse.set.toolboxmodel.Ansteuerung_Element.ENUMAussenelementansteuerungArt.*
import static org.eclipse.set.toolboxmodel.BasisTypen.ENUMWirkrichtung.*
import static org.eclipse.set.toolboxmodel.Signale.ENUMSignalFunktion.*

import static extension org.eclipse.set.ppmodel.extensions.AussenelementansteuerungExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.FahrwegExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.FstrZugRangierExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.PunktObjektTopKanteExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.SignalRahmenExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.SignalbegriffExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.StellelementExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.utils.CollectionExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.utils.Debug.*
import static extension org.eclipse.set.basis.graph.Digraphs.*
import org.eclipse.set.ppmodel.extensions.utils.TopGraph

/**
 * This class extends {@link Signal}.
 */
class SignalExtensions extends PunktObjektExtensions {

	static val Logger logger = LoggerFactory.getLogger(typeof(SignalExtensions))

	static val double ABSTAND_VORSIGNALBAKE = 260

	static val String PREFIX_VORSIGNALBAKE = "Ne3"

	/**
	 * @param signal this Signal
	 * 
	 * @returns list of Fla_Schutz elements
	 */
	def static List<Fla_Schutz> flaSchutz(
		Signal signal
	) {
		return signal.container.flaSchutz.filter [
			flaSchutzSignal !== null &&
				flaSchutzSignal.IDFlaSignal.identitaet.wert ==
					signal.identitaet.wert
		].toList
	}

	/**
	 * checks if the given signal is start signal of any route.
	 * @param signal this Signal
	 * 
	 * @returns boolean
	 */
	def static boolean isStartOfAnyTrainRoute(Signal signal) {
		for (Fstr_Zug_Rangier zugRangier : signal.container.fstrZugRangier) {
			if (zugRangier?.fstrZug !== null &&
				zugRangier?.fstrFahrweg?.start?.identitaet?.wert.equals(
					signal?.identitaet?.wert)) {
				return true
			}
		}
		return false
	}

	/**
	 * @param signal this Signal
	 * @param isStart whether the wanted end is the start of a shunting route
	 * (true) or the destination (false)
	 * 
	 * @return whether the given signal is the given end of any shunting route
	 */
	def static boolean isStartOrDestinationOfAnyShuntingRoute(Signal signal,
		boolean isStart) {
		for (Fstr_Zug_Rangier zugRangier : signal.container.fstrZugRangier) {
			var String IDEnd
			if (isStart) {
				IDEnd = zugRangier?.fstrFahrweg?.IDStart?.identitaet?.wert
			} else {
				IDEnd = zugRangier?.fstrFahrweg?.IDZiel?.identitaet?.wert
			}
			if (zugRangier?.fstrRangier !== null &&
				signal.identitaet.wert.equals(IDEnd)) {
				return true
			}
		}
		return false
	}

	/**
	 * @param signal this Signal
	 * 
	 * @returns list of Signal_Rahmen elements
	 */
	def static List<Signal_Rahmen> signalRahmen(Signal signal) {
		return signal.container.signalRahmen.filter[r|r.signal == signal].toList
	}

	/**
	 * @param signal this signal
	 * 
	 * @return whether this signal is an alleinstehendes Zusatzsignal
	 */
	def static boolean isAlleinstehendesZusatzsignal(Signal signal) {
		val signalFunktion = signal.signalReal?.signalFunktion?.wert
		val signalbegriffe = signal.signalbegriffe
		return signalFunktion ===
			ENUM_SIGNAL_FUNKTION_ALLEINSTEHENDES_ZUSATZSIGNAL &&
			signalbegriffe.containsSignalbegriffID(Zs3v)
	}

	def static boolean isInWirkrichtungOfSignal(TopGraph topGraph,
		Signal signal, Punkt_Objekt object) {
		return topGraph.getPaths(signal.singlePoints, object.singlePoints).
			flatMap[edges].forall[isForwards == true]

	}

	/**
	 * @param signal this Signal
	 * @param gruppe the Befestigungsgruppe
	 * 
	 * @returns list of Signal Rahmen with Signalbefestigungen contained in the
	 * given Befestigungsgruppe
	 */
	def static List<Signal_Rahmen> signalRahmenForBefestigung(Signal signal,
		List<Signal_Befestigung> gruppe) {
		return signal.signalRahmen.filter [
			gruppe.map[identitaet.wert].contains(
				IDSignalBefestigung.identitaet?.wert)
		].toList
	}

	/**
	 * Returns the rotation for the signal on the siteplan (counterclockwise
	 * in degrees) at the first coordinate found for the single point
	 * of the Signal.
	 * 
	 * @param signal this Signal
	 * 
	 * @returns the rotation
	 */
	def static double rotation(Signal signal) {
		// we ignore all coordinates but the first
		return signal.singlePoints.get(0).coordinate.getEffectiveRotation
	}

	/**
	 * @param signal this Signal
	 * 
	 * @returns the Vorsignalbaken of this signal
	 */
	def static List<Signal> getVorsignalbaken(Signal signal) {
		val topKanten = signal.topKanten
		Assert.isTrue(topKanten.size == 1)
		val topKante = topKanten.get(0)
		val wirkrichtung = signal.getWirkrichtung(topKante)
		val isForward = wirkrichtung == ENUM_WIRKRICHTUNG_GEGEN
		val DirectedTopKante start = new DirectedTopKante(topKante, isForward)
		val paths = Digraphs.getPaths(start, new TopRouting,
			ABSTAND_VORSIGNALBAKE)
		paths.forEach[start = signal.singlePoint]
		if (logger.debugEnabled) {
			val bezeichnung = signal?.bezeichnung?.bezeichnungTabelle?.wert
			logger.debug('''Searchpaths for Vorsignalbaken of «bezeichnung»:''')
			logger.debug(paths.debugString)
		}
		return paths.map [
			val itsPath = it
			pointIterator.map[punktObjekt].filter(Signal).filter [
				itsPath.distance(signal.singlePoint, singlePoint) <
					ABSTAND_VORSIGNALBAKE
			].filter[vorsignalbake].toList
		].flatten.toList
	}

	/**
	 * @param signal this signal
	 * 
	 * @return set of all Signalbegriffe of all Signalrahmen of this signal
	 */
	def static Set<Signalbegriff_ID_TypeClass> getSignalbegriffIds(
		Signal signal) {
		return signal.signalRahmen.map[signalbegriffe].flatten.map [
			signalbegriffID
		].toSet
	}

	/**
	 * @param signal this signal
	 * 
	 * @return set of all Signalbegriffe of all Signalrahmen of this signal
	 */
	def static Set<Signal_Signalbegriff> getSignalbegriffe(Signal signal) {
		return signal.signalRahmen.map[signalbegriffe].flatten.toSet
	}

	/**
	 * @param signal this signal
	 * 
	 * @return whether this signal is a Vorsignalbake
	 */
	def static boolean isVorsignalbake(Signal signal) {
		return signal.signalbegriffIds.exists [
			typeName.startsWith(PREFIX_VORSIGNALBAKE)
		]
	}

	/**
	 * @param signal this signal
	 * 
	 * @return all Gleisschaltmittel for the Anrückverschluss
	 */
	def static List<Schaltmittel_Zuordnung> getAnrueckverschluss(
		Signal signal
	) {
		return signal?.signalFstrS?.IDAnrueckverschluss ?: Collections.emptyList
	}

	/**
	 * @param signal this signal
	 * @param digraph the digraph
	 * @param signalBefestigung the Befestigung of signal
	 * 
	 * @return the Überhöhung at the signal; or {@code null}, if the signal does
	 * not lie within a BankingInterval
	 */
	def static BigDecimal getUeberhoehung(
		Signal signal,
		Digraph<TOP_Kante, TOP_Knoten, Punkt_Objekt_TOP_Kante_AttributeGroup> digraph,
		Signal_Befestigung signalBefestigung
	) {
		return signal.getBankingInterval(digraph, signalBefestigung)?.banking
	}

	/**
	 * @param signal this signal
	 * @param digraph the digraph
	 * @param signalBefestigung the Befestigung of signal
	 * 
	 * @return the BankingInterval of this signal; or {@code null}, if the
	 * signal does not lie within a BankingInterval
	 */
	def static BankingInterval getBankingInterval(
		Signal signal,
		Digraph<TOP_Kante, TOP_Knoten, Punkt_Objekt_TOP_Kante_AttributeGroup> digraph,
		Signal_Befestigung signalBefestigung
	) {
		return BankingInterval.createInterval(
			signal.getMountPoint(signalBefestigung), digraph)
	}

	/**
	 * @param signal this signal
	 * 
	 * @return the Schaltkasten for this Signal; or {@code null}, if this
	 * signal has no Schaltkasten
	 */
	def static Unterbringung getControlBox(Signal signal) {
		val energie = signal.realAktivStellelement?.energie
		if (energie?.AEAAllg?.aussenelementansteuerungArt?.wert ===
			ENUM_AUSSENELEMENTANSTEUERUNG_ART_OBJEKTCONTROLLER) {
			return energie.unterbringung
		}
		return null
	}

	/**
	 * @param signal this signal
	 * 
	 * @return the Signal Real Aktiv Stellelement
	 */
	def static Stellelement getRealAktivStellelement(Signal signal) {
		return signal.signalReal?.signalRealAktiv?.IDStellelement
	}

	/**
	 * @param signal this signal
	 * 
	 * @return the Stellelement of this Signal (or <code>null</code> if it does not exists) 
	 */
	def static Stellelement getStellelement(
		Signal signal
	) {
		val signalRealAktiv = signal.signalReal?.signalRealAktiv
		return signalRealAktiv?.IDStellelement
	}

	/**
	 * @param signal this Grenzzeichen
	 * 
	 * @return the Weiche of this Grenzzeichen (or <code>null</code> if no Weiche is found)
	 */
	def static W_Kr_Gsp_Element getWKrGspElement(Signal signal) {
		val weichenElemente = signal.container.WKrGspElement.filter [
			weicheElement?.IDGrenzzeichen?.identitaet?.wert ==
				signal.identitaet.wert
		]
		if (weichenElemente.empty) {
			logger.
				debug('''No weicheElement.IDGrenzzeichen found for Signal «signal.identitaet.wert».''')
			return null
		}
		if (weichenElemente.size == 1) {
			return weichenElemente.get(0)
		}
		throw new IllegalArgumentException(
			'''Signal «signal.identitaet.wert» is Grenzzeichen for {«FOR e : weichenElemente SEPARATOR ", "»«e.bezeichnung?.bezeichnungTabelle?.wert»«ENDFOR»}'''
		)
	}

	/**
	 * @param signal this Signal
	 * 
	 * @return whether this Signal has the given Signalbegriff ID
	 */
	def static <T extends Signalbegriff_ID_TypeClass> boolean hasSignalbegriffID(
		Signal signal, Class<T> type) {
		return signal.signalbegriffe.containsSignalbegriffID(type)
	}

	/**
	 * @param signal this Signal
	 * @param type the Signalbegriff ID
	 * 
	 * @return the Signalbegriffe of the given Signal with the given Signalbegriff ID
	 */
	def static <T extends Signalbegriff_ID_TypeClass> Set<Signal_Signalbegriff> getSignalbegriffe(
		Signal signal,
		Class<T> type
	) {
		return signal.signalbegriffe.filter[hasSignalbegriffID(type)].toSet
	}

	/**
	 * @param signal this Signal
	 * 
	 * @return Gleisbezeichnungen des Inselgleises, von dem aus eine
	 * gleichzeitige Rangierfahrt aus dem Inselgleis untersagt ist
	 */
	def static Set<Gleis_Bezeichnung> getRaFahrtGleichzeitigVerbot(
		Signal signal
	) {
		return (
			signal?.signalFstrAusInselgleis?.IDRaFahrtGleichzeitigVerbot ?:
			Collections.emptySet
		).toSet
	}

	/**
	 * @param signal this Signal
	 * 
	 * @return Gleisbezeichnungen des Inselgleises, von dem aus eine
	 * gleichzeitige Zugfahrt aus dem Inselgleis untersagt ist
	 */
	def static Set<Gleis_Bezeichnung> getZgFahrtGleichzeitigVerbot(
		Signal signal
	) {
		return (
			signal?.signalFstrAusInselgleis?.IDZgFahrtGleichzeitigVerbot ?:
			Collections.emptySet
		).toSet
	}

	/**
	 * @param signal this Signal
	 * 
	 * @return Gleisschaltmittel, dessen Befahrung als zweites Haltfallkriterium dient 
	 */
	static def Schaltmittel_Zuordnung getZweitesHaltfallkriterium(
		Signal signal
	) {
		return signal?.signalFstrS?.IDZweitesHaltfallkriterium
	}

	/**
	 * @param signal this Signal
	 * @param signalBefestigung the Befestigung of Signal
	 * 
	 * @return the mount point for the given Signal and Befestigung
	 */
	def static Punkt_Objekt_TOP_Kante_AttributeGroup getMountPoint(
		Signal signal, Signal_Befestigung signalBefestigung) {
		return signal.singlePoints.filter [ e |
			e.topKante.identitaet ===
				signalBefestigung.singlePoints.unique.topKante.identitaet
		].toList.unique
	}
}
