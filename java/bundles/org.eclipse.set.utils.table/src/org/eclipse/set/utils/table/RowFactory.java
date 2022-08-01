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
import org.eclipse.set.model.tablemodel.TableRow;

/**
 * Create {@link TableRow}s.
 * 
 * @author Schaefer
 */
public interface RowFactory {

	/**
	 * @return the new table row
	 */
	TableRow newTableRow();

	/**
	 * @return the row group
	 */
	RowGroup getRowGroup();
}
