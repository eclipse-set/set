/**
 * Copyright (c) 2021 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.basis;

import static org.eclipse.set.basis.NumericFirstComparatorDecoratorTest.CallStatus.IS_CALLED;
import static org.eclipse.set.basis.NumericFirstComparatorDecoratorTest.CallStatus.IS_NOT_CALLED;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Comparator;
import java.util.stream.Stream;

import org.eclipse.set.test.utils.TestData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Test for {@link NumericFirstComparatorDecorator}.
 * 
 * @author Stuecker
 */
@SuppressWarnings("nls")
class NumericFirstComparatorDecoratorTest {

	enum CallStatus {
		IS_CALLED, IS_NOT_CALLED
	}

	private NumericFirstComparatorDecorator testee;
	private Comparator<String> baseComparator;
	private int compareResult;
	private int baseCompareResult;

	private CallStatus comparatorCallStatus = IS_NOT_CALLED;

	@BeforeEach
	private void beforeEach() {
		comparatorCallStatus = CallStatus.IS_NOT_CALLED;
	}

	/**
	 * Test whether the sorting behavior changes for non-numeric strings
	 * compared to the default string comparator
	 */
	@ParameterizedTest
	@MethodSource
	void testCompareNonNumeric(final Pair<String, String> testData) {
		givenComparator(String::compareTo);
		givenDecoratedComparator();

		whenComparing(testData.getFirst(), testData.getSecond());
		thenResultIsIdenticalToBase();
	}

	private static Stream<Pair<String, String>> testCompareNonNumeric() {
		return Stream.of( //
				new Pair<>("a", "b"), //
				new Pair<>("a", "a"), //
				new Pair<>("b", "a"), //
				new Pair<>("10a", "10b"), //
				new Pair<>("10a", "10a"), //
				new Pair<>("10b", "10a") //
		);
	}

	/**
	 * Test whether the sorting behavior changes for comparisons between numeric
	 * and non-numeric strings compared to the default string comparator
	 */
	@ParameterizedTest
	@MethodSource
	void testCompareMixedNumeric(final Pair<String, String> testData) {
		givenComparator(String::compareTo);
		givenDecoratedComparator();

		whenComparing(testData.getFirst(), testData.getSecond());
		thenResultIsIdenticalToBase();
	}

	private static Stream<Pair<String, String>> testCompareMixedNumeric() {
		return Stream.of( //
				new Pair<>("a", "1"), //
				new Pair<>("1", "a") //
		);
	}

	/**
	 * Test whether the sorting behavior changes correctly for comparisons
	 * between numeric strings compared to the default string comparator
	 */
	@ParameterizedTest
	@MethodSource
	void testCompareNumeric(
			final TestData<Pair<String, String>, Integer> testData) {
		givenComparator(String::compareTo);
		givenDecoratedComparator();

		whenComparing( //
				testData.getInput().getFirst(),
				testData.getInput().getSecond());
		thenResultIs(testData.getExpected().intValue());
	}

	private static Stream<TestData<Pair<String, String>, Integer>> testCompareNumeric() {
		return Stream.of( //
				new TestData<>(new Pair<>("1", "1"), Integer.valueOf(0)), //
				new TestData<>(new Pair<>("0", "1"), Integer.valueOf(-1)), //
				new TestData<>(new Pair<>("1", "0"), Integer.valueOf(1)) //
		);
	}

	/**
	 * Test whether the sorting is correctly passed to the decorated base
	 * comparator
	 */
	@ParameterizedTest
	@MethodSource
	void testCompareDecoratedComponent(
			final TestData<Pair<String, String>, CallStatus> testData) {
		givenDecoratedComparator((a, b) -> {
			comparatorCallStatus = IS_CALLED;
			return Comparator.nullsFirst(String::compareTo).compare(a, b);
		});

		whenComparing( //
				testData.getInput().getFirst(),
				testData.getInput().getSecond());

		thenExpectBaseComparatorCallStatus(testData.getExpected());
	}

	private static Stream<TestData<Pair<String, String>, CallStatus>> testCompareDecoratedComponent() {
		return Stream.of( //
				// The base comparator should not be called for numeric strings
				new TestData<>(new Pair<>("1", "1"), IS_NOT_CALLED), //

				// The base comparator should be called for mixed inputs
				new TestData<>(new Pair<>("1", "test"), IS_CALLED), //

				new TestData<>(new Pair<>("test", "test"), IS_CALLED), //

				// The base comparator is also called for edge cases like empty
				// strings and null
				new TestData<>(new Pair<>("1", null), IS_CALLED), //
				new TestData<>(new Pair<>(null, null), IS_CALLED), //
				new TestData<>(new Pair<>("0", ""), IS_CALLED) //
		);
	}

	private void givenComparator(final Comparator<String> comparator) {
		baseComparator = comparator;
	}

	private void givenDecoratedComparator() {
		testee = new NumericFirstComparatorDecorator(baseComparator);
	}

	private void givenDecoratedComparator(final Comparator<String> comparator) {
		testee = new NumericFirstComparatorDecorator(comparator);
	}

	private void whenComparing(final String a, final String b) {
		compareResult = testee.compare(a, b);
		if (baseComparator != null) {
			baseCompareResult = baseComparator.compare(a, b);
		}
	}

	private void thenResultIsIdenticalToBase() {
		assertEquals(baseCompareResult, compareResult,
				"Bad comparision result");
	}

	private void thenResultIs(final int result) {
		assertEquals(result, compareResult, "Bad comparision result");
	}

	private void thenExpectBaseComparatorCallStatus(final CallStatus expected) {
		assertEquals(expected, comparatorCallStatus);
	}
}
