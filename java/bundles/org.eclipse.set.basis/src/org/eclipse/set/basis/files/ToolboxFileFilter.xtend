/**
 * Copyright (c) 2017 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.basis.files

import com.google.common.collect.Lists
import java.io.File
import java.io.FileFilter
import java.nio.file.Path
import java.util.List
import java.util.regex.Pattern
import org.eclipse.swt.widgets.FileDialog
import org.eclipse.xtend.lib.annotations.Accessors

/**
 * Describes a filter including the name and extensions.
 * 
 * @author Schaefer
 */
class ToolboxFileFilter implements FileFilter {

	/**
	 * Indicates that the given filename does not match any of the given extensions.
	 */
	static class InvalidFilterFilename extends Exception {
		@Accessors
		val List<ToolboxFileFilter> extensions

		@Accessors
		val Path path

		new(List<ToolboxFileFilter> extensions, Path path) {
			super(
				'''«path» does not match any of the filters «FOR e : extensions SEPARATOR ", "»«e.filterName»«ENDFOR»'''
			)
			this.extensions = extensions
			this.path = path
		}
	}

	val String filterName
	var List<String> extensions = Lists.newLinkedList
	var filterNameWithFilterList = false

	/**
	 * Test if the given filename is valid for the given extensions.
	 * 
	 * @param extensions the extensions
	 * @param filename the filename
	 * 
	 * @throws InvalidFilterFilename if the filename is not valid
	 */
	static def void check(List<ToolboxFileFilter> extensions,
		Path path) throws InvalidFilterFilename {
		val boolean valid = extensions.fold(false, [r, e|r || e.accept(path)])
		if (!valid) {
			throw new InvalidFilterFilename(extensions, path)
		}
	}

	/**
	 * @param extensions a list of named filter
	 */
	static def String getFilterExtensions(List<ToolboxFileFilter> extensions) {
		return '''«FOR e : extensions SEPARATOR ", "»«e.filterName»: «e.filterExtensions»«ENDFOR»'''
	}

	/**
	 * @param filterName the name of the filter
	 */
	package new(String filterName) {
		this.filterName = filterName
	}

	/**
	 * @param filterName the name of the filter
	 * @param extensions list of valid extensions (without placeholder)
	 */
	package new(String filterName, List<String> extensions) {
		this.filterName = filterName
		add(extensions)
	}

	/**
	 * Returns the name of the filter. If applicable, a list of filters will be appended.
	 * 
	 * @return the name of the filter 
	 * 
	 * @see #setFilterNameWithFilterList(boolean)
	 */
	def String getFilterName() {
		return '''«filterName»«IF filterNameWithFilterList» («filterExtensions»)«ENDIF»'''
	}

	/**
	 * Add all given extensions to the extensions of this named filter.
	 * 
	 * @param extensions the extensions to add (without placeholder)
	 */
	def void add(Iterable<String> extensions) {
		this.extensions.addAll(extensions)
	}

	/**
	 * Add the given extension to the extensions of this named filter.
	 * 
	 * @param newExtension the extension to add (without placeholder)
	 */
	def void add(String newExtension) {
		this.extensions.add(newExtension)
	}

	/**
	 * Set, whether a list of filters will be appended to the returned filter name.
	 * 
	 * @see #getFilterName
	 */
	def void setFilterNameWithFilterList(boolean value) {
		filterNameWithFilterList = value
	}

	/**
	 * @return the the valid extensions, formatted platform specific for the {@link FileDialog} widget.
	 */
	def String getFilterExtensions() {
		return '''«FOR ext : extensions SEPARATOR ";"»*.«ext»«ENDFOR»'''
	}

	override accept(File pathname) {
		return extensions.fold(
			false, [r, ext|r || pathname.toString.match(ext)])
	}

	/**
	 * @param path the path
	 */
	def boolean accept(Path path) {
		return extensions.fold(false, [r, ext|r || path.toString.match(ext)])
	}

	private static def boolean match(String filename, String ext) {
		return Pattern.matches('''(?i).*\.«ext»''', filename)
	}
}
