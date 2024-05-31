/**
 * Copyright (c) 2023 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.utils.table.tree

import java.util.Collections
import java.util.Comparator
import java.util.List
import java.util.Map
import java.util.function.UnaryOperator
import org.eclipse.nebula.widgets.nattable.tree.ITreeData
import org.eclipse.set.model.tablemodel.ColumnDescriptor
import org.eclipse.set.model.tablemodel.Table
import org.eclipse.set.utils.table.TMFactory
import org.eclipse.set.utils.table.TableDataProvider
import org.eclipse.set.utils.table.TableRowData

import static extension org.eclipse.set.model.tablemodel.extensions.TableExtensions.*
import static extension org.eclipse.set.model.tablemodel.extensions.TableRowExtensions.*
import java.util.Set
import org.eclipse.nebula.widgets.nattable.tree.command.TreeExpandCollapseCommand
import org.eclipse.nebula.widgets.nattable.tree.command.TreeExpandAllCommand
import org.eclipse.nebula.widgets.nattable.tree.command.TreeCollapseAllCommand
import org.eclipse.nebula.widgets.nattable.command.ILayerCommand

/**
 * ITreeData implementation for table
 * @author Truong
 */
class TreeDataProvider extends TableDataProvider implements ITreeData<TableRowData> {
	List<Pair<TableRowData, List<TableRowData>>> rowGroupMapping
	List<TableRowData> parentRowMapping
	TMFactory tmFactory;
	
	// Store original index of hidden row
	Set<Integer> hiddenRowsIndex

	new(Table table, UnaryOperator<Integer> getSourceLine) {
		super(table, getSourceLine)
		tmFactory = new TMFactory(table)
		hiddenRowsIndex = newHashSet
	}

	override refresh() {
		super.refresh()
		refreshRowGroup()
	}

	/**
	 * Grouping table contents 
	 */
	def void refreshRowGroup() {
		parentRowMapping = newLinkedList
		rowGroupMapping = newLinkedList
		val rowGroups = table.tablecontent.rowgroups.map[rows].filter [
			!empty && exists[findRow !== null]
		].toList
		rowGroups.forEach [ rows |
			val firstRow = findRow(rows.get(0))
			parentRowMapping.add(firstRow)

			if (rows.size > 1) {
				val childs = rows.subList(1, rows.size).map[findRow].filterNull.
					toList
				if (childs.empty) {
					tableContents.remove(firstRow)
				} else {
					rowGroupMapping.add(new Pair(firstRow, childs))
				}

			} else if (rows.size == 1) {
				this.rowGroupMapping.add(
					new Pair(firstRow, Collections.emptyList))
			}
		]
		if (currentComparator !== null) {
			sort(currentComparator.key, currentComparator.value)
		}
	}

	override filterMatch(TableRowData row) {
		if (row.isParentRow) {
			return true
		}
		return super.filterMatch(row)
	}

	private def boolean isParentRow(TableRowData rowData) {
		if (rowGroupMapping === null) {
			return false
		}
		// Single row group will be filtered
		return !rowGroupMapping.findFirst[key.row.equals(rowData.row)]?.value.
			isNullOrEmpty
	}

	override getChildren(TableRowData object) {
		if (object !== null && getDepthOfData(object) == 0) {
			return rowGroupMapping.findFirst [
				key.equals(object)
			]?.value
		}
		return newArrayList
	}

	override getChildren(int index) {
		return getChildren(getDataAtIndex(index))
	}

	override getChildren(TableRowData object, boolean fullDepth) {
		return getChildren(object)
	}

	override getDataAtIndex(int index) {
		if (!isValidIndex(index)) {
			return null
		}
		return tableContents.get(index)
	}

	override getOriginalRowIndex(int index) {
		val visbile = visibleRows
		if (visbile.empty) {
			return -1
		}
		val treeTableRow = visbile.get(index)
		if (treeTableRow === null || isParentRow(treeTableRow)) {
			return -1
		}
		return treeTableRow.row.rowIndex
	}
	
	override getRow(int tableRowPosition) {
		return visibleRows.get(tableRowPosition)
	}

	override getDepthOfData(TableRowData object) {
		val result = parentRowMapping.findFirst[it.equals(object)]
		return result !== null ? 0 : 1
	}

	override getDepthOfData(int index) {
		return getDepthOfData(getDataAtIndex(index))
	}

	override getElementCount() {
		return rowCount
	}

	override hasChildren(TableRowData object) {
		if (object !== null && getDepthOfData(object) == 0) {
			val childs = rowGroupMapping.findFirst[key === object]?.value
			return childs !== null && !childs.empty
		}
		return false
	}

