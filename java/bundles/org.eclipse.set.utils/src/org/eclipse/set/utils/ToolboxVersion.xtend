/**
 * Copyright (c) 2017 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.utils

import com.google.common.base.Splitter
import java.util.regex.Pattern

/**
 * Provides different representations of the toolbox version.
 * 
 * @author Schaefer
 */
class ToolboxVersion {

	static val String MISSING_VERSION = "-"

	val boolean isAvailable
	val String longVersion
	val String shortVersion
	val String extraShortVersion

	private static def String toShort(String versionString, int size) {
		val versionTags = Splitter.on(Pattern.compile("\\.|R")).
			splitToList(
				versionString
			)
		return '''«FOR tag : versionTags.subList(0, Math.min(versionTags.size, size)) SEPARATOR "."»«tag»«ENDFOR»'''
	}

	/**
	 * @param versionString the version string
	 */
	new(String versionString) {
		isAvailable = versionString !== null
		longVersion = versionString ?: MISSING_VERSION
		shortVersion = versionString?.toShort(3) ?: MISSING_VERSION
		extraShortVersion = versionString?.toShort(2) ?: MISSING_VERSION
	}

	/**
	 * @return the long version 
	 */
	def String getLongVersion() {
		return longVersion
	}

	/**
	 * @return the short version <main>.<beside>.<rc>
	 */
	def String getShortVersion() {
		return shortVersion
	}
	
	/**
	 * @return the extra short version <main>.<beside>
	 */
	def String getExtraShortVersion() {
		return extraShortVersion
	}

	def boolean isAvailable() {
		return isAvailable
	}
	
	def boolean isDevelopmentVersion() {
		val versionTags = Splitter.on(Pattern.compile("\\.")).splitToList(longVersion)
		return !versionTags.last.matches("R.*") 
	}
}
