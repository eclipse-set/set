/**
 * Copyright (c) 2018 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.validation.report

import java.nio.file.Path
import java.util.Collections
import java.util.Comparator
import java.util.List
import java.util.Set
import org.eclipse.emf.common.util.Enumerator
import org.eclipse.emf.ecore.xmi.XMIException
import org.eclipse.set.basis.IModelSession
import org.eclipse.set.basis.constants.ValidationResult
import org.eclipse.set.basis.constants.ValidationResult.Outcome
import org.eclipse.set.basis.exceptions.CustomValidationProblem
import org.eclipse.set.basis.files.ToolboxFile
import org.eclipse.set.core.services.enumtranslation.EnumTranslationService
import org.eclipse.set.core.services.version.PlanProVersionService
import org.eclipse.set.feature.validation.Messages
import org.eclipse.set.feature.validation.utils.FileInfoReader
import org.eclipse.set.model.planpro.Layoutinformationen.PlanPro_Layoutinfo
import org.eclipse.set.model.planpro.PlanPro.PlanPro_Schnittstelle
import org.eclipse.set.model.validationreport.ObjectScope
import org.eclipse.set.model.validationreport.ValidationProblem
import org.eclipse.set.model.validationreport.ValidationReport
import org.eclipse.set.model.validationreport.ValidationSeverity
import org.eclipse.set.model.validationreport.ValidationreportFactory
import org.eclipse.set.utils.ToolboxConfiguration
import org.eclipse.set.utils.xml.XMLNodeFinder
import org.xml.sax.SAXParseException

import static java.util.Comparator.comparing
import static java.util.Comparator.naturalOrder
import static java.util.Comparator.nullsLast

import static extension org.eclipse.set.basis.extensions.IModelSessionExtensions.*
import static extension org.eclipse.set.utils.xml.ObjectMetadataXMLReader.*
import org.eclipse.set.feature.validation.LayoutInfoRequired

/**
 * Transforms a {@link IModelSession} into a {@link ValidationReport}.
 * 
 * @author Schaefer
 */
class SessionToValidationReportTransformation {

	val Messages messages

	val PlanProVersionService versionService
	var ValidationReport report
	var XMLNodeFinder xmlNodeFinder;
	val EnumTranslationService enumService
	val severityOrder = newLinkedList(ValidationSeverity.ERROR,
		ValidationSeverity.WARNING, ValidationSeverity.SUCCESS)
	val List<String> problemOrder
	Class<?> validationSourceClass;

	new(Messages messages, PlanProVersionService versionService,
		EnumTranslationService enumService) {
		this.messages = messages
		this.versionService = versionService
		this.enumService = enumService
		this.problemOrder = newLinkedList(messages.IoProblemMsg,
			messages.XsdProblemMsg, messages.XsdWarningMsg,
			messages.NilTestProblem_Type)
	}

	/**
	 * @param session the model session
	 * 
	 * @return the validation report
	 */
	def ValidationReport transform(IModelSession session) {
		session.transformCreate
		report.problems.clear
		val problems = <ValidationProblem>newLinkedList
		problems.addAll(
			session.getValidationResult(PlanPro_Schnittstelle).transform(
				session?.toolboxFile, session?.toolboxFile?.modelPath))
		val layoutValidation = session.getValidationResult(PlanPro_Layoutinfo)
		if (layoutValidation !== null) {
			problems.addAll(
				layoutValidation.transform(session?.toolboxFile,
					session?.toolboxFile?.layoutPath).filter [
					!problems.contains(it)
				]
			)
		}
		problems.sortProblem
		report.problems.addAll(problems)
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

	def Set<ValidationProblem> transform(ValidationResult validationResult,
		ToolboxFile toolboxFile, Path sourcePath) {
		val problems = <ValidationProblem>newLinkedList
		xmlNodeFinder = new XMLNodeFinder()
		xmlNodeFinder.read(toolboxFile, sourcePath)
		validationSourceClass = validationResult.validatedSourceClass
		problems.addAll(
			validationResult.xsdErrors.transform(messages.XsdProblemMsg,
				ValidationSeverity.ERROR, messages.XsdErrorSuccessMsg))

		// transform the XSD problems
		problems.addAll(
			validationResult.xsdWarnings.transform(messages.XsdWarningMsg,
				ValidationSeverity.WARNING, messages.XsdSuccessMsg))
		problems.addAll(
			validationResult.ioErrors.transform(messages.IoProblemMsg,
				ValidationSeverity.ERROR, messages.IoSuccessMsg))

		// transform custom problems
		validationResult.customProblems.forEach [
			problems.add(
				transform
			)
		]
		return problems.toSet
	}

	private def <T extends Exception> List<ValidationProblem> transform(
		List<T> errors, String type, ValidationSeverity severity,
		String successMessage) {
		val result = <ValidationProblem>newLinkedList
		if (errors.empty) {
			result.add(
				type.transform(successMessage)
			)
		} else {
			errors.forEach [
				result.add(
					transform(type, severity)
				)
			]
		}
		return result
	}

	private def transformCreate(IModelSession session) {
		report = report ?:
			ValidationreportFactory.eINSTANCE.createValidationReport
		val fileInfoReader = new FileInfoReader(versionService,
			session?.toolboxFile)
		report.fileInfo = report.fileInfo ?: fileInfoReader.fileInfo

		report.modelLoaded = if (session.hasLoadedModel) {
			messages.YesMsg
		} else {
			messages.NoMsg
		}
		report.valid = session?.getValidationsOutcome([outcome])?.transform
		report.xsdValid = session?.getValidationsOutcome([xsdOutcome])?.
			transform
		report.emfValid = session?.getValidationsOutcome([emfOutcome])?.
			transform

		report.supportedVersion = report.supportedVersion ?:
			versionService.createSupportedVersion
		report.toolboxVersion = ToolboxConfiguration.toolboxVersion.longVersion
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
		String type,
		ValidationSeverity severity
	) {
		val cause = exception.cause

		if (cause instanceof XMIException) {
			return cause.transformException(type, severity)
		}
		if (cause instanceof SAXParseException) {
			return cause.transformException(type, severity)
		}
		return exception.transformException(type, severity)
	}

	private def dispatch ValidationProblem create ValidationreportFactory.eINSTANCE.createValidationProblem
	transformException(
		XMIException exception,
		String type,
		ValidationSeverity severity
	) {
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
			attributeName = xmlNode.attributeName
		}

		if (objectScope === null || objectScope === ObjectScope.UNKNOWN) {
			if (validationSourceClass == PlanPro_Layoutinfo) {
				objectScope = ObjectScope.LAYOUT
			} else {
				objectScope = ObjectScope.CONTENT
			}
		}
		return
	}

