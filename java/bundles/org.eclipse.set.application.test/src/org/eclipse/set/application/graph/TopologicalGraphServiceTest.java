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
package org.eclipse.set.application.graph;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.eclipse.set.application.cacheservice.CacheServiceImpl;
import org.eclipse.set.basis.constants.ContainerType;
import org.eclipse.set.basis.files.ToolboxFileRole;
import org.eclipse.set.basis.graph.TopPath;
import org.eclipse.set.basis.graph.TopPoint;
import org.eclipse.set.core.services.Services;
import org.eclipse.set.core.services.cache.CacheService;
import org.eclipse.set.core.services.graph.TopologicalGraphService;
import org.eclipse.set.model.planpro.Basisobjekte.Punkt_Objekt;
import org.eclipse.set.model.planpro.Basisobjekte.Ur_Objekt;
import org.eclipse.set.model.planpro.PlanPro.PlanPro_Schnittstelle;
import org.eclipse.set.model.planpro.Signale.Signal;
import org.eclipse.set.ppmodel.extensions.PlanProSchnittstelleExtensions;
import org.eclipse.set.ppmodel.extensions.container.MultiContainer_AttributeGroup;
import org.eclipse.set.ppmodel.extensions.utils.TopGraph;
import org.eclipse.set.unittest.utils.AbstractToolboxTest;
import org.eclipse.set.utils.graph.AsSplitTopGraph.Edge;
import org.eclipse.set.utils.graph.AsSplitTopGraph.Node;
import org.jgrapht.graph.WeightedPseudograph;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import com.google.common.collect.Streams;

/**
 * Test {@link TopologicalGraphServiceImpl} with PlanPro file
 * 
 * @author truong
 */
@SuppressWarnings("nls")
public class TopologicalGraphServiceTest extends AbstractToolboxTest {

	TopologicalGraphService testee;
	TopGraph topGraph;
	MultiContainer_AttributeGroup container;
	CacheService cachService;

	void givenTopologicalGraphService() throws Exception {
		testee = new TopologicalGraphServiceImpl();
		final Map<PlanPro_Schnittstelle, WeightedPseudograph<Node, Edge>> topGraphBase = new HashMap<>();
		final WeightedPseudograph<Node, Edge> pseudograph = new WeightedPseudograph<>(
				Edge.class);
		final Method addContainerGraph = TopologicalGraphServiceImpl.class
				.getDeclaredMethod("addContainerToGraph",
						WeightedPseudograph.class,
						MultiContainer_AttributeGroup.class);
		addContainerGraph.setAccessible(true);

		for (final ContainerType type : ContainerType.values()) {
			final MultiContainer_AttributeGroup multiContainer = PlanProSchnittstelleExtensions
					.getContainer(planProSchnittstelle, type);
			addContainerGraph.invoke(testee, pseudograph, multiContainer);
		}
		topGraphBase.put(planProSchnittstelle, pseudograph);
		FieldUtils.writeField(testee, "topGraphBaseMap", topGraphBase, true);
	}

	void givenFinalContainer() {
		container = PlanProSchnittstelleExtensions
				.getContainer(planProSchnittstelle, ContainerType.FINAL);
	}

	void givenTopGraph() {
		topGraph = new TopGraph(container.getTOPKante());
	}

	void givenCacheService() {
		cachService = new CacheServiceImpl() {
			@Override
			protected <T extends Ur_Objekt> ToolboxFileRole getSessionRole(
					final T schnittStelle) {
				return ToolboxFileRole.SESSION;
			}
		};
	}

	@Test
	void testFindShortedDistanceInDirection() throws Exception {
		try (MockedStatic<Services> mockServices = Mockito
				.mockStatic(Services.class)) {
			setupTest(mockServices);
			// 60L113X -> 60ZU21
			// Signal 60L113X is "gegen" topological direction
			final Optional<BigDecimal> testDistance1 = testee
					.findShortestDistanceInDirection(
							getTopPoint("43634A33-76AD-441C-8585-838C8528E2E6"),
							getTopPoint("BDB6CEAF-57F9-419D-8540-93B76EF686AD"),
							false);

			assertTrue(testDistance1.isPresent());
			assertEquals(testDistance1.get(), BigDecimal.valueOf(844.465));

			final Optional<BigDecimal> testDistance2 = testee
					.findShortestDistanceInDirection(
							getTopPoint("43634A33-76AD-441C-8585-838C8528E2E6"),
							getTopPoint("BDB6CEAF-57F9-419D-8540-93B76EF686AD"),
							true);
			assertFalse(testDistance2.isPresent());

			// 60AA -> 60P1
			// Signal 60AA is "in" topological direction
			final Optional<BigDecimal> testDistance3 = testee
					.findShortestDistanceInDirection(
							getTopPoint("67BE8406-9D49-437D-98FF-D878941DF2F4"),
							getTopPoint("C10AB78B-EA5E-44BA-BAC3-AB3B3D6C503C"),
							true);
			// It given't relevant path from 60AA -> 60P1
			assertFalse(testDistance3.isPresent());
		}
	}

