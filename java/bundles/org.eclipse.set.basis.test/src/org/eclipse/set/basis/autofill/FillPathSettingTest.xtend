/**
 * Copyright (c) 2018 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.basis.autofill

import org.eclipse.set.model.test.site.Site
import org.eclipse.set.model.test.site.SiteFactory
import org.eclipse.set.model.test.site.SitePackage
import org.eclipse.set.test.utils.SiteBuilder
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import static org.hamcrest.MatcherAssert.assertThat
import static org.hamcrest.core.Is.*
import static org.hamcrest.core.IsNull.*

/**
 * Tests for {@link FillPathSetting}.
 *  
 * @author Schaefer
 */
class FillPathSettingTest {

	var Site site

	@BeforeEach
	def void setUp() {
		site = createSiteModel
	}

	private def Site createSiteModel() {
		return SiteBuilder.createSite.addBuilding(
			"1",
			"2",
			"3"
		).build
	}

	@Test
	def void testGetValue() {
		val builder = SiteBuilder.use(site).selectByName("1")
		val building = builder.selectedBuilding
		val setting = new FillPathSetting(
			SiteFactory.eINSTANCE,
			building,
			SitePackage.eINSTANCE.building_Names,
			SitePackage.eINSTANCE.buildingNames_Entrance
		)
		assertThat(setting.value, is("2"))
	}

	@Test
	def void testSetValue() {
		val building = SiteFactory.eINSTANCE.createBuilding
		val setting = new FillPathSetting(
			SiteFactory.eINSTANCE,
			building,
			SitePackage.eINSTANCE.building_Names,
			SitePackage.eINSTANCE.buildingNames_Entrance
		)
		assertThat(building.names, nullValue)
		setting.setValue(null, "not null")
		assertThat(building.names.entrance, is("not null"))
	}
}
