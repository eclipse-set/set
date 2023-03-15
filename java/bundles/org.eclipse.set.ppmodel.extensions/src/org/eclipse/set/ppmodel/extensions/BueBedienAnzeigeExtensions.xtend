/**
 * Copyright (c) 2023 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.ppmodel.extensions

import org.eclipse.set.toolboxmodel.Bahnuebergang.BUE_Bedien_Anzeige_Element
import org.eclipse.set.toolboxmodel.Bahnuebergang.BUE_Anlage
import java.util.List
import org.eclipse.set.toolboxmodel.Bahnuebergang.BUE_Einschaltung
import org.eclipse.set.toolboxmodel.Bahnuebergang.BUE_Ausschaltung
import org.eclipse.set.toolboxmodel.Ortung.Schaltmittel_Zuordnung

/**
 * Extensions for {@link BUE_Bedien_Anzeige_Element}
 * 
 * @author Truong
 */
class BueBedienAnzeigeExtensions extends BasisObjektExtensions {

	/**
	 * @param bueAnzEle {@link BUE_Bedien_Anzeige_Element}
	 * 
	 * @return {@link BUE_Anlage}
	 */
	def static List<BUE_Anlage> getBueAnlage(
		BUE_Bedien_Anzeige_Element bueAnzEle) {
		return bueAnzEle?.container.BUEAnlage.filter [
			bueAnzEle?.BUEBedienAnzElementAllg?.IDHandschaltWirkfunktion?.
				identitaet?.wert === identitaet?.wert
		].toList
	}
	
	/**
	 * @param bueAnzEle {@link BUE_Bedien_Anzeige_Element}
	 * 
	 * @return {@link BUE_Einschaltung}
	 */
	 def static List<BUE_Einschaltung> getBueEin(BUE_Bedien_Anzeige_Element bueAnzEle) {
	 	return bueAnzEle?.container.BUEEinschaltung.filter[
	 		bueAnzEle?.BUEBedienAnzElementAllg?.IDHandschaltWirkfunktion?.
				identitaet?.wert === identitaet?.wert
	 	].toList
	 }
	 
	 	/**
	 * @param bueAnzEle {@link BUE_Bedien_Anzeige_Element}
	 * 
	 * @return {@link BUE_Ausschaltung}
	 */
	 def static List<BUE_Ausschaltung> getBueAus(BUE_Bedien_Anzeige_Element bueAnzEle) {
	 	return bueAnzEle?.container.BUEAusschaltung.filter[
	 		bueAnzEle?.BUEBedienAnzElementAllg?.IDHandschaltWirkfunktion?.
				identitaet?.wert === identitaet?.wert
	 	].toList
	 }
	 
	 def static List<Schaltmittel_Zuordnung> getSchaltmittel_Zuordnung(BUE_Einschaltung bueEin) {
	 	return bueEin?.container.schaltmittelZuordnung.filter[
	 		IDAnforderung === bueEin
	 	].toList
	 }
	 
	 def static List<Schaltmittel_Zuordnung> getSchaltmittel_Zuordnung(BUE_Ausschaltung bueAus) {
	 	return bueAus?.container.schaltmittelZuordnung.filter[
	 		IDAnforderung === bueAus
	 	].toList
	 }
}
