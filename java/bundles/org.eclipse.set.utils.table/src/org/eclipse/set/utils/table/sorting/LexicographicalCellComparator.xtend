/**
 * Copyright (c) 2019 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.utils.table.sorting

import static org.eclipse.set.utils.table.sorting.SortDirection.*

/** 
 * Compares cell content lexicographical.
 * 
 * @author Schaefer
 */
package class LexicographicalCellComparator extends AbstractCellComparator {

	val SortDirection direction

	/**
	 * @param direction the sort direction
	 */
	new(SortDirection direction) {
		this.direction = direction
	}

	override int compareString(String text1, String text2) {
		if (direction == ASCENDING) {
			return (text1 ?: "").compareTo(text2 ?: "")
		}
		return (text2 ?: "").compareTo(text1 ?: "")
	}
}
