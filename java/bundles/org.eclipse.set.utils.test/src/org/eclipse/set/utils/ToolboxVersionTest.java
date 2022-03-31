/**
 * Copyright (c) 2017 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Tests for {@link ToolboxVersion}.
 * 
 * @author Schaefer
 */
@SuppressWarnings("nls")
public class ToolboxVersionTest {

	/**
	 * Test method for {@link ToolboxVersion#getLongVersion()}.
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testGetLongVersion() {
		assertEquals("44.0.0.201707131048R11583",
				new ToolboxVersion("44.0.0.201707131048R11583")
						.getLongVersion());
		assertEquals("44.0.0R11585",
				new ToolboxVersion("44.0.0R11585").getLongVersion());
		assertEquals("44.0R11585",
				new ToolboxVersion("44.0R11585").getLongVersion());
		assertEquals("44.0.0.1.1R11585",
				new ToolboxVersion("44.0.0.1.1R11585").getLongVersion());
		assertEquals("X", new ToolboxVersion("X").getLongVersion());
		assertEquals("", new ToolboxVersion("").getLongVersion());
		assertEquals("-", new ToolboxVersion(null).getLongVersion());
	}

	/**
	 * Test method for {@link ToolboxVersion#getShortVersion()}.
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testGetShortVersion() {
		assertEquals("44.0.0", new ToolboxVersion("44.0.0.201707131048R11583")
				.getShortVersion());
		assertEquals("44.0.0",
				new ToolboxVersion("44.0.0R11585").getShortVersion());
		assertEquals("44.0.11585",
				new ToolboxVersion("44.0R11585").getShortVersion());
		assertEquals("44.0.0",
				new ToolboxVersion("44.0.0.1.1R11585").getShortVersion());
		assertEquals("X", new ToolboxVersion("X").getShortVersion());
		assertEquals("", new ToolboxVersion("").getShortVersion());
		assertEquals("-", new ToolboxVersion(null).getShortVersion());
	}

	/**
	 * Test method for {@link ToolboxVersion#isAvailable()}.
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testIsAvailable() {
		assertTrue(new ToolboxVersion("").isAvailable());
		assertFalse(new ToolboxVersion(null).isAvailable());
	}

	/**
	 * Test method for {@link ToolboxVersion#ToolboxVersion(java.lang.String)}.
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testToolboxVersion() {
		final ToolboxVersion toolboxVersion = new ToolboxVersion("");
		assertNotNull(toolboxVersion);
	}

}
