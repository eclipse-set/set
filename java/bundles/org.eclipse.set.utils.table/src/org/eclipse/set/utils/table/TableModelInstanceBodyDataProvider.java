/**
 * Copyright (c) 2017 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.utils.table;

import static org.eclipse.set.model.tablemodel.extensions.CellContentExtensions.HOURGLASS_ICON;
import static org.eclipse.set.model.tablemodel.extensions.CellContentExtensions.getStringValueIterable;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import org.eclipse.nebula.widgets.nattable.data.ISpanningDataProvider;
import org.eclipse.nebula.widgets.nattable.layer.cell.DataCell;
import org.eclipse.set.basis.constants.ContainerType;
import org.eclipse.set.basis.constants.TableType;
import org.eclipse.set.model.tablemodel.CellContent;
import org.eclipse.set.model.tablemodel.CompareCellContent;
import org.eclipse.set.model.tablemodel.StringCellContent;
import org.eclipse.set.model.tablemodel.TableCell;
import org.eclipse.set.model.tablemodel.TableRow;
import org.eclipse.set.model.tablemodel.TablemodelFactory;
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

	private final List<TableRow> instances;
	private final int propertyCount;
	private final TableSpanUtils spanUtils;

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
		this.spanUtils = new TableSpanUtils(instances);
	}

	@Override
	public int getColumnCount() {
		return propertyCount;
	}

	@Override
	public Object getDataValue(final int columnIndex, final int rowIndex) {
		final String value = TableRowExtensions
				.getRichTextValue(instances.get(rowIndex), columnIndex);
		if (value == null) {
			logger.debug("column={} row={} is empty", //$NON-NLS-1$
					Integer.valueOf(columnIndex), Integer.valueOf(rowIndex));
			return NULL_VALUE;
		}
		return value;
	}

	@Override
	public int getRowCount() {
		return instances.size();
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
	 * Update table value
	 * 
	 * @param tableType
	 *            the table type
	 * @param properties
	 *            update value event
	 */
	public void updateContent(final TableType tableType,
			final Pt1TableChangeProperties properties) {
		if (tableType != TableType.DIFF) {
			if (tableType.getContainerForTable() == properties
					.getContainerType()) {
				final Optional<TableRow> first = instances.stream()
						.filter(e -> e.equals(properties.getRow())).findFirst();
				if (first.isEmpty()) {
					return;
				}
				TableRowExtensions.set(first.get(),
						properties.getChangeDataColumn(),
						properties.getNewValues(), properties.getSeparator());
			}
			return;
		}

		// Find relevant row
		final List<TableRow> filterRows = instances.stream()
				.filter(e -> TableRowExtensions.getLeadingObjectGuid(e)
						.equals(TableRowExtensions
								.getLeadingObjectGuid(properties.getRow())))
				.filter(e -> {
					final Optional<String> cellContent = Optional.ofNullable(
							TableRowExtensions.getPlainStringValue(e,
									properties.getChangeDataColumn()));
					return !cellContent.isEmpty()
							&& !cellContent.get().isBlank()
							&& !cellContent.get().isEmpty();
				}).toList();
		if (filterRows.isEmpty()) {
			return;
		}
		filterRows.forEach(row -> {
			final TableCell cell = TableRowExtensions.getCell(row,
					properties.getChangeDataColumn());
			final CellContent newContent = getNewContent(cell.getContent(),
					properties);
			cell.setContent(newContent);
		});
	}

	private static CellContent getNewContent(final CellContent oldContent,
			final Pt1TableChangeProperties properties) {
		if (oldContent instanceof final StringCellContent stringCOntent) {
			return getNewContent(stringCOntent, properties);
		} else if (oldContent instanceof final CompareCellContent compareContent) {
			return getNewContent(compareContent, properties);
		} else {
			throw new UnsupportedOperationException();
		}
	}

	private static CellContent getNewContent(final StringCellContent oldContent,
			final Pt1TableChangeProperties properties) {
		final List<String> currentValues = StreamSupport
				.stream(getStringValueIterable(oldContent).spliterator(), false)
				.toList();
		if (currentValues.size() == 1
				&& currentValues.getFirst().equals(HOURGLASS_ICON)) {
			final StringCellContent newContent = TablemodelFactory.eINSTANCE
					.createStringCellContent();
			newContent.getValue().addAll(properties.getNewValues());
			newContent.setSeparator(properties.getSeparator());
			return newContent;
		}

		if (!equalsValues(currentValues, properties.getNewValues())) {
			if (properties.getContainerType() == ContainerType.INITIAL) {
				return createCompareCellContent(properties.getNewValues(),
						currentValues, oldContent.getSeparator());
			}
			return createCompareCellContent(currentValues,
					properties.getNewValues(), oldContent.getSeparator());
		}

		return null;
	}

	private static CellContent getNewContent(
			final CompareCellContent oldContent,
			final Pt1TableChangeProperties properties) {
		final ContainerType containerType = properties.getContainerType();
		switch (containerType) {
		case FINAL:
			if (!equalsValues(oldContent.getNewValue(),
					properties.getNewValues())) {
				return createCompareCellContent(oldContent.getOldValue(),
						properties.getNewValues(), oldContent.getSeparator());
			}
			break;
		case INITIAL:
			if (!equalsValues(oldContent.getOldValue(),
					properties.getNewValues())) {
				return createCompareCellContent(properties.getNewValues(),
						oldContent.getNewValue(), oldContent.getSeparator());
			}
			break;
		default:
			throw new IllegalArgumentException(
					"SingelState can't have compare cell content"); //$NON-NLS-1$
		}
		return null;
	}

	private static CompareCellContent createCompareCellContent(
			final List<String> oldValues, final List<String> newValues,
			final String separator) {
		final CompareCellContent compareContent = TablemodelFactory.eINSTANCE
				.createCompareCellContent();
		compareContent.getOldValue().addAll(oldValues);
		compareContent.getNewValue().addAll(newValues);
		compareContent.setSeparator(separator);
		return compareContent;
	}

	private static boolean equalsValues(final List<String> oldValues,
			final List<String> newValues) {
		return oldValues.size() == newValues.size()
				&& oldValues.stream().allMatch(newValues::contains);
	}
}