	override hasChildren(int index) {
		return hasChildren(getDataAtIndex(index))
	}

	override indexOf(TableRowData child) {
		return tableContents.indexOf(child)
	}

	override isValidIndex(int index) {
		return index >= 0 && index < tableContents.size
	}
	
	def TableRowData getParent(TableRowData row) {
		if (row.hasChildren) {
			return row
		}
		return rowGroupMapping.findFirst[value.contains(row)]?.key
	}
	
	def List<TableRowData> getVisibleRows() {
		return tableContents.filter[!hiddenRowsIndex.contains(rowIndex)].toList
	}

	/**
	 * @param descriptor this descriptor
	 * 
	 * @return the leaves of this descriptor
	 */
	def static List<ColumnDescriptor> getLeaves(ColumnDescriptor descriptor) {
		if (descriptor.children.empty) {
			return newLinkedList(descriptor)
		}
		return descriptor.children.flatMap[leaves].toList
	}

	override void sort(int column, Comparator<? super String> comparator) {
		val Comparator<Pair<TableRowData, List<TableRowData>>> rowGroupComparator = [ group1, group2 |
			val compareContent1 = group1.value.isEmpty ? group1.key : group1.
					value.get(0)
			val compareContent2 = group2.value.isEmpty ? group2.key : group2.
					value.get(0)

			return tableRowDataComparator(column, comparator).compare(
				compareContent1, compareContent2)
		]

		val sortedChilds = rowGroupMapping.map [
			val childs = value.sortWith [ row1, row2 |
				return tableRowDataComparator(column, comparator).compare(row1,
					row2)
			]
			return new Pair(key, childs)
		]

		rowGroupMapping = sortedChilds.sortWith [ group1, group2 |
			return rowGroupComparator.compare(group1, group2)
		]
		tableContents = rowGroupMapping.flatMap [
			val newList = newLinkedList(key)
			newList.addAll(value)
			return newList
		].toList

		currentComparator = new Pair(column, comparator)
	}

	override void applyFilter(Map<Integer, Object> filterIndexToObjectMap) {
		this.filters = filterIndexToObjectMap
		refresh()
		refreshRowGroup()
		sort()

	}

	/**
	 * Add content for parent row, when collapsed
	 * @param parentIndex table contents index
	 */
	def void collasedGroup(int parentIndex) {
		val parentRow = getDataAtIndex(parentIndex)
		if (parentRow === null) {
			return
		}
		parentRow.contents = parentRow.contentsFromRow
	}

	/**
	 * Remove content for parent row, when expand
	 * @param parentIndex table contents index
	 */
	def void expandedGroup(int parentIndex) {
		val parentRow = getDataAtIndex(parentIndex)
		if (parentRow === null) {
			return
		}
		val emptyRow = tmFactory.newTableRow(parentRow.row.rowIndex,
			table.columns)
		emptyRow.set(rowIndexColumn,
			parentRow.row.getPlainStringValue(rowIndexColumn))
		parentRow.contents = emptyRow.cells.map[content].toList
	}

	def Set<Integer> getHiddenRowsIndex() {
		return hiddenRowsIndex
	}

	/**
	 * The hidden rows list should just be changed through command
	 */
	def void doExpandCollapseCommand(ILayerCommand command) {
		if (command instanceof TreeExpandAllCommand) {
			expandAllCommandHandle()
		} else if (command instanceof TreeCollapseAllCommand) {
			collapseAllCommandHandle()
		} else if (command instanceof TreeExpandCollapseCommand) {
			expandCollapseCommandHandle(command.parentIndex)
		}
	}

	private def void expandCollapseCommandHandle(int parentIndex) {
		val parentRow = getDataAtIndex(parentIndex)
		if (parentRow === null) {
			return
		}
		val childsIndex = parentRow.children.map[rowIndex]
		if (childsIndex.exists[hiddenRowsIndex.contains(it)]) {
			hiddenRowsIndex.removeAll(childsIndex)
		} else {
			hiddenRowsIndex.addAll(childsIndex)
		}
	}

	private def void collapseAllCommandHandle() {
		hiddenRowsIndex.addAll(
			parentRowMapping.map[children.map[rowIndex]].flatten.toSet)
	}

	private def void expandAllCommandHandle() {
		hiddenRowsIndex = newHashSet
	}

	private def ColumnDescriptor getRowIndexColumn() {
		return table.columns.findFirst [
			label !== null && label.equals("Lfd. Nr.")
		]
	}
}
