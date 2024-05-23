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
import java.util.Set;
import java.util.function.IntPredicate;

import org.eclipse.nebula.widgets.nattable.NatTable;
import org.eclipse.nebula.widgets.nattable.config.AbstractUiBindingConfiguration;
import org.eclipse.nebula.widgets.nattable.grid.GridRegion;
import org.eclipse.nebula.widgets.nattable.layer.cell.ILayerCell;
import org.eclipse.nebula.widgets.nattable.selection.SelectionLayer;
import org.eclipse.nebula.widgets.nattable.selection.command.SelectRowsCommand;
import org.eclipse.nebula.widgets.nattable.ui.NatEventData;
import org.eclipse.nebula.widgets.nattable.ui.binding.UiBindingRegistry;
import org.eclipse.nebula.widgets.nattable.ui.matcher.MouseEventMatcher;
import org.eclipse.nebula.widgets.nattable.ui.menu.IMenuItemProvider;
import org.eclipse.nebula.widgets.nattable.ui.menu.IMenuItemState;
import org.eclipse.nebula.widgets.nattable.ui.menu.PopupMenuAction;
import org.eclipse.nebula.widgets.nattable.ui.menu.PopupMenuBuilder;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;

/**
 * Create Nattable body menu
 * 
 * @author Truong
 *
 */
public class TableBodyMenuConfiguration extends AbstractUiBindingConfiguration {

	/**
	 * @param label
	 *            the item label
	 * @param selectionLayer
	 *            the selection layer
	 * @param selectionListener
	 *            the {@link SelectionListener}
	 * @param enablePredicate
	 *            the condition for enable this item
	 */
	public record TableBodyMenuItem(String label, SelectionLayer selectionLayer,
			SelectionListener selectionListener, IntPredicate enablePredicate)
			implements IMenuItemProvider, IMenuItemState {
		@Override
		public void addMenuItem(final NatTable natTable, final Menu popupMenu) {
			final MenuItem menuItem = new MenuItem(popupMenu, SWT.PUSH);
			menuItem.setText(label);
			menuItem.addSelectionListener(selectionListener);
		}

		@Override
		public boolean isActive(final NatEventData natEventData) {
			final Collection<ILayerCell> selectedCells = selectionLayer
					.getSelectedCells();
			if (selectedCells.isEmpty()) {
				return false;
			}
			return enablePredicate
					.test(selectedCells.iterator().next().getRowPosition());
		}

	}

	protected final Menu bodyMenu;

	/**
	 * Selection Layer
	 */
	private final SelectionLayer selectionLayer;

	/**
	 * Create table drop down menu when right click
	 * 
	 * @param natTable
	 *            The {@link NatTable} instance to register the body menu to.
	 * @param selectionLayer
	 *            The selection layer
	 * @param menuItems
	 *            The menu item
	 */
	public TableBodyMenuConfiguration(final NatTable natTable,
			final SelectionLayer selectionLayer,
			final Set<TableBodyMenuItem> menuItems) {
		this.selectionLayer = selectionLayer;
		this.bodyMenu = createBodyMenu(natTable, menuItems).build();
	}

	protected static PopupMenuBuilder createBodyMenu(final NatTable natTable,
			final Set<TableBodyMenuItem> menuItems) {
		final PopupMenuBuilder popupMenu = new PopupMenuBuilder(natTable);
		menuItems.forEach(
				item -> popupMenu.withMenuItemProvider(item.label, item)
						.withEnabledState(item.label, item));
		return popupMenu;
	}

	@Override
	public void configureUiBindings(final UiBindingRegistry uiBindingRegistry) {
		uiBindingRegistry.registerMouseDownBinding(
				new MouseEventMatcher(SWT.NONE, GridRegion.BODY,
						MouseEventMatcher.RIGHT_BUTTON),
				new PopupMenuAction(bodyMenu) {

					@Override
					public void run(final NatTable natTable,
							final MouseEvent event) {
						natTable.doCommand(new SelectRowsCommand(natTable,
								natTable.getColumnPositionByX(event.x),
								natTable.getRowPositionByY(event.y),
								(event.stateMask & SWT.MOD2) != 0,
								(event.stateMask & SWT.MOD1) != 0));
						if (selectionLayer.getSelectedCells().isEmpty()) {
							return;
						}
						super.run(natTable, event);
					}

				});
	}
}
