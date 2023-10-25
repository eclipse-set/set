/**
 * Copyright (c) 2023 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.plazmodel.check

import org.eclipse.set.model.plazmodel.PlazError
import org.eclipse.set.model.plazmodel.PlazFactory
import org.eclipse.set.ppmodel.extensions.container.MultiContainer_AttributeGroup

import static extension org.eclipse.set.ppmodel.extensions.GeoKnotenExtensions.*
import org.osgi.service.component.annotations.Component
import java.util.Map
import org.apache.commons.text.StringSubstitutor

/**
 * Validates that all GEO_Kanten have a valid coordinate reference system.
 * A valid GEO_Kante either has:
 *  - a consistent CRS or
 *  - a length equal to zero (different CRS within one GEO_Kante are allowed)
 * 
 * @author Truong
 */
 @Component
class MeridianBetweenGEOKante extends AbstractPlazContainerCheck implements PlazCheck {

	override checkType() {
		return "Koordinatensystem"
	}

	override getDescription() {
		return "Instanzen von GEO_Kante haben ein konsistentes Koordinatensystem."
	}

	override protected run(MultiContainer_AttributeGroup container) {
		val geoKantenWithMeridianSprung = container.GEOKante.filter [
			val crsA = IDGEOKnotenA.geoPunkte.map [
				GEOPunktAllg?.GEOKoordinatensystem?.wert
			].toSet
			val crsB = IDGEOKnotenB.geoPunkte.map [
				GEOPunktAllg?.GEOKoordinatensystem?.wert
			].toSet
			return crsA.exists [ a |
				crsB.exists [ b |
					a != b
				]
			]
		]
		if (geoKantenWithMeridianSprung.size === 0) {
			return #[]
		}
		val errList = <PlazError>newArrayList
		geoKantenWithMeridianSprung.forEach [
			val err = PlazFactory.eINSTANCE.createPlazError
			if (GEOKanteAllg?.GEOLaenge?.wert.doubleValue !== 0.0) {
				err.message = transformErroMsg(Map.of("GUID", identitaet?.wert))
				err.type = checkType
				err.object = it
				errList.add(err)
			}
		]
		return errList
	}
	
	override getGeneralErrMsg() {
		return "Die GEO_Kante {GUID} mit der Länge > 0 hat unterschiedliche Koordinatensysteme. Der sicherungstechnische Lageplan kann unvollständig sein."
	}
	
	override transformErroMsg(Map<String, String> params) {
		return StringSubstitutor.replace(getGeneralErrMsg(), params, "{", "}"); //$NON-NLS-1$//$NON-NLS-2$
	}
}
