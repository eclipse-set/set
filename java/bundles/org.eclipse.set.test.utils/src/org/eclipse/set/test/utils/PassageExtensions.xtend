/**
 * Copyright (c) 2018 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.test.utils

import org.eclipse.set.model.test.site.Passage
import org.eclipse.set.model.test.site.Room
import org.eclipse.set.test.utils.IdentifiedExtensions

/**
 * Extensions for {@link Passage}.
 */
class PassageExtensions extends IdentifiedExtensions {

	/**
	 * Connect the given rooms via this passage. If one of the rooms already
	 * uses this passage, its role (A, B) remains unchanged. Therefore it is
	 * not guaranteed whether room1 or room2 is roomA of the passage.
	 */
	static def void connect(Passage passage, Room room1, Room room2) {
		val guid1 = room1.guid
		val guid2 = room2.guid

		if (passage.roomID_A == guid1) {
			passage.roomID_B = guid2
			return
		}

		if (passage.roomID_B == guid1) {
			passage.roomID_A = guid2
			return
		}

		if (passage.roomID_A == guid2) {
			passage.roomID_B = guid1
			return
		}

		if (passage.roomID_B == guid2) {
			passage.roomID_A = guid1
			return
		}

		passage.roomID_A = guid1
		passage.roomID_B = guid2
	}

	/**
	 * @param passage this passage
	 * 
	 * @return the room A
	 */
	static def Room getRoomA(Passage passage) {
		return getIdentified(
			passage.roomID_A,
			passage.container.allRooms
		)
	}

	/**
	 * @param passage this passage
	 * 
	 * @return the room B
	 */
	static def Room getRoomB(Passage passage) {
		return getIdentified(
			passage.roomID_B,
			passage.container.allRooms
		)
	}

	/**
	 * @param passage this passage
	 * @param room the room
	 * 
	 * @return whether the passage is connected to the given room
	 */
	static def boolean isConnectedTo(Passage passage, Room room) {
		return room === passage.roomA || room === passage.roomB
	}
}
