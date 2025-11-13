/**
 * Copyright (c) 2025 DB InfraGO AG and others
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 */
package org.eclipse.set.utils.events;

import org.eclipse.set.basis.constants.TableType;

/**
 * Event to trigger resort table
 * 
 * @author truong
 */
public class ResortTableEvent implements ToolboxEvent {

	private final String tableShortcut;
	private final TableType tableType;

	/**
	 * @param tableShortcut
	 *            the table shortcut
	 * @param tableType
	 *            the table type
	 */
	public ResortTableEvent(final String tableShortcut,
			final TableType tableType) {
		this.tableShortcut = tableShortcut;
		this.tableType = tableType;
	}

	@Override
	public String getTopic() {
		return getTopic(tableShortcut);
	}

	/**
	 * @param shortcut
	 *            the table shortcut
	 * @return the event topic
	 */
	public static String getTopic(final String shortcut) {
		return shortcut.toLowerCase() + "/triggerResortTable"; //$NON-NLS-1$
	}

	/**
	 * @return the {@link TableType}
	 */
	public TableType getTableType() {
		return tableType;
	}

}
