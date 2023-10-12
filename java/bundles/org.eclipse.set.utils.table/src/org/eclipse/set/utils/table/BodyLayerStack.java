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

import org.eclipse.nebula.widgets.nattable.data.IDataProvider;
import org.eclipse.nebula.widgets.nattable.freeze.CompositeFreezeLayer;
import org.eclipse.nebula.widgets.nattable.freeze.FreezeHelper;
import org.eclipse.nebula.widgets.nattable.freeze.FreezeLayer;
import org.eclipse.nebula.widgets.nattable.freeze.command.FreezeColumnStrategy;
import org.eclipse.nebula.widgets.nattable.layer.AbstractLayerTransform;
import org.eclipse.nebula.widgets.nattable.layer.DataLayer;
import org.eclipse.nebula.widgets.nattable.selection.SelectionLayer;
import org.eclipse.nebula.widgets.nattable.selection.config.DefaultMoveSelectionConfiguration;
import org.eclipse.nebula.widgets.nattable.selection.config.DefaultRowSelectionLayerConfiguration;
import org.eclipse.nebula.widgets.nattable.tree.TreeLayer;
import org.eclipse.nebula.widgets.nattable.viewport.ViewportLayer;

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
		this.bodyDataLayer = bodyDataLayer;
		this.selectionLayer = new SelectionLayer(bodyDataLayer);
		init();
	}

	/**
	 * @param bodyDataLayer
	 *            the data layer
	 * @param treeLayer
	 *            the tree layer
	 */
	public BodyLayerStack(final DataLayer bodyDataLayer,
			final TreeLayer treeLayer) {
		this.bodyDataLayer = bodyDataLayer;
		this.selectionLayer = new SelectionLayer(treeLayer);
		init();
	}

	protected void init() {
		this.stackBodyDataProvider = bodyDataLayer.getDataProvider();
		this.selectionLayer
				.addConfiguration(new DefaultRowSelectionLayerConfiguration());
		this.selectionLayer
				.addConfiguration(new DefaultMoveSelectionConfiguration());

		this.viewportLayer = new ViewportLayer(this.selectionLayer);
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
}
