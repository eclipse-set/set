/**
 * Copyright (c) 2016 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.table.abstracttableview;

import java.util.Arrays;

import com.google.common.base.Function;

import org.eclipse.set.basis.tables.Tables;
import org.eclipse.set.model.tablemodel.ColumnDescriptor;
import org.eclipse.set.model.tablemodel.extensions.ColumnDescriptorExtensions;

/**
 * Common heading utilities.
 * 
 * @author Schaefer
 */
// IMPROVE: Why are there different Headings classes with similar functionality?
public class Headings {

	private static float defaultHeight;

	/**
	 * @return the default height
	 */
	public static float getDefaultHeight() {
		return defaultHeight;
	}

	/**
	 * @param heading
	 *            the heading
	 * 
	 * @return string representation of the heading-tree
	 */
	public static String getTreeString(final ColumnDescriptor heading) {
		return getTreeString(heading, 0);
	}

	/**
	 * @param heading
	 *            the heading
	 * @param toPixel
	 *            the toPixel Function
	 * 
	 * @return test data for column width
	 */
	public static String getWidthTestCsv(final ColumnDescriptor heading,
			final Function<Float, Integer> toPixel) {
		final int columns = ColumnDescriptorExtensions
				.getColumns(heading).length;
		final StringBuilder result = new StringBuilder((columns + 1) * 12);
		result.append("Column;WidthExpected;WidthActual\n"); //$NON-NLS-1$
		for (int i = 0; i < columns; i++) {
			result.append(Integer.toString(i + 1)).append(";"). //$NON-NLS-1$
					append(toPixel.apply(ColumnDescriptorExtensions
							.getColumnWidth(heading, i)))
					.append(";0\n"); //$NON-NLS-1$
		}

		return result.toString();
	}

	/**
	 * @param defaultHeight
	 *            the default height
	 */
	public static void setDefaultHeight(final float defaultHeight) {
		Headings.defaultHeight = defaultHeight;
	}

	private static String getTreeString(final ColumnDescriptor heading,
			final int level) {
		final StringBuilder result = new StringBuilder();
		for (int i = 0; i < level; i++) {
			result.append("  "); //$NON-NLS-1$
		}
		final int columnIndex = Arrays
				.asList(ColumnDescriptorExtensions.getColumns(heading))
				.indexOf(heading);

		if (columnIndex >= 0) {
			result.append(
					String.format("Column %d (id=%s greyed=%b width=%.2f cm): ", //$NON-NLS-1$
							Integer.valueOf(columnIndex),
							Tables.getColumnIdentifier(columnIndex),
							Boolean.valueOf(heading.isGreyed()),
							ColumnDescriptorExtensions.getColumnWidth(heading,
									columnIndex)));
		}
		if (ColumnDescriptorExtensions.isGroup(heading)) {
			result.append("Group: "); //$NON-NLS-1$
		}
		if (ColumnDescriptorExtensions.isGroupGroup(heading)) {
			result.append("Group group: "); //$NON-NLS-1$
		}
		if (ColumnDescriptorExtensions.isGroupGroupGroup(heading)) {
			result.append("Group group group: "); //$NON-NLS-1$
		}
		result.append(String.format("\"%s\"", heading.getLabel())); //$NON-NLS-1$

		result.append("\n"); //$NON-NLS-1$
		for (final ColumnDescriptor child : heading.getChildren()) {
			result.append(getTreeString(child, level + 1));
		}
		return result.toString();
	}
}
