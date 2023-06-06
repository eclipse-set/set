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
import org.eclipse.set.toolboxmodel.Basisobjekte.Ur_Objekt
import org.osgi.service.component.annotations.Component

import static extension org.eclipse.set.ppmodel.extensions.UrObjectExtensions.getLSTZustand
import static extension org.eclipse.set.ppmodel.extensions.utils.IterableExtensions.*
import org.eclipse.emf.ecore.EClass
import org.eclipse.set.model.validationreport.ValidationSeverity

/**
 * Validates that objects are in order
 * 
 * @author Stuecker
 */
@Component(immediate=true)
class GuidOrder extends AbstractPlazContainerCheck implements PlazCheck {
	override List<PlazError> run(MultiContainer_AttributeGroup container) {
		var objectMap = <EClass, Ur_Objekt>newHashMap
		val errors = newArrayList
		for (Ur_Objekt object : container.urObjekt) {
			val newGuid = object.identitaet?.wert
			if (newGuid !== null) {
				val oldObject = objectMap.get(object.eClass)
				if (object?.LSTZustand === oldObject?.LSTZustand &&
					newGuid < oldObject?.identitaet?.wert) {
					errors.add(object)
				}
				objectMap.put(object.eClass, object)
			}

		}

		return errors.distinctBy[eClass].map [
			val err = PlazFactory.eINSTANCE.createPlazError
			err.message = '''Die Elemente des Objekttyps «it.eClass.name» sind nicht sortiert.'''
			err.type = checkType
			err.severity = ValidationSeverity.WARNING
			err.object = it
			return err
		].toList
	}

	override checkType() {
		return "GUID-Sortierung"
	}

	override getDescription() {
		return "Objekte vom gleichen Typ sind anhand ihrer GUID sortiert."
	}
}
