/**
 * Copyright (c) 2020 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.core.fileservice

import java.io.IOException
import java.nio.file.Paths
import org.eclipse.set.basis.files.ToolboxFile
import org.eclipse.set.basis.files.ToolboxFileRole
import org.eclipse.set.core.services.files.ToolboxFileFormatService
import org.eclipse.set.sessionservice.SetSessionService
import org.eclipse.set.unittest.utils.toolboxfile.AbstractToolboxFileTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Disabled

/**
 * Test for {@link PlainToolboxFile}
 * 
 * TODO(1.10.0.1) Disabled as no test data is available
 */
@Disabled
class PlainToolboxFileTest extends AbstractToolboxFileTest {
	static val TESTPATH = PHausen_ABC_01_01_PPXML

	/**
	 * Test method for {@link PlainToolboxFile#open()}
	 */
	@Test
	def void testOpen() throws IOException{
		whenOpen
		thenExpectContentsExists(true)
	}

	/**
	 * Test method for {@link PlainToolboxFile#close()}
	 */
	@Test
	def void testClose() throws IOException{
		whenOpen
		whenClose
		thenExpectContentsExists(false)
	}

	/**
	 * Test method for multiple {@link PlainToolboxFile#close()}
	 */
	@Test
	def void testMultipleClose() throws IOException{
		whenOpen
		whenClose
		whenClose
		thenExpectContentsExists(false)
	}

	/**
	 * Test method for {@link PlainToolboxFile#close()#open()}, when Close File
	 * and Open again
	 * 
	 */
	@Test
	def void testCloseThenOpen() throws IOException{
		whenOpen
		thenExpectContentsExists(true)
		whenClose
		thenExpectContentsExists(false)
		whenOpen
		thenExpectContentsExists(true)
	}

	def ToolboxFileFormatService setUpFormatService() {
		val formatService = new PlainFileFormatService
		formatService.sessionService = new SetSessionService
		return formatService
	}

	override protected ToolboxFile createToolboxFile(ToolboxFileRole role) {
		val fileService = new ToolboxFileServiceImpl
		fileService.addFormat(createFormatService)
		return fileService.load(Paths.get(TESTPATH), role)

	}

	override protected createFormatService() {
		val formatService = new PlainFileFormatService
		formatService.sessionService = new SetSessionService
		return formatService
	}

}
