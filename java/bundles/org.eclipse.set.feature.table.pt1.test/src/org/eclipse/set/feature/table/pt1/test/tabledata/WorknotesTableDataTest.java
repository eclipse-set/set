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
import org.eclipse.set.ppmodel.extensions.PlanProSchnittstelleExtensions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
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
public class WorknotesTableDataTest extends AbstractPt1TableDataTest {

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

	String tableRefName;
	@InjectService
	TopologicalGraphService topologicalService;

	@Override
	public String getTestTableReferenceName() {
		return tableRefName;
	}

	@Override
	protected CacheService getCacheService() {
		return cacheService;
	}

	@Override
	protected List<IContextFunction> getContextFunctions() {
		return contextFunctions;
	}

	@Override
	protected EnumTranslationService getEnumTranslationService() {
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
	protected void testWorknotesTableOpenAfterAnotherTable() throws Throwable {
		givenWorknotesTable();
		// Open Worknotes table after another tables
		doTestInMockEnvironment(() -> {
			givenTableService();
			assertNotNull(tableService);

			whenTransformAnotherTable();
			givenTestTable(tableToTest, TableType.DIFF, Collections.emptySet());
			whenTableTransformComplete();
			thenExpectTableStatusRelevant();
			givenWorknotesTableReferenceOpenAfter();
			if (isTableEmpty) {
				thenExpectNotExistReference();
			}

			whenExistReferenceCSV();
			thenExpectTableDataEqualReferenceCSV();
		});
	}

	@Test
	protected void testWorknotesTableOpenBeforeAnotherTable() throws Throwable {
		givenWorknotesTable();

		// Open Worknotes table before another tables
		doTestInMockEnvironment(() -> {
			givenTableService();
			givenTestTable(tableToTest, TableType.DIFF, Collections.emptySet());
			whenTableTransformComplete();
			thenExpectTableStatusRelevant();
			givenWorknotesTableReferenceOpenBefore();
			if (isTableEmpty) {
				thenExpectNotExistReference();
			}

			whenExistReferenceCSV();
			thenExpectTableDataEqualReferenceCSV();
		});
	}

	@SuppressWarnings("boxing")
	void givenWorknotesTable() {
		tableToTest = new PtTable("Sxxx",
				"Sxxx – Tabelle der Bearbeitungsvermerke", "supplement",
				List.of(0));
	}

	void givenWorknotesTableReferenceOpenAfter() throws Exception {
		tableRefName = tableToTest.shortcut().toLowerCase() + "_after";
		referenceData = loadReferenceFile(tableRefName);
	}

	void givenWorknotesTableReferenceOpenBefore() throws Exception {
		tableRefName = tableToTest.shortcut().toLowerCase() + "_before";
		referenceData = loadReferenceFile(tableRefName);
	}

	void whenTransformAnotherTable() {
		for (final PtTable table : PtTable.tablesToTest) {
			givenTestTable(table, TableType.DIFF, Collections.emptySet());
		}
	}

}
