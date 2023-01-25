/**
 * Copyright (c) 2016 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.ppmodel.extensions

import org.eclipse.set.toolboxmodel.Ansteuerung_Element.Unterbringung
import org.eclipse.set.toolboxmodel.PZB.PZB_Element
import org.eclipse.set.toolboxmodel.PZB.PZB_Element_Zuordnung

/**
 * Extensions for {@link PZB_Element}.
 */
class PZBElementExtensions extends BasisObjektExtensions {

	/**
	 * @param pzb this pzb
	 * 
	 * @return the PZB_Element_Zuordnung
	 */
	def static PZB_Element_Zuordnung getPZBElementZuordnung(PZB_Element pzb) {
		return pzb.IDPZBElementZuordnung
	}

	/**
	 * @param pzb this pzb
	 * 
	 * @return the Unterbringung
	 */
	def static Unterbringung getUnterbringung(PZB_Element pzb) {
		return pzb.IDUnterbringung
	}

}
