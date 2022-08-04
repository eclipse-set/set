/**
 * Copyright (c) 2022 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.table.overview;

import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.nebula.widgets.nattable.NatTable;
import org.eclipse.set.feature.table.messages.Messages;
import org.eclipse.set.model.tablemodel.Table;
import org.eclipse.set.utils.table.TableError;
import org.eclipse.set.utils.table.sorting.AbstractSortByColumnTables;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

/**
 * View for table errors
 * 
 * @author Peters
 *
 */
public class TableErrorTableView extends AbstractSortByColumnTables {

	private final Messages messages;
	private NatTable natTable;

	private Collection<TableError> tableErrors = new ArrayList<>();

	/**
	 * @param messages
	 *            The messages
	 */
	public TableErrorTableView(final Messages messages) {
		this.messages = messages;
	}

	/**
	 * Creates the table view
	 * 
	 * @param parent
	 *            the parent composite
	 * @return the nattable control
	 */
	public Control create(final Composite parent) {
		final Table table = getTable();
		natTable = createTable(parent, table);
		return natTable;
	}

	/**
	 * Updates the table view
	 * 
	 * @param errors
	 *            the new table errors
	 */
	public void updateView(final Collection<TableError> errors) {
		tableErrors = errors;
		if (natTable != null) {
			bodyDataProvider.refresh(getTable());
			natTable.refresh();
		}
	}

	private Table getTable() {
		final TableErrorTransformationService service = new TableErrorTransformationService(
				messages);
		return service.transform(tableErrors);
	}
}
