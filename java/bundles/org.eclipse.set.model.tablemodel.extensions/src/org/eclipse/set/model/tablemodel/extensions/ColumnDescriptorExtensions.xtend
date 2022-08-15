/**
 * Copyright (c) 2016 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.model.tablemodel.extensions

import org.eclipse.set.model.tablemodel.ColumnDescriptor
import org.eclipse.set.model.tablemodel.Table
import org.eclipse.set.model.tablemodel.TableCell
import org.eclipse.set.model.tablemodel.TablemodelFactory
import java.util.Collection
import java.util.List
import java.util.Set

import static extension org.eclipse.set.utils.math.IntegerExtensions.*
import static extension org.eclipse.set.model.tablemodel.extensions.RowGroupExtensions.*
import org.eclipse.set.model.tablemodel.ColumnWidthMode

/**
 * Extensions for {@link ColumnDescriptor}.
 */
class ColumnDescriptorExtensions {

	/**
	 * @param descriptor this descriptor
	 * 
	 * @return the heading
	 */
	def static List<ColumnDescriptor> transform(
		Collection<ColumnDescriptor> descriptors) {
		return descriptors.toList
	}

	/**
	 * Adds missing unit headings.
	 * 
	 * @param descriptor this descriptor
	 */
	def static void addMissingHeadingUnits(ColumnDescriptor descriptor) {
		descriptor.columns.filter[!unit].forEach[addUnit("")]
	}

	/**
	 * @param descriptor this descriptor
	 * @param unit the unit
	 * 
	 * @return this descriptor
	 */
	def static ColumnDescriptor addUnit(ColumnDescriptor descriptor,
		String unit) {
		val unitDescriptor = TablemodelFactory.eINSTANCE.createColumnDescriptor
		unitDescriptor.label = unit
		unitDescriptor.unit = true
		descriptor.table.columndescriptors.add(unitDescriptor)
		descriptor.children.add(unitDescriptor)
		return unitDescriptor
	}

	/**
	 * @param descriptor this descriptor
	 * 
	 * @return the table containing this descriptor
	 */
	def static Table getTable(ColumnDescriptor descriptor) {
		return descriptor.eContainer as Table
	}

	/**
	 * Returns the addresses of the columns of this heading-tree. The addresses
	 * are similar to the {@link #getColumnLabels(ColumnDescriptor) column labels}.
	 * If a column is no heading unit, the address is equal to the label of the
	 * column. If a column is a heading unit the address is the first non empty
	 * label of the parents of the heading unit.
	 * 
	 * @param descriptor this descriptor
	 * 
	 * @return the addresses of the columns of this heading-tree
	 */
	def static String[] getColumnAdresses(ColumnDescriptor descriptor) {
		return descriptor.columns.map[columnAdress]
	}

	private static def String getColumnAdress(ColumnDescriptor descriptor) {
		var d = descriptor
		if (d.unit) {
			while (d.label.empty) {
				d = d.parent
			}
		}
		return d.label
	}

	/**
	 * @param descriptor this descriptor
	 * 
	 * @return the labels of the columns of this heading-tree
	 */
	def static String[] getColumnLabels(ColumnDescriptor descriptor) {
		return descriptor.columns.map[label]
	}

	/**
	 * The group height is the maximum height of the group headings.
	 * 
	 * @param descriptor this descriptor
	 * 
	 * @return the height of the group Row in cm
	 */
	def static double getGroupRowHeight(ColumnDescriptor descriptor) {
		return Headings.getMaxHeight(descriptor.groups)
	}

	/**
	 * The group group height is the maximum height of the group group headings.
	 * 
	 * @param descriptor this descriptor
	 * 
	 * @return the height of the group group Row in cm
	 */
	def static double getGroupGroupRowHeight(ColumnDescriptor descriptor) {
		return Headings.getMaxHeight(descriptor.groupGroups)
	}

	/**
	 * The group group group height is the maximum height of the group group
	 * group headings.
	 * 
	 * @param descriptor this descriptor
	 * 
	 * @return the height of the group group group Row in cm
	 */
	def static double getGroupGroupGroupRowHeight(ColumnDescriptor descriptor) {
		return Headings.getMaxHeight(descriptor.groupGroupGroups)
	}

	/**
	 * The Group4 height is the maximum height of the group4 headings.
	 * 
	 * @param descriptor this descriptor
	 * 
	 * @return the height of the Group4 Row in cm
	 */
	def static double getGroup4RowHeight(ColumnDescriptor descriptor) {
		return Headings.getMaxHeight(descriptor.group4)
	}

	/**
	 * Return the columns of the heading-tree. The columns are the leafs of the
	 * heading-tree root. Therefore this method returns <b>all</b> columns of
	 * the heading-tree regardless of the current heading.
	 * 
	 * @param descriptor this descriptor
	 * 
	 * @return the columns (of the heading-tree root)
	 */
	def static ColumnDescriptor[] getColumns(ColumnDescriptor descriptor) {
		return descriptor.getRoot.leaves
	}

