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

import static org.hamcrest.MatcherAssert.assertThat
import static org.hamcrest.core.Is.*

/**
 * Tests for {@link DefaultAutofill}.
 *  
 * @author Schaefer
 */
class DefaultAutofillTest {

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
	def void testAddFillingInstruction() {
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
		autofill.addFillingInstruction(
			new CopyValue(
				trigger,
				new FillFeatureSetting(building.names,
					SitePackage.eINSTANCE.buildingNames_Address),
				new FillFeatureSetting(building.names,
					SitePackage.eINSTANCE.buildingNames_SitePlan)
			)
		)
		building.names.address = "address"
		assertThat(building.names.address, is("address"))
		assertThat(building.names.entrance, is(""))
		assertThat(building.names.sitePlan, is(""))
		trigger.activate();
		assertThat(building.names.address, is("address"))
		assertThat(building.names.entrance, is("address"))
		assertThat(building.names.sitePlan, is("address"))
	}

	@Test
	def void testExecute() {
		val builder = SiteBuilder.use(site).selectByName("")
		val building = builder.selectedBuilding
		val copy = new CopyValue(
			null,
			new FillFeatureSetting(building.names,
				SitePackage.eINSTANCE.buildingNames_Address),
			new FillFeatureSetting(building.names,
				SitePackage.eINSTANCE.buildingNames_Entrance)
		)
		building.names.address = "address"
		assertThat(building.names.address, is("address"))
		assertThat(building.names.entrance, is(""))
		assertThat(building.names.sitePlan, is(""))
		autofill.execute(copy)
		assertThat(building.names.address, is("address"))
		assertThat(building.names.entrance, is("address"))
		assertThat(building.names.sitePlan, is(""))
	}
}
