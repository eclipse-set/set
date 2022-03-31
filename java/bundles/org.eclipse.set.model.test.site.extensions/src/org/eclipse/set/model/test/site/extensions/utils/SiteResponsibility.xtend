/**
 * Copyright (c) 2018 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.model.test.site.extensions.utils

import org.eclipse.set.core.services.merge.MergeService.Authority
import org.eclipse.set.core.services.merge.MergeService.Responsibility
import java.util.HashMap
import java.util.Map

/**
 * Site responsibility defaults to the secondary container. Exceptions can be
 * specified with the {@link SiteResponsibility#add(String,Authority)} function.
 * 
 * @author Schaefer
 */
class SiteResponsibility implements Responsibility {

	Map<String, Authority> guidMap = new HashMap

	override getAuthority(String primaryGuid, String secondaryGuid) {
		val authorities = #{primaryGuid, secondaryGuid}.map[guidMap.get(it)].
			filterNull
		if (authorities.size == 1) {
			return authorities.head
		}
		return Authority.SECONDARY
	}

	def void add(String guid, Authority authority) {
		guidMap.put(guid, authority)
	}
}
