/**
 * Copyright (c) 2019 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.nattable.utils;

import org.eclipse.nebula.widgets.nattable.config.IConfigRegistry;
import org.eclipse.nebula.widgets.nattable.data.convert.IDisplayConverter;
import org.eclipse.nebula.widgets.nattable.extension.nebula.richtext.RichTextCellPainter;
import org.eclipse.nebula.widgets.nattable.extension.nebula.richtext.RichTextConfigAttributes;
import org.eclipse.nebula.widgets.nattable.layer.cell.ILayerCell;

/**
 * Renders HTML formatted text by using the Nebula RichTextPainter with word
 * splitting at zero width spaces.
 * 
 * @author schaefer
 */
public class PlanProRichTextCellPainter extends RichTextCellPainter {

	private static final String WORD_SPLIT_REGEX = "[\\t\\n\\x0B\\f\\r\\u200b]"; //$NON-NLS-1$

	/**
	 * Creates a new {@link PlanProRichTextCellPainter}.
	 *
	 * @param wrapText
	 *            <code>true</code> to enable text wrapping, which means that
	 *            text is wrapped for whole words. Words itself are not wrapped.
	 * @param calculateByTextLength
	 *            <code>true</code> to configure the text painter to calculate
	 *            the cell border by containing text length. This means the
	 *            width of the cell is calculated by content.
	 * @param calculateByTextHeight
	 *            <code>true</code> to configure the text painter to calculate
	 *            the cell border by containing text height. This means the
	 *            height of the cell is calculated by content.
	 */
	public PlanProRichTextCellPainter(final boolean wrapText,
			final boolean calculateByTextLength,
			final boolean calculateByTextHeight) {
		super(wrapText, calculateByTextLength, calculateByTextHeight);
		richTextPainter.setWordSplitRegex(WORD_SPLIT_REGEX);
	}

	@Override
	protected String getHtmlText(final ILayerCell cell,
			final IConfigRegistry configRegistry) {
		final Object canonicalValue = cell.getDataValue();
		Object displayValue;
		final IDisplayConverter markupDisplayConverter = configRegistry
				.getConfigAttribute(
						RichTextConfigAttributes.MARKUP_DISPLAY_CONVERTER,
						cell.getDisplayMode(), cell.getConfigLabels());

		if (markupDisplayConverter != null) {
			displayValue = markupDisplayConverter.canonicalToDisplayValue(cell,
					configRegistry, canonicalValue);
		} else {
			displayValue = canonicalValue;
		}
		return displayValue == null ? "" : String.valueOf(displayValue); //$NON-NLS-1$
	}
}
