/**
 * Copyright (c) 2025 DB InfraGO AG and others
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 */
package org.eclipse.set.feature.plazmodel.test.coordinaten;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

import org.apache.commons.csv.CSVRecord;
import org.eclipse.set.basis.Pair;
import org.eclipse.set.basis.constants.ContainerType;
import org.eclipse.set.core.services.cache.CacheService;
import org.eclipse.set.core.services.enumtranslation.EnumTranslationService;
import org.eclipse.set.core.services.geometry.GeoKanteGeometryService;
import org.eclipse.set.core.services.geometry.PointObjectPositionService;
import org.eclipse.set.core.services.graph.TopologicalGraphService;
import org.eclipse.set.core.services.session.SessionService;
import org.eclipse.set.feature.plazmodel.check.GeoCoordinateValid;
import org.eclipse.set.feature.plazmodel.check.PlazCheck;
import org.eclipse.set.feature.plazmodel.test.AbstractPlaZModelTest;
import org.eclipse.set.feature.plazmodel.utils.TopologicalCoordinate;
import org.eclipse.set.model.planpro.Basisobjekte.Punkt_Objekt;
import org.eclipse.set.model.planpro.Basisobjekte.Punkt_Objekt_TOP_Kante_AttributeGroup;
import org.eclipse.set.model.tablemodel.Table;
import org.eclipse.set.osgi.test.utils.AbstractOsgiTest;
import org.eclipse.set.osgi.test.utils.TestFailHandle;
import org.eclipse.set.ppmodel.extensions.EObjectExtensions;
import org.eclipse.set.utils.ToolboxConfiguration;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.provider.Arguments;
import org.locationtech.jts.geom.Coordinate;
import org.mockito.MockedStatic;
import org.osgi.framework.BundleContext;
import org.osgi.service.event.EventAdmin;
import org.osgi.test.common.annotation.InjectBundleContext;
import org.osgi.test.common.annotation.InjectService;
import org.osgi.test.junit5.context.BundleContextExtension;
import org.osgi.test.junit5.service.ServiceExtension;

/**
 * Test calculate topological Coordinate of {@link Punkt_Objekt}
 * 
 * @author truong
 */
@TestInstance(Lifecycle.PER_CLASS)
@ExtendWith(ServiceExtension.class)
@ExtendWith(TestFailHandle.class)
@ExtendWith(BundleContextExtension.class)
public class CalculateTopologicalCoordinateTest extends AbstractPlaZModelTest {

	private static final String CSV_HEADER = "Lfd. Nr.;Zustand;Objektart;Punkt_Objekt;TopKante;Koordinatensystem;x;y";

	private static String[] getCoordinateString(final Coordinate coord) {
		if (coord == null) {
			return new String[] { "Fehler bei der Berechnung",
					"Fehler bei der Berechnung" };
		}
		final Function<Double, String> toStringFunc = value -> value.toString()
				.replace(".", ",");
		return new String[] { toStringFunc.apply(Double.valueOf(coord.x)),
				toStringFunc.apply(Double.valueOf(coord.y)) };
	}

	@SuppressWarnings("boxing")
	private static boolean isSame(final CSVRecord entry,
			final Map<Integer, String> valueToCompare) {
		return valueToCompare.keySet()
				.stream()
				.allMatch(index -> entry.get(index)
						.equals(valueToCompare.get(index)));
	}

	/**
	 * @return the reference files
	 */
	protected static Stream<Arguments> getReferenceFiles() {
		return Stream.of(Arguments
				.of(new Pair<>("pphn", PPHN_1_10_0_3_20220517_PLANPRO)));
	}

	@InjectService
	CacheService cacheService;
	@InjectBundleContext
	BundleContext context;

	@InjectService
	EnumTranslationService enumTranslationService;
	@InjectService
	EventAdmin eventAdmin;
	GeoCoordinateValid geoCoordinateValid;
	@InjectService
	GeoKanteGeometryService geometryService;

	@InjectService
	List<PlazCheck> plazChecks;
	@InjectService
	PointObjectPositionService pointObjectPositionService;

	@InjectService
	PointObjectPositionService positionService;
	@InjectService
	SessionService sessionService;

	@InjectService
	TopologicalGraphService topgraphService;

