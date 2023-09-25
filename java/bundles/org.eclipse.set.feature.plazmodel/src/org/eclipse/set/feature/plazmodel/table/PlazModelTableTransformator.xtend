/** 
 * Copyright (c) 2022 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.plazmodel.table

import java.util.List
import org.eclipse.emf.common.util.Enumerator
import org.eclipse.set.core.services.enumtranslation.EnumTranslationService
import org.eclipse.set.model.plazmodel.PlazReport
import org.eclipse.set.model.tablemodel.ColumnDescriptor
import org.eclipse.set.model.tablemodel.TableRow
import org.eclipse.set.model.validationreport.ValidationProblem
import org.eclipse.set.utils.table.tree.AbstractTreeTableTransformator

/**
 * Table transformator for the Plaz Model Report 
 * 
 * @author Stuecker
 */
class PlazModelTableTransformator extends AbstractTreeTableTransformator<PlazReport> {
	PlazReportColumns columns
	protected val EnumTranslationService enumTranslationService
	protected val List<ColumnDescriptor> excludeColumns

	new(PlazReportColumns columns,
		EnumTranslationService enumTranslationService) {
		super()
		this.columns = columns
		this.enumTranslationService = enumTranslationService
		excludeColumns = newArrayList(columns.RowIndex, columns.Message,
			columns.LineNumber)
	}

	 override fillProblem(TableRow instance, ValidationProblem problem) {
		if (problem.id !== 0) {
			fill(instance, columns.RowIndex, problem, [problem.id.toString])
		}
		fill(instance, columns.Severity, problem, [severity.translate])
		fill(instance, columns.ProblemType, problem, [type])
		if (problem.lineNumber !== 0) {
			fillNumeric(instance, columns.LineNumber, problem, [lineNumber])
		}
		fill(instance, columns.ObjectType, problem, [objectArt])
		fill(instance, columns.AttributeGroup, problem, [attributeName])
		fill(instance, columns.ObjectScope, problem, [objectScope?.literal])
		fill(instance, columns.ObjectState, problem, [objectState?.literal])
		fill(instance, columns.Message, problem, [message])
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
	
	override getExcludeColumns() {
		return excludeColumns
	}
	
	override getIndexColumn() {
		return columns.RowIndex
	}
	
	override getProblems(PlazReport report) {
		return report.entries
	}
	
	override getMessagesColumn() {
		return columns.Message
	}
	
}
