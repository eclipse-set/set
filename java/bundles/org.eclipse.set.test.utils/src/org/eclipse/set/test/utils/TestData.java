/**
 * Copyright (c) 2022 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */

package org.eclipse.set.test.utils;

/**
 * Definition of input and expected results for unit tests.
 *
 * @param <I>
 *            type of input data
 * @param <E>
 *            type of expected data
 * 
 * @author Schaefer
 */
public class TestData<I, E> {

	private I input;
	private E expected;

	/**
	 * @param input
	 *            the input data
	 * @param expected
	 *            the expected data
	 */
	public TestData(final I input, final E expected) {
		this.input = input;
		this.expected = expected;
	}

	/**
	 * @return the input data
	 */
	public I getInput() {
		return input;
	}

	/**
	 * @return the expected data
	 */
	public E getExpected() {
		return expected;
	}

	/**
	 * @param input
	 *            the input data
	 *
	 * @return the test data
	 */
	public TestData<I, E> setInput(final I input) {
		this.input = input;
		return this;
	}

	/**
	 * @param expected
	 *            the expected data
	 *
	 * @return the test data
	 */
	public TestData<I, E> setExpected(final E expected) {
		this.expected = expected;
		return this;
	}

	@SuppressWarnings("nls")
	@Override
	public String toString() {
		return "TestData [input=" + input + ", expected=" + expected + "]";
	}
}
