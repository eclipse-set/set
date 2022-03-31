/**
 * Copyright (c) 2022 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.ppmodel.extensions

import de.scheidtbachmann.planpro.model.model1902.Basisobjekte.Ur_Objekt
import de.scheidtbachmann.planpro.model.model1902.BasisTypen.Zeiger_TypeClass
import org.eclipse.set.ppmodel.extensions.container.MultiContainer_AttributeGroup
import org.eclipse.set.core.services.Services
import org.eclipse.set.basis.constants.ToolboxConstants
import org.eclipse.set.ppmodel.extensions.utils.UrObjectLoader
import java.util.concurrent.ExecutionException

class MultiContainer_AttributeGroupExtensions {
	def static <T extends Ur_Objekt> T getObject(
		MultiContainer_AttributeGroup container,
		Class<T> clazz,
		Zeiger_TypeClass id
	) {
		return container.getObject(clazz, id?.wert)
	}
	
	def static <T extends Ur_Objekt> T getObject(
		MultiContainer_AttributeGroup container,
		Class<T> clazz,
		String guid
	) {
		if (guid === null) {
			return null
		}

		val cacheService = Services.getCacheService();
		val cache = cacheService.getCache(
			ToolboxConstants.CacheId.GUID_TO_OBJECT,
			container.cacheString
		);

		try {
			return cache.get(guid,
				new UrObjectLoader<T>(container.contents.filter [
					clazz.isInstance(it)
				].map[it as T], guid)) as T
		} catch (ExecutionException exc) {
			throw new RuntimeException(exc)
		}
	}
}
