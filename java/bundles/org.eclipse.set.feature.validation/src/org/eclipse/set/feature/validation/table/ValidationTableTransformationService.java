/**
 * Copyright (c) 2022 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.validation.table;

import org.eclipse.set.feature.validation.Messages;
import org.eclipse.set.model.tablemodel.ColumnDescriptor;
import org.eclipse.set.model.validationreport.ValidationReport;
import org.eclipse.set.utils.table.AbstractTableTransformationService;
import org.eclipse.set.utils.table.ColumnDescriptorModelBuilder;
import org.eclipse.set.utils.table.TableModelTransformator;

/**
 * Transformation service for the ValidationTable
 * 
 * @author Stuecker
 *
 */
public class ValidationTableTransformationService
		extends AbstractTableTransformationService<ValidationReport> {

	private ValidationTableColumns columns;

	private final Messages messages;

	/**
	 * @param messages
	 *            the messages
	 */
	public ValidationTableTransformationService(final Messages messages) {
		this.messages = messages;
	}

	@Override
	public TableModelTransformator<ValidationReport> createTransformator() {
		return new ValidationReportTableTransformator(columns);
	}

	@Override
	public ColumnDescriptor fillHeaderDescriptions(
			final ColumnDescriptorModelBuilder builder) {
		columns = new ValidationTableColumns(messages);
		return columns.fillHeaderDescriptions(builder);
	}
}
