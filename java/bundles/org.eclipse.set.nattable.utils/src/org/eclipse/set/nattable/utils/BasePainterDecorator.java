/**
 * Copyright (c) 2017 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.nattable.utils;

import org.eclipse.nebula.widgets.nattable.config.IConfigRegistry;
import org.eclipse.nebula.widgets.nattable.layer.cell.ILayerCell;
import org.eclipse.nebula.widgets.nattable.painter.cell.ICellPainter;
import org.eclipse.nebula.widgets.nattable.painter.cell.decorator.CellPainterDecorator;
import org.eclipse.nebula.widgets.nattable.ui.util.CellEdgeEnum;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Rectangle;

/**
 * Unlike the {@link CellPainterDecorator} which will place decorations at the
 * edges of the cells, this decorator places decorations next to the base
 * painting.
 * 
 * @author Schaefer
 */
public class BasePainterDecorator implements ICellPainter {

	private static class InternalCellPainterDecorator
			extends CellPainterDecorator {

		public InternalCellPainterDecorator(final ICellPainter baseCellPainter,
				final CellEdgeEnum cellEdge,
				final ICellPainter decoratorCellPainter) {
			super(baseCellPainter, cellEdge, decoratorCellPainter);
		}

		@Override
		public Rectangle getBaseCellPainterBounds(final ILayerCell cell,
				final GC gc, final Rectangle adjustedCellBounds,
				final IConfigRegistry configRegistry) {
			final ICellPainter decoratorCellPainter = getDecoratorCellPainter();

			final int preferredDecoratorWidth = decoratorCellPainter != null
					? decoratorCellPainter.getPreferredWidth(cell, gc,
							configRegistry)
					: 0;
			final int grabbedPreferredWidth = adjustedCellBounds.width
					- preferredDecoratorWidth - getSpacing();

			switch (getCellEdge()) {
			case LEFT:
				return new Rectangle(adjustedCellBounds.x, adjustedCellBounds.y,
						grabbedPreferredWidth, adjustedCellBounds.height)
								.intersection(adjustedCellBounds);

			default:
				return super.getBaseCellPainterBounds(cell, gc,
						adjustedCellBounds, configRegistry);
			}
		}

		@Override
		public Rectangle getDecoratorCellPainterBounds(final ILayerCell cell,
				final GC gc, final Rectangle adjustedCellBounds,
				final IConfigRegistry configRegistry) {

			final ICellPainter decoratorCellPainter = getDecoratorCellPainter();
			final ICellPainter baseCellPainter = getBaseCellPainter();

			final int preferredDecoratorWidth = decoratorCellPainter != null
					? decoratorCellPainter.getPreferredWidth(cell, gc,
							configRegistry)
					: 0;
			final int preferredDecoratorHeight = decoratorCellPainter != null
					? decoratorCellPainter.getPreferredHeight(cell, gc,
							configRegistry)
					: 0;

			final int preferredBaseHeight = baseCellPainter != null
					? baseCellPainter.getPreferredHeight(cell, gc,
							configRegistry)
					: 0;

			switch (getCellEdge()) {
			case LEFT:
				return new Rectangle(adjustedCellBounds.x, adjustedCellBounds.y,
						preferredDecoratorWidth, preferredDecoratorHeight)
								.intersection(adjustedCellBounds);

			case RIGHT:
				return new Rectangle(adjustedCellBounds.x,
						adjustedCellBounds.y + preferredBaseHeight
								- preferredDecoratorHeight,
						preferredDecoratorWidth, preferredDecoratorHeight)
								.intersection(adjustedCellBounds);

			default:
				return super.getDecoratorCellPainterBounds(cell, gc,
						adjustedCellBounds, configRegistry);
			}
		}
	}

	private static CellEdgeEnum getCellEdge(final BaseEdgeEnum position) {
		switch (position) {
		case AFTER:
			return CellEdgeEnum.RIGHT;
		case BEFORE:
			return CellEdgeEnum.LEFT;
		default:
			return CellEdgeEnum.NONE;
		}
	}

	private final InternalCellPainterDecorator internalCellPainterDecorator;

	/**
	 * @param baseCellPainter
	 *            the base cell painter
	 * @param position
	 *            the relative position
	 * @param decoratorCellPainter
	 *            the decorator cell painter
	 */
	public BasePainterDecorator(final ICellPainter baseCellPainter,
			final BaseEdgeEnum position,
			final ICellPainter decoratorCellPainter) {
		internalCellPainterDecorator = new InternalCellPainterDecorator(
				baseCellPainter, getCellEdge(position), decoratorCellPainter);
	}

	@Override
	public ICellPainter getCellPainterAt(final int x, final int y,
			final ILayerCell cell, final GC gc,
			final Rectangle adjustedCellBounds,
			final IConfigRegistry configRegistry) {
		return internalCellPainterDecorator.getCellPainterAt(x, y, cell, gc,
				adjustedCellBounds, configRegistry);
	}

	@Override
	public int getPreferredHeight(final ILayerCell cell, final GC gc,
			final IConfigRegistry configRegistry) {
		return internalCellPainterDecorator.getPreferredHeight(cell, gc,
				configRegistry);
	}

	@Override
	public int getPreferredWidth(final ILayerCell cell, final GC gc,
			final IConfigRegistry configRegistry) {
		return internalCellPainterDecorator.getPreferredWidth(cell, gc,
				configRegistry);
	}

	@Override
	public void paintCell(final ILayerCell cell, final GC gc,
			final Rectangle bounds, final IConfigRegistry configRegistry) {
		internalCellPainterDecorator.paintCell(cell, gc, bounds,
				configRegistry);
	}
}
