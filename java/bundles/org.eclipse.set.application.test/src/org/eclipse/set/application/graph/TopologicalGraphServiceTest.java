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

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Stream;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.eclipse.set.basis.constants.ContainerType;
import org.eclipse.set.basis.graph.DirectedEdgePath;
import org.eclipse.set.basis.graph.TopPath;
import org.eclipse.set.basis.graph.TopPoint;
import org.eclipse.set.model.planpro.Basisobjekte.Punkt_Objekt;
import org.eclipse.set.model.planpro.Basisobjekte.Punkt_Objekt_TOP_Kante_AttributeGroup;
import org.eclipse.set.model.planpro.Basisobjekte.Ur_Objekt;
import org.eclipse.set.model.planpro.Fahrstrasse.Fstr_Fahrweg;
import org.eclipse.set.model.planpro.Geodaten.TOP_Kante;
import org.eclipse.set.model.planpro.Geodaten.TOP_Knoten;
import org.eclipse.set.model.planpro.PlanPro.PlanPro_Schnittstelle;
import org.eclipse.set.model.planpro.Signale.Signal;
import org.eclipse.set.ppmodel.extensions.BereichObjektExtensions;
import org.eclipse.set.ppmodel.extensions.FahrwegExtensions;
import org.eclipse.set.ppmodel.extensions.PlanProSchnittstelleExtensions;
import org.eclipse.set.ppmodel.extensions.container.MultiContainer_AttributeGroup;
import org.eclipse.set.ppmodel.extensions.utils.CollectionExtensions;
import org.eclipse.set.unittest.utils.AbstractToolboxTest;
import org.eclipse.set.utils.graph.AsSplitTopGraph.Edge;
import org.eclipse.set.utils.graph.AsSplitTopGraph.Node;
import org.jgrapht.graph.WeightedPseudograph;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import com.google.common.collect.Streams;

/**
 * 
 */
public class TopologicalGraphServiceTest extends AbstractToolboxTest {

	/**
	 * @return the siteplan reference files
	 */
	protected static Stream<Arguments> getReferenceFiles() {
		return Stream.of(Arguments.of(NB_Test_Info_2025_10_16_16_35, "nbTest"));
	}

	TopologicalGraphServiceImpl testee;

	@ParameterizedTest
	@MethodSource("getReferenceFiles")
	void testTopologicalGraphService(final String file) throws Exception {
		givenPlanProFile(file);
		givenTopologicalGraphService();
		doFindFtrsWegTopPath();
	}

	private void doFindFtrsWegTopPath() {
		final List<Fstr_Fahrweg> fstrFahrweg = getUrObjekts(
				container -> container.getFstrFahrweg());
		fstrFahrweg.forEach(fstr -> {
			try {
				if (fstr.getIdentitaet()
						.getWert()
						.equals("A14453E4-E4E3-4DD3-B4FE-E74F63A58300")) {
					System.out.println("TEST");
				}
				final DirectedEdgePath<TOP_Kante, TOP_Knoten, Punkt_Objekt_TOP_Kante_AttributeGroup> topGraphPath = FahrwegExtensions
						.getPath(fstr);

				final TopPath topPath = FahrwegExtensions.getPath(fstr, testee);
				final List<TOP_Kante> test = topGraphPath.getEdgeList()
						.stream()
						.map(edge -> edge.getElement())
						.toList();
				final List<TOP_Kante> test2 = topPath.edges();
				final List<TopPath> topPaths = getFstrFarhWegPath(fstr);
				assertEquals(1, topPaths.size(), () -> {
					return fstr.getIdentitaet().getWert();
				});
				assertEquals(test, topPaths.getFirst().edges(), () -> {
					return fstr.getIdentitaet().getWert();
				});
			} catch (final Exception e) {

			}

		});
	}

	private List<TopPath> getFstrFarhWegPath(final Fstr_Fahrweg fstr) {
		final Signal startSignal = fstr.getIDStart().getValue();
		final Punkt_Objekt zielPunktObjekt = FahrwegExtensions
				.getZielPunktObjekt(fstr);
		final Punkt_Objekt_TOP_Kante_AttributeGroup signalPotk = CollectionExtensions
				.getUnique(BereichObjektExtensions.intersection(fstr,
						startSignal));
		final Punkt_Objekt_TOP_Kante_AttributeGroup zielPotk = CollectionExtensions
				.getUnique(BereichObjektExtensions.intersection(fstr,
						zielPunktObjekt));
		if (signalPotk.getIDTOPKante().getValue() == zielPotk.getIDTOPKante()
				.getValue()) {
			return List.of(
					new TopPath(List.of(signalPotk.getIDTOPKante().getValue()),
							signalPotk.getAbstand()
									.getWert()
									.subtract(zielPotk.getAbstand().getWert())
									.abs(),
							new TopPoint(signalPotk)));
		}

		final BigDecimal length = BereichObjektExtensions.getLength(fstr);
		final TopPath testPath = testee.findPathBetween(
				new TopPoint(startSignal), new TopPoint(zielPunktObjekt),
				length.intValue() + 100, topPath -> {
					final int size = fstr.getBereichObjektTeilbereich().size();
					final List<TOP_Kante> topkanten = fstr
							.getBereichObjektTeilbereich()
							.stream()
							.map(botb -> botb.getIDTOPKante().getValue())
							.toList();
					return size == topPath.edges().size()
							&& topPath.edges().containsAll(topkanten);
				});
		final List<TopPath> allPathsBetween = testee.findAllPathsBetween(
				new TopPoint(signalPotk), new TopPoint(zielPotk),
				length.intValue() + 100);
		final List<TopPath> result = allPathsBetween.stream()
				.filter(path -> path.edges()
						.size() == fstr.getBereichObjektTeilbereich().size())
				.filter(path -> path.edges()
						.stream()
						.allMatch(edge -> fstr.getBereichObjektTeilbereich()
								.stream()
								.map(botb -> botb.getIDTOPKante().getValue())
								.anyMatch(topKante -> topKante == edge)))
				.toList();
		if (result.size() != 1) {

			System.out.println("TEST");
		}
		return result;
	}

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

	private <T extends Ur_Objekt> List<T> getUrObjekts(
			final Function<MultiContainer_AttributeGroup, Iterable<T>> getObjFunc) {
		return Arrays.stream(ContainerType.values())
				.map(type -> PlanProSchnittstelleExtensions
						.getContainer(planProSchnittstelle, type))
				.filter(Objects::nonNull)
				.flatMap(container -> Streams
						.stream(getObjFunc.apply(container)))
				.toList();
	}
}
