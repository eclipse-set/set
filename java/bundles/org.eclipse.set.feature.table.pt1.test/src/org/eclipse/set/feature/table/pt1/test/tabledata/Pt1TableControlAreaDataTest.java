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

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.e4.core.contexts.IContextFunction;
import org.eclipse.set.basis.Pair;
import org.eclipse.set.basis.constants.ContainerType;
import org.eclipse.set.basis.constants.TableType;
import org.eclipse.set.core.services.cache.CacheService;
import org.eclipse.set.core.services.enumtranslation.EnumTranslationService;
import org.eclipse.set.core.services.geometry.GeoKanteGeometryService;
import org.eclipse.set.core.services.graph.TopologicalGraphService;
import org.eclipse.set.core.services.session.SessionService;
import org.eclipse.set.feature.table.pt1.test.utils.PtTable;
import org.eclipse.set.feature.table.pt1.test.utils.TestFailHandle;
import org.eclipse.set.model.planpro.Ansteuerung_Element.Stell_Bereich;
import org.eclipse.set.ppmodel.extensions.PlanProSchnittstelleExtensions;
import org.eclipse.set.ppmodel.extensions.StellBereichExtensions;
import org.eclipse.set.ppmodel.extensions.container.MultiContainer_AttributeGroup;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.FieldSource;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.osgi.service.event.EventAdmin;
import org.osgi.test.common.annotation.InjectService;
import org.osgi.test.junit5.service.ServiceExtension;

import com.google.common.collect.Streams;

/**
 * 
 */
@TestInstance(Lifecycle.PER_CLASS)
@ExtendWith(ServiceExtension.class)
@ExtendWith(TestFailHandle.class)
public class Pt1TableControlAreaDataTest extends AbstractPt1TableDataTest {
	private PtTable tableToTest;

	private String testArea;

	@InjectService
	CacheService cacheService;

	@InjectService
	List<IContextFunction> contextFunctions;

	List<Arguments.ArgumentSet> controlAreasAndPtTable;

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
		return super.getReferenceDir() + testArea + "/";
	}

	@Override
	public String getTestTableReferenceName() {
		if (tableToTest != null) {
			return tableToTest.shortcut();
		}
		return null;
	}

	private String getControlAreaDesignation(final String areaId) {
		final MultiContainer_AttributeGroup finalContainer = PlanProSchnittstelleExtensions
				.getContainer(planProSchnittstelle, ContainerType.FINAL);
		final List<Stell_Bereich> finalAreas = Streams
				.stream(finalContainer.getStellBereich())
				.toList();

		final MultiContainer_AttributeGroup initalContainer = PlanProSchnittstelleExtensions
				.getContainer(planProSchnittstelle, ContainerType.INITIAL);
		final List<Stell_Bereich> initalAreas = Streams
				.stream(initalContainer.getStellBereich())
				.toList();

		return Stream.of(finalAreas, initalAreas)
				.flatMap(List::stream)
				.filter(area -> area.getIdentitaet()
						.getWert()
						.equalsIgnoreCase(areaId))
				.map(StellBereichExtensions::getStellBeteichBezeichnung)
				.findFirst()
				.orElse("");
	}

	private void givenControlArea() {
		final MultiContainer_AttributeGroup finalContainer = PlanProSchnittstelleExtensions
				.getContainer(planProSchnittstelle, ContainerType.FINAL);
		final List<Stell_Bereich> finalAreas = Streams
				.stream(finalContainer.getStellBereich())
				.toList();

		final MultiContainer_AttributeGroup initalContainer = PlanProSchnittstelleExtensions
				.getContainer(planProSchnittstelle, ContainerType.INITIAL);
		final List<Stell_Bereich> initalAreas = Streams
				.stream(initalContainer.getStellBereich())
				.toList();
		final Set<String> areasId = Stream.of(initalAreas, finalAreas)
				.flatMap(List::stream)
				.filter(Objects::nonNull)
				.map(area -> area.getIdentitaet().getWert())
				.collect(Collectors.toSet());

		controlAreasAndPtTable = areasId.stream()
				.flatMap(areaId -> PtTable.tablesToTest.stream()
						.filter(table -> table.category().endsWith("estw"))
						.map(table -> new Pair<>(areaId, table)))
				.map(pair -> Arguments.argumentSet(
						getControlAreaDesignation(pair.getFirst()) + " - "
								+ pair.getSecond().shortcut(),
						pair.getFirst(), pair.getSecond()))
				.toList();
	}

	@BeforeAll
	@Override
	protected void beforeAll() throws Exception {
		super.beforeAll();
		givenControlArea();
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
	void testExistTableTransformService() {
		assertTrue(assertInjectedAllTransformationService(
				modelServiceMap.values().stream().toList()));
	}

	/**
	 * Compare table data with reference file
	 * 
	 * @throws Throwable
	 */
	@ParameterizedTest(name = "{argumentSetName}")
	@FieldSource("controlAreasAndPtTable")
	@ExtendWith(TestFailHandle.class)
	void testTableControlAreaData(final String areaId, final PtTable table)
			throws Throwable {
		testArea = getControlAreaDesignation(areaId);
		tableToTest = table;
		final Executable testCase = () -> {
			givenTableService();
			assertNotNull(tableService);

			givenTestTable(table, TableType.DIFF, Set.of(areaId));
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

		doTestInMockEnvironment(testCase);
	}
}
