/**
 * Copyright (c) 2017 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.ppmodel.extensions

import org.eclipse.set.basis.graph.DirectedEdge
import de.scheidtbachmann.planpro.model.model1902.BasisTypen.ENUMWirkrichtung

import static de.scheidtbachmann.planpro.model.model1902.BasisTypen.ENUMWirkrichtung.*

/**
 * Extensions for {@link ENUMWirkrichtung}.
 * 
 * @author Schaefer
 */
class ENUMWirkrichtungExtensions {

	/**
	 * @param wirkrichtung this Wirkrichtung
	 * 
	 * @return the inverted Wirkrichtung
	 */
	static def ENUMWirkrichtung getInverted(ENUMWirkrichtung wirkrichtung) {
		switch wirkrichtung {
			case ENUM_WIRKRICHTUNG_IN: return ENUM_WIRKRICHTUNG_GEGEN
			case ENUM_WIRKRICHTUNG_GEGEN: return ENUM_WIRKRICHTUNG_IN
			case ENUM_WIRKRICHTUNG_BEIDE: return ENUM_WIRKRICHTUNG_BEIDE
		}
	}

	/**
	 * @param wirkrichtung this Wirkrichtung
	 * @param isForwards whether the reference direction is forwards
	 * 
	 * @return whether this Wirkrichtung has the reference direction
	 */
	def static boolean isInWirkrichtung(ENUMWirkrichtung wirkrichtung,
		boolean isForwards) {

		switch (wirkrichtung) {
			case null:
				return false
			case ENUM_WIRKRICHTUNG_IN:
				return isForwards
			case ENUM_WIRKRICHTUNG_GEGEN:
				return !isForwards
			case ENUM_WIRKRICHTUNG_BEIDE:
				return true
			default:
				throw new IllegalArgumentException(wirkrichtung.toString)
		}
	}

	/**
	 * @param wirkrichtung this Wirkrichtung
	 * @param edge the edge
	 * 
	 * @return whether this Wirkrichtung is in the direction of the given edge
	 */
	def static <E, N, P> boolean isInWirkrichtung(ENUMWirkrichtung wirkrichtung,
		DirectedEdge<E, N, P> edge) {
		return wirkrichtung.isInWirkrichtung(edge.forwards)
	}
}
