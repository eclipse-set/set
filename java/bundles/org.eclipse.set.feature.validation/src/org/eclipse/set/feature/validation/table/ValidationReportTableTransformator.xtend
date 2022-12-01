/** 
 * Copyright (c) 2022 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.validation.table

import org.eclipse.set.model.validationreport.ValidationReport
import org.eclipse.set.utils.table.AbstractTableModelTransformator
import org.eclipse.set.utils.table.TMFactory
import org.eclipse.set.model.tablemodel.Table
import org.eclipse.set.core.services.enumtranslation.EnumTranslationService
import org.eclipse.emf.common.util.Enumerator

class ValidationReportTableTransformator extends AbstractTableModelTransformator<ValidationReport> {	
	ValidationTableColumns columns;
	
	EnumTranslationService enumTranslationService

	new(ValidationTableColumns columns, EnumTranslationService enumTranslationService) {
		super()
		this.columns = columns;
		this.enumTranslationService = enumTranslationService;
	}
	
	override transformTableContent(ValidationReport report, TMFactory factory) {
		for (problem : report.problems) {
			val instance = factory.newTableRow(null, problem.id)
			fill(instance, columns.RowIndex, problem, [problem.id.toString])
			fill(instance, columns.Severity, problem, [severity.translate])
			fill(instance, columns.ProblemType, problem, [type])
			fillNumeric(instance, columns.LineNumber, problem, [lineNumber])
			fill(instance, columns.ObjectType, problem, [objectArt])
			fill(instance, columns.AttributeGroup, problem, [attributeName])
			fill(instance, columns.ObjectScope, problem, [objectScope.toString])
			fill(instance, columns.ObjectState, problem, [objectState])
			fill(instance, columns.Message, problem, [message])			
		}
		
		return factory.table
	}
	
		/**
	 * Translates the enum via the enum translation service.
	 * 
	 * @param enumerator the enumerator
	 * 
	 * @return the translation or <code>null</code>, if the enumerator is <code>null</code>
	 */
	def String translate(Enumerator enumerator) {
		if (enumerator === null) {
			return null
		}
		return enumTranslationService.translate(enumerator).alternative
	}
	
	override formatTableContent(Table table) {
	}
	
}
