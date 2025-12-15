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

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.eclipse.set.application.cacheservice.CacheServiceImpl;
import org.eclipse.set.basis.constants.ContainerType;
import org.eclipse.set.basis.files.ToolboxFileRole;
import org.eclipse.set.core.services.Services;
import org.eclipse.set.core.services.cache.CacheService;
import org.eclipse.set.core.services.graph.TopologicalGraphService;
import org.eclipse.set.model.planpro.Fahrstrasse.Fstr_Fahrweg;
import org.eclipse.set.model.planpro.PlanPro.PlanPro_Schnittstelle;
import org.eclipse.set.ppmodel.extensions.DwegExtensions;
import org.eclipse.set.ppmodel.extensions.FstrFahrwegExtensions;
import org.eclipse.set.ppmodel.extensions.PlanProSchnittstelleExtensions;
import org.eclipse.set.ppmodel.extensions.container.MultiContainer_AttributeGroup;
import org.eclipse.set.ppmodel.extensions.utils.TopGraph;
import org.eclipse.set.unittest.utils.AbstractToolboxTest;
import org.eclipse.set.utils.graph.AsSplitTopGraph.Edge;
import org.eclipse.set.utils.graph.AsSplitTopGraph.Node;
import org.jgrapht.graph.WeightedPseudograph;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import com.google.common.collect.Streams;

/**
 * 
 */
public class TopologicalGraphServiceTest extends AbstractToolboxTest {
	/**
	 * @return the siteplan reference files
	 */
	protected static Stream<Arguments> getReferenceFiles() {
		return Stream.of(Arguments.of(NB_INFO_TEST, "nbTest"));
	}

	TopologicalGraphService testee;
	TopGraph topGraph;
	MultiContainer_AttributeGroup finalContainer;
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
			final MultiContainer_AttributeGroup container = PlanProSchnittstelleExtensions
					.getContainer(planProSchnittstelle, type);
			addContainerGraph.invoke(testee, pseudograph, container);
		}
		topGraphBase.put(planProSchnittstelle, pseudograph);
		FieldUtils.writeField(testee, "topGraphBaseMap", topGraphBase, true);
	}

	void givenFinalContainer() {
		finalContainer = PlanProSchnittstelleExtensions
				.getContainer(planProSchnittstelle, ContainerType.FINAL);
	}

	void givenTopGraph() {
		topGraph = new TopGraph(finalContainer.getTOPKante());
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

	@ParameterizedTest
	@MethodSource("getReferenceFiles")
	void test() throws Exception {
		try (MockedStatic<Services> mockServices = Mockito
				.mockStatic(Services.class)) {
			mockServices.when(Services::getCacheService)
					.thenReturn(cachService);
			givenTopologicalGraphService();
			givenFinalContainer();
			givenTopGraph();
		}
	}

	void topGraphDoFindInRichtungSignal() {
		Streams.stream(finalContainer.getFstrDWeg()).map(dweg -> {
			final Fstr_Fahrweg fstrFahrweg = DwegExtensions.getFstrFahrweg(dweg);
			
		})
		
	}
}
