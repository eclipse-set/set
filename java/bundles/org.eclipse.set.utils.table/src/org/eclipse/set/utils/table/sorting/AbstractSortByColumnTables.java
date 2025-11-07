/**
 * Copyright (c) 2022 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */

package org.eclipse.set.utils.table.sorting;

import java.nio.file.Path;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.UnaryOperator;

import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.nebula.widgets.nattable.NatTable;
import org.eclipse.nebula.widgets.nattable.config.ConfigRegistry;
import org.eclipse.nebula.widgets.nattable.data.IDataProvider;
import org.eclipse.nebula.widgets.nattable.filterrow.FilterRowHeaderComposite;
import org.eclipse.nebula.widgets.nattable.filterrow.IFilterStrategy;
import org.eclipse.nebula.widgets.nattable.grid.data.DefaultColumnHeaderDataProvider;
import org.eclipse.nebula.widgets.nattable.grid.data.DefaultCornerDataProvider;
import org.eclipse.nebula.widgets.nattable.grid.data.DefaultRowHeaderDataProvider;
import org.eclipse.nebula.widgets.nattable.grid.layer.ColumnHeaderLayer;
import org.eclipse.nebula.widgets.nattable.grid.layer.CornerLayer;
import org.eclipse.nebula.widgets.nattable.grid.layer.GridLayer;
import org.eclipse.nebula.widgets.nattable.grid.layer.RowHeaderLayer;
import org.eclipse.nebula.widgets.nattable.group.model.RowGroupModel;
import org.eclipse.nebula.widgets.nattable.layer.DataLayer;
import org.eclipse.nebula.widgets.nattable.layer.cell.ILayerCell;
import org.eclipse.nebula.widgets.nattable.selection.SelectionLayer;
import org.eclipse.nebula.widgets.nattable.sort.SortDirectionEnum;
import org.eclipse.nebula.widgets.nattable.sort.SortHeaderLayer;
import org.eclipse.nebula.widgets.nattable.sort.command.SortColumnCommand;
import org.eclipse.nebula.widgets.nattable.sort.config.SingleClickSortConfiguration;
import org.eclipse.nebula.widgets.nattable.tree.command.TreeCollapseAllCommand;
import org.eclipse.nebula.widgets.nattable.ui.matcher.KeyEventMatcher;
import org.eclipse.set.basis.Pair;
import org.eclipse.set.basis.PlanProXMLNode;
import org.eclipse.set.core.services.Services;
import org.eclipse.set.core.services.configurationservice.UserConfigurationService;
import org.eclipse.set.core.services.dialog.DialogService;
import org.eclipse.set.model.tablemodel.ColumnDescriptor;
import org.eclipse.set.model.tablemodel.Table;
import org.eclipse.set.model.tablemodel.TableRow;
import org.eclipse.set.model.tablemodel.extensions.ColumnDescriptorExtensions;
import org.eclipse.set.model.validationreport.ObjectScope;
import org.eclipse.set.nattable.utils.PlanProTableThemeConfiguration;
import org.eclipse.set.utils.BasePart;
import org.eclipse.set.utils.events.JumpToSiteplanEvent;
import org.eclipse.set.utils.events.JumpToSourceLineEvent;
import org.eclipse.set.utils.table.BodyLayerStack;
import org.eclipse.set.utils.table.TableDataProvider;
import org.eclipse.set.utils.table.export.ExportToCSV;
import org.eclipse.set.utils.table.menu.TableBodyMenuConfiguration.TableBodyMenuItem;
import org.eclipse.set.utils.table.menu.TableMenuService;
import org.eclipse.set.utils.xml.XMLNodeFinder;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;

/**
 * Table with sorting function, when column header click
 * 
 * @author Truong
 *
 */
public abstract class AbstractSortByColumnTables {
	/**
	 * the table data provider
	 */
	public TableDataProvider bodyDataProvider;

	/**
	 * the table body layer
	 */
	public BodyLayerStack bodyLayerStack;

	protected DataLayer bodyDataLayer;
	protected RowGroupModel<TableRow> rowGroupModel;

	protected void createTableBodyData(final Table table,
			final UnaryOperator<Integer> getSourceLine) {
		this.bodyDataProvider = new TableDataProvider(table, getSourceLine);
		bodyDataLayer = new DataLayer(bodyDataProvider);
		this.bodyLayerStack = new BodyLayerStack(bodyDataLayer);
	}

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

	protected SelectionLayer getSelectionLayer() {
		return bodyLayerStack.getSelectionLayer();
	}

