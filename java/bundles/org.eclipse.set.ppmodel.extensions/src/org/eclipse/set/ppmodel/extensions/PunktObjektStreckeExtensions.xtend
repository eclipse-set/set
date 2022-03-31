/**
 * Copyright (c) 2016 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.ppmodel.extensions

import de.scheidtbachmann.planpro.model.model1902.Basisobjekte.Punkt_Objekt_Strecke_AttributeGroup
import de.scheidtbachmann.planpro.model.model1902.Geodaten.Strecke
import static extension org.eclipse.set.ppmodel.extensions.ZeigerExtensions.*

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
		return p.IDStrecke.resolve(Strecke)
	}
}
