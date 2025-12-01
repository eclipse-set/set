/**
 * Copyright (c) 2025 DB InfraGO AG and others
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 */
package org.eclipse.set.utils.table.test.sorting;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.regex.Pattern;

import org.eclipse.emf.ecore.EValidator.PatternMatcher;
import org.eclipse.set.basis.Pair;
import org.eclipse.set.model.planpro.BasisTypen.util.BasisTypenValidator;
import org.eclipse.set.model.planpro.Basisobjekte.Punkt_Objekt;
import org.eclipse.set.utils.table.sorting.CompareRouteAndKmCriterion;
import org.junit.jupiter.api.Test;

/**
 * Test class for {@link CompareRouteAndKmCriterion}
 */
public class CompareRouteAndKmCriterionTest {

	private static Pair<Double, Double> analyseKmValue(final String km) {
		if (isContainsExtraKm(km)) {
			final String extraLength = km.contains("+")
					? "+" + km.substring(km.indexOf("+") + 1)
					: "-" + km.substring(km.lastIndexOf("-") + 1);
			final String kilometer = km.replace(extraLength, "");
			final double doubleValue = Double
					.parseDouble(kilometer.replace(",", "."));
			final double extraLengthDouble = Double.parseDouble(extraLength);
			return new Pair<>(Double.valueOf(doubleValue),
					Double.valueOf(extraLengthDouble));
		}
		return new Pair<>(Double.valueOf(km.replace(",", ".")),
				Double.valueOf(0.0));
	}

	private static int compareKm(final Pair<Double, Double> first,
			final Pair<Double, Double> second) {
		final int mainValueCompare = first.getFirst()
				.compareTo(second.getFirst());
		if (mainValueCompare != 0) {
			return mainValueCompare;
		}
		return first.getSecond().compareTo(second.getSecond());
	}

	private static boolean isContainsExtraKm(final String km) {
		final String withoutPrefixKm = km.startsWith("-") ? km.substring(1)
				: km;
		return withoutPrefixKm.contains("-") || withoutPrefixKm.contains("+");
	}

	List<String> kilometrierungPatternInSchema;

	List<String> kmList;

	List<String> sortedKmList;

	CompareRouteAndKmCriterion testee;

	List<String> unsortKmList;

	private void givenSchemaPattern() {
		final List<String> patterns = Arrays.stream(
				BasisTypenValidator.KILOMETRIERUNG_TYPE__PATTERN__VALUES)
				.flatMap(Arrays::stream)
				.map(PatternMatcher::toString)
				.toList();
		kilometrierungPatternInSchema = new ArrayList<>();
		kilometrierungPatternInSchema.addAll(patterns);
	}

	/**
	 * Create random kilometer value, which match to Regex
	 * {@link BasisTypenValidator#KILOMETRIERUNG_TYPE__PATTERN__VALUES}
	 */
	private void givenUnsortKilometrierung() {
		final Random random = new Random();
		unsortKmList = new ArrayList<>();
		while (unsortKmList.size() < 100) {
			final boolean withoutExtraKm = Math.random() > 0.5 ? true : false;
			final boolean negativeKm = Math.random() > 0.6 ? true : false;
			final StringBuilder km = new StringBuilder();
			if (negativeKm) {
				km.append("-");
			}
			// Before comma number
			km.append(random.nextInt(1000));
			if (withoutExtraKm) {
				final int afterCommaNumber = random.nextInt(1000);
				km.append(String.format(",%03d",
						Integer.valueOf(afterCommaNumber)));
			} else {
				final int afterCommaNumber = random.nextInt(10);
				final String prefix = Math.random() > 0.5 ? "+" : "-";
				final int extraKm = 1 + random.nextInt(100000);
				km.append(",")
						.append(afterCommaNumber)
						.append(prefix)
						.append(extraKm);
			}
			unsortKmList.add(km.toString());
		}
	}

	private void thenExpectEqualsSortedList() {
		final List<String> clone = new LinkedList<>(unsortKmList);
		clone.sort((first, second) -> {
			final Pair<Double, Double> firstKm = analyseKmValue(first);
			final Pair<Double, Double> secondKm = analyseKmValue(second);
			return compareKm(firstKm, secondKm);
		});
		for (int i = 0; i < clone.size(); i++) {
			assertEquals(clone.get(i), sortedKmList.get(i), () -> {
				return "TEST";
			});
		}
	}

	@SuppressWarnings("boxing")
	private void thenThePatternMustMatch() {
		assertEquals(1, kilometrierungPatternInSchema.size());
		final Pattern kilometrierungPattern = CompareRouteAndKmCriterion
				.getKilometrierungPattern();
		final Pattern schemaPattern = Pattern
				.compile(kilometrierungPatternInSchema.getFirst());

		unsortKmList.forEach(km -> {
			assertEquals(schemaPattern.matcher(km).matches(),
					kilometrierungPattern.matcher(km).matches(),
					String.format("Km Value: %s", km));
		});
	}

	private void whenSortKilometrierung() {
		sortedKmList = new LinkedList<>(unsortKmList);
		sortedKmList.sort((first, second) -> testee.compareKm(Set.of(first),
				Set.of(second)));
	}

	void givenCompareRouteAndKmCriterion() {
		testee = new CompareRouteAndKmCriterion(obj -> {
			if (obj instanceof final Punkt_Objekt po) {
				return po;
			}
			return null;
		});
	}

	@Test
	void testCompareKm() {
		givenCompareRouteAndKmCriterion();
		assertEquals(-1,
				testee.compareKm(Set.of("123,100"), Set.of("123,1+100")));
		assertEquals(1,
				testee.compareKm(Set.of("123,100"), Set.of("123,1-100")));
		assertEquals(1,
				testee.compareKm(Set.of("123,200"), Set.of("123,1+100")));
	}

	/**
	 * Compare the pattern
	 * {@link CompareRouteAndKmCriterion#KILOMETRIERUNG_PATTERN} and the pattern
	 * from schema
	 * {@link BasisTypenValidator#KILOMETRIERUNG_TYPE__PATTERN__VALUES}
	 */
	@Test
	void testKilometrierungPattern() {
		givenCompareRouteAndKmCriterion();
		givenSchemaPattern();
		givenUnsortKilometrierung();
		thenThePatternMustMatch();
	}

	/**
	 * Test the comparator of Kilometer through pattern
	 */
	@Test
	void testSortierungKm() {
		givenCompareRouteAndKmCriterion();
		givenUnsortKilometrierung();
		whenSortKilometrierung();
		thenExpectEqualsSortedList();
	}
}
