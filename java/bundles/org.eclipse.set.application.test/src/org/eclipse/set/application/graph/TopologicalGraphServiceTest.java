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
import java.util.Map;
import java.util.Optional;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.eclipse.set.application.cacheservice.CacheServiceImpl;
import org.eclipse.set.basis.constants.ContainerType;
import org.eclipse.set.basis.files.ToolboxFileRole;
import org.eclipse.set.basis.graph.TopPoint;
import org.eclipse.set.core.services.Services;
import org.eclipse.set.core.services.cache.CacheService;
import org.eclipse.set.core.services.graph.TopologicalGraphService;
import org.eclipse.set.model.planpro.Basisobjekte.Punkt_Objekt;
import org.eclipse.set.model.planpro.PlanPro.PlanPro_Schnittstelle;
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
			protected ToolboxFileRole getSessionRole(
					final PlanPro_Schnittstelle schnittStelle) {
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
