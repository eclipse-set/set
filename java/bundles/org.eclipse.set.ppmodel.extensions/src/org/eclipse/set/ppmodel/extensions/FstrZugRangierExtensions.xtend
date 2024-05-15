/**
 * Copyright (c) 2015 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.ppmodel.extensions

import java.math.BigInteger
import java.util.LinkedList
import java.util.List
import java.util.Set
import org.eclipse.set.basis.graph.DirectedEdgePoint
import org.eclipse.set.model.planpro.Bahnuebergang.BUE_Anlage
import org.eclipse.set.model.planpro.Basisobjekte.Punkt_Objekt_TOP_Kante_AttributeGroup
import org.eclipse.set.model.planpro.Fahrstrasse.Fstr_DWeg
import org.eclipse.set.model.planpro.Fahrstrasse.Fstr_Fahrweg
import org.eclipse.set.model.planpro.Fahrstrasse.Fstr_Nichthaltfall
import org.eclipse.set.model.planpro.Fahrstrasse.Fstr_Rangier_Fla_Zuordnung
import org.eclipse.set.model.planpro.Fahrstrasse.Fstr_Signalisierung
import org.eclipse.set.model.planpro.Fahrstrasse.Fstr_Zug_Art_TypeClass
import org.eclipse.set.model.planpro.Fahrstrasse.Fstr_Zug_Rangier
import org.eclipse.set.model.planpro.Geodaten.TOP_Kante
import org.eclipse.set.model.planpro.Geodaten.TOP_Knoten
import org.eclipse.set.model.planpro.Gleis.ENUMGleisart
import org.eclipse.set.model.planpro.Gleis.Gleis_Abschnitt
import org.eclipse.set.model.planpro.Ortung.FMA_Anlage
import org.eclipse.set.model.planpro.Signalbegriffe_Ril_301.Kl
import org.eclipse.set.model.planpro.Signale.Signal
import org.eclipse.set.model.planpro.Signale.Signal_Signalbegriff
import org.eclipse.set.model.planpro.Weichen_und_Gleissperren.Kreuzung_AttributeGroup
import org.eclipse.set.model.planpro.Weichen_und_Gleissperren.W_Kr_Gsp_Komponente
import org.eclipse.set.model.planpro.Weichen_und_Gleissperren.Zungenpaar_AttributeGroup
import org.eclipse.set.ppmodel.extensions.utils.CrossingRoute
import org.eclipse.set.ppmodel.extensions.utils.GestellteWeiche
import org.eclipse.set.ppmodel.extensions.utils.WeichenSchenkel

import static org.eclipse.set.model.planpro.Signale.ENUMSignalArt.*

import static extension org.eclipse.set.ppmodel.extensions.BueAnlageExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.BueEinschaltungZuordnungExtension.*
import static extension org.eclipse.set.ppmodel.extensions.BueGleisbezogenerGefahrraumExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.ENUMWirkrichtungExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.FahrwegExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.FstrSignalisierungExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.PunktObjektTopKanteExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.SignalExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.SignalRahmenExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.SignalbegriffExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.WKrGspKomponenteExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.utils.IterableExtensions.*
import static extension org.eclipse.set.utils.math.BigIntegerExtensions.*

/**
 * This class extends {@link Fstr_Zug_Rangier}.
 */
class FstrZugRangierExtensions extends BasisObjektExtensions {

	/**
	 * @param fstr this Fstr_Zug_Rangier
	 * 
	 * @returns the Signalisierung
	 */
	def static List<Fstr_Signalisierung> fstrSignalisierung(
		Fstr_Zug_Rangier fstr
	) {
		val result = new LinkedList<Fstr_Signalisierung>
		val signalisierungen = fstr.container.fstrSignalisierung
		for (signalisierung : signalisierungen) {
			if (signalisierung.IDFstrZugRangier?.value?.identitaet?.wert ==
				fstr.identitaet.wert) {
				result.add(signalisierung)
			}
		}
		return result
	}

	/**
	 * @param zugRangier this Fstr_Zug_Rangier
	 * 
	 * @returns the Durchrutschweg
	 */
	def static Fstr_DWeg getFstrDWeg(
		Fstr_Zug_Rangier zugRangier
	) {
		return zugRangier?.fstrZug?.fstrZugDWeg?.IDFstrDWeg?.value
	}

	/**
	 * @param zugRangier this Fstr_Zug_Rangier
	 * 
	 * @returns the Fahrweg
	 */
	def static Fstr_Fahrweg getFstrFahrweg(
		Fstr_Zug_Rangier zugRangier
	) {
		return zugRangier.IDFstrFahrweg?.value
	}

