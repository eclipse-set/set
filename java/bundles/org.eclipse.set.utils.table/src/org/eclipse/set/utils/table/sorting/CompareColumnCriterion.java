/**
 * Copyright (c) 2019 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.utils.table.sorting;

import java.util.Comparator;

import org.eclipse.set.model.tablemodel.TableCell;
import org.eclipse.set.model.tablemodel.TableRow;

/**
 * Comparator for Table rows
 */
class CompareColumnCriterion implements Comparator<TableRow> {

	private final Comparator<TableCell> cellComparator;
	private final int index;

	public CompareColumnCriterion(final int columnIndex,
			final Comparator<TableCell> cellComparator) {
		this.index = columnIndex;
		this.cellComparator = cellComparator;
	}

	@Override
	public int compare(final TableRow row1, final TableRow row2) {
		return cellComparator.compare(row1.getCells().get(index),
				row2.getCells().get(index));
	}
}
