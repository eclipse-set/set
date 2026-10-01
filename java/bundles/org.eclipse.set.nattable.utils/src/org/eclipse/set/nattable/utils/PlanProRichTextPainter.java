/**
 * Copyright (c) 2026 DB InfraGO AG and others
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 */
package org.eclipse.set.nattable.utils;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.XMLEvent;

import org.eclipse.nebula.widgets.richtext.RichTextPainter;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Rectangle;

/**
 * 
 */
public class PlanProRichTextPainter extends RichTextPainter {
	private static String HYPHENATION_SEPARATOR = "-"; //$NON-NLS-1$
	XMLInputFactory factory = XMLInputFactory.newInstance();
	{
		// as we don't have a well-formed XML document, we need to take care
		// of
		// entity references ourself
		factory.setProperty(XMLInputFactory.IS_REPLACING_ENTITY_REFERENCES,
				Boolean.FALSE);
	}
	Hyphenator hyphenator;

	/**
	 * @param wrap
	 */
	public PlanProRichTextPainter(final boolean wrap) {
		super(wrap);
		try {
			hyphenator = Hyphenator.createInstance();
		} catch (ClassNotFoundException | IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	protected void paintHTML(final String html, final GC gc,
			final Rectangle bounds, final boolean render) {
		try {
			final String cleanedHTML = html
					.replaceAll(RichTextPainter.CONTROL_CHARACTER_REGEX, ""); //$NON-NLS-1$
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
			final String newHtmlText = html.replace(textValue, hyphenationText);
			super.paintHTML(newHtmlText, gc, bounds, render);
		} catch (final Exception e) {
			super.paintHTML(html, gc, bounds, render);
		}
	}

	/**
	 * @param text
	 * @param gc
	 * @param availableLength
	 * @return
	 */
	public String hyphenationText(final String text, final GC gc,
			final int availableLength) {
		if (gc.textExtent(text).x <= availableLength) {
			return text;
		}
		final StringBuilder result = new StringBuilder();
		final String[] split = text.split("\\s+");
		for (int i = 0; i < split.length; i++) {
			result.append(hyphenationText(split[i], gc, availableLength, "_")); //$NON-NLS-1$
			if (i < split.length - 1) {
				result.append(" "); //$NON-NLS-1$
			}
		}
		return result.toString();
	}

	private String hyphenationText(final String text, final GC gc,
			final int availableLength, final String seperator) {
		final String[] words = text.split(seperator);
		if (words.length == 1) {
			if (gc.textExtent(text).x > availableLength) {
				return splitWord(gc, text, availableLength).stream()
						.collect(Collectors
								.joining(HYPHENATION_SEPARATOR + "<br></br>"));
			}
			return text;
		}
		final StringBuilder result = new StringBuilder();
		final int separatorW = gc.textExtent(seperator).x;
		int lineWidth = 0;
		for (int i = 0; i < words.length; i++) {
			final int w = gc.textExtent(words[i]).x;
			if (lineWidth + separatorW + w > availableLength) {
				// Add line break instead hyphenation, when word length relevant
				if (separatorW + w <= availableLength) {
					result.append("<br></br>").append(words[i]);
					lineWidth = w;
				} else {
					int rest = availableLength - lineWidth - separatorW;
					if (rest == 0) {
						result.append("<br></br>");
						rest = availableLength;
						lineWidth = 0;
					}
					final List<String> hyphenatedWord = splitWord(gc, words[i],
							rest);
					result.append(hyphenatedWord.stream()
							.collect(Collectors.joining(
									HYPHENATION_SEPARATOR + "<br></br>")));
					lineWidth = gc.textExtent(hyphenatedWord.getLast()).x;
				}
			} else {
				result.append(words[i]);
				lineWidth += w;
			}

			if (i < words.length - 1) {
				lineWidth += separatorW;
				result.append(seperator);
			}
		}
		return result.toString();

	}

	private List<String> splitWord(final GC gc, final String word,
			final int rest) {
		final String[] splitdWord = hyphenator.splitedWord(word);
		final List<String> hyphenatedWord = new ArrayList<>();
		StringBuilder builder = new StringBuilder();
		for (final String w : splitdWord) {
			if (gc.textExtent(
					builder.toString() + w + HYPHENATION_SEPARATOR).x <= rest) {
				builder.append(w);
			} else {
				hyphenatedWord.add(builder.toString());
				builder = new StringBuilder(w);
			}
		}
		hyphenatedWord.add(builder.toString());
		return hyphenatedWord;

	}
}