	/**
	 * @param fstrZugRangier this Fstr_Zug_Rangier
	 * 
	 * @returns the Fstr_Rangier_Fla_Zuordnung
	 */
	def static Iterable<Fstr_Rangier_Fla_Zuordnung> getFstrRangierFlaZuordnung(
		Fstr_Zug_Rangier fstrZugRangier) {
		return fstrZugRangier.container.fstrRangierFlaZuordnung.filter [
			IDFstrRangier?.value === fstrZugRangier
		]
	}

	/**
	 * @param fstrZugRangier this Fstr_Zug_Rangier
	 * 
	 * @returns the Fstr_Nichthaltfall
	 */
	def static List<Fstr_Nichthaltfall> getFstrNichthaltfall(
		Fstr_Zug_Rangier fstrZugRangier) {
		return fstrZugRangier.container.fstrNichthaltfall.filter [
			it.IDFstrFahrweg?.value.identitaet?.wert ==
				fstrZugRangier.IDFstrFahrweg?.value.identitaet?.wert
		].toList
	}

	def static List<GestellteWeiche> getRangierEntscheidungsweichen(
		Fstr_Zug_Rangier fstrZugRangier) {
		val fstrFahrweg = fstrZugRangier.fstrFahrweg

		// Find all shunt routes
		val fstrZugRangierAll = fstrZugRangier.container.fstrZugRangier.filter [
			fstrRangier?.fstrRangierArt?.wert?.literal?.substring(0, 1) == "R"
		].map[it.fstrFahrweg]

		// Check if at least two Fstr_Fahrweg exist for this path. If not
		// no Entscheidungsweiche may be present
		if (!fstrZugRangierAll.exists [
			it !== fstrFahrweg && IDStart.value === fstrFahrweg.IDStart.value &&
				IDZiel.value === fstrFahrweg.IDZiel.value
		]) {
			return newArrayList
		}

		// Defer to the topological path search
		return getEntscheidungsweichen(fstrZugRangier, newArrayList)
	}

	/**
	 * @param fstrZugRangier this Fstr_Zug_Rangier
	 * 
	 * @return list of Entscheidungsweichen for this Fstr_Zug_Rangier
	 */
	def static List<GestellteWeiche> getEntscheidungsweichen(
		Fstr_Zug_Rangier fstrZugRangier, List<ENUMGleisart> notUsable) {

		val fstrFahrweg = fstrZugRangier.fstrFahrweg

		val path = fstrFahrweg.path
		val fwSinglePoints = path.pointIterator.toList
		val wKomponenten = fwSinglePoints.map[punktObjekt].filter(
			W_Kr_Gsp_Komponente).filter[hasZungenpaar].toUniqueList

		val weichen = wKomponenten.map [
			fstrFahrweg.getEntscheidungsweiche(it, notUsable)
		].filterNull.toUniqueList
		return weichen
	}

	/**
	 * @param fstrZugRangier this Fstr_Zug_Rangier
	 * 
	 * @return Fahrweggeschwindigkeit at Startsignal in km/h (Integer.MAX_VALUE means "MAX")
	 */
	def static int geschwindigkeit(Fstr_Zug_Rangier fstrZugRangier) {
		var int vmin = -1
		val fw = fstrZugRangier.fstrFahrweg
		val vmax = fw.path.pointIterator.map[punktObjekt].map[getVmax(fw)].
			filter[it >= 0].toList
		vmax.addAll(fw.gleisabschnitte.map[getVmax(fw)].filter[it >= 0])
		if (!vmax.empty) {
			vmin = vmax.min
		}
		if (vmin < 0) {
			return Integer.MAX_VALUE
		} else {
			return vmin;
		}
	}

	/**
	 * @param fstrZugRangier this Fstr_Zug_Rangier
	 * @param signalbegriff the Signalbegriff
	 * 
	 * @return whether the Signalbegriff is a Signalbegriff im Fahrweg
	 */
	def static boolean isImFahrweg(Fstr_Zug_Rangier fstrZugRangier,
		Signal_Signalbegriff signalbegriff) {
		return fstrZugRangier.fstrFahrweg.signalbegriffeImFahrweg.contains(
			signalbegriff)
	}

	def static Set<BUE_Anlage> getBUesImGefahrraum(
		Fstr_Zug_Rangier fstrZugRangier) {
		return fstrZugRangier.container.BUEAnlage.filter [
			gleisbezogeneGefahrraeume.map [
				einschaltungZuordnungen
			].flatten.toSet.map[einschaltung].exists [
				fstrZugRangier.fstrZug.IDBUEEinschaltung.map [
					value.identitaet.wert
				].contains(
					identitaet.wert
				)
			]
		].toSet
	}

