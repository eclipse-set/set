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
import org.osgi.service.component.annotations.Component

/**
 * Validates that a TOP_Kante of length zero has distinct 
 * start and end nodes (Error)
 * Validates that a TOP_Kante of length greater than zero has distinct 
 * start and end nodes (Warning)
 * 
 * @author Stuecker
 */
@Component
class TOPKanteLength extends AbstractPlazContainerCheck implements PlazCheck {
	override List<PlazError> run(MultiContainer_AttributeGroup container) {
		return container.TOPKante.filter[IDTOPKnotenA === IDTOPKnotenB]
			.filter[TOPKanteAllg?.TOPLaenge?.wert !== null]
			.map[
				val topLength = it.TOPKanteAllg?.TOPLaenge?.wert
				if(topLength.doubleValue === 0.0)
				{
					val err = PlazFactory.eINSTANCE.createPlazError
					err.message = '''TOP_Kante der Länge null enthält identische Endknoten.'''
					err.type = checkType
					err.object = it
					err.severity = ValidationSeverity.ERROR
					return err	
				}
				else 
				{
					val err = PlazFactory.eINSTANCE.createPlazError
					err.message = '''TOP_Kante enthält identische Endknoten.'''
					err.type = "TOP_Kante"
					err.object = it
					err.severity = ValidationSeverity.WARNING
					return err	
				}
			]
		.toList
	}
	
	override checkType() {
		return "TOP_Kante"
	}
	
	override getDescription() {
		return "TOP_Kanten haben eindeutige Endknoten."
	}
	
	override getGeneralErrMsg() {
		return "Es gibt TOP_Kanten mit nicht eindeutigen Endknoten."
	}
	
}
