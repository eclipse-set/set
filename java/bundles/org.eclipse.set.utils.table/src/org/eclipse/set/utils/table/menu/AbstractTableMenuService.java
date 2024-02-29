/**
 * Copyright (c) 2022 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */

package org.eclipse.set.utils.table.menu;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.inject.Inject;

import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.nebula.widgets.nattable.NatTable;
import org.eclipse.nebula.widgets.nattable.data.IDataProvider;
import org.eclipse.nebula.widgets.nattable.layer.cell.ILayerCell;
import org.eclipse.nebula.widgets.nattable.selection.SelectionLayer;
import org.eclipse.nebula.widgets.nattable.ui.menu.IMenuItemProvider;
import org.eclipse.set.basis.Pair;
import org.eclipse.set.core.services.part.ToolboxPartService;
import org.eclipse.set.model.tablemodel.Table;
import org.eclipse.set.model.tablemodel.TableRow;
import org.eclipse.set.model.tablemodel.extensions.TableExtensions;
import org.eclipse.set.model.tablemodel.extensions.TableRowExtensions;
import org.eclipse.set.model.validationreport.ObjectScope;
import org.eclipse.set.utils.BasePart;
import org.eclipse.set.utils.events.JumpToSourceLineEvent;
import org.eclipse.set.utils.events.ToolboxEvent;
import org.eclipse.set.utils.events.ToolboxEvents;
import org.eclipse.set.utils.table.TableDataProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;

/**
 * Abstract implementation of {@link TableMenuService}
 * 
 * @author Truong
 */
public abstract class AbstractTableMenuService implements TableMenuService {
	private static final String SOURCE_TEXT_VIEWER_PART_ID = "org.eclipse.set.application.descriptions.SourceWebTextViewDescriptionService"; //$NON-NLS-1$

	@Inject
	ToolboxPartService toolboxPartService;

	@Inject
	protected IEventBroker broker;
	protected Set<IMenuItemProvider> menuItems = new HashSet<>();

	protected TableBodyMenuConfiguration tableBodyMenuConfiguration;

	@Override
	public TableBodyMenuConfiguration createMenuConfiguration(
			final NatTable natTable, final SelectionLayer selectionLayer) {
		tableBodyMenuConfiguration = new TableBodyMenuConfiguration(natTable,
				selectionLayer, menuItems);
		return tableBodyMenuConfiguration;
	}

	protected void createShowInTextViewItem(final String label,
			final ToolboxEvent toolboxEvent) {
		this.menuItems.add(createMenuItem(label, new SelectionAdapter() {
			@Override
			public void widgetSelected(final SelectionEvent e) {

				if (tableBodyMenuConfiguration.selectionLayer.getSelectedCells()
						.isEmpty()) {
					return;
				}
				toolboxPartService.showPart(SOURCE_TEXT_VIEWER_PART_ID);
				ToolboxEvents.send(broker, toolboxEvent);
			}
		}));
	}

	/**
	 * @param label
	 *            item label
	 * @param selectionListener
	 *            the {@link SelectionListener}
	 * @return {@link IMenuItemProvider}
	 */
	@Override
	public IMenuItemProvider createMenuItem(final String label,
			final SelectionListener selectionListener) {
		return (final NatTable natTable, final Menu popupMenu) -> {
			final MenuItem menuItem = new MenuItem(popupMenu, SWT.PUSH);
			menuItem.setText(label);
			menuItem.setEnabled(true);
			menuItem.addSelectionListener(selectionListener);
		};
	}

	@Override
	public void addMenuItem(final IMenuItemProvider menuItem) {
		this.menuItems.add(menuItem);
	}

	protected void createDefaultMenuItems(final String viewItemTitle,
			final BasePart part, final Table table,
			final IDataProvider dataProvider,
			final SelectionLayer selectionLayer) {

		this.createShowInTextViewItem(viewItemTitle,
				new JumpToSourceLineEvent(part) {

					@Override
					public Pair<ObjectScope, Integer> getLineNumber() {
						final Collection<ILayerCell> selectedCells = selectionLayer
								.getSelectedCells();
						if (selectedCells.isEmpty()) {
							return new Pair<>(null, Integer.valueOf(-1));
						}
						final int rowPosition = selectedCells.iterator().next()
								.getRowPosition();
						if (dataProvider instanceof final TableDataProvider tableDataProvider) {
							final int originalRow = tableDataProvider
									.getOriginalRowIndex(rowPosition);
							final String objectScope = tableDataProvider
									.getObjectScope(rowPosition);
							final ObjectScope scope = ObjectScope
									.get(objectScope);
							final Integer lineNumber = Integer
									.valueOf(tableDataProvider
											.getObjectSourceLine(originalRow));
							return new Pair<>(scope, lineNumber);
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
