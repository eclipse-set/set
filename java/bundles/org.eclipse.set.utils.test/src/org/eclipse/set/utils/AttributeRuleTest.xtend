/**
 * Copyright (c) 2019 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.utils

import org.eclipse.jface.text.BadLocationException
import org.eclipse.jface.text.TextAttribute
import org.eclipse.jface.text.rules.IToken
import org.eclipse.jface.text.rules.RuleBasedScanner
import org.eclipse.jface.text.rules.Token
import org.eclipse.set.basis.StringDocument
import org.eclipse.set.utils.text.AttributeRule
import org.eclipse.set.utils.text.TagRule
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import static org.hamcrest.MatcherAssert.assertThat
import static org.hamcrest.core.Is.*
import static org.hamcrest.core.IsSame.*
import static org.junit.jupiter.api.Assertions.*

/** 
 * Tests for {@link AttributeRule}.
 * 
 * @author Schaefer
 */
class AttributeRuleTest {

	var IToken defaultToken
	var IToken tagToken
	var IToken attributeToken
	var TagRule tagRule
	var AttributeRule attributeRule
	var RuleBasedScanner scanner

	@BeforeEach
	def void before() {
		defaultToken = new Token(new TextAttribute(null))
		tagToken = new Token(new TextAttribute(null))
		attributeToken = new Token(new TextAttribute(null))
		tagRule = new TagRule(tagToken)
		attributeRule = new AttributeRule(tagRule, attributeToken)
		scanner = new RuleBasedScanner
		scanner.defaultReturnToken = defaultToken
	}

	@Test
	def void testSimpleAttribute() throws BadLocationException {
		val document = new StringDocument("<tag attribute=0/>")
		scanner.rules = #[tagRule, attributeRule]
		scanner.setRange(document, 0, document.length)

		var token = scanner.nextToken
		var offset = scanner.tokenOffset
		var length = scanner.tokenLength
		assertThat(token, sameInstance(tagToken))
		assertThat(document.get(offset, length), is("<tag "))
		assertFalse(token.EOF)

		token = scanner.nextToken
		offset = scanner.tokenOffset
		length = scanner.tokenLength
		assertThat(token, sameInstance(attributeToken))
		assertThat(document.get(offset, length), is("attribute"))
		assertFalse(token.EOF)

		token = scanner.nextToken
		offset = scanner.tokenOffset
		length = scanner.tokenLength
		assertThat(token, sameInstance(defaultToken))
		assertThat(document.get(offset, length), is("="))
		assertFalse(token.EOF)

		token = scanner.nextToken
		offset = scanner.tokenOffset
		length = scanner.tokenLength
		assertThat(token, sameInstance(defaultToken))
		assertThat(document.get(offset, length), is("0"))
		assertFalse(token.EOF)

		token = scanner.nextToken
		offset = scanner.tokenOffset
		length = scanner.tokenLength
		assertThat(token, sameInstance(tagToken))
		assertThat(document.get(offset, length), is("/>"))
		assertFalse(token.EOF)

		token = scanner.nextToken
		offset = scanner.tokenOffset
		length = scanner.tokenLength
		assertTrue(token.EOF)
	}

	@Test
	def void testComment() throws BadLocationException {
		val document = new StringDocument("<!-- x -->")
		scanner.rules = #[tagRule, attributeRule]
		scanner.setRange(document, 0, document.length)

		var token = scanner.nextToken
		var offset = scanner.tokenOffset
		var length = scanner.tokenLength
		assertThat(token, sameInstance(tagToken))
		assertThat(document.get(offset, length), is("<!-- "))
		assertFalse(token.EOF)

		token = scanner.nextToken
		offset = scanner.tokenOffset
		length = scanner.tokenLength
		assertThat(token, sameInstance(defaultToken))
		assertThat(document.get(offset, length), is("x"))
		assertFalse(token.EOF)

		token = scanner.nextToken
		offset = scanner.tokenOffset
		length = scanner.tokenLength
		assertThat(token, sameInstance(defaultToken))
		assertThat(document.get(offset, length), is(" "))
		assertFalse(token.EOF)

		token = scanner.nextToken
		offset = scanner.tokenOffset
		length = scanner.tokenLength
		assertThat(token, sameInstance(tagToken))
		assertThat(document.get(offset, length), is("-->"))
		assertFalse(token.EOF)

		token = scanner.nextToken
		offset = scanner.tokenOffset
		length = scanner.tokenLength
		assertTrue(token.EOF)
	}
}
