/**
 * Copyright (c) 2017 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.table.abstracttableview;

import java.util.Collection;
import java.util.Set;

import org.eclipse.nebula.widgets.nattable.group.ColumnGroupGroupHeaderLayer;
import org.eclipse.nebula.widgets.nattable.group.ColumnGroupHeaderLayer;
import org.eclipse.set.basis.tables.Tables;
import org.eclipse.set.model.tablemodel.ColumnDescriptor;
import org.eclipse.set.model.tablemodel.extensions.ColumnDescriptorExtensions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Helper class for handling tree like column headers in a nat table.
 * 
 * @author rumpf
 *
 */
public final class NatTableColumnGroupHelper {

	private static final Logger logger = LoggerFactory
			.getLogger(NatTableColumnGroupHelper.class);

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
		final Collection<ColumnDescriptor> groups4 = ColumnDescriptorExtensions
				.getGroup4(header);
		for (final ColumnDescriptor group4 : groups4) {
			final int[] indices = ColumnDescriptorExtensions
					.getColumnIndices(group4);
			final String group4Label = group4.getLabel();
			logger.info("Create group 4 {} with indices {}", //$NON-NLS-1$ log
					// message
					group4Label, indices);
			columnGroup4HeaderLayer.addColumnsIndexesToGroup(group4Label,
					indices);
		}
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
		final Collection<ColumnDescriptor> groupGroupGroups = ColumnDescriptorExtensions
				.getGroupGroupGroups(header);
		for (final ColumnDescriptor groupGroupGroup : groupGroupGroups) {
			final int[] indices = ColumnDescriptorExtensions
					.getColumnIndices(groupGroupGroup);
			final String groupGroupGroupLabel = groupGroupGroup.getLabel();
			logger.info("Create group group group {} with indices {}", //$NON-NLS-1$ log
					// message
					groupGroupGroupLabel, indices);
			columnGroupGroupGroupHeaderLayer
					.addColumnsIndexesToGroup(groupGroupGroupLabel, indices);
		}
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
		final Collection<ColumnDescriptor> groupGroups = ColumnDescriptorExtensions
				.getGroupGroups(header);
		for (final ColumnDescriptor groupGroup : groupGroups) {
			final int[] indices = ColumnDescriptorExtensions
					.getColumnIndices(groupGroup);
			final String groupGroupLabel = groupGroup.getLabel();
			logger.info("Create group group {} with indices {}", //$NON-NLS-1$ log
					// message
					groupGroupLabel, indices);
			columnGroupGroupHeaderLayer
					.addColumnsIndexesToGroup(groupGroupLabel, indices);
		}
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
		final Set<ColumnDescriptor> groups = ColumnDescriptorExtensions
				.getGroups(header);
		for (final ColumnDescriptor group : groups) {
			final int[] indices = ColumnDescriptorExtensions
					.getColumnIndices(group);
			final String groupLabel = group.getLabel();
			logger.info("Create group {} with indices {}", //$NON-NLS-1$ log
															// message
					groupLabel, indices);
			columnGroupHeaderLayer.addColumnsIndexesToGroup(groupLabel,
					indices);
		}
	}

	private NatTableColumnGroupHelper() {
		super();
	}

}
