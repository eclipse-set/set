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
import org.eclipse.set.model.test.site.Passage
import org.eclipse.set.model.test.site.Room
import java.util.List

import static extension org.eclipse.set.test.utils.FloorExtensions.*

/**
 * Extensions for {@link Room}.
 */
class RoomExtensions extends IdentifiedExtensions {

	/**
	 * @param room this room
	 * 
	 * @return the building of this room
	 */
	static def Building getBuilding(Room room) {
		return room.floor.building
	}

	/**
	 * @param room this room
	 * 
	 * @return the floor of this room
	 */
	static def Floor getFloor(Room room) {
		val site = room.site
		val floors = site.allFloors.filter[guid == room.floorID]
		if (floors.size == 1) {
			return floors.head
		}
		throw new IllegalArgumentException('''«floors.size» floors for «room».''')
	}

	/**
	 * @param this room
	 * 
	 * @return the passages of this room
	 */
	static def List<Passage> getPassages(Room room) {
		val roomGuid = room.guid
		return room.site.allPassages.filter [
			roomID_A == roomGuid || roomID_B == roomGuid
		].toList
	}
}
