/**
 * Copyright (c) 2016 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.utils.table;

import org.eclipse.set.model.tablemodel.ColumnDescriptor;
import org.eclipse.set.model.tablemodel.Table;
import org.eclipse.set.model.tablemodel.TablemodelFactory;

/**
 * Helper class for building the header part of the table model based on
 * {@link ColumnDescriptor} instances.
 * 
 * @author Schaefer
 */
public final class ColumnDescriptorModelBuilder {

	private final Table table;

	/**
	 * Creates a new builder.
	 *
	 * @param table
	 *            the table
	 */
	public ColumnDescriptorModelBuilder(final Table table) {
		this.table = table;
	}

	/**
	 * adds a heading
	 * 
	 * @param parentDescriptor
	 *            parent node
	 * @param childDescriptor
	 *            child node
	 * @return the child
	 */
	public ColumnAccessor addColumn(final ColumnDescriptor parentDescriptor,
			final ColumnDescriptor childDescriptor) {
		table.getColumndescriptors().add(childDescriptor);
		parentDescriptor.getChildren().add(childDescriptor);
		return new ColumnAccessor(childDescriptor);
	}

	/**
	 * adds a heading
	 * 
	 * @param parentDescriptor
	 *            the parent
	 * @param childDescriptor
	 *            the child
	 * @param unit
	 *            the unit
	 * @return the child
	 */
	public ColumnAccessor addColumnWithUnit(
			final ColumnDescriptor parentDescriptor,
			final ColumnDescriptor childDescriptor, final String unit) {
		final ColumnDescriptor unitDescriptor = TablemodelFactory.eINSTANCE
				.createColumnDescriptor();
		unitDescriptor.setLabel(unit);
		unitDescriptor.setUnit(true);
		unitDescriptor.setColumnPosition(childDescriptor.getColumnPosition());
		table.getColumndescriptors().add(childDescriptor);
		table.getColumndescriptors().add(unitDescriptor);
		parentDescriptor.getChildren().add(childDescriptor);
		childDescriptor.getChildren().add(unitDescriptor);
		return new ColumnAccessor(childDescriptor);
	}

	/**
	 * creates a new column group
	 * 
	 * @param parentDescriptor
	 *            the parent
	 * @param label
	 *            the label
	 * @return the group builder
	 */
	public GroupBuilder addGroup(final ColumnDescriptor parentDescriptor,
			final String label) {
		final ColumnDescriptor childDescriptor = TablemodelFactory.eINSTANCE
				.createColumnDescriptor();
		childDescriptor.setLabel(label);
		table.getColumndescriptors().add(childDescriptor);
		parentDescriptor.getChildren().add(childDescriptor);
		return new GroupBuilder(this, childDescriptor);
	}

	/**
	 * creates a new column group with a default column width for all child
	 * columns.
	 * 
	 * @param parentDescriptor
	 *            the parent
	 * @param label
	 *            the label
	 * @param defaultWidth
	 *            the default width
	 * @return the group builder
	 */
	public GroupBuilder addGroup(final ColumnDescriptor parentDescriptor,
			final String label, final float defaultWidth) {
		final ColumnDescriptor childDescriptor = TablemodelFactory.eINSTANCE
				.createColumnDescriptor();
		childDescriptor.setLabel(label);
		table.getColumndescriptors().add(childDescriptor);
		parentDescriptor.getChildren().add(childDescriptor);
		return new GroupBuilder(this, childDescriptor, defaultWidth);
	}

	/**
	 * adds a child descriptor to a parent descriptor.
	 * 
	 * @param parentDescriptor
	 *            the parent
	 * @param childDescriptor
	 *            the child
	 * @return the child
	 */
	public ColumnDescriptor addHeading(final ColumnDescriptor parentDescriptor,
			final ColumnDescriptor childDescriptor) {
		table.getColumndescriptors().add(childDescriptor);
		parentDescriptor.getChildren().add(childDescriptor);
		return childDescriptor;
	}

