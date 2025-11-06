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
import java.util.function.Function;

import org.eclipse.nebula.widgets.nattable.sort.SortDirectionEnum;
import org.eclipse.set.basis.tables.Tables;
import org.eclipse.set.model.planpro.Basisobjekte.Punkt_Objekt;
import org.eclipse.set.model.planpro.Basisobjekte.Ur_Objekt;
import org.eclipse.set.model.tablemodel.RowGroup;
import org.eclipse.set.model.tablemodel.TableCell;
import org.eclipse.set.model.tablemodel.TableRow;

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
		// the sorting shouldn't execute, when it must wait the find geometry
		// service
		if (group1.getRows().isEmpty()) {
			if (group2.getRows().isEmpty()) {
				return 0;
			}
			return -1;
		}
		if (group2.getRows().isEmpty()) {
			return 1;
		}
		return compare(group1.getRows().get(0), group2.getRows().get(0));
	}

	/**
	 * @param row1
	 *            the {@link TableRow}
	 * @param row2
	 *            the {@link TableRow}
	 * @return the compare result
	 */
	public int compare(final TableRow row1, final TableRow row2) {
		for (final Comparator<TableRow> criterion : criteria) {
			final int result = criterion.compare(row1, row2);
			if (result != 0) {
				return result;
			}
		}
		return 0;
	}

	/**
	 * @param getPunktObjectFunc
	 *            the function to get {@link Punkt_Objekt}
	 * @param direction
	 *            the sort direction
	 */
	public void addRouteAndKmCriterion(
			final Function<Ur_Objekt, Punkt_Objekt> getPunktObjectFunc,
			final SortDirectionEnum direction) {
		criteria.add(
				new CompareRouteAndKmCriterion(getPunktObjectFunc, direction));
	}

	/**
	 * @param getPunktObjectFunc
	 *            the function to get {@link Punkt_Objekt}
	 */
	public void addRouteAndKmCriterion(
			final Function<Ur_Objekt, Punkt_Objekt> getPunktObjectFunc) {
		criteria.add(new CompareRouteAndKmCriterion(getPunktObjectFunc));
	}
}
