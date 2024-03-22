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

import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.stream.Stream;

import org.eclipse.set.basis.graph.TopPoint;
import org.eclipse.set.core.services.graph.TopologicalGraphService;
import org.eclipse.set.model.planpro.Bahnsteig.Bahnsteig_Kante;
import org.eclipse.set.model.planpro.Basisobjekte.Bereich_Objekt_Teilbereich_AttributeGroup;
import org.eclipse.set.model.planpro.Basisobjekte.Punkt_Objekt_TOP_Kante_AttributeGroup;
import org.eclipse.set.model.planpro.Basisobjekte.Seitliche_Lage_TypeClass;
import org.eclipse.set.model.planpro.Basisobjekte.Wirkrichtung_TypeClass;
import org.eclipse.set.model.planpro.PZB.PZB_Element;

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

		public OptionalDouble getDistanceStart() {
			return distanceStart;
		}

		public OptionalDouble getDistanceEnd() {
			return distanceEnd;
		}
	}

	private static boolean isInBOTB(
			final Bereich_Objekt_Teilbereich_AttributeGroup botb,
			final Punkt_Objekt_TOP_Kante_AttributeGroup point) {
		final double limitA = botb.getBegrenzungA().getWert().doubleValue();
		final double limitB = botb.getBegrenzungB().getWert().doubleValue();
		final double position = point.getAbstand().getWert().doubleValue();
		return limitA < position && position < limitB
				|| limitB < position && position < limitA;

	}

	static BahnsteigDistance getBahnsteigDistances(
			final TopologicalGraphService topGraphService,
			final List<Bahnsteig_Kante> bahnsteigs, final PZB_Element pzb) {

		final Punkt_Objekt_TOP_Kante_AttributeGroup point = pzb
				.getPunktObjektTOPKante().get(0);

		final boolean isPZBAtBahnsteig = bahnsteigs.stream()
				.anyMatch(bahnsteig -> bahnsteig.getBereichObjektTeilbereich()
						.stream()
						.filter(tb -> tb.getIDTOPKante()
								.equals(point.getIDTOPKante()))
						.anyMatch(tb -> isInBOTB(tb, point)));

		if (isPZBAtBahnsteig) {
			return getBahnsteigDistanceAtMagnet(topGraphService, bahnsteigs,
					pzb);
		}
		return getBahnsteigDistancesNotAtMagnet(topGraphService, bahnsteigs,
				pzb);
	}

	private static boolean isPZBWirkrichtungTopDirection(
			final PZB_Element pzb) {
		final Punkt_Objekt_TOP_Kante_AttributeGroup poto = pzb
				.getPunktObjektTOPKante().get(0);

		final Wirkrichtung_TypeClass wirkrichtung = poto.getWirkrichtung();

		if (wirkrichtung != null) {
			switch (wirkrichtung.getWert()) {
			case ENUM_WIRKRICHTUNG_GEGEN:
				return false;
			case ENUM_WIRKRICHTUNG_IN:
				return true;
			default:
				break;
			}
		}

		final Seitliche_Lage_TypeClass seitlicheLage = poto.getSeitlicheLage();

		if (seitlicheLage != null) {
			switch (seitlicheLage.getWert()) {
			case ENUM_LINKS_RECHTS_LINKS:
				return false;
			case ENUM_LINKS_RECHTS_RECHTS:
				return true;
			default:
				break;

			}
		}

		throw new UnsupportedOperationException(
				"PZB_Element has no wirkrichtung"); //$NON-NLS-1$
	}

	private static BahnsteigDistance getBahnsteigDistancesNotAtMagnet(
			final TopologicalGraphService topGraphService,
			final Iterable<Bahnsteig_Kante> bahnsteig, final PZB_Element pzb) {
		// If the final PZB is not final at a Bahnsteig_Kante, search in final
		// the opposite of final the
		// final effective direction
		final boolean searchDirection = !isPZBWirkrichtungTopDirection(pzb);
		final TopPoint pzbPoint = new TopPoint(pzb);
		final DoubleSummaryStatistics statistics = Streams.stream(bahnsteig)
				.flatMap(bsk -> bsk.getBereichObjektTeilbereich().stream()
						.flatMap(tb -> toTopPoints(tb)))
				.map(point -> topGraphService.findShortestDistanceInDirection(
						pzbPoint, point, searchDirection))
				.filter(Optional::isPresent)
				.mapToDouble(c -> c.get().doubleValue())
				.collect(DoubleSummaryStatistics::new,
						DoubleSummaryStatistics::accept,
						DoubleSummaryStatistics::combine);
		if (statistics.getCount() == 0) {
			return new BahnsteigDistance(OptionalDouble.empty(),
					OptionalDouble.empty());
		}
		return new BahnsteigDistance(statistics.getMax(), statistics.getMin());
	}

	private static BahnsteigDistance getBahnsteigDistanceAtMagnet(
			final TopologicalGraphService topGraphService,
			final Iterable<Bahnsteig_Kante> bahnsteig, final PZB_Element pzb) {
		final boolean isWirkrichtungTopDirection = isPZBWirkrichtungTopDirection(
				pzb);
		final TopPoint pzbPoint = new TopPoint(pzb);

		final OptionalDouble start = Streams.stream(bahnsteig)
				.flatMap(bsk -> bsk.getBereichObjektTeilbereich().stream()
						.flatMap(tb -> toTopPoints(tb)))
				.map(point -> topGraphService.findShortestDistanceInDirection(
						pzbPoint, point, !isWirkrichtungTopDirection))
				.filter(Optional::isPresent)
				.mapToDouble(c -> c.get().doubleValue()).max();

		final OptionalDouble end = Streams.stream(bahnsteig)
				.flatMap(bsk -> bsk.getBereichObjektTeilbereich().stream()
						.flatMap(tb -> toTopPoints(tb)))
				.map(point -> topGraphService.findShortestDistanceInDirection(
						pzbPoint, point, isWirkrichtungTopDirection))
				.filter(Optional::isPresent)
				.mapToDouble(c -> c.get().doubleValue()).map(c -> -c).min();
		return new BahnsteigDistance(start, end);
	}

	private static Stream<TopPoint> toTopPoints(
			final Bereich_Objekt_Teilbereich_AttributeGroup tb) {
		return Stream.of(
				new TopPoint(tb.getIDTOPKante().getValue(),
						tb.getBegrenzungA().getWert()),
				new TopPoint(tb.getIDTOPKante().getValue(),
						tb.getBegrenzungB().getWert()));

	}

}
