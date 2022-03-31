/**
 * Copyright (c) 2018 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.basis.extensions

import java.util.regex.Pattern
import org.eclipse.jface.text.BadLocationException
import org.eclipse.jface.text.IDocument
import org.eclipse.jface.text.IRegion
import org.eclipse.jface.text.Region
import org.eclipse.set.basis.StringDocument
import org.junit.jupiter.api.Test

import static org.hamcrest.MatcherAssert.assertThat
import static org.hamcrest.core.Is.*

import static extension org.eclipse.set.basis.extensions.IDocumentExtensions.*
import static extension org.eclipse.set.basis.extensions.MatcherExtensions.*

/**
 * Tests for {@link MatcherExtensions}.
 *  
 * @author Schaefer
 */
class MatcherExtensionsTest {

	@Test
	def void testGetAllMatches() throws BadLocationException {
		val document = new StringDocument("0abc 123 xyz22af9")
		var region = new Region(0, document.length)
		var text = document.get(region)
		val pattern = Pattern.compile("[0-9]+")
		var matcher = pattern.matcher(text)
		var allMatches = matcher.getAllMatches(region).map[toText(document)]
		assertThat(allMatches, is(#["0", "123", "22", "9"]))

		region = new Region(1, document.length - 2)
		text = document.get(region)
		matcher = pattern.matcher(text)
		allMatches = matcher.getAllMatches(region).map[toText(document)]
		assertThat(allMatches, is(#["123", "22"]))
	}

	private static def String toText(IRegion region, IDocument document) {
		try {
			return document.get(region)
		} catch (BadLocationException e) {
			throw new RuntimeException(e)
		}
	}
}
