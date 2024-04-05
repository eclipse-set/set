/**
 * Copyright (c) 2022 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.plazmodel.check

import org.eclipse.emf.ecore.util.ExtendedMetaData
import org.eclipse.set.basis.IModelSession
import org.eclipse.set.model.plazmodel.PlazFactory
import org.osgi.service.component.annotations.Component
import org.eclipse.set.model.planpro.BasisTypen.Zeiger_TypeClass
import org.eclipse.emf.ecore.EStructuralFeature
import org.eclipse.set.model.planpro.Verweise.VerweisePackage

/**
 * Check if that GUID references point to correct object
 * 
 * @author Truong
 */
@Component(immediate=true)
class GuidRefInvalid implements PlazCheck {
	override run(IModelSession modelSession) {
		return modelSession.planProSchnittstelle.eAllContents.filter(
			Zeiger_TypeClass).filter [
			eGet(getValidFeature(it)).equals(Boolean.TRUE)
		].map [
			val err = PlazFactory.eINSTANCE.createPlazError
			err.message = '''Der ID-Verweis «ExtendedMetaData.INSTANCE.getName(it.eClass)» im Objekt «it.eContainer.eClass.name» verweist auf ein falsches Objekt.'''

			err.type = checkType
			err.object = it
			return err
		].toList
	}

	def EStructuralFeature getValidFeature(Zeiger_TypeClass ref) {
		return ref.eClass().getEStructuralFeature(
			VerweisePackage.eINSTANCE.
				getID_Anforderer_Element_TypeClass_InvalidReference().
				getName());
	}

	override checkType() {
		return "Ungültiger ID-Verweis"
	}

	override getDescription() {
		return "Verweise verweisen auf ein Objekt vom erwarteten Typ."
	}

	override getGeneralErrMsg() {
		return "Es gibt ungültige Verweise."
	}

}
