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
package org.eclipse.set.feature.table.pt1.ssls;

import static org.eclipse.set.ppmodel.extensions.BasisAttributExtensions.getContainer;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.set.basis.Pair;
import org.eclipse.set.model.planpro.Fahrstrasse.Fstr_Zug_Rangier;
import org.eclipse.set.model.planpro.Signale.Signal;
import org.eclipse.set.ppmodel.extensions.FstrZugRangierExtensions;

import com.google.common.collect.Streams;

/**
 * The Signaling section
 * 
 * @author truong
 * 
 */
public class SignalingSection {
	Signal startSignal;
	private final Set<SignalingRouteSection> signalingRouteSections;
	private final List<Fstr_Zug_Rangier> allTrainRoute;
	private final int MAXIMAL_ROUTE_SECTION = 4;

	/**
	 * @param startSignal
	 *            the start {@link Signal}
	 */
	public SignalingSection(final Signal startSignal) {
		this.startSignal = startSignal;
		allTrainRoute = Streams
				.stream(getContainer(startSignal).getFstrZugRangier())
				.filter(FstrZugRangierExtensions::isZ)
				.toList();
		signalingRouteSections = new HashSet<>();
		determineFstrAbschnitte();
	}

	private void determineFstrAbschnitte() {
		final List<Fstr_Zug_Rangier> fstrZugFromStart = allTrainRoute.stream()
				.filter(fstrZug -> FstrZugRangierExtensions
						.getStartSignal(fstrZug)
						.equals(startSignal))
				.toList();
		signalingRouteSections.addAll(fstrZugFromStart.stream().map(fstrZug -> {
			return new SignalingRouteSection(startSignal, fstrZug);
		}).toList());
		fstrZugFromStart.forEach(fstr -> determineFstrAbschnitte(fstr, 1));
	}

	private void determineFstrAbschnitte(final Fstr_Zug_Rangier fstrZug,
			final int sectionCount) {
		if (sectionCount >= MAXIMAL_ROUTE_SECTION) {
			return;
		}
		final Signal endSignal = FstrZugRangierExtensions
				.getZielSignal(fstrZug);
		// TODO: Point 2.1.a in excel template
		// Find the next sections from the end signal of last section
		final List<Fstr_Zug_Rangier> fstrZugsFromEndSignal = allTrainRoute
				.stream()
				.filter(fstr -> endSignal
						.equals(FstrZugRangierExtensions.getStartSignal(fstr)))
				.toList();
		if (fstrZugsFromEndSignal.isEmpty()) {
			return;
		}
		fstrZugsFromEndSignal.forEach(nextFstr -> {
			addNextAbschnitte(fstrZug, nextFstr);
			determineFstrAbschnitte(nextFstr, sectionCount + 1);
		});
	}

	private void addNextAbschnitte(final Fstr_Zug_Rangier preAbschnitte,
			final Fstr_Zug_Rangier fstrZug) {
		final List<SignalingRouteSection> foundedAbschnitte = signalingRouteSections
				.stream()
				.filter(fstr -> fstr.getRouteSection().equals(preAbschnitte))
				.toList();
		foundedAbschnitte.forEach(section -> {
			final List<SignalingRouteSection> preRouteSections = new LinkedList<>(
					section.getPreRouteSections());
			preRouteSections.add(section);
			// The section always start with the start Signal of the signaling
			// route
			final SignalingRouteSection next = new SignalingRouteSection(
					startSignal, fstrZug, preRouteSections);
			signalingRouteSections.add(next);
		});
	}

	/**
	 * @return the start {@link Signal}
	 */
	public Signal getStartSignal() {
		return startSignal;
	}

	/**
	 * @return the signaling route sections
	 */
	public Set<SignalingRouteSection> getSignalingRouteSections() {
		return signalingRouteSections;
	}

	/**
	 * @return the end {@link Signal} of this signaling section
	 */
	public Signal getEndSignal() {
		final Set<Signal> signalBetween = getSignalBetween();
		final SignalingRouteSection lastSection = signalingRouteSections
				.stream()
				.filter(routeSection -> !signalBetween
						.contains(routeSection.getEndRouteSecionSignal()))
				.findFirst()
				.orElse(null);
		if (lastSection == null) {
			throw new RuntimeException("Missing last section"); //$NON-NLS-1$
		}
		return lastSection.getEndRouteSecionSignal();
	}

	/**
	 * @return the signals between start and end {@link Signal} of this
	 *         Signaling section
	 */
	public Set<Signal> getSignalBetween() {
		return signalingRouteSections.stream()
				.flatMap(routeSection -> routeSection.getElementBetween()
						.stream()
						.map(Pair::getFirst))
				.collect(Collectors.toSet());
	}
}
