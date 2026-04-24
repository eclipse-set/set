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

	/**
	 * The event topic
	 */
	public static final String TOPIC = "toolboxevents/update/tabledata"; //$NON-NLS-1$

	private final List<Object> properties;
	private final String tableShortcut;

	/**
	 * @param tableShortcut
	 *            the table shortcut
	 * @param property
	 *            the property for change data
	 */
	public TableDataChangeEvent(final String tableShortcut,
			final Object property) {
		this.properties = new ArrayList<>();
		if (property instanceof final List<?> list) {
			properties.addAll(list);
		} else {
			properties.add(property);
		}

		this.tableShortcut = tableShortcut;
	}

	/**
	 * @return properties object
	 */
	public List<Object> getProperties() {
		return properties;
	}

	/**
	 * @return the table shortcut
	 */
	public String getTableShortcut() {
		return tableShortcut;
	}

	@Override
	public String getTopic() {
		return TOPIC;
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
