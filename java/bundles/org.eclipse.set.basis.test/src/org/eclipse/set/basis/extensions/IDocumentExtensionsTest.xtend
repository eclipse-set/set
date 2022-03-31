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
import org.eclipse.jface.text.Region
import org.eclipse.set.basis.StringDocument
import org.junit.jupiter.api.Test

import static java.lang.Character.*
import static org.hamcrest.MatcherAssert.assertThat
import static org.hamcrest.core.Is.*
import static org.junit.jupiter.api.Assertions.*

import static extension org.eclipse.set.basis.extensions.IDocumentExtensions.*

/**
 * Tests for {@link ICharacterScannerExtensions}.
 *  
 * @author Schaefer
 */
class IDocumentExtensionsTest {

	@Test
	def void testGetPrevious() {
		val document = new StringDocument("A simple document content.")

		assertEquals(null, document.getPrevious(0, 'A'))
		assertEquals(valueOf('A'), document.getPrevious(8, 'A'))
		assertEquals(valueOf('e'), document.getPrevious(8, 'A', 'e'))
		assertEquals(valueOf('e'), document.getPrevious(8, 'A', 'e'))
		assertEquals(null, document.getPrevious(8, 'x'))
	}

	@Test
	def void testGetNext() {
		val document = new StringDocument("A simple document content.")

		assertEquals(null, document.getNext(document.length - 1, '.', 't'))
		assertEquals(valueOf('.'),
			document.getNext(document.length - 2, '.', 't'))
		assertEquals(valueOf('t'),
			document.getNext(document.length - 3, '.', 't'))
	}

	@Test
	def void testGetTags() throws BadLocationException {
		val document = new StringDocument("<a><a><b></b><c/></a></a>")
		var region = new Region(0, document.length)

		var tags = document.getTags(region)
		assertThat(tags.map[name], is(#["a", "a", "b", "b", "c", "a", "a"]))

		region = new Region(3, 18)
		assertThat(document.get(region), is("<a><b></b><c/></a>"))
		tags = document.getTags(region)
		assertThat(tags.map[name], is(#["a", "b", "b", "c", "a"]))
	}

	@Test
	def void testGetTags2() throws BadLocationException {
		val document = new StringDocument("<a><b></b><c/></a>")
		val region = new Region(0, document.length)

		val tags = document.getTags(region)
		assertThat(tags.map[name], is(#["a", "b", "b", "c", "a"]))
	}
}
