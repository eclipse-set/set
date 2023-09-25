/**
 * Copyright (c) 2023 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.utils.table.tree

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
import java.util.Comparator

abstract class AbstractTreeTableTransformator<T> extends AbstractTableModelTransformator<T> {
	Set<ValidationProblem> listProblems = newHashSet
	override transformTableContent(T report, TMFactory factory) {
		report.problems.forEach [
			factory.transformProblem(it)
			listProblems.add(it)
		]
		factory.instertTreeRootRow
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

	/**
	 * Insert row with abstract content into group
	 * 
	 * @param factory {@link TMFactory}
	 */
	protected def void instertTreeRootRow(TMFactory factory) {
		val rowGroups = factory.table.tablecontent.rowgroups.filter [
			rows.size >= ToolboxConfiguration.tableTreeMinimum
		]
		rowGroups.forEach [ group |
			val sortedRows = group.rows.sortWith(new Comparator<TableRow>() {
				override compare(TableRow r1, TableRow r2) {
					return r1.rowIndex.compareTo(r2.rowIndex)
				}
				
			})
			val rootRow = factory.createGeneralGroupRow(sortedRows.get(0),
				excludeColumns)
			rootRow.set(
				indexColumn, '''«sortedRows.get(0).rowIndex»..«sortedRows.last.rowIndex»''')
			val generalErroMsg = listProblems.findFirst[id === sortedRows.get(0).rowIndex]?.generalMsg
			rootRow.set(messagesColumn, generalErroMsg)
			group.rows.add(0, rootRow)
			
		]
	}

	/**
	 * The row in group, which not enought minumum content,
	 * will be extract to single group
	 * 
	 * @param factory {@link TMFactory}
	 */
	protected def void reGroupingTree(TMFactory factory) {
		val rowGroups = factory.table.tablecontent.rowgroups.filter [
			rows.size < ToolboxConfiguration.tableTreeMinimum && rows.size > 1
		]
		val rows = rowGroups.map[rows.subList(1, rows.size)].flatten.toSet
		rows.forEach [
			val newGroup = factory.rowGroup
			newGroup.rows.add(it)
		]
	}

	abstract def List<ColumnDescriptor> getExcludeColumns()

	abstract def ColumnDescriptor getIndexColumn()

	abstract def ColumnDescriptor getMessagesColumn()
}
