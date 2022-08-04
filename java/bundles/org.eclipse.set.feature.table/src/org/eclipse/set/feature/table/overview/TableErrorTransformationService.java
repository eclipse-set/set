/**
 * Copyright (c) 2022 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.table.overview;

import java.util.Collection;

import org.eclipse.set.feature.table.messages.Messages;
import org.eclipse.set.model.tablemodel.ColumnDescriptor;
import org.eclipse.set.utils.table.AbstractTableTransformationService;
import org.eclipse.set.utils.table.ColumnDescriptorModelBuilder;
import org.eclipse.set.utils.table.TableError;
import org.eclipse.set.utils.table.TableModelTransformator;

/**
 * Transformation service for the table error table
 * 
 * @author Peters
 *
 */
public class TableErrorTransformationService
		extends AbstractTableTransformationService<Collection<TableError>> {

	private TableErrorTableColumns columns;

	private final Messages messages;

	/**
	 * @param messages
	 *            the messages
	 */
	public TableErrorTransformationService(final Messages messages) {
		this.messages = messages;
	}

	@Override
	public TableModelTransformator<Collection<TableError>> createTransformator() {
		return new TableErrorTableTransformator(columns);
	}

	@Override
	public ColumnDescriptor fillHeaderDescriptions(
			final ColumnDescriptorModelBuilder builder) {
		return columns.fillHeaderDescriptions(builder);
	}

	@Override
	protected void buildColumns() {
		columns = new TableErrorTableColumns(messages);
	}

}
