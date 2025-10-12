/**
 * Copyright (c) 2022 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */

package org.eclipse.set.utils.table;

import java.util.Collections;
import java.util.Set;
import java.util.function.Function;

import org.eclipse.nebula.widgets.nattable.command.ILayerCommand;
import org.eclipse.nebula.widgets.nattable.command.ILayerCommandHandler;
import org.eclipse.nebula.widgets.nattable.config.CellConfigAttributes;
import org.eclipse.nebula.widgets.nattable.config.IConfigRegistry;
import org.eclipse.nebula.widgets.nattable.data.IDataProvider;
import org.eclipse.nebula.widgets.nattable.data.convert.ContextualDisplayConverter;
import org.eclipse.nebula.widgets.nattable.data.convert.DefaultDisplayConverter;
import org.eclipse.nebula.widgets.nattable.freeze.CompositeFreezeLayer;
import org.eclipse.nebula.widgets.nattable.freeze.FreezeHelper;
import org.eclipse.nebula.widgets.nattable.freeze.FreezeLayer;
import org.eclipse.nebula.widgets.nattable.freeze.command.FreezeColumnStrategy;
import org.eclipse.nebula.widgets.nattable.group.command.ViewportSelectColumnGroupCommand;
import org.eclipse.nebula.widgets.nattable.group.command.ViewportSelectColumnGroupCommandHandler;
import org.eclipse.nebula.widgets.nattable.layer.AbstractLayerTransform;
import org.eclipse.nebula.widgets.nattable.layer.DataLayer;
import org.eclipse.nebula.widgets.nattable.layer.IUniqueIndexLayer;
import org.eclipse.nebula.widgets.nattable.layer.cell.ILayerCell;
import org.eclipse.nebula.widgets.nattable.search.config.DefaultSearchBindings;
import org.eclipse.nebula.widgets.nattable.selection.SelectionLayer;
import org.eclipse.nebula.widgets.nattable.selection.config.DefaultMoveSelectionConfiguration;
import org.eclipse.nebula.widgets.nattable.selection.config.DefaultSelectionLayerConfiguration;
import org.eclipse.nebula.widgets.nattable.selection.config.RowOnlySelectionBindings;
import org.eclipse.nebula.widgets.nattable.selection.config.RowOnlySelectionConfiguration;
import org.eclipse.nebula.widgets.nattable.style.DisplayMode;
import org.eclipse.nebula.widgets.nattable.tree.TreeLayer;
import org.eclipse.nebula.widgets.nattable.viewport.ViewportLayer;
import org.eclipse.set.basis.constants.ToolboxConstants;

/**
 * Always encapsulate the body layer stack in an AbstractLayerTransform to
 * ensure that the index transformations are performed in later commands.
 * 
 * @author rumpf
 */
public class BodyLayerStack extends AbstractLayerTransform {

	private IDataProvider stackBodyDataProvider;

	protected SelectionLayer selectionLayer;
	private ViewportLayer viewportLayer;

	private FreezeLayer freezeLayer;

	protected DataLayer bodyDataLayer;

	/**
	 * @param bodyDataLayer
	 *            the data layer
	 */
	public BodyLayerStack(final DataLayer bodyDataLayer) {
		this(bodyDataLayer, createSelectionLayer(bodyDataLayer));
	}

	/**
	 * @param bodyDataLayer
	 *            the data layer
	 * @param treeLayer
	 *            the tree layer
	 */
	public BodyLayerStack(final DataLayer bodyDataLayer,
			final TreeLayer treeLayer) {
		this(bodyDataLayer, createSelectionLayer(treeLayer));
	}

	/**
	 * @param bodyDataLayer
	 *            the data layer
	 * @param selectionLayer
	 *            the selection layer
	 */
	public BodyLayerStack(final DataLayer bodyDataLayer,
			final SelectionLayer selectionLayer) {
		this.bodyDataLayer = bodyDataLayer;
		this.selectionLayer = selectionLayer;
		init();
	}

	private static SelectionLayer createSelectionLayer(
			final IUniqueIndexLayer indexLayer) {
		final SelectionLayer result = new SelectionLayer(indexLayer, false);
		result.addConfiguration(new RowOnlySelectionBindings());
		result.addConfiguration(new RowOnlySelectionConfiguration());
		result.addConfiguration(new DefaultMoveSelectionConfiguration());
		result.addConfiguration(new DefaultSelectionLayerConfiguration() {
			// By default disable search feature by table
			@Override
			protected void addSearchUIBindings() {
				// do nothing
			}
		});
		return result;
	}

	protected void init() {
		this.stackBodyDataProvider = bodyDataLayer.getDataProvider();
		this.viewportLayer = createViewportLayer();
		freezeLayer = new FreezeLayer(this.selectionLayer);
		final CompositeFreezeLayer compositeFreezeLayer = new CompositeFreezeLayer(
				freezeLayer, viewportLayer, this.selectionLayer);

		setUnderlyingLayer(compositeFreezeLayer);
	}

