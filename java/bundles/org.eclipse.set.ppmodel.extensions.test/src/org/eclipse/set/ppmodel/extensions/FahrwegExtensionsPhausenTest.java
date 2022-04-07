/**
 * Copyright (c) 2016 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.ppmodel.extensions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.util.List;

import org.eclipse.set.core.services.Services;
import org.eclipse.set.core.services.cache.NoCacheService;
import org.eclipse.set.ppmodel.extensions.utils.ModelBaseTest;
import org.eclipse.set.ppmodel.extensions.utils.TeilFahrweg;
import org.eclipse.set.toolboxmodel.Basisobjekte.Punkt_Objekt;
import org.eclipse.set.toolboxmodel.Fahrstrasse.Fstr_Abhaengigkeit;
import org.eclipse.set.toolboxmodel.Fahrstrasse.Fstr_Fahrweg;
import org.eclipse.set.toolboxmodel.Signale.Signal;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link FahrwegExtensions}.
 * 
 * @author Schaefer
 */
@SuppressWarnings("nls")
@Disabled
public class FahrwegExtensionsPhausenTest extends ModelBaseTest {

	private static final String FIRST_FAHRWEG_ABHAENGIGKEIT_GUID = "B3117456-E07F-4656-B631-E9A70E98AD57";

	private static final String FIRST_FAHRWEG_GUID = "36EF4A47-E5A6-43BD-A66F-531ED3F676FF";

	private static final String PLAN_PRO_FILE = "testdaten/2015-12-10_P-Hausen-Zustand_1.8.0.1_4.0.xml";

	/**
	 * Global initialization before all tests.
	 */
	@BeforeAll
	public static void setUpBeforeClass() {
		loadPlanProContainer(PLAN_PRO_FILE);
		Services.setCacheService(new NoCacheService());
	}

	private Fstr_Fahrweg fahrweg;

	/**
	 * Initialization before each test.
	 */
	@BeforeEach
	public void setUp() {
		fahrweg = container.getFstrFahrweg().iterator().next();
	}

	/**
	 * Test for {@link FahrwegExtensions#getAbhaengigkeiten(Fstr_Fahrweg)}.
	 */
	@Test
	public void testGetAbhaengigkeiten() {
		assertEquals(FIRST_FAHRWEG_GUID, fahrweg.getIdentitaet().getWert());
		final List<Fstr_Abhaengigkeit> abhaengigkeiten = FahrwegExtensions
				.getAbhaengigkeiten(fahrweg);
		assertEquals(1, abhaengigkeiten.size());
		final Fstr_Abhaengigkeit abhaengigkeit = abhaengigkeiten.get(0);
		assertEquals(FIRST_FAHRWEG_ABHAENGIGKEIT_GUID,
				abhaengigkeit.getIdentitaet().getWert());
	}

	/**
	 * Test for
	 * {@link FahrwegExtensions#getTeilFahrweg(Fstr_Fahrweg, Punkt_Objekt, Punkt_Objekt)}
	 * .
	 */
	@Test
	public void testGetTeilFahrweg() {
		final Punkt_Objekt start = FahrwegExtensions.getStart(fahrweg);
		final Signal end = FahrwegExtensions.getZielSignal(fahrweg);
		final TeilFahrweg segment = FahrwegExtensions.getTeilFahrweg(fahrweg,
				start, end);
		assertSame(fahrweg, segment.bereichObjekt);
	}
}
