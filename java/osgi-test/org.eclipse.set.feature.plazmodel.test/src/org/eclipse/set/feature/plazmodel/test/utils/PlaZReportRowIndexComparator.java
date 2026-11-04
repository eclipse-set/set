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
package org.eclipse.set.feature.plazmodel.test.utils;

import java.util.Comparator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.set.model.tablemodel.TableRow;
import org.eclipse.set.model.tablemodel.extensions.TableCellExtensions;

/**
 * 
 */
public class PlaZReportRowIndexComparator implements Comparator<TableRow> {
	private static final Pattern ROW_COUNT_PATTERN = Pattern
			.compile("(\\d+)(?:...)?(\\d+)?"); //$NON-NLS-1$

	private static int endCount(final String row) {
		final Matcher matcher = matchRowCountValue(row);
		final String end = matcher.group(2);
		return Integer.parseInt(end != null ? end : matcher.group(1));
	}

	private static Matcher matchRowCountValue(final String value) {
		final Matcher matcher = ROW_COUNT_PATTERN.matcher(value);
		if (matcher.matches()) {
			return matcher;
		}
		throw new IllegalArgumentException(
				"Illegal value for row count: " + value); //$NON-NLS-1$
	}

	/**
	 * @return comparator for tree table row count
	 */
	private static Comparator<String> rowCountComparator() {
		return Comparator.comparingInt(PlaZReportRowIndexComparator::startCount)
				.thenComparing(PlaZReportRowIndexComparator::endCount);
	}

	private static int startCount(final String value) {
		return Integer.parseInt(matchRowCountValue(value).group(1));
	}

	@Override
	public int compare(final TableRow first, final TableRow second) {
		final String firstIndex = TableCellExtensions
				.getPlainStringValue(first.getCells().getFirst());
		final String secondIndex = TableCellExtensions
				.getPlainStringValue(second.getCells().getFirst());
		return rowCountComparator().compare(firstIndex, secondIndex);
	}
}
