/**
 * Copyright (c) 2022 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.plazmodel.check

import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.util.ExtendedMetaData
import org.eclipse.set.basis.IModelSession
import org.eclipse.set.model.plazmodel.PlazFactory
import org.osgi.service.component.annotations.Component

/**
 * Check if that GUID references point to correct object
 * 
 * @author Truong
 */
@Component(immediate=true)
class GuidRefInvalid implements PlazCheck {
	override run(IModelSession modelSession) {
		return modelSession.planProSchnittstelle.wzkInvalidIDReferences.filter [
			guid !== null && !sourceRef.many
		].map [
			val err = PlazFactory.eINSTANCE.createPlazError
			if (target.eGet(targetRef) === null) {
				err.message = '''Für den Verweis «ExtendedMetaData.INSTANCE.getName(sourceRef)» im Objekt «source.eClass.name» kann das Zielobjekt nicht gefunden werden.'''
			} else {
				err.message = '''Der ID-Verweis «ExtendedMetaData.INSTANCE.getName(sourceRef)» im Objekt «source.eClass.name» verweist auf ein Objekt mit falschem Typ («(source.eGet(sourceRef) as EObject).eClass.name»)'''
			}
			err.type = checkType
			err.object = target
			return err
		].toList
	}

	override checkType() {
		return "Ungültiger ID-Verweis"
	}

	override getDescription() {
		return "Verweise verweisen auf ein Objekt vom erwarteten Typ."
	}
}
