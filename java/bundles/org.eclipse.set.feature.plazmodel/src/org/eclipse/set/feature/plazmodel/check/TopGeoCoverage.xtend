/********************************************************************************
 * Copyright (c) 2024 DB InfraGO AG and others
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0 
 * 
 ********************************************************************************/
package org.eclipse.set.feature.plazmodel.check

import java.math.BigDecimal
import java.util.List
import java.util.Map
import org.apache.commons.text.StringSubstitutor
import org.eclipse.set.model.plazmodel.PlazError
import org.eclipse.set.model.plazmodel.PlazFactory
import org.eclipse.set.ppmodel.extensions.container.MultiContainer_AttributeGroup
import org.eclipse.set.model.planpro.Geodaten.TOP_Kante
import org.osgi.service.component.annotations.Component

import static extension org.eclipse.set.ppmodel.extensions.TopKanteExtensions.*
import org.eclipse.set.basis.constants.ToolboxConstants

/**
 * Validates that a TOP_Kante's length matches the associated GEO_Kante lengths
 */
@Component
class TopGeoCoverage extends AbstractPlazContainerCheck implements PlazCheck {

	override List<PlazError> run(MultiContainer_AttributeGroup container) {
		return container.TOPKante.filter [
			TOPKanteAllg?.TOPLaenge?.wert !== null
		].map [
			val geoLength = geoKanten.map[GEOKanteAllg?.GEOLaenge?.wert].
				filterNull.reduce[a, b|a + b]
			val topLength = TOPKanteAllg?.TOPLaenge?.wert
			if (geoLength === null || topLength === null)
				return null

			val diff = (topLength - geoLength).doubleValue
			if (diff < -ToolboxConstants.TOP_GEO_LENGTH_TOLERANCE)
				return createError(
					"Die topologische Kante {GUID} (Länge: {TOP_LENGTH}) ist kürzer als die dazu gehörigen geographischen Kanten (Länge: {GEO_LENGTH}).",
					topLength, geoLength)
			if (diff > ToolboxConstants.TOP_GEO_LENGTH_TOLERANCE)
				return createError(
					"Die topologische Kante {GUID} (Länge: {TOP_LENGTH}) ist länger als die dazu gehörigen geographischen Kanten (Länge: {GEO_LENGTH}).",
					topLength, geoLength)
			return null
		].filterNull.toList
	}

	private def createError(TOP_Kante topEdge, String message,
		BigDecimal topLength, BigDecimal geoLength) {
		val err = PlazFactory.eINSTANCE.createPlazError
		err.message = StringSubstitutor.replace(message, Map.of( //
		"GUID", topEdge.identitaet?.wert, //
		"TOP_LENGTH", topLength.toString, //
		"GEO_LENGTH", geoLength.toString), "{", "}"); // $NON-NLS-1$//$NON-NLS-2$
		err.type = checkType
		err.object = topEdge?.TOPKanteAllg?.TOPLaenge
		return err
	}

	override checkType() {
		return "TOP_Kante"
	}

	override getDescription() {
		return "TOP_Kanten und GEO_Kanten haben übereinstimmende Längen."
	}

	override getGeneralErrMsg() {
		return "Es gibt TOP_Kanten/GEO_Kanten mit fehlerhaften Längen."
	}

}
