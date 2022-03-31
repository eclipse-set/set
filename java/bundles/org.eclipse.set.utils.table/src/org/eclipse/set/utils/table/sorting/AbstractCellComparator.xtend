/**
 * Copyright (c) 2019 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.utils.table.sorting

import org.eclipse.set.model.tablemodel.CellContent
import org.eclipse.set.model.tablemodel.CompareCellContent
import org.eclipse.set.model.tablemodel.StringCellContent
import org.eclipse.set.model.tablemodel.TableCell
import java.util.Comparator

/**
 * Comparator for TableCells
 */
package abstract class AbstractCellComparator implements Comparator<TableCell> {

	override int compare(TableCell cell1, TableCell cell2) {
		return cell1.content.compareDispatch(cell2.content)
	}

	private def dispatch int compareDispatch(CellContent c1, CellContent c2) {
		throw new IllegalArgumentException('''«c1.class.simpleName» vs. «c2.class.simpleName»''')
	}

	private def dispatch int compareDispatch(Void c1, Void c2) {
		return 0
	}

	private def dispatch int compareDispatch(Void c1, CellContent c2) {
		return -1
	}

	private def dispatch int compareDispatch(CellContent c1, Void c2) {
		return 1
	}

	private def dispatch int compareDispatch(
		StringCellContent c1,
		StringCellContent c2
	) {
		return c1.value.compareString(c2.value)
	}

	private def dispatch int compareDispatch(
		CompareCellContent c1,
		CompareCellContent c2
	) {
		val newResult = c1.newValue.compareString(c2.newValue);
		if (newResult != 0){
			return newResult
		}
		return c1.oldValue.compareString(c2.oldValue)
	}

	private def dispatch int compareDispatch(
		StringCellContent c1,
		CompareCellContent c2
	) {
		return c1.value.compareString(c2.compareCellContentString)
	}

	private def dispatch int compareDispatch(
		CompareCellContent c1,
		StringCellContent c2
	) {
		return c1.compareCellContentString.compareString(c2.value)
	}

	private def String compareCellContentString(CompareCellContent content) {
		return '''«content.newValue»«content.oldValue»'''
	}

	def int compareString(String text1, String text2)
}
