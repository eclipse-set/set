/**
 * Copyright (c) 2026 DB InfraGO AG and others
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 */
package org.eclipse.set.nattable.utils.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.stream.Stream;

import org.apache.fop.hyphenation.HyphenationTree;
import org.eclipse.set.nattable.utils.Hyphenator;
import org.eclipse.set.nattable.utils.PlanProRichTextPainter;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.ArgumentMatchers;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

/**
 * 
 */
@SuppressWarnings("boxing")
public class PlanProRichTextPainterTest {

	private static Stream<Arguments> getTestSpecialString() {
		return Stream.of(Arguments.of("a  b\tc\n", "a  b\tc\n", 2),
				Arguments.of("123456789", "123456789", 3));
	}

	private static Stream<Arguments> getTestStringWithOutSeparator() {
		return Stream.of(
				Arguments.of("Aussenelementansteuerung",
						"Aussenelementansteuerung", 30),
				Arguments.of("Aussenelementansteuerung",
						"Aussenelement-<br></br>ansteuerung", 14),
				Arguments.of("Aussenelementansteuerung",
						"Aussen-<br></br>element-<br></br>ansteue-<br></br>rung",
						8),
				Arguments.of("Aussenelementansteuerung",
						"Aussenelementansteue-<br></br>rung", 22));
	}

	private static Stream<Arguments> getTestStringWithUnterlineSeparator() {
		return Stream.of(
				Arguments.of("Aus_sen_element_ansteue_rung",
						"Aus_sen_element_<br></br>ansteue_rung", 17),
				Arguments.of(
						"Aussenelementansteuerung_Aussenelementansteuerung",
						"Aussenelemen-<br></br>tansteuerung_<br></br>Aussenelement-<br></br>ansteuerung",
						14));
	}

	GC gc;

	String hyphenatedStr;

	Hyphenator hyphenator;

	PlanProRichTextPainter testee;

	private void thenExpectEqual(final String expect) {
		assertEquals(expect, hyphenatedStr);
	}

	private void whenHyphenateText(final String origin,
			final int avaiableLength) {
		hyphenatedStr = testee.hyphenationText(origin, gc, avaiableLength);
	}

	void givenGC() {
		gc = Mockito.mock(GC.class);
		Mockito.when(gc.textExtent(ArgumentMatchers.anyString()))
				.thenAnswer(invocate -> {
					if (invocate.getArgument(0) instanceof final String word) {
						return new Point(word.length(), 0);
					}
					throw new IllegalArgumentException();
				});
	}

	void givenHyphenator()
			throws FileNotFoundException, IOException, ClassNotFoundException {
		try (var fileInputStream = new FileInputStream(
				new File("hyph/de.hyp"))) {
			final ObjectInputStream objectInputStream = new ObjectInputStream(
					fileInputStream);
			if (objectInputStream
					.readObject() instanceof final HyphenationTree hyphenationTree) {
				hyphenator = new Hyphenator(hyphenationTree);
			}
		}
	}

	void givenPlanProRichTextPainter() {
		testee = new PlanProRichTextPainter(false);
	}

	@ParameterizedTest
	@MethodSource("getTestSpecialString")
	void testHyphenationSpeicalText(final String origin, final String expect,
			final int availableLength)
			throws FileNotFoundException, ClassNotFoundException, IOException {
		givenHyphenator();
		try (MockedStatic<Hyphenator> mockStatic = Mockito
				.mockStatic(Hyphenator.class)) {
			mockStatic.when(Hyphenator::createInstance).thenReturn(hyphenator);
			givenPlanProRichTextPainter();
			givenGC();
			whenHyphenateText(origin, availableLength);
			thenExpectEqual(expect);
		}
	}

	@Test
	void testHyphenationTextDoesNotEmitEmptyFragmentWhenRestIsNegative()
			throws FileNotFoundException, ClassNotFoundException, IOException {
		givenHyphenator();
		try (MockedStatic<Hyphenator> mockStatic = Mockito
				.mockStatic(Hyphenator.class)) {
			mockStatic.when(Hyphenator::createInstance).thenReturn(hyphenator);
			givenPlanProRichTextPainter();
			givenGC();
			whenHyphenateText("foo_Aussenelementansteuerung", 4);
			assertFalse(hyphenatedStr.startsWith("-<br></br>"));
		}
	}

	@ParameterizedTest
	@MethodSource("getTestStringWithOutSeparator")
	void testHyphenationTextWithoutSeperator(final String origin,
			final String expect, final int avaiableLength)
			throws FileNotFoundException, ClassNotFoundException, IOException {
		givenHyphenator();
		try (MockedStatic<Hyphenator> mockStatic = Mockito
				.mockStatic(Hyphenator.class)) {
			mockStatic.when(Hyphenator::createInstance).thenReturn(hyphenator);
			givenPlanProRichTextPainter();
			givenGC();
			whenHyphenateText(origin, avaiableLength);
			thenExpectEqual(expect);
		}
	}

	@ParameterizedTest
	@MethodSource("getTestStringWithUnterlineSeparator")
	void testHyphenationTextWithUnterline(final String origin,
			final String expect, final int avaiableLength)
			throws FileNotFoundException, ClassNotFoundException, IOException {
		givenHyphenator();
		try (MockedStatic<Hyphenator> mockStatic = Mockito
				.mockStatic(Hyphenator.class)) {
			mockStatic.when(Hyphenator::createInstance).thenReturn(hyphenator);
			givenPlanProRichTextPainter();
			givenGC();
			whenHyphenateText(origin, avaiableLength);
			thenExpectEqual(expect);
		}
	}
}
