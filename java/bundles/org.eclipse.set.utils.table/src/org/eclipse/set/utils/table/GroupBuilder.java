/**
 * Copyright (c) 2017 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.utils.table;

import org.eclipse.set.model.tablemodel.ColumnDescriptor;

/**
 * convenience class for building groups.
 * 
 * @author rumpf
 *
 */
public final class GroupBuilder {

	private final ColumnDescriptorModelBuilder builder;
	private final Float defaultColumnWidth;
	private final ColumnDescriptor groupRoot;

	/**
	 * 
	 * @param builder
	 *            the regular builder
	 * @param parent
	 *            the parent
	 */
	public GroupBuilder(final ColumnDescriptorModelBuilder builder,
			final ColumnDescriptor parent) {
		this.builder = builder;
		this.groupRoot = parent;
		this.defaultColumnWidth = null;
	}

	/**
	 * creates a new group builder with default column width
	 * 
	 * @param builder
	 *            the regular builder
	 * @param parent
	 *            the parent
	 * @param defaultColumnWidth
	 *            the default width for all childs
	 */
	public GroupBuilder(final ColumnDescriptorModelBuilder builder,
			final ColumnDescriptor parent, final float defaultColumnWidth) {
		this.builder = builder;
		this.groupRoot = parent;
		this.defaultColumnWidth = Float.valueOf(defaultColumnWidth);
	}

	/**
	 * adds a heading
	 * 
	 * @param childDescriptor
	 *            child node
	 * @return the child
	 */
	public ColumnAccessor add(final ColumnDescriptor childDescriptor) {
		return handleDefaultColumnWidth(
				builder.addColumn(groupRoot, childDescriptor));
	}

	/**
	 * adds a list of column descriptors.
	 * 
	 * @param childDescriptorList
	 *            the list
	 * 
	 */
	public void add(final ColumnDescriptor... childDescriptorList) {
		for (final ColumnDescriptor childDescriptor : childDescriptorList) {
			handleDefaultColumnWidth(
					builder.addColumn(groupRoot, childDescriptor));
		}
	}

	/**
	 * adds a heading
	 * 
	 * @param childDescriptor
	 *            the child
	 * @param unit
	 *            the unit
	 * @return accessor to the child
	 */
	public ColumnAccessor add(final ColumnDescriptor childDescriptor,
			final String unit) {
		return handleDefaultColumnWidth(
				builder.addColumnWithUnit(groupRoot, childDescriptor, unit));
	}

	/**
	 * adds a list of column descriptors with a common width.
	 * 
	 * @param width
	 *            width to be applied to all columns
	 * @param childDescriptorList
	 *            the list
	 * 
	 */
	public void add(final float width,
			final ColumnDescriptor... childDescriptorList) {
		for (final ColumnDescriptor childDescriptor : childDescriptorList) {
			handleDefaultColumnWidth(
					builder.addColumn(groupRoot, childDescriptor)).width(width);
		}
	}

	/**
	 * adds a new group.
	 * 
	 * @param label
	 *            the label
	 * @return the column descriptor
	 */
	public GroupBuilder addGroup(final String label) {
		return handleDefaultColumnWidth(builder.addGroup(groupRoot, label));
	}

	/**
	 * adds a new group.
	 * 
	 * @param label
	 *            the label
	 * @param defaultWidth
	 *            default width for all columns of the group
	 * @return the column descriptor
	 */
	public GroupBuilder addGroup(final String label, final float defaultWidth) {
		return handleDefaultColumnWidth(
				builder.addGroup(groupRoot, label, defaultWidth));
	}

	/**
	 * @return the group's root column descriptor.
	 */
	public ColumnDescriptor getGroupRoot() {
		return groupRoot;
	}

	/**
	 * sets the height of the group's root.
	 * 
	 * @param height
	 *            the height
	 * @return this
	 */
	public GroupBuilder height(final float height) {
		groupRoot.setHeight(height);
		return this;
	}

	/**
	 * sets the width of the group's root.
	 * 
	 * @param width
	 *            the width
	 * @return this
	 */
	public GroupBuilder width(final float width) {
		groupRoot.setWidth(Float.valueOf(width));
		return this;
	}

	private ColumnAccessor handleDefaultColumnWidth(
			final ColumnAccessor column) {
		if (defaultColumnWidth != null) {
			return column.width(defaultColumnWidth.floatValue());
		}
		return column;
	}

	private GroupBuilder handleDefaultColumnWidth(final GroupBuilder group) {
		if (defaultColumnWidth != null) {
			return group.width(defaultColumnWidth.floatValue());
		}
		return group;
	}
}