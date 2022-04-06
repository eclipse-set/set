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
import org.eclipse.set.toolboxmodel.Fahrstrasse.Markanter_Punkt
import org.eclipse.set.toolboxmodel.Ortung.FMA_Anlage
import org.eclipse.set.toolboxmodel.Ortung.FMA_Komponente
import org.eclipse.set.toolboxmodel.Ortung.Schaltmittel_Zuordnung

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
		return !komp?.IDFMAgrenze?.filter[it.identitaet.wert == anlage?.identitaet?.wert].
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
		return komp.IDFMAgrenze
	}

	def static Markanter_Punkt getMarkanterPunkt(FMA_Komponente komp) {
		return komp.IDBezugspunkt.resolve(Markanter_Punkt)
	}

	def static Schaltmittel_Zuordnung getSchaltmittelZuordnung(
		FMA_Komponente komp) {
		for (Schaltmittel_Zuordnung zuord : komp.container.
			schaltmittelZuordnung) {
			if (zuord?.IDSchalter?.identitaet?.wert !== null &&
				zuord?.IDSchalter?.identitaet?.wert.equals(komp.identitaet.wert)) {
				return zuord;
			}
		}
		return null;
	}
}
