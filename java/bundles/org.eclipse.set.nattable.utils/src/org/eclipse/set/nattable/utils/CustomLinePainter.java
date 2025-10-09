/**
 * Copyright (c) 2025 DB InfraGO AG and others
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 */
package org.eclipse.set.nattable.utils;

import java.util.function.Function;

import org.eclipse.nebula.widgets.nattable.config.IConfigRegistry;
import org.eclipse.nebula.widgets.nattable.layer.cell.ILayerCell;
import org.eclipse.nebula.widgets.nattable.painter.cell.CellPainterWrapper;
import org.eclipse.nebula.widgets.nattable.painter.cell.ICellPainter;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Rectangle;

/**
 * 
 */
public class CustomLinePainter extends CellPainterWrapper {
	Function<Rectangle, Integer[]> getStartPositionFunc;
	Function<Rectangle, Integer[]> getEndPositionFunc;
	Color lineColor;
	int lineWidth;

	/**
	 * @param painter
	 *            the painter
	 * @param startPositionFunc
	 *            the function to get start position
	 * @param endPositionFunc
	 *            the function to get end position
	 * @param color
	 *            the line color
	 * @param lineWidth
	 *            the line width
	 */
	public CustomLinePainter(final ICellPainter painter,
			final Function<Rectangle, Integer[]> startPositionFunc,
			final Function<Rectangle, Integer[]> endPositionFunc,
			final Color color, final int lineWidth) {
		super(painter);
		getStartPositionFunc = startPositionFunc;
		getEndPositionFunc = endPositionFunc;
		this.lineColor = color;
		this.lineWidth = lineWidth;
	}

	@Override
	public void paintCell(final ILayerCell cell, final GC gc,
			final Rectangle bounds, final IConfigRegistry configRegistry) {
		super.paintCell(cell, gc, bounds, configRegistry);
		gc.setForeground(lineColor);
		gc.setLineWidth(lineWidth);

		final Integer[] startPosistion = getStartPositionFunc.apply(bounds);
		final Integer[] endPosition = getEndPositionFunc.apply(bounds);
		gc.drawLine(startPosistion[0].intValue(), startPosistion[1].intValue(),
				endPosition[0].intValue(), endPosition[1].intValue());

	}
}
