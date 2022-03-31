/**
 * Copyright (c) 2018 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.test.utils

import org.eclipse.set.model.test.site.Building
import org.eclipse.set.model.test.site.Floor

/**
 * Extensions for {@link Floor}.
 */
class FloorExtensions extends IdentifiedExtensions {

	/**
	 * @param floor the floor
	 * 
	 * @return the building of this floor
	 */
	static def Building getBuilding(Floor floor) {
		val site = floor.site
		val buildings = site.allBuildings.filter[guid == floor.buildingID]
		if (buildings.size == 1) {
			return buildings.head
		}
		throw new IllegalArgumentException('''«buildings.size» buildings for «floor».''')
	}
}
