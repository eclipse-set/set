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
package org.eclipse.set.feature.plazmodel.test;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.eclipse.set.basis.Pair;
import org.eclipse.set.basis.constants.ContainerType;
import org.eclipse.set.core.services.cache.CacheService;
import org.eclipse.set.core.services.enumtranslation.EnumTranslationService;
import org.eclipse.set.core.services.geometry.GeoKanteGeometryService;
import org.eclipse.set.core.services.geometry.PointObjectPositionService;
import org.eclipse.set.core.services.graph.TopologicalGraphService;
import org.eclipse.set.core.services.session.SessionService;
import org.eclipse.set.feature.plazmodel.check.GeoCoordinateValid;
import org.eclipse.set.feature.plazmodel.utils.TopologicalCoordinate;
import org.eclipse.set.model.planpro.Basisobjekte.Bereich_Objekt;
import org.eclipse.set.model.planpro.Basisobjekte.Punkt_Objekt;
import org.eclipse.set.model.planpro.Basisobjekte.Punkt_Objekt_TOP_Kante_AttributeGroup;
import org.eclipse.set.model.planpro.Ortung.FMA_Komponente;
import org.eclipse.set.model.planpro.PZB.PZB_Element;
import org.eclipse.set.model.tablemodel.Table;
import org.eclipse.set.osgi.test.utils.AbstractOsgiTableTest;
import org.eclipse.set.osgi.test.utils.AbstractOsgiTest;
import org.eclipse.set.osgi.test.utils.OsgiTestTestFile;
import org.eclipse.set.osgi.test.utils.TestFailHandle;
import org.eclipse.set.osgi.test.utils.TestFile;
import org.eclipse.set.ppmodel.extensions.EObjectExtensions;
import org.eclipse.set.ppmodel.extensions.PlanProSchnittstelleExtensions;
import org.eclipse.set.ppmodel.extensions.PunktObjektExtensions;
import org.eclipse.set.ppmodel.extensions.container.MultiContainer_AttributeGroup;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.provider.Arguments;
import org.locationtech.jts.geom.Coordinate;
import org.osgi.service.event.EventAdmin;
import org.osgi.test.common.annotation.InjectService;
import org.osgi.test.junit5.service.ServiceExtension;

import com.google.common.collect.Streams;

/**
 * Test calculate topological Coordinate of {@link Punkt_Objekt}
 * 
 * @author truong
 */
@TestInstance(Lifecycle.PER_CLASS)
@ExtendWith(ServiceExtension.class)
public class CalculateTopologicalCoordinateTest extends AbstractOsgiTableTest {

	private static final String CSV_HEADER = "Lfd. Nr.;Zustand;Objektart;Punkt_Objekt;TopKante;Koordinatensystem;x;y";

	private static final String REF_DIR = "resource/";

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

	@InjectService
	EnumTranslationService enumTranslationService;
	@InjectService
	EventAdmin eventAdmin;
	GeoCoordinateValid geoCoordinateValid;
	@InjectService
	GeoKanteGeometryService geometryService;
	@InjectService
	PointObjectPositionService pointObjectPositionService;
	@InjectService
	SessionService sessionService;

	@InjectService
	TopologicalGraphService topgraphService;
	List<TopologicalCoordinate> topologicalCoordinates;

	@Override
	public String getTableCSVHeader(final Table table) {
		return CSV_HEADER;
	}

	@Override
	public String getTestTableReferenceName() {
		return "topological_coordinate";
	}

	private void givenGeoCoordinateValid() throws IllegalAccessException {
		geoCoordinateValid = new GeoCoordinateValid();
		FieldUtils.writeField(testee, "topologicalCoordinates",
				Optional.empty(), true);
		FieldUtils.writeField(testee, "alreadyFoundMetaData", new ArrayList<>(),
				true);
		// FieldUtils.writeField(testee, "pointObjectPositionService",
		// new PointObjectPositionServiceImpl(), true);
	}

	private void givenTopologicalCoordinaten() {
		referenceData = loadReferenceFile(getTestTableReferenceName());
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
		List.of(ContainerType.INITIAL, ContainerType.FINAL)
				.stream()
				.forEach(type -> {
					final MultiContainer_AttributeGroup container = PlanProSchnittstelleExtensions
							.getContainer(planProSchnittstelle, type);
					final List<Bereich_Objekt> bereichObjects = Streams
							.stream(container.getBereichObjekt())
							.toList();
					Streams.stream(container.getPunktObjekts())
							.filter(po -> PunktObjektExtensions
									.existLateralDistance(po)
									|| po instanceof FMA_Komponente
									|| po instanceof PZB_Element)
							.forEach(po -> po.getPunktObjektTOPKante()
									.forEach(potk -> {
										try {
											calculatedMethode.invoke(testee,
													type, po, potk,
													bereichObjects);
										} catch (final Exception e) {
											throw new RuntimeException(e);
										}

									}));
				});
		topologicalCoordinates = geoCoordinateValid
				.getTopologischeCoordinaten();
		isTableEmpty = topologicalCoordinates.isEmpty();
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
	protected String getReferenceDir() {
		return REF_DIR;
	}

	@Override
	protected SessionService getSessionService() {
		return sessionService;
	}

	@Override
	protected TestFile getTestFile() {
		return OsgiTestTestFile.PPHN_1_10_0_3;
	}

	@Override
	protected Class<? extends AbstractOsgiTest> getTestResourceClass() {
		return getClass();
	}

	@Override
	protected TopologicalGraphService getTopologicalGraphService() {
		return topgraphService;
	}

	@Test
	@ExtendWith(TestFailHandle.class)
	void testTopologischeCoordinateCalculate() throws Throwable {
		doTestInMockEnvironment(() -> {
			givenTopologicalCoordinaten();
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
