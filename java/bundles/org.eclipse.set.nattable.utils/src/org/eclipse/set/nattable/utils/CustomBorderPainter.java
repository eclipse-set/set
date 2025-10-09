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

import java.util.Set;

import org.eclipse.nebula.widgets.nattable.config.IConfigRegistry;
import org.eclipse.nebula.widgets.nattable.layer.cell.ILayerCell;
import org.eclipse.nebula.widgets.nattable.painter.cell.CellPainterWrapper;
import org.eclipse.nebula.widgets.nattable.painter.cell.ICellPainter;
import org.eclipse.set.utils.export.xsl.XSLConstant.TableAttribute.BorderDirection;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Rectangle;

/**
 * 
 */
public class CustomBorderPainter extends CellPainterWrapper {
	private final Color borderColor;
	private final int borderThickness;

	private final Set<BorderDirection> directions;

	/**
	 * @param painter
	 *            the {@link ICellPainter}
	 * @param borderColor
	 *            the borderColor
	 * @param borderThickness
	 *            the border width
	 * @param directions
	 *            the border direction
	 */
	public CustomBorderPainter(final ICellPainter painter,
			final Color borderColor, final int borderThickness,
			final Set<BorderDirection> directions) {
		super(painter);
		this.borderColor = borderColor;
		this.borderThickness = borderThickness;
		this.directions = directions;
	}

	@Override
	public void paintCell(final ILayerCell cell, final GC gc,
			final Rectangle bounds, final IConfigRegistry configRegistry) {
		super.paintCell(cell, gc, bounds, configRegistry);

		gc.setForeground(borderColor);
		gc.setLineWidth(borderThickness);
		final int maxX = bounds.x + bounds.width;
		// -1 for distance between two row
		final int maxY = bounds.y + bounds.height - 1;
		directions.forEach(direction -> {
			switch (direction) {
				case BOTTOM: {
					gc.drawLine(bounds.x, maxY, maxX, maxY);
					break;
				}
				case TOP: {
					gc.drawLine(bounds.x, bounds.y, maxX, bounds.y);
					break;
				}
				case LEFT: {
					gc.drawLine(bounds.x, bounds.y, bounds.x, maxY);
					break;
				}
				case RIGHT: {
					gc.drawLine(maxX, bounds.y, maxX, maxY);
					break;
				}
				default:
					break;

			}
		});
	}
}
