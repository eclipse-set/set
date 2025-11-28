/********************************************************************************
 * Copyright (c) 2024 DB InfraGO AG and others
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0 
 * 
 ********************************************************************************/
package org.eclipse.set.feature.table.pt1.sskp;

import static org.eclipse.set.ppmodel.extensions.BereichObjektExtensions.toTopPoints;
import static org.eclipse.set.ppmodel.extensions.PunktObjektExtensions.getSinglePoint;
import static org.eclipse.set.ppmodel.extensions.PunktObjektTopKanteExtensions.isBelongToBereichObjekt;
import static org.eclipse.set.ppmodel.extensions.PunktObjektTopKanteExtensions.isWirkrichtungTopDirection;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.OptionalDouble;

import org.eclipse.set.basis.graph.TopPoint;
import org.eclipse.set.core.services.Services;
import org.eclipse.set.core.services.graph.TopologicalGraphService;
import org.eclipse.set.model.planpro.Bahnsteig.Bahnsteig_Kante;
import org.eclipse.set.model.planpro.Basisobjekte.Punkt_Objekt_TOP_Kante_AttributeGroup;
import org.eclipse.set.model.planpro.PZB.PZB_Element;
import org.eclipse.set.ppmodel.extensions.PunktObjektExtensions;

import com.google.common.collect.Range;
import com.google.common.collect.Streams;

/**
 * Helper class to determine distances
 */
public class SskpBahnsteigUtils {
	// IMPROVE: This should be a record. Blocked by
	// https://github.com/eclipse/xtext/issues/2823
	@SuppressWarnings("javadoc")
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

	/**
	 * @param bahnsteigs
	 *            the list of {@link Bahnsteig_Kante}
	 * @param pzb
	 *            the {@link PZB_Element}
	 * @return the {@link BahnsteigDistance}
	 */
	public static BahnsteigDistance getBahnsteigDistances(
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

	private static BahnsteigDistance getBahnsteigDistancesNotAtMagnet(
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

	private static BahnsteigDistance getBahnsteigDistanceAtMagnet(
			final Iterable<Bahnsteig_Kante> bahnsteig, final PZB_Element pzb) {
		final Punkt_Objekt_TOP_Kante_AttributeGroup potk = getSinglePoint(pzb);
		final boolean isWirkrichtungTopDirection = isWirkrichtungTopDirection(
				potk);
		final TopPoint pzbPoint = new TopPoint(pzb);
		final TopologicalGraphService topGraphService = Services
				.getTopGraphService();
		final OptionalDouble start = Streams.stream(bahnsteig)
				.flatMap(bsk -> toTopPoints(bsk).stream()
						.flatMap(Collection::stream))
				.map(point -> topGraphService.findShortestDistanceInDirection(
						pzbPoint, point, !isWirkrichtungTopDirection))
				.filter(Optional::isPresent)
				.mapToDouble(c -> c.get().doubleValue())
				.max();

		final OptionalDouble end = Streams.stream(bahnsteig)
				.flatMap(bsk -> toTopPoints(bsk).stream()
						.flatMap(Collection::stream))
				.map(point -> topGraphService.findShortestDistanceInDirection(
						pzbPoint, point, isWirkrichtungTopDirection))
				.filter(Optional::isPresent)
				.mapToDouble(c -> c.get().doubleValue())
				.map(c -> -c)
				.min();
		return new BahnsteigDistance(start, end);
	}
}
