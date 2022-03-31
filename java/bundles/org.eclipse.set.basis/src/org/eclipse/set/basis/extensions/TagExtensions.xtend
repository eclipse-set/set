/**
 * Copyright (c) 2018 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.basis.extensions

import com.google.common.collect.Sets
import org.eclipse.set.basis.text.Tag
import java.util.ArrayList
import java.util.Set
import org.eclipse.jface.text.BadLocationException
import org.eclipse.jface.text.Position

/**
 * Extensions for {@link Tag}.
 * 
 * @author Schaefer
 */
class TagExtensions {

	/**
	 * @param tag this tag
	 * 
	 * @return a string representation of this tag
	 */
	static def String asString(Tag tag) {
		var String name
		try {
			name = tag.name
		} catch (BadLocationException e) {
			name = '''!«e.message»!'''
		}
		return '''name=«name» line=«tag.line»'''
	}

	/**
	 * @param tags the tags
	 * 
	 * @return the folding positions for the given tags
	 */
	static def Set<Position> getFoldingPositions(ArrayList<Tag> tags) {
		val result = Sets.newHashSet

		for (var int i = 0; i < tags.size; i++) {
			val position = tags.get(i).getPosition(i, tags)
			if (position !== null) {
				result.add(position)
			}
		}

		return result
	}

	private static def Tag findClosing(
		Tag tag,
		int tagNumber,
		ArrayList<Tag> tags
	) {
		var level = 0

		for (var int i = tagNumber + 1; i < tags.size; i++) {
			val other = tags.get(i)

			if (other.name == tag.name) {
				if (other.opening && other.closing) {
					// next
				} else if (other.opening) {
					level++
				} else if (other.closing) {
					if (level === 0) {
						return other
					}
					level--
				}
			}
		}

		return null
	}

	private static def Position getPosition(
		Tag tag,
		int tagNumber,
		ArrayList<Tag> tags
	) {
		if (!tag.closing) {
			val closing = tag.findClosing(tagNumber, tags)
			if (closing !== null) {
				if (tag.line !== closing.line) {
					val position = new Position(
						tag.lineOffset,
						closing.endLineOffset - tag.lineOffset
					)
					return position
				}
			}
		}

		return null
	}
}
