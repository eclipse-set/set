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
package org.eclipse.set.application.graph;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.set.basis.constants.ContainerType;
import org.eclipse.set.basis.constants.Events;
import org.eclipse.set.basis.graph.TopPath;
import org.eclipse.set.basis.graph.TopPoint;
import org.eclipse.set.core.services.graph.BankService;
import org.eclipse.set.core.services.graph.TopologicalGraphService;
import org.eclipse.set.ppmodel.extensions.PlanProSchnittstelleExtensions;
import org.eclipse.set.ppmodel.extensions.container.MultiContainer_AttributeGroup;
import org.eclipse.set.ppmodel.extensions.graph.TopObjectIterator;
import org.eclipse.set.toolboxmodel.Geodaten.TOP_Kante;
import org.eclipse.set.toolboxmodel.Geodaten.Ueberhoehung;
import org.eclipse.set.toolboxmodel.Geodaten.Ueberhoehungslinie;
import org.eclipse.set.toolboxmodel.PlanPro.PlanPro_Schnittstelle;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventConstants;
import org.osgi.service.event.EventHandler;

/**
 * Implementation of BankingService
 */
@Component(property = EventConstants.EVENT_TOPIC + "="
		+ Events.TOPMODEL_CHANGED, service = { EventHandler.class,
				BankService.class })
public class BankServiceImpl implements BankService, EventHandler {
	@Reference
	private TopologicalGraphService topGraph;

	private record BankingInformation(Ueberhoehungslinie line, TopPath path) {

		boolean isOnBankingLine(final TopPoint point) {
			final BigDecimal pointDistance = point.distance();
			if (path == null) {
				final BigDecimal ueLeft = line.getIDUeberhoehungA()
						.getPunktObjektTOPKante().get(0).getAbstand().getWert();
				final BigDecimal ueRight = line.getIDUeberhoehungB()
						.getPunktObjektTOPKante().get(0).getAbstand().getWert();
				final BigDecimal min = ueLeft.min(ueRight);
				final BigDecimal max = ueLeft.max(ueRight);

				return min.compareTo(pointDistance) <= 0
						&& max.compareTo(pointDistance) >= 0;
			}

			final Optional<BigDecimal> pDistance = path.getDistance(point);
			if (!pDistance.isPresent()) {
				return false;
			}

			// Validate bank line start is before the point object on the
			// first edge, if the point is on the first edge
			final Optional<BigDecimal> A = path
					.getDistance(new TopPoint(line.getIDUeberhoehungA()));
			final Optional<BigDecimal> B = path
					.getDistance(new TopPoint(line.getIDUeberhoehungB()));

			if (!A.isPresent() || !B.isPresent()) {
				return false;
			}
			final BigDecimal min = A.get().min(B.get());
			final BigDecimal max = A.get().max(B.get());

			return min.compareTo(pDistance.get()) <= 0
					&& max.compareTo(pDistance.get()) >= 0;

		}
	}

	private HashMap<TOP_Kante, Set<BankingInformation>> topEdgeBanking;

	@Override
	public void handleEvent(final Event event) {
		final PlanPro_Schnittstelle planProSchnittstelle = (PlanPro_Schnittstelle) event
				.getProperty(IEventBroker.DATA);

		topEdgeBanking = new HashMap<>();

		addBankingForContainer(PlanProSchnittstelleExtensions
				.getContainer(planProSchnittstelle, ContainerType.INITIAL));
		addBankingForContainer(PlanProSchnittstelleExtensions
				.getContainer(planProSchnittstelle, ContainerType.FINAL));
		addBankingForContainer(PlanProSchnittstelleExtensions
				.getContainer(planProSchnittstelle, ContainerType.SINGLE));
	}

	private void addBankingForContainer(
			final MultiContainer_AttributeGroup container) {
		if (container == null) {
			return;
		}
		container.getUeberhoehungslinie().forEach(this::findTOPBanking);
	}

