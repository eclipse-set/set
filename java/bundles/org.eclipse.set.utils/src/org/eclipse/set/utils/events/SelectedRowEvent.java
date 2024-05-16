/**
 * Copyright (c) 2021 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */

package org.eclipse.set.utils.events;

import org.eclipse.set.model.tablemodel.TableRow;

/**
 * Selected table row event
 * 
 * @author Truong
 *
 */
public class SelectedRowEvent implements ToolboxEvent {
	private static final String TOPIC = "toolboxevents/table/selectedRow"; //$NON-NLS-1$

	/**
	 * Returns the topic for a specific table that the event should affect
	 * 
	 * @param table
	 *            the element id for the table component
	 * @return a topic for the event
	 */
	public static String getTopic(final String table) {
		return TOPIC;

	}

	TableRow row;

	String table;

	public SelectedRowEvent(final String table) {
		this.table = table;
		this.row = null;
	}

	/**
	 * @param table
	 *            name of table
	 * @param row
	 *            the row
	 */
	public SelectedRowEvent(final String table, final TableRow row) {
		this.table = table;
		this.row = row;
	}

	/**
	 * @return the row
	 */
	public TableRow getRow() {
		return row;
	}

	/**
	 * @return name of table
	 */
	public String getTable() {
		return table;
	}

	@Override
	public String getTopic() {
		return TOPIC;
	}

}
