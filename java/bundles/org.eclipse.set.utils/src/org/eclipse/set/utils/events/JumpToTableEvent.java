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
public class JumpToTableEvent implements ToolboxEvent {
	/**
	 * The topic of the event.
	 */
	private static final String TOPIC = "toolboxevents/table/row/selectByGuid"; //$NON-NLS-1$

	private final String searchKey;

	private String tableShortcut;

	private String tableCategory;

	/**
	 * Constructor used for retrieving the event name
	 */
	public JumpToTableEvent() {
		this.searchKey = ""; //$NON-NLS-1$
	}

	/**
	 * @param tableShortcut
	 *            the table shortcut
	 * @param tableCategory
	 *            the table category
	 * @param searchKey
	 *            The key to search for
	 * 
	 */
	public JumpToTableEvent(final String tableShortcut,
			final String tableCategory, final String searchKey) {
		this.searchKey = searchKey;
		this.tableShortcut = tableShortcut;
		this.tableCategory = tableCategory;
	}

	/**
	 * 
	 * @param searchKey
	 *            The key to search for
	 * 
	 */
	public JumpToTableEvent(final String searchKey) {
		this.searchKey = searchKey;
		this.tableShortcut = null;
		this.tableCategory = null;
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

	/**
	 * @return the table shortcut
	 */
	public String getTableShortcut() {
		return tableShortcut;
	}

	/**
	 * @return the table category
	 */
	public String getTableCategory() {
		return tableCategory;
	}
}
