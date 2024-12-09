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
import org.eclipse.set.basis.graph.TopPoint
import org.eclipse.set.core.services.graph.BankService
import org.eclipse.set.core.services.graph.TopologicalGraphService
import org.eclipse.set.model.planpro.Geodaten.Ueberhoehungslinie
import org.eclipse.set.model.plazmodel.PlazError
import org.eclipse.set.model.plazmodel.PlazFactory
import org.eclipse.set.model.validationreport.ValidationSeverity
import org.eclipse.set.ppmodel.extensions.container.MultiContainer_AttributeGroup
import org.eclipse.set.utils.ToolboxConfiguration
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
	TopologicalGraphService topologicalService;

	@Reference
	EventAdmin eventAdmin;

	override List<PlazError> run(MultiContainer_AttributeGroup container) {
		if (!bankService.findBankingComplete) {
			return List.of(createProcessingWarning)
		}
		return container.getUeberhoehungslinie.filter [
			bankService.findTOPBanking(it) === null
		].map [
			val bankLength = ueberhoehungslinieAllg.
				ueberhoehungslinieLaenge.wert
			if (IDUeberhoehungA?.value === null ||
				IDUeberhoehungB?.value === null) {
				return createError(
					"Die Überhöhungslinie {GUID} hat entweder keine Ueberhoehung_A oder keine Ueberhoehung_B.",
					Map.of("GUID", it.identitaet.wert))
			}
			val begin = new TopPoint(IDUeberhoehungA.value)
			val end = new TopPoint(IDUeberhoehungB.value)
			val paths = topologicalService.findAllPathsBetween(begin, end,
				(bankLength.doubleValue +
					ToolboxConfiguration.bankLineTopOffsetLimit + 1) as int)
			if (paths.isEmpty) {
				return createError(
					"Es konnte kein topologischer Pfad für die Überhöhungslinie {GUID} gefunden werden.",
					Map.of("GUID", it.identitaet?.wert))
			}
			val completeShortesPath = paths.filter [ path |
				path.getDistance(end).present
			].reduce [ p1, p2 |
				p1.length < p2.length
				return p1
			]
			if (completeShortesPath !== null &&
				Math.abs(
					(bankLength - completeShortesPath.length).doubleValue) >
					ToolboxConfiguration.bankLineTopOffsetLimit) {
				return createError(
					"Die Länge des topologischen Pfads ({PFAD}) für die Überhöhungslinie {GUID} weicht von der Länge der Überhöhungslinie ({UEBERHOEHUNG}) ab.",
					Map.of(
						"GUID",
						it.identitaet?.wert,
						"PFAD",
						completeShortesPath?.length?.toString,
						"UEBERHOEHUNG",
						bankLength.toString
					))
			}
			val longestPath = paths.reduce [ p1, p2 |
				p1.length > p2.length
				return p1
			]
			return createError(
				"Es konnte kein passender topologischer Pfad (gesucht bis {PFAD}) für die Überhöhungslinie {GUID} mit entsprechender Länge ({UEBERHOEHUNG}) gefunden werden.",
				Map.of(
					"GUID",
					it.identitaet?.wert,
					"PFAD",
					longestPath?.length?.toString,
					"UEBERHOEHUNG",
					bankLength.toString
				)
			)
		].filterNull.toList

	}

	private def createError(Ueberhoehungslinie object, String message,
		Map<String, String> data) {
		val err = PlazFactory.eINSTANCE.createPlazError
		err.message = StringSubstitutor.replace(message, data, "{", "}"); // $NON-NLS-1$//$NON-NLS-2$
		err.type = checkType
		err.object = object
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
