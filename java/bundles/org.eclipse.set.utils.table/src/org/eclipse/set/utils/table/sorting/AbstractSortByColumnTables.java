/**
 * Copyright (c) 2022 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */

package org.eclipse.set.utils.table.sorting;

import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.nebula.widgets.nattable.NatTable;
import org.eclipse.nebula.widgets.nattable.config.ConfigRegistry;
import org.eclipse.nebula.widgets.nattable.config.DefaultNatTableStyleConfiguration;
import org.eclipse.nebula.widgets.nattable.data.IDataProvider;
import org.eclipse.nebula.widgets.nattable.freeze.command.FreezeColumnCommand;
import org.eclipse.nebula.widgets.nattable.grid.data.DefaultColumnHeaderDataProvider;
import org.eclipse.nebula.widgets.nattable.grid.data.DefaultCornerDataProvider;
import org.eclipse.nebula.widgets.nattable.grid.data.DefaultRowHeaderDataProvider;
import org.eclipse.nebula.widgets.nattable.grid.layer.ColumnHeaderLayer;
import org.eclipse.nebula.widgets.nattable.grid.layer.CornerLayer;
import org.eclipse.nebula.widgets.nattable.grid.layer.GridLayer;
import org.eclipse.nebula.widgets.nattable.grid.layer.RowHeaderLayer;
import org.eclipse.nebula.widgets.nattable.layer.DataLayer;
import org.eclipse.nebula.widgets.nattable.layer.ILayerListener;
import org.eclipse.nebula.widgets.nattable.layer.event.ILayerEvent;
import org.eclipse.nebula.widgets.nattable.sort.SortHeaderLayer;
import org.eclipse.nebula.widgets.nattable.sort.config.SingleClickSortConfiguration;
import org.eclipse.nebula.widgets.nattable.sort.event.SortColumnEvent;
import org.eclipse.set.model.tablemodel.ColumnDescriptor;
import org.eclipse.set.model.tablemodel.Table;
import org.eclipse.set.model.tablemodel.extensions.ColumnDescriptorExtensions;
import org.eclipse.set.nattable.utils.PlanProTableThemeConfiguration;
import org.eclipse.set.utils.table.BodyLayerStack;
import org.eclipse.set.utils.table.TableDataProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

/**
 * Table with sorting function, when column header click
 * 
 * @author Truong
 *
 */
public class AbstractSortByColumnTables {

	protected TableDataProvider bodyDataProvider;
	protected BodyLayerStack bodyLayerStack;

	protected NatTable createTable(final Composite parent,
			final Table tableModel) {
		final ColumnDescriptor rootColumnDescriptor = tableModel
				.getColumndescriptors().get(0);
		bodyDataProvider = new TableDataProvider(tableModel);

		final DataLayer bodyDataLayer = new DataLayer(bodyDataProvider);
		bodyLayerStack = new BodyLayerStack(bodyDataLayer);

		// column header stack
		final IDataProvider columnHeaderDataProvider = new DefaultColumnHeaderDataProvider(
				ColumnDescriptorExtensions
						.getColumnLabels(rootColumnDescriptor));
		final DataLayer columnHeaderDataLayer = new DataLayer(
				columnHeaderDataProvider);
		final ColumnHeaderLayer columnHeaderLayer = new ColumnHeaderLayer(
				columnHeaderDataLayer, bodyLayerStack,
				bodyLayerStack.getSelectionLayer());
		final SortHeaderLayer<BodyLayerStack> sortHeaderLayer = new SortHeaderLayer<>(
				columnHeaderLayer,
				new TableSortModel(tableModel.getTablecontent().getRowgroups(),
						tableModel.getColumndescriptors().size()),
				true);
		sortHeaderLayer.addLayerListener(new ILayerListener() {
			@Override
			public void handleLayerEvent(final ILayerEvent event) {
				if (event instanceof SortColumnEvent) {
					bodyDataProvider.refresh(tableModel);
				}
			}
		});

		// row header stack
		final IDataProvider rowHeaderDataProvider = new DefaultRowHeaderDataProvider(
				bodyDataProvider);
		final DataLayer rowHeaderDataLayer = new DataLayer(
				rowHeaderDataProvider, 0, 20);
		final RowHeaderLayer rowHeaderLayer = new RowHeaderLayer(
				rowHeaderDataLayer, bodyLayerStack,
				bodyLayerStack.getSelectionLayer());

		// Corner Layer stack
		final DefaultCornerDataProvider cornerDataProvider = new DefaultCornerDataProvider(
				columnHeaderDataProvider, rowHeaderDataProvider);
		final DataLayer cornerDataLayer = new DataLayer(cornerDataProvider);
		final CornerLayer cornerLayer = new CornerLayer(cornerDataLayer,
				rowHeaderLayer, columnHeaderLayer);

		// gridlayer
		final GridLayer gridLayer = new GridLayer(bodyLayerStack,
				sortHeaderLayer, rowHeaderLayer, cornerLayer);

		final NatTable natTable = new NatTable(parent, SWT.NO_BACKGROUND
				| SWT.DOUBLE_BUFFERED | SWT.V_SCROLL | SWT.H_SCROLL, gridLayer,
				false);
		GridDataFactory.fillDefaults().grab(true, true).applyTo(natTable);

		natTable.setConfigRegistry(new ConfigRegistry());
		natTable.addConfiguration(new DefaultNatTableStyleConfiguration());
		natTable.addConfiguration(new SingleClickSortConfiguration());
		natTable.configure();

		// set style
		natTable.setTheme(new PlanProTableThemeConfiguration(natTable,
				columnHeaderDataLayer, bodyDataLayer, gridLayer,
				rootColumnDescriptor, bodyLayerStack, bodyDataProvider));

		natTable.doCommand(new FreezeColumnCommand(bodyLayerStack, 0));
		bodyLayerStack.getSelectionLayer().clear();

		return natTable;
	}
}
