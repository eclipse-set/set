/**
 * Copyright (c) 2022 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */

package org.eclipse.set.utils.table.sorting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import org.eclipse.emf.common.util.ECollections;
import org.eclipse.emf.common.util.EList;
import org.eclipse.nebula.widgets.nattable.sort.ISortModel;
import org.eclipse.nebula.widgets.nattable.sort.SortDirectionEnum;
import org.eclipse.set.model.tablemodel.RowGroup;
import org.eclipse.set.model.tablemodel.Table;
import org.eclipse.set.utils.table.sorting.ComparatorBuilder.CellComparatorType;

/**
 * Sort table by Column
 * 
 * @author Truong
 *
 */
public class TableSortModel implements ISortModel {

	private final EList<RowGroup> sortData;
	protected Table table;
	protected SortDirectionEnum[] sortDirections;
	protected boolean[] sorted;
	protected SortDirectionEnum currentSortDirection = SortDirectionEnum.ASC;
	protected int currentSortColumn = -1;

	/**
	 * @param sortData
	 *            table Data
	 * @param columnCount
	 *            column number of the tabele
	 */
	public TableSortModel(final EList<RowGroup> sortData,
			final int columnCount) {
		this.sortData = sortData;
		this.sortDirections = new SortDirectionEnum[columnCount];
		Arrays.fill(this.sortDirections, SortDirectionEnum.NONE);
		this.sorted = new boolean[columnCount];
		Arrays.fill(this.sorted, false);
	}

	@Override
	public List<Integer> getSortedColumnIndexes() {
		final List<Integer> indexes = new ArrayList<>();
		if (currentSortColumn > -1) {
			indexes.add(Integer.valueOf(currentSortColumn));
		}
		return indexes;
	}

	@Override
	public boolean isColumnIndexSorted(final int columnIndex) {
		return sorted[columnIndex];
	}

	@Override
	public SortDirectionEnum getSortDirection(final int columnIndex) {
		return sortDirections[columnIndex];
	}

	@Override
	public int getSortOrder(final int columnIndex) {
		return 0;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List<Comparator> getComparatorsForColumnIndex(
			final int columnIndex) {
		return null;
	}

	@Override
	public void sort(final int columnIndex,
			final SortDirectionEnum sortDirection, final boolean accumulate) {
		if (!isColumnIndexSorted(columnIndex)) {
			clear();
		}
		currentSortDirection = sortDirection.equals(SortDirectionEnum.NONE)
				? SortDirectionEnum.ASC
				: sortDirection;

		ECollections.sort(sortData, getColumnComparator(columnIndex));
		sortDirections[columnIndex] = currentSortDirection;
		sorted[columnIndex] = true;
		currentSortColumn = columnIndex;

	}

	@Override
	public void clear() {
		Arrays.fill(sortDirections, SortDirectionEnum.NONE);
		Arrays.fill(sorted, false);
		currentSortColumn = -1;
	}

	@Override
	public Comparator<RowGroup> getColumnComparator(final int columnIndex) {
		return TableRowGroupComparator.builder()
				.sort(String.valueOf(columnIndex),
						CellComparatorType.LEXICOGRAPHICAL,
						currentSortDirection)
				.build();
	}

}
