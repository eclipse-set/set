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
 * Comparator to put empty cells at last. In descending order empty cells are
 * first.
 * 
 * @author Truong
 */
public class EmptyLastCellComparator extends AbstractCellComparator {

	/**
	 * @param direction
	 *            sort direction
	 */
	public EmptyLastCellComparator(final SortDirectionEnum direction) {
		super(direction);
	}

	@Override
	public int compareString(final String text1, final String text2) {
		final boolean isFirstTextEmpty = text1 == null || text1.isEmpty()
				|| text1.isBlank();
		final boolean isSecondTextEmpty = text2 == null || text2.isEmpty()
				|| text2.isBlank();

		final int directionFactor = direction == SortDirectionEnum.ASC ? 1 : -1;

		if (!isFirstTextEmpty && isSecondTextEmpty) {
			return -1 * directionFactor;
		}

		if (isFirstTextEmpty && !isSecondTextEmpty) {
			return 1 * directionFactor;
		}

		return 0;
	}
}