	/**
	 * @param fstrZugRangier this Fstr Zug Rangier
	 * 
	 * @return the Vorsignalisierung of this Fstr Zug Rangier
	 */
	def static List<Signal> getVorsignalisierung(
		Fstr_Zug_Rangier fstrZugRangier) {
		return fstrZugRangier.fstrFahrweg.path.edgePointIterator.filter [
			fstrZugRangier.isVorsignalisierung(it)
		].map[point].map[punktObjekt].filter(Signal).toList
	}

	/**
	 * @param fstrZugRangier this Fstr Zug Rangier
	 * @param signal a Signal
	 * 
	 * @return whether the Signal indicates Kennlicht for this Fstr Zug Rangier
	 */
	def static boolean isKennlichtSignalisierung(
		Fstr_Zug_Rangier fstrZugRangier, Signal signal) {
		return fstrZugRangier.fstrSignalisierung.filter [
			signalSignalbegriff.hasSignalbegriffID(Kl)
		].exists [
			signalSignalbegriff.signalRahmen.signal.identitaet.wert ==
				signal.identitaet.wert
		]
	}

	/**
	 * @param fstrZugRangier this Fstr Zug Rangier
	 * 
	 * @return die FMA Anlagen, für die eine positive Gleisfreimeldung vor Sh 1/Ra 12-Stellung für
	 * die Rangierstraße notwendig sind
	 */
	def static Set<FMA_Anlage> getFmaAnlageRangierFrei(
		Fstr_Zug_Rangier fstrZugRangier
	) {
		return fstrZugRangier?.fstrRangier?.IDFMAAnlageRangierFrei?.map[value]?.
			filterNull?.toSet
	}

	def static <T> String getFstrZugRangierBezeichnung(
		Fstr_Zug_Rangier fstrZugRangier) {
		if (fstrZugRangier?.fstrZug !== null) {
			return fstrZugRangier.getZugFstrBezeichnung[isZ]
		}

		if (fstrZugRangier?.fstrRangier !== null) {
			return fstrZugRangier.getRangierFstrBezeichnung[isR]
		}
		return null
	}

	def static String getRangierFstrBezeichnung(Fstr_Zug_Rangier fstrZugRangier,
		(Fstr_Zug_Rangier)=>boolean condition) {
		if (!condition.apply(fstrZugRangier)) {
			return null
		}
		val fstrFahrwegStartSignalBezeichnung = fstrZugRangier.fstrFahrweg?.
			start?.bezeichnung?.bezeichnungTabelle?.wert ?: ""
		val fstrFahrwegZielSignalBezeichnung = fstrZugRangier.fstrFahrweg?.
			zielSignal?.bezeichnung?.bezeichnungTabelle?.wert ?: ""
		val fstrReihenfolge = fstrZugRangier?.fstrZugRangierAllg?.
			fstrReihenfolge?.wert.compareTo(BigInteger.ZERO)
		if (fstrReihenfolge === 0) {
			return '''«fstrFahrwegStartSignalBezeichnung»/«fstrFahrwegZielSignalBezeichnung»'''
		}
		if (fstrReihenfolge > 0) {
			return '''«fstrFahrwegStartSignalBezeichnung»/«fstrFahrwegZielSignalBezeichnung» [U«fstrZugRangier.fstrZugRangierAllg.fstrReihenfolge.wert.toString»]'''
		}
	}

	def static String getZugFstrBezeichnung(Fstr_Zug_Rangier fstrZugRangier,
		(Fstr_Zug_Art_TypeClass)=>boolean condition) {
		if (!condition.apply(fstrZugRangier?.fstrZug?.fstrZugArt)) {
			return null
		}

		if (fstrZugRangier?.fstrZug?.fstrZugDWeg === null ||
			fstrZugRangier?.fstrDWeg?.fstrDWegSpezifisch === null) {
			if (fstrZugRangier?.fstrZugRangierAllg?.fstrReihenfolge?.wert ==
				BigInteger.ZERO) {
				return fstrZugRangier?.fstrFahrweg?.transformFahrwegStartZiel
			}

			if (fstrZugRangier?.fstrZugRangierAllg?.fstrReihenfolge?.wert.
				isNotNullAndGreater(BigInteger.ZERO)) {
				return '''«fstrZugRangier?.fstrFahrweg?.
					transformFahrwegStartZiel» [U«fstrZugRangier?.
					fstrZugRangierAllg?.fstrReihenfolge?.wert»]'''
			}
		}

		if (fstrZugRangier?.fstrZug?.fstrZugDWeg !== null) {
			if (fstrZugRangier?.fstrZugRangierAllg?.fstrReihenfolge?.wert ==
				BigInteger.ZERO) {
				return '''«fstrZugRangier?.fstrFahrweg?.
					transformFahrwegStartZiel» («fstrZugRangier?.
					fstrDWeg?.bezeichnung?.bezeichnungFstrDWeg?.wert»)'''

			}

			if (fstrZugRangier?.fstrZugRangierAllg?.fstrReihenfolge?.wert.
				isNotNullAndGreater(BigInteger.ZERO)) {
				return '''«fstrZugRangier?.fstrFahrweg?.
					transformFahrwegStartZiel» [U«fstrZugRangier?.
					fstrZugRangierAllg?.fstrReihenfolge?.wert»] («fstrZugRangier?.
					fstrDWeg?.bezeichnung?.bezeichnungFstrDWeg?.wert»)'''

			}
		}
		return null
	}

