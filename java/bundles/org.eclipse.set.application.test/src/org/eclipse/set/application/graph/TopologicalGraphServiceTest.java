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

import static org.eclipse.set.ppmodel.extensions.BereichObjektExtensions.toTopPoints;
import static org.eclipse.set.ppmodel.extensions.PunktObjektExtensions.getSinglePoint;
import static org.eclipse.set.ppmodel.extensions.PunktObjektTopKanteExtensions.isBelongToBereichObjekt;
import static org.eclipse.set.ppmodel.extensions.PunktObjektTopKanteExtensions.isWirkrichtungTopDirection;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.stream.Stream;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.eclipse.set.application.cacheservice.CacheServiceImpl;
import org.eclipse.set.basis.constants.ContainerType;
import org.eclipse.set.basis.files.ToolboxFileRole;
import org.eclipse.set.basis.graph.TopPoint;
import org.eclipse.set.core.services.Services;
import org.eclipse.set.core.services.cache.CacheService;
import org.eclipse.set.core.services.graph.TopologicalGraphService;
import org.eclipse.set.model.planpro.Bahnsteig.Bahnsteig_Kante;
import org.eclipse.set.model.planpro.Basisobjekte.Punkt_Objekt_TOP_Kante_AttributeGroup;
import org.eclipse.set.model.planpro.PZB.PZB_Element;
import org.eclipse.set.model.planpro.PZB.PZB_Element_Zuordnung_BP_AttributeGroup;
import org.eclipse.set.model.planpro.PZB.PZB_Element_Zuordnung_INA_AttributeGroup;
import org.eclipse.set.model.planpro.PlanPro.PlanPro_Schnittstelle;
import org.eclipse.set.model.planpro.Verweise.ID_Bahnsteig_Kante_TypeClass;
import org.eclipse.set.ppmodel.extensions.PZBElementExtensions;
import org.eclipse.set.ppmodel.extensions.PlanProSchnittstelleExtensions;
import org.eclipse.set.ppmodel.extensions.PunktObjektExtensions;
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

