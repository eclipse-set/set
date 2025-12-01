/**
 * Copyright (c) 2016 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.basis;

import java.util.Comparator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Strings;

/**
 * Compares strings based on a signature, defined by a regular expression. The
 * comparison is performed in groups with numerical comparisons for groups
 * starting with "number" and {@link String#compareTo(String)} for all other
 * groups.
 * 
 * @author Schaefer
 */
@SuppressWarnings("nls")
public class MixedStringComparator implements Comparator<String> {
	Logger logger = LoggerFactory.getLogger(MixedStringComparator.class);
	private static final String NUMBER = "number";

	private static String transform(final String text) {
		String replaced = text.replace('ä', 'a');
		replaced = text.replace('Ä', 'A');
		replaced = text.replace('ö', 'o');
		replaced = text.replace('Ö', 'O');
		replaced = text.replace('ü', 'u');
		replaced = text.replace('Ü', 'U');
		replaced = text.replace("ß", "ss");
		return replaced;
	}

	private final List<String> groups;

	private final Pattern pattern;

	/**
	 * The given signature defines the groups, which are used to perform the
	 * comparison. The signature is a regular expression with named capturing
	 * groups.
	 * 
	 * @param signature
	 *            the signature
	 */
	public MixedStringComparator(final String signature) {
		pattern = Pattern.compile(signature);
		final List<Entry<String, Integer>> sortedGroups = pattern.namedGroups()
				.entrySet()
				.stream()
				.sorted((a, b) -> a.getValue().compareTo(b.getValue()))
				.toList();
		groups = sortedGroups.stream().map(Entry::getKey).toList();
	}

	/**
	 * Compares the given values.
	 * 
	 * @param obj1
	 *            the first object to be compared
	 * @param obj2
	 *            the second object to be compared
	 * 
	 * @return a negative integer, zero, or a positive integer as the first
	 *         argument is less than, equal to, or greater than the second
	 * 
	 * @throws IllegalArgumentException
	 *             if one of the values does not match the given signature
	 * 
	 * @see Comparator#compare(Object, Object)
	 */
	@Override
	public int compare(final String obj1, final String obj2) {
		final String o1 = transform(obj1);
		final String o2 = transform(obj2);

		final Matcher matcher1 = pattern.matcher(o1);
		final Matcher matcher2 = pattern.matcher(o2);
		final Optional<Integer> patternCompare = compareNullableValue(matcher1,
				matcher2, m -> {
					if (!m.matches()) {
						logger.error("pattern= {} - input= {}", pattern,
								m == matcher1 ? o1 : o2);
						return true;
					}
					return false;
				});
		if (patternCompare.isPresent()) {
			return patternCompare.get().intValue();
		}

		for (final String groupName : groups) {
			final Optional<Integer> compareExistGroup = compareNullableValue(
					matcher1, matcher2,
					m -> tryCatchFalse(m, o -> o.group(groupName)));
			if (compareExistGroup.isPresent()) {
				return compareExistGroup.get().intValue();
			}
			final String groupO1 = matcher1.group(groupName);
			final String groupO2 = matcher2.group(groupName);
			if (groupName.startsWith(NUMBER)) {
				final int compareNumber = compareNumber(groupName, groupO1,
						groupO2);
				if (compareNumber != 0) {
					return compareNumber;
				}
			} else {
				final String groupO1NullSafe = groupO1 == null ? "" : groupO1; //$NON-NLS-1$
				final int result = groupO1NullSafe.compareTo(groupO2);
				if (result != 0) {
					return result;
				}
			}
		}

		return 0;
	}

	private static int compareNumber(final String groupName,
			final String groupO1, final String groupO2) {
		if (groupName.startsWith("numberD")) {
			final String value1 = groupO1 == null ? "0.0" : "0." + groupO1;
			final String value2 = groupO2 == null ? "0.0" : "0." + groupO2;
			return Double.valueOf(value1).compareTo(Double.valueOf(value2));
		}
		final double value1 = Strings.isNullOrEmpty(groupO1) ? 0
				: Double.parseDouble(groupO1);
		final double value2 = Strings.isNullOrEmpty(groupO2) ? 0
				: Double.parseDouble(groupO2);

		return Double.compare(value1, value2);
	}

	/**
	 * Compare nullable/ not available objects
	 * 
	 * @param <T>
	 *            the compare type
	 * @param first
	 *            the first value
	 * @param second
	 *            the second value
	 * @param checkNullable
	 *            checkNullable predicate
	 * @return the optional empty, when both of the values are available
	 */
	public static <T> Optional<Integer> compareNullableValue(final T first,
			final T second, final Predicate<T> checkNullable) {
		if (checkNullable.test(first) && first == second) {
			return Optional.of(Integer.valueOf(0));
		}

		if (checkNullable.test(first)) {
			return Optional.of(Integer.valueOf(-1));
		}

		if (checkNullable.test(second)) {
			return Optional.of(Integer.valueOf(1));
		}

		return Optional.empty();
	}

	private static <T, U> boolean tryCatchFalse(final T obj,
			final Function<T, U> function) {
		try {
			function.apply(obj);
			return false;
		} catch (final Exception e) {
			return true;
		}
	}
}