	@Test
	void testFindShortesRelevantPath() throws Exception {
		try (MockedStatic<Services> mockServices = Mockito
				.mockStatic(Services.class)) {
			setupTest(mockServices);
			// 60L107X -> 60L106X
			// it give no relevant path from 60L107X to 60L106X
			final Optional<TopPath> testPath1 = testee
					.findTopologicalShortesPath(
							getTopPoint("2B347074-8B80-48F8-960D-DF2F8DC0244F"),
							getTopPoint("78BB7C0E-A90D-4A6E-9F60-0A855A010B6B") //
					);

			assertTrue(testPath1.isEmpty());

			// 60L107X -> 60L123Y
			final Optional<TopPath> testPath2 = testee
					.findTopologicalShortesPath(
							getTopPoint("2B347074-8B80-48F8-960D-DF2F8DC0244F"),
							getTopPoint("3941E450-42FE-4F9A-9370-262C50E2BC9F") //
					);

			assertTrue(testPath2.isPresent());

			final Optional<BigDecimal> shortestDistance = testee
					.findShortestDistance(
							getTopPoint("2B347074-8B80-48F8-960D-DF2F8DC0244F"),
							getTopPoint("3941E450-42FE-4F9A-9370-262C50E2BC9F") //
					);

			assertEquals(shortestDistance.get(), testPath2.get().length());
		}
	}

