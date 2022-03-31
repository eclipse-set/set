/**
 * Copyright (c) 2016 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.ppmodel.extensions

import de.scheidtbachmann.planpro.model.model1902.Basisobjekte.Basis_Objekt
import de.scheidtbachmann.planpro.model.model1902.Flankenschutz.Fla_Freimelde_Zuordnung
import de.scheidtbachmann.planpro.model.model1902.Flankenschutz.Fla_Schutz
import de.scheidtbachmann.planpro.model.model1902.Signale.Signal
import de.scheidtbachmann.planpro.model.model1902.Weichen_und_Gleissperren.W_Kr_Gsp_Element
import java.util.List

import static extension org.eclipse.set.ppmodel.extensions.FlaZwieschutzExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.ZeigerExtensions.*

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
		return flaSchutz.flaSchutzAnforderer.IDAnfordererElement.resolve(
			Basis_Objekt)
	}

	/**
	 * @param flaSchutz this Flankenschutz
	 * 
	 * @return the Weiche or Gleissperre providing the Flankenschutz
	 */
	def static W_Kr_Gsp_Element getWeicheGleissperreElement(
		Fla_Schutz flaSchutz) {
		return flaSchutz.flaSchutzWGsp.IDFlaWGspElement.resolve(
			W_Kr_Gsp_Element)
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
		return flaSchutz.flaSchutzSignal.IDFlaSignal.resolve(Signal)
	}

	/**
	 * @param flaSchutz this Flankenschutz
	 * 
	 * @return the Flankenschutzmaßnahme of the left Weitergabe
	 */
	def static Fla_Schutz getWeitergabeL(Fla_Schutz flaSchutz) {
		return flaSchutz.flaSchutzWeitergabe.IDFlaWeitergabeL.resolve(
			Fla_Schutz)
	}

	/**
	 * @param flaSchutz this Flankenschutz
	 * 
	 * @return the Flankenschutzmaßnahme of the right Weitergabe
	 */
	def static Fla_Schutz getWeitergabeR(Fla_Schutz flaSchutz) {
		return flaSchutz.flaSchutzWeitergabe.IDFlaWeitergabeR.resolve(
			Fla_Schutz)
	}

	/**
	 * @param flaSchutz this Flankenschutz
	 * 
	 * @return additional Flankenschutzmaßnahme for Flankenschutzweitergabe of EKW or <code>null</code>
	 */
	def static Fla_Schutz getWeitergabeEKW(Fla_Schutz flaSchutz) {
		return flaSchutz?.IDFlaWeitergabeEKW?.resolve(Fla_Schutz)
	}

	/**
	 * @param flaSchutz this Flankenschutz
	 * 
	 * @return the Freimeldezuordnungen this Flankenschutz is a Flankenschutzfall for
	 */
	def static List<Fla_Freimelde_Zuordnung> getFreimeldeZuordnungen(
		Fla_Schutz flaSchutz) {
		return flaSchutz.container.flaFreimeldeZuordnung.filter [
			it?.IDFlaSchutz?.wert == flaSchutz?.identitaet?.wert &&
				it?.IDFlaSchutz?.wert !== null
		].toList
	}

}
