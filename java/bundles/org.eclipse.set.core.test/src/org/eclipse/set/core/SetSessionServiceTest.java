/**
 * Copyright (c) 2020 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */

package org.eclipse.set.core;

import java.nio.file.Path;

import org.eclipse.set.feature.validation.session.SetSessionService;
import org.eclipse.set.unittest.utils.session.AbstractSessionServiceTest;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link SetSessionService}.
 * 
 * @author Schaefer
 */
class SetSessionServiceTest extends AbstractSessionServiceTest {

	/**
	 * Test method for {@link SetSessionService#getFormat(Path)}.
	 */
	@Test
	void testGetFormat() {
		givenSetSessionService();

		whenGetFormat(XML_PATH);
		thenExpectPlainFile();
		thenExpectDefaultExtension(PP_XML);

		whenGetFormat(ZIP_PATH);
		thenExpectZippedPlanProFile();
		thenExpectDefaultExtension(PLANPRO);

		whenGetFormat(TEMPORARY_INTEGRATION_PATH);
		thenExpectTemporaryIntegrationFile();
		thenExpectDefaultExtension(M_PLANPRO);
	}

	/**
	 * Test method for {@link SetSessionService#getInitializationFormat()}.
	 */
	@Test
	void testGetInitializationFormat() {
		givenSetSessionService();
		whenGetInitializationFormat();
		thenExpectZippedPlanProFile();
		thenExpectDefaultExtension(PLANPRO);
	}

	/**
	 * Test method for {@link SetSessionService#getMergedFileFormat()}.
	 */
	@Test
	void testGetMergedFileFormat() {
		givenSetSessionService();
		whenGetMergedFileFormat();
		thenExpectZippedPlanProFile();
		thenExpectDefaultExtension(PLANPRO);
	}

	/**
	 * Test method for {@link SetSessionService#getPlainPlanProFormat()}.
	 */
	@Test
	void testGetPlainPlanProFormat() {
		givenSetSessionService();
		whenGetPlainPlanProFormat();
		thenExpectPlainFile();
		thenExpectDefaultExtension(PP_XML);
	}

	/**
	 * Test method for {@link SetSessionService#getZippedPlanProFormat()}.
	 */
	@Test
	void testGetZippedPlanProFormat() {
		givenSetSessionService();
		whenGetZippedPlanProFormat();
		thenExpectZippedPlanProFile();
		thenExpectDefaultExtension(PLANPRO);
	}

	private void givenSetSessionService() {
		testee = new SetSessionService();
	}
}
