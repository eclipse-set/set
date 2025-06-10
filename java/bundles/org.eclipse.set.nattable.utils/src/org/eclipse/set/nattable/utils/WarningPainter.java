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
import org.eclipse.nebula.widgets.nattable.painter.cell.ImagePainter;
import org.eclipse.swt.graphics.Image;

/**
 * Display a warning mark if the cell contains a warning.
 * 
 * @author Schaefer
 */
public class WarningPainter extends ImagePainter {

	private final Image warningImage;
	private final String warningPattern;

	/**
	 * @param warningImage
	 *            the warning image to be displayed
	 * @param warningPattern
	 *            the warning pattern for triggering this warning image
	 */
	public WarningPainter(final Image warningImage,
			final String warningPattern) {
		super(true);
		this.warningImage = warningImage;
		this.warningPattern = warningPattern;
	}

	private boolean showWarning(final Object value) {
		return value instanceof final String text
				&& text.contains(warningPattern);
	}

	@Override
	protected Image getImage(final ILayerCell cell,
			final IConfigRegistry configRegistry) {
		if (showWarning(cell.getDataValue())) {
			return warningImage;
		}
		return null;
	}
}
