/**
 * Copyright (c) 2022 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.validation.table;

import org.eclipse.nebula.widgets.nattable.NatTable;
import org.eclipse.set.basis.IModelSession;
import org.eclipse.set.feature.validation.Messages;
import org.eclipse.set.model.tablemodel.Table;
import org.eclipse.set.model.validationreport.ValidationReport;
import org.eclipse.set.utils.BasePart;
import org.eclipse.set.utils.table.menu.TableMenuService;
import org.eclipse.set.utils.table.sorting.AbstractSortByColumnTables;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

/**
 * View for the validation table
 * 
 * @author Stuecker
 *
 */
public class ValidationTableView extends AbstractSortByColumnTables {
	private final Messages messages;
	private final BasePart<? extends IModelSession> part;
	private NatTable natTable;

	private final TableMenuService tableMenuService;

	/**
	 * @param part
	 *            The source part used for events
	 * @param messages
	 *            The messages
	 * @param tableMenuService
	 *            The table menu service
	 */
	public ValidationTableView(final BasePart<? extends IModelSession> part,
			final Messages messages, final TableMenuService tableMenuService) {
		this.part = part;
		this.messages = messages;
		this.tableMenuService = tableMenuService;
	}

	/**
	 * Creates the table view
	 * 
	 * @param parent
	 *            the parent composite
	 * @param validationReport
	 *            the validation report to show
	 * @return the nattable control
	 */
	public Control create(final Composite parent,
			final ValidationReport validationReport) {
		final ValidationTableTransformationService service = new ValidationTableTransformationService(
				messages);

		final Table table = service.transform(validationReport);
		this.createTableBodyData(table,
				rowline -> Integer.valueOf(validationReport.getProblems()
						.get(rowline.intValue()).getLineNumber()));
		tableMenuService.createDefaultMenuItems(part, table, bodyDataProvider,
				bodyLayerStack.getSelectionLayer());
		natTable = createTable(parent, table, tableMenuService);
		return natTable;
	}

	/**
	 * Updates the table view
	 * 
	 * @param validationReport
	 *            the new report
	 */
	public void updateView(final ValidationReport validationReport) {
		final ValidationTableTransformationService service = new ValidationTableTransformationService(
				messages);
		bodyDataProvider.refresh(service.transform(validationReport));
		natTable.refresh();
	}
}
