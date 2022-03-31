/**
 * Copyright (c) 2018 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.validation.report

import org.eclipse.jface.text.BadLocationException
import org.eclipse.jface.text.IDocument
import org.eclipse.jface.text.Position
import org.eclipse.jface.text.source.Annotation
import org.eclipse.jface.text.source.AnnotationModel
import org.eclipse.jface.text.source.IAnnotationModel
import org.eclipse.set.model.validationreport.ValidationProblem
import org.eclipse.set.model.validationreport.ValidationReport
import org.eclipse.swt.graphics.Image

/**
 * Transforms a {@link ValidationReport} into an {@link IAnnotationModel}.
 * 
 * @author Schaefer
 */
class ReportToAnnotationModelTransformation {

	private static class ReportPosition extends Position {
	}

	val IDocument document
	val String annotationType
	val Image image

	/**
	 * @param document the document
	 * @param annotationType the annotation type
	 * @param image the image used for the annotations
	 */
	new(IDocument document, String annotationType,
		Image image) {
		this.document = document
		this.annotationType = annotationType
		this.image = image
	}

	/**
	 * @param report the validation report
	 * 
	 * @return the annotation model
	 */
	def IAnnotationModel create new AnnotationModel transform(
		ValidationReport report) {
		val model = it
		report.problems.forEach [
			model.addAnnotation(transformToAnnotation, transformToPosition)
		]
		return
	}

	private def Annotation create new ValidationAnnotation(image)
	transformToAnnotation(ValidationProblem problem) {
		type = annotationType
		text = problem.message
		return
	}

	private def Position create new ReportPosition
	transformToPosition(ValidationProblem problem) {
		try {
			offset = document.getLineInformation(problem.lineNumber - 1).offset
		} catch (BadLocationException exc) {
			// we use an offset of 0 for invalid line numbers
		}
		return
	}
}
