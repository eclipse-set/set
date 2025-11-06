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
import org.eclipse.set.model.validationreport.ValidationSeverity
import org.eclipse.set.ppmodel.extensions.container.MultiContainer_AttributeGroup
import org.eclipse.set.model.planpro.Basisobjekte.Bereich_Objekt_Teilbereich_AttributeGroup
import org.osgi.service.component.annotations.Component

import static extension org.eclipse.set.ppmodel.extensions.BereichObjektExtensions.*
import org.eclipse.set.utils.ToolboxConfiguration
import org.eclipse.set.basis.constants.ToolboxConstants

/**
 * Validates that Bereich_Objekt_Teilbereich entries
 * - are located at a positive distance from the start of the TOP_Kante
 * - are located within the TOP_Kante
 * - have correctly ordered start/end limits 
 * 
 * @author Stuecker
 */
@Component
class TeilbereichTOPKante extends AbstractPlazContainerCheck implements PlazCheck {
	override List<PlazError> run(MultiContainer_AttributeGroup container) {
		return container.allContents.filter(
			Bereich_Objekt_Teilbereich_AttributeGroup).map [
			val limitA = it?.begrenzungA?.wert
			val limitB = it?.begrenzungB?.wert
			val topLength = it.topKante?.TOPKanteAllg?.TOPLaenge?.wert

			// Missing entries are handled via schema/nil validation
			if (limitA === null || limitB === null || topLength === null)
				return null

			val errmsg = getErrorMessage(limitA.doubleValue, limitB.doubleValue,
				topLength.doubleValue)
			if (errmsg !== null) {
				val err = PlazFactory.eINSTANCE.createPlazError
				err.message = errmsg
				err.type = checkType
				err.object = it
				err.severity = getErrorSeverity(limitA.doubleValue,
					limitB.doubleValue)
				return err
			}
			return null
		].filterNull.toList
	}

	private def getErrorMessage(double limitA, double limitB,
		double topLength) {
		if (limitA < 0)
			return '''«generalErrMsg» BegrenzungA: «limitA».'''
		if (limitB < 0)
			return '''«generalErrMsg» BegrenzungB: «limitB».'''
		if (limitA > (topLength + ToolboxConstants.TOP_GEO_LENGTH_TOLERANCE))
			return '''«generalErrMsg» BegrenzungA: «limitA». Länge TOP-Kante: «topLength»'''
		if (Math.abs(limitB - topLength) >
			ToolboxConstants.TOP_GEO_LENGTH_TOLERANCE)
			return '''«generalErrMsg» BegrenzungB: «limitB». Länge TOP-Kante: «topLength»'''
		if (limitB < limitA)
			return '''«generalErrMsg» BegrenzungA: «limitA». BegrenzungB: «limitB».'''
		return null
	}

	private def getErrorSeverity(double limitA, double limitB) {
		return ValidationSeverity.ERROR;
	}

	override checkType() {
		return "Teilbereichsgrenze"
	}

	override getDescription() {
		return "Teilbereichsgrenzen der LST-Objekte sind gültig."
	}

	override getGeneralErrMsg() {
		return "Ungültige Teilbereichsgrenzen für LST-Objekt."
	}

}
