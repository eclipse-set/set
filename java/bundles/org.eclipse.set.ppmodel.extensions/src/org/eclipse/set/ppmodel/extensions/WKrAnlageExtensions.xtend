/**
 * Copyright (c) 2018 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.ppmodel.extensions

import de.scheidtbachmann.planpro.model.model1902.Weichen_und_Gleissperren.W_Kr_Anlage
import de.scheidtbachmann.planpro.model.model1902.Weichen_und_Gleissperren.W_Kr_Gsp_Element
import java.util.List

/**
 * Extensions for {@link W_Kr_Anlage}.
 */
class WKrAnlageExtensions extends BasisObjektExtensions {

	/**
	 * @param anlage this Weiche, Kreuzung Anlage
	 * 
	 * @return the Weiche, Kreuzung, Gleissperre Elemente of this Anlage
	 */
	static def List<W_Kr_Gsp_Element> getWKrGspElemente(W_Kr_Anlage anlage) {
		return anlage.container.WKrGspElement.filter [
			IDWKrAnlage?.wert == anlage.identitaet.wert
		].toList
	}
}
