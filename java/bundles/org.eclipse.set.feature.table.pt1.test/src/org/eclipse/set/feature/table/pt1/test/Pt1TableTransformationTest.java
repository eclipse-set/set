/**
 * Copyright (c) 2026 DB InfraGO AG and others
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 */
package org.eclipse.set.feature.table.pt1.test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

import org.dom4j.Document;
import org.dom4j.io.SAXReader;
import org.eclipse.emf.common.util.ECollections;
import org.eclipse.set.basis.ToolboxProperties;
import org.eclipse.set.basis.constants.ExportType;
import org.eclipse.set.basis.constants.TableType;
import org.eclipse.set.core.services.enumtranslation.EnumTranslationService;
import org.eclipse.set.feature.table.PlanPro2TableTransformationService;
import org.eclipse.set.feature.table.pt1.test.utils.CustomDOMReader;
import org.eclipse.set.feature.table.pt1.test.utils.CustomNodeComparator;
import org.eclipse.set.model.tablemodel.RowGroup;
import org.eclipse.set.model.tablemodel.Table;
import org.eclipse.set.ppmodel.extensions.MultiContainer_AttributeGroupExtensions;
import org.eclipse.set.ppmodel.extensions.container.MultiContainer_AttributeGroup;
import org.eclipse.set.utils.export.xsl.TransformTable;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.osgi.service.event.EventAdmin;
import org.osgi.test.common.annotation.InjectService;
import org.osgi.test.junit5.service.ServiceExtension;

/**
 * Test Pt1 Table transformation
 * 
 * @author Truong
 */
@TestInstance(Lifecycle.PER_CLASS)
@ExtendWith(ServiceExtension.class)
class Pt1TableTransformationTest extends Pt1TableTest {

	private static String Ssks_Pagebreak_Column_Index = "27";
	private static String XSL_DIR = "res/xsl";

	private static boolean compareXSLDoc(final Document actual,
			final Document expect) {
		final CustomNodeComparator nodeComparator = new CustomNodeComparator();
		return nodeComparator.compare(actual, expect) == 0;
	}

	private static Document loadReferenceXSLDoc(final String shortcut)
			throws Exception {
		final String xslName = shortcut.toLowerCase() + "-fop.xsl";
		final File file = Path.of(XSL_DIR, xslName).toFile();
		if (!file.exists() || !file.isFile()) {
			throw new IllegalArgumentException(
					"File Not found: " + file.toString());
		}

		final SAXReader saxReader = new SAXReader();
		saxReader.setMergeAdjacentText(true);
		return saxReader.read(file);
	}

	/**
	 * @return the reference files
	 */
	protected static Stream<Arguments> getReferenceFiles() {
		return Stream.of(Arguments.of(PPHN_1_10_0_3_20220517_PLANPRO, "pphn"),
				Arguments.of(SINGLE_STATE_PLAN, "zustandpphn"));
	}

	@InjectService
	EventAdmin eventAdmin;

	@InjectService
	List<PlanPro2TableTransformationService> transformationServices;

	@InjectService
	EnumTranslationService translationService;

	@Test
	void testPDFExportStyle() throws Exception {
		givenPlanProFile(PPHN_1_10_0_3_20220517_PLANPRO);
		setupTransformationService(eventAdmin);
		for (final PlanPro2TableTransformationService service : transformationServices) {
			final TransformTable transformTable = new TransformTable(
					ExportType.INVENTORY_RECORDS,
					service.getTableNameInfo().getShortName().toLowerCase(),
					TableType.DIFF, translationService);
			final Document xslDoc = assertDoesNotThrow(() -> {
				final org.w3c.dom.Document doc = service.getTableNameInfo()
						.getShortName()
						.equalsIgnoreCase("ssks")
								? transformTable.transform(
										List.of(Ssks_Pagebreak_Column_Index))
								: transformTable.transform();
				final CustomDOMReader reader = new CustomDOMReader();
				return reader.read(doc);
			}, "Error by create XSL Doc: "
					+ service.getClass().getPackageName());
			assertNotNull(xslDoc, "Error by create XSL Doc: "
					+ service.getClass().getPackageName());
			final Document expect = loadReferenceXSLDoc(
					service.getTableNameInfo().getShortName());
			assertTrue(compareXSLDoc(xslDoc, expect),
					"The XSL isn't equal expected: "
							+ service.getTableNameInfo().getShortName());
		}
	}

	@ParameterizedTest
	@MethodSource("getReferenceFiles")
	void testTransformator(final String file) throws Exception {
		givenPlanProFile(file);
		setupTransformationService(eventAdmin);
		System.setProperty(ToolboxProperties.DEVELOPMENT_MODE,
				Boolean.FALSE.toString());
		for (final PlanPro2TableTransformationService transformationService : transformationServices) {
			for (final MultiContainer_AttributeGroup container : getLSTContainer()) {
				// Test transformation table
				final Table transformedTable = assertDoesNotThrow(
						() -> transformationService.transform(container),
						() -> "Error by transformation Table: "
								+ transformationService.getClass()
										.getPackageName());
				final TableType defaultTableType = MultiContainer_AttributeGroupExtensions
						.getContainerType(container)
						.getDefaultTableType();
				// Test sorting table
				assertDoesNotThrow(() -> {
					final Comparator<RowGroup> comparator = transformationService
							.getRowGroupComparator(defaultTableType);
					ECollections.sort(
							transformedTable.getTablecontent().getRowgroups(),
							comparator);
				}, () -> "Error by sort table: "
						+ transformationService.getClass().getPackageName());
			}
		}
	}
}
