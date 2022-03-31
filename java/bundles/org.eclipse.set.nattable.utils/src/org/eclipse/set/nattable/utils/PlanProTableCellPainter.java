/**
 * Copyright (c) 2017 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.nattable.utils;

import org.eclipse.nebula.widgets.nattable.painter.cell.BackgroundPainter;
import org.eclipse.nebula.widgets.nattable.painter.cell.ICellPainter;
import org.eclipse.nebula.widgets.nattable.painter.cell.decorator.PaddingDecorator;
import org.eclipse.swt.graphics.Image;

import org.eclipse.set.model.tablemodel.extensions.CellContentExtensions;

/**
 * Painter used to draw PlanPro table cells.
 * 
 * @author Schaefer
 */
public class PlanProTableCellPainter extends BackgroundPainter {

	private static final int BOTTOM_PADDING = 0;
	private static final int LEFT_PADDING = 5;
	private static final int RIGHT_PADDING = 5;
	private static final int TOP_PADDING = 0;

	/**
	 * @param interiorPainter
	 *            the interior painter
	 * @param leftWarningImage
	 *            the left warning image
	 * @param rightWarningImage
	 *            the right warning image
	 * @param blackWarningImage
	 *            the black warning image
	 */
	public PlanProTableCellPainter(final ICellPainter interiorPainter,
			final Image leftWarningImage, final Image rightWarningImage,
			final Image blackWarningImage) {
		super(new PaddingDecorator(new BasePainterDecorator(
				new BasePainterDecorator(new BasePainterDecorator(
						interiorPainter, BaseEdgeEnum.BEFORE,
						new WarningPainter(blackWarningImage,
								CellContentExtensions.WARNING_MARK_BLACK)),
						BaseEdgeEnum.BEFORE,
						new WarningPainter(leftWarningImage,
								CellContentExtensions.WARNING_MARK_YELLOW)),
				BaseEdgeEnum.AFTER,
				new WarningPainter(rightWarningImage,
						CellContentExtensions.WARNING_MARK_RED)),
				TOP_PADDING, RIGHT_PADDING, BOTTOM_PADDING, LEFT_PADDING,
				false));
	}
}
