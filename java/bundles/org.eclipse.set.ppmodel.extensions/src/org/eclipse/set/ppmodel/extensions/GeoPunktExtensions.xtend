/**
 * Copyright (c) 2016 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.ppmodel.extensions

import org.eclipse.set.model.planpro.Geodaten.GEO_Punkt
import org.locationtech.jts.geom.Coordinate

/**
 * This class extends {@link GEO_Punkt}.
 * 
 * @author Schaefer
 */
class GeoPunktExtensions {
	
	/**
	 * @param geoPunkt this GEO Punkt
	 * 
	 * @returns the coordinate of this GEO Punkt
	 */
	def static Coordinate getCoordinate(GEO_Punkt geoPunkt) {
		val double x = geoPunkt.GEOPunktAllg.GKX.wert.doubleValue
		val double y = geoPunkt.GEOPunktAllg.GKY.wert.doubleValue
		return new Coordinate(x, y);
	}
}
