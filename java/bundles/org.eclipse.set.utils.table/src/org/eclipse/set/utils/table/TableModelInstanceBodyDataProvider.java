/**
 * Copyright (c) 2017 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.utils.table;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import org.eclipse.nebula.widgets.nattable.data.ISpanningDataProvider;
import org.eclipse.nebula.widgets.nattable.layer.cell.DataCell;
import org.eclipse.set.model.tablemodel.CellContent;
import org.eclipse.set.model.tablemodel.TableRow;
import org.eclipse.set.model.tablemodel.extensions.CellContentExtensions;
import org.eclipse.set.model.tablemodel.extensions.TableRowExtensions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Provides data from the table model.
 * 
 * @author Bleidiessel
 */
public class TableModelInstanceBodyDataProvider
		implements ISpanningDataProvider {

	private static final Logger logger = LoggerFactory
			.getLogger(TableModelInstanceBodyDataProvider.class);

	private static final String NULL_VALUE = ""; //$NON-NLS-1$
	private static final String EXCULDE_FILTER_SIGN = "-"; //$NON-NLS-1$

	private final List<TableRow> instances;
	private List<TableRow> filteredInstances;
	private final int propertyCount;
	private TableSpanUtils spanUtils;
	private Map<Integer, Object> filters = new HashMap<>();

	/**
	 * @param propertyCount
	 *            the number of columns
	 * @param instances
	 *            the table model
	 */
	public TableModelInstanceBodyDataProvider(final int propertyCount,
			final List<TableRow> instances) {
		this.instances = instances;
		this.propertyCount = propertyCount;
		this.refresh();
	}

	@Override
	public int getColumnCount() {
		return propertyCount;
	}

	@Override
	public Object getDataValue(final int columnIndex, final int rowIndex) {
		final String value = TableRowExtensions
				.getRichTextValue(filteredInstances.get(rowIndex), columnIndex);
		if (value == null) {
			logger.debug("column={} row={} is empty", //$NON-NLS-1$
					Integer.valueOf(columnIndex), Integer.valueOf(rowIndex));
			return NULL_VALUE;
		}
		return value;
	}

	@Override
	public int getRowCount() {
		return filteredInstances.size();
	}

	@Override
	public void setDataValue(final int columnIndex, final int rowIndex,
			final Object newValue) {
		// does nothing atm
	}

	@Override
	public DataCell getCellByPosition(final int column, final int row) {
		if (!spanUtils.isMergeAllowed(column, row)) {
			return new DataCell(column, row, 1, 1);
		}

		// Calculate the span
		final int spanUp = spanUtils.getRowSpanUp(column, row);
		final int spanDown = spanUtils.getRowSpanDown(column, row);

		final int startRow = row - spanUp;
		final int spanSize = spanUp + spanDown + 1;

		return new DataCell(column, startRow, 1, spanSize);
	}

	/**
	 * Applies a set of filters to the table
	 * 
	 * @param filterIndexToObjectMap
	 *            A map<columnIndex, filterValue> of filters to apply
	 */
	public void applyFilter(final Map<Integer, Object> filterIndexToObjectMap) {
		this.filters = filterIndexToObjectMap;
		this.refresh();
	}

	/**
	 * Refreshes the internal state e.g. when the instances changed.
	 */
	public void refresh() {
		this.filteredInstances = this.instances.stream()
				.filter(this::filterMatch)
				.toList();
		this.spanUtils = new TableSpanUtils(this.filteredInstances);
	}

	private boolean filterMatch(final TableRow row) {
		final List<CellContent> contents = row.getCells()
				.stream()
				.map(cell -> cell.getContent())
				.toList();
		for (int i = 0; i < this.getColumnCount(); i++) {
			if (filters.containsKey(Integer.valueOf(i))) {
				final String content = CellContentExtensions
						.getPlainStringValue(contents.get(i))
						.toLowerCase();
				String filterValue = filters.get(Integer.valueOf(i))
						.toString()
						.toLowerCase();
				final boolean isExcludeFilter = filterValue.substring(0, 1)
						.equals(EXCULDE_FILTER_SIGN);
				filterValue = isExcludeFilter ? filterValue.substring(1)
						: filterValue;

				// Equivalence logic
				if (isExcludeFilter == content.contains(filterValue)) {
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * Update table value
	 * 
	 * @param updateFunc
	 *            the update function
	 */
	public void updateContent(final Consumer<List<TableRow>> updateFunc) {
		updateFunc.accept(instances);
		this.refresh();
	}

}
