/**
 * Copyright (c) 2020 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */

package org.eclipse.set.utils

import java.util.List
import org.eclipse.set.basis.viewgroups.ToolboxViewGroup
import org.eclipse.set.core.services.part.ToolboxPartService

class ToolboxPartServiceExtensions {

	/**
	 * @return whether the part service has registered development views
	 */
	static def boolean hasDevelopmentViews(ToolboxPartService service) {
		return service.registeredDescriptions.values.flatten.findFirst [
			toolboxViewGroup.development
		] !== null
	}

	/**
	 * @return list of all development view groups, ordered alphabetically by the group text.
	 */
	static def List<ToolboxViewGroup> getDevelopmentGroups(
		ToolboxPartService service) {
		return service.registeredDescriptions.values.flatten.map [
			toolboxViewGroup
		].toSet.filter [
			development
		].sortBy[text]
	}
}
