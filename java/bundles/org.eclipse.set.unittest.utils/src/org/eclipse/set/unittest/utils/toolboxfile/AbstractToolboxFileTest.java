/**
 * Copyright (c) 2020 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */

package org.eclipse.set.unittest.utils.toolboxfile;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.set.basis.files.ToolboxFile;
import org.eclipse.set.basis.files.ToolboxFileAC;
import org.eclipse.set.basis.files.ToolboxFileRole;
import org.eclipse.set.core.services.files.ToolboxFileFormatService;
import org.eclipse.set.toolboxmodel.Layoutinformationen.LayoutinformationenPackage;
import org.eclipse.set.toolboxmodel.PlanPro.PlanProPackage;
import org.eclipse.set.toolboxmodel.PlanPro.util.PlanProResourceFactoryImpl;
import org.eclipse.set.toolboxmodel.Signalbegriffe_Ril_301.Signalbegriffe_Ril_301Package;
import org.eclipse.set.unittest.utils.AbstractToolboxTest;
import org.junit.jupiter.api.BeforeEach;

/**
 * Common toolboxfile test
 * 
 * @author Truong
 *
 */
@SuppressWarnings("nls")
public abstract class AbstractToolboxFileTest extends AbstractToolboxTest {

	protected static final String TMP_PATH = "testToolboxFile";

	protected static void thenExpectZippedDirectoryNotExist()
			throws IOException {
		try (final Stream<Path> listFiles = Files.list(Paths.get(TMP_PATH))) {
			assertFalse(listFiles.anyMatch(ele -> {
				return ele.getFileName().toString()
						.equals(ToolboxFileRole.SESSION.toDirectoryName());
			}));
		}
	}

	protected ToolboxFile testee;
	protected ToolboxFile toolboxFilebeforClose;

	/**
	 * Setup Test
	 */
	@BeforeEach
	public void setUp() {
		testee = createToolboxFile(ToolboxFileRole.SESSION);
		resourcePackageRegistry();
	}

	protected abstract ToolboxFileFormatService createFormatService();

	protected abstract ToolboxFile createToolboxFile(ToolboxFileRole role);

	protected void resourcePackageRegistry() {
		if (testee == null) {
			return;
		}
		Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("xml",
				new PlanProResourceFactoryImpl());
		testee.getEditingDomain().getResourceSet().getPackageRegistry()
				.put(PlanProPackage.eNS_URI, PlanProPackage.eINSTANCE);
		testee.getEditingDomain().getResourceSet().getPackageRegistry().put(
				Signalbegriffe_Ril_301Package.eNS_URI,
				Signalbegriffe_Ril_301Package.eINSTANCE);
		testee.getEditingDomain().getResourceSet().getPackageRegistry().put(
				LayoutinformationenPackage.eNS_URI,
				LayoutinformationenPackage.eINSTANCE);
	}

	protected void thenExpectContentsExists(final boolean exists) {
		assertNotNull(testee.getPlanProResource());
		assertTrue(
				testee.getPlanProResource().getContents().isEmpty() != exists);
		assertTrue(testee.getEditingDomain().getResourceSet().getResources()
				.isEmpty() != exists);
	}

	protected void thenExpectResourceCallsWithinZipDirectory() {
		assertTrue(Files.exists(testee.getModelPath().getParent()));
		assertTrue(Files.exists(testee.getModelPath()));
	}

	protected void whenClose() throws IOException {
		testee.close();
	}

	protected void whenOpen() throws IOException {
		assertNotNull(testee);
		testee.open();
	}

	protected void whenOpenAndAutoclose(final ToolboxFileRole role)
			throws IOException {
		try (ToolboxFileAC file = new ToolboxFileAC(createToolboxFile(role))) {
			file.get().open();
		}
	}
}
