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
import java.util.function.IntPredicate;

import org.eclipse.nebula.widgets.nattable.NatTable;
import org.eclipse.nebula.widgets.nattable.selection.SelectionLayer;
import org.eclipse.set.utils.events.JumpToSourceLineEvent;
import org.eclipse.set.utils.table.menu.TableBodyMenuConfiguration.TableBodyMenuItem;

/**
 * Service for table menu
 * 
 * @author Truong
 */
public interface TableMenuService {

	/**
	 * @return list of menu item
	 */
	Set<TableBodyMenuItem> getMenuItems();

	/**
	 * @param menuItem
	 */
	void addMenuItem(TableBodyMenuItem menuItem);

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

	/**
	 * @param jumpEvent
	 *            the {@link JumpToSourceLineEvent}
	 * @param enablePredicate
	 *            condition for enable this item
	 * @return show text view item
	 */
	TableBodyMenuItem createShowInTextViewItem(
			final JumpToSourceLineEvent jumpEvent,
			IntPredicate enablePredicate);

}
