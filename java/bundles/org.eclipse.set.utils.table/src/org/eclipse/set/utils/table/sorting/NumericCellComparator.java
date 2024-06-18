/**
 * Copyright (c) 2019 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.utils.table.sorting;

import org.eclipse.nebula.widgets.nattable.sort.SortDirectionEnum;
import org.eclipse.set.basis.constants.ToolboxConstants;
import org.eclipse.set.model.tablemodel.extensions.CellContentExtensions;

/**
 * Cell comparator using the NUMERIC_COMPARATOR
 * 
 * @author mariusheine
 *
 */
public class NumericCellComparator extends AbstractCellComparator {

	/**
	 * @param direction
	 *            the direction to sort in
	 */
	public NumericCellComparator(final SortDirectionEnum direction) {
		super(direction);
	}

	@Override
	public int compareCell(final Iterable<String> iterable1,
			final Iterable<String> iterable2) {
		return compareString(
				CellContentExtensions.iterableToString(iterable1, ""), //$NON-NLS-1$
				CellContentExtensions.iterableToString(iterable2, "")); //$NON-NLS-1$
	}

	@Override
	public int compareString(final String text1, final String text2) {
		if (direction == SortDirectionEnum.ASC) {
			return ToolboxConstants.NUMERIC_COMPARATOR.compare(text1, text2);
		}
		return ToolboxConstants.NUMERIC_COMPARATOR.compare(text2, text1);
	}
}
