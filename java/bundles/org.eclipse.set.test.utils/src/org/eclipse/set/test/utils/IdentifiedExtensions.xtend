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
import org.eclipse.set.model.test.site.Identified
import org.eclipse.set.model.test.site.Passage
import org.eclipse.set.model.test.site.Room
import org.eclipse.set.model.test.site.Site
import java.util.List
import org.eclipse.emf.ecore.EObject

/**
 * Site extensions for {@link Identified}.
 */
class IdentifiedExtensions {

	/**
	 * @param identified this identified
	 * @param name the name
	 * 
	 * @return whether this identified has the given name
	 */
	static def boolean hasName(Identified identified, String name) {
		return identified.hasNameInternal(name)
	}

	/**
	 * @param identified this identified
	 * 
	 * @return the site of this identified
	 */
	static def Site getSite(Identified identified) {
		return identified.eContainer as Site
	}

	static def Site getContainer(Identified identified) {
		return identified.containerDispatch
	}

	static def dispatch Site getContainerDispatch(EObject object) {
		return object.eContainer.containerDispatch
	}

	static def dispatch Site getContainerDispatch(Site site) {
		return site
	}

	static def <T extends Identified> T getIdentified(
		String guid,
		List<T> elements
	) {
		val guidElements = elements.filter[it.guid == guid]
		if (guidElements.size == 1) {
			return guidElements.head
		}
		throw new IllegalArgumentException('''«guidElements.size» elements found for guid=«guid»''')
	}

	private static def dispatch boolean hasNameInternal(Identified object,
		String name) {
		throw new IllegalArgumentException('''Unexpected object «object».''')
	}

	private static def dispatch boolean hasNameInternal(Building building,
		String name) {
		if (building.names.address == name) {
			return true
		}
		if (building.names.entrance == name) {
			return true
		}
		if (building.names.sitePlan == name) {
			return true
		}
		return false
	}

	private static def dispatch boolean hasNameInternal(Floor floor,
		String name) {
		if (floor.names.elevator == name) {
			return true
		}
		if (floor.names.floorPlanTitle == name) {
			return true
		}
		return false
	}

	private static def dispatch boolean hasNameInternal(Room room,
		String name) {
		if (room.names.doorSign == name) {
			return true
		}
		if (room.names.floorPlan == name) {
			return true
		}
		return false
	}

	private static def dispatch boolean hasNameInternal(Passage passage,
		String name) {
		return false
	}
}
