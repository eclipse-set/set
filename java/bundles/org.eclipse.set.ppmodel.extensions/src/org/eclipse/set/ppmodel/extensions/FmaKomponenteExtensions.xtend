/**
 * Copyright (c) 2016 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.ppmodel.extensions

import de.scheidtbachmann.planpro.model.model1902.Fahrstrasse.Markanter_Punkt
import de.scheidtbachmann.planpro.model.model1902.Ortung.FMA_Anlage
import de.scheidtbachmann.planpro.model.model1902.Ortung.FMA_Komponente
import de.scheidtbachmann.planpro.model.model1902.Ortung.Schaltmittel_Zuordnung
import de.scheidtbachmann.planpro.model.model1902.Verweise.ID_FMA_Anlage_TypeClass
import java.util.List
import static extension org.eclipse.set.ppmodel.extensions.ZeigerExtensions.*

/**
 * This class extends {@link FMA_Komponente}.
 * 
 * @author Schaefer
 */
class FmaKomponenteExtensions extends BasisObjektExtensions {

	/**
	 * @param komp the FMA Komponente
	 * @param anlage the FMA Anlage
	 * 
	 * @return whether the FMA Komponente belongs to the given FMA Anlage
	 */
	def static boolean belongsTo(FMA_Komponente komp, FMA_Anlage anlage) {
		return !komp?.IDFMAgrenze?.filter[it.wert == anlage?.identitaet?.wert].
			nullOrEmpty
	}

	/**
	 * @param komp the FMA Komponente
	 * 
	 * @return all FMA-Anlagen the FMA-Komponente is a FMA-Grenze for
	 */
	def static List<FMA_Anlage> getAngrenzendeFMA(
		FMA_Komponente komp
	) {
		return komp.IDFMAgrenze.map[getFmaAnlage(komp)]
	}

	private def static FMA_Anlage getFmaAnlage(
		ID_FMA_Anlage_TypeClass id,
		FMA_Komponente achszaehlpunktGroup
	) {
		return id.resolve(FMA_Anlage)
	}

	def static Markanter_Punkt getMarkanterPunkt(FMA_Komponente komp) {
		return komp.IDBezugspunkt.resolve(Markanter_Punkt)
	}

	def static Schaltmittel_Zuordnung getSchaltmittelZuordnung(
		FMA_Komponente komp) {
		for (Schaltmittel_Zuordnung zuord : komp.container.
			schaltmittelZuordnung) {
			if (zuord?.IDSchalter?.wert !== null &&
				zuord?.IDSchalter?.wert.equals(komp.identitaet.wert)) {
				return zuord;
			}
		}
		return null;
	}
}
