/**
 * Copyright (c) 2022 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */

package org.eclipse.set.application.table;

import java.util.HashSet;
import java.util.Set;
import java.util.function.IntPredicate;

import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.core.services.nls.Translation;
import org.eclipse.nebula.widgets.nattable.NatTable;
import org.eclipse.nebula.widgets.nattable.selection.SelectionLayer;
import org.eclipse.set.application.Messages;
import org.eclipse.set.core.services.part.ToolboxPartService;
import org.eclipse.set.utils.events.JumpToSourceLineEvent;
import org.eclipse.set.utils.events.ToolboxEvents;
import org.eclipse.set.utils.table.menu.TableBodyMenuConfiguration;
import org.eclipse.set.utils.table.menu.TableBodyMenuConfiguration.TableBodyMenuItem;
import org.eclipse.set.utils.table.menu.TableMenuService;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

import jakarta.inject.Inject;

/**
 * Implementation of {@link TableMenuService}
 * 
 * @author Truong
 *
 */
public class TableMenuServiceImpl implements TableMenuService {
	private static final String SOURCE_TEXT_VIEWER_PART_ID = "org.eclipse.set.application.descriptions.SourceWebTextViewDescriptionService"; //$NON-NLS-1$

	@Inject
	ToolboxPartService toolboxPartService;

	@Inject
	@Translation
	Messages messages;

	@Inject
	protected IEventBroker broker;
	protected Set<TableBodyMenuItem> menuItems = new HashSet<>();

	protected TableBodyMenuConfiguration tableBodyMenuConfiguration;

	@Override
	public TableBodyMenuConfiguration createMenuConfiguration(
			final NatTable natTable, final SelectionLayer selectionLayer) {
		tableBodyMenuConfiguration = new TableBodyMenuConfiguration(natTable,
				selectionLayer, menuItems);
		return tableBodyMenuConfiguration;
	}

	@Override
	public void addMenuItem(final TableBodyMenuItem menuItem) {
		this.menuItems.add(menuItem);
	}

	@Override
	public Set<TableBodyMenuItem> getMenuItems() {
		return this.menuItems;
	}

	@Override
	public TableBodyMenuItem createShowInTextViewItem(
			final JumpToSourceLineEvent toolboxEvent,
			final IntPredicate enablePredicate) {

		return new TableBodyMenuItem(messages.TableMenuService_TextView,
				new SelectionAdapter() {
					@Override
					public void widgetSelected(final SelectionEvent e) {

						if (tableBodyMenuConfiguration.selectionLayer
								.getSelectedCells().isEmpty()) {
							return;
						}
						toolboxPartService.showPart(SOURCE_TEXT_VIEWER_PART_ID);
						ToolboxEvents.send(broker, toolboxEvent);
					}
				}, enablePredicate);
	}
}
