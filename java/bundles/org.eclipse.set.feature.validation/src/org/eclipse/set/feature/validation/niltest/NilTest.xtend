/**
 * Copyright (c) 2019 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.validation.niltest

import java.io.IOException
import java.util.List
import javax.xml.parsers.ParserConfigurationException
import org.eclipse.set.basis.PlanProXMLNode
import org.eclipse.set.basis.constants.ValidationResult
import org.eclipse.set.basis.exceptions.CustomValidationProblem
import org.eclipse.set.basis.files.ToolboxFile
import org.eclipse.set.core.services.validation.CustomValidator
import org.eclipse.set.feature.validation.AbstractCustomValidator
import org.eclipse.set.feature.validation.CustomValidationProblemImpl
import org.eclipse.set.model.planpro.Layoutinformationen.PlanPro_Layoutinfo
import org.eclipse.set.model.planpro.PlanPro.PlanPro_Schnittstelle
import org.eclipse.set.model.validationreport.ObjectScope
import org.eclipse.set.model.validationreport.ValidationSeverity
import org.eclipse.set.utils.xml.ObjectMetadataXMLReader
import org.osgi.service.component.annotations.Component
import org.xml.sax.SAXException

import static extension org.eclipse.set.utils.xml.ObjectMetadataXMLReader.*

/** 
 * Test for intentionally incomplete data.
 * 
 * @author Schaefer
 */
@Component(immediate=true, service=CustomValidator)
class NilTest extends AbstractCustomValidator {
	static val NIL = "xsi:nil"
	Class<?> sourceClass

	override void validate(
		ToolboxFile toolboxFile,
		ValidationResult result,
		FileType type
	) {
		try {
			sourceClass = result.validatedSourceClass
			val nilProblems = newLinkedList
			if (sourceClass.isAssignableFrom(PlanPro_Schnittstelle)) {
				nilProblems.addAll(
					ObjectMetadataXMLReader.read(toolboxFile,
						toolboxFile.modelPath).validate)
			} else if (toolboxFile.layoutPath?.toFile.exists &&
				sourceClass.isAssignableFrom(PlanPro_Layoutinfo)) {
				nilProblems.addAll(
					ObjectMetadataXMLReader.read(toolboxFile,
						toolboxFile.layoutPath).validate)
			}

			if (nilProblems.size === 0) {
				result.addCustomProblem(
					messages.NilTestProblem_Description.successValidationReport
				)
			} else {
				nilProblems.forEach[result.addCustomProblem(it)]
			}

		} catch (ParserConfigurationException e) {
			result.addCustomProblem(e.transform)
		} catch (SAXException e) {
			result.addCustomProblem(e.transform)
		} catch (IOException e) {
			result.addIoError(e)
		}
	}

	private def List<CustomValidationProblem> validate(PlanProXMLNode node) {
		val it = newLinkedList
		val nilAttribute = node?.attributes.exists[attributeName == NIL]
		if (nilAttribute) {
			it.add(node.transform)
		}
		it.addAll(node.getChildren.flatMap[validate])
		return it
	}

	private def dispatch CustomValidationProblem transform(Exception e) {
		val it = new CustomValidationProblemImpl
		lineNumber = 0
		message = e.message
		severity = ValidationSeverity.ERROR
		type = messages.NilTestProblem_Type
		if (sourceClass.isAssignableFrom(PlanPro_Layoutinfo)) {
			objectScope = ObjectScope.LAYOUT
		}
		return it
	}

	private def dispatch CustomValidationProblem transform(PlanProXMLNode node) {
		val it = new CustomValidationProblemImpl
		lineNumber = Integer.parseInt(node.startLineNumber)
		message = messages.NilTestProblem_Message
		severity = ValidationSeverity.WARNING
		type = validationType
		objectArt = node.objectType
		objectScope = node.objectScope
		objectState = node.objectState
		attributeName = node.attributeName
		return it
	}

	override validationType() {
		return messages.NilTestProblem_Type
	}

}
