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

import java.util.ArrayList;
import java.util.List;

import org.eclipse.set.model.planpro.Fahrstrasse.Fstr_Zug_Rangier;
import org.eclipse.set.model.planpro.Signale.Signal;
import org.eclipse.set.ppmodel.extensions.EObjectExtensions;
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
	private final List<SignalingRouteSection> signalingRouteSections;
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
		signalingRouteSections = new ArrayList<>();
		determineFstrAbschnitte();
	}

	private void determineFstrAbschnitte() {
		final List<Fstr_Zug_Rangier> fstrZugFromStart = allTrainRoute.stream()
				.filter(fstrZug -> FstrZugRangierExtensions
						.getStartSignal(fstrZug)
						.equals(startSignal))
				.toList();
		signalingRouteSections.addAll(fstrZugFromStart.stream().map(fstrZug -> {
			final SignalingRouteSection abschnitte = new SignalingRouteSection(
					startSignal);
			abschnitte.endSignal = FstrZugRangierExtensions
					.getZielSignal(fstrZug);
			abschnitte.routeSections.add(fstrZug);
			abschnitte.dweg = EObjectExtensions
					.getNullableObject(fstrZug,
							FstrZugRangierExtensions::getFstrDWeg)
					.orElse(null);
			return abschnitte;
		}).toList());
		fstrZugFromStart.forEach(fstr -> determineFstrAbschnitte(fstr, 1));
	}

	private void determineFstrAbschnitte(final Fstr_Zug_Rangier fstrZug,
			final int sectionCount) {
		if (sectionCount > MAXIMAL_ROUTE_SECTION) {
			return;
		}
		final Signal endSignal = FstrZugRangierExtensions
				.getZielSignal(fstrZug);
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
				.filter(fstr -> fstr.routeSections.getLast()
						.equals(preAbschnitte))
				.toList();
		foundedAbschnitte.forEach(abchnitt -> {
			// The section always start with the start Signal of the signaling
			// route
			final SignalingRouteSection next = new SignalingRouteSection(
					startSignal);
			next.endSignal = FstrZugRangierExtensions.getZielSignal(fstrZug);
			next.routeSections.addAll(abchnitt.routeSections);
			next.routeSections.add(fstrZug);
			next.dweg = EObjectExtensions
					.getNullableObject(fstrZug,
							FstrZugRangierExtensions::getFstrDWeg)
					.orElse(null);
			next.getSignalsBetween().addAll(abchnitt.getSignalsBetween());
			next.getSignalsBetween().add(abchnitt.endSignal);
			addFstrAbschnitte(next);
		});
	}

	private void addFstrAbschnitte(final SignalingRouteSection newAbschnitt) {
		if (signalingRouteSections.stream().anyMatch(abschnitt -> {
			return abschnitt.endSignal.equals(newAbschnitt.endSignal)
					&& abschnitt.getSignalsBetween()
							.size() == newAbschnitt.getSignalsBetween().size()
					&& abschnitt.getSignalsBetween()
							.containsAll(newAbschnitt.getSignalsBetween());
		})) {
			return;
		}
		signalingRouteSections.add(newAbschnitt);
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
	public List<SignalingRouteSection> getSignalingRouteSections() {
		return signalingRouteSections;
	}
}
