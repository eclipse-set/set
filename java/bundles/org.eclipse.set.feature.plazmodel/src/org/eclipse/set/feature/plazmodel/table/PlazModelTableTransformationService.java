/**
 * Copyright (c) 2022 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.plazmodel.table;

import org.eclipse.set.core.services.enumtranslation.EnumTranslationService;
import org.eclipse.set.feature.plazmodel.Messages;
import org.eclipse.set.model.plazmodel.PlazReport;
import org.eclipse.set.model.tablemodel.ColumnDescriptor;
import org.eclipse.set.utils.table.AbstractTableTransformationService;
import org.eclipse.set.utils.table.ColumnDescriptorModelBuilder;
import org.eclipse.set.utils.table.TableModelTransformator;

/**
 * Transformation service for the Plaz Report
 * 
 * @author Stuecker
 *
 */
public class PlazModelTableTransformationService
		extends AbstractTableTransformationService<PlazReport> {

	private PlazReportColumns columns;

	private final Messages messages;

	private final EnumTranslationService enumTranslationService;

	/**
	 * @param messages
	 *            the messages
	 * @param enumTranslationService
	 *            the enum translation service
	 */
	public PlazModelTableTransformationService(final Messages messages,
			final EnumTranslationService enumTranslationService) {
		this.messages = messages;
		this.enumTranslationService = enumTranslationService;
	}

	@Override
	public TableModelTransformator<PlazReport> createTransformator() {
		return new PlazModelTableTransformator(columns, enumTranslationService);
	}

	@Override
	public ColumnDescriptor fillHeaderDescriptions(
			final ColumnDescriptorModelBuilder builder) {
		columns = new PlazReportColumns(messages);
		return columns.fillHeaderDescriptions(builder);
	}
}
