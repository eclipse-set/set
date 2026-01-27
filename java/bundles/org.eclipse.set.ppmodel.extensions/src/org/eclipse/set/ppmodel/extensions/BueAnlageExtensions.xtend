/**
 * Copyright (c) 2017 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.ppmodel.extensions

import org.eclipse.set.model.planpro.Bahnuebergang.BUE_Anlage
import org.eclipse.set.model.planpro.Bahnuebergang.BUE_Gleisbezogener_Gefahrraum
import java.util.List
import org.eclipse.set.model.planpro.Ansteuerung_Element.Stellelement

/**
 * Extensions for {@link BUE_Anlage}.
 * 
 * @author Schaefer
 */
class BueAnlageExtensions extends PunktObjektExtensions {

	/**
	 * @param anlage this BUE Anlage
	 * 
	 * @return the gleisbezogene Gefahrräume of this BUE Anlage
	 */
	static def List<BUE_Gleisbezogener_Gefahrraum> getGleisbezogeneGefahrraeume(
		BUE_Anlage anlage) {
		return anlage.container.BUEGleisbezogenerGefahrraum.filter [
			IDBUEAnlage?.value?.identitaet?.wert == anlage.identitaet.wert
		].toList
	}
	
	/**
	 * @param anlage this BUE Anlage
	 * 
	 * @return the BUE Anlage Stellelement
	 */
	 static def Stellelement getStellelement(BUE_Anlage anlage) {
	 	return anlage?.IDBUESchnittstelle?.value?.IDStellelement?.value
	 }
	 
	 static def String getBezeichnung(BUE_Anlage anlage) {
	 	return anlage?.bezeichnung?.bezeichnungTabelle?.wert
	 }
}
