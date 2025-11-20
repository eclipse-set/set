/**
 * Copyright (c) 2020 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */

package org.eclipse.set.core.fileservice

import java.nio.file.Files
import java.nio.file.Paths
import org.eclipse.set.basis.files.ToolboxFile
import org.eclipse.set.basis.files.ToolboxFileRole
import org.eclipse.set.core.services.files.ToolboxFileFormatService
import org.eclipse.set.feature.validation.session.SetSessionService
import org.eclipse.set.model.planpro.PlanPro.PlanProPackage
import org.eclipse.set.unittest.utils.toolboxfile.AbstractToolboxFileTest
import org.junit.jupiter.api.Test

import static org.junit.Assert.assertFalse
import static org.junit.Assert.assertNotNull

/**
 * Test for {@link ZippedPlanProToolboxFile}
 * 
 */
class ZippedToolboxFileTest extends AbstractToolboxFileTest {
	static val TESTPATH = PPHN_1_10_0_3_20220517_PLANPRO

	/**
	 * Test method for {@link ZippedPlanProToolboxFile#open()}
	 */
	@Test
	def void testOpen() throws Throwable{
		MockPlanProVersionService.mockPlanProVersionService([
			whenOpen
			thenExpectContentsExists()
			thenExpectLayoutContentExists()
			thenExpectResourceCallsWithinZipDirectory
		])
	}

	/**
	 * Test method for {@link ZippedPlanProToolboxFile#close()}
	 * 
	 */
	@Test
	def void testClose() throws Throwable {
		MockPlanProVersionService.mockPlanProVersionService([
			whenOpen
			whenClose
			thenExpectZippedDirectoryNotExist
			thenExpectContentsNotExists()
		])
	}

	/**
	 * Test method for multiple {@link ZippedPlanProToolboxFile#close()}
	 */
	@Test
	def void testMultipleClose() throws Throwable {
		MockPlanProVersionService.mockPlanProVersionService([
			whenOpen
			whenClose
			whenClose
			thenExpectZippedDirectoryNotExist
			thenExpectContentsNotExists()
		])
	}

	/**
	 * Test method for multiple {@link ZippedPlanProToolboxFile#close()}
	 */
	@Test
	def void testAutoclose() throws Throwable {
		MockPlanProVersionService.mockPlanProVersionService([
			PlanProPackage.eINSTANCE.eClass();
			PlanProPackage.eINSTANCE.
				eClass();

			ToolboxFileRole.SESSION.whenOpenAndAutoclose
			thenExpectZippedDirectoryNotExist

		])
	}

	/**
	 * Test method for {@link ZippedPlanProToolboxFile#close()#open()}, when Close File
	 * and Open again
	 * 
	 */
	@Test
	def void testCloseThenOpen() throws Throwable{
		MockPlanProVersionService.mockPlanProVersionService([
			whenOpen
			thenExpectContentsExists()
			thenExpectLayoutContentExists()
			whenClose
			thenExpectContentsNotExists()
			whenOpen
			thenExpectContentsExists()
			thenExpectLayoutContentExists()
			thenExpectResourceCallsWithinZipDirectory

		])
	}

	def void thenExpectLayoutContentExists() {
		if (Files.exists(testee.layoutPath)) {
			assertNotNull(testee.layoutResource);
			assertFalse(testee.layoutResource.contents.empty)
		}
	}

	def ToolboxFileFormatService setUpFormatService() {
	}

	override protected ToolboxFile createToolboxFile(ToolboxFileRole role) {
		val fileService = new ToolboxFileServiceImpl
		fileService.addFormat(createFormatService)
		val toolboxFile = fileService.load(Paths.get(TESTPATH), role)
		toolboxFile.temporaryDirectory = Paths.get(TMP_PATH)
		return toolboxFile

	}

	override protected createFormatService() {
		val formatService = new ZippedPlanProFileFormatService
		formatService.sessionService = new SetSessionService
		return formatService
	}

}
