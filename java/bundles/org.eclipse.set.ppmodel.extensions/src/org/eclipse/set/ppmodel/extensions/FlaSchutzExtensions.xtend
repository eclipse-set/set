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
import org.eclipse.set.model.planpro.Ansteuerung_Element.Stell_Bereich
import org.eclipse.set.model.planpro.Basisobjekte.Basis_Objekt
import org.eclipse.set.model.planpro.Flankenschutz.Fla_Freimelde_Zuordnung
import org.eclipse.set.model.planpro.Flankenschutz.Fla_Schutz
import org.eclipse.set.model.planpro.Nahbedienung.NB_Zone_Grenze
import org.eclipse.set.model.planpro.Signale.Signal
import org.eclipse.set.model.planpro.Weichen_und_Gleissperren.ENUMWKrArt
import org.eclipse.set.model.planpro.Weichen_und_Gleissperren.W_Kr_Gsp_Element

import static extension org.eclipse.set.ppmodel.extensions.FlaZwieschutzExtensions.*

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
		return flaSchutz.flaSchutzAnforderer.IDAnfordererElement?.value
	}

	/**
	 * @param flaSchutz this Flankenschutz
	 * 
	 * @return the Weiche or Gleissperre providing the Flankenschutz
	 */
	def static W_Kr_Gsp_Element getWeicheGleissperreElement(
		Fla_Schutz flaSchutz) {
		return flaSchutz?.flaSchutzWGsp?.IDFlaWGspElement?.value
	}

	/**
	 * @param flaSchutz this Flankenschutz
	 * 
	 * @return whether the Weiche or Gleissperre providing the Flankenschutz is a Zwieschutz
	 */
	def static boolean hasZwieschutz(Fla_Schutz flaSchutz) {
		return flaSchutz?.container.flaZwieschutz?.map[zwieschutzweiche].contains(
			flaSchutz?.weicheGleissperreElement)
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
		return flaSchutz?.flaSchutzSignal?.IDFlaSignal?.value
	}

	/**
	 * @param flaSchutz this Flankenschutz
	 * 
	 * @return the Flankenschutzmaßnahme of the left Weitergabe
	 */
	def static Fla_Schutz getWeitergabeL(Fla_Schutz flaSchutz) {
		return flaSchutz?.flaSchutzWeitergabe?.IDFlaWeitergabeL?.value
	}

	/**
	 * @param flaSchutz this Flankenschutz
	 * 
	 * @return the Flankenschutzmaßnahme of the right Weitergabe
	 */
	def static Fla_Schutz getWeitergabeR(Fla_Schutz flaSchutz) {
		return flaSchutz?.flaSchutzWeitergabe?.IDFlaWeitergabeR?.value
	}

	/**
	 * @param flaSchutz this Flankenschutz
	 * 
	 * @return the Freimeldezuordnungen this Flankenschutz is a Flankenschutzfall for
	 */
	def static List<Fla_Freimelde_Zuordnung> getFreimeldeZuordnungen(
		Fla_Schutz flaSchutz) {
		return flaSchutz.container.flaFreimeldeZuordnung.filter [
			it?.IDFlaSchutz?.value?.identitaet?.wert ==
				flaSchutz?.identitaet?.wert &&
				it?.IDFlaSchutz?.value?.identitaet?.wert !== null
		].toList
	}

	def static String getAnfordererBezeichnung(Fla_Schutz anforderer,
		W_Kr_Gsp_Element wKrGspElement) {
		val wKrArt = wKrGspElement?.IDWKrAnlage?.value?.WKrAnlageAllg?.WKrArt?.
			wert
		if (wKrArt === ENUMWKrArt.ENUMW_KR_ART_EKW &&
			anforderer?.flaSchutzAnforderer?.EKWKrAnteil?.wert) {
			return '''«wKrGspElement?.bezeichnung?.kennzahl»Kr«wKrGspElement.bezeichnung.oertlicherElementname»'''
		} else {
			wKrGspElement?.bezeichnung?.bezeichnungTabelle?.wert
		}
		return ""
	}

	def static boolean isBelongToControlArea(Fla_Schutz fla,
		Stell_Bereich controlArea) {
		val anforderer = fla.anforderer
		return switch (anforderer) {
			W_Kr_Gsp_Element:
				WKrGspElementExtensions.isBelongToControlArea(anforderer,
					controlArea)
			NB_Zone_Grenze:
				NbZoneGrenzeExtensions.isBelongToControlArea(anforderer,
					controlArea)
			default:
				throw new IllegalArgumentException()
		}
	}
}
