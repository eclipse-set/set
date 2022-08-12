/**
 * Copyright (c) 2022 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */

package org.eclipse.set.utils.table.sorting;

import java.util.Map;

import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.nebula.widgets.nattable.NatTable;
import org.eclipse.nebula.widgets.nattable.config.CellConfigAttributes;
import org.eclipse.nebula.widgets.nattable.config.ConfigRegistry;
import org.eclipse.nebula.widgets.nattable.config.DefaultNatTableStyleConfiguration;
import org.eclipse.nebula.widgets.nattable.data.IDataProvider;
import org.eclipse.nebula.widgets.nattable.filterrow.FilterIconPainter;
import org.eclipse.nebula.widgets.nattable.filterrow.FilterRowHeaderComposite;
import org.eclipse.nebula.widgets.nattable.filterrow.FilterRowPainter;
import org.eclipse.nebula.widgets.nattable.filterrow.IFilterStrategy;
import org.eclipse.nebula.widgets.nattable.filterrow.config.DefaultFilterRowConfiguration;
import org.eclipse.nebula.widgets.nattable.freeze.command.FreezeColumnCommand;
import org.eclipse.nebula.widgets.nattable.grid.GridRegion;
import org.eclipse.nebula.widgets.nattable.grid.data.DefaultColumnHeaderDataProvider;
import org.eclipse.nebula.widgets.nattable.grid.data.DefaultCornerDataProvider;
import org.eclipse.nebula.widgets.nattable.grid.data.DefaultRowHeaderDataProvider;
import org.eclipse.nebula.widgets.nattable.grid.layer.ColumnHeaderLayer;
import org.eclipse.nebula.widgets.nattable.grid.layer.CornerLayer;
import org.eclipse.nebula.widgets.nattable.grid.layer.GridLayer;
import org.eclipse.nebula.widgets.nattable.grid.layer.RowHeaderLayer;
import org.eclipse.nebula.widgets.nattable.layer.DataLayer;
import org.eclipse.nebula.widgets.nattable.painter.cell.decorator.PaddingDecorator;
import org.eclipse.nebula.widgets.nattable.sort.SortDirectionEnum;
import org.eclipse.nebula.widgets.nattable.sort.SortHeaderLayer;
import org.eclipse.nebula.widgets.nattable.sort.command.SortColumnCommand;
import org.eclipse.nebula.widgets.nattable.sort.config.SingleClickSortConfiguration;
import org.eclipse.nebula.widgets.nattable.style.DisplayMode;
import org.eclipse.nebula.widgets.nattable.util.GUIHelper;
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

	class FilterStrategy<T> implements IFilterStrategy<T> {
		private final TableDataProvider tableDataProvider;

		public FilterStrategy(final TableDataProvider tableDataProvider) {
			this.tableDataProvider = tableDataProvider;
		}

		@Override
		public void applyFilter(
				final Map<Integer, Object> filterIndexToObjectMap) {
			tableDataProvider.applyFilter(filterIndexToObjectMap);
		}

	}

	class FilterRowCustomConfiguration extends DefaultFilterRowConfiguration {
		public FilterRowCustomConfiguration() {
			this.cellPainter = new FilterRowPainter(
					new FilterIconPainter(GUIHelper.getImage("filter"))); //$NON-NLS-1$
		}
	}

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
				columnHeaderLayer, new TableSortModel(bodyDataProvider), true);

		final ConfigRegistry configRegistry = new ConfigRegistry();
		configRegistry.registerConfigAttribute(
				CellConfigAttributes.CELL_PAINTER,
				new PaddingDecorator(
						new FilterRowPainter(new FilterIconPainter(
								GUIHelper.getImage("remove-filter"))), //$NON-NLS-1$
						0, 0, 0, 5),
				DisplayMode.NORMAL, GridRegion.FILTER_ROW);
		final FilterRowHeaderComposite<Object> filterRowHeaderLayer = new FilterRowHeaderComposite<>(
				new FilterStrategy<>(bodyDataProvider), sortHeaderLayer,
				columnHeaderDataLayer.getDataProvider(), configRegistry);

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
				rowHeaderLayer, filterRowHeaderLayer);

		// gridlayer
		final GridLayer gridLayer = new GridLayer(bodyLayerStack,
				filterRowHeaderLayer, rowHeaderLayer, cornerLayer);

		final NatTable natTable = new NatTable(parent, SWT.NO_BACKGROUND
				| SWT.DOUBLE_BUFFERED | SWT.V_SCROLL | SWT.H_SCROLL, gridLayer,
				false);
		GridDataFactory.fillDefaults().grab(true, true).applyTo(natTable);

		natTable.setConfigRegistry(configRegistry);
		natTable.addConfiguration(new DefaultNatTableStyleConfiguration());
		natTable.addConfiguration(new SingleClickSortConfiguration());
		natTable.addConfiguration(new FilterRowCustomConfiguration());
		natTable.configure();

		// set style
		natTable.setTheme(new PlanProTableThemeConfiguration(natTable,
				columnHeaderDataLayer, bodyDataLayer, gridLayer,
				rootColumnDescriptor, bodyLayerStack, bodyDataProvider));

		natTable.doCommand(new FreezeColumnCommand(bodyLayerStack, 0));
		bodyLayerStack.getSelectionLayer().clear();

		// Sort by first column (Ascending)
		natTable.doCommand(new SortColumnCommand(sortHeaderLayer, 0,
				SortDirectionEnum.ASC));

		return natTable;
	}
}
