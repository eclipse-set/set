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
import org.eclipse.set.model.tablemodel.ColumnWidthMode;

/**
 * convenience access for column properties.
 * 
 * @author rumpf
 *
 */
public final class ColumnAccessor {

	private final ColumnDescriptor column;

	/**
	 * creates the accessor
	 * 
	 * @param column
	 *            the corresponding column descriptor
	 */
	public ColumnAccessor(final ColumnDescriptor column) {
		super();
		this.column = column;
	}

	/**
	 * @return the leading column descriptor.
	 */
	public ColumnDescriptor build() {
		return column;
	}

	/**
	 * sets the dimension of the column.
	 * 
	 * @param width
	 *            the width
	 * @param height
	 *            the height
	 * @return this
	 */
	public ColumnAccessor dimension(final float width, final float height) {
		column.setWidth(Float.valueOf(width));
		column.setHeight(height);
		return this;
	}

	/**
	 * sets the greyed out flag to <code>true</code>.
	 * 
	 * @return this
	 */
	public ColumnAccessor greyed() {
		column.setGreyed(true);
		return this;
	}

	/**
	 * sets the height of the column
	 * 
	 * @param height
	 *            the height
	 * @return this
	 */
	public ColumnAccessor height(final float height) {
		column.setHeight(height);
		return this;
	}

	/**
	 * sets the width of the column in centimeters
	 * 
	 * @param width
	 *            the width
	 * @return this
	 */
	public ColumnAccessor width(final float width) {
		column.setWidth(Float.valueOf(width));
		column.setWidthMode(ColumnWidthMode.WIDTH_CM);
		return this;
	}

	/**
	 * sets the width of the column in percent of the full table width
	 * 
	 * @param width
	 *            the width
	 * @return this
	 */
	public ColumnAccessor widthPercent(final float width) {
		column.setWidth(Float.valueOf(width));
		column.setWidthMode(ColumnWidthMode.WIDTH_PERCENT);
		return this;
	}
}