	List<TopologicalCoordinate> topologicalCoordinates;

	@Override
	public String getTableCSVHeader(final Table table) {
		return CSV_HEADER + System.lineSeparator();
	}

	@Override
	public String getTestTableReferenceName() {
		return "topological_coordinate";
	}

	private void givenGeoCoordinateValid() throws IllegalArgumentException {
		final Optional<PlazCheck> target = plazChecks.stream()
				.filter(GeoCoordinateValid.class::isInstance)
				.findFirst();
		if (target.isPresent()) {
			geoCoordinateValid = (GeoCoordinateValid) target.get();
			return;
		}
		throw new IllegalArgumentException("Can't found GeoCoordinateValie");
	}

	@SuppressWarnings("boxing")
	private void thenExpectAllCoordinateAreEqualReference() {
		assertEquals(referenceData.size(), topologicalCoordinates.size());
		topologicalCoordinates.forEach(coord -> {
			final String state = switch (coord.state()) {
				case FINAL -> "Ziel";
				case INITIAL -> "Start";
				default -> "Alleinstehend";
			};
			final String poGuid = coord.po().getIdentitaet().getWert();
			final String topKanteGuid = coord.potk().getIDTOPKante().getWert();
			final List<CSVRecord> csvEntries = referenceData.stream()
					.filter(entry -> entry.get(1).equals(state))
					.filter(entry -> entry.get(3).equals(poGuid))
					.filter(entry -> entry.get(4).equals(topKanteGuid))
					.toList();
			assertFalse(csvEntries.isEmpty());
			assertEquals(1, csvEntries.size());
			final CSVRecord csvEntry = csvEntries.getFirst();
			final String[] coordStrArry = getCoordinateString(EObjectExtensions
					.getNullableObject(coord,
							c -> coord.coordinate().getCoordinate())
					.orElse(null));
			final String crs = EObjectExtensions
					.getNullableObject(coord,
							c -> coord.coordinate().getCRS().getLiteral())
					.orElse("Fehler bei der Berechnung");

			// No need to compare State and GUID here. It is already valid
			final Map<Integer, String> valueToCompare = Map.of(5, crs, 6,
					coordStrArry[0], 7, coordStrArry[1]);
			assertTrue(isSame(csvEntry, valueToCompare), () -> {
				return "Expect: " + csvEntry.toString() + "- Actual: "
						+ coord.toString();
			});
		});
	}

	private void whenCalculateCoordinate()
			throws NoSuchMethodException, SecurityException {
		final Method calculatedMethode = GeoCoordinateValid.class
				.getDeclaredMethod("calculateCoordinate", ContainerType.class,
						Punkt_Objekt.class,
						Punkt_Objekt_TOP_Kante_AttributeGroup.class,
						List.class);
		calculatedMethode.setAccessible(true);
		geoCoordinateValid.run(modelSession);
		topologicalCoordinates = geoCoordinateValid
				.getTopologischeCoordinaten();
		isTableEmpty = topologicalCoordinates.isEmpty();
	}

	@Override
	protected BundleContext getBundleContext() {
		return context;
	}

	@Override
	protected CacheService getCacheService() {
		return cacheService;
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
	protected Class<? extends AbstractOsgiTest> getTestResourceClass() {
		return getClass();
	}

	@Override
	protected TopologicalGraphService getTopologicalGraphService() {
		return topgraphService;
	}

	@Override
	protected void mockToolboxConfigurationMethode(
			final MockedStatic<ToolboxConfiguration> mockConfiguration) {
		super.mockToolboxConfigurationMethode(mockConfiguration);
		mockConfiguration.when(ToolboxConfiguration::getGeometryBlossStepLength)
				.thenReturn(Double.valueOf(0.5));
		mockConfiguration
				.when(ToolboxConfiguration::getGeometryClothoidStepLength)
				.thenReturn(Double.valueOf(0.5));
	}

	@Test
	void testTopologischeCoordinateCalculate() throws Throwable {
		doTestInMockEnvironment(() -> {
			givenReferenceData();
			givenGeoCoordinateValid();
			whenCalculateCoordinate();
			if (isTableEmpty) {
				thenExpectNotExistReference();
				return;
			}
			thenExpectAllCoordinateAreEqualReference();
		});
	}
}
