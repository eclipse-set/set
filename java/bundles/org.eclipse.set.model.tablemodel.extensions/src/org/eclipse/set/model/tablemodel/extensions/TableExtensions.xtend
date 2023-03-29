/**
 * Copyright (c) 2016 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.model.tablemodel.extensions

import org.eclipse.set.toolboxmodel.Basisobjekte.Ur_Objekt
import org.eclipse.set.model.tablemodel.ColumnDescriptor
import org.eclipse.set.model.tablemodel.RowGroup
import org.eclipse.set.model.tablemodel.Table
import org.eclipse.set.model.tablemodel.TableRow
import org.eclipse.set.model.tablemodel.TablemodelFactory
import org.eclipse.set.model.tablemodel.TablemodelPackage
import org.eclipse.set.model.tablemodel.extensions.internal.TableToFootnoteText
import org.eclipse.set.model.tablemodel.format.TextAlignment
import java.io.IOException
import java.nio.file.Path
import java.util.Collections
import java.util.List
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl

import static extension org.eclipse.set.model.tablemodel.extensions.ColumnDescriptorExtensions.*
import static extension org.eclipse.set.model.tablemodel.extensions.RowGroupExtensions.*
import static extension org.eclipse.set.model.tablemodel.extensions.TableContentExtensions.*
import static extension org.eclipse.set.model.tablemodel.extensions.TableRowExtensions.*
import static extension org.eclipse.set.utils.StringExtensions.*

/**
 * Extensions for {@link Table}.
 */
class TableExtensions {

	/**
	 * @param columnLabels the column labels
	 * 
	 * @return a new table with the given columns
	 */
	static def Table create(String root, String... columnLabels) {
		val newTable = TablemodelFactory.eINSTANCE.createTable
		val newTableContent = TablemodelFactory.eINSTANCE.createTableContent
		newTable.tablecontent = newTableContent
		val rootDescriptor = root.toDescriptor(null)
		newTable.columndescriptors.add(rootDescriptor)
		newTable.columndescriptors.addAll(columnLabels.map [
			toDescriptor(rootDescriptor)
		])
		return newTable
	}

	private static def ColumnDescriptor toDescriptor(String columnLabel,
		ColumnDescriptor parent) {
		val descriptor = TablemodelFactory.eINSTANCE.createColumnDescriptor
		descriptor.label = columnLabel
		descriptor.parent = parent
		return descriptor
	}

	/**
	 * @param table this table
	 * 
	 * @return the columns of this table
	 */
	static def List<ColumnDescriptor> getColumns(Table table) {
		val descriptors = table.columndescriptors

		if (descriptors.empty) {
			return newLinkedList()
		}

		return descriptors.get(0).columns
	}

	/**
	 * @param table this table
	 * 
	 * @return the rows of this table
	 */
	static def List<TableRow> getTableRows(Table table) {
		val content = table.tablecontent
		var rows = newLinkedList();
		for (RowGroup rowgroup : content.rowgroups)
			rows.addAll(rowgroup.rows)
		return rows
	}

	/** 
	 * @param table this table
	 * 
	 * @return the number of columns in the table
	 */
	static def int getPropertyCount(Table table) {
		return table.columns.size
	}

	/**
	 * @param table the table
	 * 
	 * @return the root {@link ColumnDescriptor} (or <code>null</code> if the
	 * table has no column descriptors)
	 */
	static def ColumnDescriptor getRootDescriptor(Table table) {
		if (table.columndescriptors.empty) {
			return null
		}
		return table.columndescriptors.get(0).getRoot()
	}

	/**
	 * @param leadingObject the leadingObject 
	 * @param table the table
	 * @param values the cell values
	 * 
	 * @return the new row with the given values
	 */
	static def TableRow addRow(Ur_Objekt leadingObject, Table table,
		String... values) {
		return addRow(leadingObject, 0, table, values)
	}

