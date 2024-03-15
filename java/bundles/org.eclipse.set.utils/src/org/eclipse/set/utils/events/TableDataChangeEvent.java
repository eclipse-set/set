/**
 * Copyright (c) 2024 DB InfraGO AG and others
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 */
package org.eclipse.set.utils.events;

/**
 * Update table data at run time
 * 
 * @author Truong
 */
public class TableDataChangeEvent implements ToolboxEvent {

	private static final String TOPIC = "toolboxevents/update/tabledata/"; //$NON-NLS-1$

	/**
	 * Returns the topic for a specific table that the event should affect
	 * 
	 * @param table
	 *            the element id for the table component
	 * @return a topic for the event
	 */
	public static String getTopic(final String table) {
		return TOPIC + table.replace(".", "/"); //$NON-NLS-1$ //$NON-NLS-2$
	}

	private final Object properties;
	private final String tableName;

	/**
	 * @param tableName
	 *            name of table
	 * @param properties
	 *            the properties for change data
	 */
	public TableDataChangeEvent(final String tableName,
			final Object properties) {
		this.properties = properties;
		this.tableName = tableName;

	}

	/**
	 * @return properties object
	 */
	public Object getProperties() {
		return properties;
	}

	@Override
	public String getTopic() {
		return getTopic(tableName);
	}

}
