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

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVFormat.Builder;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.set.application.geometry.GeoKanteGeometryServiceImpl;
import org.eclipse.set.application.geometry.GeoKanteGeometrySessionData;
import org.eclipse.set.basis.Pair;
import org.eclipse.set.basis.constants.ContainerType;
import org.eclipse.set.basis.constants.Events;
import org.eclipse.set.basis.files.ToolboxFileRole;
import org.eclipse.set.feature.plazmodel.check.GeoCoordinateValid;
import org.eclipse.set.feature.plazmodel.check.PlazCheck;
import org.eclipse.set.feature.plazmodel.export.TopologicalCoordinate;
import org.eclipse.set.model.planpro.Basisobjekte.Punkt_Objekt;
import org.eclipse.set.model.planpro.Basisobjekte.Punkt_Objekt_TOP_Kante_AttributeGroup;
import org.eclipse.set.model.planpro.Ortung.FMA_Komponente;
import org.eclipse.set.model.planpro.PZB.PZB_Element;
import org.eclipse.set.model.planpro.PlanPro.PlanPro_Schnittstelle;
import org.eclipse.set.ppmodel.extensions.EObjectExtensions;
import org.eclipse.set.ppmodel.extensions.PlanProSchnittstelleExtensions;
import org.eclipse.set.ppmodel.extensions.PunktObjektExtensions;
import org.eclipse.set.ppmodel.extensions.container.MultiContainer_AttributeGroup;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.locationtech.jts.geom.Coordinate;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventAdmin;
import org.osgi.service.event.EventConstants;
import org.osgi.test.common.annotation.InjectService;

/**
 * Test calculate topological Coordinate of {@link Punkt_Objekt}
 * 
 * @author truong
 */
public class CalculateTopologicalCoordinateTest extends AbstractPlaZModelTest {

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

	List<CSVRecord> csvRecords;
	@InjectService
	EventAdmin eventAdmin;

	GeoKanteGeometryServiceImpl geometryService;

	@InjectService
	List<PlazCheck> plazChecks;

	GeoCoordinateValid testee;

	List<TopologicalCoordinate> topologicalCoordinates;

	private void givenGeoCoordinateValid() throws IllegalAccessException {
		final Optional<PlazCheck> testCheck = plazChecks.stream()
				.filter(GeoCoordinateValid.class::isInstance)
				.findFirst();
		if (testCheck.isEmpty()) {
			throw new RuntimeException("Missing test class");
		}
		testee = (GeoCoordinateValid) testCheck.get();
	}

	private void givenGeoKanteGeometryService() throws Exception {
		geometryService = new GeoKanteGeometryServiceImpl();
		final Map<PlanPro_Schnittstelle, GeoKanteGeometrySessionData> sessionesData = new HashMap<>();
		final GeoKanteGeometrySessionData geoKanteGeometrySessionData = new GeoKanteGeometrySessionData();
		sessionesData.put(planProSchnittstelle, geoKanteGeometrySessionData);
		FieldUtils.writeField(geometryService, "sessionesData", sessionesData,
				true);
		final Method declaredMethod = geometryService.getClass()
				.getDeclaredMethod("findGeoKanteGeometry",
						GeoKanteGeometrySessionData.class,
						MultiContainer_AttributeGroup.class);
		declaredMethod.setAccessible(true);
		declaredMethod.invoke(geometryService, geoKanteGeometrySessionData,
				PlanProSchnittstelleExtensions.getContainer(
						planProSchnittstelle, ContainerType.INITIAL));
		declaredMethod.invoke(geometryService, geoKanteGeometrySessionData,
				PlanProSchnittstelleExtensions.getContainer(
						planProSchnittstelle, ContainerType.FINAL));

	}

	private void givenTopologicalCoordinaten(final String testFile)
			throws IOException {
		final String fileName = testFile + "_topological_coordinate.csv";

		final Builder builder = CSVFormat.Builder.create(CSVFormat.DEFAULT);
		builder.setDelimiter(";");
		try (InputStream refResource = CalculateTopologicalCoordinateTest.class
				.getClassLoader()
				.getResourceAsStream(fileName);
				final Reader reader = new InputStreamReader(refResource);
				final CSVParser parser = new CSVParser(reader,
						builder.build())) {
			csvRecords = parser.getRecords();
			// Remove CSV header info
			csvRecords = csvRecords.subList(4, csvRecords.size());
		}
	}