	private def dispatch ValidationProblem create ValidationreportFactory.eINSTANCE.createValidationProblem
	transformException(
		SAXParseException exception,
		String type,
		ValidationSeverity severity
	) {
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
			attributeName = xmlNode.attributeName
		}

		if (objectScope === null || objectScope === ObjectScope.UNKNOWN) {
			if (validationSourceClass == PlanPro_Layoutinfo) {
				objectScope = ObjectScope.LAYOUT
			} else {
				objectScope = ObjectScope.CONTENT
			}
		}
		return
	}

	private def dispatch ValidationProblem create ValidationreportFactory.eINSTANCE.createValidationProblem
	transformException(
		Exception exception,
		String type,
		ValidationSeverity severity
	) {
		it.type = type
		it.severity = severity
		it.severityText = severity.translate
		lineNumber = 0
		message = exception.transformToMessage
		objectArt = ""
		attributeName = ""

		if (validationSourceClass == PlanPro_Layoutinfo) {
			objectScope = ObjectScope.LAYOUT
		} else {
			objectScope = ObjectScope.CONTENT
		}
		return
	}

	private def ValidationProblem create ValidationreportFactory.eINSTANCE.createValidationProblem
	transform(
		CustomValidationProblem problem
	) {
		type = problem.type
		severity = problem.severity
		severityText = problem.severity.translate
		lineNumber = problem.lineNumber
		message = problem.message
		objectArt = problem.objectArt
		objectScope = problem.objectScope
		objectState = problem.objectState
		attributeName = problem.attributeName

		if (objectScope === null || objectScope === ObjectScope.UNKNOWN) {
			if (validationSourceClass == PlanPro_Layoutinfo ||
				type === LayoutInfoRequired.LAYOUT_VALIDATION_TYPE) {
				objectScope = ObjectScope.LAYOUT
			} else {
				objectScope = ObjectScope.CONTENT
			}
		}
		return
	}

	private def ValidationProblem transform(
		String errorType,
		String message
	) {
		val it = ValidationreportFactory.eINSTANCE.createValidationProblem
		type = errorType
		severity = ValidationSeverity.SUCCESS
		severityText = severity.translate
		if (validationSourceClass == PlanPro_Layoutinfo) {
			objectScope = ObjectScope.LAYOUT
		} else {
			objectScope = ObjectScope.CONTENT
		}
		it.message = message
		return it
	}

	private def Iterable<String> getSubworkTypes(IModelSession session) {
		val fachdaten = session.planProSchnittstelle?.LSTPlanung?.fachdaten?.
			ausgabeFachdaten
		if (fachdaten === null)
			return #[]
		val subtypes = newHashMap
		fachdaten.map[untergewerkArt?.wert].filterNull.forEach [
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

	/**
	 * Sort problems by:
	 * 1) severity
	 * 2) problem type
	 * 3) object type
	 * 4) attribute
	 * 5) object scope
	 * 6) line number
	 * @param problems the list of problems
	 */
	private def void sortProblem(List<ValidationProblem> problems) {
		val comparator = Comparator.comparing [ ValidationProblem it |
			severityOrder.indexOf(severity)
		].thenComparing([problemOrder.indexOf(type)], nullsLast(naturalOrder)) //
		.thenComparing([objectArt], nullsLast(naturalOrder)) //
		.thenComparing(
			[attributeName], nullsLast(naturalOrder)) //
		.thenComparing(Collections.reverseOrder(comparing(
			[ValidationProblem it|objectScope], nullsLast(naturalOrder)))) //
		.thenComparing(
			[lineNumber], nullsLast(naturalOrder))

		problems.sort(comparator);
		problems.forEach[it, index|id = index + 1]
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
