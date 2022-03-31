/**
 * Copyright (c) 2018 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.basis.extensions

import com.google.common.collect.Lists
import org.eclipse.set.basis.text.Tag
import java.util.ArrayList
import java.util.regex.Matcher
import java.util.regex.Pattern

import org.eclipse.jface.text.IDocument
import org.eclipse.jface.text.IRegion
import org.eclipse.jface.text.BadLocationException

import static extension org.eclipse.set.basis.extensions.MatcherExtensions.*
/**
 * Extensions for {@link IDocument}.
 * 
 * @author Schaefer
 */
class IDocumentExtensions {

	static val TAG_PATTERN = Pattern.compile(
		"<\\s*[/?]?\\s*([^ \\t\\n\\x0B\\f\\r>/]+)([^>]*)>")

	/**
	 * @param document this document
	 * @param region the region
	 * 
	 * @return the text of the document within the given region
	 */
	static def String get(
		IDocument document,
		IRegion region
	) throws BadLocationException {
		return document.get(region.offset, region.length)
	}

	/**
	 * Find the next character from the given offset in the document,
	 * matching one of the given characters.
	 * 
	 * @param document this document
	 * @param offset the offset
	 * @param chars the chars
	 */
	static def Character getNext(IDocument document, int offset,
		char... chars) {
		val charList = Lists.newArrayList(chars)
		val length = document.length
		try {
			for (var position = offset + 1; position < length; position++) {
				val positionChar = Character.valueOf(document.getChar(position))
				if (charList.contains(positionChar)) {
					return positionChar
				}
			}
			return null
		} catch (BadLocationException e) {
			// should be checked with the above routine
			throw new RuntimeException(e)
		}
	}

	/**
	 * Find the previous character from the given offset in the document,
	 * matching one of the given characters.
	 * 
	 * @param document this document
	 * @param offset the offset
	 * @param chars the chars
	 */
	static def Character getPrevious(
		IDocument document,
		int offset,
		char... chars
	) {
		val charList = Lists.newArrayList(chars)
		try {
			for (var position = offset - 1; position >= 0; position--) {
				val positionChar = Character.valueOf(document.getChar(position))
				if (charList.contains(positionChar)) {
					return positionChar
				}
			}
			return null
		} catch (BadLocationException e) {
			// should be checked with the above routine
			throw new RuntimeException(e)
		}
	}

	/**
	 * @param document this document
	 * @param region the region
	 * 
	 * @return the tags within the given region
	 */
	static def ArrayList<Tag> getTags(
		IDocument document,
		IRegion region
	) throws BadLocationException {
		val text = document.get(region.offset, region.length)
		val Matcher matcher = TAG_PATTERN.matcher(text)
		return Lists.newArrayList(matcher.getAllMatches(region).map [
			toTag(document)
		])
	}

	private static def Tag toTag(IRegion match, IDocument document) {
		try {
			return new Tag(document, match)
		} catch (BadLocationException e) {
			throw new RuntimeException(e)
		}
	}
}
