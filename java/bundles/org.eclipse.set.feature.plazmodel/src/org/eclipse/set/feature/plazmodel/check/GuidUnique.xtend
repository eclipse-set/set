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
import org.eclipse.set.model.plazmodel.PlazError
import org.eclipse.set.model.plazmodel.PlazFactory
import org.eclipse.set.ppmodel.extensions.container.MultiContainer_AttributeGroup
import org.osgi.service.component.annotations.Component
import java.util.Map
import org.apache.commons.text.StringSubstitutor

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
			if (knownGUIDs.contains(guid)) {
				val err = PlazFactory.eINSTANCE.createPlazError
				err.message = transformErroMsg(Map.of("GUID", guid))
				err.type = checkType
				err.object = it
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

	override transformErroMsg(Map<String, String> params) {
		return StringSubstitutor.replace(getGeneralErrMsg(), params, "{", "}"); // $NON-NLS-1$//$NON-NLS-2$
	}
}
