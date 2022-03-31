/**
 * Copyright (c) 2017 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.ppmodel.extensions;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Tests for {@link BereichObjektExtensions}.
 * 
 * @author Schaefer
 */
public class BereichObjektExtensionsTest {

	/**
	 * Test for
	 * {@link BereichObjektExtensions#intersects(double, double, double, double)}
	 * .
	 */
	@SuppressWarnings({ "static-method", "javadoc" })
	@Test
	public void testIntersectsDoubleDoubleDoubleDouble() {
		assertFalse(BereichObjektExtensions.intersects(-10, -1, 1, 10));
		assertFalse(BereichObjektExtensions.intersects(-10, 0.5, 1, 10));
		assertTrue(BereichObjektExtensions.intersects(-10, 1, 1, 10));
		assertTrue(BereichObjektExtensions.intersects(-10, 5, 1, 10));
		assertTrue(BereichObjektExtensions.intersects(4, 5, 1, 10));
		assertTrue(BereichObjektExtensions.intersects(4, 20, 1, 10));
		assertTrue(BereichObjektExtensions.intersects(10, 20, 1, 10));
		assertFalse(BereichObjektExtensions.intersects(11, 20, 1, 10));
	}
}
