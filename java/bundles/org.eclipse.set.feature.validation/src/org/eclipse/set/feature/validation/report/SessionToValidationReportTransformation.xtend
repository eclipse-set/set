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

	new(Messages messages, PlanProVersionService versionService) {
		this.messages = messages
		this.versionService = versionService
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
		val filePath = session?.toolboxFile?.path
		report.fileName = filePath?.toString ?: ""
		report.modelLoaded = if (session.hasLoadedModel) {
			messages.YesMsg
		} else {
			messages.NoMsg
		}

		report.valid = session?.validationResult?.outcome?.transform
		report.xsdValid = session?.validationResult?.xsdOutcome?.transform
		report.emfValid = session?.validationResult?.emfOutcome?.transform

		report.supportedVersion = versionService.createSupportedVersion
		report.usedVersion = versionService.createUsedVersion(
			session.toolboxFile.modelPath)
		report.toolboxVersion = ToolboxConfiguration.toolboxVersion.longVersion

		report.problems.clear
		// transform the IO problems
		session.validationResult.ioErrors.forEach [
			report.problems.add(transform(createId, messages.IoProblemMsg,
				ValidationSeverity.ERROR))
		]

		// transform the XSD problems
		session.validationResult.xsdErrors.forEach [
			report.problems.add(
				transform(createId, messages.XsdProblemMsg,
					ValidationSeverity.ERROR))
		]
		session.validationResult.xsdWarnings.forEach [
			report.problems.add(
				transform(createId, messages.XsdWarningMsg,
					ValidationSeverity.WARNING)
			)

		]
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
	
	private def ValidationReport create ValidationreportFactory.eINSTANCE.createValidationReport
	transformCreate(IModelSession session) {
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
		severityText = severity.transformToText
		lineNumber = exception.line
		message = exception.transformToMessage
		val xmlNode = xmlNodeFinder.findNodeByLineNumber(lineNumber)
		if (xmlNode !== null) {
			objectArt = xmlNode.objectType
			objectScope = xmlNode.objectScope
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
		severityText = severity.transformToText
		lineNumber = exception.lineNumber
		message = exception.transformToMessage
		val xmlNode = xmlNodeFinder.findNodeByLineNumber(lineNumber)
		if (xmlNode !== null) {
			objectArt = xmlNode.objectType
			objectScope = xmlNode.objectScope
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
		severityText = severity.transformToText
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
		severityText = severity.transformToText
		lineNumber = problem.lineNumber
		message = problem.message
		objectArt = problem.objectArt
		objectScope = problem.objectScope
		attributeName = problem.attributeName
		return
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
		return fachdaten.map[untergewerkArt?.wert?.toString].filter [ x |
			x !== null
		]
	}
	
	private def String transformToText(ValidationSeverity severity) {
		switch (severity) {
			case ERROR: {
				return messages.ErrorMsg
			}
			case WARNING: {
				return messages.WarningMsg
			}
			default: {
				throw new IllegalArgumentException(severity.toString)
			}
		}
	}

	private def dispatch String transformToMessage(Exception exception) {
		return exception.message
	}

	private def dispatch String transformToMessage(
		SAXParseException exception) {
		val original = exception.message
		return original.replaceFirst("cvc[^:]+: ", "")
	}


	
}
