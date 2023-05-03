/**
 * Copyright (c) 2016 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.ppmodel.extensions

import java.util.List
import org.eclipse.set.toolboxmodel.Basisobjekte.Basis_Objekt
import org.eclipse.set.toolboxmodel.Flankenschutz.Fla_Freimelde_Zuordnung
import org.eclipse.set.toolboxmodel.Flankenschutz.Fla_Schutz
import org.eclipse.set.toolboxmodel.Signale.Signal
import org.eclipse.set.toolboxmodel.Weichen_und_Gleissperren.W_Kr_Gsp_Element

import static extension org.eclipse.set.ppmodel.extensions.FlaZwieschutzExtensions.*
import org.eclipse.set.toolboxmodel.Weichen_und_Gleissperren.ENUMWKrArt

/**
 * Extensions for {@link Fla_Schutz}.
 */
class FlaSchutzExtensions extends BasisObjektExtensions {

	/**
	 * @param flaSchutz this Flankenschutz
	 * 
	 * @return the Anforderer of this Flankenschutz
	 */
	def static Basis_Objekt getAnforderer(Fla_Schutz flaSchutz) {
		return flaSchutz.flaSchutzAnforderer.IDAnfordererElement
	}

	/**
	 * @param flaSchutz this Flankenschutz
	 * 
	 * @return the Weiche or Gleissperre providing the Flankenschutz
	 */
	def static W_Kr_Gsp_Element getWeicheGleissperreElement(
		Fla_Schutz flaSchutz) {
		return flaSchutz.flaSchutzWGsp.IDFlaWGspElement
	}

	/**
	 * @param flaSchutz this Flankenschutz
	 * 
	 * @return whether the Weiche or Gleissperre providing the Flankenschutz is a Zwieschutz
	 */
	def static boolean hasZwieschutz(Fla_Schutz flaSchutz) {
		return flaSchutz.container.flaZwieschutz.map[zwieschutzweiche].contains(
			flaSchutz.weicheGleissperreElement)
	}

	/**
	 * @param flaSchutz this Flankenschutz
	 * 
	 * @return whether the Flankenschutzsignal has a Zielsperre while in use
	 */
	def static Boolean hasZielsperrung(Fla_Schutz flaSchutz) {
		return flaSchutz?.flaSchutzSignal?.flaSignalZielsperrung?.wert
	}

	/**
	 * @param flaSchutz this Flankenschutz
	 * 
	 * @return the Signal providing Flankenschutz
	 */
	def static Signal getSignal(Fla_Schutz flaSchutz) {
		return flaSchutz.flaSchutzSignal.IDFlaSignal
	}

	/**
	 * @param flaSchutz this Flankenschutz
	 * 
	 * @return the Flankenschutzmaßnahme of the left Weitergabe
	 */
	def static Fla_Schutz getWeitergabeL(Fla_Schutz flaSchutz) {
		return flaSchutz?.flaSchutzWeitergabe?.IDFlaWeitergabeL
	}

	/**
	 * @param flaSchutz this Flankenschutz
	 * 
	 * @return the Flankenschutzmaßnahme of the right Weitergabe
	 */
	def static Fla_Schutz getWeitergabeR(Fla_Schutz flaSchutz) {
		return flaSchutz?.flaSchutzWeitergabe?.IDFlaWeitergabeR
	}

	/**
	 * @param flaSchutz this Flankenschutz
	 * 
	 * @return the Freimeldezuordnungen this Flankenschutz is a Flankenschutzfall for
	 */
	def static List<Fla_Freimelde_Zuordnung> getFreimeldeZuordnungen(
		Fla_Schutz flaSchutz) {
		return flaSchutz.container.flaFreimeldeZuordnung.filter [
			it?.IDFlaSchutz?.identitaet?.wert == flaSchutz?.identitaet?.wert &&
				it?.IDFlaSchutz?.identitaet?.wert !== null
		].toList
	}

	def static String getAnfordererBezeichnung(Fla_Schutz anforderer,
		W_Kr_Gsp_Element wKrGspElement) {
		val wKrArt = wKrGspElement?.IDWKrAnlage?.WKrAnlageAllg?.WKrArt?.wert
		if (wKrArt === ENUMWKrArt.ENUMW_KR_ART_EKW &&
			anforderer?.flaSchutzAnforderer?.EKWKrAnteil?.wert) {
			return '''«wKrGspElement?.bezeichnung?.kennzahl»Kr«wKrGspElement.bezeichnung.oertlicherElementname»'''
		} else {
			wKrGspElement?.bezeichnung?.bezeichnungTabelle?.wert
		}
		return ""
	}

}
