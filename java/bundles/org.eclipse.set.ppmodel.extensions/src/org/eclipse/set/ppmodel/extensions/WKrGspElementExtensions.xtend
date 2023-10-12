/**
 * Copyright (c) 2016 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.ppmodel.extensions

import java.util.Collections
import java.util.List
import java.util.Set
import org.eclipse.core.runtime.Assert
import org.eclipse.set.toolboxmodel.Ansteuerung_Element.Stellelement
import org.eclipse.set.toolboxmodel.Basisobjekte.Basis_Objekt
import org.eclipse.set.toolboxmodel.Fahrstrasse.Fstr_Zug_Rangier
import org.eclipse.set.toolboxmodel.Geodaten.ENUMTOPAnschluss
import org.eclipse.set.toolboxmodel.Geodaten.TOP_Kante
import org.eclipse.set.toolboxmodel.Geodaten.TOP_Knoten
import org.eclipse.set.toolboxmodel.Regelzeichnung.Regelzeichnung
import org.eclipse.set.toolboxmodel.Weichen_und_Gleissperren.GZ_Freimeldung_L_AttributeGroup
import org.eclipse.set.toolboxmodel.Weichen_und_Gleissperren.GZ_Freimeldung_R_AttributeGroup
import org.eclipse.set.toolboxmodel.Weichen_und_Gleissperren.W_Kr_Anlage
import org.eclipse.set.toolboxmodel.Weichen_und_Gleissperren.W_Kr_Gsp_Element
import org.eclipse.set.toolboxmodel.Weichen_und_Gleissperren.W_Kr_Gsp_Komponente

import static org.eclipse.set.toolboxmodel.Geodaten.ENUMTOPAnschluss.*
import static org.eclipse.set.toolboxmodel.Weichen_und_Gleissperren.ENUMWKrArt.*

import static extension org.eclipse.set.ppmodel.extensions.BereichObjektExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.PunktObjektExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.TopKanteExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.TopKnotenExtensions.*

/**
 * This class extends {@link W_Kr_Gsp_Element}.
 * 
 * @author Schaefer
 */
class WKrGspElementExtensions extends BasisObjektExtensions {

	/**
	 * @param wKrGspElement this Weiche, Kreuzung oder Gleissperre
	 * 
	 * @return the Weiche/Kreuzung Anlage this element is part of
	 */
	def static W_Kr_Anlage getWKrAnlage(W_Kr_Gsp_Element wKrGspElement) {
		return wKrGspElement.IDWKrAnlage
	}

	/**
	 * @param wKrGspElement this Weiche, Kreuzung oder Gleissperre
	 * 
	 * @return list of Weiche, Kreuzung, Gleissperre Komponente this element is associated with
	 */
	def static List<W_Kr_Gsp_Komponente> getWKrGspKomponenten(
		W_Kr_Gsp_Element wKrGspElement) {
		if (wKrGspElement === null) {
			return Collections.emptyList
		}
		return wKrGspElement.container.WKrGspKomponente.filter [
			IDWKrGspElement.identitaet?.wert == wKrGspElement.identitaet.wert
		].toList
	}

	/**
	 * @param wKrGspElement this Weiche, Kreuzung oder Gleissperre
	 * 
	 * @return the Regelzeichnung
	 */
	def static Regelzeichnung getRegelzeichnung(
		W_Kr_Gsp_Element wKrGspElement) {
		return wKrGspElement.IDRegelzeichnung
	}

	/**
	 * @param group this GZ Freimeldung R Attribute Group
	 * 
	 * @return the angrenzende Element used for testing the
	 * Grenzzeichenfreiheit of the left or right Weichenschenkel 
	 */
	def static Basis_Objekt getElement(GZ_Freimeldung_R_AttributeGroup group) {
		return group.IDElement
	}

	/**
	 * @param group this GZ Freimeldung L Attribute Group
	 * 
	 * @return the angrenzende Element used for testing the
	 * Grenzzeichenfreiheit of the left or right Weichenschenkel 
	 */
	def static Basis_Objekt getElement(GZ_Freimeldung_L_AttributeGroup group) {
		return group.IDElement
	}

