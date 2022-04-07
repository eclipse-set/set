/**
 * Copyright (c) 2016 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.ppmodel.extensions

import org.eclipse.set.toolboxmodel.Ansteuerung_Element.Unterbringung
import org.eclipse.set.toolboxmodel.BasisTypen.ENUMWirkrichtung
import org.eclipse.set.toolboxmodel.Basisobjekte.Basis_Objekt
import org.eclipse.set.toolboxmodel.Basisobjekte.Punkt_Objekt
import org.eclipse.set.toolboxmodel.Basisobjekte.Punkt_Objekt_TOP_Kante_AttributeGroup
import org.eclipse.set.toolboxmodel.PZB.PZB_Element
import org.eclipse.set.toolboxmodel.PZB.PZB_Element_Zuordnung
import org.eclipse.set.toolboxmodel.Signale.Signal
import org.eclipse.set.ppmodel.extensions.utils.DirectedTopKante
import org.slf4j.Logger
import org.slf4j.LoggerFactory

import static org.eclipse.set.toolboxmodel.Signale.ENUMSignalArt.*

import static extension org.eclipse.set.ppmodel.extensions.ENUMWirkrichtungExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.PunktObjektExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.PunktObjektTopKanteExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.TopKanteExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.utils.Debug.*

/**
 * Extensions for {@link PZB_Element}.
 */
class PZBElementExtensions extends BasisObjektExtensions {

	static final Logger logger = LoggerFactory.getLogger(
		typeof(PZBElementExtensions));

	/**
	 * @param pzb this pzb
	 * 
	 * @return the PZB_Element_Zuordnung
	 */
	def static PZB_Element_Zuordnung getPZBElementZuordnung(PZB_Element pzb) {
		return pzb.IDPZBElementZuordnung
	}

	/**
	 * @param pzb this pzb
	 * 
	 * @return the Unterbringung
	 */
	def static Unterbringung getUnterbringung(PZB_Element pzb) {
		return pzb.IDUnterbringung
	}

	/**
	 * @param pzb this PZB Element
	 * 
	 * @return the Bezugspunkt of this PZB Element
	 */
	static def Basis_Objekt getBezugspunkt(PZB_Element pzb) {
		return pzb.PZBElementZuordnung?.IDPZBElementBezugspunkt
	}

	/**
	 * @param pzb this PZB Element
	 * 
	 * @return the INA Gefahrenstelle (or <code>null</code> if there is no INA Gefahrenstelle for this PZB Element)
	 */
	static def Basis_Objekt getInaGefStelle(PZB_Element pzb) {
		logger.debug('''getInaGefStelle(«pzb.debugString»)''')
		val bezugspunkt = pzb.bezugspunkt
		if (bezugspunkt instanceof Signal) {
			val topKanten = bezugspunkt.topKanten
			if (topKanten.size != 1) {
				logger.debug('''topKanten.size=«topKanten.size»''')
				return null
			}
			val topKante = topKanten.get(0)
			val wirkrichtung = bezugspunkt.getWirkrichtung(topKante)
			var boolean isForward
			switch (wirkrichtung) {
				case ENUM_WIRKRICHTUNG_IN:
					isForward = true
				case ENUM_WIRKRICHTUNG_GEGEN:
					isForward = false
				default: {
					logger.debug('''wirkrichtung=«wirkrichtung»''')
					return null
				}
			}
			val directedTopEdge = new DirectedTopKante(topKante, isForward)
			val edgePoints = directedTopEdge.iterator.toList
			val pointsAfterSignal = edgePoints.subList(
				edgePoints.indexOf(bezugspunkt.singlePoint), edgePoints.size).
				filter [
					it.punktObjekt.identitaet.wert !=
						bezugspunkt.identitaet.wert
				]
			val inaGefahrPunkt = pointsAfterSignal.
				getInaGefahrPunkt(wirkrichtung)
			if (inaGefahrPunkt === null) {
				if (isForward) {
					return topKante.TOPKnotenB
				} else {
					return topKante.TOPKnotenA
				}
			}
			return inaGefahrPunkt
		}
		logger.
			debug('''bezugspunkt.class.simpleName=«bezugspunkt.class.simpleName»''')
		return null
	}

	private static def Punkt_Objekt getInaGefahrPunkt(
		Iterable<Punkt_Objekt_TOP_Kante_AttributeGroup> singlePoints,
		ENUMWirkrichtung wirkrichtung) {
		if (singlePoints.empty) {
			return null
		}
		val head = singlePoints.head
		val tail = singlePoints.tail

		val punktObjekt = head.punktObjekt
		if (punktObjekt.grenzzeichen) {
			return punktObjekt
		}
		if (punktObjekt.isSignalInRichtung(wirkrichtung.inverted)) {
			return punktObjekt
		}
		if (punktObjekt.achszaehlpunkt) {
			return punktObjekt
		}
		return tail.getInaGefahrPunkt(wirkrichtung)
	}

	private static def boolean isSignalInRichtung(Punkt_Objekt punktObjekt,
		ENUMWirkrichtung wirkrichtung) {
		if (punktObjekt instanceof Signal) {
			val singlePoints = punktObjekt.singlePoints
			if (singlePoints.size != 1) {
				return false
			}
			if (singlePoints.get(0).wirkrichtung?.wert != wirkrichtung) {
				return false
			}
			return #{
				ENUM_SIGNAL_ART_HAUPTSIGNAL,
				ENUM_SIGNAL_ART_HAUPTSPERRSIGNAL,
				ENUM_SIGNAL_ART_MEHRABSCHNITTSSIGNAL,
				ENUM_SIGNAL_ART_MEHRABSCHNITTSSPERRSIGNAL,
				ENUM_SIGNAL_ART_SPERRSIGNAL
			}.contains(
				punktObjekt.signalReal?.signalRealAktivSchirm?.signalArt?.wert
			)
		}
		return false
	}
}
