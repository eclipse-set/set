/**
 * Copyright (c) 2017 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.utils.math;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests for {@link AgateRounding}.
 * 
 * @author Schaefer
 */
@SuppressWarnings("static-method")
public class AgateRoundingTest {

	private static final double DELTA = 1e-9;

	/**
	 * Tests for {@link AgateRounding#roundDown(double)}.
	 */
	@Test
	public void testRoundDown() {
		assertEquals(0.0, AgateRounding.roundDown(0.01), DELTA);
		assertEquals(0.0, AgateRounding.roundDown(0.09), DELTA);
		assertEquals(0.0, AgateRounding.roundDown(0.1), DELTA);
		assertEquals(0.0, AgateRounding.roundDown(0.11), DELTA);
		assertEquals(0.0, AgateRounding.roundDown(0.89), DELTA);
		assertEquals(1.0, AgateRounding.roundDown(0.9), DELTA);
		assertEquals(1.0, AgateRounding.roundDown(0.91), DELTA);
	}

	/**
	 * Tests for {@link AgateRounding#roundUp(double)}.
	 */
	@Test
	public void testRoundUp() {
		assertEquals(0.0, AgateRounding.roundUp(0.01), DELTA);
		assertEquals(0.0, AgateRounding.roundUp(0.09), DELTA);
		assertEquals(0.0, AgateRounding.roundUp(0.1), DELTA);
		assertEquals(1.0, AgateRounding.roundUp(0.11), DELTA);
		assertEquals(1.0, AgateRounding.roundUp(0.89), DELTA);
		assertEquals(1.0, AgateRounding.roundUp(0.9), DELTA);
		assertEquals(1.0, AgateRounding.roundUp(0.91), DELTA);
	}
}
