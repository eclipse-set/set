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
import org.eclipse.set.model.test.site.SitePackage
import org.eclipse.set.test.utils.SiteBuilder
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import static org.hamcrest.MatcherAssert.*
import static org.hamcrest.core.Is.*

/**
 * Tests for {@link CopyValue}.
 *  
 * @author Schaefer
 */
class CopyValueTest {

	var DefaultAutofill autofill
	var Site site

	@BeforeEach
	def void setUp() {
		autofill = new DefaultAutofill(null)
		site = createSiteModel
	}

	private def Site createSiteModel() {
		return SiteBuilder.createSite.addBuilding(
			"",
			"",
			""
		).build
	}

	@Test
	def void testEmpty() {
		val builder = SiteBuilder.use(site).selectByName("")
		val building = builder.selectedBuilding
		val trigger = new DefaultTrigger
		autofill.addFillingInstruction(
			new CopyValue(
				trigger,
				new FillFeatureSetting(building.names,
					SitePackage.eINSTANCE.buildingNames_Address),
				new FillFeatureSetting(building.names,
					SitePackage.eINSTANCE.buildingNames_Entrance)
			)
		)
		building.names.address = "address"
		assertThat(building.names.address, is("address"))
		assertThat(building.names.entrance, is(""))
		assertThat(building.names.sitePlan, is(""))
		trigger.activate();
		assertThat(building.names.address, is("address"))
		assertThat(building.names.entrance, is("address"))
		assertThat(building.names.sitePlan, is(""))
	}

	@Test
	def void testNotEmpty() {
		val builder = SiteBuilder.use(site).selectByName("")
		val building = builder.selectedBuilding
		val trigger = new DefaultTrigger
		autofill.addFillingInstruction(
			new CopyValue(
				trigger,
				new FillFeatureSetting(building.names,
					SitePackage.eINSTANCE.buildingNames_Address),
				new FillFeatureSetting(building.names,
					SitePackage.eINSTANCE.buildingNames_Entrance)
			)
		)
		building.names.address = "address"
		building.names.entrance = "some other address"
		assertThat(building.names.address, is("address"))
		assertThat(building.names.entrance, is("some other address"))
		assertThat(building.names.sitePlan, is(""))
		trigger.activate();
		assertThat(building.names.address, is("address"))
		assertThat(building.names.entrance, is("address"))
		assertThat(building.names.sitePlan, is(""))
	}
}
