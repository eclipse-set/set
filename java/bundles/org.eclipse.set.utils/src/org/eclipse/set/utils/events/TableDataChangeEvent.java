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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.eclipse.e4.core.services.events.IEventBroker;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventAdmin;

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

	private final List<Object> properties;
	private final String tableName;

	/**
	 * @param tableName
	 *            name of table
	 * @param property
	 *            the property for change data
	 */
	public TableDataChangeEvent(final String tableName, final Object property) {
		this.properties = new ArrayList<>();
		properties.add(property);
		this.tableName = tableName;

	}

	/**
	 * @param tableName
	 *            the name of table
	 * @param properties
	 *            the properties for change data
	 */
	public TableDataChangeEvent(final String tableName,
			final List<Object> properties) {
		this.properties = properties;
		this.tableName = tableName;
	}

	/**
	 * @return properties object
	 */
	public List<Object> getProperties() {
		return properties;
	}

	@Override
	public String getTopic() {
		return getTopic(tableName);
	}

	/**
	 * Create & send osgi event
	 * 
	 * @param eventAdmin
	 *            the event admin
	 * @param dataChangeEvent
	 *            the toolbox event
	 */
	public static void sendEvent(final EventAdmin eventAdmin,
			final TableDataChangeEvent dataChangeEvent) {
		final HashMap<String, Object> eventProperties = new HashMap<>();
		eventProperties.put(ToolboxEvents.TOOLBOX_EVENT, dataChangeEvent);
		eventProperties.put(IEventBroker.DATA, dataChangeEvent);
		eventAdmin.sendEvent(
				new Event(dataChangeEvent.getTopic(), eventProperties));
	}
}
