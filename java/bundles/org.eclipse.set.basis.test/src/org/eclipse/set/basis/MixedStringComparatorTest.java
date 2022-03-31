/**
 * Copyright (c) 2016 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.basis;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test for {@link MixedStringComparator}.
 * 
 * @author Schaefer
 */
@SuppressWarnings("nls")
class MixedStringComparatorTest {

	private String signature;
	private String[] values;

	/**
	 * Initialize test.
	 */
	@BeforeEach
	public void setUp() {
		signature = "(?<number>[0-9]*)(?<letters>[A-Za-z]*)";
		values = new String[] { "9", "9a", "10", "10b", "10c", "14", "160" };
	}

	/**
	 * Test method for {@link MixedStringComparator#compare(String, String)}.
	 */
	@Test
	void testCompare() {
		final MixedStringComparator mixedStringComparator = new MixedStringComparator(
				signature);
		assertEquals(1, mixedStringComparator.compare("9a", "9"));
		assertEquals(1, mixedStringComparator.compare("9b", "9aa"));
		assertEquals(0, mixedStringComparator.compare("10a", "10a"));
		assertEquals(-1, mixedStringComparator.compare("a", "1a"));
		assertEquals(-1, mixedStringComparator.compare("4", "12"));
		for (int i = 0; i < values.length - 1; i++) {
			System.out.println(i);
			assertEquals(-1,
					mixedStringComparator.compare(values[i], values[i + 1]));
		}
	}

	/**
	 * Test method for
	 * {@link MixedStringComparator#MixedStringComparator(java.lang.String)}.
	 */
	@Test
	void testMixedStringComparator() {
		@SuppressWarnings("unused")
		final MixedStringComparator mixedStringComparator = new MixedStringComparator(
				signature);
		// no exception expected
	}
}
