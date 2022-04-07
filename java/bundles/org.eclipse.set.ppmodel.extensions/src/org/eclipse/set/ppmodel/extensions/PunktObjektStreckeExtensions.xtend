/**
 * Copyright (c) 2016 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.ppmodel.extensions

import org.eclipse.set.toolboxmodel.Basisobjekte.Punkt_Objekt_Strecke_AttributeGroup
import org.eclipse.set.toolboxmodel.Geodaten.Strecke

/**
 * Extensions for {@link Punkt_Objekt_Strecke_AttributeGroup}.
 * 
 * @author Schaefer
 */
class PunktObjektStreckeExtensions extends BasisObjektExtensions {

	/**
	 * @param p this Punkt Objekt
	 * 
	 * @returns the Strecke of the Punkt Objekt
	 */
	def static Strecke getStrecke(Punkt_Objekt_Strecke_AttributeGroup p) {
		return p.IDStrecke
	}
}