	/**
	 * @param descriptor this descriptor
	 * 
	 * @return the root of the heading-tree
	 */
	def static ColumnDescriptor getRoot(ColumnDescriptor descriptor) {
		if (descriptor.parent === null) {
			return descriptor
		}
		return descriptor.parent.getRoot
	}

	/**
	 * @return whether this heading is the root of the heading-tree
	 */
	def static boolean isRoot(ColumnDescriptor descriptor) {
		return descriptor === descriptor.getRoot
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

	/**
	 * @param descriptor this descriptor
	 * 
	 * @return whether this heading is a leaf of the heading-tree
	 */
	def static boolean isLeaf(ColumnDescriptor descriptor) {
		return descriptor.children.empty
	}

	/**
	 * Return the group group group groups of the heading-tree. A group group
	 * group group is a group group group parent which is not root and only
	 * contains groups, group groups, group group groups or heading leafs.
	 * 
	 * @param descriptor this descriptor
	 * 
	 * @return the group group groups
	 */
	def static Set<ColumnDescriptor> getGroup4(ColumnDescriptor descriptor) {
		return descriptor.groupGroupGroups.map[parent].filter[group4Test].toSet
	}

	private def static boolean isGroup4Test(ColumnDescriptor descriptor) {
		if (descriptor.isRoot) {
			return false
		}
		return descriptor.children.forall [
			leaf || group || groupGroup || groupGroupGroup
		]
	}

	/**
	 * Return the group group groups of the heading-tree. A group group group is
	 * a group group parent which is not root and only contains groups, group
	 * groups or heading leafs.
	 * 
	 * @param descriptor this descriptor
	 * 
	 * @return the group group groups
	 */
	def static Set<ColumnDescriptor> getGroupGroupGroups(
		ColumnDescriptor descriptor) {
		return descriptor.groupGroups.map[parent].filter[groupGroupGroupTest].
			toSet
	}

	private def static boolean isGroupGroupGroupTest(
		ColumnDescriptor descriptor) {
		if (descriptor.isRoot) {
			return false
		}
		return descriptor.children.forall [
			leaf || group || groupGroup
		]
	}

	/**
	 * @param descriptor this descriptor
	 * 
	 * @return whether this heading is a group group
	 * 
	 * @see #getGroupGroupGroups(ColumnDescriptor)
	 */
	def static boolean isGroupGroupGroup(ColumnDescriptor descriptor) {
		return descriptor.groupGroupGroups.contains(descriptor)
	}

	/**
	 * Return the group groups of the heading-tree. A group group is a group
	 * parent which is not root and only contains groups or heading leafs.
	 * 
	 * @param descriptor this descriptor
	 * 
	 * @return the group groups
	 */
	def static Set<ColumnDescriptor> getGroupGroups(
		ColumnDescriptor descriptor) {
		return descriptor.groups.map[parent].filter[groupGroupTest].toSet
	}

	private def static boolean isGroupGroupTest(ColumnDescriptor descriptor) {
		if (descriptor.isRoot) {
			return false
		}
		return descriptor.children.forall[leaf || group]
	}

	/**
	 * @param descriptor this descriptor
	 * 
	 * @return whether this heading is a group group
	 * 
	 * @see #getGroupGroups(ColumnDescriptor)
	 */
	def static boolean isGroupGroup(ColumnDescriptor descriptor) {
		return descriptor.groupGroups.contains(descriptor)
	}

	/**
	 * Return the groups of the heading-tree. A group is a column parent which
	 * is not root and only contains heading leafs.
	 * 
	 * @param descriptor this descriptor
	 * 
	 * @return the groups
	 */
	def static Set<ColumnDescriptor> getGroups(ColumnDescriptor descriptor) {
		return descriptor.columns.map[parent].filter[groupTest].toSet
	}

	private def static boolean isGroupTest(ColumnDescriptor descriptor) {
		if (descriptor.isRoot) {
			return false
		}

		val isGroup = descriptor.children.forall[leaf]
		return isGroup
	}

	/**
	 * @param descriptor this descriptor
	 * 
	 * @return whether this heading is a group
	 * 
	 * @see #getGroups(ColumnDescriptor)
	 */
	def static boolean isGroup(ColumnDescriptor descriptor) {
		return descriptor.groups.contains(descriptor)
	}

	/**
	 * @param descriptor this descriptor
	 * 
	 * @return the indices of all descendant columns
	 */
	def static int[] getColumnIndices(ColumnDescriptor descriptor) {
		val columns = descriptor.columns
		return columns.length.predecessors.filter [
			columns.get(it).isDescendantOf(descriptor)
		]
	}

	/**
	 * This heading is a descendant of another heading, if this heading is
	 * either a child of the other heading or is recursively the descendant of
	 * any of the children of the other heading.
	 * 
	 * @param descriptor this descriptor
	 * @param otherDescriptor the other heading
	 * 
	 * @return whether this heading is a descendant of the other heading
	 */
	def static boolean isDescendantOf(ColumnDescriptor descriptor,
		ColumnDescriptor otherDescriptor) {
		return otherDescriptor.children.exists [
			it == descriptor || descriptor.isDescendantOf(it)
		]
	}

	/**
	 * @param descriptor this descriptor
	 * 
	 * @return the column indices for all greyed out columns
	 */
	def static int[] getGreyedColumnIndices(ColumnDescriptor descriptor) {
		val columns = descriptor.columns
		return columns.length.predecessors.filter[columns.get(it).columnGreyed]
	}

	/**
	 * @param descriptor this descriptor
	 * 
	 * @return the height of the heading in cm
	 */
	def static float getHeight(ColumnDescriptor descriptor) {
		return descriptor.height.floatValue
	}

	/**
	 * @param descriptor this descriptor
	 * 
	 * @return whether this column should be displayed greyed out
	 */
	def static boolean isColumnGreyed(ColumnDescriptor descriptor) {
		if (descriptor.greyed) {
			return true
		}
		if (descriptor.parent !== null) {
			return descriptor.parent.columnGreyed
		}
		return false
	}

	/**
	 * The width of a column is the first non <code>null</code> width in the
	 * heading path form the leaf to the root or <code>null</code>, if there are
	 * no non <code>null</code> widths in the path.
	 * 
	 * @param descriptor this descriptor
	 * @param columnIdx the column index
	 * 
	 * @return the width of the column in cm or <code>null</code>
	 */
	def static Float getColumnWidth(ColumnDescriptor descriptor,
		int columnIdx) {
		return descriptor.columns.get(columnIdx).columnWidth
	}
	
	/**
	 * The width mode of a column is the width mode of the column with a non 
	 * <code>null</code> width in the heading path form the leaf to the root 
	 * or <code>null</code>, if there are no non <code>null</code> widths in 
	 * the path.
	 * 
	 * @param descriptor this descriptor
	 * @param columnIdx the column index
	 * 
	 * @return the width mode of the column
	 */
	def static ColumnWidthMode getColumnWidthMode(ColumnDescriptor descriptor,
		int columnIdx) {
		return descriptor.columns.get(columnIdx).columnWidthMode
	}
	
	private def static ColumnWidthMode getColumnWidthMode(ColumnDescriptor descriptor) {
		if (descriptor === null) {
			return null
		}
		if (descriptor.width !== null) {
			return descriptor.widthMode
		}
		return descriptor.parent.columnWidthMode
	}
	

	/**
	 * The width of a descriptor is the first non <code>null</code> width in
	 * the heading path form the descriptor to the root or <code>null</code>,
	 * if there are no non <code>null</code> widths in the path.
	 * 
	 * @param descriptor this descriptor
	 * 
	 * @return the width of the descriptor in cm or <code>null</code>
	 */
	def static Float getColumnWidth(ColumnDescriptor descriptor) {
		if (descriptor === null) {
			return null
		}
		val result = descriptor.width
		if (result !== null) {
			return result
		}
		return descriptor.parent.columnWidth
	}

	/**
	 * @param descriptor this column descriptor
	 * 
	 * @return a new table cell for this descriptor
	 */
	static def TableCell createTableCell(ColumnDescriptor descriptor) {
		val newColumn = TablemodelFactory.eINSTANCE.createTableCell
		newColumn.columndescriptor = descriptor
		return newColumn
	}

	/**
	 * @param descriptor this column descriptor
	 * @param other another column descriptor
	 */
	def static boolean isEqual(ColumnDescriptor descriptor,
		ColumnDescriptor other) {
		var result = testEqual(descriptor.width, other.width)
		result = result && testEqual(descriptor.label, other.label)
		result = result && testEqual(descriptor.greyed, other.greyed)
		result = result && testEqual(descriptor.unit, other.unit)
		result = result && testEqual(descriptor.height, other.height)
		result = result && testEqualChildren(descriptor, other)
		return result
	}

	/**
	 * @param descriptor this column descriptor
	 * 
	 * @return the cells of this column descriptor
	 */
	def static List<TableCell> getColumnCells(ColumnDescriptor descriptor) {
		return descriptor.table.tablecontent.rowgroups.map [
			it.getColumnCells(descriptor)
		].flatten.toList
	}

	private static def boolean testEqualChildren(ColumnDescriptor descriptor,
		ColumnDescriptor other) {
		if (descriptor.children.size != other.children.size) {
			return false
		}
		return descriptor.children.indexed.forall [
			value.isEqual(other.children.get(key))
		]
	}

	private static def boolean testEqual(Object left, Object right) {
		if (left !== null) {
			return left.equals(right)
		}
		return right === null
	}
}
