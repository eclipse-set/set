/**
 * Copyright (c) 2019 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.nattable.utils;

import java.io.IOException;
import java.io.StringReader;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.XMLEvent;

import org.eclipse.nebula.widgets.nattable.config.IConfigRegistry;
import org.eclipse.nebula.widgets.nattable.data.convert.IDisplayConverter;
import org.eclipse.nebula.widgets.nattable.extension.nebula.richtext.RichTextCellPainter;
import org.eclipse.nebula.widgets.nattable.extension.nebula.richtext.RichTextConfigAttributes;
import org.eclipse.nebula.widgets.nattable.layer.cell.ILayerCell;
import org.eclipse.nebula.widgets.richtext.RichTextPainter;
import org.eclipse.set.basis.Pair;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Rectangle;

/**
 * Renders HTML formatted text by using the Nebula RichTextPainter with word
 * splitting at zero width spaces.
 * 
 * @author schaefer
 */
public class PlanProRichTextCellPainter extends RichTextCellPainter {

	private static final String WORD_SPLIT_REGEX = "[\\t\\n\\x0B\\f\\r\\u200b]"; //$NON-NLS-1$

	private static class PlanProRichTextPainter extends RichTextPainter {
		private static String HYPENATION_SEPRATOR = "-"; //$NON-NLS-1$
		XMLInputFactory factory = XMLInputFactory.newInstance();
		{
			// as we don't have a well-formed XML document, we need to take care
			// of
			// entity references ourself
			factory.setProperty(XMLInputFactory.IS_REPLACING_ENTITY_REFERENCES,
					Boolean.FALSE);
		}
		Hyphenator hypenator;

		private PlanProRichTextPainter(final boolean wrap) {
			super(wrap);
			try {
				hypenator = Hyphenator.createInstance();
			} catch (ClassNotFoundException | IOException e) {
				throw new RuntimeException(e);
			}
		}

		@Override
		protected void paintHTML(final String html, final GC gc,
				final Rectangle bounds, final boolean render) {
			try {
				final String cleanedHTML = html.replaceAll(
						RichTextPainter.CONTROL_CHARACTER_REGEX, ""); //$NON-NLS-1$
				final XMLEventReader reader = factory
						.createXMLEventReader(new StringReader(cleanedHTML));
				String textValue = ""; //$NON-NLS-1$
				while (reader.hasNext()) {
					final XMLEvent event = reader.nextEvent();
					if (event.getEventType() == XMLStreamConstants.CHARACTERS) {
						final Characters asCharacters = event.asCharacters();
						textValue = asCharacters.getData();
						break;
					}
				}
				if (textValue.isEmpty()) {
					super.paintHTML(html, gc, bounds, render);
					return;
				}
				final String hyphenationText = hyphenationText(textValue, gc,
						bounds.width);
				final String newHtmlText = html.replace(textValue,
						hyphenationText);
				super.paintHTML(newHtmlText, gc, bounds, render);
			} catch (final Exception e) {
				super.paintHTML(html, gc, bounds, render);
			}
		}

		private String hyphenationText(final String text, final GC gc,
				final int availableLength) {
			final StringBuilder result = new StringBuilder();
			int lineWidth = 0;
			final String[] split = text.split("[\\s_]"); //$NON-NLS-1$
			for (String word : split) {
				final String seperator = String
						.valueOf(text.charAt(word.length()));
				final int seperatorW = gc.textExtent(seperator).x;
				int w = gc.textExtent(word).x;
				if (lineWidth + seperatorW + w > availableLength) {
					final Pair<String, String> splitWord = splitWord(gc, word,
							availableLength - lineWidth - seperatorW);
					if (splitWord != null) {
						result.append(splitWord.getFirst())
								.append(HYPENATION_SEPRATOR)
								.append("<br></br>"); //$NON-NLS-1$
						word = splitWord.getSecond();
					}
					lineWidth = 0;
					w = gc.textExtent(word).x;
				} else if (lineWidth > 0) {
					lineWidth += seperatorW;
				}
				result.append(word);
				lineWidth += w;
			}
			// final int space = gc.textExtent(" ").x; //$NON-NLS-1$
			// for (String word : text.split("[\\s_]+")) { //$NON-NLS-1$
			// int w = gc.textExtent(word).x;
			// if (lineWidth + space + w > availableLength) {
			// final Pair<String, String> splitWord = splitWord(gc, word,
			// availableLength - lineWidth - space);
			// if (splitWord != null) {
			// result.append(splitWord.getFirst())
			// .append(HYPENATION_SEPRATOR)
			// .append("<br></br>"); //$NON-NLS-1$
			// word = splitWord.getSecond();
			// }
			// lineWidth = 0;
			// w = gc.textExtent(word).x;
			// } else if (lineWidth > 0) {
			// lineWidth += space;
			// }
			// result.append(word);
			// lineWidth += w;
			// }
			return result.toString();
		}

		private Pair<String, String> splitWord(final GC gc, final String word,
				final int rest) {
			final int[] points = hypenator.points(word);
			for (int i = points.length - 1; i >= 0; i--) {
				final String head = word.substring(0, points[i]);
				if (gc.textExtent(head + HYPENATION_SEPRATOR).x <= rest) {
					return new Pair<>(head, word.substring(points[i]));
				}
			}
			return null;
		}
	}

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
		this.richTextPainter = new PlanProRichTextPainter(wrapText);
		this.richTextPainter.setWordSplitRegex(WORD_SPLIT_REGEX);
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
