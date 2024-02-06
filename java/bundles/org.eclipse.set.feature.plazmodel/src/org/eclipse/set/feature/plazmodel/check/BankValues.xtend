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

import java.util.List
import java.util.Map
import org.apache.commons.text.StringSubstitutor
import org.eclipse.set.core.services.graph.BankService
import org.eclipse.set.model.plazmodel.PlazError
import org.eclipse.set.model.plazmodel.PlazFactory
import org.eclipse.set.ppmodel.extensions.container.MultiContainer_AttributeGroup
import org.eclipse.set.toolboxmodel.Geodaten.Ueberhoehungslinie
import org.osgi.service.component.annotations.Component
import org.osgi.service.component.annotations.Reference
import org.eclipse.set.utils.ToolboxConfiguration

/**
 * Validates that a TOP_Kante of length zero has distinct 
 * start and end nodes (Error)
 * Validates that a TOP_Kante of length greater than zero has distinct 
 * start and end nodes (Warning)
 */
@Component
class BankValues extends AbstractPlazContainerCheck implements PlazCheck {
	@Reference
	BankService bankService;

	override List<PlazError> run(MultiContainer_AttributeGroup container) {
		return container.getUeberhoehungslinie.map [
			val bankinfo = bankService.findTOPBanking(it)
			if (bankinfo === null) {
				return createError(
					"Es konnte kein topologischer Pfad für die Überhöhungslinie {GUID} gefunden werden.",
					Map.of("GUID", it.identitaet?.wert))
			}

			val bankLength = bankinfo.line.ueberhoehungslinieAllg.
				ueberhoehungslinieLaenge.wert
			val pathLength = bankinfo.path.length
			val diff = (bankLength - pathLength).doubleValue
			if (diff > ToolboxConfiguration.bankLineTopOffsetLimit) {
				return createError(
					"Die Länge des topologischen Pfads ({PFAD}) für die Überhöhungslinie {GUID} weicht von der Länge der Überhöhungslinie ({UEBERHOEHUNG}) ab.",
					Map.of(
						"GUID",
						it.identitaet?.wert,
						"PFAD",
						pathLength.toString,
						"UEBERHOEHUNG",
						bankLength.toString
					))
			}

			return null
		].filterNull.toList
	}

	private def createError(Ueberhoehungslinie object, String message,
		Map<String, String> data) {
		val err = PlazFactory.eINSTANCE.createPlazError
		err.message = StringSubstitutor.replace(message, data, "{", "}"); // $NON-NLS-1$//$NON-NLS-2$
		err.type = checkType
		err.object = object?.identitaet
		return err
	}

	override checkType() {
		return "Überhöhungslinie"
	}

	override getDescription() {
		return "Topologische Pfade konnten für Überhöhungslinien gefunden werden."
	}

	override getGeneralErrMsg() {
		return "Es gibt Überhöhungslinien mit fehlerhaften topologischen Pfaden."
	}
}
