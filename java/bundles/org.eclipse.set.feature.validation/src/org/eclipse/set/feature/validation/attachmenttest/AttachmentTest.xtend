/**
 * Copyright (c) 2020 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.validation.attachmenttest

import com.google.common.collect.Sets
import java.util.Collection
import java.util.HashSet
import java.util.Set
import org.eclipse.set.basis.constants.ValidationResult
import org.eclipse.set.basis.exceptions.CustomValidationProblem
import org.eclipse.set.basis.files.ToolboxFile
import org.eclipse.set.feature.validation.AbstractCustomValidator
import org.eclipse.set.feature.validation.CustomValidationProblemImpl
import org.eclipse.set.feature.validation.utils.ObjectMetadataXMLReader
import org.eclipse.set.model.validationreport.ValidationSeverity
import org.osgi.service.component.annotations.Component
import org.w3c.dom.Element
import org.w3c.dom.Node

import static extension org.eclipse.set.basis.extensions.NodeExtensions.*
import static extension org.eclipse.set.basis.extensions.NodeListExtensions.*
import static extension org.eclipse.set.feature.validation.utils.ObjectMetadataXMLReader.*
import static extension org.eclipse.set.utils.xml.LineNumberXMLReader.*
import org.eclipse.set.core.services.validation.CustomValidator

/** 
 * Test for consistent use of attachment integration.
 * 
 * @author Schaefer
 */
@Component(immediate=true, service=CustomValidator)
class AttachmentTest extends AbstractCustomValidator {

	var ValidationResult result
	var Boolean globalHasData

	static val Set<String> ATTACHMENT_TAGS = #{
		"Anhang_Erlaeuterungsbericht",
		"Anhang_Material_Besonders",
		"Anhang_VzG",
		"Anhang_BAST",
		"Anhang_Dokumentation",
		"Anhang_LST_Zustand",
		"Anhang"
	}

	static val String DATA_TAG = "Daten"
	static val String IDENTITY_TAG = "Identitaet"
	static val String VALUE_TAG = "Wert"

	override void validate(
		ToolboxFile toolboxFile,
		ValidationResult result
	) {
		this.result = result
		toolboxFile.testValidate
	}

	private def void testValidate(ToolboxFile toolboxFile) {
		if (toolboxFile.format.zippedPlanPro) {
			toolboxFile.validate
		}
	}

	private def void validate(ToolboxFile toolboxFile) {
		try {
			val document = ObjectMetadataXMLReader.read(toolboxFile)
			val Set<Node> noDataAttachments = document.validate
			
			noDataAttachments.validate(toolboxFile)
		} catch (Exception e) {
			result.addCustomProblem(e.transform)
		}
	}

	private def Set<Node> validate(Node node) {
		val result = Sets.newHashSet
		val problems = <CustomValidationProblem> newHashSet
		result.validate(node, problems)
		problems.addCustomProblems(messages
			.AttachmentTest_Inconsistency_Validation_Description
		)
		return result
	}

	private def void validate(Set<Node> result, Node node, HashSet<CustomValidationProblem> problems) {
		if (node.isAttachment) {
			val hasData = node.hasData
			if (globalHasData === null) {
				globalHasData = hasData
			} else {
				if (globalHasData != hasData) {
					problems.add(
						node.transform(messages.
								AttachmentTest_InconsistencyMessage
						)
					)
				}
			}
			if (!hasData) {
				result.add(node)
			}
		}
		node.childNodes.iterable.forEach[result.validate(it, problems)]
	}

	private static def boolean isAttachment(Node node) {
		if (node instanceof Element) {
			return ATTACHMENT_TAGS.contains(node.tagName)
		}
		return false
	}

	private static def boolean hasData(Node node) {
		return node.hasChildElementWithTag(DATA_TAG)
	}

	private static def String getGuid(Node node) {
		return node?.getFirstChildWithTagName(IDENTITY_TAG)?.
			getFirstChildWithTagName(VALUE_TAG)?.textContent
	}
	
	private def addCustomProblems(Collection<CustomValidationProblem> problems, String msg) {
		if (problems.length === 0) {
			this.result.addCustomProblem(msg.successValidationReport)
		} else {
			problems.forEach[this.result.addCustomProblem(it)]
		}
	}

	private def CustomValidationProblem create new CustomValidationProblemImpl
	transform(Exception exception) {
		lineNumber = 0
		message = exception.message
		severity = ValidationSeverity.ERROR
		type = validationType

		return
	}

	private def CustomValidationProblem create new CustomValidationProblemImpl
	transform(Node node, String message) {
		lineNumber = node.lineNumber
		it.message = message
		severity = ValidationSeverity.ERROR
		type = validationType
		objectArt = node.objectType
		objectScope = node.objectScope
		objectState = node.objectState
		attributeName = node.attributeName
		return
	}

	private def validate(
		Set<Node> noDataAttachments,
		ToolboxFile toolboxFile
	) {
		noDataAttachments.map[it.validate(toolboxFile)]
			.filterNull
			.toList
			.addCustomProblems(messages
				.AttachmentTest_MissingMedia_Validation_Description
			)
	}

	private def validate(
		Node node,
		ToolboxFile toolboxFile
	) {
		if (!toolboxFile.hasMedia(node.guid)) {
			return node.transform(
				messages.AttachmentTest_MissingMediaMessage
			)
		}
		return null
	}
	
	override validationType() {
		return messages.AttachmentTestProblem_Type
	}
	
}
