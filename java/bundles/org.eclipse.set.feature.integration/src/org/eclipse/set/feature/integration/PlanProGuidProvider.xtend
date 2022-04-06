/**
 * Copyright (c) 2018 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.integration

import org.eclipse.set.toolboxmodel.Basisobjekte.Ur_Objekt
import org.eclipse.set.core.services.merge.MergeService.GuidProvider
import org.eclipse.emf.ecore.EObject

/**
 * Guid provider for PlanPro model.
 */
class PlanProGuidProvider implements GuidProvider {

	override getGuid(EObject element) {
		if (element instanceof Ur_Objekt) {
			return element?.identitaet?.wert
		}
		return null
	}
}