	/**
	 * @return the data provider
	 */
	public IDataProvider getBodyDataProvider() {
		return this.stackBodyDataProvider;
	}

	/**
	 * @return the selection layer
	 */
	public SelectionLayer getSelectionLayer() {
		return this.selectionLayer;
	}

	/**
	 * @return the viewport layer
	 */
	public ViewportLayer getViewportLayer() {
		return viewportLayer;
	}

	/**
	 * @return the freeze layer
	 */
	public FreezeLayer getFreezeLayer() {
		return freezeLayer;
	}

	/**
	 * Freeze column
	 * 
	 * @param columnPos
	 *            column position
	 */
	public void freezeColumn(final int columnPos) {
		final FreezeColumnStrategy freezeColumnStrategy = new FreezeColumnStrategy(
				freezeLayer, viewportLayer, columnPos);
		FreezeHelper.freeze(freezeLayer, viewportLayer,
				freezeColumnStrategy.getTopLeftPosition(),
				freezeColumnStrategy.getBottomRightPosition());
	}

	/**
	 * Fixed columns
	 * 
	 * @param columnsPos
	 *            position of fixed columns
	 */
	public void freezeColumns(final Set<Integer> columnsPos) {
		if (columnsPos.isEmpty()) {
			freezeColumn(0);
			return;
		}
		final Integer maxValue = Collections.max(columnsPos);
		final Integer minValue = Collections.min(columnsPos);
		final FreezeColumnStrategy firstColumntoFreeze = new FreezeColumnStrategy(
				freezeLayer, viewportLayer, minValue.intValue());
		final FreezeColumnStrategy lastColumntoFreeze = new FreezeColumnStrategy(
				freezeLayer, viewportLayer, maxValue.intValue());
		FreezeHelper.freeze(freezeLayer, viewportLayer,
				firstColumntoFreeze.getTopLeftPosition(),
				lastColumntoFreeze.getBottomRightPosition());
	}

	/**
	 * Add search configuration to selection layer
	 * 
	 * @param registry
	 *            the {@link IConfigRegistry}
	 * @param getCellValue
	 *            the get cell value function
	 */
	public void addSearchConfiguration(final IConfigRegistry registry,
			final Function<ILayerCell, String> getCellValue) {
		this.selectionLayer.addConfiguration(new DefaultSearchBindings());
		// Give text cell plain string value back instead of rich text value
		final ContextualDisplayConverter displayConverter = new ContextualDisplayConverter() {
			@Override
			public Object displayToCanonicalValue(final ILayerCell cell,
					final IConfigRegistry configRegistry,
					final Object displayValue) {
				// Only apply this converted for the selectionlayer
				if (cell.getLayer() != selectionLayer) {
					return new DefaultDisplayConverter()
							.displayToCanonicalValue(cell, configRegistry,
									displayValue);
				}
				return getCellValue.apply(cell);
			}

			@Override
			public Object canonicalToDisplayValue(final ILayerCell cell,
					final IConfigRegistry configRegistry,
					final Object canonicalValue) {
				// Only apply this converted for the selectionlayer
				if (cell.getLayer() != selectionLayer) {
					return new DefaultDisplayConverter()
							.canonicalToDisplayValue(cell, configRegistry,
									canonicalValue);
				}
				return displayToCanonicalValue(cell, configRegistry,
						canonicalValue);
			}
		};
		registry.registerConfigAttribute(CellConfigAttributes.DISPLAY_CONVERTER,
				displayConverter, DisplayMode.NORMAL,
				ToolboxConstants.SEARCH_CELL_DISPLAY_CONVERTER);
	}

	protected ViewportLayer createViewportLayer() {
		return new ViewportLayer(selectionLayer) {
			private final ILayerCommandHandler<ViewportSelectColumnGroupCommand> commandHandler = new ViewportSelectColumnGroupCommandHandler(
					this);

			/**
			 * Use {@link ViewportSelectColumnGroupCommand} on the
			 * {@link SelectionLayer} instead of {@link ViewportLayer}
			 * 
			 * Reason: The {@link ViewportLayer} computes and interprets
			 * "relative" positions. When a column group mixed fixed (fixed
			 * columns are handled by the {@link FreezeLayer} and scrollable
			 * columns, the {@link ViewportLayer} can't reliably determine the
			 * group's origin (absolute) column position from a
			 * viewport-relative click on a scrollable column. The mapping
			 * beween viewport positions and absolute column position is
			 * disrupted by the presence of the fixed layer . The
			 * {@link SelectionLayer} works with absolute columns positions and
			 * can there fore select the whole group correctly.
			 */
			@Override
			public boolean doCommand(final ILayerCommand command) {
				if (command instanceof final ViewportSelectColumnGroupCommand selectGroupCommand) {
					return commandHandler.doCommand(selectionLayer,
							selectGroupCommand);
				}
				return super.doCommand(command);
			}
		};
	}
}
