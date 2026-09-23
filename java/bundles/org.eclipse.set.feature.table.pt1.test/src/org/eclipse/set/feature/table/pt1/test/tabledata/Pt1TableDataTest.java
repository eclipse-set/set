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
import org.eclipse.set.basis.constants.TableType;
import org.eclipse.set.core.services.cache.CacheService;
import org.eclipse.set.core.services.enumtranslation.EnumTranslationService;
import org.eclipse.set.core.services.geometry.GeoKanteGeometryService;
import org.eclipse.set.core.services.graph.TopologicalGraphService;
import org.eclipse.set.core.services.session.SessionService;
import org.eclipse.set.feature.table.pt1.test.utils.PtTable;
import org.eclipse.set.feature.table.pt1.test.utils.TestFailHandle;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import org.osgi.service.event.EventAdmin;
import org.osgi.test.common.annotation.InjectService;
import org.osgi.test.junit5.service.ServiceExtension;

/**
 * 
 */
@TestInstance(Lifecycle.PER_CLASS)
@ExtendWith(ServiceExtension.class)
public class Pt1TableDataTest extends AbstractPt1TableDataTest {
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
		return super.getReferenceDir() + "diffState/";
	}

	@Override
	public String getTestTableReferenceName() {
		return tableToTest.shortcut();
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
	protected void setupModelSession(final EventAdmin eventAdmin) {
		super.setupModelSession(eventAdmin);
		Mockito.when(modelSession.isPlanningAreaIgnored())
				.thenReturn(Boolean.TRUE);

	}

	@Test
	void testExistTableTransformService() {
		assertTrue(assertInjectedAllTransformationService(
				modelServiceMap.values().stream().toList()));
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
			thenRowAndColumnCountEqualReferenceCSV();
			thenExpectTableDataEqualReferenceCSV();
		};

		doTestInMockEnvironment(testCases);
	}
}
