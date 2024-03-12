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
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

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
import org.eclipse.set.utils.ToolboxConfiguration;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventAdmin;
import org.osgi.service.event.EventConstants;
import org.osgi.service.event.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Implementation of BankingService
 */
@Component(property = {
		EventConstants.EVENT_TOPIC + "=" + Events.TOPMODEL_CHANGED,
		EventConstants.EVENT_TOPIC + "=" + Events.CLOSE_SESSION }, service = {
				EventHandler.class, BankService.class })
public class BankServiceImpl implements BankService, EventHandler {
	@Reference
	private TopologicalGraphService topGraph;

	@Reference
	private EventAdmin eventAdmin;

	private Map<TOP_Kante, Set<BankingInformation>> topEdgeBanking;
	private Map<Ueberhoehungslinie, Optional<BankingInformation>> bankingInformations;

	private boolean isComplete = false;

	private static final Logger logger = LoggerFactory
			.getLogger(BankServiceImpl.class);

	private Thread findBankingThread;

	@Override
	public void handleEvent(final Event event) {
		final String topic = event.getTopic();
		if (topic.equals(Events.TOPMODEL_CHANGED)) {
			final PlanPro_Schnittstelle planProSchnittstelle = (PlanPro_Schnittstelle) event
					.getProperty(IEventBroker.DATA);

			topEdgeBanking = new ConcurrentHashMap<>();
			bankingInformations = new ConcurrentHashMap<>();
			// final Use thread for final find banking value,
			// because this process can take a long time
			findBankingThread = new Thread() {
				@Override
				public void run() {
					try {
						isComplete = false;
						addBankingForContainer(PlanProSchnittstelleExtensions
								.getContainer(planProSchnittstelle,
										ContainerType.INITIAL));
						addBankingForContainer(PlanProSchnittstelleExtensions
								.getContainer(planProSchnittstelle,
										ContainerType.FINAL));
						addBankingForContainer(PlanProSchnittstelleExtensions
								.getContainer(planProSchnittstelle,
										ContainerType.SINGLE));
						isComplete = true;
						final HashMap<String, Object> properties = new HashMap<>();
						properties.put(EventConstants.EVENT_TOPIC,
								Events.BANKING_PROCESS_DONE);
						eventAdmin.sendEvent(new Event(
								Events.BANKING_PROCESS_DONE, properties));
					} catch (final InterruptedException e) {
						Thread.currentThread().interrupt();
					}
				}
			};
			findBankingThread.start();

		}

		if (topic.equals(Events.CLOSE_SESSION) && findBankingThread.isAlive()
				&& !findBankingThread.isInterrupted()) {
			findBankingThread.interrupt();
		}
	}

	private void addBankingForContainer(
			final MultiContainer_AttributeGroup container)
			throws InterruptedException {
		if (container == null) {
			return;
		}
		for (final Ueberhoehungslinie line : container
				.getUeberhoehungslinie()) {
			final BankingInformation bankingInformation = findTOPBanking(line);
			if (Thread.currentThread().isInterrupted()) {
				throw new InterruptedException();
			}
			if (bankingInformation == null) {
				logger.debug("Can't find TopPath for Ueberhoehungslinie: {}", //$NON-NLS-1$
						line.getIdentitaet().getWert());
				bankingInformations.put(line, Optional.empty());
			} else {
				logger.debug("Found TopPath for Ueberhoehungslinie: {}", //$NON-NLS-1$
						line.getIdentitaet().getWert());
				bankingInformations.put(line, Optional.of(bankingInformation));
			}
		}

		for (final Optional<BankingInformation> bankingInfo : bankingInformations
				.values()) {
			if (Thread.currentThread().isInterrupted()) {
				throw new InterruptedException();
			}
			if (bankingInfo.isEmpty()) {
				continue;
			}
			bankingInfo.get().path().edges().forEach(edge -> {
				topEdgeBanking.putIfAbsent(edge, new HashSet<>());
				topEdgeBanking.get(edge).add(bankingInfo.get());
			});
		}
	}

