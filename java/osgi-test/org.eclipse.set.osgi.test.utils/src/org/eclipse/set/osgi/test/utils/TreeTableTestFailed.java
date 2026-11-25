/**
 * Copyright (c) 2026 DB InfraGO AG and others
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 */
package org.eclipse.set.osgi.test.utils;

import java.io.File;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.eclipse.set.model.tablemodel.Table;
import org.eclipse.set.model.tablemodel.TableRow;
import org.eclipse.set.model.tablemodel.extensions.TableCellExtensions;
import org.eclipse.set.model.tablemodel.extensions.TableExtensions;
import org.eclipse.set.utils.table.export.ExportToCSV;

/**
 * 
 */
public class TreeTableTestFailed extends TestFailHandle {
	private static final Pattern ROW_COUNT_PATTERN = Pattern
			.compile("(\\d+)(?:\\.\\.(\\d+))?"); //$NON-NLS-1$

	/**
	 * @return
	 */
	public static Comparator<TableRow> treeRowComparator() {
		return (first, second) -> {
			final String firstRowIndex = TableCellExtensions
					.getPlainStringValue(first.getCells().getFirst());
			final String secondRowIndex = TableCellExtensions
					.getPlainStringValue(second.getCells().getFirst());
			return treeRowIndexComparator().compare(firstRowIndex,
					secondRowIndex);
		};
	}

	private static int endCount(final String row) {
		final Matcher matcher = matchRowCountValue(row);
		final String end = matcher.group(2);
		return Integer.parseInt(end != null ? end : "-1");
	}

	private static Matcher matchRowCountValue(final String value) {
		final Matcher matcher = ROW_COUNT_PATTERN.matcher(value);
		if (matcher.matches()) {
			return matcher;
		}
		throw new IllegalArgumentException(
				"Illegal value for row count: " + value); //$NON-NLS-1$
	}

	private static int startCount(final String value) {
		return Integer.parseInt(matchRowCountValue(value).group(1));
	}

	private static Comparator<String> treeRowIndexComparator() {
		return (first, second) -> {
			final int firstCount = startCount(first);
			final int secondCount = startCount(second);
			final int compare = Integer.compare(firstCount, secondCount);
			if (compare != 0) {
				return compare;
			}
			final int firstEnd = endCount(first);
			final int secondEnd = endCount(second);

			if (firstEnd == -1 && secondEnd == -1) {
				throw new IllegalArgumentException("Row index can't be same");
			}
			if (firstEnd == -1) {
				return 1;
			}
			if (secondEnd == -1) {
				return -1;
			}
			throw new IllegalArgumentException("Row index can't be same");
		};
	}

	@Override
	protected void exportCurrentCSV(final AbstractOsgiTableTest tableTest) {
		final File file = getExportFile(tableTest, CURRENT_CSV_EXTENSIONS);
		if (!file.getParentFile().exists()) {
			file.getParentFile().mkdirs();
		}
		final Table testTable = tableTest.getTestTable();
		final ExportToCSV<TableRow> exportToCSV = new ExportToCSV<>(
				tableTest.getTableCSVHeader(testTable));
		final List<TableRow> rows = TableExtensions.getTableRows(testTable);
		rows.sort(treeRowComparator());
		exportToCSV.exportToCSV(Optional.of(file.toPath()), rows,
				row -> row.getCells()
						.stream()
						.map(TableCellExtensions::getPlainStringValue)
						.collect(Collectors
								.joining(AbstractOsgiTableTest.CSV_DELIMITER))
						+ System.lineSeparator());
	}
}
