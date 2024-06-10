/**
 * Copyright (c) 2022 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.plazmodel.check

import java.util.List
import java.util.Map
import org.eclipse.set.model.plazmodel.PlazError
import org.eclipse.set.model.plazmodel.PlazFactory
import org.eclipse.set.ppmodel.extensions.container.MultiContainer_AttributeGroup
import org.osgi.service.component.annotations.Component

/**
 * Validates that GUIDs are unique
 * 
 * @author Stuecker
 */
@Component
class GuidUnique extends AbstractPlazContainerCheck implements PlazCheck {
	override List<PlazError> run(MultiContainer_AttributeGroup container) {
		val knownGUIDs = newHashSet
		return container.urObjekt.map [
			val guid = it.identitaet?.wert
			if (guid !== null && knownGUIDs.contains(guid)) {
				val err = PlazFactory.eINSTANCE.createPlazError
				err.message = transformErrorMsg(Map.of("GUID", guid))
				err.type = checkType
				err.object = identitaet
				return err
			}
			knownGUIDs.add(guid)
			return null
		].filterNull.toList

	}

	override checkType() {
		return "GUID-Eindeutigkeit"
	}

	override getDescription() {
		return "Die GUIDs aller Objekte sind eindeutig."
	}

	override getGeneralErrMsg() {
		return "Die GUID {GUID} ist nicht eindeutig!"
	}
}
