/**
 * Copyright (c) 2022 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */

package org.eclipse.set.sessionservice;

import java.util.Set;

import jakarta.inject.Inject;

import org.eclipse.e4.core.services.nls.Translation;
import org.eclipse.nebula.widgets.nattable.data.IDataProvider;
import org.eclipse.nebula.widgets.nattable.selection.SelectionLayer;
import org.eclipse.nebula.widgets.nattable.ui.menu.IMenuItemProvider;
import org.eclipse.set.model.tablemodel.Table;
import org.eclipse.set.utils.BasePart;
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
		createDefaultMenuItems(messages.SetTableMenuService_TextView, part,
				table, dataProvider, selectionLayer);
	}
}