import com.google.common.collect.Range;
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
				.getContainer(planProSchnittstelle, ContainerType.SINGLE);
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

	@ParameterizedTest
	@MethodSource("getReferenceFiles")
	void test(final String file) throws Exception {
		try (MockedStatic<Services> mockServices = Mockito
				.mockStatic(Services.class)) {
			givenCacheService();
			mockServices.when(Services::getCacheService)
					.thenReturn(cachService);
			givenPlanProFile(file);
			givenTopologicalGraphService();
			mockServices.when(Services::getTopGraphService).thenReturn(testee);
			givenFinalContainer();
			givenTopGraph();
			doTestAction();
		}
	}

	private void doTestAction() {
		Streams.stream(container.getPZBElement()).forEach(pzb -> {
			// PZBElementExtensions.getPZBElementBezugspunkt(pzb)
			// .stream()
			// .filter(Objects::nonNull)
			// .forEach(bezugPunkt -> {
			// if (bezugPunkt instanceof final Signal signal) {
			// final Punkt_Objekt_TOP_Kante_AttributeGroup singlePoint =
			// PunktObjektExtensions
			// .getSinglePoint(signal);
			// if (pzb.getIdentitaet()
			// .getWert()
			// .equals("60b28fb6-c281-11ed-9412-8932a423e144")) {
			// System.out.println("TEST");
			// }
			// assertDoesNotThrow(() -> testee
			// .findShortestDistance(new TopPoint(pzb),
			// new TopPoint(signal)));
			// }
			// });
			if (pzb.getIdentitaet()
					.getWert()
					.equals("60b28e13-c281-11ed-9412-8932a423e144")) {
				System.out.println("TESt");
				final List<Bahnsteig_Kante> kanten = Streams
						.stream(PZBElementExtensions
								.getPZBElementZuordnungBP(pzb))
						.map(PZB_Element_Zuordnung_BP_AttributeGroup::getPZBElementZuordnungINA)
						.filter(Objects::nonNull)
						.map(PZB_Element_Zuordnung_INA_AttributeGroup::getIDBahnsteigKante)
						.filter(Objects::nonNull)
						.map(ID_Bahnsteig_Kante_TypeClass::getValue)
						.filter(Objects::nonNull)
						.toList();

				assertDoesNotThrow(() -> getBahnsteigDistances(kanten, pzb));
			}
		});
	}

	public BahnsteigDistance getBahnsteigDistances(
			final List<Bahnsteig_Kante> bahnsteigs, final PZB_Element pzb) {

		final Punkt_Objekt_TOP_Kante_AttributeGroup point = pzb
				.getPunktObjektTOPKante()
				.get(0);

		final boolean isPZBAtBahnsteig = bahnsteigs.stream()
				.anyMatch(
						bahnsteig -> isBelongToBereichObjekt(point, bahnsteig));

		if (isPZBAtBahnsteig) {
			return getBahnsteigDistanceAtMagnet(bahnsteigs, pzb);
		}
		return getBahnsteigDistancesNotAtMagnet(bahnsteigs, pzb);
	}

	private BahnsteigDistance getBahnsteigDistancesNotAtMagnet(
			final Iterable<Bahnsteig_Kante> bahnsteig, final PZB_Element pzb) {
		// If the final PZB is not final at a Bahnsteig_Kante, search in final
		// the opposite of final the
		// final effective direction
		final Punkt_Objekt_TOP_Kante_AttributeGroup potk = getSinglePoint(pzb);
		final boolean searchDirection = !isWirkrichtungTopDirection(potk);

		final Optional<Range<BigDecimal>> reduce = Streams.stream(bahnsteig)
				.map(bsk -> PunktObjektExtensions.distanceToBereichObjekt(pzb,
						bsk, searchDirection))
				.filter(Optional::isPresent)
				.map(Optional::get)
				.reduce(Range::span);
		if (reduce.isEmpty()) {
			return new BahnsteigDistance(OptionalDouble.empty(),
					OptionalDouble.empty());
		}
		return new BahnsteigDistance(reduce.get().upperEndpoint().doubleValue(),
				reduce.get().lowerEndpoint().doubleValue());
	}

	private BahnsteigDistance getBahnsteigDistanceAtMagnet(
			final Iterable<Bahnsteig_Kante> bahnsteig, final PZB_Element pzb) {
		final Punkt_Objekt_TOP_Kante_AttributeGroup potk = getSinglePoint(pzb);
		final boolean isWirkrichtungTopDirection = isWirkrichtungTopDirection(
				potk);
		final TopPoint pzbPoint = new TopPoint(pzb);
		final OptionalDouble start = Streams.stream(bahnsteig)
				.flatMap(bsk -> toTopPoints(bsk).stream()
						.flatMap(Collection::stream))
				.map(point -> testee.findShortestDistanceInDirection(pzbPoint,
						point, !isWirkrichtungTopDirection))
				.filter(Optional::isPresent)
				.mapToDouble(c -> c.get().doubleValue())
				.max();

		final OptionalDouble end = Streams.stream(bahnsteig)
				.flatMap(bsk -> toTopPoints(bsk).stream()
						.flatMap(Collection::stream))
				.map(point -> testee.findShortestDistanceInDirection(pzbPoint,
						point, isWirkrichtungTopDirection))
				.filter(Optional::isPresent)
				.mapToDouble(c -> c.get().doubleValue())
				.map(c -> -c)
				.min();
		return new BahnsteigDistance(start, end);
	}

	public static class BahnsteigDistance {
		private final OptionalDouble distanceEnd;
		private final OptionalDouble distanceStart;

		public BahnsteigDistance(final OptionalDouble min,
				final OptionalDouble max) {
			distanceStart = min;
			distanceEnd = max;
		}

		public BahnsteigDistance(final double start, final double end) {
			distanceStart = OptionalDouble.of(start);
			distanceEnd = OptionalDouble.of(end);
		}

		public BahnsteigDistance(final Double start, final Double end) {
			this(OptionalDouble.of(start.doubleValue()),
					OptionalDouble.of(end.doubleValue()));
		}

		public OptionalDouble getDistanceStart() {
			return distanceStart;
		}

		public OptionalDouble getDistanceEnd() {
			return distanceEnd;
		}
	}
}
