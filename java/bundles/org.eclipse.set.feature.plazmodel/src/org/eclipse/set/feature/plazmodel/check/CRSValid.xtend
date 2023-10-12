/**
 * Copyright (c) 2022 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.plazmodel.check

import org.eclipse.set.toolboxmodel.Geodaten.GEO_Punkt
import org.eclipse.set.model.plazmodel.PlazFactory
import org.eclipse.set.ppmodel.extensions.container.MultiContainer_AttributeGroup
import org.osgi.service.component.annotations.Component
import org.apache.commons.text.StringSubstitutor
import java.util.Map

/**
 * Validates that all GEO_Punkt_Allg have a defined and consistent coordinate reference system
 * 
 * @author Stuecker
 */
@Component
class CRSValid extends AbstractPlazContainerCheck implements PlazCheck {
	override protected run(MultiContainer_AttributeGroup container) {
		val crslist = container.contents.filter(GEO_Punkt).map [
			GEOPunktAllg?.GEOKoordinatensystem?.wert
		].toSet
		if (crslist.contains(null)) {
			val err = PlazFactory.eINSTANCE.createPlazError
			err.message = '''Es gibt Objekte ohne Angabe eines Koordinatensystem. Der sicherungstechnische Lageplan kann unvollständig sein.'''
			err.type = checkType
			err.object = null
			return #[err]
		}
		if (crslist.size > 1) {
			val err = PlazFactory.eINSTANCE.createPlazError
			err.message = '''Es gibt Objekte in unterschiedlichen Koordinatensystemen. Der sicherungstechnische Lageplan kann unvollständig sein.'''
			err.type = checkType
			err.object = null
			return #[err]
		}
		return #[]
	}

	override checkType() {
		return "Koordinatensystem"
	}

	override getDescription() {
		return "Instanzen von GEO_Punkt_Allg haben ein konsistentes Koordinatensystem."
	}

	override getGeneralErrMsg() {
		return "Es gibt Objekte mit ungültigen Koordinatensystemen. Der sicherungstechnische Lageplan kann unvollständig sein"
	}

	override transformErroMsg(Map<String, String> params) {
		return StringSubstitutor.replace(getGeneralErrMsg(), params, "{", "}"); // $NON-NLS-1$//$NON-NLS-2$
	}

}