	private void findTOPBanking(final Ueberhoehungslinie bankingLine) {
		final Ueberhoehung begin = bankingLine.getIDUeberhoehungA();
		final Ueberhoehung end = bankingLine.getIDUeberhoehungB();

		// If both Ueberhoehungen are on the same TOP_Kante, consider the path
		// the relevant one
		final TOP_Kante beginEdge = begin.getPunktObjektTOPKante().get(0)
				.getIDTOPKante();
		if (beginEdge
				.equals(end.getPunktObjektTOPKante().get(0).getIDTOPKante())) {
			topEdgeBanking.putIfAbsent(beginEdge, new HashSet<>());
			topEdgeBanking.get(beginEdge)
					.add(new BankingInformation(bankingLine,
							new TopPath(List.of(beginEdge),
									beginEdge.getTOPKanteAllg().getTOPLaenge()
											.getWert())));
			return;
		}

		// Otherwise find all possible paths and find the path with the smallest
		// deviation from the banking line length
		final List<TopPath> paths = topGraph
				.findAllPathsBetween(new TopPoint(begin), new TopPoint(end));
		final double bankingLineLength = bankingLine.getUeberhoehungslinieAllg()
				.getUeberhoehungslinieLaenge().getWert().doubleValue();

		TopPath path = null;
		double minLengthDiff = Double.MAX_VALUE;
		for (final TopPath foundPath : paths) {
			final double diff = Math
					.abs(foundPath.length().doubleValue() - bankingLineLength);
			if (diff < minLengthDiff) {
				minLengthDiff = diff;
				path = foundPath;
			}
		}

		if (path == null) {
			return;
		}

		final BankingInformation bankInfo = new BankingInformation(bankingLine,
				path);
		path.edges().forEach(e -> {
			topEdgeBanking.putIfAbsent(e, new HashSet<>());
			topEdgeBanking.get(e).add(bankInfo);
		});
	}

	@Override
	public List<BigDecimal> findBankValue(final TopPoint point) {
		// If we have a banking line for this edge, use the line
		if (topEdgeBanking.containsKey(point.edge())
				&& !topEdgeBanking.get(point.edge()).isEmpty()) {

			final Set<BankingInformation> bankingLines = topEdgeBanking
					.get(point.edge());
			final List<BankingInformation> relevantLineBankings = bankingLines
					.stream().filter(line -> line.isOnBankingLine(point))
					.toList();
			final List<BigDecimal> lineBankings = relevantLineBankings.stream()
					.map(line -> findBankingValue(point, line)).toList();
			if (!lineBankings.isEmpty()) {
				return lineBankings;
			}
		}

		// Otherwise find the nearest banking points left/right of the point on
		// the same edge
		final BigDecimal pointDistance = point.distance();
		final Optional<Ueberhoehung> left = TopObjectIterator
				.getEdgeObjectsFromPoint(point.edge(), false, pointDistance,
						Ueberhoehung.class)
				.findFirst();
		final Optional<Ueberhoehung> right = TopObjectIterator
				.getEdgeObjectsFromPoint(point.edge(), true, pointDistance,
						Ueberhoehung.class)
				.findFirst();

		if (!left.isPresent() || !right.isPresent()) {
			if (left.isPresent()) {
				return List.of(left.get().getUeberhoehungAllg()
						.getUeberhoehungHoehe().getWert());
			}
			if (right.isPresent()) {
				return List.of(right.get().getUeberhoehungAllg()
						.getUeberhoehungHoehe().getWert());
			}
			return List.of();
		}

		// If banking points were found, linearly interpolate
		final BigDecimal ueLeft = left.get().getUeberhoehungAllg()
				.getUeberhoehungHoehe().getWert();
		final BigDecimal ueRight = right.get().getUeberhoehungAllg()
				.getUeberhoehungHoehe().getWert();

		final BigDecimal leftPosition = left.get().getPunktObjektTOPKante()
				.get(0).getAbstand().getWert();
		final BigDecimal rightPosition = right.get().getPunktObjektTOPKante()
				.get(0).getAbstand().getWert();

		final BigDecimal length = leftPosition.subtract(rightPosition).abs();

		final BigDecimal distanceLeft = leftPosition.subtract(pointDistance)
				.abs();

		return List.of(
				bankingDefault(ueRight.subtract(ueLeft), distanceLeft, length)
						.add(ueLeft));
	}

