/**
 * Copyright (c) 2018 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.utils.testing

import org.eclipse.set.model.test.site.Identified
import org.eclipse.set.core.services.merge.MergeService.GuidProvider
import org.eclipse.emf.ecore.EObject

/**
 * Guid provider for site model.
 */
class SiteGuidProvider implements GuidProvider {

	override getGuid(EObject element) {
		if (element instanceof Identified) {
			return element?.guid
		}
		return null
	}
}
