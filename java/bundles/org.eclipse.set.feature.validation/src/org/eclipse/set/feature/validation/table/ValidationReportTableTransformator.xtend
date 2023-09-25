/** 
 * Copyright (c) 2022 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.validation.table

import java.util.List
import org.eclipse.set.model.tablemodel.ColumnDescriptor
import org.eclipse.set.model.tablemodel.TableRow
import org.eclipse.set.model.validationreport.ValidationProblem
import org.eclipse.set.model.validationreport.ValidationReport
import org.eclipse.set.utils.table.tree.AbstractTreeTableTransformator

class ValidationReportTableTransformator extends AbstractTreeTableTransformator<ValidationReport> {
	ValidationTableColumns columns;
	protected val List<ColumnDescriptor> excludeColumns

	new(ValidationTableColumns columns) {
		super()
		this.columns = columns;
		excludeColumns = newArrayList(columns.RowIndex, columns.Message,
			columns.LineNumber)
	}

	override getProblems(ValidationReport report) {
		return report.problems
	}

	override fillProblem(TableRow instance, ValidationProblem problem) {
		fill(instance, columns.RowIndex, problem, [problem.id.toString])
		fill(instance, columns.Severity, problem, [severityText])
		fill(instance, columns.ProblemType, problem, [type])
		if (problem.lineNumber != 0) {
			fillNumeric(instance, columns.LineNumber, problem, [lineNumber])
		}
		fill(instance, columns.ObjectType, problem, [objectArt])
		fill(instance, columns.AttributeGroup, problem, [attributeName])
		fill(instance, columns.ObjectScope, problem, [objectScope?.toString])
		fill(instance, columns.ObjectState, problem, [objectState?.literal])
		fill(instance, columns.Message, problem, [message])
	}

	override getExcludeColumns() {
		return excludeColumns
	}

	override getIndexColumn() {
		return columns.RowIndex
	}
	
	override getMessagesColumn() {
		return columns.Message
	}

}