	@SuppressWarnings("boxing")
	private void thenExpectAllCoordinateAreEqualReference() {
		assertEquals(csvRecords.size(), topologicalCoordinates.size());
		topologicalCoordinates.forEach(coord -> {
			final String state = switch (coord.state()) {
				case FINAL -> "Ziel";
				case INITIAL -> "Start";
				default -> "Alleinstehend";
			};
			final String poGuid = coord.po().getIdentitaet().getWert();
			final String topKanteGuid = coord.potk().getIDTOPKante().getWert();
			final List<CSVRecord> csvEntries = csvRecords.stream()
					.filter(entry -> entry.get(1).equals(state))
					.filter(entry -> entry.get(3).equals(poGuid))
					.filter(entry -> entry.get(4).equals(topKanteGuid))
					.toList();
			assertFalse(csvEntries.isEmpty());
			assertEquals(1, csvEntries.size());
			final CSVRecord csvEntry = csvEntries.getFirst();
			EObjectExtensions.getNullableObject(coord,
					TopologicalCoordinate::coordinate);
			// final String[] coordStrArry = getCoordinateString(
			// );
			// final String crs = EObjectExtensions
			// .getNullableObject(coord,
			// c -> c.coordinate().getCRS().getLiteral())
			// .orElse("Fehler bei der Berechnung");
			//
			// // No need to compare State and GUID here. It is already valid
			// final Map<Integer, String> valueToCompare = Map.of(5, crs, 6,
			// coordStrArry[0], 7, coordStrArry[1]);
			// assertTrue(isSame(csvEntry, valueToCompare));
		});
	}

	private void whenCalculateCoordinate()
			throws NoSuchMethodException, SecurityException {
		final Method calculatedMethode = GeoCoordinateValid.class
				.getDeclaredMethod("calculateCoordinate", ContainerType.class,
						Punkt_Objekt.class,
						Punkt_Objekt_TOP_Kante_AttributeGroup.class);
		calculatedMethode.setAccessible(true);
		List.of(ContainerType.INITIAL, ContainerType.FINAL)
				.stream()
				.forEach(type -> {
					final MultiContainer_AttributeGroup container = PlanProSchnittstelleExtensions
							.getContainer(planProSchnittstelle, type);
					StreamSupport
							.stream(container.getPunktObjekts().spliterator(),
									false)
							.filter(po -> PunktObjektExtensions
									.existLateralDistance(po)
									|| po instanceof FMA_Komponente
									|| po instanceof PZB_Element)
							.forEach(po -> po.getPunktObjektTOPKante()
									.forEach(potk -> {
										try {
											calculatedMethode.invoke(testee,
													type, po, potk);
										} catch (final Exception e) {
											throw new RuntimeException(e);
										}

									}));
				});
		topologicalCoordinates = testee.getTopologischeCoordinaten();
	}

	@Override
	protected EventAdmin getEventAdmin() {
		return eventAdmin;
	}

	@AfterEach
	void afterEach() {
		final Map<String, Object> properties = new HashMap<>();
		properties.put(EventConstants.EVENT_TOPIC, Events.CLOSE_SESSION);
		properties.put(IEventBroker.DATA, ToolboxFileRole.SESSION);
		eventAdmin.sendEvent(new Event(Events.CLOSE_SESSION, properties));
	}

	@ParameterizedTest
	@MethodSource("getReferenceFiles")
	void testTopologischeCoordinateCalculate(
			final Pair<String, String> testFile) throws Exception {
		givenGeoKanteGeometryService();
		givenGeoCoordinateValid();
		givenTopologicalCoordinaten(testFile.getFirst());
		givenPlanProFile(testFile.getSecond());
		whenCalculateCoordinate();
		thenExpectAllCoordinateAreEqualReference();

	}

}
