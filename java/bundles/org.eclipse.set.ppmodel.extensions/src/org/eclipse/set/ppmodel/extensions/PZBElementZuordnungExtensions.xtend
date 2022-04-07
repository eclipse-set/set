/**
 * Copyright (c) 2016 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.ppmodel.extensions

import org.eclipse.set.toolboxmodel.BasisTypen.Bezeichnung_Element_AttributeGroup
import org.eclipse.set.toolboxmodel.Basisobjekte.Ur_Objekt
import org.eclipse.set.toolboxmodel.Fahrstrasse.Fstr_Zug_Rangier
import org.eclipse.set.toolboxmodel.PZB.PZB_Element_Zuordnung
import org.eclipse.set.toolboxmodel.Signale.Signal
import org.eclipse.set.toolboxmodel.Weichen_und_Gleissperren.W_Kr_Gsp_Element

/**
 * Extensions for {@link PZB_Element_Zuordnung}.
 */
class PZBElementZuordnungExtensions extends BasisObjektExtensions {

	/**
	 * @param pzbZuordnung this pbzZuordnung
	 * 
	 * @return the bezugspunkt object (may be of type WKrGspElement or Signal)
	 */
	def static Ur_Objekt getPZBElementBezugspunkt(
		PZB_Element_Zuordnung pzbZuordnung) {
		return pzbZuordnung.IDPZBElementBezugspunkt
	}

	/**
	 * @param pzb this pbzZuordnung
	 * 
	 * @return the Fstr_Zug_Rangier
	 */
	def static Fstr_Zug_Rangier getFstrZugRangier(
		PZB_Element_Zuordnung pzbZuordnung) {
		return pzbZuordnung.IDFstrZugRangier
	}

	/**
	 * @param pzbZuordnung this pbzZuordnung
	 * 
	 * @return the name of the bezugspunkt object (may be of type WKrGspElement or Signal)
	 */
	def static Bezeichnung_Element_AttributeGroup getPZBElementBezugspunktBezeichnung(
		PZB_Element_Zuordnung pzbZuordnung) {
		val bezugspunkt = getPZBElementBezugspunkt(pzbZuordnung)

		if (bezugspunkt instanceof W_Kr_Gsp_Element) {
			return bezugspunkt?.bezeichnung
		} else if (bezugspunkt instanceof Signal) {
			return bezugspunkt?.bezeichnung
		}
		return null
	}

// "1.9 update" INA Signal was replaced by INA Markanter Punkt. How do we test the Signalbegriff for a Markanter Punkt?
//	/**
//	 * @param pzbZuordnung this pbzZuordnung
//	 * 
//	 * @return the Signal of the PZB Element Zuordnung INA (or <code>null</code> if there is no such Signal)
//	 */
//	def static Signal getINASignal(PZB_Element_Zuordnung pzbZuordnung) {
//		return getObject(
//			pzbZuordnung.container.signal,
//			pzbZuordnung.PZBElementZuordnungINA?.IDSignal
//		)
//	}
}
