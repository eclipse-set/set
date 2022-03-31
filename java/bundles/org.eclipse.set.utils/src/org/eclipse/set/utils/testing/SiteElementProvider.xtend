/**
 * Copyright (c) 2018 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.utils.testing

import org.eclipse.set.core.services.merge.MergeService.ElementProvider
import org.eclipse.set.core.services.merge.MergeService.GuidProvider
import java.util.Optional
import org.eclipse.emf.ecore.EObject

/**
 * Element provider for site model.
 */
class SiteElementProvider implements ElementProvider {

	val GuidProvider guidProvider

	new(GuidProvider guidProvider) {
		this.guidProvider = guidProvider
	}

	override getElement(EObject container, String guid, String type) {
		val elements = container.eContents.filter [
			guid == guidProvider.getGuid(it)
		]
		if (elements.size == 1) {
			return Optional.of(elements.head)
		}
		return Optional.empty
	}
}
