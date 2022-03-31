/**
 * Copyright (c) 2018 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.basis.text

import org.eclipse.jface.text.BadLocationException
import org.eclipse.jface.text.Region
import org.eclipse.set.basis.StringDocument
import org.junit.jupiter.api.Test

import static org.hamcrest.MatcherAssert.assertThat
import static org.hamcrest.core.Is.*
import static org.junit.jupiter.api.Assertions.*

import static extension org.eclipse.set.basis.extensions.IDocumentExtensions.*

/**
 * Tests for {@link Tag}.
 *  
 * @author Schaefer
 */
class TagTest {

	@Test
	def void testTag() throws BadLocationException {
		val document = new StringDocument("<tagname/>")
		val region = new Region(0, 10)
		val tag = new Tag(document, region)
		assertNotNull(tag)
	}

	@Test
	def void testGetLine() throws BadLocationException {
		val document = new StringDocument(
			"first line\n second line  <tagname/>   \n third line"
		)
		val region = new Region(25, 10)
		val tagText = document.get(region)
		assertThat(tagText, is("<tagname/>"))
		val tag = new Tag(document, region)
		assertThat(tag.line, is(1));
	}

	@Test
	def void testGetName() throws BadLocationException {
		val document = new StringDocument(
			"first line <?name?>\n second line  <tagname/>   \n third line </anotherTag x=1>"
		)
		var region = new Region(11, 8)
		var tagText = document.get(region)
		assertThat(tagText, is("<?name?>"))
		var tag = new Tag(document, region)
		assertThat(tag.name, is("name"));

		region = new Region(34, 10)
		tagText = document.get(region)
		assertThat(tagText, is("<tagname/>"))
		tag = new Tag(document, region)
		assertThat(tag.name, is("tagname"));

		region = new Region(60, 17)
		tagText = document.get(region)
		assertThat(tagText, is("</anotherTag x=1>"))
		tag = new Tag(document, region)
		assertThat(tag.name, is("anotherTag"));
	}

	@Test
	def void testGetText() throws BadLocationException {
		val document = new StringDocument(
			"first line <?name?>\n second line  <tagname/>   \n third line </anotherTag x=1>"
		)
		var region = new Region(11, 8)
		var tagText = document.get(region)
		assertThat(tagText, is("<?name?>"))
		var tag = new Tag(document, region)
		assertThat(tag.text, is("<?name?>"));

		region = new Region(34, 10)
		tagText = document.get(region)
		assertThat(tagText, is("<tagname/>"))
		tag = new Tag(document, region)
		assertThat(tag.text, is("<tagname/>"));

		region = new Region(60, 17)
		tagText = document.get(region)
		assertThat(tagText, is("</anotherTag x=1>"))
		tag = new Tag(document, region)
		assertThat(tag.text, is("</anotherTag x=1>"));
	}

	@Test
	def void testIsClosing() throws BadLocationException {
		val document = new StringDocument(
			"first line <?name?>\n second line  <tagname/>   \n third line </anotherTag x=1>"
		)
		var region = new Region(11, 8)
		var tagText = document.get(region)
		assertThat(tagText, is("<?name?>"))
		var tag = new Tag(document, region)
		assertThat(tag.closing, is(false));

		region = new Region(34, 10)
		tagText = document.get(region)
		assertThat(tagText, is("<tagname/>"))
		tag = new Tag(document, region)
		assertThat(tag.closing, is(true));

		region = new Region(60, 17)
		tagText = document.get(region)
		assertThat(tagText, is("</anotherTag x=1>"))
		tag = new Tag(document, region)
		assertThat(tag.closing, is(true));
	}

	@Test
	def void testIsOpening() throws BadLocationException {
		val document = new StringDocument(
			"first line <?name?>\n second line  <tagname/>   \n third line </anotherTag x=1>"
		)
		var region = new Region(11, 8)
		var tagText = document.get(region)
		assertThat(tagText, is("<?name?>"))
		var tag = new Tag(document, region)
		assertThat(tag.opening, is(true));

		region = new Region(34, 10)
		tagText = document.get(region)
		assertThat(tagText, is("<tagname/>"))
		tag = new Tag(document, region)
		assertThat(tag.opening, is(true));

		region = new Region(60, 17)
		tagText = document.get(region)
		assertThat(tagText, is("</anotherTag x=1>"))
		tag = new Tag(document, region)
		assertThat(tag.opening, is(false));
	}
}
