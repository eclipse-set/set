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
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.set.basis.constants.ContainerType;
import org.eclipse.set.basis.constants.Events;
import org.eclipse.set.basis.constants.ToolboxConstants;
import org.eclipse.set.basis.files.ToolboxFileRole;
import org.eclipse.set.basis.graph.TopPath;
import org.eclipse.set.basis.graph.TopPoint;
import org.eclipse.set.core.services.graph.BankService;
import org.eclipse.set.core.services.graph.TopologicalGraphService;
import org.eclipse.set.core.services.session.SessionService;
import org.eclipse.set.model.planpro.Basisobjekte.Punkt_Objekt_TOP_Kante_AttributeGroup;
import org.eclipse.set.model.planpro.Basisobjekte.Ur_Objekt;
import org.eclipse.set.model.planpro.Geodaten.TOP_Kante;
import org.eclipse.set.model.planpro.Geodaten.TOP_Knoten;
import org.eclipse.set.model.planpro.Geodaten.Ueberhoehung;
import org.eclipse.set.model.planpro.Geodaten.Ueberhoehungslinie;
import org.eclipse.set.model.planpro.PlanPro.PlanPro_Schnittstelle;
import org.eclipse.set.ppmodel.extensions.BasisAttributExtensions;
import org.eclipse.set.ppmodel.extensions.MultiContainer_AttributeGroupExtensions;
import org.eclipse.set.ppmodel.extensions.PlanProSchnittstelleExtensions;
import org.eclipse.set.ppmodel.extensions.PunktObjektExtensions;
import org.eclipse.set.ppmodel.extensions.TopKanteExtensions;
import org.eclipse.set.ppmodel.extensions.container.MultiContainer_AttributeGroup;
import org.eclipse.set.ppmodel.extensions.graph.TopObjectIterator;
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

	private class BankingInformationSession {
		private final Map<TOP_Kante, Set<BankingInformation>> topEdgeBanking;
		private final Map<Ueberhoehungslinie, Optional<BankingInformation>> bankingInformations;

		public BankingInformationSession() {
			this.topEdgeBanking = new ConcurrentHashMap<>();
			this.bankingInformations = new ConcurrentHashMap<>();
		}

		public Map<TOP_Kante, Set<BankingInformation>> getTopEdgeBanking() {
			return topEdgeBanking;
		}

		public Map<Ueberhoehungslinie, Optional<BankingInformation>> getBankingInformations() {
			return bankingInformations;
		}

	}

	@Reference
	private TopologicalGraphService topGraph;

	@Reference
	private EventAdmin eventAdmin;

	@Reference
	private SessionService sessionService;
	private final Map<PlanPro_Schnittstelle, BankingInformationSession> bankingSessiones = new HashMap<>();

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
			final BankingInformationSession collection = getBankingSession(
					planProSchnittstelle);
			collection.getBankingInformations().clear();
			collection.getTopEdgeBanking().clear();

			// final Use thread for final find banking value,
			// because this process can take a long time
			findBankingThread = new Thread() {
				@Override
				public void run() {
					try {
						isComplete = false;
						addBankingForContainer(collection,
								PlanProSchnittstelleExtensions.getContainer(
										planProSchnittstelle,
										ContainerType.INITIAL));
						addBankingForContainer(collection,
								PlanProSchnittstelleExtensions.getContainer(
										planProSchnittstelle,
										ContainerType.FINAL));
						addBankingForContainer(collection,
								PlanProSchnittstelleExtensions.getContainer(
										planProSchnittstelle,
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

		if (topic.equals(Events.CLOSE_SESSION)) {
			final ToolboxFileRole role = (ToolboxFileRole) event
					.getProperty(IEventBroker.DATA);
			if (role == ToolboxFileRole.SESSION) {
				bankingSessiones.clear();
			} else {
				final PlanPro_Schnittstelle closed = sessionService
						.getLoadedSession(role)
						.getPlanProSchnittstelle();
				bankingSessiones.remove(closed);
			}
			if (!isComplete) {
				findBankingThread.interrupt();
				isComplete = false;
			}

		}
	}

	private BankingInformationSession getBankingSession(
			final Ur_Objekt object) {
		final MultiContainer_AttributeGroup container = BasisAttributExtensions
				.getContainer(object);
		final PlanPro_Schnittstelle planProSchnittstelle = MultiContainer_AttributeGroupExtensions
				.getPlanProSchnittstelle(container);
		return getBankingSession(planProSchnittstelle);
	}

	private BankingInformationSession getBankingSession(
			final PlanPro_Schnittstelle schnittStelle) {
		return bankingSessiones.computeIfAbsent(schnittStelle,
				k -> new BankingInformationSession());
	}

	private void addBankingForContainer(
			final BankingInformationSession bankingSession,
			final MultiContainer_AttributeGroup container)
			throws InterruptedException {
		if (container == null) {
			return;
		}
		for (final Ueberhoehungslinie line : container
				.getUeberhoehungslinie()) {
			if (Thread.currentThread().isInterrupted()) {
				throw new InterruptedException();
			}

			try {
				final BankingInformation bankingInformation = findTOPBanking(
						line);

				if (bankingInformation == null) {
					logger.debug(
							"Can't find TopPath for Ueberhoehungslinie: {}", //$NON-NLS-1$
							line.getIdentitaet().getWert());
					bankingSession.getBankingInformations()
							.put(line, Optional.empty());
				} else {
					logger.debug("Found TopPath for Ueberhoehungslinie: {}", //$NON-NLS-1$
							line.getIdentitaet().getWert());
					bankingSession.getBankingInformations()
							.put(line, Optional.of(bankingInformation));
				}
			} catch (final NullPointerException e) {
				logger.error(e.getMessage());
			}
		}

		for (final Optional<BankingInformation> bankingInfo : bankingSession
				.getBankingInformations()
				.values()) {
			if (Thread.currentThread().isInterrupted()) {
				throw new InterruptedException();
			}
			if (bankingInfo.isEmpty()) {
				continue;
			}
			bankingInfo.get().path().edges().forEach(edge -> {
				bankingSession.getTopEdgeBanking()
						.putIfAbsent(edge, new HashSet<>());
				bankingSession.getTopEdgeBanking()
						.get(edge)
						.add(bankingInfo.get());
			});
		}
	}

	@Override
	public BankingInformation findTOPBanking(
			final Ueberhoehungslinie bankingLine) {
		final BankingInformationSession bankingSession = getBankingSession(
				bankingLine);
		final Map<Ueberhoehungslinie, Optional<BankingInformation>> bankingInformations = bankingSession
				.getBankingInformations();
		if (bankingInformations.containsKey(bankingLine)) {
			final Optional<BankingInformation> optional = bankingInformations
					.get(bankingLine);
			return optional.isEmpty() ? null : optional.get();
		}

		final Ueberhoehung begin = bankingLine.getIDUeberhoehungA().getValue();
		final Ueberhoehung end = bankingLine.getIDUeberhoehungB().getValue();
		if (begin == null || end == null) {
			return null;
		}

		final BigDecimal bankingLineLength = bankingLine
				.getUeberhoehungslinieAllg()
				.getUeberhoehungslinieLaenge()
				.getWert();

		// If both Ueberhoehungen are on the same TOP_Kante, consider the path
		// the relevant one if lengths match
		final TOP_Kante beginEdge = begin.getPunktObjektTOPKante()
				.get(0)
				.getIDTOPKante()
				.getValue();
		if (beginEdge.equals(end.getPunktObjektTOPKante()
				.get(0)
				.getIDTOPKante()
				.getValue())) {
			final BigDecimal distanceA = begin.getPunktObjektTOPKante()
					.get(0)
					.getAbstand()
					.getWert();
			final BigDecimal distanceB = end.getPunktObjektTOPKante()
					.get(0)
					.getAbstand()
					.getWert();

			final BigDecimal topLengthDifference = distanceA.subtract(distanceB)
					.abs()
					.subtract(bankingLineLength)
					.abs();

			if (topLengthDifference.doubleValue() <= ToolboxConfiguration
					.getBankLineTopOffsetLimit()) {
				final BigDecimal pathLength = beginEdge.getTOPKanteAllg()
						.getTOPLaenge()
						.getWert();
				return new BankingInformation(bankingLine, new TopPath(
						List.of(beginEdge), pathLength, pathLength));
			}
		}

		if (!bankingLine.getIDTOPKantePfad().isEmpty()) {
			try {
				return getBankingInformation(bankingLine);
			} catch (final IllegalArgumentException e) {
				logger.error(
						"IDTOPKantePfad der Überhöhungslinien {} ist nicht plausible", //$NON-NLS-1$
						bankingLine.getIdentitaet().getWert());
				return null;
			}

		}
		// Otherwise find all possible paths and find the path with the smallest
		// deviation from the banking line length
		// Add 1 to the limit to account for rounding errors
		final int limit = (int) (bankingLineLength.doubleValue()
				+ ToolboxConfiguration.getBankLineTopOffsetLimit() + 1);

		final Predicate<TopPath> predicate = t -> {
			final BigDecimal diff = t.length()
					.subtract(bankingLineLength)
					.abs();
			return diff.doubleValue() < ToolboxConfiguration
					.getBankLineTopOffsetLimit();
		};
		final TopPath path = topGraph.findPathBetween(new TopPoint(begin),
				new TopPoint(end), limit, predicate);

		if (path == null) {
			return null;
		}
		return new BankingInformation(bankingLine, path);
	}

	/**
	 * The Banking path is already defined. This function transform the
	 * information to {@link BankingInformation}
	 * 
	 * @param bankingLine
	 *            the {@link Ueberhoehungslinie}
	 * @return the {@link BankingInformation}
	 * @throws IllegalArgumentException
	 *             when the path isn't plausible
	 */
	private static BankingInformation getBankingInformation(
			final Ueberhoehungslinie bankingLine)
			throws IllegalArgumentException {
		final Set<TOP_Kante> relevantEdges = bankingLine.getIDTOPKantePfad()
				.stream()
				.map(ele -> ele.getValue())
				.collect(Collectors.toSet());
		final Punkt_Objekt_TOP_Kante_AttributeGroup start = PunktObjektExtensions
				.getSinglePoint(bankingLine.getIDUeberhoehungA().getValue());
		final Punkt_Objekt_TOP_Kante_AttributeGroup end = PunktObjektExtensions
				.getSinglePoint(bankingLine.getIDUeberhoehungB().getValue());
		// When the defined path not contains start/end point
		if (relevantEdges.stream()
				.noneMatch(edge -> edge == start.getIDTOPKante().getValue())
				|| relevantEdges.stream()
						.noneMatch(edge -> edge == end.getIDTOPKante()
								.getValue())) {
			throw new IllegalArgumentException();
		}
		final List<TOP_Kante> sortedEdges = new LinkedList<>();
		TOP_Kante currentEdge = start.getIDTOPKante().getValue();
		BigDecimal pathLength = BigDecimal.ZERO;

		while (!relevantEdges.isEmpty()) {
			sortedEdges.add(currentEdge);
			relevantEdges.remove(currentEdge);
			if (currentEdge == end.getIDTOPKante().getValue()) {
				// when the path isn't end at end point
				if (!relevantEdges.isEmpty()) {
					throw new IllegalArgumentException();
				}
				// Edge before last edge
				final TOP_Kante beforeLastEdge = sortedEdges
						.get(sortedEdges.size() - 2);
				final TOP_Knoten connectionNode = TopKanteExtensions
						.connectionTo(currentEdge, beforeLastEdge);
				final BigDecimal lastEdgeLength = connectionNode == currentEdge
						.getIDTOPKnotenA()
						.getValue() ? end.getAbstand().getWert()
								: TopKanteExtensions.getLaenge(currentEdge)
										.subtract(end.getAbstand().getWert());
				pathLength = pathLength.add(lastEdgeLength);
				break;
			}
			final List<TOP_Kante> nextEdges = new ArrayList<>();
			for (final TOP_Kante edge : relevantEdges) {
				if (TopKanteExtensions.isConnectedTo(edge, currentEdge)) {
					nextEdges.add(edge);
				}
			}

			// The current edge should only connected to one edge in the path
			if (nextEdges.size() != 1) {
				throw new IllegalArgumentException();
			}
			if (currentEdge == start.getIDTOPKante().getValue()) {
				final TOP_Knoten connectionNode = TopKanteExtensions
						.connectionTo(currentEdge, nextEdges.getFirst());
				final BigDecimal firstEdgeLength = connectionNode == currentEdge
						.getIDTOPKnotenB()
						.getValue()
								? TopKanteExtensions.getLaenge(currentEdge)
										.subtract(start.getAbstand().getWert())
								: start.getAbstand().getWert();
				pathLength = pathLength.add(firstEdgeLength);
			} else {
				pathLength = pathLength
						.add(TopKanteExtensions.getLaenge(currentEdge));
			}
			currentEdge = nextEdges.getFirst();
		}
		return new BankingInformation(bankingLine,
				new TopPath(sortedEdges, pathLength, new TopPoint(start)));
	}

	@Override
	public List<BankingInformation> findRelevantLineBankings(
			final TopPoint point) {
		final BankingInformationSession bankingSession = getBankingSession(
				point.edge());
		final Set<BankingInformation> bankingLines = bankingSession
				.getTopEdgeBanking()
				.get(point.edge());
		return bankingLines.stream()
				.filter(line -> line.isOnBankingLine(point))
				.toList();
	}

	@Override
	public List<BigDecimal> findBankValue(final TopPoint point) {
		final BankingInformationSession bankingSession = getBankingSession(
				point.edge());
		// If we have a banking line for this edge, use the line
		if (bankingSession.getTopEdgeBanking().containsKey(point.edge())) {
			final List<BigDecimal> lineBankings = findRelevantLineBankings(
					point).stream()
							.map(line -> findBankingValue(point, line))
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
				return List.of(left.get()
						.getUeberhoehungAllg()
						.getUeberhoehungHoehe()
						.getWert());
			}
			if (right.isPresent()) {
				return List.of(right.get()
						.getUeberhoehungAllg()
						.getUeberhoehungHoehe()
						.getWert());
			}
			return List.of();
		}

		// If banking points were found, linearly interpolate
		final BigDecimal ueLeft = left.get()
				.getUeberhoehungAllg()
				.getUeberhoehungHoehe()
				.getWert();
		final BigDecimal ueRight = right.get()
				.getUeberhoehungAllg()
				.getUeberhoehungHoehe()
				.getWert();

		final BigDecimal leftPosition = left.get()
				.getPunktObjektTOPKante()
				.get(0)
				.getAbstand()
				.getWert();
		final BigDecimal rightPosition = right.get()
				.getPunktObjektTOPKante()
				.get(0)
				.getAbstand()
				.getWert();

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
		final BigDecimal pointDistance = bankInfo.path()
				.getDistance(point)
				.orElseThrow();

		final BigDecimal ueLeft = bankingLine.getIDUeberhoehungA()
				.getValue()
				.getUeberhoehungAllg()
				.getUeberhoehungHoehe()
				.getWert();
		final BigDecimal ueRight = bankingLine.getIDUeberhoehungB()
				.getValue()
				.getUeberhoehungAllg()
				.getUeberhoehungHoehe()
				.getWert();
		final BigDecimal length = bankingLine.getUeberhoehungslinieAllg()
				.getUeberhoehungslinieLaenge()
				.getWert();

		final BigDecimal leftPosition = bankInfo.path()
				.getDistance(new TopPoint(
						bankingLine.getIDUeberhoehungA().getValue()))
				.orElseThrow();
		final BigDecimal rightPosition = bankInfo.path()
				.getDistance(new TopPoint(
						bankingLine.getIDUeberhoehungB().getValue()))
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
				.getUeberhoehungslinieForm()
				.getWert()) {
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
		if (distanceFromLeft.compareTo(length.divide(BigDecimal.valueOf(2),
				ToolboxConstants.ROUNDING_TO_PLACE,
				RoundingMode.HALF_UP)) > 0) {
			return bankingDefault(h_between, distanceFromLeft, length);
		}
		final BigDecimal a = h_between.multiply(BigDecimal.valueOf(3))
				.multiply(distanceFromLeft.pow(2))
				.divide(length.pow(2), ToolboxConstants.ROUNDING_TO_PLACE,
						RoundingMode.HALF_EVEN);
		final BigDecimal b = h_between.multiply(BigDecimal.valueOf(2))
				.multiply(distanceFromLeft.pow(3))
				.divide(length.pow(3), ToolboxConstants.ROUNDING_TO_PLACE,
						RoundingMode.HALF_EVEN);
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
		if (distanceFromLeft.compareTo(length.divide(BigDecimal.valueOf(2),
				ToolboxConstants.ROUNDING_TO_PLACE,
				RoundingMode.HALF_UP)) < 1) {
			return h_between.multiply(BigDecimal.valueOf(2))
					.multiply(distanceFromLeft.pow(2))
					.divide(length.pow(2), ToolboxConstants.ROUNDING_TO_PLACE,
							RoundingMode.HALF_UP);
		}
		return h_between.add(h_between.multiply(BigDecimal.valueOf(2))
				.multiply(distanceFromRight.pow(2))
				.divide(length.pow(2), ToolboxConstants.ROUNDING_TO_PLACE,
						RoundingMode.HALF_UP)
				.negate());
	}

	/**
	 * Default BankingInterval u = U / L * x with: U: the height between banking
	 * start and banking end L: the length of banking x: distance form start of
	 * banking to point
	 */
	private static BigDecimal bankingDefault(final BigDecimal h_between,
			final BigDecimal distanceFromLeft, final BigDecimal length) {
		return h_between.multiply(distanceFromLeft)
				.divide(length, ToolboxConstants.ROUNDING_TO_PLACE,
						RoundingMode.HALF_EVEN);
	}

	@Override
	public boolean isFindBankingComplete() {
		return isComplete;
	}

}