	protected NatTable createTable(final Composite parent,
			final Table tableModel) {
		final ColumnDescriptor rootColumnDescriptor = tableModel
				.getColumndescriptors()
				.get(0);
		if (bodyDataProvider == null || bodyLayerStack == null) {
			this.createTableBodyData(tableModel, null);
		}
		// column header stack
		final IDataProvider columnHeaderDataProvider = new DefaultColumnHeaderDataProvider(
				ColumnDescriptorExtensions
						.getColumnLabels(rootColumnDescriptor));

		final DataLayer columnHeaderDataLayer = new DataLayer(
				columnHeaderDataProvider);

		final ColumnHeaderLayer columnHeaderLayer = new ColumnHeaderLayer(
				columnHeaderDataLayer, bodyLayerStack, getSelectionLayer());
		final SortHeaderLayer<BodyLayerStack> sortHeaderLayer = creatSortColumnHeaderLayer(
				columnHeaderLayer);

		final ConfigRegistry configRegistry = new ConfigRegistry();
		final GridLayer gridLayer = createGridLayer(columnHeaderDataProvider,
				columnHeaderDataLayer, sortHeaderLayer, configRegistry);

		final NatTable natTable = createNattable(parent, rootColumnDescriptor,
				columnHeaderDataLayer, configRegistry, gridLayer);
		getSelectionLayer().clear();

		// Sort by first column (Ascending)
		natTable.doCommand(new SortColumnCommand(sortHeaderLayer, 0,
				SortDirectionEnum.ASC));

		natTable.doCommand(new TreeCollapseAllCommand());

		return natTable;
	}

	protected SortHeaderLayer<BodyLayerStack> creatSortColumnHeaderLayer(
			final ColumnHeaderLayer columnHeaderLayer) {
		// Sort Column Header
		return new SortHeaderLayer<>(columnHeaderLayer,
				new TableSortModel(bodyDataProvider), true);
	}

	private GridLayer createGridLayer(
			final IDataProvider columnHeaderDataProvider,
			final DataLayer columnHeaderDataLayer,
			final SortHeaderLayer<BodyLayerStack> sortHeaderLayer,
			final ConfigRegistry configRegistry) {
		// row header stack
		final IDataProvider rowHeaderDataProvider = new DefaultRowHeaderDataProvider(
				bodyDataProvider);
		final DataLayer rowHeaderDataLayer = new DataLayer(
				rowHeaderDataProvider, 0, 20);
		final RowHeaderLayer rowHeaderLayer = new RowHeaderLayer(
				rowHeaderDataLayer, bodyLayerStack, getSelectionLayer());

		final FilterRowHeaderComposite<Object> filterRowHeaderLayer = createFilterRowHeader(
				sortHeaderLayer, columnHeaderDataLayer, configRegistry);

		// Corner Layer stack
		final DefaultCornerDataProvider cornerDataProvider = new DefaultCornerDataProvider(
				columnHeaderDataProvider, rowHeaderDataProvider);
		final DataLayer cornerDataLayer = new DataLayer(cornerDataProvider);
		final CornerLayer cornerLayer = new CornerLayer(cornerDataLayer,
				rowHeaderLayer, filterRowHeaderLayer);

		// gridlayer
		return new GridLayer(bodyLayerStack, filterRowHeaderLayer,
				rowHeaderLayer, cornerLayer);
	}

	protected FilterRowHeaderComposite<Object> createFilterRowHeader(
			final SortHeaderLayer<BodyLayerStack> sortHeaderLayer,
			final DataLayer columnHeaderDataLayer,
			final ConfigRegistry configRegistry) {
		return new FilterRowHeaderComposite<>(
				new FilterStrategy<>(bodyDataProvider), sortHeaderLayer,
				columnHeaderDataLayer.getDataProvider(), configRegistry);
	}

	protected NatTable createNattable(final Composite parent,
			final ColumnDescriptor rootColumnDescriptor,
			final DataLayer columnHeaderDataLayer,
			final ConfigRegistry configRegistry, final GridLayer gridLayer) {
		final NatTable natTable = new NatTable(parent, SWT.NO_BACKGROUND
				| SWT.DOUBLE_BUFFERED | SWT.V_SCROLL | SWT.H_SCROLL, gridLayer,
				false);
		GridDataFactory.fillDefaults().grab(true, true).applyTo(natTable);
		natTable.setConfigRegistry(configRegistry);

		natTable.addConfiguration(new SingleClickSortConfiguration());
		if (getTableMenuService() != null) {
			natTable.addConfiguration(getTableMenuService()
					.createMenuConfiguration(natTable, getSelectionLayer()));
		}

		natTable.configure();

		// set style
		natTable.setTheme(new PlanProTableThemeConfiguration(natTable,
				columnHeaderDataLayer, bodyDataLayer, gridLayer,
				rootColumnDescriptor, bodyLayerStack, bodyDataProvider));
		// register key bindings for table exports
		natTable.getUiBindingRegistry()
				.registerKeyBinding(new KeyEventMatcher(SWT.MOD1, 'r'),
						(table, event) -> exportCsv());
		return natTable;
	}

