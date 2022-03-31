/**
 * Copyright (c) 2018 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.utils.events;

/**
 * Selects a row in all table according to a leading guid
 * 
 * @author Stuecker
 */
public class TableSelectRowByGuidEvent implements ToolboxEvent {
	/**
	 * The topic of the event.
	 */
	private static final String TOPIC = "toolboxevents/table/row/selectByGuid"; //$NON-NLS-1$

	private final String searchKey;

	/**
	 * Constructor used for retrieving the event name
	 */
	public TableSelectRowByGuidEvent() {
		this.searchKey = ""; //$NON-NLS-1$
	}

	/**
	 * @param searchKey
	 *            The key to search for
	 * 
	 */
	public TableSelectRowByGuidEvent(final String searchKey) {
		this.searchKey = searchKey;
	}

	/**
	 * @return the value to search for
	 */
	public String getSearchKey() {
		return searchKey;
	}

	@Override
	public String getTopic() {
		return TOPIC;
	}
}
