/**
 * Copyright (c) 2016 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.table;

import org.eclipse.set.feature.table.messages.Messages;
import org.eclipse.set.model.tablemodel.ColumnDescriptor;
import org.eclipse.set.nattable.utils.AbstractColumns;

/**
 * Abstract Columns description, containing default columns for the identifier
 * and notice
 * 
 * @author rumpf
 *
 */
public abstract class AbstractTableColumns extends AbstractColumns {
	/**
	 * basis_bemerkung
	 */
	public final ColumnDescriptor basis_bemerkung;

	/**
	 * basis_bezeichnung
	 */
	public final ColumnDescriptor basis_bezeichnung;

	/**
	 * constructor.
	 * 
	 * @param messages
	 *            the i8n messages
	 */
	public AbstractTableColumns(final Messages messages) {
		super();
		basis_bezeichnung = createNew(messages.Common_Identifier);
		basis_bemerkung = createNew(messages.Common_Remark);
	}

	/**
	 * @return basis_bemerkung
	 */
	public ColumnDescriptor getBasis_bemerkung() {
		return basis_bemerkung;
	}

	/**
	 * @return basis_bezeichnung
	 */
	public ColumnDescriptor getBasis_bezeichnung() {
		return basis_bezeichnung;
	}
}