	protected abstract TableMenuService getTableMenuService();

	protected abstract XMLNodeFinder getXMLNodeFinder();

	protected abstract String getCSVHeaderPattern();

	/**
	 * Export table data to csv file
	 */
	public abstract void exportCsv();

	protected void exportCsv(final Shell shell,
			final DialogService dialogService, final String dialogTilte,
			final String defaultFileName) {
		final UserConfigurationService userConfigurationService = Services
				.getUserConfigurationService();
		final Optional<Path> outPath = dialogService.saveFileDialog(shell,
				dialogService.getCsvFileFilters(),
				userConfigurationService.getLastExportPath()
						.resolve(defaultFileName),
				dialogTilte);

		final ExportToCSV<String> exportToCSV = new ExportToCSV<>(
				getCSVHeaderPattern());
		exportToCSV.exportToCSV(outPath, transformToCSV());
		outPath.ifPresent(out -> {
			dialogService.openDirectoryAfterExport(shell, out.getParent());
			userConfigurationService.setLastExportPath(out);
		});
	}

	protected TableBodyMenuItem createJumpToTextViewMenuItem(
			final BasePart part) {
		if (getTableMenuService() == null) {
			return null;
		}
		return getTableMenuService().createShowInTextViewItem(
				createJumpToTextViewEvent(part), getSelectionLayer(),
				selectedRowIndex -> selectedRowIndex >= 0
						&& getXmlLineNumber(selectedRowIndex).intValue() > 0);
	}

	protected JumpToSourceLineEvent createJumpToTextViewEvent(
			final BasePart part) {
		return new JumpToSourceLineEvent(part) {
			@Override
			public Pair<ObjectScope, Integer> getLineNumber() {
				final Collection<ILayerCell> selectedCells = getSelectionLayer()
						.getSelectedCells();
				if (selectedCells.isEmpty()) {
					return new Pair<>(null, Integer.valueOf(-1));
				}
				final int rowPosition = selectedCells.iterator()
						.next()
						.getRowPosition();
				final String objectScope = bodyDataProvider
						.getObjectScope(rowPosition);
				final ObjectScope scope = ObjectScope.get(objectScope);
				return new Pair<>(scope, getXmlLineNumber(rowPosition));
			}
		};
	}

	protected TableBodyMenuItem createJumpToSiteplanMenuItem() {
		if (getTableMenuService() == null) {
			return null;
		}
		return getTableMenuService().createShowInSitePlanItem(
				creataJumpToSiteplanEvent(), getSelectionLayer(), rowIndex -> {
					final Collection<ILayerCell> selectedCells = bodyLayerStack
							.getSelectionLayer()
							.getSelectedCells();
					if (selectedCells.isEmpty()) {
						return false;
					}
					final int rowPosition = selectedCells.iterator()
							.next()
							.getRowPosition();
					final String guid = getTableRowReferenceObjectGuid(
							rowPosition);
					if (guid == null || guid.isBlank()) {
						return false;
					}
					// When siteplan never opended before, then active the item
					return Services.getSiteplanService()
							.isSiteplanElement(guid);
				});
	}

	protected JumpToSiteplanEvent creataJumpToSiteplanEvent() {
		return new JumpToSiteplanEvent() {
			@Override
			public String getGuid() {
				final Collection<ILayerCell> selectedCells = bodyLayerStack
						.getSelectionLayer()
						.getSelectedCells();
				if (selectedCells.isEmpty()) {
					return null;
				}
				final int rowPosition = selectedCells.iterator()
						.next()
						.getRowPosition();
				return getTableRowReferenceObjectGuid(rowPosition);
			}
		};
	}

	protected Integer getXmlLineNumber(final int rowPosition) {
		final int originalRow = bodyDataProvider
				.getOriginalRowIndex(rowPosition);
		if (originalRow < 0) {
			return Integer.valueOf(originalRow);
		}
		return Integer
				.valueOf(bodyDataProvider.getObjectSourceLine(originalRow));
	}

	protected String getTableRowReferenceObjectGuid(final int rowPosition) {
		final Integer xmlLineNumber = getXmlLineNumber(rowPosition);
		final PlanProXMLNode nodeByLineNumber = getXMLNodeFinder()
				.findNodeByLineNumber(xmlLineNumber.intValue());
		return XMLNodeFinder.findNearestNodeGUID(nodeByLineNumber);
	}

	/**
	 * Transform table cotent to csv
	 * 
	 * @return table contents als csv content
	 */
	public List<String> transformToCSV() {
		return bodyDataProvider.transformToCSV();
	}

}
