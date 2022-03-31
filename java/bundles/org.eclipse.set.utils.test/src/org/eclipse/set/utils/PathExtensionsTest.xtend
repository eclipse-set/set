/**
 * Copyright (c) 2018 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.utils

import java.nio.file.Paths
import org.eclipse.set.basis.extensions.PathExtensions
import org.junit.jupiter.api.Test

import static org.hamcrest.MatcherAssert.assertThat
import static org.hamcrest.core.Is.*

import static extension org.eclipse.set.basis.extensions.PathExtensions.*

/**
 * Tests for {@link PathExtensions}.
 * 
 * @author Schaefer 
 */
class PathExtensionsTest {

	static final String[] TEST_SEGMENTS = #[
		"jenkins-slave",
		"workspace",
		"Toolbox_Ranorex_Autotest",
		"testdata",
		"attachment",
		"Beispielanhang.pdf"
	]

	@Test
	def void testToStringSeparator1() {
		assertThat(
			Paths.get("c:", TEST_SEGMENTS).toString("/"),
			is(String.join("/", #["c:"] + TEST_SEGMENTS))
		)
	}

	@Test
	def void testToStringSeparator2() {
		assertThat(
			Paths.get("c:", TEST_SEGMENTS).toString("\\"),
			is(String.join("\\", #["c:"] + TEST_SEGMENTS))
		)
	}

	@Test
	def void testToStringSeparator3() {
		assertThat(Paths.get("/a/b/c").toString("."), is(".a.b.c"))
		assertThat(Paths.get("a/b/c").toString("."), is("a.b.c"))
	}

	@Test
	def void testGetBaseFileName() {
		assertThat(Paths.get("/a/b/c").baseFileName, is("c"))
		assertThat(Paths.get("/a/b/name.ext").baseFileName, is("name"))
		assertThat(Paths.get("/a/b/name.subname.ext").baseFileName,
			is("name.subname"))
	}

	@Test
	def void testGetExtension() {
		assertThat(Paths.get("/a/b/c").extension, is(""))
		assertThat(Paths.get("/a/b/name.ext").extension, is("ext"))
		assertThat(Paths.get("/a/b/name.subname.ext").extension, is("ext"))
	}

	@Test
	def void testReplaceExtension() {
		val path = Paths.get("/a/b/name.ext")
		assertThat(path.extension, is("ext"))
		val replace = path.replaceExtension("xxx")
		assertThat(replace.extension, is("xxx"))

		// test string representation
		val pathString = path.toString
		val replaceString = replace.toString
		assertThat(replaceString, is(pathString.replace(".ext", ".xxx")))
	}
}
