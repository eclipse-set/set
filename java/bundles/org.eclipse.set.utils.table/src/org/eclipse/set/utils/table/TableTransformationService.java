/**
 * Copyright (c) 2016 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.utils.table;

import java.util.Collection;
import java.util.Comparator;

import org.eclipse.set.model.tablemodel.RowGroup;
import org.eclipse.set.model.tablemodel.Table;

/**
 * Interface for all model services which transform a model into the table
 * specific table model. TableTransformionServiceat
 * 
 * @author rumpf
 * @param <T>
 *            the model type to be transformed by this service
 */
public interface TableTransformationService<T> {

	/**
	 * Formats a table.
	 * 
	 * @param table
	 *            the table
	 */
	void format(Table table);

	/**
	 * @return the comparator for sorting row groups
	 */
	Comparator<RowGroup> getRowGroupComparator();

	/**
	 * Transforms a model to a table model.
	 * 
	 * 
	 * @param model
	 *            the model to be used
	 * @return the transformed table
	 */
	Table transform(T model);

	/**
	 * Errors that occurred during transformation
	 * 
	 * @return the errors
	 */
	Collection<TableError> getTableErrors();
}
