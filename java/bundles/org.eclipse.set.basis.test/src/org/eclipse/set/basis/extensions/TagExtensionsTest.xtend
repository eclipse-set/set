/**
 * Copyright (c) 2018 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.basis.extensions

import org.eclipse.jface.text.BadLocationException
import org.eclipse.jface.text.Position
import org.eclipse.jface.text.Region
import org.eclipse.set.basis.StringDocument
import org.eclipse.set.basis.text.Tag
import org.junit.jupiter.api.Test

import static org.hamcrest.MatcherAssert.assertThat
import static org.hamcrest.core.Is.*

import static extension org.eclipse.set.basis.extensions.IDocumentExtensions.*
import static extension org.eclipse.set.basis.extensions.TagExtensions.*

/**
 * Tests for {@link TagExtensions}.
 *  
 * @author Schaefer
 */
class TagExtensionsTest {

	@Test
	def void testAsString() throws BadLocationException {
		val document = new StringDocument("<tag/>")
		val region = new Region(0, 6)
		assertThat(document.get(region), is("<tag/>"))
		val tag = new Tag(document, region)
		assertThat(tag.asString, is("name=tag line=0"))
	}

	@Test
	def void testGetFoldingPositions1() throws BadLocationException {
		val document = new StringDocument(
			"<tag>\n<a>\n</a><tag>\n</tag>\n</tag>")
		val region = new Region(0, document.length)
		val tags = document.getTags(region)
		val positions = tags.foldingPositions
		assertThat(positions,
			is(
				#{new Position(0, 33), new Position(6, 14),
					new Position(10, 17)}))
	}

	@Test
	def void testGetFoldingPositions2() throws BadLocationException {
		val document = new StringDocument(
			"<tag>\n<a>\n</a><tag>\n</tagx>\n</tag>")
		val region = new Region(0, document.length)
		val tags = document.getTags(region)
		val positions = tags.foldingPositions
		assertThat(positions, is(#{new Position(6, 14), new Position(10, 24)}))
	}
}
