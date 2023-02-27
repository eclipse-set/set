/**
 * Copyright (c) 2018 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.validation.report

import org.eclipse.emf.ecore.xmi.XMIException
import org.eclipse.set.basis.IModelSession
import org.eclipse.set.basis.constants.ValidationResult.Outcome
import org.eclipse.set.basis.exceptions.CustomValidationProblem
import org.eclipse.set.core.services.version.PlanProVersionService
import org.eclipse.set.feature.validation.Messages
import org.eclipse.set.feature.validation.utils.XMLNodeFinder
import org.eclipse.set.model.validationreport.ObjectScope
import org.eclipse.set.model.validationreport.ValidationProblem
import org.eclipse.set.model.validationreport.ValidationReport
import org.eclipse.set.model.validationreport.ValidationSeverity
import org.eclipse.set.model.validationreport.ValidationreportFactory
import org.eclipse.set.utils.ToolboxConfiguration
import org.xml.sax.SAXParseException

import static extension org.eclipse.set.basis.extensions.IModelSessionExtensions.*
import static extension org.eclipse.set.feature.validation.utils.ObjectMetadataXMLReader.*
import java.util.List
import org.eclipse.set.core.services.enumtranslation.EnumTranslationService
import org.eclipse.emf.common.util.Enumerator

/**
 * Transforms a {@link IModelSession} into a {@link ValidationReport}.
 * 
 * @author Schaefer
 */
class SessionToValidationReportTransformation {

	var int id = 0

	val Messages messages

	val PlanProVersionService versionService
	var ValidationReport report
	val XMLNodeFinder xmlNodeFinder = new XMLNodeFinder();
	val EnumTranslationService enumService

	new(Messages messages, PlanProVersionService versionService,
		EnumTranslationService enumService) {
		this.messages = messages
		this.versionService = versionService
		this.enumService = enumService
	}

	/**
	 * @param session the model session
	 * 
	 * @return the validation report
	 */
	def ValidationReport transform(IModelSession session) {
		xmlNodeFinder.read(session?.toolboxFile);
		id = 0

		report = session.transformCreate

		report.problems.clear

		// transform the IO problems
		session.validationResult.xsdErrors.addProblems(messages.XsdProblemMsg,
			ValidationSeverity.ERROR, messages.XsdErrorSuccessMsg)

		// transform the XSD problems
		session.validationResult.xsdWarnings.addProblems(messages.XsdWarningMsg,
			ValidationSeverity.WARNING, messages.XsdSuccessMsg)
		session.validationResult.ioErrors.addProblems(messages.IoProblemMsg,
			ValidationSeverity.ERROR, messages.IoSuccessMsg)

		// transform custom problems
		session.validationResult.customProblems.forEach [
			report.problems.add(
				transform(createId)
			)
		]

		// Add subwork information
		val subworkTypes = session.subworkTypes
		val subworkCount = subworkTypes.length
		report.subworkCount = subworkCount.toString
		if (subworkCount > 0)
			report.subworkTypes = String.join(", ", subworkTypes)
		else
			report.subworkTypes = messages.NoSubworks

		return report
	}

	private def <T extends Exception> void addProblems(List<T> errors,
		String type, ValidationSeverity severity, String successMessage) {
		if (errors.empty) {
			report.problems.add(
				type.transform(createId, successMessage)
			)
		} else {
			errors.forEach [
				report.problems.add(
					transform(createId, type, severity)
				)
			]
		}
	}

	private def ValidationReport create ValidationreportFactory.eINSTANCE.createValidationReport
	transformCreate(IModelSession session) {

		val filePath = session?.toolboxFile?.path
		fileInfo = ValidationreportFactory.eINSTANCE.createFileInfo
		fileInfo.fileName = filePath?.toString ?: ""
		fileInfo.usedVersion = versionService.createUsedVersion(
			session.toolboxFile.modelPath)
		fileInfo.checksum = session.toolboxFile.checksum
		
		val timeStamp = session.planProSchnittstelle.planProSchnittstelleAllg.
			erzeugungZeitstempel.wert.toGregorianCalendar
		fileInfo.timeStamp = String.format("%1$td.%1$tm.%1$tY %1$tT",timeStamp)
		fileInfo.guid = session.planProSchnittstelle.identitaet.wert
		modelLoaded = if (session.hasLoadedModel) {
			messages.YesMsg
		} else {
			messages.NoMsg
		}
		valid = session?.validationResult?.outcome?.transform
		xsdValid = session?.validationResult?.xsdOutcome?.transform
		emfValid = session?.validationResult?.emfOutcome?.transform

		supportedVersion = versionService.createSupportedVersion

		toolboxVersion = ToolboxConfiguration.toolboxVersion.longVersion
	}

