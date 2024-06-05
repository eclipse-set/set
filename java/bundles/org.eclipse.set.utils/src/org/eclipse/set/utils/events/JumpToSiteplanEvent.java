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
public class JumpToSiteplanEvent implements ToolboxEvent {
	private static final String TOPIC = "toolboxevents/table/selectedRow"; //$NON-NLS-1$

	TableRow row;
	String guid;

	/**
	 * Default Constructor
	 */
	public JumpToSiteplanEvent() {
		this.row = null;
		this.guid = null;
	}

	/**
	 * @param row
	 *            the table row
	 * @param guid
	 *            the row reference object guid
	 */
	public JumpToSiteplanEvent(final TableRow row, final String guid) {
		this.row = row;
		this.guid = guid;
	}

	/**
	 * @param guid
	 *            the row reference object guid
	 */
	public JumpToSiteplanEvent(final String guid) {
		this.row = null;
		this.guid = guid;
	}

	/**
	 * @param row
	 *            the row
	 */
	public JumpToSiteplanEvent(final TableRow row) {
		this.row = row;
		this.guid = null;
	}

	/**
	 * @return the row
	 */
	public TableRow getRow() {
		return row;
	}

	/**
	 * @return the row referenct object guid
	 */
	public String getGuid() {
		return guid;
	}

	@Override
	public String getTopic() {
		return TOPIC;
	}

}
