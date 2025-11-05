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

import org.eclipse.set.basis.tables.Tables;
import org.eclipse.set.model.planpro.Basisobjekte.Ur_Objekt;
import org.eclipse.set.model.tablemodel.RowGroup;
import org.eclipse.set.model.tablemodel.TableCell;
import org.eclipse.set.model.tablemodel.TableRow;
import org.eclipse.set.model.tablemodel.extensions.TableRowExtensions;

import com.google.common.collect.Lists;

/**
 * Compare {@link RowGroup}s.
 * 
 * @author Schaefer
 */
public final class TableRowGroupComparator implements Comparator<RowGroup> {

	/**
	 * @return create a builder
	 */
	public static ComparatorBuilder builder() {
		return new ComparatorBuilder(new TableRowGroupComparator());
	}

	private final List<Comparator<TableRow>> criteria = Lists.newLinkedList();

	private TableRowGroupComparator() {
	}

	/**
	 * Add a sort criterion for comparing types of leading objects.
	 * 
	 * @param list
	 *            the types implying the sort order
	 */
	public void addCriterion(final List<Class<?>> list) {
		criteria.add(new CompareTypesCriterion(list));
	}

	/**
	 * Add a sort criterion for comparing cells of a given column.
	 * 
	 * @param columnId
	 *            the column id
	 * @param cellComparator
	 *            the cell comparator
	 */
	public void addCriterion(final String columnId,
			final Comparator<TableCell> cellComparator) {
		criteria.add(new CompareColumnCriterion(Tables.getColumnIndex(columnId),
				cellComparator));
	}

	/**
	 * Add a sort criterion for comparing cells of a given column index.
	 * 
	 * @param columnIndex
	 *            the column index
	 * @param cellComparator
	 *            the cell comparator
	 */
	public void addCriterion(final int columnIndex,
			final Comparator<TableCell> cellComparator) {
		criteria.add(new CompareColumnCriterion(columnIndex, cellComparator));
	}

	@Override
	public int compare(final RowGroup group1, final RowGroup group2) {
		if (group1.getRows().isEmpty()) {
			if (group2.getRows().isEmpty()) {
				return 0;
			}
			return -1;
		}
		if (group2.getRows().isEmpty()) {
			return 1;
		}
		final TableRow row1 = group1.getRows().get(0);
		final TableRow row2 = group2.getRows().get(0);
		for (final Comparator<TableRow> criterion : criteria) {
			final int result = criterion.compare(row1, row2);
			if (result != 0) {
				return result;
			}
		}
		return 0;
	}

	/**
	 * @param <T>
	 * @param leadingObjectComparator
	 */
	@SuppressWarnings("unchecked")
	public <T extends Ur_Objekt> void addCriterion(
			final Comparator<T> leadingObjectComparator) {
		criteria.add((row1, row2) -> {
			final Ur_Objekt firstLeadingObject = TableRowExtensions
					.getLeadingObject(row1);
			final Ur_Objekt secondLeadingObject = TableRowExtensions
					.getLeadingObject(row2);
			if (firstLeadingObject == null) {
				return secondLeadingObject == null ? 0 : -1;
			}

			if (secondLeadingObject == null) {
				return 1;
			}

			final T first = (T) firstLeadingObject;
			final T second = (T) secondLeadingObject;
			return leadingObjectComparator.compare(first, second);
		});

	}
}
