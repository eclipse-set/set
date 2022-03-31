/**
 * Copyright (c) 2019 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.utils.table;

import org.eclipse.set.model.tablemodel.RowGroup;

/**
 * Implementation of {@link RowFactory} for a {@link RowGroup}.
 * 
 * @author Schaefer
 */
public class RowGroupRowFactory extends AbstractRowFactory {

	private final RowGroup rowGroup;

	/**
	 * @param rowGroup
	 *            the row group
	 */
	public RowGroupRowFactory(final RowGroup rowGroup) {
		this.rowGroup = rowGroup;
	}

	@Override
	public RowGroup getRowGroup() {
		return rowGroup;
	}
}
