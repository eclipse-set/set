/**
 * Copyright (c) 2022 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */

package org.eclipse.set.sessionservice;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import org.eclipse.e4.core.services.nls.Translation;
import org.eclipse.nebula.widgets.nattable.data.IDataProvider;
import org.eclipse.nebula.widgets.nattable.layer.cell.ILayerCell;
import org.eclipse.nebula.widgets.nattable.selection.SelectionLayer;
import org.eclipse.nebula.widgets.nattable.ui.menu.IMenuItemProvider;
import org.eclipse.set.basis.Pair;
import org.eclipse.set.model.tablemodel.Table;
import org.eclipse.set.model.tablemodel.TableRow;
import org.eclipse.set.model.tablemodel.extensions.TableExtensions;
import org.eclipse.set.model.tablemodel.extensions.TableRowExtensions;
import org.eclipse.set.model.validationreport.ObjectState;
import org.eclipse.set.utils.BasePart;
import org.eclipse.set.utils.events.JumpToSourceLineEvent;
import org.eclipse.set.utils.table.TableDataProvider;
import org.eclipse.set.utils.table.menu.AbstractTableMenuService;
import org.eclipse.set.utils.table.menu.TableMenuService;

/**
 * Implementation of {@link TableMenuService} for the SET product
 * 
 * @author Truong
 *
 */
public class SetTableMenuService extends AbstractTableMenuService {
	@Inject
	@Translation
	Messages messages;

	@Override
	public Set<IMenuItemProvider> getMenuItems() {
		return this.menuItems;
	}

	@Override
	public void createDefaultMenuItems(final BasePart part, final Table table,
			final IDataProvider dataProvider,
			final SelectionLayer selectionLayer) {

		this.createShowInTextViewItem(messages.SetTableMenuService_TextView,
				new JumpToSourceLineEvent(part) {

					@Override
					public Pair<ObjectState, Integer> getLineNumber() {
						final Collection<ILayerCell> selectedCells = selectionLayer
								.getSelectedCells();
						if (selectedCells.isEmpty()) {
							return new Pair<>(null, Integer.valueOf(-1));
						}
						final int rowPosition = selectedCells.iterator().next()
								.getRowPosition();
						if (dataProvider instanceof final TableDataProvider tableDataProvider) {
							final int originalRow = tableDataProvider
									.getOriginalRow(rowPosition);
							final String objectState = tableDataProvider
									.getObjectState(rowPosition);
							final ObjectState state = ObjectState
									.get(objectState);
							final Integer lineNumber = Integer
									.valueOf(tableDataProvider
											.getObjectSourceLine(originalRow));
							return new Pair<>(state, lineNumber);
						}
						return new Pair<>(null, Integer.valueOf(-1));

					}

					@Override
					public String getObjectGuid() {
						final Collection<ILayerCell> selectedCells = selectionLayer
								.getSelectedCells();
						if (selectedCells.isEmpty()) {
							return null;
						}
						final int rowPosition = selectedCells.iterator().next()
								.getRowPosition();
						final List<TableRow> tableRows = TableExtensions
								.getTableRows(table);
						return TableRowExtensions.getLeadingObjectGuid(
								tableRows.get(rowPosition));
					}

				});
	}
}
