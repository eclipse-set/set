/**
 * Copyright (c) 2020 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */

package org.eclipse.set.unittest.utils.session;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Paths;

import org.eclipse.set.basis.files.ToolboxFile.Format;
import org.eclipse.set.core.services.session.SessionService;

/**
 * Common session service test.
 * 
 * @author Schaefer
 */
@SuppressWarnings("nls")
public class AbstractSessionServiceTest {

	protected static final String M_PLANPRO = "mplanpro";
	protected static final String PLANPRO = "planpro";
	protected static final String PP_XML = "ppxml";
	protected static final String TEMPORARY_INTEGRATION_PATH = "a/b/c.mplanpro";
	protected static final String XML_PATH = "a/b/c.xml";
	protected static final String ZIP_PATH = "a/b/c.planpro";

	protected Format format;
	protected SessionService testee;

	protected void thenExpectDefaultExtension(final String expectedExtension) {
		assertThat(testee.getDefaultExtension(format), is(expectedExtension));
	}

	protected void thenExpectPlainFile() {
		assertTrue(format.isPlain());
		assertFalse(format.isTemporaryIntegration());
		assertFalse(format.isZippedPlanPro());
	}

	protected void thenExpectTemporaryIntegrationFile() {
		assertFalse(format.isPlain());
		assertTrue(format.isTemporaryIntegration());
		assertTrue(format.isZippedPlanPro());
	}

	protected void thenExpectZippedPlanProFile() {
		assertFalse(format.isPlain());
		assertFalse(format.isTemporaryIntegration());
		assertTrue(format.isZippedPlanPro());
	}

	protected void whenGetFormat(final String path) {
		format = testee.getFormat(Paths.get(path));
	}

	protected void whenGetInitializationFormat() {
		format = testee.getInitializationFormat();
	}

	protected void whenGetMergedFileFormat() {
		format = testee.getMergedFileFormat();
	}

	protected void whenGetPlainPlanProFormat() {
		format = testee.getPlainPlanProFormat();
	}

	protected void whenGetZippedPlanProFormat() {
		format = testee.getZippedPlanProFormat();
	}

}
