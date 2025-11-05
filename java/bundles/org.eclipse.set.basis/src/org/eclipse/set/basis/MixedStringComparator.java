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
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

	private static final int NAME_GROUP = 1;

	private static final Pattern NAME_PATTERN = Pattern
			.compile("\\??\\(\\?\\<([^>]+)\\>[^)]*\\)(.*)");

	private static final String NUMBER = "number";

	private static final int REST_GROUP = 2;

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
		groups = findGroupNames(signature);
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

		if (!matcher1.matches()) {
			throw new IllegalArgumentException(
					"pattern=" + pattern.toString() + " input=" + o1);
		}
		if (!matcher2.matches()) {
			throw new IllegalArgumentException(
					"pattern=" + pattern.toString() + " input=" + o2);
		}
		for (final String groupName : groups) {
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
		if (groupName.endsWith("Prefix")) {
			final String value1 = groupO1 == null ? "" : groupO1;
			final String value2 = groupO2 == null ? "" : groupO2;
			if (value1.equals("-") && !value2.equals("-")) {
				return -1;
			}

			if (!value1.equals("-") && value2.equals("-")) {
				return 1;
			}
			return 0;
		}
		final int value1 = Strings.isNullOrEmpty(groupO1) ? 0
				: Integer.parseInt(groupO1);
		final int value2 = Strings.isNullOrEmpty(groupO2) ? 0
				: Integer.parseInt(groupO2);

		return Integer.compare(value1, value2);
	}

	private List<String> findGroupNames(final String signature) {
		final LinkedList<String> result = new LinkedList<>();

		final Matcher matcher = NAME_PATTERN.matcher(signature);
		if (matcher.matches()) {
			final String name = matcher.group(NAME_GROUP);
			final String rest = matcher.group(REST_GROUP);
			result.add(name);
			result.addAll(findGroupNames(rest));
		}

		return result;
	}
}
