/**
 * Copyright (c) 2019 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.utils.table.sorting

import java.util.Comparator
import org.eclipse.nebula.widgets.nattable.sort.SortDirectionEnum
import org.eclipse.set.model.tablemodel.CellContent
import org.eclipse.set.model.tablemodel.CompareCellContent
import org.eclipse.set.model.tablemodel.StringCellContent
import org.eclipse.set.model.tablemodel.TableCell

import static extension org.eclipse.set.model.tablemodel.extensions.CellContentExtensions.*

/**
 * Comparator for TableCells
 */
package abstract class AbstractCellComparator implements Comparator<TableCell> {

	protected SortDirectionEnum direction
	/**
	 * @param direction the sort direction
	 */
	new(SortDirectionEnum direction) {
		this.direction = direction
	}
	
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
		return c1.value.compareCell(c2.value)
	}

	private def dispatch int compareDispatch(
		CompareCellContent c1,
		CompareCellContent c2
	) {
		val newResult = c1.newValue.compareCell(c2.newValue);
		
		if (newResult != 0){
			return newResult
		}
		return c1.oldValue.compareCell(c2.oldValue)
	}

	private def dispatch int compareDispatch(
		StringCellContent c1,
		CompareCellContent c2
	) {
		return c1.value.compareCell(c2.compareCellContentString)
	}

	private def dispatch int compareDispatch(
		CompareCellContent c1,
		StringCellContent c2
	) {
		return c1.compareCellContentString.compareCell(c2.value)
	}

	private def Iterable<String> compareCellContentString(CompareCellContent content) {
		return #[content.newValue,content.oldValue].flatten.toSet
	}
	
	def int compareCell(Iterable<String> iterable1, Iterable<String> iterable2) {
		// For compare, the separator should be empty
		val text1 = iterable1.iterableToString("")
		val text2 = iterable2.iterableToString("")
		try {
			val number1 = Integer.parseInt(text1)
			val number2 = Integer.parseInt(text2)
			return number1.compareInt(number2)
		} catch (NumberFormatException e) {
			return text1.compareString(text2)
		}
	}

	def int compareString(String text1, String text2)
	
	def compareInt(int number1, int number2) {
		if (direction == SortDirectionEnum.ASC) {
			return number1.compareTo(number2)
		}
		return number2.compareTo(number1)
	}
	
}
