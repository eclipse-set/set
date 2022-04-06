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
import org.eclipse.set.core.services.merge.MergeService.ElementProvider
import java.util.Optional
import org.eclipse.emf.common.util.EList
import org.eclipse.emf.ecore.EObject

/**
 * Element provider for PlanPro model.
 * 
 * @author Schaefer
 */
class PlanProElementProvider implements ElementProvider {

	override getElement(EObject container, String guid, String type) {
		if (guid === null) {
			return Optional.empty
		}
		val eClass = container.eClass
		val feature = eClass.getEStructuralFeature(type)
		val objects = container.eGet(feature) as EList<? extends Ur_Objekt>

		// We do not use the cache here, because merging invalidates the cache anyway
		return Optional.ofNullable(objects.findFirst[identitaet.wert == guid])
	}
}
