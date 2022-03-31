/**
 * Copyright (c) 2016 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.ppmodel.extensions;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import org.eclipse.set.ppmodel.extensions.utils.ModelBaseTest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import de.scheidtbachmann.planpro.model.model1902.Fahrstrasse.Fstr_DWeg;
import de.scheidtbachmann.planpro.model.model1902.Ortung.FMA_Anlage;

/**
 * Tests for {@link DwegExtensions}.
 * 
 * @author Schaefer
 */
@SuppressWarnings("nls")
@Disabled
public class DwegExtensionsTest extends ModelBaseTest {

	private static final String PLAN_PRO_FILE = "testdaten/2015-12-10_P-Hausen-Zustand_1.8.0.1_4.0.xml";

	/**
	 * Global initialization before all tests.
	 */
	@BeforeAll
	public static void setUpBeforeClass() {
		loadPlanProContainer(PLAN_PRO_FILE);
	}

	private Fstr_DWeg dweg;

	/**
	 * Initialization before each test.
	 */
	@BeforeEach
	public void setUp() {
		dweg = getFstrDweg("60P1 D1");
	}

	/**
	 * Test for {@link DwegExtensions#getFmaAnlageFreimeldung(Fstr_DWeg)}.
	 */
	@Test
	public void testRelevanteFmaAnlagen() {
		final Set<FMA_Anlage> relevanteFmaAnlagen = DwegExtensions
				.getFmaAnlageFreimeldung(dweg);
		for (final FMA_Anlage anlage : relevanteFmaAnlagen) {
			System.out.println(FmaAnlageExtensions.getGleisabschnitt(anlage)
					.getBezeichnung().getBezeichnungTabelle().getWert());
		}
		assertTrue(relevanteFmaAnlagen.size() > 0);
	}

	/**
	 * Test for {@link DwegExtensions#getFmaAnlageFreimeldung(Fstr_DWeg)}.
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testRelevanteFmaAnlagen60N21D1() {
		// relevante FMA Anlagen bestimmen
		final Fstr_DWeg dweg60N21D1 = getFstrDweg("60N21 D1");
		assertNotNull(dweg60N21D1);
		final Set<FMA_Anlage> relevanteFmaAnlagen = DwegExtensions
				.getFmaAnlageFreimeldung(dweg60N21D1);

		// erwartete FMA Anlage bestimmen
		final FMA_Anlage fmaAnlageG221 = getFMAAnlage("60G221");
		assertNotNull(fmaAnlageG221);

		// Erwartung überprüfen
		assertTrue(relevanteFmaAnlagen.contains(fmaAnlageG221));
	}

	/**
	 * Test for {@link DwegExtensions#getFmaAnlageFreimeldung(Fstr_DWeg)}.
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testRelevanteFmaAnlagen60P1D1() {
		// relevante FMA Anlagen bestimmen
		final Fstr_DWeg dweg60P1D1 = getFstrDweg("60P1 D1");
		assertNotNull(dweg60P1D1);
		final Set<FMA_Anlage> relevanteFmaAnlagen = DwegExtensions
				.getFmaAnlageFreimeldung(dweg60P1D1);

		// erwartete FMA Anlage bestimmen
		final FMA_Anlage fmaAnlageW1 = getFMAAnlage("60W1");
		assertNotNull(fmaAnlageW1);

		// Erwartung überprüfen
		assertTrue(relevanteFmaAnlagen.contains(fmaAnlageW1));
	}

	/**
	 * Test for {@link DwegExtensions#getFmaAnlageFreimeldung(Fstr_DWeg)}.
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testRelevanteFmaAnlagen60P3D1() {
		// relevante FMA Anlagen bestimmen
		final Fstr_DWeg dweg60P3D1 = getFstrDweg("60P3 D1");
		assertNotNull(dweg60P3D1);

		// Erwartung: keine Ausnahme (AreaNotContinuous)
	}

	/**
	 * Test for {@link DwegExtensions#getFmaAnlageFreimeldung(Fstr_DWeg)}.
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testRelevanteFmaAnlagen60ZU21() {
		// relevante FMA Anlagen bestimmen
		final Fstr_DWeg dweg60ZU21D1 = getFstrDweg("60ZU21 D1");
		assertNotNull(dweg60ZU21D1);
		final Set<FMA_Anlage> relevanteFmaAnlagen = DwegExtensions
				.getFmaAnlageFreimeldung(dweg60ZU21D1);

		// erwartete FMA Anlage bestimmen
		final FMA_Anlage fmaAnlageW33 = getFMAAnlage("60W33");
		assertNotNull(fmaAnlageW33);
		final FMA_Anlage fmaAnlageG301 = getFMAAnlage("60G301");
		assertNotNull(fmaAnlageG301);

		// Erwartung überprüfen
		assertTrue(relevanteFmaAnlagen.contains(fmaAnlageW33));
		assertTrue(relevanteFmaAnlagen.contains(fmaAnlageG301));
	}
}
