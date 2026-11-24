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

import org.eclipse.set.basis.constants.ContainerType;
import org.eclipse.set.basis.constants.TableType;
import org.eclipse.set.feature.table.pt1.test.Activator;
import org.eclipse.set.feature.table.pt1.test.utils.PtTable;
import org.eclipse.set.osgi.test.utils.TestFailHandle;
import org.eclipse.set.ppmodel.extensions.PlanProSchnittstelleExtensions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.osgi.test.junit5.service.ServiceExtension;

/**
 * 
 */
@TestInstance(Lifecycle.PER_CLASS)
@ExtendWith(ServiceExtension.class)
@ExtendWith(TestFailHandle.class)
public class Pt1TableStateDataTest extends AbstractPt1TableDataTest {
	private TableType tableState;

	private PtTable tableToTest;

	@Override
	public String getReferenceDir() {
		final String tableStateDir = switch (tableState) {
			case FINAL -> "finalState";
			case INITIAL -> "initialState";
			default -> throw new IllegalStateException(
					"Not support the Tablestate in this test case: "
							+ tableState);
		};
		return super.getReferenceDir() + tableStateDir + "/";
	}

	@Override
	public String getTestTableReferenceName() {
		if (tableToTest != null) {
			return tableToTest.shortcut();
		}
		return null;
	}

	@BeforeAll
	@Override
	protected void beforeAll() throws Exception {
		super.beforeAll();
	}

	@Override
	protected PtTable getTableToTest() {
		return tableToTest;
	}

	@SuppressWarnings("boxing")
	@Override
	protected void setupModelSession() {
		super.setupModelSession();
		Mockito.when(modelSession.getContainer(ArgumentMatchers.any()))
				.thenAnswer(invocation -> {
					if (invocation.getArgument(
							0) instanceof final ContainerType containerType) {
						return PlanProSchnittstelleExtensions.getContainer(
								planProSchnittstelle, containerType);
					}
					throw new IllegalAccessException();
				});
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
	void testTableFinalData(final PtTable table) throws Throwable {
		tableToTest = table;
		tableState = TableType.FINAL;
		final Executable testCases = () -> {
			givenTableService();
			assertNotNull(tableService);

			givenTestTable(table, tableState, Collections.emptySet());
			whenTableTransformComplete();
			thenExpectTableStatusRelevant();
			givenReferenceCSV(table);
			if (isTableEmpty) {
				thenExpectNotExistReference();
				return;
			}

			whenExistReferenceCSV();
			thenRowAndColumnCountEqualReferenceCSV();
			thenExpectTableDataEqualReferenceCSV();
		};

		doTestInMockEnvironment(testCases);
	}

	@ParameterizedTest
	@MethodSource("providesPtTable")
	@ExtendWith(TestFailHandle.class)
	void testTableInitalData(final PtTable table) throws Throwable {
		tableToTest = table;
		tableState = TableType.INITIAL;
		final Executable testCases = () -> {
			givenTableService();
			assertNotNull(tableService);

			givenTestTable(table, tableState, Collections.emptySet());
			whenTableTransformComplete();
			thenExpectTableStatusRelevant();
			givenReferenceCSV(table);
			if (isTableEmpty) {
				thenExpectNotExistReference();
				return;
			}

			whenExistReferenceCSV();
			thenRowAndColumnCountEqualReferenceCSV();
			thenExpectTableDataEqualReferenceCSV();
		};

		doTestInMockEnvironment(testCases);
	}
}
