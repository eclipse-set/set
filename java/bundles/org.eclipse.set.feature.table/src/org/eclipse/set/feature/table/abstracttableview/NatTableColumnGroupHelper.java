/**
 * Copyright (c) 2017 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.table.abstracttableview;

import java.util.Set;

import org.eclipse.nebula.widgets.nattable.group.ColumnGroupGroupHeaderLayer;
import org.eclipse.nebula.widgets.nattable.group.ColumnGroupHeaderLayer;
import org.eclipse.set.basis.tables.Tables;
import org.eclipse.set.model.tablemodel.ColumnDescriptor;
import org.eclipse.set.model.tablemodel.extensions.ColumnDescriptorExtensions;

/**
 * Helper class for handling tree like column headers in a nat table.
 * 
 * @author rumpf
 *
 */
public final class NatTableColumnGroupHelper {
	private static final String ZERO_WIDTH_SPACE = "\u200B"; //$NON-NLS-1$

	/**
	 * Helper interface to wrap the method reference ::addColumnsIndexesToGroup
	 * across multiple classes
	 *
	 */
	@FunctionalInterface
	private interface HeaderLayer {
		void addColumnsIndexesToGroup(String colGroupName, int... colIndexes);
	}

	private static void addGroupsToHeader(final Set<ColumnDescriptor> groups,
			final HeaderLayer columnHeaderLayer) {
		for (final ColumnDescriptor group : groups) {
			final int[] indices = ColumnDescriptorExtensions
					.getColumnIndices(group);

			final int uniqueIndex = indices.length > 0 ? indices[0] : 0;
			final String groupLabel = group.getLabel()
					+ ZERO_WIDTH_SPACE.repeat(uniqueIndex);

			columnHeaderLayer.addColumnsIndexesToGroup(groupLabel, indices);
		}
	}

	/**
	 * adds column numbers
	 * 
	 * @param header
	 *            the header
	 * @param columnGroup4HeaderLayer
	 *            the header layer
	 */
	public static void addColumnNumbers(final ColumnDescriptor header,
			final ColumnGroup4HeaderLayer columnGroup4HeaderLayer) {
		final int noOfcolumns = ColumnDescriptorExtensions
				.getColumns(header).length;
		for (int i = 0; i < noOfcolumns; i++) {
			final String label = Tables.getColumnIdentifier(i);
			columnGroup4HeaderLayer.addColumnsIndexesToGroup(label, i);
		}
	}

	/**
	 * adds column groups (4th level)
	 * 
	 * @param header
	 *            the header
	 * @param columnGroup4HeaderLayer
	 *            the header layer
	 */
	public static void addGroup4(final ColumnDescriptor header,
			final ColumnGroup4HeaderLayer columnGroup4HeaderLayer) {
		addGroupsToHeader(ColumnDescriptorExtensions.getGroup4(header),
				columnGroup4HeaderLayer::addColumnsIndexesToGroup);
	}

	/**
	 * adds column groups (3rd level)
	 * 
	 * @param header
	 *            the header
	 * @param columnGroupGroupGroupHeaderLayer
	 *            the header layer
	 */
	public static void addGroupGroupGroups(final ColumnDescriptor header,
			final ColumnGroupGroupGroupHeaderLayer columnGroupGroupGroupHeaderLayer) {
		addGroupsToHeader(
				ColumnDescriptorExtensions.getGroupGroupGroups(header),
				columnGroupGroupGroupHeaderLayer::addColumnsIndexesToGroup);
	}

	/**
	 * adds column groups (2nd level)
	 * 
	 * @param header
	 *            the header
	 * @param columnGroupGroupHeaderLayer
	 *            the header layer
	 */
	public static void addGroupGroups(final ColumnDescriptor header,
			final ColumnGroupGroupHeaderLayer columnGroupGroupHeaderLayer) {
		addGroupsToHeader(ColumnDescriptorExtensions.getGroupGroups(header),
				columnGroupGroupHeaderLayer::addColumnsIndexesToGroup);
	}

	/**
	 * adds column groups.
	 * 
	 * @param header
	 *            the header
	 * @param columnGroupHeaderLayer
	 *            the header layer
	 */
	public static void addGroups(final ColumnDescriptor header,
			final ColumnGroupHeaderLayer columnGroupHeaderLayer) {
		addGroupsToHeader(ColumnDescriptorExtensions.getGroups(header),
				columnGroupHeaderLayer::addColumnsIndexesToGroup);
	}

	private NatTableColumnGroupHelper() {
		super();
	}

}
