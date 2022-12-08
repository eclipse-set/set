/**
 * Copyright (c) 2022 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */

package org.eclipse.set.utils.table.menu;

import java.util.Set;

import org.eclipse.nebula.widgets.nattable.NatTable;
import org.eclipse.nebula.widgets.nattable.data.IDataProvider;
import org.eclipse.nebula.widgets.nattable.selection.SelectionLayer;
import org.eclipse.nebula.widgets.nattable.ui.menu.IMenuItemProvider;
import org.eclipse.set.model.tablemodel.Table;
import org.eclipse.set.utils.BasePart;
import org.eclipse.swt.events.SelectionListener;

/**
 * Service for table menu
 * 
 * @author Truong
 */
public interface TableMenuService {

	/**
	 * @return list of menu item
	 */
	Set<IMenuItemProvider> getMenuItems();

	/**
	 * @param menuItem
	 */
	void addMenuItem(IMenuItemProvider menuItem);

	/**
	 * Create default items for the menu (e.g show text view)
	 * 
	 * @param part
	 *            the part of table
	 * @param table
	 *            the {@link Table}
	 * @param tableDataProvider
	 *            table data provider
	 * @param selectionLayer
	 *            selection layer
	 */
	void createDefaultMenuItems(BasePart part, Table table,
			final IDataProvider tableDataProvider,
			SelectionLayer selectionLayer);

	/**
	 * @param label
	 * @param selectionListerner
	 * @return menu item
	 */
	IMenuItemProvider createMenuItem(final String label,
			final SelectionListener selectionListerner);

	/**
	 * craete menu configuration for the table
	 * 
	 * @param natTable
	 *            the table
	 * @param selectionLayer
	 *            the selection layer
	 * @return {@link TableBodyMenuConfiguration}
	 */
	TableBodyMenuConfiguration createMenuConfiguration(final NatTable natTable,
			final SelectionLayer selectionLayer);
}
