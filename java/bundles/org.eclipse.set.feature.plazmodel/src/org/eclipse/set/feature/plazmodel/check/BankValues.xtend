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
import org.eclipse.set.basis.constants.Events
import org.eclipse.set.core.services.graph.BankService
import org.eclipse.set.model.plazmodel.PlazError
import org.eclipse.set.model.plazmodel.PlazFactory
import org.eclipse.set.model.validationreport.ValidationSeverity
import org.eclipse.set.ppmodel.extensions.container.MultiContainer_AttributeGroup
import org.eclipse.set.utils.ToolboxConfiguration
import org.eclipse.set.model.planpro.Geodaten.Ueberhoehungslinie
import org.osgi.service.component.annotations.Component
import org.osgi.service.component.annotations.Reference
import org.osgi.service.event.Event
import org.osgi.service.event.EventAdmin
import org.osgi.service.event.EventConstants
import org.osgi.service.event.EventHandler

/**
 * Validates that there's a valid top path for each bank line
 */
@Component(property=EventConstants.EVENT_TOPIC + "=" +
	Events.BANKING_PROCESS_DONE)
class BankValues extends AbstractPlazContainerCheck implements PlazCheck, EventHandler {
	@Reference
	BankService bankService;

	@Reference
	EventAdmin eventAdmin;

	override List<PlazError> run(MultiContainer_AttributeGroup container) {
		if (!bankService.findBankingComplete) {
			return List.of(createProcessingWarning)
		}
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

	private def PlazError createProcessingWarning() {
		val err = PlazFactory.eINSTANCE.createPlazError
		err.type = checkType
		err.message = processingWarningMsg
		err.severity = ValidationSeverity.WARNING
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

	static def String getProcessingWarningMsg() {
		return "Die Suchung nach Topologischen Pfade for Überhöhungslinien ist noch nicht beendet."
	}

	override handleEvent(Event event) {
		val properties = newHashMap;
		properties.put("org.eclipse.e4.data", this.class); // $NON-NLS-1$
		eventAdmin.sendEvent(new Event(Events.DO_PLAZ_CHECK, properties));
	}

}
