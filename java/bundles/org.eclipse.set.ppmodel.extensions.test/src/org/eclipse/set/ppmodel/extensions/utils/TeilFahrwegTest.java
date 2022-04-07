/**
 * Copyright (c) 2016 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.ppmodel.extensions.utils;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;

import org.eclipse.set.core.services.Services;
import org.eclipse.set.core.services.cache.NoCacheService;
import org.eclipse.set.ppmodel.extensions.FahrwegExtensions;
import org.eclipse.set.toolboxmodel.Basisobjekte.Punkt_Objekt;
import org.eclipse.set.toolboxmodel.Fahrstrasse.Fstr_Fahrweg;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link TeilFahrweg}.
 * 
 * @author Schaefer
 */
@SuppressWarnings("nls")
@Disabled
public class TeilFahrwegTest extends ModelBaseTest {

	private static final String PLAN_PRO_FILE = "testdaten/2015-12-10_P-Hausen-Zustand_1.8.0.1_4.0.xml";

	/**
	 * Global initialization before all tests.
	 */
	@BeforeAll
	public static void setUpBeforeClass() {
		loadPlanProContainer(PLAN_PRO_FILE);
		Services.setCacheService(new NoCacheService());
	}

	private Punkt_Objekt end;

	private Fstr_Fahrweg fahrweg;

	private Punkt_Objekt start;

	/**
	 * Initialization before each test.
	 */
	@BeforeEach
	public void setUp() {
		fahrweg = container.getFstrFahrweg().iterator().next();
		start = FahrwegExtensions.getStart(fahrweg);
		end = FahrwegExtensions.getZielSignal(fahrweg);
	}

	/**
	 * Test for
	 * {@link TeilFahrweg#TeilFahrweg(Fstr_Fahrweg, Punkt_Objekt, Punkt_Objekt)}
	 * .
	 */
	@Test
	public void testTeilFahrweg() {
		final TeilFahrweg localTeilFahrweg = new TeilFahrweg(fahrweg, start,
				end);
		assertNotNull(localTeilFahrweg);
		assertSame(fahrweg, localTeilFahrweg.bereichObjekt);
	}
}
