/**
 * Copyright (c) 2016 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.ppmodel.extensions

import java.math.BigDecimal
import java.util.List
import org.eclipse.set.model.planpro.Basisobjekte.Punkt_Objekt
import org.eclipse.set.model.planpro.Geodaten.GEO_Knoten
import org.eclipse.set.model.planpro.Geodaten.TOP_Kante
import org.eclipse.set.model.planpro.Geodaten.TOP_Knoten
import org.eclipse.set.model.planpro.Weichen_und_Gleissperren.W_Kr_Gsp_Element
import org.eclipse.set.model.planpro.Weichen_und_Gleissperren.W_Kr_Gsp_Komponente
import org.eclipse.set.ppmodel.extensions.utils.Distance
import org.locationtech.jts.geom.Coordinate

import static org.eclipse.set.model.planpro.Geodaten.ENUMTOPAnschluss.*

import static extension org.eclipse.set.ppmodel.extensions.GeoKnotenExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.PunktObjektExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.PunktObjektTopKanteExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.TopKanteExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.WKrGspKomponenteExtensions.*

/**
 * Diese Klasse erweitert {@link TOP_Knoten}.
 * 
 * @author Schaefer
 */
class TopKnotenExtensions extends BasisObjektExtensions {

	/**
	 * @param topKnoten this TOP Knoten
	 * 
	 * @returns GEO Knoten of the TOP Knoten
	 */
	def static GEO_Knoten getGEOKnoten(TOP_Knoten topKnoten) {
		return topKnoten.IDGEOKnoten?.value
	}
	
	def static Coordinate getCoordinate(TOP_Knoten topKnoten) {
		return topKnoten.GEOKnoten.coordinate
	}

	/**
	 * @param topKnoten this TOP Knoten
	 * 
	 * @returns list of TOP Kanten with this TOP Knoten
	 */
	def static Iterable<TOP_Kante> getTopKanten(TOP_Knoten topKnoten) {
		return topKnoten.container.TOPKante.filter [
			TOPKnotenA == topKnoten || TOPKnotenB == topKnoten
		]
	}

	/**
	 * @param topKnoten this TOP Knoten
	 * 
	 * @returns the Weichenelement at this TOP Knoten
	 */
	def static W_Kr_Gsp_Element getWKrGspElement(TOP_Knoten topKnoten) {
		val elemente = topKnoten?.container.WKrGspKomponente.filter [
			topKnoten.contains(it)
		].map[WKrGspElement].toSet
		if (elemente.size != 1) {
			throw new IllegalArgumentException(
				'''«elemente.size» WKrGspElemente for Knoten «topKnoten?.identitaet?.wert» with «topKnoten.topKanten.size» TOP Kanten.'''
			)
		}
		return elemente.get(0)
	}

	/**
	 * @param topKnoten this TOP Knoten
	 * @param punktObjekt a Punkt Objekt
	 * 
	 * @returns whether this TOP Knoten contains the Punkt Objekt
	 */
	def static boolean contains(TOP_Knoten topKnoten,
		Punkt_Objekt punktObjekt) {
		val singlePoints = topKnoten.topKantenForPunktObjekt(punktObjekt).map [
			intersection(punktObjekt.singlePoints)
		].flatten
		if (singlePoints.empty) {
			return false
		}
		val singlePoint = singlePoints.get(0)
		val topKante = singlePoint.topKante
		val comparator = new Distance()
		if (topKante.IDTOPKnotenA?.wert ==
			topKnoten.identitaet.wert) {
			return comparator.compare(BigDecimal.ZERO, singlePoint.abstand.wert) ==
				0
		}

		if (topKante.IDTOPKnotenB?.wert ==
			topKnoten.identitaet.wert) {
			return comparator.compare(
				topKante.TOPKanteAllg.TOPLaenge.wert,
				singlePoint.abstand.wert
			) == 0
		}

		throw new RuntimeException(
			'''topKnoten=«topKnoten.identitaet.wert» topKante=«topKante.identitaet.wert»'''
		)
	}

	private static dispatch def List<TOP_Kante> topKantenForPunktObjekt(
		TOP_Knoten topKnoten, Punkt_Objekt punktObjekt) {
		return topKnoten.topKanten.toList
	}

	private static dispatch def List<TOP_Kante> topKantenForPunktObjekt(
		TOP_Knoten topKnoten, W_Kr_Gsp_Komponente komponente) {
		return topKnoten.topKanten.filter [
			getTOPAnschluss(topKnoten) != ENUMTOP_ANSCHLUSS_SPITZE
		].toList
	}
}
