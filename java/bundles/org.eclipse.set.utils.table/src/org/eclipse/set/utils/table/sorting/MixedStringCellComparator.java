/**
 * Copyright (c) 2019 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.utils.table.sorting;

import org.eclipse.set.basis.constants.ToolboxConstants;

/**
 * Cell comparator using the LST_OBJECT_NAME_COMPARATOR
 * 
 * @author Stuecker
 *
 */
public class MixedStringCellComparator extends AbstractCellComparator {

	private final SortDirection direction;

	/**
	 * @param direction
	 *            the direction to sort in
	 */
	public MixedStringCellComparator(final SortDirection direction) {
		this.direction = direction;
	}

	@Override
	public int compareString(final String text1, final String text2) {
		if (direction == SortDirection.ASCENDING) {
			return ToolboxConstants.LST_OBJECT_NAME_COMPARATOR.compare(text1,
					text2);
		}
		return ToolboxConstants.LST_OBJECT_NAME_COMPARATOR.compare(text2,
				text1);
	}
}
