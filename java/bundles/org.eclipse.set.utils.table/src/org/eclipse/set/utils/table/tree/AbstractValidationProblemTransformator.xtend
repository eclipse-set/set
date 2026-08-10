/**
 * Copyright (c) 2023 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.utils.table.tree

import java.util.Comparator
import java.util.List
import java.util.Set
import org.eclipse.set.model.tablemodel.ColumnDescriptor
import org.eclipse.set.model.tablemodel.TableRow
import org.eclipse.set.model.validationreport.ValidationProblem
import org.eclipse.set.utils.ToolboxConfiguration
import org.eclipse.set.utils.table.AbstractTableModelTransformator
import org.eclipse.set.utils.table.TMFactory

import static extension org.eclipse.set.model.tablemodel.extensions.RowGroupExtensions.*
import static extension org.eclipse.set.model.tablemodel.extensions.TableExtensions.*
import static extension org.eclipse.set.model.tablemodel.extensions.TableRowExtensions.*

/**
 * Abstract class for validation problem transformation
 * @author Truong
 */
abstract class AbstractValidationProblemTransformator<T> extends AbstractTableModelTransformator<T> {
	Set<ValidationProblem> listProblems = newHashSet

	override transformTableContent(T report, TMFactory factory) {
		report.problems.forEach [
			factory.transformProblem(it)
			listProblems.add(it)
		]
		factory.instertGroupAbstractRow
		factory.reGroupingTree
		return factory.table
	}
	
	abstract def List<ValidationProblem> getProblems(T report)

	protected def void transformProblem(TMFactory factory,
		ValidationProblem problem) {
		val instance = factory.newTableRow(problem.id, factory.table.columns)
		instance.fillProblem(problem)
		val rowGroup = factory.table.findRowGroup(instance, excludeColumns)
		if (rowGroup !== null) {
			rowGroup.rows.add(instance)
		} else {
			factory.newRowGroup(null).rowGroup.rows.add(instance)
		}
	}

	abstract def void fillProblem(TableRow instance, ValidationProblem problem)

	private def void instertGroupAbstractRow(TMFactory factory) {
		val rowGroups = factory.table.tablecontent.rowgroups.filter [
			rows.size >= ToolboxConfiguration.tableTreeMinimum
		]
		rowGroups.forEach [
			val sortedRows = rows.sortWith(new Comparator<TableRow>() {
				override compare(TableRow r1, TableRow r2) {
					return r1.rowIndex.compareTo(r2.rowIndex)
				}
			})
			rows.add(0, factory.createGroupAbstractRow(sortedRows))
		]
	}

	/**
	 * Create row with abstract content of group
	 * @param factory {@link TMFactory}
	 * @param groupRows list of rows in group
	 */
	protected def TableRow createGroupAbstractRow(TMFactory factory,
		List<TableRow> groupRows) {
		val rootRow = factory.createGeneralGroupRow(groupRows.get(0),
			excludeColumns)
		rootRow.set(
			indexColumn, '''«groupRows.get(0).rowIndex»..«groupRows.last.rowIndex»''')
		val generalErroMsg = listProblems.findFirst [
			id === groupRows.get(0).rowIndex
		]?.generalMsg
		rootRow.set(messagesColumn, generalErroMsg)
		return rootRow
	}

	/**
	 * The row in group, which not enought minumum content,
	 * will be extract to single group
	 * 
	 * @param factory {@link TMFactory}
	 */
	protected def void reGroupingTree(TMFactory factory) {
		val tableRowGroups = factory.table.tablecontent.rowgroups
		val extractRowGroups = tableRowGroups.filter [
			rows.size < ToolboxConfiguration.tableTreeMinimum && rows.size > 1
		]
		val rows = extractRowGroups.map[rows.subList(1, rows.size)].flatten.
			toSet
		rows.forEach [ row |
			val newGroup = factory.rowGroup
			newGroup.rows.add(row)
		]
	}

	abstract def List<ColumnDescriptor> getExcludeColumns()

	abstract def ColumnDescriptor getIndexColumn()

	abstract def ColumnDescriptor getMessagesColumn()
}
