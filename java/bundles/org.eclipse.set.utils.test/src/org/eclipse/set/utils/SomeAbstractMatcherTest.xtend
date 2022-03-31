/**
 * Copyright (c) 2018 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.utils

import org.eclipse.set.basis.integration.Matcher
import org.eclipse.set.model.test.site.SiteFactory
import org.eclipse.set.model.test.site.WindowType
import org.eclipse.set.utils.testing.SiteMergeConfiguration
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import static org.hamcrest.MatcherAssert.assertThat
import static org.hamcrest.core.Is.*
import static org.junit.jupiter.api.Assertions.*

import static extension org.eclipse.set.basis.extensions.ByteExtensions.*

/**
 * Tests for {@link AbstractMatcher}.
 * 
 * @author Schaefer
 */
class SomeAbstractMatcherTest {
	var Matcher matcher = null

	@BeforeEach
	def void before() {
		val config = new SiteMergeConfiguration
		matcher = config.matcher
		assertTrue(matcher instanceof AbstractMatcher)
	}

	@Test
	def void testGetNonEmptyAttributes() {
		val first = SiteFactory.eINSTANCE.createBuildingNames
		val second = SiteFactory.eINSTANCE.createBuildingNames
		assertThat(matcher.getNonEmptyAttributes(first, second), is(#[]))
		first.address = "dummy address"
		assertThat(matcher.getNonEmptyAttributes(first, second),
			is(#[#["address"]]))
		first.entrance = "test"
		second.entrance = "test"
		assertThat(matcher.getNonEmptyAttributes(first, second),
			is(#[#["address"], #["entrance"]]))
	}

	@Test
	def void testIsDifferentStringAndEnums() {
		val first = SiteFactory.eINSTANCE.createWindow
		val second = SiteFactory.eINSTANCE.createWindow
		assertFalse(matcher.isDifferent(first, second))
		first.position = "dummy position"
		assertTrue(matcher.isDifferent(first, second))
		second.position = "dummy position"
		assertFalse(matcher.isDifferent(first, second))
		first.type = WindowType.BARRED
		assertTrue(matcher.isDifferent(first, second))
		second.type = WindowType.BARRED
		assertFalse(matcher.isDifferent(first, second))
	}

	@Test
	def void testIsDifferentLists() {
		val first = SiteFactory.eINSTANCE.createBuilding
		val second = SiteFactory.eINSTANCE.createBuilding
		assertFalse(matcher.isDifferent(first, second))
		first.buildingsInTheSameStreet.add(first)
		assertTrue(matcher.isDifferent(first, second))
		second.buildingsInTheSameStreet.add(first)
		assertFalse(matcher.isDifferent(first, second))
		first.buildingsInTheSameStreet.add(second)
		assertTrue(matcher.isDifferent(first, second))
		second.buildingsInTheSameStreet.add(second)
		assertFalse(matcher.isDifferent(first, second))
	}

	@Test
	def void testIsDifferentArrays() {
		val first = SiteFactory.eINSTANCE.createBuilding
		val second = SiteFactory.eINSTANCE.createBuilding
		assertFalse(matcher.isDifferent(first, second))
		first.data = #[0.b, 1.b, 2.b]
		assertTrue(matcher.isDifferent(first, second))
		second.data = #[0.b, 1.b, 2.b]
		assertFalse(matcher.isDifferent(first, second))
		first.data = #[0.b, 1.b, 2.b, 3.b]
		assertTrue(matcher.isDifferent(first, second))
		second.data = #[0.b, 1.b, 2.b, 3.b]
		assertFalse(matcher.isDifferent(first, second))
	}
}
