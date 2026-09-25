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

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collections;
import java.util.List;

import org.eclipse.e4.core.contexts.IContextFunction;
import org.eclipse.set.basis.constants.ContainerType;
import org.eclipse.set.basis.constants.TableType;
import org.eclipse.set.core.services.cache.CacheService;
import org.eclipse.set.core.services.enumtranslation.EnumTranslationService;
import org.eclipse.set.core.services.geometry.GeoKanteGeometryService;
import org.eclipse.set.core.services.graph.TopologicalGraphService;
import org.eclipse.set.core.services.session.SessionService;
import org.eclipse.set.feature.table.pt1.test.utils.PtTable;
import org.eclipse.set.feature.table.pt1.test.utils.TestFailHandle;
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
import org.osgi.service.event.EventAdmin;
import org.osgi.test.common.annotation.InjectService;
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

	@InjectService
	CacheService cacheService;

	@InjectService
	List<IContextFunction> contextFunctions;

	@InjectService
	EnumTranslationService enumTranslationService;

	@InjectService
	EventAdmin eventAdmin;

	@InjectService
	GeoKanteGeometryService geometryService;

	@InjectService
	SessionService sessionService;

	@InjectService
	TopologicalGraphService topologicalService;

	@Override
	public String getReferenceDir() {
		final String tableStateDir = switch (tableState) {
			case FINAL -> "finalState";
			case INITIAL -> "initialState";
			default -> "diffState";
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
	protected Object getCacheService() {
		return cacheService;
	}

	@Override
	protected List<IContextFunction> getContextFunctions() {
		return contextFunctions;
	}

	@Override
	protected Object getEnumTranslationService() {
		return enumTranslationService;
	}

	@Override
	protected EventAdmin getEventAdmin() {
		return eventAdmin;
	}

	@Override
	protected GeoKanteGeometryService getGeometryService() {
		return geometryService;
	}

	@Override
	protected SessionService getSessionService() {
		return sessionService;
	}

	@Override
	protected PtTable getTableToTest() {
		return tableToTest;
	}

	@Override
	protected TopologicalGraphService getTopologicalGraphService() {
		return topologicalService;
	}

	@SuppressWarnings("boxing")
	@Override
	protected void setupModelSession(final EventAdmin event) {
		super.setupModelSession(event);
		Mockito.when(modelSession.isPlanningAreaIgnored())
				.thenReturn(Boolean.TRUE);
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
		assertTrue(assertInjectedAllTransformationService(
				modelServiceMap.values().stream().toList()));
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
