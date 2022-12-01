/**
 * Copyright (c) 2022 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.validation.table;

import java.util.Collection;

import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.nebula.widgets.nattable.NatTable;
import org.eclipse.nebula.widgets.nattable.layer.cell.ILayerCell;
import org.eclipse.nebula.widgets.nattable.ui.matcher.MouseEventMatcher;
import org.eclipse.set.basis.IModelSession;
import org.eclipse.set.core.services.enumtranslation.EnumTranslationService;
import org.eclipse.set.core.services.part.ToolboxPartService;
import org.eclipse.set.feature.validation.Messages;
import org.eclipse.set.model.tablemodel.Table;
import org.eclipse.set.model.validationreport.ValidationReport;
import org.eclipse.set.utils.BasePart;
import org.eclipse.set.utils.events.JumpToSourceLineEvent;
import org.eclipse.set.utils.events.ToolboxEvents;
import org.eclipse.set.utils.table.sorting.AbstractSortByColumnTables;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

/**
 * View for the validation table
 * 
 * @author Stuecker
 *
 */
public class ValidationTableView extends AbstractSortByColumnTables {
	private static final String SOURCE_TEXT_VIEWER_PART_ID = "org.eclipse.set.application.descriptions.SourceWebTextViewDescriptionService"; //$NON-NLS-1$

	private final ToolboxPartService toolboxPartService;
	private final Messages messages;
	private final IEventBroker broker;
	private final BasePart<? extends IModelSession> part;
	private NatTable natTable;
	private final EnumTranslationService enumTranslationService;

	/**
	 * @param toolboxPartService
	 *            the part service
	 * @param part
	 *            The source part used for events
	 * @param messages
	 *            The messages
	 * @param broker
	 *            The event broker
	 * @param enumService
	 *            The enum translation service
	 */
	public ValidationTableView(final ToolboxPartService toolboxPartService,
			final BasePart<? extends IModelSession> part,
			final Messages messages, final IEventBroker broker,
			final EnumTranslationService enumService) {
		this.toolboxPartService = toolboxPartService;
		this.part = part;
		this.messages = messages;
		this.broker = broker;
		this.enumTranslationService = enumService;
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
				messages, enumTranslationService);

		final Table table = service.transform(validationReport);
		natTable = createTable(parent, table);

		natTable.getUiBindingRegistry().registerFirstDoubleClickBinding(
				MouseEventMatcher.bodyLeftClick(0),
				(final NatTable natTable2, final MouseEvent event) -> {
					final Collection<ILayerCell> selectedCells = bodyLayerStack
							.getSelectionLayer().getSelectedCells();
					if (selectedCells.isEmpty()) {
						return;
					}

					final int row = selectedCells.iterator().next()
							.getRowPosition();
					final int originalRow = bodyDataProvider
							.getOriginalRow(row);
					toolboxPartService.showPart(SOURCE_TEXT_VIEWER_PART_ID);
					ToolboxEvents.send(broker,
							new JumpToSourceLineEvent(
									validationReport.getProblems()
											.get(originalRow).getLineNumber(),
									part));
				}

		);

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
				messages, enumTranslationService);
		bodyDataProvider.refresh(service.transform(validationReport));
		natTable.refresh();
	}
}
