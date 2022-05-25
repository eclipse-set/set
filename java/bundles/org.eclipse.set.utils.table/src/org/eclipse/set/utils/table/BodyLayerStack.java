/**
 * Copyright (c) 2022 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */

package org.eclipse.set.utils.table;

import org.eclipse.nebula.widgets.nattable.data.IDataProvider;
import org.eclipse.nebula.widgets.nattable.freeze.CompositeFreezeLayer;
import org.eclipse.nebula.widgets.nattable.freeze.FreezeLayer;
import org.eclipse.nebula.widgets.nattable.layer.AbstractLayerTransform;
import org.eclipse.nebula.widgets.nattable.layer.DataLayer;
import org.eclipse.nebula.widgets.nattable.selection.SelectionLayer;
import org.eclipse.nebula.widgets.nattable.selection.config.DefaultMoveSelectionConfiguration;
import org.eclipse.nebula.widgets.nattable.selection.config.DefaultRowSelectionLayerConfiguration;
import org.eclipse.nebula.widgets.nattable.viewport.ViewportLayer;

/**
 * Always encapsulate the body layer stack in an AbstractLayerTransform to
 * ensure that the index transformations are performed in later commands.
 * 
 * @author rumpf
 */
public class BodyLayerStack extends AbstractLayerTransform {

	private final IDataProvider stackBodyDataProvider;

	private final SelectionLayer selectionLayer;
	private final ViewportLayer viewportLayer;

	/**
	 * @param bodyDataLayer
	 *            the data layer
	 */
	public BodyLayerStack(final DataLayer bodyDataLayer) {
		this.stackBodyDataProvider = bodyDataLayer.getDataProvider();
		this.selectionLayer = new SelectionLayer(bodyDataLayer);
		this.viewportLayer = new ViewportLayer(this.selectionLayer);

		this.selectionLayer
				.addConfiguration(new DefaultRowSelectionLayerConfiguration());
		this.selectionLayer
				.addConfiguration(new DefaultMoveSelectionConfiguration());

		final FreezeLayer freezeLayer = new FreezeLayer(this.selectionLayer);
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
}
