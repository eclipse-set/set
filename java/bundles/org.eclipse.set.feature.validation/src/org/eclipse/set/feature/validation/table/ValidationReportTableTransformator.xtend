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
import org.eclipse.set.utils.table.tree.AbstractValidationProblemTransformator
import org.eclipse.set.utils.table.TMFactory

import static extension org.eclipse.set.model.tablemodel.extensions.TableRowExtensions.*

class ValidationReportTableTransformator extends AbstractValidationProblemTransformator<ValidationReport> {
	ValidationTableColumns columns;
	protected val List<ColumnDescriptor> excludeColumns

	static val SPECIFIC_VALUE_REGEX = "'[^']+'"

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

	override createGroupAbstractRow(TMFactory factory, List<TableRow> groupRows) {
		val rootRow = super.createGroupAbstractRow(factory, groupRows)
		val allMsg = groupRows.map [
			//Replace specific value like attribute name in report message
			getPlainStringValue(messagesColumn)?.replaceAll(
				SPECIFIC_VALUE_REGEX, "...")
		].filterNull.toSet
		// Only generate general report message for group with same messages format
		if (allMsg.length === 1) {
			rootRow.set(messagesColumn, allMsg.get(0))
		} else {
			rootRow.set(messagesColumn, "")
		}
		return rootRow
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