	/**
	 * @param leadingObject the leadingObject 
	 * @param leadingObjectIndex the index for the leadingObject
	 * @param table the table
	 * @param values the cell values
	 * 
	 * @return the new row with the given values
	 */
	static def TableRow addRow(Ur_Objekt leadingObject, int leadingObjectIndex,
		Table table, String... values) {
		val newRowGroup = TablemodelFactory.eINSTANCE.createRowGroup
		val newRow = TablemodelFactory.eINSTANCE.createTableRow
		newRowGroup.rows.add(newRow)
		newRowGroup.leadingObject = leadingObject
		newRowGroup.leadingObjectIndex = leadingObjectIndex
		table.tablecontent.rowgroups.add(newRowGroup)
		table.columns.forEach[newRow.cells.add(createTableCell)]
		values.indexed.forEach[newRow.set(key, value)]
		return newRow
	}

	/**
	 * @param table the table
	 * @param values the cell values
	 * 
	 * @return the new row with the given values
	 */
	static def TableRow addRow(Table table, String... values) {
		val newRowGroup = TablemodelFactory.eINSTANCE.createRowGroup
		val newRow = TablemodelFactory.eINSTANCE.createTableRow
		newRowGroup.rows.add(newRow)
		table.tablecontent.rowgroups.add(newRowGroup)
		table.columns.forEach[newRow.cells.add(createTableCell)]
		values.indexed.forEach[newRow.set(key, value)]
		return newRow
	}

	/**
	 * Set the given cell value.
	 * 
	 * @param table the table
	 * @param row the row index
	 * @param columnLabel the column label
	 * @param value the value
	 */
	static def void set(Table table, int row, String columnLabel,
		String value) {
		table.tableRows.get(row).set(columnLabel, value)
	}

	/**
	 * @param table the table
	 * @param row the row index
	 * @param columnLabel the column label
	 * 
	 * @return the plain string cell value at the given position
	 */
	static def String getPlainStringValue(Table table, int row,
		String columnLabel) {
		return table.tableRows.get(row).getPlainStringValue(columnLabel)
	}

	/**
	 * @param table the table
	 * @param row the row index
	 * @param column the column index
	 * 
	 * @return the cell value at the given position
	 */
	static def String getPlainStringValue(Table table, int row, int column) {
		return table.tableRows.get(row).getPlainStringValue(column)
	}

	/**
	 * @param table this table
	 * @param columnWidth the column width in characters
	 * 
	 * @return a simple output of the table content
	 */
	static def String toDebugString(Table table, int columnWidth) {
		return '''
			«"Row".toPaddedString(3)» «"Id".toPaddedString(columnWidth)» «table.columns.toSimpleString(columnWidth)»
			«val rows = table.tableRows»
			«FOR row : rows»
				«row.toDebugString(rows.indexOf(row) + 1, columnWidth)»
			«ENDFOR»
		'''
	}

	/**
	 * @param table this table
	 * @param other another table
	 * 
	 * @return whether the tables have equal descriptors and content
	 */
	static def boolean isEqual(Table table, Table other) {
		return hasEqualDescriptors(table, other) &&
			table.tablecontent.isEqual(other.tablecontent)
	}

	/**
	 * @param table this table
	 * @param other another table
	 * 
	 * @return whether the tables have equal descriptors
	 */
	static def boolean hasEqualDescriptors(Table table, Table other) {
		if (table.columndescriptors.size != other.columndescriptors.size) {
			return false
		}
		return table.columndescriptors.indexed.forall [
			value.isEqual(other.columndescriptors.get(it.key))
		]
	}

	/**
	 * @param table this table
	 * @param row a row
	 * 
	 * @return the first row of this table with the same row id as the given
	 * row (or <code>null</code> if no such row exists)
	 */
	static def TableRow getMatchingRow(Table table, TableRow row) {
		val group = row.group
		val rowIndex = group.rows.indexOf(row)
		return table.getGroupByLeadingObject(group.leadingObject, group.leadingObjectIndex)?.rows?.get(
			rowIndex)
	}

	/**
	 * @param table this table
	 * @param object the objects to find  the group for
	 * @param index the object index
	 * 
	 * @return the first group of this table with the given leading object (or
	 * <code>null</code> if no such row exists)
	 */
	static def RowGroup getGroupByLeadingObject(Table table, Ur_Objekt object, int index) {
		return table.tablecontent.rowgroups.findFirst [
			leadingObject?.identitaet?.wert == object?.identitaet?.wert && leadingObjectIndex === index
		]
	}