	@Override
	public BankingInformation findTOPBanking(
			final Ueberhoehungslinie bankingLine) {
		if (bankingInformations.containsKey(bankingLine)) {
			final Optional<BankingInformation> optional = bankingInformations
					.get(bankingLine);
			return optional.isEmpty() ? null : optional.get();
		}

		final Ueberhoehung begin = bankingLine.getIDUeberhoehungA();
		final Ueberhoehung end = bankingLine.getIDUeberhoehungB();
		final BigDecimal bankingLineLength = bankingLine
				.getUeberhoehungslinieAllg().getUeberhoehungslinieLaenge()
				.getWert();

		// If both Ueberhoehungen are on the same TOP_Kante, consider the path
		// the relevant one if lengths match
		final TOP_Kante beginEdge = begin.getPunktObjektTOPKante().get(0)
				.getIDTOPKante();
		if (beginEdge
				.equals(end.getPunktObjektTOPKante().get(0).getIDTOPKante())) {
			final BigDecimal distanceA = begin.getPunktObjektTOPKante().get(0)
					.getAbstand().getWert();
			final BigDecimal distanceB = end.getPunktObjektTOPKante().get(0)
					.getAbstand().getWert();

			final BigDecimal topLengthDifference = distanceA.subtract(distanceB)
					.abs().subtract(bankingLineLength).abs();

			if (topLengthDifference.doubleValue() <= ToolboxConfiguration
					.getBankLineTopOffsetLimit()) {
				return new BankingInformation(bankingLine,
						new TopPath(
								List.of(beginEdge), beginEdge.getTOPKanteAllg()
										.getTOPLaenge().getWert(),
								BigDecimal.ZERO));
			}
		}

		// Otherwise find all possible paths and find the path with the smallest
		// deviation from the banking line length
		// Add 1 to the limit to account for rounding errors
		final int limit = (int) (bankingLineLength.doubleValue()
				+ ToolboxConfiguration.getBankLineTopOffsetLimit() + 1);
		final List<TopPath> paths = topGraph.findAllPathsBetween(
				new TopPoint(begin), new TopPoint(end), limit);

		TopPath path = null;
		double minLengthDiff = Double.MAX_VALUE;
		for (final TopPath foundPath : paths) {
			final double diff = Math.abs(foundPath.length().doubleValue()
					- bankingLineLength.doubleValue());
			if (diff < minLengthDiff) {
				minLengthDiff = diff;
				path = foundPath;
			}
		}

		if (path == null || minLengthDiff > ToolboxConfiguration
				.getBankLineTopOffsetLimit()) {
			return null;
		}
		return new BankingInformation(bankingLine, path);
	}

	@Override
	public List<BankingInformation> findRelevantLineBankings(
			final TopPoint point) {
		final Set<BankingInformation> bankingLines = topEdgeBanking
				.get(point.edge());
		return bankingLines.stream().filter(line -> line.isOnBankingLine(point))
				.toList();
	}

	@Override
	public List<BigDecimal> findBankValue(final TopPoint point) {
		// If we have a banking line for this edge, use the line
		if (topEdgeBanking.containsKey(point.edge())) {
			final List<BigDecimal> lineBankings = findRelevantLineBankings(
					point).stream().map(line -> findBankingValue(point, line))
							.toList();
			if (!lineBankings.isEmpty()) {
				return lineBankings;
			}
		}

		// If find banking process not complete, then return empty list
		if (!isFindBankingComplete()) {
			return Collections.emptyList();
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
		final BigDecimal pointDistance = bankInfo.path().getDistance(point)
				.orElseThrow();

		final BigDecimal ueLeft = bankingLine.getIDUeberhoehungA()
				.getUeberhoehungAllg().getUeberhoehungHoehe().getWert();
		final BigDecimal ueRight = bankingLine.getIDUeberhoehungB()
				.getUeberhoehungAllg().getUeberhoehungHoehe().getWert();
		final BigDecimal length = bankingLine.getUeberhoehungslinieAllg()
				.getUeberhoehungslinieLaenge().getWert();

		final BigDecimal leftPosition = bankInfo.path()
				.getDistance(new TopPoint(bankingLine.getIDUeberhoehungA()))
				.orElseThrow();
		final BigDecimal rightPosition = bankInfo.path()
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

	@Override
	public boolean isFindBankingComplete() {
		return isComplete;
	}

}
