/**
 * Copyright (c) 2017 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.utils.table;

import java.util.Collection;

import org.eclipse.set.model.tablemodel.Table;

/**
 * Describes a transformation from a model T to a table model.
 * 
 * @author Bleidiessel
 * @param <T>
 *            The model type
 */
public interface TableModelTransformator<T> {

	/**
	 * Transforms the given container of a PlanPro model to a particular table
	 * of the table model.
	 * 
	 * @param model
	 *            the model
	 * @param factory
	 *            factory to create new rows of the table model
	 * @return the table model
	 */
	public Table transformTableContent(T model, TMFactory factory);

	/**
	 * Errors that occurred during transformation
	 * 
	 * @return the errors
	 */
	Collection<TableError> getTableErrors();
}
