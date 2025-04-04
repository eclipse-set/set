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
import org.eclipse.set.model.planpro.Basisobjekte.Bereich_Objekt
import org.osgi.service.component.annotations.Component

import static extension org.eclipse.set.ppmodel.extensions.BereichObjektExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.utils.IterableExtensions.*

/**
 * Validates that Bereich_Objekt_Teilbereich in Object not identical
 * 
 * @author Truong
 */
@Component(immediate=true)
class TeilbereicheUnique extends AbstractPlazContainerCheck implements PlazCheck {

	override List<PlazError> run(MultiContainer_AttributeGroup container) {
		return container.allContents.filter(Bereich_Objekt).map [ object |
			val identicals = object.bereichObjektTeilbereich.groupBy [
				it.topKante?.identitaet?.wert
			].filter[k, v|v.size > 1].values.map [ regionObjects |
				regionObjects.notDistinctBy[begrenzungA?.wert].map [ x |
					return regionObjects.filter [
						begrenzungA?.wert == x.begrenzungA?.wert &&
							begrenzungB?.wert == x.begrenzungB?.wert
					]
				].flatten
			].filter[size > 0]
			if (identicals.size > 0) {
				return identicals.map [
					val err = PlazFactory.eINSTANCE.createPlazError
					err.message = transformErrorMsg(
						Map.of("GUID", object.identitaet?.wert))
					err.type = checkType
					err.object = it.firstOrNull
					return err
				]
			}
			return null
		]?.filterNull?.flatten?.toList
	}

	override checkType() {
		return "Mehrfache Teilbereiche"
	}

	override getDescription() {
		return "Teilbereichsgrenzen der LST-Objekte sind einzigartig."
	}

	override getGeneralErrMsg() {
		return "Es gibt mehrere identische Teilbereiche in Objekt {GUID}."
	}
}