	/**
	 * @param wKrGspElement this Weiche, Kreuzung oder Gleissperre
	 * 
	 * @return the TOP Kante on the left track of this Weiche, or
	 * <code>null</code> if this element is no Weiche
	 */
	def static TOP_Kante getTopKanteL(W_Kr_Gsp_Element wKrGspElement) {
		return getTopKanteWithAnschluss(wKrGspElement, ENUMTOP_ANSCHLUSS_LINKS)
	}

	/**
	 * @param wKrGspElement this Weiche, Kreuzung oder Gleissperre
	 * 
	 * @return the TOP Kante on the right track of this Weiche, or
	 * <code>null</code> if this element is no Weiche
	 */
	def static TOP_Kante getTopKanteR(W_Kr_Gsp_Element wKrGspElement) {
		return getTopKanteWithAnschluss(wKrGspElement, ENUMTOP_ANSCHLUSS_RECHTS)
	}

	/**
	 * @param wKrGspElement this Weiche, Kreuzung oder Gleissperre
	 * 
	 * @return the TOP Knoten of this Weiche, or <code>null</code> if this
	 * element is no Weiche
	 */
	def static TOP_Knoten getTopKnoten(W_Kr_Gsp_Element wKrGspElement) {
		switch (wKrGspElement.getWKrAnlage.getWKrAnlageAllg.getWKrArt) {
			case ENUMW_KR_ART_KLOTHOIDENWEICHE,
			case ENUMW_KR_ART_IBW,
			case ENUMW_KR_ART_KORBBOGENWEICHE,
			case ENUMW_KR_ART_ABW,
			case ENUMW_KR_ART_DKW,
			case ENUMW_KR_ART_DW,
			case ENUMW_KR_ART_EKW,
			case ENUMW_KR_ART_EW: {
				if (wKrGspElement?.weicheElement === null) {
					return null
				}
				val zungenpaare = wKrGspElement.WKrGspKomponenten.filter [
					zungenpaar !== null
				]
				Assert.isTrue(!zungenpaare.empty)
				val zungenpaar = zungenpaare.get(0)
				return zungenpaar.topKnoten
			}
			case ENUMW_KR_ART_FLACHKREUZUNG,
			case ENUMW_KR_ART_KR,
			case ENUMW_KR_ART_SONSTIGE:
				return wKrGspElement.WKrGspKomponenten.get(0).topKnoten
		}
	}

	/**
	 * @param wKrGspElement this Weiche, Kreuzung oder Gleissperre
	 * 
	 * @return the Stellelement of this Weiche, Kreuzung oder Gleissperre
	 */
	static def Stellelement getStellelement(W_Kr_Gsp_Element wKrGspElement) {
		return wKrGspElement.IDStellelement
	}

	private def static TOP_Kante getTopKanteWithAnschluss(
		W_Kr_Gsp_Element wKrGspElement,
		ENUMTOPAnschluss anschluss
	) {
		val weichenKnoten = wKrGspElement.topKnoten

		if (weichenKnoten === null) {
			return null
		}

		val results = weichenKnoten.topKanten.filter [
			getTOPAnschluss(weichenKnoten) == anschluss
		]
		if (results.size !== 1)
			return null
		return results.get(0)
	}

	/**
	 * Get all train roads run over trackswitch leg
	 * @param element this Weiche, Kreuzung oder Gleissperre
	 * @param legTopKante topkante of Weiche leg
	 */
	static def Set<Fstr_Zug_Rangier> getFstrZugCrossingLeg(
		W_Kr_Gsp_Element element, TOP_Kante legTopKante) {
		return element.container.fstrZugRangier.filter [
			fstrZug !== null && IDFstrFahrweg.bereichObjektTeilbereich.map [
				topKante
			].contains(legTopKante)
		].filterNull.toSet
	}
}
