/**
 * Copyright (c) 2015 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.table.abstracttableview;

import org.eclipse.nebula.widgets.nattable.NatTable;
import org.eclipse.nebula.widgets.nattable.data.IDataProvider;
import org.eclipse.nebula.widgets.nattable.grid.layer.GridLayer;
import org.eclipse.nebula.widgets.nattable.layer.AbstractLayer;
import org.eclipse.nebula.widgets.nattable.layer.DataLayer;
import org.eclipse.nebula.widgets.nattable.ui.matcher.KeyEventMatcher;
import org.eclipse.set.core.services.dialog.DialogService;
import org.eclipse.set.model.tablemodel.ColumnDescriptor;
import org.eclipse.set.nattable.utils.PlanProTableThemeConfiguration;
import org.eclipse.set.utils.ToolboxConfiguration;
import org.eclipse.swt.SWT;

/**
 * Toolbox theme configuration.
 * 
 * @author Schaefer
 */
public class ToolboxTableModelThemeConfiguration
		extends PlanProTableThemeConfiguration {
	private static final float SCALE_CM_TO_PIXEL = ToolboxConfiguration
			.getTablesScaleFactor();

	/**
	 * @param length
	 *            the length in cm
	 * 
	 * @return the length in pixel
	 */
	public static int toPixel(final float length) {
		return Math.round(length * SCALE_CM_TO_PIXEL);
	}

	/**
	 * @param natTable
	 *            the NAT-table
	 * @param headerLayer
	 *            the header layer
	 * @param dataLayer
	 *            the data layer
	 * @param gridLayer
	 *            the grid layer
	 * @param heading
	 *            the heading model
	 * @param bodyDataProvider
	 *            data provider
	 * @param bodyLayer
	 *            the body layer
	 * @param dialogService
	 *            the dialogService
	 */
	public ToolboxTableModelThemeConfiguration(final NatTable natTable,
			final AbstractLayer headerLayer, final DataLayer dataLayer,
			final GridLayer gridLayer, final ColumnDescriptor heading,
			final AbstractLayer bodyLayer, final IDataProvider bodyDataProvider,
			final DialogService dialogService) {
		super(natTable, headerLayer, dataLayer, gridLayer, heading, bodyLayer,
				bodyDataProvider);

		// register key bindings for table exports
		natTable.getUiBindingRegistry().registerKeyBinding(
				new KeyEventMatcher(SWT.MOD1, 'r'),
				new CsvExportAction(dialogService));
	}
}
