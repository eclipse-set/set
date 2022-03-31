/**
 * Copyright (c) 2021 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */

package org.eclipse.set.utils.table;

import java.util.List;

import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.nebula.widgets.nattable.config.AbstractUiBindingConfiguration;
import org.eclipse.nebula.widgets.nattable.selection.SelectionLayer;
import org.eclipse.nebula.widgets.nattable.ui.binding.UiBindingRegistry;
import org.eclipse.nebula.widgets.nattable.ui.matcher.MouseEventMatcher;
import org.eclipse.set.model.tablemodel.TableRow;
import org.eclipse.set.utils.events.SelectedRowEvent;
import org.eclipse.set.utils.events.ToolboxEvents;

/**
 * Selected row listener
 * 
 * @author Truong
 *
 */
public class RowSelectionListener extends AbstractUiBindingConfiguration {

	private int tmpRowPosition = -1;
	IEventBroker broker;
	List<TableRow> rows;
	SelectionLayer selectionLayer;
	String table;

	/**
	 * @param table
	 *            the element id for the table component
	 * @param selectionLayer
	 *            the selection layer
	 * @param rows
	 *            list rows of table
	 * @param broker
	 *            event broker to use to send the event
	 */
	public RowSelectionListener(final String table, final SelectionLayer selectionLayer,
			final List<TableRow> rows, final IEventBroker broker) {
		this.table = table;
		this.selectionLayer = selectionLayer;
		this.broker = broker;
		this.rows = rows;
	}

	@Override
	public void configureUiBindings(final UiBindingRegistry uiBindingRegistry) {
		if (rows.isEmpty()) {
			return;
		}
		uiBindingRegistry.registerFirstSingleClickBinding(
				MouseEventMatcher.bodyLeftClick(0), (natTable, event) -> {
					if (selectionLayer.getSelectedCells().isEmpty()) {
						ToolboxEvents.send(broker,
								new SelectedRowEvent(table, null));
						return;
					}

					final int rowPosition = selectionLayer.getSelectedCells()
							.iterator().next().getRowPosition();

					if (tmpRowPosition == rowPosition) {
						ToolboxEvents.send(broker,
								new SelectedRowEvent(table, null));
						tmpRowPosition = -1;
						selectionLayer.clear();
						return;
					}

					final TableRow row = rows.get(rowPosition);
					tmpRowPosition = rowPosition;
					ToolboxEvents.send(broker,
							new SelectedRowEvent(table, row));
				});
	}

}
