/**
 * Copyright (c) 2017 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.utils.table;

import java.util.List;

import org.eclipse.nebula.widgets.nattable.data.IDataProvider;
import org.eclipse.set.model.tablemodel.TableRow;
import org.eclipse.set.model.tablemodel.extensions.TableRowExtensions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Provides data from the table model.
 * 
 * @author Bleidiessel
 */
public class TableModelInstanceBodyDataProvider implements IDataProvider {

	private static final Logger logger = LoggerFactory
			.getLogger(TableModelInstanceBodyDataProvider.class);

	private static final String NULL_VALUE = ""; //$NON-NLS-1$

	private final List<TableRow> instances;
	private final int propertyCount;

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

}
