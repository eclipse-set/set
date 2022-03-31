/**
 * Copyright (c) 2018 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.integration

import org.eclipse.set.application.nameservice.PlanProNameService
import org.eclipse.set.basis.integration.DiffLabelProvider
import org.eclipse.set.core.services.Services
import org.eclipse.set.core.services.cache.NoCacheService
import org.eclipse.set.core.services.name.NameService
import org.eclipse.set.ppmodel.extensions.utils.ModelBaseTest
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test

import static org.hamcrest.MatcherAssert.assertThat
import static org.hamcrest.core.Is.*

@Disabled
class PlanProLabelProviderTest extends ModelBaseTest {
	static final String PLAN_PRO_FILE = "testdaten/2015-12-10_P-Hausen-Zustand_1.8.0.1_4.0.xml";

	val DiffLabelProvider provider = new PlanProLabelProvider(
		createNameService())

	def NameService createNameService() {
		val service = new PlanProNameService()
		return service
	}

	/**
	 * Global initialization before all tests.
	 */
	@BeforeAll
	def static void setUpBeforeClass() {
		ModelBaseTest.loadPlanProContainer(PLAN_PRO_FILE);
		Services.setCacheService(new NoCacheService());
	}

	@Test
	def void testGetElementLabel() {
		val fstrZugRangier = ModelBaseTest.container.fstrZugRangier
		for (var int i = 0; i < fstrZugRangier.size; i++) {
			println('''«i» : «provider.getElementLabel(fstrZugRangier.get(i))»''')
		}
		assertThat(
			provider.getElementLabel(
				ModelBaseTest.container.fstrZugRangier.get(76)),
			is("Zug-Umfahrstraße \"60G/60L108Y [60W29] (D1)\""))
		assertThat(
			provider.getElementLabel(ModelBaseTest.container.TOPKante.get(40)),
			is("Top-Kante \"?/?\""))
		assertThat(
			provider.getElementLabel(
				ModelBaseTest.container.gleisAbschnitt.get(9)),
			is("Gleisabschnitt \"60G111\""))
	}

}
