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

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
	private static String RICHTEXT_LINE_BREAK = "<br></br>"; //$NON-NLS-1$
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
		hyphenator = Hyphenator.createInstance();
	}

	@Override
	protected void paintHTML(final String html, final GC gc,
			final Rectangle bounds, final boolean render) {
		try {
			final String cleanedHTML = html
					.replaceAll(RichTextPainter.CONTROL_CHARACTER_REGEX, ""); //$NON-NLS-1$
			final XMLEventReader reader = factory
					.createXMLEventReader(new StringReader(cleanedHTML));
			String newHtmlText = html;
			while (reader.hasNext()) {
				final XMLEvent event = reader.nextEvent();
				if (event.getEventType() == XMLStreamConstants.CHARACTERS) {
					final Characters asCharacters = event.asCharacters();
					final String textValue = asCharacters.getData();
					final String hyphenationText = hyphenationText(textValue,
							gc, bounds.width);
					newHtmlText = html.replace(textValue, hyphenationText);
				}
			}
			super.paintHTML(newHtmlText, gc, bounds, render);
		} catch (final Exception e) {
			super.paintHTML(html, gc, bounds, render);
		}
	}

	/**
	 * @param text
	 *            the text to hyphenation
	 * @param gc
	 *            the {@link GC}
	 * @param availableLength
	 *            the available length
	 * @return the hyphenated text
	 */
	public String hyphenationText(final String text, final GC gc,
			final int availableLength) {
		if (gc.textExtent(text).x <= availableLength) {
			return text;
		}
		final StringBuilder result = new StringBuilder();
		final Matcher matcher = Pattern.compile("\\S+|\\s+").matcher(text);
		while (matcher.find()) {
			final String word = matcher.group();
			if (Character.isWhitespace(word.charAt(0))) {
				// Keep original whitespace character
				result.append(word);
			} else {
				result.append(hyphenationText(word, gc, availableLength, "_")); //$NON-NLS-1$
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
						.collect(Collectors.joining(
								HYPHENATION_SEPARATOR + RICHTEXT_LINE_BREAK));
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
					result.append(RICHTEXT_LINE_BREAK).append(words[i]);
					lineWidth = w;
				} else {
					int rest = availableLength - lineWidth - separatorW;
					if (rest <= 0) {
						result.append(RICHTEXT_LINE_BREAK);
						rest = availableLength;
						lineWidth = 0;
					}
					final List<String> hyphenatedWord = splitWord(gc, words[i],
							rest);
					result.append(hyphenatedWord.stream()
							.collect(Collectors.joining(HYPHENATION_SEPARATOR
									+ RICHTEXT_LINE_BREAK)));
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
			final String tmpW = builder.toString() + w;
			if (builder.length() > 0
					&& gc.textExtent(tmpW + HYPHENATION_SEPARATOR).x > rest) {
				hyphenatedWord.add(builder.toString());
				builder = new StringBuilder(w);
			} else {
				builder.append(w);
			}
		}

		if (builder.length() > 0 || hyphenatedWord.isEmpty()) {
			hyphenatedWord.add(builder.toString());
		}

		return hyphenatedWord;

	}
}