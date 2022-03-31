/**
 * Copyright (c) 2018 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.utils

import org.junit.jupiter.api.Test

import static org.hamcrest.MatcherAssert.assertThat
import static org.hamcrest.core.Is.*

import static extension org.eclipse.set.utils.StringExtensions.*

/**
 * Tests for {@link StringExtensions}.
 * 
 * @author Schaefer 
 */
class StringExtensionsTest {

	@Test
	def void testShortenBy() {
		assertThat(
			"TEST".shortenBy(2),
			is("TE")
		)
		assertThat(
			"TEST".shortenBy(3),
			is("T")
		)
		assertThat(
			"TEST".shortenBy(4),
			is("")
		)
		assertThat(
			"TEST".shortenBy(5),
			is("")
		)
		assertThat(
			"TEST".shortenBy(0),
			is("TEST")
		)
	}

	@Test
	def void testRemoveSuffix() {
		assertThat("60W42AB".removeSuffix("A", "B", "AB", "CD"), is("60W42"))
		assertThat("60W42A".removeSuffix("A", "B", "AB", "CD"), is("60W42"))
		assertThat("60W42B".removeSuffix("A", "B", "AB", "CD"), is("60W42"))
		assertThat("60W42CD".removeSuffix("A", "B", "AB", "CD"), is("60W42"))
		assertThat("60W42".removeSuffix("A", "B", "AB", "CD"), is("60W42"))
	}
}
