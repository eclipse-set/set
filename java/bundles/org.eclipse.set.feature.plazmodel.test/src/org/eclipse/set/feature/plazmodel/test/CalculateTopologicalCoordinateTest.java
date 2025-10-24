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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Method;
import java.util.ArrayList;
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
import org.eclipse.set.application.geometry.GeoKanteGeometryServiceImpl;
import org.eclipse.set.application.geometry.PointObjectPositionServiceImpl;
import org.eclipse.set.application.geometry.GeoKanteGeometryServiceImpl.GeoKanteGeometrySessionData;
import org.eclipse.set.basis.Pair;
import org.eclipse.set.basis.constants.ContainerType;
import org.eclipse.set.basis.geometry.GEOKanteMetadata;
import org.eclipse.set.feature.plazmodel.check.GeoCoordinateValid;
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
import org.eclipse.set.unittest.utils.AbstractToolboxTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.locationtech.jts.geom.Coordinate;

/**
 * Test calculate topological Coordinate of {@link Punkt_Objekt}
 * 
 * @author truong
 */
public class CalculateTopologicalCoordinateTest extends AbstractToolboxTest {

	/**
	 * @return the reference files
	 */
	protected static Stream<Arguments> getReferenceFiles() {
		return Stream.of(Arguments
				.of(new Pair<>("pphn", PPHN_1_10_0_3_20220517_PLANPRO)));
	}

	GeoCoordinateValid testee;
	List<CSVRecord> csvRecords;
	List<TopologicalCoordinate> topologicalCoordinates;
	GeoKanteGeometryServiceImpl geometryService;

	@ParameterizedTest
	@MethodSource("getReferenceFiles")
	void testTopologischeCoordinateCalculate(Pair<String, String> testFile)
			throws Exception {
		givenGeoKanteGeometryService();
		givenGeoCoordinateValid();
		givenTopologicalCoordinaten(testFile.getFirst());
		givenPlanProFile(testFile.getSecond());
		whenCalculateCoordinate();
		thenExpectAllCoordinateAreEqualReference();

	}

	private void thenExpectAllCoordinateAreEqualReference() {
		assertEquals(csvRecords.size(), topologicalCoordinates.size());
		topologicalCoordinates.forEach(coord -> {
			String state = switch (coord.state()) {
				case FINAL -> "Ziel";
				case INITIAL -> "Start";
				default -> "Alleinstehend";
			};
			String guid = coord.po().getIdentitaet().getWert();
			List<CSVRecord> csvEntries = csvRecords.stream()
					.filter(entry -> entry.get(3).equals(guid)
							&& entry.get(1).equals(state))
					.toList();
			assertFalse(csvEntries.isEmpty());
			assertEquals(1, csvEntries.size());
			CSVRecord csvEntry = csvEntries.getFirst();
			String[] coordStrArry = getCoordinateString(EObjectExtensions
					.getNullableObject(coord,
							c -> coord.coordinate().getCoordinate())
					.orElse(null));
			String crs = EObjectExtensions
					.getNullableObject(coord,
							c -> coord.coordinate().getCRS().getLiteral())
					.orElse("Fehler bei der Berechnung");

			// No need to compare State and GUID here. It is already valid
			Map<Integer, String> valueToCompare = Map.of(4, crs, 5,
					coordStrArry[0], 6, coordStrArry[1]);
			assertTrue(isSame(csvEntry, valueToCompare));
		});
	}

	private boolean isSame(CSVRecord entry,
			Map<Integer, String> valueToCompare) {
		return valueToCompare.keySet()
				.stream()
				.allMatch(index -> entry.get(index)
						.equals(valueToCompare.get(index)));
	}

	private String[] getCoordinateString(Coordinate coord) {
		if (coord == null) {
			return new String[] { "Fehler bei der Berechnung",
					"Fehler bei der Berechnung" };
		}
		Function<Double, String> toStringFunc = value -> value.toString()
				.replace(".", ",");
		return new String[] { toStringFunc.apply(Double.valueOf(coord.x)),
				toStringFunc.apply(Double.valueOf(coord.y)) };
	}

	private void whenCalculateCoordinate()
			throws NoSuchMethodException, SecurityException {
		Method calculatedMethode = GeoCoordinateValid.class.getDeclaredMethod(
				"calculateCoordinate", ContainerType.class, Punkt_Objekt.class,
				Punkt_Objekt_TOP_Kante_AttributeGroup.class);
		calculatedMethode.setAccessible(true);
		List.of(ContainerType.INITIAL, ContainerType.FINAL)
				.stream()
				.forEach(type -> {
					MultiContainer_AttributeGroup container = PlanProSchnittstelleExtensions
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
										} catch (Exception e) {
											throw new RuntimeException(e);
										}

									}));
				});
		topologicalCoordinates = testee.getTopologischeCoordinaten();
	}

	private void givenTopologicalCoordinaten(String testFile)
			throws IOException {
		String fileName = testFile + "_topological_coordinate.csv";

		Builder builder = CSVFormat.Builder.create(CSVFormat.DEFAULT);
		builder.setDelimiter(";");
		try (InputStream refResource = CalculateTopologicalCoordinateTest.class
				.getClassLoader()
				.getResourceAsStream(fileName);
				final Reader reader = new InputStreamReader(refResource);
				final CSVParser parser = new CSVParser(reader,
						builder.build())) {
			csvRecords = parser.getRecords();
			// Remove CSV header info
			csvRecords = csvRecords.subList(5, csvRecords.size());
		}
	}

	private void givenGeoCoordinateValid() throws IllegalAccessException {
		testee = new GeoCoordinateValid();
		FieldUtils.writeField(testee, "topologischeCoordinaten",
				Optional.empty(), true);
		FieldUtils.writeField(testee, "alreadyFoundMetaData",
				new ArrayList<GEOKanteMetadata>(), true);
		FieldUtils.writeField(testee, "pointObjectPositionService",
				new PointObjectPositionServiceImpl(), true);
		FieldUtils.writeField(testee, "geometryService", geometryService, true);
	}

	private void givenGeoKanteGeometryService() throws Exception {
		geometryService = new GeoKanteGeometryServiceImpl();
		final Map<PlanPro_Schnittstelle, GeoKanteGeometrySessionData> sessionesData = new HashMap<>();
		GeoKanteGeometrySessionData geoKanteGeometrySessionData = new GeoKanteGeometrySessionData();
		sessionesData.put(planProSchnittstelle, geoKanteGeometrySessionData);
		FieldUtils.writeField(geometryService, "sessionesData", sessionesData,
				true);
		Method declaredMethod = geometryService.getClass()
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
		FieldUtils.writeField(geometryService, "isProcessComplete", true, true);

	}

}