	@Test
	void testFindPointsInRadius() throws Exception {
		final double radius = 1000;
		try (final MockedStatic<Services> mockServices = Mockito
				.mockStatic(Services.class)) {
			setupTest(mockServices);
			// 60ZR1
			final TopPoint start = getTopPoint(
					"B26E901C-BEEF-463C-8690-BD23EC8B3BB2");

			final Map<TopPoint, Set<Signal>> topPointSignalsMap = new HashMap<>();
			Streams.stream(container.getSignal()).forEach(signal -> {
				final TopPoint topPoint = new TopPoint(signal);
				final Optional<TopPoint> samePositionSignal = topPointSignalsMap
						.keySet()
						.stream()
						.filter(p -> p.edge() == topPoint.edge() && p.distance()
								.compareTo(topPoint.distance()) == 0)
						.findFirst();
				if (samePositionSignal.isEmpty()) {
					final HashSet<Signal> signalSet = new HashSet<>();
					signalSet.add(signal);
					topPointSignalsMap.put(topPoint, signalSet);
				} else {
					topPointSignalsMap.get(samePositionSignal.get())
							.add(signal);
				}
			});
			final Set<String> signalInRaidusWithoutDirection = topPointsToSignalGuid( //
					topPointSignalsMap, //
					testee.findClosestPoints(start, topPointSignalsMap.keySet(),
							radius, Optional.empty()));
			assertEquals(Set.of(//
					// 60
					"3ACA01D4-3707-4813-84A6-8915E3E885C3", //
					// 60HM1B
					"77647EC6-BD6F-45B3-B3D3-C9B6D8A22903", //
					// 60P1
					"C10AB78B-EA5E-44BA-BAC3-AB3B3D6C503C", //
					// no Label
					"0814189A-A1B8-4EC0-B7A8-6B1F76A4F781", //
					// 60L211X
					"5156BD62-C7C6-471D-846C-137D56A44209", //
					// 60FAZU21
					"E07EEA03-6814-45A6-8DF1-238032122A2E", //
					// 60ZU20
					"06E36CF6-B36B-4EA9-B5D3-CBEC16C3AC1D", //
					// Ra 10 km 119,183
					"07AD4EA0-AA24-48AE-9D67-85C8F699AF56", //
					// 60G221X
					"1D03C214-5E6A-4610-B228-52A9701691F1", //
					// 60N21
					"C1BA183F-ADF4-41F7-9098-D26C2A3B31BA", //
					// 60
					"872AA688-B22A-486C-BF9E-1A1C429097E1", //
					// 60G220X
					"EF6E2D71-2219-47E7-B4EC-B46E29EEA266", //
					// no label
					"700D30FE-430F-4D67-AA8A-DAB86A796938", //
					// 60PHLIG
					"603BF949-742C-475C-A7EF-F255F193F1C4", //
					// 60G221Y
					"E3F0BE0E-24E9-4A58-8DD8-F19A5EA4AE1F", //
					// 60G220X
					"0BE4CE8E-C263-4453-B0CB-83A49E49E96D", //
					// 60
					"43BD2EC4-F13C-4BB6-8EF3-17E0A9419F4E", //
					// 60G221Y
					"4B1CDA54-379F-4B67-9A89-7DC256360CE7", //
					// 60ZU21
					"BDB6CEAF-57F9-419D-8540-93B76EF686AD", //
					// 60FAZU20
					"F1BC23EC-C2FA-4766-ACDC-3777570A528C", //
					// sh2 Gl.20
					"F23159B9-1CDF-48BA-8F29-7B6D7B5C0EC1", //
					// 60
					"1808FC97-275D-4322-8AAA-5AF34BF37BEA"),
					signalInRaidusWithoutDirection);

			final Set<String> signalInRaidusInDirection = topPointsToSignalGuid( //
					topPointSignalsMap, //
					testee.findClosestPoints(start, topPointSignalsMap.keySet(),
							radius, Optional.of(Boolean.TRUE)));

			assertEquals(Set.of(//
					// 60HM1B
					"77647EC6-BD6F-45B3-B3D3-C9B6D8A22903", //
					// 60FAZU21
					"E07EEA03-6814-45A6-8DF1-238032122A2E", //
					// 60ZU20
					"06E36CF6-B36B-4EA9-B5D3-CBEC16C3AC1D", //
					// 60G221X
					"1D03C214-5E6A-4610-B228-52A9701691F1", //
					// 60N21
					"C1BA183F-ADF4-41F7-9098-D26C2A3B31BA", //
					// 60G220X
					"EF6E2D71-2219-47E7-B4EC-B46E29EEA266", //
					// no label
					"700D30FE-430F-4D67-AA8A-DAB86A796938", //
					// 60G221Y
					"E3F0BE0E-24E9-4A58-8DD8-F19A5EA4AE1F", //
					// 60G220X
					"0BE4CE8E-C263-4453-B0CB-83A49E49E96D", //
					// 60
					"43BD2EC4-F13C-4BB6-8EF3-17E0A9419F4E", //
					// 60ZU21
					"BDB6CEAF-57F9-419D-8540-93B76EF686AD", //
					// 60FAZU20
					"F1BC23EC-C2FA-4766-ACDC-3777570A528C", //
					// sh2 Gl.20
					"F23159B9-1CDF-48BA-8F29-7B6D7B5C0EC1", //
					// 60
					"1808FC97-275D-4322-8AAA-5AF34BF37BEA"),
					signalInRaidusInDirection);
			final Set<String> signalInRaidusOppositeDirection = topPointsToSignalGuid( //
					topPointSignalsMap, //
					testee.findClosestPoints(start, topPointSignalsMap.keySet(),
							radius, Optional.of(Boolean.FALSE)));
			assertEquals(Set.of(//
					// 60
					"3ACA01D4-3707-4813-84A6-8915E3E885C3", //
					// 60P1
					"C10AB78B-EA5E-44BA-BAC3-AB3B3D6C503C", //
					// no Label
					"0814189A-A1B8-4EC0-B7A8-6B1F76A4F781", //
					// 60L211X
					"5156BD62-C7C6-471D-846C-137D56A44209", //
					// Ra 10 km 119,183
					"07AD4EA0-AA24-48AE-9D67-85C8F699AF56", //
					// 60
					"872AA688-B22A-486C-BF9E-1A1C429097E1", //
					// 60PHLIG
					"603BF949-742C-475C-A7EF-F255F193F1C4", //
					// 60G221Y
					"4B1CDA54-379F-4B67-9A89-7DC256360CE7"),
					signalInRaidusOppositeDirection);

		}
	}

	private static Set<String> topPointsToSignalGuid(
			final Map<TopPoint, Set<Signal>> topPointSignalsMap,
			final Set<TopPoint> points) {
		return points.stream()
				.flatMap(p -> topPointSignalsMap.get(p).stream())
				.map(s -> s.getIdentitaet().getWert())
				.sorted()
				.collect(Collectors.toSet());
	}

	private TopPoint getTopPoint(final String guid) {
		final Punkt_Objekt punkObjekt = Streams
				.stream(container.getPunktObjekts())
				.filter(po -> po.getIdentitaet().getWert().equals(guid))
				.findFirst()
				.orElse(null);
		if (punkObjekt == null) {
			throw new NullPointerException();
		}
		return new TopPoint(punkObjekt);
	}

	private void setupTest(final MockedStatic<Services> mockServices)
			throws Exception {
		givenCacheService();
		mockServices.when(Services::getCacheService).thenReturn(cachService);
		givenPlanProFile(getTestFile());
		givenTopologicalGraphService();
		mockServices.when(Services::getTopGraphService).thenReturn(testee);
		givenFinalContainer();
		givenTopGraph();
	}

	private static String getTestFile() {
		return PPHN_1_10_0_3_20220517_PLANPRO;
	}

}