	/**
	 * @param table this table
	 * @param groupId a row group id
	 * @param index the group index
	 * 
	 * @return the first group of this table with the given id (or
	 * <code>null</code> if no such row exists)
	 */
	static def RowGroup getGroupById(Table table, String groupId, int index) {
		return table.tablecontent.rowgroups.findFirst [
			leadingObject?.identitaet?.wert == groupId && leadingObjectIndex === index
		]
	}

	/**
	 * Set the given text alignment to the given column of all row groups of this table.
	 * 
	 * @param table this table
	 * @param columnIdx the column index
	 */
	static def void setTextAlignment(Table table, ColumnDescriptor column,
		TextAlignment textAlignment) {
		val columnIdx = table.columns.indexed.findFirst [
			value.equals(column) || (value.columnPosition !== null &&
				value.columnPosition.equals(column.columnPosition))
		]?.key

		if (columnIdx === null) {
			return
		}
		table.tablecontent.rowgroups.forEach [
			it.setTextAlignment(columnIdx, textAlignment)
		]

	}

	/**
	 * @param table this table
	 * @param filename the filename
	 */
	static def void save(Table table, Path filepath) throws IOException {
		val registry = Resource.Factory.Registry.INSTANCE
		val extensionToFactoryMap = registry.getExtensionToFactoryMap()
		extensionToFactoryMap.put(TablemodelPackage.eNS_PREFIX,
			new XMIResourceFactoryImpl())
		val resourceSet = new ResourceSetImpl()
		val resource = resourceSet.createResource(
			URI.createFileURI(filepath.toString))
		resource.contents.add(table)
		resource.save(Collections.EMPTY_MAP)
	}

	/**
	 * @param table this table
	 * 
	 * @return the next footnote number for this table
	 */
	static def int getNextFootnoteNumber(Table table) {
		return table.maxFootnoteNumber + 1
	}

	/**
	 * @param table this table
	 * 
	 * @return the maximum footnote number for this table
	 */
	static def int getMaxFootnoteNumber(Table table) {
		return table.tablecontent.rowgroups.fold(0, [ n, g |
			Math.max(n, g.maxFootnoteNumber)
		])
	}

	/**
	 * @param filepath the file path
	 * 
	 * @return the table
	 */
	static def Table load(Path filepath) {
		TablemodelPackage.eINSTANCE.eClass
		val registry = Resource.Factory.Registry.INSTANCE
		val extensionToFactoryMap = registry.getExtensionToFactoryMap()
		extensionToFactoryMap.put(TablemodelPackage.eNS_PREFIX,
			new XMIResourceFactoryImpl())
		val resourceSet = new ResourceSetImpl()
		val resource = resourceSet.getResource(URI.createURI(filepath.toString),
			true)
		return resource.contents.get(0) as Table
	}

	/**
	 * @param table this table
	 * 
	 * @return the text for all footnotes of this table
	 */
	static def String getFootnotesText(Table table) {
		val tranformation = new TableToFootnoteText
		return tranformation.transform(table)
	}

	private static def String toSimpleString(List<ColumnDescriptor> descriptors,
		int columnWidth) {
		return '''«FOR descriptor : descriptors SEPARATOR " "»«
			descriptor.label.toPaddedString(columnWidth)»«ENDFOR»'''
	}

	/**
	 * @param table the table
	 * @param guid the guid to find the leading object for
	 * 
	 * @return the index of the first row for the leading object or -1 if no leading object was found for the guid
	 */
	static def int getLeadingObjectRowIndexByGUID(Table table, String guid) {
		var rowIndex = 0;
		for (RowGroup rowGroup : table.tablecontent.rowgroups) {
			if (rowGroup.leadingObject?.identitaet?.wert == guid) {
				return rowIndex;
			}
			rowIndex += rowGroup.getRows().size();
		}
		return -1;
	}
}
