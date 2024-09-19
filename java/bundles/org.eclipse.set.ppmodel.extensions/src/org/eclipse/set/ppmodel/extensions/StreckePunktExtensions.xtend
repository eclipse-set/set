/**
 * Copyright (c) 2021 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.ppmodel.extensions

import org.eclipse.set.model.planpro.Geodaten.GEO_Knoten
import org.eclipse.set.model.planpro.Geodaten.Strecke_Punkt

/**
 * Extensions for {@link Strecke_Punkt}.
 * 
 * @author Stuecker
 */
class StreckePunktExtensions extends BasisObjektExtensions {
	/**
	 * @param routePoint a Strecke_Punkt
	 * @returns the GEO_Knoten for the route point
	 */
	static def GEO_Knoten getGeoKnoten(Strecke_Punkt routePoint) {
		return routePoint.IDGEOKnoten?.value
	}

}
