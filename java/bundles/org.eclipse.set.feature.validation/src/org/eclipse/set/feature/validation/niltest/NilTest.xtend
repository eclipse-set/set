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
import org.eclipse.set.basis.constants.ValidationResult
import org.eclipse.set.basis.exceptions.CustomValidationProblem
import org.eclipse.set.basis.files.ToolboxFile
import org.eclipse.set.feature.validation.AbstractCustomValidator
import org.eclipse.set.feature.validation.CustomValidationProblemImpl
import org.eclipse.set.feature.validation.utils.ObjectMetadataXMLReader
import org.eclipse.set.model.validationreport.ValidationSeverity
import org.osgi.service.component.annotations.Component
import org.w3c.dom.Node
import org.xml.sax.SAXException

import static extension org.eclipse.set.basis.extensions.NodeListExtensions.*
import static extension org.eclipse.set.feature.validation.utils.ObjectMetadataXMLReader.*
import static extension org.eclipse.set.utils.xml.LineNumberXMLReader.*
import org.eclipse.set.core.services.validation.CustomValidator

/** 
 * Test for intentionally incomplete data.
 * 
 * @author Schaefer
 */
@Component(immediate=true, service=CustomValidator)
class NilTest extends AbstractCustomValidator {
	static val NIL = "xsi:nil"
	
	override void validate(
		ToolboxFile toolboxFile,
		ValidationResult result
	) {
		try {
			val nilProblems = ObjectMetadataXMLReader.read(toolboxFile).validate
			if (nilProblems.length === 0) {
				result.addCustomProblem(messages
					.NilTestProblem_Description.successValidationReport
				)
			} else {
				nilProblems.forEach[result.addCustomProblem(it)]
			}
			
		} catch (ParserConfigurationException e) {
			result.addCustomProblem(e.transform)
		} catch (SAXException e) {
			e.notify
			result.addCustomProblem(e.transform)
		} catch (IOException e) {
			result.addIoError(e)
		}
	}

	private def List<CustomValidationProblem> create newLinkedList
		validate(Node node) {
			val nilAttribute = node?.attributes?.getNamedItem(NIL)
			if (nilAttribute !== null) {
				it.add(node.transform)
			}
			it.addAll(node.childNodes.iterable.flatMap[validate])
			return
	}

	private def dispatch CustomValidationProblem create new CustomValidationProblemImpl
	transform(Exception e) {
		lineNumber = 0
		message = e.message
		severity = ValidationSeverity.ERROR
		type = messages.NilTestProblem_Type
		
		return
	}

	private def dispatch CustomValidationProblem create new CustomValidationProblemImpl
	transform(Node node) {
		lineNumber = node.lineNumber
		message = messages.NilTestProblem_Message
		severity = ValidationSeverity.ERROR
		type = validationType
		objectArt = node.objectType
		objectScope = node.objectScope
		objectState = node.objectState
		attributeName = node.attributeName
		return
	}
	
	override validationType() {
		return messages.NilTestProblem_Type
	}
	
}
