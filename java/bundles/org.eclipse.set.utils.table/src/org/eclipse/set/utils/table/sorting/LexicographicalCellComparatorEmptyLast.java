/**
 * Copyright (c) 2025 DB InfraGO AG and others
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 */
package org.eclipse.set.utils.table.sorting;

import org.eclipse.nebula.widgets.nattable.sort.SortDirectionEnum;

/**
 * Compares cell content lexicographical with empty cell at last
 * 
 * @author Truong
 */
public class LexicographicalCellComparatorEmptyLast
		extends AbstractCellComparator {

	/**
	 * @param direction
	 *            sort direction
	 */
	public LexicographicalCellComparatorEmptyLast(
			final SortDirectionEnum direction) {
		super(direction);
	}

	@SuppressWarnings("null")
	@Override
	public int compareString(final String text1, final String text2) {
		final boolean isFirstTextEmpty = text1 == null || text1.isEmpty()
				|| text1.isBlank();
		final boolean isSecondTextEmpty = text2 == null || text2.isEmpty()
				|| text2.isBlank();
		if (isFirstTextEmpty && isSecondTextEmpty) {
			return 0;
		}

		if (!isFirstTextEmpty && isSecondTextEmpty) {
			return -1;
		}

		// text2 can't be empty here
		if (isFirstTextEmpty && !isSecondTextEmpty) {
			return 1;
		}

		if (direction == SortDirectionEnum.ASC) {
			return text1.compareTo(text2);
		}
		return text2.compareTo(text1);
	}
}
