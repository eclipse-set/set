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
package org.eclipse.set.feature.table.pt1.test.tabledata;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Collections;

import org.eclipse.set.basis.constants.TableType;
import org.eclipse.set.feature.table.pt1.test.Activator;
import org.eclipse.set.feature.table.pt1.test.utils.PtTable;
import org.eclipse.set.osgi.test.utils.TestFailHandle;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.osgi.test.junit5.service.ServiceExtension;

/**
 * 
 */
@TestInstance(Lifecycle.PER_CLASS)
@ExtendWith(ServiceExtension.class)
public class Pt1TableDataTest extends AbstractPt1TableDataTest {
	private PtTable tableToTest;

	@Override
	public String getReferenceDir() {
		return super.getReferenceDir() + "diffState/";
	}

	@Override
	public String getTestTableReferenceName() {
		return tableToTest.shortcut();
	}

	@Override
	protected PtTable getTableToTest() {
		return tableToTest;
	}

	@Test
	void testExistTableTransformService() throws Exception {
		givenTableService();
		assertDoesNotThrow(
				() -> Activator.assertInjectedAllTransformationService(
						modelServiceMap.values()));
	}

	@ParameterizedTest
	@MethodSource("providesPtTable")
	@ExtendWith(TestFailHandle.class)
	void testTableData(final PtTable table) throws Throwable {
		tableToTest = table;

		final Executable testCases = () -> {
			givenTableService();
			assertNotNull(tableService);

			givenTestTable(table, TableType.DIFF, Collections.emptySet());
			whenTableTransformComplete();
			thenExpectTableStatusRelevant();
			givenReferenceCSV(table);
			if (isTableEmpty) {
				thenExpectNotExistReference();
				return;
			}

			whenExistReferenceCSV();
			thenExpectRowAndColumnCountEqualReferenceCSV();
			thenExpectTableDataEqualReferenceCSV();
		};

		doTestInMockEnvironment(testCases);
	}
}
