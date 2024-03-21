/**
 * Copyright (c) 2017 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.ppmodel.extensions

import org.eclipse.set.model.planpro.Ansteuerung_Element.Aussenelementansteuerung
import org.eclipse.set.model.planpro.Ortung.FMA_Komponente_Achszaehlpunkt_AttributeGroup
import java.util.List

/**
 * Diese Klasse erweitert {@link FMA_Komponente_Achszaehlpunkt_AttributeGroup}.
 */
class FmaKomponenteAchszaehlpunktExtensions extends BasisObjektExtensions {

	/**
	 * @param komp the component
	 * 
	 * @returns the Aussenelementansteuerung of the given id
	 */
	def static Aussenelementansteuerung getAussenelementEnergie(
		FMA_Komponente_Achszaehlpunkt_AttributeGroup komp) {
		return komp?.IDEnergie?.value
	}

	/**
	 * @param komp the component
	 * 
	 * @returns the Aussenelementansteuerung of the given id
	 */
	def static List<Aussenelementansteuerung> getAussenelementInformation(
		FMA_Komponente_Achszaehlpunkt_AttributeGroup komp) {
		return komp?.IDInformation?.map[value]?.filterNull?.toList
	}
}
