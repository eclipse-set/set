/**
 * Copyright (c) 2016 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.nattable.utils;

import org.eclipse.set.model.tablemodel.ColumnDescriptor;
import org.eclipse.set.model.tablemodel.TablemodelFactory;

/**
 * abstract super class for all columns classes.
 * 
 * @author rumpf
 *
 */
public abstract class AbstractColumns {

	/**
	 * creates a new column descriptor.
	 * 
	 * @param label
	 *            the label
	 * @return the descriptor
	 */
	public static ColumnDescriptor createNew(final String label) {
		final ColumnDescriptor columnDescriptor = TablemodelFactory.eINSTANCE
				.createColumnDescriptor();
		columnDescriptor.setLabel(label);
		return columnDescriptor;
	}

	/**
	 * creates a new column descriptor with a width.
	 * 
	 * @param label
	 *            the label
	 * @param width
	 *            the width
	 * @return the descriptor
	 */
	public static ColumnDescriptor createNew(final String label,
			final float width) {
		final ColumnDescriptor columnDescriptor = TablemodelFactory.eINSTANCE
				.createColumnDescriptor();
		columnDescriptor.setLabel(label);
		columnDescriptor.setWidth(Float.valueOf(width));
		return columnDescriptor;
	}
}