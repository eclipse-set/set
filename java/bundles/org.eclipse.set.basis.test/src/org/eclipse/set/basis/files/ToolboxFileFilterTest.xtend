/**
 * Copyright (c) 2019 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.basis.files

import java.nio.file.Paths
import java.util.List
import org.eclipse.set.basis.files.ToolboxFileFilter.InvalidFilterFilename
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import static org.hamcrest.MatcherAssert.assertThat
import static org.hamcrest.core.Is.*
import static org.junit.jupiter.api.Assertions.*

import static extension org.eclipse.set.basis.files.ToolboxFileFilter.*

/**
 * Tests for {@link ToolboxFileFilter}.
 * 
 * @author Schaefer
 */
class ToolboxFileFilterTest {

	var ToolboxFileFilter txtFilter
	var ToolboxFileFilter exeFilter
	var List<ToolboxFileFilter> filters

	/** 
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	def void setUp() throws Exception {
		val txtBuilder = ToolboxFileFilterBuilder.forName("Textdateien")
		txtBuilder.add("txt")
		txtFilter = txtBuilder.create

		val exeBuilder = ToolboxFileFilterBuilder.forName("Ausführbare Dateien")
		exeBuilder.add("exe", "com")
		exeFilter = exeBuilder.create

		filters = #[txtFilter, exeFilter]
	}

	/** 
	 * Test method for {@link ToolboxFileFilter#check(java.util.List, java.nio.file.Path)}.
	 * 
	 * Pass valid path.
	 */
	@Test
	def void testCheckValid() throws InvalidFilterFilename {
		filters.check(Paths.get("a/b/c/test.com"))
		filters.check(Paths.get("a/b/c/test.txt"))
		filters.check(Paths.get("test.txt"))
		filters.check(Paths.get("/test.exe"))
	}

	/** 
	 * Test method for {@link ToolboxFileFilter#check(java.util.List, java.nio.file.Path)}.
	 * 
	 * Pass invalid path.
	 */
	@Test
	def void testCheckInvalid() throws InvalidFilterFilename {
		assertThrows(InvalidFilterFilename, [
			filters.check(Paths.get("a/b/c/test.bat"))
		])
	}

	/** 
	 * Test method for {@link ToolboxFileFilter#getFilterExtensions(java.util.List)}.
	 */
	@Test
	def void testGetFilterExtensionsListOfToolboxFileFilter() {
		assertThat(txtFilter.filterExtensions, is("*.txt"))
		assertThat(exeFilter.filterExtensions, is("*.exe;*.com"))
	}

	/** 
	 * Test method for {@link ToolboxFileFilter#getFilterName()}.
	 */
	@Test
	def void testGetFilterName() {
		assertThat(txtFilter.filterName, is("Textdateien"))
		assertThat(exeFilter.filterName, is("Ausführbare Dateien"))
	}

	/** 
	 * Test method for {@link ToolboxFileFilter#add(java.util.List)}.
	 */
	@Test
	def void testAdd() {
		exeFilter.add(#["bat", "sh"])
		assertThat(exeFilter.filterExtensions, is("*.exe;*.com;*.bat;*.sh"))
	}

	/** 
	 * Test method for {@link ToolboxFileFilter#setFilterNameWithFilterList(boolean)}.
	 */
	@Test
	def void testSetFilterNameWithFilterList() {
		assertThat(exeFilter.filterName, is("Ausführbare Dateien"))
		exeFilter.filterNameWithFilterList = true
		assertThat(exeFilter.filterName,
			is("Ausführbare Dateien (*.exe;*.com)"))
	}

	/** 
	 * Test method for {@link ToolboxFileFilter#getFilterExtensions()}.
	 */
	@Test
	def void testGetFilterExtensions() {
		assertThat(txtFilter.filterExtensions, is("*.txt"))
		assertThat(exeFilter.filterExtensions, is("*.exe;*.com"))
	}

	/** 
	 * Test method for {@link ToolboxFileFilter#accept(java.io.File)}.
	 */
	@Test
	def void testAcceptFile() {
		assertTrue(exeFilter.accept(Paths.get("a/b/c/test.com").toFile))
		assertFalse(txtFilter.accept(Paths.get("a/b/c/test.com").toFile))
		assertFalse(exeFilter.accept(Paths.get("a/b/c/test.txt").toFile))
		assertTrue(txtFilter.accept(Paths.get("a/b/c/test.txt").toFile))
		assertFalse(exeFilter.accept(Paths.get("test.txt").toFile))
		assertTrue(txtFilter.accept(Paths.get("test.txt").toFile))
		assertTrue(exeFilter.accept(Paths.get("/test.exe").toFile))
		assertFalse(txtFilter.accept(Paths.get("/test.exe").toFile))
	}

	/** 
	 * Test method for {@link ToolboxFileFilter#accept(java.io.File)}.
	 */
	@Test
	def void testAcceptPath() {
		assertTrue(exeFilter.accept(Paths.get("a/b/c/test.com")))
		assertFalse(txtFilter.accept(Paths.get("a/b/c/test.com")))
		assertFalse(exeFilter.accept(Paths.get("a/b/c/test.txt")))
		assertTrue(txtFilter.accept(Paths.get("a/b/c/test.txt")))
		assertFalse(exeFilter.accept(Paths.get("test.txt")))
		assertTrue(txtFilter.accept(Paths.get("test.txt")))
		assertTrue(exeFilter.accept(Paths.get("/test.exe")))
		assertFalse(txtFilter.accept(Paths.get("/test.exe")))
	}
}
