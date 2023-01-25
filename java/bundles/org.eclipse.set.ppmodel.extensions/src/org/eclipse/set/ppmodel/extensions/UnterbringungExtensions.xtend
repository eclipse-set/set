/**
 * Copyright (c) 2016 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.ppmodel.extensions

import org.eclipse.set.toolboxmodel.Ansteuerung_Element.ENUMUnterbringungArt
import org.eclipse.set.toolboxmodel.Ansteuerung_Element.Unterbringung
import org.eclipse.set.toolboxmodel.Geodaten.GEO_Punkt
import org.eclipse.set.toolboxmodel.Geodaten.Strecke
import java.util.List

/**
 * Extensions for {@link Unterbringung}.
 */
class UnterbringungExtensions extends BasisObjektExtensions {

	/**
	 * @param unterbringung this Unterbringung
	 * 
	 * @return the type of this Unterbringung
	 */
	def static ENUMUnterbringungArt getArt(Unterbringung unterbringung) {
		return unterbringung?.unterbringungAllg?.unterbringungArt?.wert
	}

	/**
	 * @param unterbringung this Unterbringung
	 * 
	 * @return the GEO Punkt of this Unterbringung
	 */
	def static GEO_Punkt getGeoPunkt(Unterbringung unterbringung) {
		// TODO(1.10.0.1): There are now multiple ID_GEO_Punkt
		return unterbringung.IDGEOPunkt.get(0)

	}

	/**
	 * @param unterbringung this Unterbringung
	 * 
	 * @return the location of this Unterbringung
	 */
	def static String getOrt(Unterbringung unterbringung) {
		return unterbringung?.standortBeschreibung?.wert
	}

	/**
	 * @param unterbringung this Unterbringung
	 * 
	 * @return the Strecke km of this Unterbringung
	 */
	def static String getStreckeKm(Unterbringung unterbringung) {
		return '''«FOR u : unterbringung?.punktObjektStrecke?.map[streckeKm?.wert] ?: emptyList SEPARATOR " "»«u»«ENDFOR»'''
	}

	/**
	 * @param unterbringung this Unterbringung
	 * 
	 * @return a List of {@link Strecke}
	 */
	def static List<Strecke> getStrecken(Unterbringung unterbringung) {
		return unterbringung?.punktObjektStrecke?.map [
			IDStrecke
		];
	}
}
