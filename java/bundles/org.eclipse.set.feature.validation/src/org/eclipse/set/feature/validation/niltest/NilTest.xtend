/**
 * Copyright (c) 2019 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.validation.niltest

import org.eclipse.set.basis.constants.ValidationResult
import org.eclipse.set.basis.exceptions.CustomValidationProblem
import org.eclipse.set.basis.files.ToolboxFile
import org.eclipse.set.core.services.validation.CustomValidator
import org.eclipse.set.feature.validation.CustomValidationProblemImpl
import org.eclipse.set.feature.validation.Messages
import org.eclipse.set.model.validationreport.ValidationSeverity
import java.io.IOException
import javax.inject.Inject
import javax.xml.parsers.ParserConfigurationException
import org.eclipse.e4.core.services.nls.Translation
import org.osgi.service.component.annotations.Component
import org.w3c.dom.Node
import org.xml.sax.SAXException

import static extension org.eclipse.set.basis.extensions.NodeListExtensions.*
import static extension org.eclipse.set.utils.xml.LineNumberXMLReader.*
import static extension org.eclipse.set.feature.validation.utils.ObjectMetadataXMLReader.*
import org.eclipse.set.feature.validation.utils.ObjectMetadataXMLReader

/** 
 * Test for intentionally incomplete data.
 * 
 * @author Schaefer
 */
@Component(immediate=true)
class NilTest implements CustomValidator {

	static class MessagesProvider {

		@Inject
		@Translation
		Messages messages
	}

	static val NIL = "xsi:nil"

	var ValidationResult result

	var MessagesProvider messagesProvider

	override void validate(
		ToolboxFile toolboxFile,
		ValidationResult result
	) {
		try {
			this.result = result
			ObjectMetadataXMLReader.read(toolboxFile).validate
		} catch (ParserConfigurationException e) {
			result.addCustomProblem(e.transform)
		} catch (SAXException e) {
			e.notify
			result.addCustomProblem(e.transform)
		} catch (IOException e) {
			result.addIoError(e)
		}
	}

	private def void validate(Node node) {
		val nilAttribute = node?.attributes?.getNamedItem(NIL)
		if (nilAttribute !== null) {
			result.addCustomProblem(node.transform)
		}

		node.childNodes.iterable.forEach[validate]
	}

	private def dispatch CustomValidationProblem create new CustomValidationProblemImpl
	transform(Exception e) {
		lineNumber = 0
		val messages = messagesProvider.messages
		message = e.message
		severity = ValidationSeverity.ERROR
		type = messages.NilTestProblem_Type
		
		return
	}

	private def dispatch CustomValidationProblem create new CustomValidationProblemImpl
	transform(Node node) {
		lineNumber = node.lineNumber
		val messages = messagesProvider.messages
		message = messages.NilTestProblem_Message
		severity = ValidationSeverity.ERROR
		type = messages.NilTestProblem_Type
		objectArt = node.objectType
		objectScope = node.objectScope
		objectState = node.objectState
		attributeName = node.attributeName
		return
	}

	override getMessageProviderClass() {
		return typeof(MessagesProvider)
	}

	override setMessagesProvider(Object messagesProvider) {
		this.messagesProvider = messagesProvider as MessagesProvider
	}
}
