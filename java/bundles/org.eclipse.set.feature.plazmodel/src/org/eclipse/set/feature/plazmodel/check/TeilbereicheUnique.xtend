/**
 * Copyright (c) 2022 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.plazmodel.check

import org.osgi.service.component.annotations.Component
import org.eclipse.set.ppmodel.extensions.container.MultiContainer_AttributeGroup
import java.util.List
import org.eclipse.set.model.plazmodel.PlazError
import static extension org.eclipse.set.ppmodel.extensions.utils.IterableExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.BereichObjektExtensions.*
import org.eclipse.set.model.plazmodel.PlazFactory
import org.eclipse.set.toolboxmodel.Basisobjekte.Bereich_Objekt
import java.util.Map
import org.apache.commons.text.StringSubstitutor

/**
 * Validates that Bereich_Objekt_Teilbereich in Object not identical
 * 
 * @author Truong
 */
@Component(immediate=true)
class TeilbereicheUnique extends AbstractPlazContainerCheck implements PlazCheck {
	
	override List<PlazError> run(MultiContainer_AttributeGroup container) {
		return container.allContents
			.filter(Bereich_Objekt)
			.map[object|
				var identicals = object.bereichObjektTeilbereich.groupBy[it.topKante?.identitaet?.wert]
					.filter[k, v| v.size > 1]
					.values.map[regionObjects| regionObjects.notDistinctBy[begrenzungA?.wert]
						.map[x| 
							return regionObjects.filter[begrenzungA?.wert == x.begrenzungA?.wert &&
									begrenzungB?.wert == x.begrenzungB?.wert]
							].flatten
						].flatten
				if (identicals.size > 1) {
					val err = PlazFactory.eINSTANCE.createPlazError
					err.message = transformErroMsg(Map.of("GUID", object.identitaet?.wert))
					err.type = checkType
					err.object = object?.identitaet
					return err
				}
				return null
			].filterNull.toList
	}
	
	override checkType() {
		return "Mehrfache Teilbereiche"
	}
	
	override getDescription() {
		return "Teilbereichsgrenzen der LST-Objekte sind einzigartig."
	}
	
	override getGeneralErrMsg() {
		return "Es gibt mehrere identische Teilbereich in Objekt {GUID}."
	}

	override transformErroMsg(Map<String, String> params) {
		return StringSubstitutor.replace(getGeneralErrMsg(), params, "{", "}"); //$NON-NLS-1$//$NON-NLS-2$
	}	
}