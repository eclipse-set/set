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
import java.util.List;

import org.eclipse.nebula.widgets.nattable.sort.SortDirectionEnum;
import org.eclipse.set.model.tablemodel.RowGroup;
import org.eclipse.set.model.tablemodel.TableCell;

/**
 * Build a {@link Comparator} for {@link RowGroup}s.
 * 
 * @author Schaefer
 */
public class ComparatorBuilder {

	/**
	 * The cell comparator to use for sorting.
	 */
	public static enum CellComparatorType {
		/**
		 * Sort string content lexicographical.
		 */
		LEXICOGRAPHICAL,

		/**
		 * Sort string content with mixed string comparator.
		 */
		MIXED_STRING,

		/**
		 * Sort string content with numeric comparator.
		 */
		NUMERIC,
	}

	private final TableRowGroupComparator tableRowGroupComparator;

	/**
	 * @param tableRowGroupComparator
	 *            the table row group comparator
	 */
	public ComparatorBuilder(
			final TableRowGroupComparator tableRowGroupComparator) {
		this.tableRowGroupComparator = tableRowGroupComparator;
	}

	/**
	 * @return the row group comparator
	 */
	public Comparator<RowGroup> build() {
		return tableRowGroupComparator;
	}

	/**
	 * @param types
	 *            the list of types implying the sort order
	 * 
	 * @return this builder
	 */
	public ComparatorBuilder sort(final Class<?>... types) {
		tableRowGroupComparator.addCriterion(List.of(types));
		return this;
	}

	/**
	 * @param columnId
	 *            the column id
	 * @param cellComparatorType
	 *            the type of cell comparison
	 * @param direction
	 *            the direction
	 * 
	 * @return this builder
	 */
	public ComparatorBuilder sort(final String columnId,
			final CellComparatorType cellComparatorType,
			final SortDirectionEnum direction) {
		Comparator<TableCell> cellComparator;
		switch (cellComparatorType) {
		case LEXICOGRAPHICAL:
			cellComparator = new LexicographicalCellComparator(direction);
			break;
		case MIXED_STRING:
			cellComparator = new MixedStringCellComparator(direction);
			break;
		case NUMERIC:
			cellComparator = new NumericCellComparator(direction);
			break;
		default:
			throw new IllegalArgumentException(cellComparatorType.toString());
		}
		try {
			final int index = Integer.parseInt(columnId);
			tableRowGroupComparator.addCriterion(index, cellComparator);
		} catch (final NumberFormatException e) {
			tableRowGroupComparator.addCriterion(columnId, cellComparator);
		}
		return this;
	}

	/**
	 * @param columnId
	 *            the column id
	 * @param cellComparator
	 *            the cell comparator
	 * 
	 * @return this builder
	 */
	public ComparatorBuilder sort(final String columnId,
			final Comparator<TableCell> cellComparator) {
		tableRowGroupComparator.addCriterion(columnId, cellComparator);
		return this;
	}

}
