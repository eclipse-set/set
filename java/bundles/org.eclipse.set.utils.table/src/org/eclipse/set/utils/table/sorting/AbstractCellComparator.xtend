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
import java.util.List
import org.eclipse.nebula.widgets.nattable.sort.SortDirectionEnum
import org.eclipse.set.model.tablemodel.CellContent
import org.eclipse.set.model.tablemodel.CompareCellContent
import org.eclipse.set.model.tablemodel.StringCellContent
import org.eclipse.set.model.tablemodel.TableCell

import static extension org.eclipse.set.model.tablemodel.extensions.CellContentExtensions.*
import org.eclipse.set.model.tablemodel.CompareTableCellContent

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
		// To avoid compare error, the compare cell shouldn't separate compare new/old value 
		return c1.compareCellContentString.compareCell(
			c2.compareCellContentString)
	}

	private def dispatch compareDispatch(List<String> value1,
		List<String> value2) {
		if (value1.join.isNullOrEmpty || value2.join.isNullOrEmpty) {
			return 0
		}
		return value1.compareCell(value2)
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

	private def dispatch int compareDispatch(CompareTableCellContent c1,
		CellContent c2) {
		return c1.mainPlanCellContent.compareDispatch(c2)
	}

	private def dispatch int compareDispatch(CellContent c1,
		CompareTableCellContent c2) {
		return c1.compareDispatch(c2.mainPlanCellContent)
	}

	private def dispatch int compareDispatch(CompareTableCellContent c1,
		CompareTableCellContent c2) {
		return c1.mainPlanCellContent.compareDispatch(c2.mainPlanCellContent)
	}

	private def Iterable<String> compareCellContentString(
		CompareCellContent content) {
		return #[content.newValue, content.oldValue].flatten.toSet
	}

	private def boolean isInteger(String text) {
		try {
			Integer.parseInt(text)
			return true
		} catch (NumberFormatException e) {
			return false
		}
	}

	/**
	 * Extract a numeric prefix from a string. For example
	 * "60P42" -> 60, "P42"
	 * "60" -> 60, null
	 * "P42" -> null, "P42"
	 * "" -> null, ""
	 *   
	 */
	private def Pair<String, String> splitNumericPrefix(String text) {
		if (text === null)
			return "" -> ""

		val result = text.split("((?=\\D)|(?<=\\D))", 2)
		val first = result.get(0)
		// Either entirely numeric or at most 1 character text
		if (result.length == 1) {
			return isInteger(first) ? result.get(0) -> "" : "" -> result.get(0)
		}
		// Length: 2 - Either entirely text or mixed
		val second = result.get(1)
		return isInteger(first) ? first -> second : "" -> first + second
	}

	def int compareCell(Iterable<String> iterable1,
		Iterable<String> iterable2) {
		// For compare, the separator should be empty
		val text1 = iterable1.iterableToString("").splitNumericPrefix
		val text2 = iterable2.iterableToString("").splitNumericPrefix

		// Find minimum integer prefix
		val minPrefixLength = Math.min(text1.key.length, text2.key.length)

		// Split each text into an integral prefix of equal length and a text suffix 
		val intPrefix1 = text1.key.substring(0, minPrefixLength)
		val intPrefix2 = text2.key.substring(0, minPrefixLength)
		val textSuffix1 = text1.key.substring(minPrefixLength) + text1.value
		val textSuffix2 = text2.key.substring(minPrefixLength) + text2.value

		if (minPrefixLength > 0) {
			val intCompare = Integer.parseInt(intPrefix1).compareInt(
				Integer.parseInt(intPrefix2))
			if (intCompare !== 0)
				return intCompare
		}
		return textSuffix1.compareString(textSuffix2)
	}

	def int compareString(String text1, String text2)

	def compareInt(int number1, int number2) {
		if (direction == SortDirectionEnum.ASC) {
			return number1.compareTo(number2)
		}
		return number2.compareTo(number1)
	}

}
