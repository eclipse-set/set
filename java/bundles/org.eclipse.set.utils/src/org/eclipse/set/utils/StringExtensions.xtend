/**
 * Copyright (c) 2017 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.utils

import java.util.regex.Pattern

/**
 * Extensions for {@link String}.
 * 
 * @author Schaefer
 */
class StringExtensions {

	static val ENV_PATTERN = Pattern.compile(
		"(?<start>[^$]*)\\$(?<var>[^$]+)\\$(?<end>.*)")
	static val ENV_VAR = "var"

	/**
	 * This constant determine manual with OpenSans font 
	 */
	static val int CHARACTER_PRO_CM = 8
	static final String ZERO_WIDTH_SPACE = "\u200b"

	/**
	 * @param string this string
	 * 
	 * @return the string interspersed with "zero spaces"
	 */
	static def String intersperseWithZeroSpaces(String string) {
		return string.replaceAll("(.)", "$1" + ZERO_WIDTH_SPACE)
	}

	/**
	 * Intersperse with zero spaces after special characters only.
	 * 
	 * @param string this string
	 * 
	 * @return the string interspersed with "zero spaces"
	 */
	static def String intersperseWithZeroSpacesSC(String string) {
		return string.replaceAll("([ /\\-_)}\\]])", "$1" + ZERO_WIDTH_SPACE)
	}

	static def int maxCharInCell(float cellWidth) {
		return Math.round(cellWidth * CHARACTER_PRO_CM)
	}

	/**
	 * @param string this string
	 * 
	 * @return the padded string
	 */
	static def String toPaddedString(String string, int columnWidth) {
		return String.format('''%«columnWidth»s''', string)
	}

	/**
	 * Shorten a string at the end.
	 * 
	 * @param string this string
	 * @param numberOfCharacters the number of characters
	 * 
	 * @return the shortened string
	 */
	static def String shortenBy(String string, int numberOfCharacters) {
		val length = string.length
		if (length > numberOfCharacters) {
			return string.substring(0, length - numberOfCharacters)
		}
		return ""
	}

	/**
	 * Remove the given suffix from this string.
	 * 
	 * @param string this string
	 * @param suffix the given suffix collection
	 */
	static def String removeSuffix(String string, String... suffix) {
		val sortedSuffix = suffix.sortWith([ a, b |
			Integer.compare(b.length, a.length)
		])

		for (p : sortedSuffix) {
			if (string.endsWith(p)) {
				return string.substring(0, string.length - p.length)
			}
		}

		return string
	}

	/**
	 * Compares the arguments. Any argument may be <code>null</code>.
	 * 
	 * @param string this string
	 * @param other the other string
	 */
	static def boolean nullSafeEquals(String string, String other) {
		if (string === null || other === null) {
			return string === other
		}
		return string.equals(other)
	}

	/**
	 * Expand the given path with environment variables, marked with dollarsigns.
	 * <p>
	 * Example: $WORKSPACE$\testdata
	 * 
	 * @param string this string
	 * 
	 * @return the string with the environment variables expanded
	 */
	static def String expandFromEnvironment(String string) {
		var String result = String.valueOf(string)
		for (var matcher = ENV_PATTERN.matcher(result); matcher.
			matches; matcher = ENV_PATTERN.matcher(result)) {
			val name = matcher.group(ENV_VAR)
			val expanded = System.getenv(name)
			result = string.replace('''$«name»$''', expanded)
		}
		return result
	}
}
