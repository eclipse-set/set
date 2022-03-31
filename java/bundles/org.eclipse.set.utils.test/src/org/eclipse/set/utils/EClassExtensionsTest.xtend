/**
 * Copyright (c) 2018 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.utils

import org.eclipse.set.model.test.site.SitePackage
import org.junit.jupiter.api.Test

import static org.hamcrest.MatcherAssert.assertThat
import static org.hamcrest.core.Is.*

import static extension org.eclipse.set.utils.EClassExtensions.*

/**
 * Tests for {@link EClassExtensions}.
 * 
 * @author Schaefer
 */
class EClassExtensionsTest {

	/**
	 * Test for {@link EClassExtensions#getAttributePaths(EClass, boolean)}.
	 */
	@Test
	def void testGetAttributePaths() {
		val listPath = SitePackage.eINSTANCE.room.getAttributePaths(true).toSet
		val noListPath = SitePackage.eINSTANCE.room.getAttributePaths(false).
			toSet
		assertThat(
			listPath,
			is(
				#[
					#["guid"],
					#["floorID"],
					#["names", "doorSign"],
					#["names", "floorPlan"],
					#["windows", "position"],
					#["windows", "type"]
				].toSet
			)
		)
		assertThat(
			noListPath,
			is(
				#[
					#["guid"],
					#["floorID"],
					#["names", "doorSign"],
					#["names", "floorPlan"],
					#["windows"]
				].toSet
			)
		)
	}
}
