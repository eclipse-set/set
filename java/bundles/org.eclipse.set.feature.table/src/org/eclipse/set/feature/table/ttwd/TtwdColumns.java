/**
 * Copyright (c) 2017 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.table.ttwd;

import org.eclipse.set.feature.table.AbstractTableColumns;
import org.eclipse.set.feature.table.messages.Messages;
import org.eclipse.set.model.tablemodel.ColumnDescriptor;

/**
 * Symbolic addressing for Ttwd columns.
 * 
 * @author Schaefer
 */
public class TtwdColumns extends AbstractTableColumns {

	/**
	 * A: Kein Warndreieck/Kein Warndreieck
	 */
	public final ColumnDescriptor kwdkwd;

	/**
	 * B: Kein Warndreieck/Warndreieck
	 */
	public final ColumnDescriptor kwdwd;

	/**
	 * C: Warndreieck/Kein Warndreieck
	 */
	public final ColumnDescriptor wdkwd;

	/**
	 * D: Warndreieck/Warndreieck
	 */
	public final ColumnDescriptor wdwd;

	/**
	 * @param messages
	 *            the messages
	 */
	public TtwdColumns(final Messages messages) {
		super(messages);
		kwdkwd = createNew(messages.TtwdTableView_Kwd_Kwd);
		kwdwd = createNew(messages.TtwdTableView_Kwd_Wd);
		wdkwd = createNew(messages.TtwdTableView_Wd_Kwd);
		wdwd = createNew(messages.TtwdTableView_Wd_Wd);
	}
}