	private def String transform(Outcome outcome) {
		switch (outcome) {
			case VALID: {
				return messages.ValidMsg
			}
			case INVALID: {
				return messages.InvalidMsg
			}
			default: {
				return messages.ValidationNotSupportedMsg
			}
		}
	}

	private def ValidationProblem transform(
		Exception exception,
		int id,
		String type,
		ValidationSeverity severity
	) {
		val cause = exception.cause

		if (cause instanceof XMIException) {
			return cause.transformException(id, type, severity)
		}
		if (cause instanceof SAXParseException) {
			return cause.transformException(id, type, severity)
		}
		return exception.transformException(id, type, severity)
	}

	private def dispatch ValidationProblem create ValidationreportFactory.eINSTANCE.createValidationProblem
	transformException(
		XMIException exception,
		int id,
		String type,
		ValidationSeverity severity
	) {
		it.id = id
		it.type = type
		it.severity = severity
		it.severityText = severity.translate
		lineNumber = exception.line
		message = exception.transformToMessage
		val xmlNode = xmlNodeFinder.findNodeByLineNumber(lineNumber)
		if (xmlNode !== null) {
			objectArt = xmlNode.objectType
			objectScope = xmlNode.objectScope
			objectState = xmlNode.objectState
		}
		return
	}

	private def dispatch ValidationProblem create ValidationreportFactory.eINSTANCE.createValidationProblem
	transformException(
		SAXParseException exception,
		int id,
		String type,
		ValidationSeverity severity
	) {
		it.id = id
		it.type = type
		it.severity = severity
		it.severityText = severity.translate
		lineNumber = exception.lineNumber
		message = exception.transformToMessage
		val xmlNode = xmlNodeFinder.findNodeByLineNumber(lineNumber)
		if (xmlNode !== null) {
			objectArt = xmlNode.objectType
			objectScope = xmlNode.objectScope
			objectState = xmlNode.objectState
		}
		return
	}

	private def dispatch ValidationProblem create ValidationreportFactory.eINSTANCE.createValidationProblem
	transformException(
		Exception exception,
		int id,
		String type,
		ValidationSeverity severity
	) {
		it.id = id
		it.type = type
		it.severity = severity
		it.severityText = severity.translate
		lineNumber = 0
		message = exception.transformToMessage
		objectArt = ""
		objectScope = ObjectScope.UNKNOWN
		attributeName = ""
		return
	}

	private def ValidationProblem create ValidationreportFactory.eINSTANCE.createValidationProblem
	transform(
		CustomValidationProblem problem,
		int id
	) {
		it.id = id
		type = problem.type
		severity = problem.severity
		severityText = problem.severity.translate
		lineNumber = problem.lineNumber
		message = problem.message
		objectArt = problem.objectArt
		objectScope = problem.objectScope
		objectState = problem.objectState
		attributeName = problem.attributeName
		return
	}

	private def ValidationProblem create ValidationreportFactory.eINSTANCE.createValidationProblem
	transform(
		String errorType,
		int id,
		String message
	) {
		it.id = id
		type = errorType
		severity = ValidationSeverity.SUCCESS
		severityText = severity.translate
		it.message = message
	}

	private def int createId() {
		id++
		return id
	}

	private def Iterable<String> getSubworkTypes(IModelSession session) {
		val fachdaten = session.planProSchnittstelle?.LSTPlanung?.fachdaten?.
			ausgabeFachdaten
		if (fachdaten === null)
			return #[]
		val subtypes = newHashMap
		fachdaten.map[untergewerkArt?.wert?.toString].forEach [
			if (!subtypes.containsKey(it)) {
				subtypes.put(it, 1)
			} else {
				val count = subtypes.get(it)
				subtypes.put(it, count + 1)
			}
		]
		val result = newLinkedList
		subtypes.forEach[type, count|result.add('''«type» («count»)''')]
		return result
	}

	private def dispatch String transformToMessage(Exception exception) {
		return exception.message
	}

	private def dispatch String transformToMessage(
		SAXParseException exception) {
		val original = exception.message
		return original.replaceFirst("cvc[^:]+: ", "")
	}

	private def String translate(Enumerator enumerator) {
		if (enumerator === null) {
			return null
		}
		return enumService.translate(enumerator).alternative
	}

}
