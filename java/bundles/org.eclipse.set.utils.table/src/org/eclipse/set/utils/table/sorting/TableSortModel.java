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

import org.eclipse.nebula.widgets.nattable.sort.ISortModel;
import org.eclipse.nebula.widgets.nattable.sort.SortDirectionEnum;
import org.eclipse.set.utils.table.TableDataProvider;

/**
 * Sort table by Column
 * 
 * @author Truong
 *
 */
public class TableSortModel implements ISortModel {
	protected TableDataProvider dataProvider;
	protected SortDirectionEnum[] sortDirections;
	protected boolean[] sorted;
	protected SortDirectionEnum currentSortDirection = SortDirectionEnum.ASC;
	protected int currentSortColumn = -1;

	/**
	 * @param dataProvider
	 *            table data provider
	 */
	public TableSortModel(final TableDataProvider dataProvider) {
		this.dataProvider = dataProvider;
		final int columnCount = dataProvider.getColumnCount();
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

		dataProvider.sort(columnIndex, getColumnComparator(columnIndex));
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
	public Comparator<String> getColumnComparator(final int columnIndex) {
		return (text1, text2) -> {
			// Attempt to sort as integers first
			try {
				final int number1 = Integer.parseInt(text1);
				final int number2 = Integer.parseInt(text2);
				if (currentSortDirection == SortDirectionEnum.ASC) {
					return Integer.compare(number1, number2);
				}
				return Integer.compare(number2, number1);
			} catch (final Exception ex) {
				if (currentSortDirection == SortDirectionEnum.ASC) {
					return text1.compareTo(text2);
				}
				return text2.compareTo(text1);
			}
		};
	}

}
