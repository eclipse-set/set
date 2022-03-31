/**
 * Copyright (c) 2019 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.table.bv;

import org.eclipse.set.feature.table.AbstractTableColumns;
import org.eclipse.set.feature.table.messages.Messages;
import org.eclipse.set.model.tablemodel.ColumnDescriptor;

/**
 * Sslr Columns.
 * 
 * @author Schaefer
 * 
 * @see ColumnDescriptor
 */
public class BvColumns extends AbstractTableColumns {

	/**
	 * C: Bv.Attributname
	 */
	public ColumnDescriptor Attributname;

	/**
	 * D: Bv.Attributwert
	 */
	public ColumnDescriptor Attributwert;

	/**
	 * E: Bv.Bearbeitungsvermerk
	 */
	public ColumnDescriptor Bearbeitungsvermerk;

	/**
	 * B: Bv.Bezeichnung
	 */
	public ColumnDescriptor Bezeichnung;

	/**
	 * A: Bv.LstObjektName
	 */
	public ColumnDescriptor LstObjektName;

	/**
	 * @param messages
	 *            the messages
	 */
	public BvColumns(final Messages messages) {
		super(messages);
		LstObjektName = createNew(messages.Bv_LstObjektName);
		Bezeichnung = createNew(messages.Bv_Bezeichnung);
		Attributname = createNew(messages.Bv_Attributname);
		Attributwert = createNew(messages.Bv_Attributwert);
		Bearbeitungsvermerk = createNew(messages.Bv_Bearbeitungsvermerk);
	}
}