	/**
	 * adds a heading with width.
	 * 
	 * @param parentDescriptor
	 *            the parent
	 * @param label
	 *            the label
	 * @param width
	 *            the width in pixel
	 * 
	 * @return the column descriptor
	 */
	public ColumnDescriptor addHeading(final ColumnDescriptor parentDescriptor,
			final String label, final float width) {
		final ColumnDescriptor childDescriptor = TablemodelFactory.eINSTANCE
				.createColumnDescriptor();
		childDescriptor.setLabel(label);
		childDescriptor.setWidth(Float.valueOf(width));
		table.getColumndescriptors().add(childDescriptor);
		parentDescriptor.getChildren().add(childDescriptor);
		return childDescriptor;
	}

	/**
	 * adds a heading with width.
	 * 
	 * @param parentDescriptor
	 *            the parent
	 * @param label
	 *            the label
	 * @param width
	 *            the width in pixel
	 * @param height
	 *            the height in pixel
	 * 
	 * @return the column descriptor
	 */
	public ColumnDescriptor addHeading(final ColumnDescriptor parentDescriptor,
			final String label, final float width, final float height) {
		final ColumnDescriptor childDescriptor = TablemodelFactory.eINSTANCE
				.createColumnDescriptor();
		childDescriptor.setLabel(label);
		childDescriptor.setWidth(Float.valueOf(width));
		childDescriptor.setHeight(height);
		table.getColumndescriptors().add(childDescriptor);
		parentDescriptor.getChildren().add(childDescriptor);
		return childDescriptor;
	}

	/**
	 * adds a unit heading.
	 * 
	 * @param parentDescriptor
	 *            the parent
	 * @param label
	 *            the label
	 * @param unit
	 *            the unit identifier
	 * 
	 * @return the column descriptor
	 */
	public ColumnDescriptor addHeading(final ColumnDescriptor parentDescriptor,
			final String label, final String unit) {
		final ColumnDescriptor childDescriptor = TablemodelFactory.eINSTANCE
				.createColumnDescriptor();
		childDescriptor.setLabel(label);
		final ColumnDescriptor unitDescriptor = TablemodelFactory.eINSTANCE
				.createColumnDescriptor();
		unitDescriptor.setLabel(unit);
		unitDescriptor.setUnit(true);
		table.getColumndescriptors().add(childDescriptor);
		table.getColumndescriptors().add(unitDescriptor);
		parentDescriptor.getChildren().add(childDescriptor);
		childDescriptor.getChildren().add(unitDescriptor);
		return childDescriptor;
	}

	/**
	 * adds a unit heading with width.
	 * 
	 * @param parentDescriptor
	 *            the parent
	 * @param label
	 *            the label
	 * @param unit
	 *            the unit identifier
	 * @param width
	 *            the width in pixel
	 * 
	 * @return the column descriptor
	 */
	public ColumnDescriptor addHeading(final ColumnDescriptor parentDescriptor,
			final String label, final String unit, final float width) {
		final ColumnDescriptor childDescriptor = TablemodelFactory.eINSTANCE
				.createColumnDescriptor();
		childDescriptor.setLabel(label);
		childDescriptor.setWidth(Float.valueOf(width));
		final ColumnDescriptor unitDescriptor = TablemodelFactory.eINSTANCE
				.createColumnDescriptor();
		unitDescriptor.setLabel(unit);
		unitDescriptor.setUnit(true);
		table.getColumndescriptors().add(childDescriptor);
		table.getColumndescriptors().add(unitDescriptor);
		parentDescriptor.getChildren().add(childDescriptor);
		childDescriptor.getChildren().add(unitDescriptor);
		return childDescriptor;
	}

	/**
	 * adds a simple heading without a label
	 * 
	 * @return the group builder
	 */
	public GroupBuilder createRootColumn() {
		return createRootColumn(null);
	}

	/**
	 * adds a simple heading
	 * 
	 * @param label
	 *            the label
	 * @return the group builder
	 */
	public GroupBuilder createRootColumn(final String label) {
		final ColumnDescriptor columnDescriptor = TablemodelFactory.eINSTANCE
				.createColumnDescriptor();
		if (label != null) {
			columnDescriptor.setLabel(label);
		}
		table.getColumndescriptors().add(columnDescriptor);
		return new GroupBuilder(this, columnDescriptor);
	}

}
