/**
 * Copyright (c) 2017 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.utils.math;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

import java.math.BigDecimal;
import java.util.Locale;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link BigDecimalExtensions}.
 * 
 * @author Schaefer
 */
@SuppressWarnings({ "static-method", "nls" })
public class BigDecimalExtensionsTest {

	/**
	 * Set up.
	 */
	@BeforeEach
	public void setUp() {
		Locale.setDefault(Locale.GERMANY);
	}

	/**
	 * Tests for {@link BigDecimalExtensions#toTableDecimal(BigDecimal)}.
	 */
	@Test
	public void testToolboxIntegerFormat() {
		assertThat(BigDecimalExtensions.toTableInteger(BigDecimal.valueOf(200)),
				is("200"));
		assertThat(BigDecimalExtensions.toTableInteger(BigDecimal.valueOf(0)),
				is("0"));
		assertThat(BigDecimalExtensions.toTableInteger(BigDecimal.valueOf(1)),
				is("1"));
		assertThat(BigDecimalExtensions.toTableInteger(BigDecimal.valueOf(99)),
				is("99"));
	}

	/**
	 * Tests for {@link BigDecimalExtensions#toTableDecimal(BigDecimal)}.
	 */
	@Test
	public void testToolboxLengthFormat() {
		assertThat(
				BigDecimalExtensions.toTableDecimal(BigDecimal.valueOf(0.001)),
				is("0,001"));
		assertThat(
				BigDecimalExtensions.toTableDecimal(BigDecimal.valueOf(0.01)),
				is("0,01"));
		assertThat(
				BigDecimalExtensions.toTableDecimal(BigDecimal.valueOf(0.0001)),
				is("0"));
		assertThat(
				BigDecimalExtensions.toTableDecimal(BigDecimal.valueOf(1000.1)),
				is("1000,1"));
	}
}
