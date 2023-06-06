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
import org.eclipse.set.toolboxmodel.Basisobjekte.Bereich_Objekt_Teilbereich_AttributeGroup
import org.osgi.service.component.annotations.Component

import static extension org.eclipse.set.ppmodel.extensions.BereichObjektExtensions.*

/**
 * Validates that Bereich_Objekt_Teilbereich entries have useful (> 10cm) lengths
 */
@Component
class TeilbereichTOPKanteLength extends AbstractPlazContainerCheck implements PlazCheck {
	override List<PlazError> run(MultiContainer_AttributeGroup container) {
		return container.allContents.filter(
			Bereich_Objekt_Teilbereich_AttributeGroup).map [
			val limitA = it?.begrenzungA?.wert
			val limitB = it?.begrenzungB?.wert
			val topLength = it.topKante?.TOPKanteAllg?.TOPLaenge?.wert

			// Missing entries are handled via schema/nil validation
			if (limitA === null || limitB === null || topLength === null)
				return null

			val length = limitA.subtract(limitB).abs().doubleValue
			if (length >= 0.1)
				return null

			val err = PlazFactory.eINSTANCE.createPlazError
			err.message = '''Sehr kleine Teilbereichslänge (<0.1 m). Die Länge des Teilbereichs ist «length» m.'''
			err.type = checkType
			err.object = it
			err.severity = ValidationSeverity.WARNING
			return err
		].filterNull.toList
	}

	override checkType() {
		return "Teilbereichslänge"
	}

	override getDescription() {
		return "Teilbereichslängen der LST-Objekte sind gültig."
	}
}
