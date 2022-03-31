/**
 * Copyright (c) 2018 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.basis.text;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;

/**
 * A Tag within a {@link IDocument}.
 * 
 * @author Schaefer
 */
public class Tag {

	private static final String CLOSING_PATTERN1 = "^<\\s*/.*"; //$NON-NLS-1$
	private static final String CLOSING_PATTERN2 = ".*/\\s*>$"; //$NON-NLS-1$
	private static final int NAME_GROUP = 1;
	private static final Pattern NAME_PATTERN = Pattern
			.compile("<\\s*[/?]?\\s*([^ \\t\\n\\x0B\\f\\r>/\\?]+).*"); //$NON-NLS-1$
	private static final String OPENING_PATTERN = "^<\\s*[^/].*"; //$NON-NLS-1$

	private static String findName(final String text) {
		final Matcher matcher = NAME_PATTERN.matcher(text);
		if (matcher.matches()) {
			return matcher.group(NAME_GROUP);
		}
		throw new IllegalArgumentException(text);
	}

	private static boolean isClosing(final String text) {
		return text.matches(CLOSING_PATTERN1) || text.matches(CLOSING_PATTERN2);
	}

	private static boolean isOpening(final String text) {
		return text.matches(OPENING_PATTERN);
	}

	private final int endLineOffset;
	private final boolean isClosing;
	private final boolean isOpening;
	private final int line;
	private final int lineOffset;
	private final String name;
	private final String text;

	/**
	 * @param document
	 *            the document
	 * @param region
	 *            the region of the tag
	 * @throws BadLocationException
	 *             if the range is invalid in this document
	 */
	public Tag(final IDocument document, final IRegion region)
			throws BadLocationException {
		text = document.get(region.getOffset(), region.getLength());
		line = document.getLineOfOffset(region.getOffset());
		lineOffset = document.getLineOffset(line);
		endLineOffset = lineOffset + document.getLineLength(line);
		name = findName(text);
		isOpening = isOpening(text);
		isClosing = isClosing(text);
	}

	/**
	 * @return the offset of the end of the line of the tag
	 */
	public int getEndLineOffset() {
		return endLineOffset;
	}

	/**
	 * @return the line of the tag within the document (starting with line 0)
	 */
	public int getLine() {
		return line;
	}

	/**
	 * @return the offset of the line of the tag
	 */
	public int getLineOffset() {
		return lineOffset;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the text
	 */
	public String getText() {
		return text;
	}

	/**
	 * @return whether the tag is a closing tag
	 */
	public boolean isClosing() {
		return isClosing;
	}

	/**
	 * @return whether the tag is an opening tag
	 */
	public boolean isOpening() {
		return isOpening;
	}
}
