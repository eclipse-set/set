/**
 * Copyright (c) 2018 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.ppmodel.extensions

import org.eclipse.set.model.planpro.Weichen_und_Gleissperren.W_Kr_Anlage
import org.eclipse.set.model.planpro.Weichen_und_Gleissperren.W_Kr_Gsp_Element
import java.util.List
import org.eclipse.set.model.planpro.Weichen_und_Gleissperren.ENUMWKrArt

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
			IDWKrAnlage?.value?.identitaet?.wert == anlage.identitaet.wert
		].toList
	}
	
	/**
	 * @param anlage this Weiche, Kreuzung Anlage
	 * 
	 * @return the Weiche, Kreuzung Anlage Art
	 */
	static def ENUMWKrArt getWKrAnlageArt(W_Kr_Anlage anlage) {
		return anlage?.WKrAnlageAllg?.WKrArt?.wert
	}
}
