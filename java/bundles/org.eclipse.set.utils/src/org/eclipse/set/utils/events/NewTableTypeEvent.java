/**
 * Copyright (c) 2018 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.utils.events;

import org.eclipse.set.basis.constants.TableType;

/**
 * A new table type was selected.
 * 
 * @author Schaefer
 */
public class NewTableTypeEvent implements ToolboxEvent {

	private static final String TOPIC = "tooboxevents/tabletype/selection/new"; //$NON-NLS-1$

	private final TableType tableType;

	/**
	 * Create default event.
	 */
	public NewTableTypeEvent() {
		this(null);
	}

	/**
	 * @param tableType
	 *            the table type
	 */
	public NewTableTypeEvent(final TableType tableType) {
		this.tableType = tableType;
	}

	/**
	 * @return the tableType
	 */
	public TableType getTableType() {
		return tableType;
	}

	@Override
	public String getTopic() {
		return TOPIC;
	}
}
