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
import org.eclipse.emf.ecore.EObject
import java.util.List
import org.eclipse.set.model.validationreport.ValidationProblem
import org.eclipse.set.model.tablemodel.TablemodelFactory
import static extension org.eclipse.set.model.tablemodel.extensions.TableExtensions.*

class ValidationReportTableTransformator extends AbstractTableModelTransformator<ValidationReport> {
	ValidationTableColumns columns;

	new(ValidationTableColumns columns) {
		super()
		this.columns = columns;
	}

	override transformTableContent(ValidationReport report, TMFactory factory) {

		for (problem : report.problems) {
			val instance = factory.newTableRow(problem.id, factory.table.columns)
			fill(instance, columns.RowIndex, problem, [problem.id.toString])
			fill(instance, columns.Severity, problem, [severityText])
			fill(instance, columns.ProblemType, problem, [type])
			if (problem.lineNumber != 0) {
				fillNumeric(instance, columns.LineNumber, problem, [lineNumber])
			}
			fill(instance, columns.ObjectType, problem, [objectArt])
			fill(instance, columns.AttributeGroup, problem, [attributeName])
			fill(instance, columns.ObjectScope,
				problem, [objectScope?.toString])
			fill(instance, columns.ObjectState, problem, [objectState?.literal])
			fill(instance, columns.Message, problem, [message])
			factory.addRowToGroup(instance,
				List.of(columns.Severity, columns.ProblemType,
					columns.ObjectType, columns.AttributeGroup,
					columns.ObjectScope, columns.ObjectState))
		}
		return factory.table
	}

	def <T extends EObject> List<List<T>> groupingProblems(List<T> problems,
		Class<?> claszz, List<String> exculdeFields) {
		val result = <List<T>>newLinkedList
		val fields = claszz.declaredFields.
			filter[!exculdeFields.contains(name)].toList
		problems.forEach [ problem |
			val isGroupAlreadExist = result.findFirst [
				val firstProblemInGroup = get(0)
				fields.forall [ field |
					try {

						field.get(firstProblemInGroup).equals(
							field.get(problem))
					} catch (IllegalAccessException exc) {
						throw new RuntimeException(exc)
					}
				]
			]

			if (isGroupAlreadExist !== null) {
				isGroupAlreadExist.add(problem)
			} else {
				result.add(List.of(problem))
			}
		]
		return result
	}
}
