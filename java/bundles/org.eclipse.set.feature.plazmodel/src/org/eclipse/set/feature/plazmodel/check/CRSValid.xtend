/**
 * Copyright (c) 2022 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.plazmodel.check

import org.eclipse.set.model.plazmodel.PlazFactory
import org.eclipse.set.ppmodel.extensions.container.MultiContainer_AttributeGroup
import org.osgi.service.component.annotations.Component

/**
 * Validates that all GEO_Punkt_Allg have a defined and consistent coordinate reference system
 * 
 * @author Stuecker
 */
@Component
class CRSValid extends AbstractPlazContainerCheck implements PlazCheck {
	override protected run(MultiContainer_AttributeGroup container) {
		val inValidGEOPoint = container.GEOPunkt.filter [
			GEOPunktAllg?.GEOKoordinatensystem?.wert === null
		].toSet
		return inValidGEOPoint.map [
			val error = PlazFactory.eINSTANCE.createPlazError
			error.type = checkType
			error.message = '''GEO_Punkt: «identitaet.wert» hat kein gültiges Koordinatensystem. Der sicherungstechnische Lageplan kann unvollständig sein.'''
			if (GEOPunktAllg === null) {
				error.object = it
			} else if (GEOPunktAllg.GEOKoordinatensystem === null ||
				GEOPunktAllg.GEOKoordinatensystem.wert === null) {
				error.object = GEOPunktAllg.GEOKoordinatensystem
			}
			return error
		].toList
	}

	override checkType() {
		return "Koordinatensystem"
	}

	override getDescription() {
		return "Instanzen von GEO_Punkt_Allg haben ein gültiges Koordinatensystem."
	}

	override getGeneralErrMsg() {
		return "Es gibt Objekte mit ungültigen Koordinatensystemen. Der sicherungstechnische Lageplan kann unvollständig sein"
	}

}
