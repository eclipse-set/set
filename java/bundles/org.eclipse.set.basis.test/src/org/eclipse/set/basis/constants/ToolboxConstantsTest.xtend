/**
 * Copyright (c) 2019 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.basis.constants

import org.junit.jupiter.api.Test

import static org.eclipse.set.basis.constants.ToolboxConstants.*
import static org.hamcrest.MatcherAssert.assertThat
import static org.hamcrest.core.Is.*

/** 
 * Tests for {@link ToolboxConstants}.
 * 
 * @author Schaefer
 */
class ToolboxConstantsTest {

	@Test
	def void test_LST_OBJECT_NAME_COMPARATOR_Kennzahl() {
		val sorted = #{"72y", "70111", "72x"}.sortWith(
			LST_OBJECT_NAME_COMPARATOR)
		assertThat(sorted, is(#["70111", "72x", "72y"]))
	}

	@Test
	def void test_LST_OBJECT_NAME_COMPARATOR_LettersOnly1() {
		var sorted = #{"c", "a", "b"}.sortWith(LST_OBJECT_NAME_COMPARATOR)
		assertThat(sorted, is(#["a", "b", "c"]))
	}

	@Test
	def void test_LST_OBJECT_NAME_COMPARATOR_LettersOnly2() {
		var sorted = #{"abc", "aba", "abb"}.sortWith(LST_OBJECT_NAME_COMPARATOR)
		assertThat(sorted, is(#["aba", "abb", "abc"]))
	}

	@Test
	def void test_LST_OBJECT_NAME_COMPARATOR_NumbersOnly() {
		var sorted = #{"4", "3", "7"}.sortWith(LST_OBJECT_NAME_COMPARATOR)
		assertThat(sorted, is(#["3", "4", "7"]))
	}

	@Test
	def void test_LST_OBJECT_NAME_COMPARATOR_MixedInput1() {
		var sorted = #{"70a", "103", "7"}.sortWith(LST_OBJECT_NAME_COMPARATOR)
		assertThat(sorted, is(#["7", "103", "70a"]))
	}

	@Test
	def void test_LST_OBJECT_NAME_COMPARATOR_MixedInput2() {
		var sorted = #{"4", "3", "70a", "07a", "7"}.sortWith(
			LST_OBJECT_NAME_COMPARATOR)
		assertThat(sorted, is(#["3", "4", "7", "07a", "70a"]))
	}

	@Test
	def void test_LST_OBJECT_NAME_COMPARATOR_MixedInput3() {
		var sorted = #{"701", "700a", "3", "4a", "12345/67", "700"}.sortWith(
			LST_OBJECT_NAME_COMPARATOR)
		assertThat(sorted, is(#["3", "4a", "12345/67", "700", "700a", "701"]))
	}
}