	static BigDecimal findBankingValue(final TopPoint point,
			final BankingInformation bankInfo) {

		final Ueberhoehungslinie bankingLine = bankInfo.line();
		final BigDecimal pointDistance = bankInfo.path.getDistance(point)
				.orElseThrow();

		final BigDecimal ueLeft = bankingLine.getIDUeberhoehungA()
				.getUeberhoehungAllg().getUeberhoehungHoehe().getWert();
		final BigDecimal ueRight = bankingLine.getIDUeberhoehungB()
				.getUeberhoehungAllg().getUeberhoehungHoehe().getWert();
		final BigDecimal length = bankingLine.getUeberhoehungslinieAllg()
				.getUeberhoehungslinieLaenge().getWert();

		final BigDecimal leftPosition = bankInfo.path
				.getDistance(new TopPoint(bankingLine.getIDUeberhoehungA()))
				.orElseThrow();
		final BigDecimal rightPosition = bankInfo.path
				.getDistance(new TopPoint(bankingLine.getIDUeberhoehungB()))
				.orElseThrow();
		final BigDecimal distanceLeft = leftPosition.subtract(pointDistance)
				.abs();
		final BigDecimal distanceRight = rightPosition.subtract(pointDistance)
				.abs();

		return getBanking(bankingLine, ueLeft, ueRight, distanceLeft,
				distanceRight, length);
	}

	/**
	 * @param interval
	 *            the banking interval
	 * @param punktObjekt
	 *            the Punkt Objekt
	 */
	static BigDecimal getBanking(final Ueberhoehungslinie bankingLine,
			final BigDecimal left, final BigDecimal right,
			final BigDecimal distanceFromLeft,
			final BigDecimal distanceFromRight, final BigDecimal length) {
		final BigDecimal h_start = left;
		final BigDecimal h_end = right;
		final BigDecimal h_between = h_end.subtract(h_start);
		switch (bankingLine.getUeberhoehungslinieAllg()
				.getUeberhoehungslinieForm().getWert()) {
		case ENUM_UEBERHOEHUNGSLINIE_FORM_RAMPE_BLOSS:
			return bankingOfRampeBloss(h_between, distanceFromLeft, length)
					.add(h_start);
		case ENUM_UEBERHOEHUNGSLINIE_FORM_RAMPE_S:
			return bankingOfRampeS(h_between, distanceFromLeft,
					distanceFromRight, length).add(h_start);

		default:
			return bankingDefault(h_between, distanceFromLeft, length)
					.add(h_start);
		}
	}

	/**
	 * BankingIntervall S-Form to Bloss
	 * 
	 * When x <= L / 2, then: u = (3 * U / L^2) * x^2 - (2 * U / L^3) * x^3
	 * else: u = U / L * x* with: U: the height between banking start and
	 * banking end L: the length of banking x: distance form start of banking to
	 * point
	 */
	private static BigDecimal bankingOfRampeBloss(final BigDecimal h_between,
			final BigDecimal distanceFromLeft, final BigDecimal length) {
		if (distanceFromLeft
				.compareTo(length.divide(BigDecimal.valueOf(2))) > 0) {
			return bankingDefault(h_between, distanceFromLeft, length);
		}
		final BigDecimal a = h_between.multiply(BigDecimal.valueOf(3))
				.multiply(distanceFromLeft.pow(2))
				.divide(length.pow(2), RoundingMode.HALF_EVEN);
		final BigDecimal b = h_between.multiply(BigDecimal.valueOf(2))
				.multiply(distanceFromLeft.pow(3))
				.divide(length.pow(3), RoundingMode.HALF_EVEN);
		return a.add(b.negate());
	}

	/**
	 * BankingIntervall S-Form to Rampe 1. Case: when x =< L/2 => u = 2 * U *
	 * x^2 / L^2 2. Case: when x > L/2 => u = U - 2 * U * z^2 / L^2 with: U: the
	 * height between banking start and banking end L: the length of banking x:
	 * distance form start of banking to point z: distance from end of banking
	 * to point
	 */
	private static BigDecimal bankingOfRampeS(final BigDecimal h_between,
			final BigDecimal distanceFromLeft,
			final BigDecimal distanceFromRight, final BigDecimal length) {
		if (distanceFromLeft
				.compareTo(length.divide(BigDecimal.valueOf(2))) < 1) {
			return h_between.multiply(BigDecimal.valueOf(2))
					.multiply(distanceFromLeft.pow(2)).divide(length.pow(2));
		}
		return h_between.add(h_between.multiply(BigDecimal.valueOf(2))
				.multiply(distanceFromRight.pow(2)).divide(length.pow(2))
				.negate());
	}

	/**
	 * Default BankingInterval u = U / L * x with: U: the height between banking
	 * start and banking end L: the length of banking x: distance form start of
	 * banking to point
	 */
	private static BigDecimal bankingDefault(final BigDecimal h_between,
			final BigDecimal distanceFromLeft, final BigDecimal length) {
		return h_between.multiply(distanceFromLeft).divide(length,
				RoundingMode.HALF_EVEN);
	}

}
