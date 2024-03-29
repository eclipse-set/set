/**
 * Copyright (c) 2022 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.table.overview;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.nebula.widgets.nattable.NatTable;
import org.eclipse.nebula.widgets.nattable.layer.cell.ILayerCell;
import org.eclipse.nebula.widgets.nattable.ui.matcher.MouseEventMatcher;
import org.eclipse.set.core.services.part.ToolboxPartService;
import org.eclipse.set.feature.table.messages.Messages;
import org.eclipse.set.model.tablemodel.Table;
import org.eclipse.set.utils.events.TableSelectRowByGuidEvent;
import org.eclipse.set.utils.events.ToolboxEvents;
import org.eclipse.set.utils.table.TableError;
import org.eclipse.set.utils.table.menu.TableMenuService;
import org.eclipse.set.utils.table.sorting.AbstractSortByColumnTables;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

/**
 * View for table errors
 * 
 * @author Peters
 *
 */
public class TableErrorTableView extends AbstractSortByColumnTables {

	private static final String TABLE_PART_ID_PREFIX = "org.eclipse.set.feature.table."; //$NON-NLS-1$

	private final Messages messages;
	private NatTable natTable;

	private Collection<TableError> tableErrors = new ArrayList<>();
	private final IEventBroker broker;
	private final ToolboxPartService toolboxPartService;

	/**
	 * @param messages
	 *            The messages
	 * @param broker
	 *            the event broker
	 * @param toolboxPartService
	 *            the part service
	 */
	public TableErrorTableView(final Messages messages,
			final IEventBroker broker,
			final ToolboxPartService toolboxPartService) {
		this.messages = messages;
		this.broker = broker;
		this.toolboxPartService = toolboxPartService;
	}

	/**
	 * Creates the table view
	 * 
	 * @param parent
	 *            the parent composite
	 * @return the nattable control
	 */
	public Control create(final Composite parent) {
		final Table table = getTable();
		natTable = createTable(parent, table, null);
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
							.getOriginalRowIndex(row);

					final Optional<TableError> rowElement = tableErrors.stream()
							.skip(originalRow).findFirst();
					if (rowElement.isPresent()) {
						final TableError element = rowElement.get();
						final String guid = element.getLeadingObject();
						final String shortCut = element.getSource()
								.toLowerCase();

						toolboxPartService
								.showPart(TABLE_PART_ID_PREFIX + shortCut);
						ToolboxEvents.send(broker,
								new TableSelectRowByGuidEvent(guid));
					}

				});
		return natTable;
	}

	/**
	 * Updates the table view
	 * 
	 * @param errors
	 *            the new table errors
	 */
	public void updateView(final Collection<TableError> errors) {
		tableErrors = errors;
		if (natTable != null) {
			bodyDataProvider.refresh(getTable());
			natTable.refresh();
		}
	}

	private Table getTable() {
		final TableErrorTransformationService service = new TableErrorTransformationService(
				messages);
		return service.transform(tableErrors);
	}

	@Override
	protected TableMenuService getTableMenuService() {
		throw new UnsupportedOperationException();
	}
}
