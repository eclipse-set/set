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
import java.util.Set
import java.util.function.UnaryOperator
import org.eclipse.nebula.widgets.nattable.tree.ITreeData
import org.eclipse.set.model.tablemodel.ColumnDescriptor
import org.eclipse.set.model.tablemodel.Table
import org.eclipse.set.utils.table.TMFactory
import org.eclipse.set.utils.table.TableDataProvider
import org.eclipse.set.utils.table.TableRowData

import static extension org.eclipse.set.model.tablemodel.extensions.TableExtensions.*
import static extension org.eclipse.set.model.tablemodel.extensions.TableRowExtensions.*

/**
 * ITreeData implementation for table
 * @author Truong
 */
class TreeDataProvider extends TableDataProvider implements ITreeData<TableRowData> {
	List<Pair<TableRowData, List<TableRowData>>> rowGroupMapping
	List<TableRowData> parentRowMapping
	TMFactory tmFactory;
	Set<Integer> hiddenRowsIndex

	new(Table table, UnaryOperator<Integer> getSourceLine) {
		super(table, getSourceLine)
		tmFactory = new TMFactory(table)
		hiddenRowsIndex = newHashSet
	}

	override refresh() {
		super.refresh()
		rowGroupMapping = newLinkedList
		parentRowMapping = newLinkedList

		val rowGroups = table.tablecontent.rowgroups
		for (var i = 0; i < rowGroups.size; i++) {
			val rows = rowGroups.get(i).rows.filter[filterMatch].toList
			if (rows.size > 1) {
				val firstRow = rows.get(0).findRow
				val childs = rows.subList(1, rows.size).map[findRow].toList
				this.rowGroupMapping.add(new Pair(firstRow, childs))
				parentRowMapping.add(firstRow)
			} else if (rows.size == 1) {
				val singleRow = rows.get(0).findRow
				this.rowGroupMapping.add(
					new Pair(singleRow, Collections.emptyList))
				parentRowMapping.add(singleRow)
			}
		}
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

	override getOriginalRowIndex(int row) {
		return tableContents.filter[!hiddenRowsIndex.contains(rowIndex)].get(
			row).row.rowIndex
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
		val Comparator<TableRowData> tableRowDataComparator = [ row1, row2 |
			val cell1 = row1.contents
			val cell2 = row2.contents
			return comparator.rowComparator(column).compare(cell1, cell2)
		]

		val Comparator<Pair<TableRowData, List<TableRowData>>> rowGroupComparator = [ group1, group2 |
			val compareContent1 = group1.value.isEmpty
					? group1.key
					: group1.value.get(0)
			val compareContent2 = group2.value.isEmpty
					? group2.key
					: group2.value.get(0)

			return tableRowDataComparator.compare(compareContent1,
				compareContent2)
		]

		val sortedChilds = rowGroupMapping.map [
			val childs = value.sortWith [ row1, row2 |
				return tableRowDataComparator.compare(row1, row2)
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
	}

	def void collasedGroup(int parentIndex) {
		val parentRow = parentIndex.rowData
		if (parentRow === null) {
			return
		}
		parentRow.contents = parentRow.contentsFromRow
		val childsIndex = parentRow.children.map[rowIndex]
		hiddenRowsIndex.addAll(childsIndex)
	}

	def void expandedGroup(int parentIndex) {
		val parentRow = parentIndex.rowData
		if (parentRow === null) {
			return
		}
		val emptyRow = tmFactory.newTableRow(parentRow.row.rowIndex,
			table.columns)
		emptyRow.set(rowIndexColumn,
			parentRow.row.getPlainStringValue(rowIndexColumn))
		parentRow.contents = emptyRow.cells.map[content].toList
		val childsIndex = parentRow.children.map[rowIndex]
		hiddenRowsIndex.removeAll(childsIndex)

	}

	private def ColumnDescriptor getRowIndexColumn() {
		return table.columns.findFirst [
			label !== null && label.equals("Lfd. Nr.")
		]
	}
}
