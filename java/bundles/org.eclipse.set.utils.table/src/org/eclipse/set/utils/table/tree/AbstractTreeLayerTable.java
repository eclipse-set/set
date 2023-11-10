/**
 * Copyright (c) 2023 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */

package org.eclipse.set.utils.table.tree;

import java.util.List;
import java.util.Map;
import java.util.function.UnaryOperator;

import org.eclipse.nebula.widgets.nattable.command.ILayerCommand;
import org.eclipse.nebula.widgets.nattable.command.StructuralRefreshCommand;
import org.eclipse.nebula.widgets.nattable.config.ConfigRegistry;
import org.eclipse.nebula.widgets.nattable.filterrow.FilterRowHeaderComposite;
import org.eclipse.nebula.widgets.nattable.filterrow.IFilterStrategy;
import org.eclipse.nebula.widgets.nattable.grid.layer.ColumnHeaderLayer;
import org.eclipse.nebula.widgets.nattable.layer.DataLayer;
import org.eclipse.nebula.widgets.nattable.sort.SortHeaderLayer;
import org.eclipse.nebula.widgets.nattable.sort.command.SortColumnCommand;
import org.eclipse.nebula.widgets.nattable.tree.TreeLayer;
import org.eclipse.nebula.widgets.nattable.tree.command.TreeCollapseAllCommand;
import org.eclipse.nebula.widgets.nattable.tree.command.TreeExpandAllCommand;
import org.eclipse.nebula.widgets.nattable.tree.command.TreeExpandCollapseCommand;
import org.eclipse.nebula.widgets.nattable.tree.command.TreeExpandCollapseCommandHandler;
import org.eclipse.set.model.tablemodel.Table;
import org.eclipse.set.utils.table.BodyLayerStack;
import org.eclipse.set.utils.table.sorting.AbstractSortByColumnTables;
import org.eclipse.set.utils.table.sorting.TableSortModel;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

/**
 * Table with sorting, grouping, filertering function
 * 
 * @author Truong
 *
 */
public class AbstractTreeLayerTable extends AbstractSortByColumnTables {

	/**
	 * The table data provider
	 */
	protected TreeLayer treeLayer;

	@Override
	protected void createTableBodyData(final Table table,
			final UnaryOperator<Integer> getSourceLine) {
		bodyDataProvider = new TreeDataProvider(table, getSourceLine);
		bodyDataLayer = new DataLayer(bodyDataProvider);
		createTreeLayer();

		bodyLayerStack = new BodyLayerStack(bodyDataLayer, treeLayer);
	}

	/**
	 * Create treelayer
	 */
	private void createTreeLayer() {
		if (!(bodyDataProvider instanceof TreeDataProvider)) {
			return;
		}
		final TreeDataProvider treeData = (TreeDataProvider) bodyDataProvider;
		final TableTreeRowModel treeRowModel = new TableTreeRowModel(treeData);
		treeLayer = new TreeLayer(bodyDataLayer, treeRowModel) {
			@Override
			public boolean doCommand(final ILayerCommand command) {
				// Refresh hidden row index by data change
				if (command instanceof StructuralRefreshCommand) {
					treeLayer.doCommand(new TreeExpandAllCommand());
					treeLayer.doCommand(new TreeCollapseAllCommand());
					return true;
				}
				// Update hidden rows list in {@link TreeDataProvider}
				treeData.doExpandCollapseCommand(command);
				return super.doCommand(command);
			}
		};
		// Collapse all group by default
		treeLayer.doCommand(new TreeCollapseAllCommand());
	}

	@Override
	protected SortHeaderLayer<BodyLayerStack> creatSortColumnHeaderLayer(
			final ColumnHeaderLayer columnHeaderLayer) {
		final TableSortModel tableSortModel = new TableSortModel(
				bodyDataProvider);
		final SortHeaderLayer<BodyLayerStack> sortHeaderLayer = new SortHeaderLayer<>(
				columnHeaderLayer, tableSortModel, true);
		sortHeaderLayer.unregisterCommandHandler(SortColumnCommand.class);
		sortHeaderLayer.registerCommandHandler(new TreeSortCommandHandler(
				tableSortModel, sortHeaderLayer, treeLayer));
		return sortHeaderLayer;
	}

	class TreeFilterStrategy<T> implements IFilterStrategy<T> {

		private final TreeDataProvider treeDataProvider;
		private final TreeLayer layer;

		public TreeFilterStrategy(final TreeLayer treeLayer,
				final TreeDataProvider treeDataProvider) {
			this.layer = treeLayer;
			this.treeDataProvider = treeDataProvider;
		}

		@Override
		public void applyFilter(
				final Map<Integer, Object> filterIndexToObjectMap) {
			// The hidden rows list in {@link TreeDataProvider} will not be
			// change by direct call expland-collapse function
			layer.expandAll();
			treeDataProvider.applyFilter(filterIndexToObjectMap);
			treeLayer.doCommand(new TreeCollapseAllCommand());
		}

	}

	@Override
	protected FilterRowHeaderComposite<Object> createFilterRowHeader(
			final SortHeaderLayer<BodyLayerStack> sortHeaderLayer,
			final DataLayer columnHeaderDataLayer,
			final ConfigRegistry configRegistry) {
		if (bodyDataProvider instanceof final TreeDataProvider treeDataProvider) {
			return new FilterRowHeaderComposite<>(
					new TreeFilterStrategy<>(treeLayer, treeDataProvider),
					sortHeaderLayer, columnHeaderDataLayer.getDataProvider(),
					configRegistry);
		}
		return super.createFilterRowHeader(sortHeaderLayer,
				columnHeaderDataLayer, configRegistry);

	}

	/**
	 * Creat button to toogle expand and collapse all group
	 * 
	 * @param parent
	 *            the parent
	 * @param expandAllLabel
	 *            the label for expand all button
	 * @param collapseAllLabel
	 *            the label for collapse all button
	 * @return expand or collcapse all button
	 */
	public Button createExpandCollapseAllButton(final Composite parent,
			final String expandAllLabel, final String collapseAllLabel) {
		final Button button = new Button(parent, SWT.PUSH);
		button.setText(
				treeLayer.hasHiddenRows() ? expandAllLabel : collapseAllLabel);

		treeLayer.registerCommandHandler(
				new TreeExpandCollapseCommandHandler(treeLayer) {
					@Override
					protected boolean doCommand(
							final TreeExpandCollapseCommand command) {
						final boolean doCommand = super.doCommand(command);
						final String buttonText = treeLayer.hasHiddenRows()
								? expandAllLabel
								: collapseAllLabel;
						if (!button.getText().equals(buttonText)) {
							button.setText(buttonText);
						}
						return doCommand;
					}
				});
		button.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(final SelectionEvent e) {
				toggleExpandCollapseAll(button, expandAllLabel,
						collapseAllLabel);
			}

			@Override
			public void widgetDefaultSelected(final SelectionEvent e) {
				widgetSelected(e);
			}
		});
		return button;
	}

	private void toggleExpandCollapseAll(final Button button,
			final String expandAllLabel, final String collapseAllLabel) {
		if (button.getText().equals(expandAllLabel)) {
			treeLayer.doCommand(new TreeExpandAllCommand());
			button.setText(collapseAllLabel);
		} else {
			treeLayer.doCommand(new TreeCollapseAllCommand());
			button.setText(expandAllLabel);
		}
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