	private def static boolean isVorsignalisierung(
		Fstr_Zug_Rangier fstrZugRangier,
		DirectedEdgePoint<TOP_Kante, TOP_Knoten, Punkt_Objekt_TOP_Kante_AttributeGroup> edgePoint) {

		// has the point the same Wirkrichtung than the Fahrstraße?
		if (!edgePoint.edge.contains(edgePoint.point)) {
			return false
		}
		if (!edgePoint.point?.wirkrichtung?.wert.
			isInWirkrichtung(edgePoint.edge)) {
			return false
		}

		// test the correct type of the point
		val punktObjekt = edgePoint.point.punktObjekt
		if (!(punktObjekt instanceof Signal)) {
			return false
		}
		val signal = punktObjekt as Signal
		val signalArt = signal.signalReal?.signalRealAktivSchirm?.signalArt?.
			wert
		if (signalArt !== ENUM_SIGNAL_ART_VORSIGNAL &&
			signalArt !== ENUM_SIGNAL_ART_VORSIGNALWIEDERHOLER &&
			!signal.isAlleinstehendesZusatzsignal) {
			return false
		}

		// test whether the signal has Kennlicht for the Fahrstraße
		if (fstrZugRangier.isKennlichtSignalisierung(signal)) {
			return false
		}

		return true
	}

	def static String transformFahrwegStartZiel(Fstr_Fahrweg fahrweg) {
		return '''«fahrweg?.start?.bezeichnung?.bezeichnungTabelle?.wert»/«fahrweg?.zielSignal?.bezeichnung?.bezeichnungTabelle?.wert»'''
	}

	def static boolean isZOrGz(Fstr_Zug_Art_TypeClass fstrZugArt) {
		val rangierArt = fstrZugArt?.wert?.literal
		return rangierArt.matches(
			"Z.*"
		) || rangierArt.matches(
			"GZ.*"
		)
	}

	def static boolean isZ(
		Fstr_Zug_Art_TypeClass typeClazz
	) {
		val lit = typeClazz?.wert?.literal
		return lit !== null && lit.matches("Z.*")
	}

	def static boolean isR(Fstr_Zug_Rangier fstrZugRangier) {
		return fstrZugRangier?.fstrRangier?.fstrRangierArt?.wert?.literal?.
			substring(0, 1) == "R"
	}

	private def static dispatch int getVmax(Object object, Fstr_Fahrweg fw) {
		return -1
	}

	private def static dispatch int getVmax(W_Kr_Gsp_Komponente object,
		Fstr_Fahrweg fw) {
		val kreuzung = object.kreuzung
		val zungenpaar = object.zungenpaar

		if (kreuzung !== null && zungenpaar === null) {
			return kreuzung.getVmaxKreuzung(fw)
		}

		if (kreuzung === null && zungenpaar !== null) {
			return zungenpaar.getVmaxWeiche(fw.getWeichenSchenkel(object))
		}

		// Max.V für bewegliche Brücke?
		// -> object.besonderesFahrwegelement
		return Integer.MAX_VALUE
	}

	private def static dispatch int getVmax(Gleis_Abschnitt object,
		Fstr_Fahrweg fw) {
		val geschwindigkeit = object.geschwindigkeit?.wert
		if (geschwindigkeit !== null) {
			return geschwindigkeit.intValue
		} else {
			return -1
		}
	}

	private def static int getVmaxKreuzung(
		Kreuzung_AttributeGroup object,
		Fstr_Fahrweg fw
	) {
		val crossingRoute = fw.getCrossingRoute(object)
		if (crossingRoute == CrossingRoute.LEFT) {
			return object.geschwindigkeitL.wert.intValue
		}
		if (crossingRoute == CrossingRoute.RIGHT) {
			return object.geschwindigkeitR.wert.intValue
		}
		throw new IllegalArgumentException(crossingRoute.toString)
	}

	private def static int getVmaxWeiche(Zungenpaar_AttributeGroup object,
		WeichenSchenkel schenkel) {
		if (schenkel.lage === WeichenSchenkel.Lage.L) {
			return (object?.geschwindigkeitL?.wert ?: BigInteger.ZERO).intValue
		}
		return (object?.geschwindigkeitR?.wert ?: BigInteger.ZERO).intValue
	}
}
