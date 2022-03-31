/**
 * Copyright (c) 2020 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.core.fileservice

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test

/** 
 * Tests for {@link PlainFileFormatService}.
 * 
 * @author Schaefer
 */
class PlainFileFormatServiceTest {

	var PlainFileFormatService service

	@BeforeEach
	def void setUp() {
		service = new PlainFileFormatService
		service.registerResourceFactories
	}

	/** 
	 * Test method for {@link PlainFileFormatService#create(Format)}.
	 */
	@Test
	@Disabled
	def void testCreateFormat() {
		// IMPROVE Mock the Format
		// var toolboxfile = service.create(Format.PLAIN_PPFILE)
		// assertThat(toolboxfile.resource.encoding,
		// is(StandardCharsets.UTF_8.name()))
		//
		// toolboxfile.path = Paths.get("test.ppxml")
		// assertThat(toolboxfile.resource.encoding,
		// is(StandardCharsets.UTF_8.name()))
		//
		// toolboxfile = service.create(Format.MERGE)
		// assertThat(toolboxfile.resource.encoding,
		// is(StandardCharsets.UTF_8.name()))
		//
		// toolboxfile.path = Paths.get("test.ppmxml")
		// assertThat(toolboxfile.resource.encoding,
		// is(StandardCharsets.UTF_8.name()))
	}
}
